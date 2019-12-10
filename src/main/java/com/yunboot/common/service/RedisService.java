package com.yunboot.common.service;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService extends AbstractRedisService
{
    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    public boolean set(final String key, Object value)
    {
        try
        {
            this.getValueOps().set(key, value);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean set(final String key, Object value, int expireTime)
    {
        try
        {
            this.getValueOps().set(key, value, expireTime, TimeUnit.SECONDS);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void delete(final String key)
    {
        if (exists(key))
        {
            redisTemplate.delete(key);
        }
    }

    public boolean exists(String key)
    {
        return redisTemplate.hasKey(key);
    }

    public void batchDel(String keyPattern)
    {
        Set<String> keys = redisTemplate.keys(keyPattern);
        redisTemplate.delete(keys);
    }

    public void delete(String... keys)
    {
        for (String key : keys)
        {
            this.delete(key);
        }
    }

    public Object get(final String key)
    {
        return this.getValueOps().get(key);
    }

    public void hashSet(String key, Object hashKey, Object value)
    {
        this.getHashOps().put(key, hashKey, value);
    }

    public Object hashGet(String key, Object hashKey)
    {
        return this.getHashOps().get(key, hashKey);
    }

    public void lPush(String key, Object value)
    {
        this.getListOps().leftPush(key, value);
    }

    public void rPush(String key, Object value)
    {
        this.getListOps().rightPush(key, value);
    }

    public Object rPop(String key)
    {
        return this.getListOps().rightPop(key);
    }

    public Object lPop(String key)
    {
        return this.getListOps().leftPop(key);
    }

    public List<Object> range(String key, long start, long end)
    {
        return this.getListOps().range(key, start, end);
    }

    public long listSize(String key)
    {
        return this.getListOps().size(key);
    }

    public void addSet(String key, Object... values)
    {
        this.getSetOps().add(key, values);
    }

    public Set<Object> getSet(String key)
    {
        return this.getSetOps().members(key);
    }

    public void zSetAdd(String key, Object value, double score)
    {
        this.getZsetOps().add(key, value, score);
    }

    public Set<Object> zsetGet(String key, long start, long end)
    {
        return this.getZsetOps().range(key, start, end);
    }

    public long zSize(String key)
    {
        return this.getZsetOps().size(key);
    }

    public long increase(String key, long stepLen)
    {
        return this.getValueOps().increment(key, stepLen);
    }

    public boolean lock(String key, String value, long time)
    {
        try
        {

            RedisCallback<String> callback = (connection) ->
            {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, value, "NX", "PX", time);
            };
            String result = redisTemplate.execute(callback);
            return !StringUtils.isEmpty(result);
        }
        catch (Exception e)
        {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }

    public String getLock(String key)
    {
        try
        {
            RedisCallback<String> callback = (connection) ->
            {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };
            String result = redisTemplate.execute(callback);
            return result;
        }
        catch (Exception e)
        {
            logger.error("get redis occured an exception", e);
        }
        return "";
    }

    /**
     * lua脚本
     **/
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    public boolean unlock(String key, String value)
    {
        try
        {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
            args.add(value);
            RedisCallback<Long> callback = (connection) ->
            {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster)
                {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis)
                {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            };
            Long result = redisTemplate.execute(callback);
            return result != null && result > 0;
        }
        catch (Exception e)
        {
            logger.error("release lock occured an exception", e);
        }
        finally
        {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            //lockFlag.remove();
        }
        return false;
    }

}

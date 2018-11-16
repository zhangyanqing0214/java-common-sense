package com.yunboot.common.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.yunboot.common.config.YunBootRedisTemplate;

@Service
public class RedisService
{

    @Autowired
    private YunBootRedisTemplate redisTemplate;

    private ValueOperations<String, Object> getValueOps()
    {
        return redisTemplate.opsForValue();
    }

    private ListOperations<String, Object> getListOps()
    {
        return redisTemplate.opsForList();
    }

    private SetOperations<String, Object> getSetOps()
    {
        return redisTemplate.opsForSet();
    }

    private HashOperations<String, Object, Object> getHashOps()
    {
        return redisTemplate.opsForHash();
    }

    private ZSetOperations<String, Object> getZsetOps()
    {
        return redisTemplate.opsForZSet();
    }

    private GeoOperations<String, Object> getGeoOps()
    {
        return redisTemplate.opsForGeo();
    }

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

}
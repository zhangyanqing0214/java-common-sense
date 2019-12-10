package com.yunboot.common.service;

import com.yunboot.common.BaseTests;
import com.yunboot.common.config.*;
import com.yunboot.common.enums.CrmServiceEnum;
import com.yunboot.common.exception.CommonException;
import lombok.extern.java.Log;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * [简要描述]:service层单元测试
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2018年11月14日
 * @since JDK1.8
 */
@Log
public class CommonService extends BaseTests
{

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private RedisService redisService;

    @Test
    public void sayHi()
    {
        log.info("test good ");
    }

    @Test
    public void testException()
    {
        CommonException ex = new CommonException(CrmServiceEnum.ID_IS_NULL);
        System.out.println(ex);
    }

    @Test
    public void testRedisConfig()
    {

        RedisConfig redisConfig = new RedisConfig();
        YunBootJedisPoolConfig jedisPoolConfig = redisConfig.buildYunBootJedisPoolConfig();
        YunBootRedisSentinelConfiguration sentinelConfiguration = redisConfig.bulidYunBootRedisSentinelConfiguration();
        YunBootRedisConnectionFactory connectionFactory = redisConfig.bulidYunBootRedisConnectionFactory(sentinelConfiguration, jedisPoolConfig);
        connectionFactory.afterPropertiesSet();
        YunBootRedisTemplate redisTemplate = redisConfig.bulidYunBootRedisTemplate(connectionFactory);
        redisTemplate.afterPropertiesSet();
        redisTemplate.opsForValue().set("test_123_22", "配置真麻烦");
        Object value = redisTemplate.opsForValue().get("test_123_22");
        System.out.println(value.toString());
    }

    @Test
    public void testRedis()
    {
        String value_key = "redis_key";
        String list_key = "list_key_test";
        String set_key = "set_key";
        String hash_key = "hash_key";
        String zset_key = "zset_key";
        this.redisService.set(value_key, 124342);
        System.out.println(this.redisService.get(value_key));

        System.out.println(this.redisService.exists(value_key));
        List<Object> list = Lists.newArrayList(true, "12", 12334);
        this.redisService.lPush(list_key, list);
        this.redisService.lPush(list_key, "tet");
        System.out.println(this.redisService.lPop(list_key));
        System.out.println(this.redisService.range(list_key, 0, list.size()));

        this.redisService.addSet(set_key, "gggg");
        System.out.println(this.redisService.getSet(set_key));

        this.redisService.hashSet(hash_key, "hash_key", "hashValue");
        System.out.println(this.redisService.hashGet(hash_key, "hash_key"));

        this.redisService.zSetAdd(zset_key, "zset_key", 30d);
        System.out.println(this.redisService.zsetGet("dddd", 0, redisService.zSize(zset_key)));
        System.out.println(this.redisService.zSize("fdfsdf"));

    }

    @Test
    public void testIncr()
    {
        System.out.println(redisService.increase("orderNo", 2));
    }

    @Test
    public void testM()
    {
        System.out.println(redisService.lock("12232", "121", 5000));
        System.out.println(redisService.getLock("12232"));
        System.out.println(redisService.unlock("12232", "121"));
    }
}

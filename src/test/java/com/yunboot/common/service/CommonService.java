package com.yunboot.common.service;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yunboot.common.BaseTests;
import com.yunboot.common.config.RedisConfig;
import com.yunboot.common.config.YunBootJedisPoolConfig;
import com.yunboot.common.config.YunBootRedisConnectionFactory;
import com.yunboot.common.config.YunBootRedisSentinelConfiguration;
import com.yunboot.common.config.YunBootRedisTemplate;
import com.yunboot.common.enums.CrmServiceEnum;
import com.yunboot.common.exception.CommonException;

import lombok.extern.java.Log;

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
        YunBootRedisConnectionFactory connectionFactory = redisConfig
                .bulidYunBootRedisConnectionFactory(sentinelConfiguration, jedisPoolConfig);
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
}

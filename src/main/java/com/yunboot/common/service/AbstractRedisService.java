package com.yunboot.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

import com.yunboot.common.config.YunBootRedisTemplate;

public abstract class AbstractRedisService
{

    @Autowired
    public YunBootRedisTemplate redisTemplate;

    public ValueOperations<String, Object> getValueOps()
    {
        return redisTemplate.opsForValue();
    }

    public ListOperations<String, Object> getListOps()
    {
        return redisTemplate.opsForList();
    }

    public SetOperations<String, Object> getSetOps()
    {
        return redisTemplate.opsForSet();
    }

    public HashOperations<String, Object, Object> getHashOps()
    {
        return redisTemplate.opsForHash();
    }

    public ZSetOperations<String, Object> getZsetOps()
    {
        return redisTemplate.opsForZSet();
    }

    public GeoOperations<String, Object> getGeoOps()
    {
        return redisTemplate.opsForGeo();
    }
}

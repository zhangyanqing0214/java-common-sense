package com.yunboot.common.service;

import com.yunboot.common.config.YunBootRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

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

package com.yunboot.common.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class YunBootRedisTemplate extends RedisTemplate<String, Object>
{

    public YunBootRedisTemplate(YunBootRedisConnectionFactory connectionFactory)
    {
        super.setConnectionFactory(connectionFactory);
        super.setKeySerializer(new StringRedisSerializer());
        super.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        super.setHashKeySerializer(new StringRedisSerializer());
        super.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
    }
}

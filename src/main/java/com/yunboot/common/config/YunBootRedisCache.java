package com.yunboot.common.config;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@EnableCaching
public class YunBootRedisCache extends CachingConfigurerSupport
{

    /**
     * [简要描述]:主键生成策略
     * [详细描述]:<br/>
     * 
     * @author yqzhang
     * @return
     * @exception
     *                @see
     *                org.springframework.cache.annotation.CachingConfigurerSupport#keyGenerator()
     */
    @Override
    public KeyGenerator keyGenerator()
    {
        return new KeyGenerator()
        {
            @Override
            public Object generate(Object target, Method method, Object... params)
            {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object object : params)
                {
                    sb.append(object.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public CacheManager cacheManager(YunBootRedisTemplate redisTemplate)
    {
        return new RedisCacheManager(redisTemplate);
    }

}

package com.yunboot.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig
{

    @Value("${repository.redis.master.name:NONE}")
    private String masterName = "master";
    @Value("${repository.redis.master.port:26379}")
    private int port = 26389;
    @Value("${repository.redis.master.host:NONE}")
    private String host = "192.168.206.200";
    @Value("${repository.redis.sentinel.list:NONE}")
    private String sentinelListString = "192.168.206.200:26389";
    @Value("${repository.redis.sentinel.password}")
    private String password = "123456";
    @Value("${repository.redis.master.databases}")
    private int databases = 0;
    @Value("${repository.redis.host.name:NONE}")
    private String hostName;
    @Value("${repository.redis.host.port:6379}")
    private String hostPort;

    @Bean
    public YunBootJedisPoolConfig buildYunBootJedisPoolConfig()
    {
        return new YunBootJedisPoolConfig();
    }

    /**
     * [简要描述]:哨兵配置
     * 
     * @author yqzhang
     * @return
     */
    @Bean
    public YunBootRedisSentinelConfiguration bulidYunBootRedisSentinelConfiguration()
    {
        if (masterName.equals("NONE"))
        {
            return null;
        }
        YunBootRedisSentinelConfiguration redisSentinelConfiguration = new YunBootRedisSentinelConfiguration(masterName,
                host, port);
        redisSentinelConfiguration.setSentinelListString(sentinelListString);
        return redisSentinelConfiguration;
    }

    @Bean
    public YunBootRedisConnectionFactory bulidYunBootRedisConnectionFactory(
            YunBootRedisSentinelConfiguration sentinelConfiguration, YunBootJedisPoolConfig jedisPoolConfig)
    {
        YunBootRedisConnectionFactory factory = null;
        if (sentinelConfiguration != null)
        {
            // redis工厂配置-sentinel模式
            factory = new YunBootRedisConnectionFactory(sentinelConfiguration, jedisPoolConfig);
        }
        else
        {
            // redis工厂配置-单节点模式
            factory = new YunBootRedisConnectionFactory(jedisPoolConfig, hostName, port);
        }
        factory.setPassword(password);
        factory.setDatabase(databases);
        return factory;
    }

    @Bean
    public YunBootRedisTemplate bulidYunBootRedisTemplate(YunBootRedisConnectionFactory connectionFactory)
    {
        return new YunBootRedisTemplate(connectionFactory);
    }
}

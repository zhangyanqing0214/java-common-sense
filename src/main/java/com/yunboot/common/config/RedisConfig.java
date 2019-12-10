package com.yunboot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig
{
    private String masterName;
    private int port;
    private String host;
    private String sentinelListString;
    private String password = "jl!@12";
    private int databases = 9;
    private String hostName = "192.168.206.204";
    private int hostPort = 6789;

    @Bean
    public YunBootJedisPoolConfig buildYunBootJedisPoolConfig()
    {
        return new YunBootJedisPoolConfig();
    }

    /**
     * [简要描述]:哨兵配置
     *
     * @return
     */
    @Bean
    public YunBootRedisSentinelConfiguration bulidYunBootRedisSentinelConfiguration()
    {
        if (masterName == null || masterName.equals("NONE"))
        {
            return null;
        }
        YunBootRedisSentinelConfiguration redisSentinelConfiguration = new YunBootRedisSentinelConfiguration(masterName, host, port);
        redisSentinelConfiguration.setSentinelListString(sentinelListString);
        return redisSentinelConfiguration;
    }

    @Bean
    public YunBootRedisConnectionFactory bulidYunBootRedisConnectionFactory(YunBootRedisSentinelConfiguration sentinelConfiguration, YunBootJedisPoolConfig jedisPoolConfig)
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
            factory = new YunBootRedisConnectionFactory(jedisPoolConfig, hostName, hostPort);
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

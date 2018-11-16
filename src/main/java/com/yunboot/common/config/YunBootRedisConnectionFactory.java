package com.yunboot.common.config;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class YunBootRedisConnectionFactory extends JedisConnectionFactory
{

    public YunBootRedisConnectionFactory(YunBootRedisSentinelConfiguration sentinelConfiguration,
            YunBootJedisPoolConfig jedisPoolConfig)
    {
        super(sentinelConfiguration, jedisPoolConfig);
    }

    // 单节点配置
    public YunBootRedisConnectionFactory(YunBootJedisPoolConfig jedisPoolConfig, String hostName, int port)
    {
        this.setPoolConfig(jedisPoolConfig);
        this.setHostName(hostName);
        this.setPort(port);
    }

    @Override
    public void setPassword(String password)
    {
        super.setPassword(password);
    }

}

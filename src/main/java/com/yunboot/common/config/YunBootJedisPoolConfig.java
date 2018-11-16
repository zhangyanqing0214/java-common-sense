package com.yunboot.common.config;

import lombok.extern.java.Log;
import redis.clients.jedis.JedisPoolConfig;

@Log
public class YunBootJedisPoolConfig extends JedisPoolConfig
{

    public YunBootJedisPoolConfig()
    {
        init();
    }

    public void init()
    {

        log.info("配置初始化。。。。。。");
    }
}

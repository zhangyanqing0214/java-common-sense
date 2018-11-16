package com.yunboot.common.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.util.StringUtils;

public class YunBootRedisSentinelConfiguration extends RedisSentinelConfiguration
{

    String sentinelListString;

    public YunBootRedisSentinelConfiguration(String masterName, String host, int port)
    {
        init(masterName, host, port);
    }

    private void init(String masterName, String host, int port)
    {

        RedisNode node = new RedisNode(host, port);
        node.setName(masterName);
        super.setMaster(node);
    }

    public String getSentinelListString()
    {
        return sentinelListString;
    }

    public void setSentinelListString(String sentinelListString)
    {
        this.sentinelListString = sentinelListString;
        if (StringUtils.isEmpty(sentinelListString))
        {
            throw new RuntimeException("哨兵列表不能为空");
        }
        String[] sentinels = sentinelListString.split(",");
        Set<RedisNode> nodes = new HashSet<>();
        for (String node : sentinels)
        {
            String[] address = node.split(":");
            nodes.add(new RedisNode(address[0], Integer.valueOf(address[1])));
        }

        setSentinels(nodes);
    }

}

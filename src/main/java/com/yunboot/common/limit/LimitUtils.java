package com.yunboot.common.limit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 *
 * @author yqzhang
 * @version 1.0, 2020/4/9 9:13
 * @since JDK 1.8
 */
public class LimitUtils
{

    public static LoadingCache<Long, AtomicInteger> getLimitCounter(int expireSeconds)
    {
        return CacheBuilder.newBuilder().expireAfterWrite(expireSeconds, TimeUnit.SECONDS).build(new CacheLoader<Long, AtomicInteger>()
        {
            @Override
            public AtomicInteger load(Long aLong)
            {
                return new AtomicInteger(0);
            }
        });
    }

    public static void main(String[] args) throws ExecutionException
    {
        long limit = 100000;
        LoadingCache<Long, AtomicInteger> counter = LimitUtils.getLimitCounter(1);
        while (true)
        {
            long currentSeconds = Instant.now().getEpochSecond();
            if (counter.get(currentSeconds).incrementAndGet() > limit)
            {
                System.out.println("限流了！");
                continue;
            }
            else
            {
                System.out.println("正常！");
            }
        }
    }
}

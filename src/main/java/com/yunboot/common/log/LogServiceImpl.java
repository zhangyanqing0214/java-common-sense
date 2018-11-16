package com.yunboot.common.log;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LogServiceImpl implements LogService
{
    @Override
    public void debug(String message)
    {
        log.debug(message);
    }

    @Override
    public void info(String message)
    {
        log.info(message);
    }

    @Override
    public void warn(String message)
    {
        log.warn(message);
    }

    @Override
    public void error(String message, Throwable e)
    {
        log.error(message, e);
    }

}

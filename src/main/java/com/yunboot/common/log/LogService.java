package com.yunboot.common.log;

public interface LogService
{

    public void debug(String message);

    public void info(String message);

    public void warn(String message);

    public void error(String message, Throwable e);
}

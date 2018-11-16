package com.yunboot.common.enums;

public interface ServiceException
{

    /**
     * 获取异常的状态码
     */
    public Integer getCode();

    /**
     * 获取异常的提示信息
     */
    public String getMessage();
}

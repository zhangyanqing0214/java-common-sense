package com.yunboot.common.enums;

public enum CrmServiceEnum implements ServiceException
{

    ID_IS_NULL(100, "业务异常1");

    private CrmServiceEnum(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    Integer code;

    String message;

    @Override
    public Integer getCode()
    {
        return code;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}

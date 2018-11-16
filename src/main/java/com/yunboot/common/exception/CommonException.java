package com.yunboot.common.exception;

import com.yunboot.common.enums.ServiceException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommonException extends RuntimeException
{

    /**
     * [简要描述]:
     * 
     * @author yqzhang
     */
    private static final long serialVersionUID = -2550309062701528397L;

    private Integer code;

    private String errorMessage;

    public CommonException(Integer code, String errorMessage)
    {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public CommonException(ServiceException e)
    {
        super(e.getMessage());
        this.code = e.getCode();
        this.errorMessage = e.getMessage();
    }

}

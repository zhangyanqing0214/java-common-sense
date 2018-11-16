package com.yunboot.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * [简要描述]:全局异常捕捉类
 *
 * @author yqzhang
 * @version 1.0, 2018年11月15日
 * @since JDK1.8
 */
@ControllerAdvice
public class BaseControllerAdvice
{

    /**
     * [简要描述]:自定义异常捕捉方法
     * 
     * @author yqzhang
     * @param e
     * @return
     */
    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public ResponseJson serviceException(CommonException e)
    {
        return ResponseJson.error(e.getCode(), e.getErrorMessage().toString());
    }

    /**
     * [简要描述]:全局异常捕捉方法
     * 
     * @author yqzhang
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseJson errorHandler(Exception e)
    {
        if (e instanceof NoHandlerFoundException)
        {
            return ResponseJson.error(404, e.getMessage());
        }
        else
        {
            return ResponseJson.error(500, e.getMessage());
        }
    }
}

package com.yunboot.common.exception;

import lombok.Data;

@Data
public class ResponseJson
{
    public static final String REQUEST_OK = "请求成功";

    public static final String REQUEST_ERROR = "网络异常";

    public static final Integer REQUEST_200 = 200;

    public static final Integer REQUEST_500 = 500;

    Integer code;

    Boolean success;

    String message;

    Object data;

    public ResponseJson(Integer code, Boolean success, String message)
    {
        super();
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public ResponseJson(Integer code, Boolean success, String message, Object data)
    {
        super();
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static ResponseJson success()
    {
        return new ResponseJson(REQUEST_200, true, REQUEST_OK);
    }

    public static ResponseJson success(Object object)
    {
        return new ResponseJson(REQUEST_200, true, REQUEST_OK, object);
    }

    public static ResponseJson success(Integer code, String message, Object data)
    {
        return new ResponseJson(code, true, message, data);
    }

    public static ResponseJson error()
    {
        return new ResponseJson(REQUEST_500, false, REQUEST_ERROR);
    }

    public static ResponseJson error401()
    {
        return new ResponseJson(401, false, "没有登录");
    }

    public static ResponseJson error403()
    {

        return new ResponseJson(403, false, "没有权限");
    }

    public static ResponseJson error(Integer code, String message)
    {

        return new ResponseJson(code, false, message);
    }

    public static ResponseJson error(Integer code, String message, Object object)
    {
        return new ResponseJson(code, false, message, object);
    }
}

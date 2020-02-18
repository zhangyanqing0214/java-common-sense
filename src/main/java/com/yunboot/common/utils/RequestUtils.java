package com.yunboot.common.utils;

import cn.hutool.json.JSONUtil;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

public class RequestUtils {
    /**
     * @param response : 响应请求
     * @param object:  object
     * @return void
     * @Title: out
     * @Description: response输出JSON数据
     **/
    public static void out(ServletResponse response, Object object) {
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(JSONUtil.toJsonStr(object));
        } catch (Exception e) {
            System.out.println("输出JSON报错!" + e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}

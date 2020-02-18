package com.yunboot.common.limit.interceptor;


import com.yunboot.common.exception.CommonException;
import com.yunboot.common.exception.ResponseJson;
import com.yunboot.common.limit.annotation.AccessLimit;
import com.yunboot.common.service.RedisService;
import com.yunboot.common.utils.IPUtils;
import com.yunboot.common.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AccessLimitInterceptor.class);
    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // Handler 是否为 HandlerMethod 实例
            if (handler instanceof HandlerMethod) {
                // 强转
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                // 获取方法
                Method method = handlerMethod.getMethod();
                // 是否有AccessLimit注解
                if (!method.isAnnotationPresent(AccessLimit.class)) {
                    return true;
                }
                // 获取注解内容信息
                AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
                if (accessLimit == null) {
                    return true;
                }
                int times = accessLimit.times();//请求次数
                int second = accessLimit.second();//请求时间范围
                //根据 IP + API 限流
                String key = IPUtils.getIpAddr(request) + request.getRequestURI();
                //根据key获取已请求次数
                Integer maxTimes = (Integer) redisService.get(key);
                if (maxTimes == null) {
                    //set时一定要加过期时间
                    redisService.set(key, 1, second);
                } else if (maxTimes < times) {
                    redisService.set(key, maxTimes + 1, second);
                } else {
                    // 30405 API_REQUEST_TOO_MUCH  请求过于频繁
                    RequestUtils.out(response, ResponseJson.error(ResponseJson.REQUEST_500,"请求过于频繁"));
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("API请求限流拦截异常,请检查Redis是否开启!", e);
            throw new CommonException(500, "系统内部错误");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

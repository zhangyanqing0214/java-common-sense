package com.yunboot.common.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.yunboot.common.annotation.Log;
import com.yunboot.common.exception.CommonException;
import com.yunboot.common.log.LogInfo;
import com.yunboot.common.log.LogService;
import com.yunboot.common.utils.HttpContextUtils;
import com.yunboot.common.utils.IPUtils;

@Aspect
@Component
public class LogAspect
{

    @Autowired
    LogService logService;

    @Pointcut("@annotation(com.yunboot.common.annotation.Log)")
    public void logPointCut()
    {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        Object result = null;
        Throwable ex = null;
        long requestTime = System.currentTimeMillis();
        try
        {
            result = point.proceed();
        }
        catch (Throwable e)
        {
            logService.error(e.getMessage(), e);
            ex = e;
            throw e;
        }
        finally
        {
            long responseTime = System.currentTimeMillis();
            saveLog(point, requestTime, responseTime, result, ex);
        }
        return result;

    }

    private void saveLog(ProceedingJoinPoint point, long requestTime, long responseTime, Object result, Throwable ex)
    {
        MethodSignature sign = (MethodSignature) point.getSignature();

        Method method = sign.getMethod();

        Log log = method.getAnnotation(Log.class);

        LogInfo infos = new LogInfo();
        if (log != null)
        {
            infos.setRemark(log.value());
        }
        Object[] args = point.getArgs();
        String className = point.getTarget().getClass().getName();
        String methodName = sign.getName();
        infos.setParams(new Gson().toJson(args[0]));
        infos.setClsName(className);
        infos.setMethodName(className + "." + methodName + "()");
        infos.setResult(new Gson().toJson(result));
        infos.setCostTime(responseTime - requestTime);
        infos.setRequestTime(getFormatDate(requestTime));
        infos.setResponseTime(getFormatDate(responseTime));
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        infos.setClientHost(request.getRemoteHost());
        infos.setClientIp(IPUtils.getIpAddr(request));
        infos.setClientPort(request.getRemotePort());
        infos.setServerHost(request.getLocalName());
        infos.setServerIp(request.getLocalAddr());
        infos.setServerPort(request.getLocalPort());
        infos.setRequestUri(request.getRequestURI());
        if (ex != null && ex instanceof CommonException)
        {
            CommonException commonException = (CommonException) ex;
            infos.setErrorCode(commonException.getCode());
            infos.setErrorMsg(commonException.getErrorMessage());
            infos.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        else if (ex instanceof Exception)
        {
            Exception exception = (Exception) ex;
            infos.setErrorCode(exception.hashCode());
            infos.setErrorMsg(exception.getMessage());
            infos.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        logService.info(new Gson().toJson(infos));
    }

    private String getFormatDate(long now)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(now);
        Date date = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}

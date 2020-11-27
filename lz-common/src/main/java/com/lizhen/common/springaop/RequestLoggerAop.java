package com.lizhen.common.springaop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yangwei on 2019/6/15.
 */
//@Aspect
//@Component
public class RequestLoggerAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggerAop.class);


    @Pointcut("execution(* com.lizhen..*Controller.*(..))")
    private void pointCut() {
    }

    @Pointcut("execution(* com.lizhen..*service..*(..))")
    public void service() {
    }

//    @Before(value = "pointCut()")
//    public void before(JoinPoint joinPoint){
//
//    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String uuid = MDC.get("traceId");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LOGGER.info("\n\t请求标识: {}\n\t请求IP: {}\n\t请求路径: {}\n\t请求方式: {}\n\t方法描述: {}\n\t请求参数: {}",
                uuid, request.getRemoteAddr(), request.getRequestURL(), request.getMethod(),
                getMethodInfo(point), JSONObject.toJSONString(request.getParameterMap()));

        long startTime = System.currentTimeMillis();
        Object[] args = point.getArgs();
        Object retVal = point.proceed(args);
        long endTime = System.currentTimeMillis();

        LOGGER.info("\n\t请求标识: {} \n\t执行时间: {} ms \n\t返回值: {}", uuid, endTime - startTime, JSONObject.toJSONString(retVal));
        return retVal;
    }

    @Before("service()")
    public void service(JoinPoint point) {
        LOGGER.info("\n\tservice method: {}", getMethodInfo(point));
    }

    private String getMethodInfo(JoinPoint point) {
        ConcurrentHashMap<String, Object> info = new ConcurrentHashMap<>(3);
        info.put("class", point.getTarget().getClass().getSimpleName());
        info.put("method", point.getSignature().getName());
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        ConcurrentHashMap<String, String> args = null;
        if (Objects.nonNull(parameterNames)) {
            args = new ConcurrentHashMap<>(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                String value = point.getArgs()[i] != null ? point.getArgs()[i].toString() : "null";
                args.put(parameterNames[i], value);
            }
            info.put("args", args);
        }

        return JSONObject.toJSONString(info);
    }


}

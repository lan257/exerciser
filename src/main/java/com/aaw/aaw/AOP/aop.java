package com.aaw.aaw.AOP;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class aop {
    @Around("execution(* com.aaw.aaw.A_represents.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin, end;
        begin = System.nanoTime();
        String methodName =joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        String methodParams= Arrays.toString(args);
        Object result = joinPoint.proceed();
        end = System.nanoTime();
        double elapsedTime = (end - begin) / 1000000.0; // 转换为毫秒
//        String returnValue= JSONObject.toJSONString(result);
        log.info("执行任务："+methodName);
        log.info("执行时间：" + begin);
        log.info("执行耗时: " + elapsedTime + "ms");
        log.info("请求数据：" + methodParams);
        log.info("响应数据: " + result);
        return result;
    }
}
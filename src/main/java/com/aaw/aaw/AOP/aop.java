package com.aaw.aaw.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class aop {
    @Around("execution(* com.aaw.aaw.A_represents.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin, end;
        begin = System.nanoTime();
        Object result = joinPoint.proceed();
        end = System.nanoTime();
        double elapsedTime = (end - begin) / 1000000.0; // 转换为毫秒
        log.info("执行耗时: " + elapsedTime + "ms");
        log.info("响应数据: " + result);
        return result;
    }
}
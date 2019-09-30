package com.shixun.ihome.hourwork.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Record {
    @Pointcut("execution(public * com.shixun.ihome.hourwork.service.*.*(..))")
    public void fuck(){

    }

    @Around("fuck()")
    public Object doAfterReturning(ProceedingJoinPoint point) throws Throwable {
        System.out.println("执行前");
        Object[] objects = point.getArgs();
        for (Object o: objects
             ) {
            System.out.println(o);
        }
        Object result = null;
        try{
            result = point.proceed();
            System.out.println("执行后");
        }catch (Exception e){
            System.out.println("发送错误");
            e.printStackTrace();
        }

        return result;
    }
}

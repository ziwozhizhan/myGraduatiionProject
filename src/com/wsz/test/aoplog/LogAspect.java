package com.wsz.test.aoplog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * test日志统一管理
 * @author wanshenzhen  2017/5/22.
 */
@Component
@Aspect
public class LogAspect {
    /**
     * Pointcut 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(* com.wsz.test.aoplog.*.*(..))")
    public void aspectjMethod(){}

    @Before("aspectjMethod()")
    public void before(JoinPoint joinPoint){
        System.out.println("切面成功 开始 ===============");
    }

    @AfterThrowing(value = "aspectjMethod()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Throwable e){
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String targetName = joinPoint.getTarget().getClass().getName(); //类名
        String methodName = joinPoint.getSignature().getName(); //方法名
        String errorName = e.getClass().toString();
        String errorMsg = e.getMessage();
        String[] params = {targetName,methodName,errorName,errorMsg};

        logger.error("-------------------------- start ----------------------");
        logger.error("类：{},方法：{},异常名称：{},异常信息：{}",params);
        logger.error("-------------------------- end ----------------------");
    }
}

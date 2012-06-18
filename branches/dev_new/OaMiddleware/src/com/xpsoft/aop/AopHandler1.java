package com.xpsoft.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class AopHandler1  {
    public void before(JoinPoint joinPoint) {
       // 获取方法名
       System.out.println("-----方法:" + joinPoint.getSignature().getName());
       // 获取参数信息
       Object[] args = joinPoint.getArgs();
       for (int i = 0; i < args.length; i++) {
           System.out.println("-------参数" + args[i]);
       }
 
    }
 
    public Object around(ProceedingJoinPoint p) throws Throwable{
       System.out.println("-----------comeinto around");
       Object retVal = p.proceed();  
 
       System.out.println("-----------leave around");
       return retVal;
    }
 
    public void after(JoinPoint joinPoint) {
       System.out.println("-----------inafter");
    }
   
    public void doThrowing(JoinPoint jp) { 
        System.out.println("--------in exception");
    } 
    public void doReturning(JoinPoint jp) { 
        System.out.println("--------in return");
    }
}
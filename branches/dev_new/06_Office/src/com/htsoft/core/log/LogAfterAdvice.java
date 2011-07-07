/*    */ package com.htsoft.core.log;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Method;
/*    */ import org.aopalliance.aop.Advice;
/*    */ 
/*    */ public class LogAfterAdvice
/*    */   implements Advice
/*    */ {
/*    */   public void afterReturning(Object returnObj, Method method, Object[] args, Object targetObj)
/*    */     throws Throwable
/*    */   {
/* 18 */     if (method.getName().equals("saveLog")) return;
/*    */ 
/* 20 */     System.out.println("save log is ------------>:" + method.getName());
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.log.LogAfterAdvice
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.core.log;
/*    */ 
/*    */ import com.xpsoft.core.util.ContextUtil;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.model.system.SystemLog;
/*    */ import com.xpsoft.oa.service.system.SystemLogService;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Date;
/*    */ import javax.annotation.Resource;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.aspectj.lang.ProceedingJoinPoint;
/*    */ import org.aspectj.lang.Signature;
/*    */ 
/*    */ public class LogAspect
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private SystemLogService systemLogService;
/* 23 */   private Log logger = LogFactory.getLog(LogAspect.class);
/*    */ 
/*    */   public Object doSystemLog(ProceedingJoinPoint point) throws Throwable
/*    */   {
/* 27 */     String methodName = point.getSignature().getName();
/*    */ 
/* 30 */     if (StringUtils.isNotEmpty(methodName))
/*    */     {
/* 32 */       if ((!methodName.startsWith("set")) && (!methodName.startsWith("get")))
/*    */       {
/* 34 */         Class targetClass = point.getTarget().getClass();
/* 35 */         Method method = targetClass.getMethod(methodName, new Class[0]);
/*    */ 
/* 37 */         if (method != null)
/*    */         {
/* 39 */           boolean hasAnnotation = method.isAnnotationPresent(Action.class);
/*    */ 
/* 41 */           if (hasAnnotation) {
/* 42 */             Action annotation = (Action)method.getAnnotation(Action.class);
/*    */ 
/* 44 */             String methodDescp = annotation.description();
/* 45 */             if (this.logger.isDebugEnabled()) {
/* 46 */               this.logger.debug("Action method:" + method.getName() + " Description:" + methodDescp);
/*    */             }
/*    */ 
/* 49 */             AppUser appUser = ContextUtil.getCurrentUser();
/* 50 */             if (appUser != null) {
/*    */               try {
/* 52 */                 SystemLog sysLog = new SystemLog();
/*    */ 
/* 54 */                 sysLog.setCreatetime(new Date());
/* 55 */                 sysLog.setUserId(appUser.getUserId());
/* 56 */                 sysLog.setUsername(appUser.getFullname());
/* 57 */                 sysLog.setExeOperation(methodDescp);
/*    */ 
/* 59 */                 this.systemLogService.save(sysLog);
/*    */               } catch (Exception ex) {
/* 61 */                 this.logger.error(ex.getMessage());
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 70 */     return point.proceed();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.log.LogAspect
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.core.util;
/*    */ 
/*    */ import com.htsoft.oa.model.system.AppUser;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.springframework.security.Authentication;
/*    */ import org.springframework.security.context.SecurityContext;
/*    */ import org.springframework.security.context.SecurityContextHolder;
/*    */ 
/*    */ public class ContextUtil
/*    */ {
/* 24 */   private static final Log logger = LogFactory.getLog(ContextUtil.class);
/*    */ 
/*    */   public static AppUser getCurrentUser()
/*    */   {
/* 31 */     SecurityContext securityContext = SecurityContextHolder.getContext();
/* 32 */     if (securityContext != null) {
/* 33 */       Authentication auth = securityContext.getAuthentication();
/* 34 */       if (auth != null) {
/* 35 */         Object principal = auth.getPrincipal();
/* 36 */         if ((principal instanceof AppUser))
/* 37 */           return (AppUser)principal;
/*    */       }
/*    */       else {
/* 40 */         logger.warn("WARN: securityContext cannot be lookuped using SecurityContextHolder.");
/*    */       }
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   public static Long getCurrentUserId() {
/* 47 */     AppUser curUser = getCurrentUser();
/* 48 */     if (curUser != null) return curUser.getUserId();
/* 49 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.util.ContextUtil
 * JD-Core Version:    0.6.0
 */
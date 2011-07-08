/*    */ package com.xpsoft.core.web.listener;
/*    */ 
/*    */ import com.xpsoft.core.util.AppUtil;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
/*    */ 
/*    */ public class UserSessionListener
/*    */   implements HttpSessionListener
/*    */ {
/*    */   public void sessionCreated(HttpSessionEvent arg0)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void sessionDestroyed(HttpSessionEvent event)
/*    */   {
/* 36 */     String sessionId = event.getSession().getId();
/* 37 */     AppUtil.removeOnlineUser(sessionId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.web.listener.UserSessionListener
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.core.jbpm.pv;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class UserInfo
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Long userId;
/*    */   private String username;
/*    */   private String fullname;
/*    */ 
/*    */   public Long getUserId()
/*    */   {
/* 36 */     return this.userId;
/*    */   }
/*    */   public void setUserId(Long userId) {
/* 39 */     this.userId = userId;
/*    */   }
/*    */   public String getUsername() {
/* 42 */     return this.username;
/*    */   }
/*    */   public void setUsername(String username) {
/* 45 */     this.username = username;
/*    */   }
/*    */   public String getFullname() {
/* 48 */     return this.fullname;
/*    */   }
/*    */   public void setFullname(String fullname) {
/* 51 */     this.fullname = fullname;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.jbpm.pv.UserInfo
 * JD-Core Version:    0.6.0
 */
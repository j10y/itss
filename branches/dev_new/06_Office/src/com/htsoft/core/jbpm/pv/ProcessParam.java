/*    */ package com.htsoft.core.jbpm.pv;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ProcessParam
/*    */   implements Serializable
/*    */ {
/*    */   public static final String PARAM_NAME = "_pp";
/*    */   private static final long serialVersionUID = 1L;
/*    */   private UserInfo user;
/*    */   private Date createtime;
/*    */   private String processName;
/*    */   private String activityName;
/*    */   private ProcessForm processForm;
/*    */ 
/*    */   public UserInfo getUser()
/*    */   {
/* 52 */     return this.user;
/*    */   }
/*    */   public void setUser(UserInfo user) {
/* 55 */     this.user = user;
/*    */   }
/*    */ 
/*    */   public Date getCreatetime() {
/* 59 */     return this.createtime;
/*    */   }
/*    */   public void setCreatetime(Date createtime) {
/* 62 */     this.createtime = createtime;
/*    */   }
/*    */ 
/*    */   public String getProcessName() {
/* 66 */     return this.processName;
/*    */   }
/*    */ 
/*    */   public void setProcessName(String processName) {
/* 70 */     this.processName = processName;
/*    */   }
/*    */ 
/*    */   public ProcessForm getProcessForm() {
/* 74 */     return this.processForm;
/*    */   }
/*    */ 
/*    */   public void setProcessForm(ProcessForm processForm) {
/* 78 */     this.processForm = processForm;
/*    */   }
/*    */ 
/*    */   public String getActivityName() {
/* 82 */     return this.activityName;
/*    */   }
/*    */ 
/*    */   public void setActivityName(String activityName) {
/* 86 */     this.activityName = activityName;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.pv.ProcessParam
 * JD-Core Version:    0.6.0
 */
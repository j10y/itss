/*    */ package com.htsoft.core.jbpm.pv;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.LinkedList;
/*    */ 
/*    */ public class ProcessForm
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String activityName;
/* 26 */   private LinkedList<ParamInfo> params = new LinkedList();
/*    */ 
/*    */   public String getActivityName()
/*    */   {
/* 33 */     return this.activityName;
/*    */   }
/*    */ 
/*    */   public void setActivityName(String activityName) {
/* 37 */     this.activityName = activityName;
/*    */   }
/*    */ 
/*    */   public LinkedList<ParamInfo> getParams() {
/* 41 */     return this.params;
/*    */   }
/*    */ 
/*    */   public void setParams(LinkedList<ParamInfo> params) {
/* 45 */     this.params = params;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.pv.ProcessForm
 * JD-Core Version:    0.6.0
 */
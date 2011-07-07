/*    */ package com.htsoft.oa.model.task;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ 
/*    */ public class PlanInfo
/*    */ {
/*    */   private Long planId;
/*    */   private String content;
/*    */   private String durationTime;
/*    */   private String status;
/*    */   private String urgent;
/*    */ 
/*    */   public Long getPlanId()
/*    */   {
/* 23 */     return this.planId;
/*    */   }
/*    */   public void setPlanId(Long planId) {
/* 26 */     this.planId = planId;
/*    */   }
/*    */   public String getContent() {
/* 29 */     return this.content;
/*    */   }
/*    */   public void setContent(String content) {
/* 32 */     this.content = content;
/*    */   }
/*    */   public String getDurationTime() {
/* 35 */     return this.durationTime;
/*    */   }
/*    */   public void setDurationTime(String durationTime) {
/* 38 */     this.durationTime = durationTime;
/*    */   }
/*    */   public String getStatus() {
/* 41 */     return this.status;
/*    */   }
/*    */   public void setStatus(String status) {
/* 44 */     this.status = status;
/*    */   }
/*    */   public String getUrgent() {
/* 47 */     return this.urgent;
/*    */   }
/*    */   public void setUrgent(String urgent) {
/* 50 */     this.urgent = urgent;
/*    */   }
/*    */ 
/*    */   public PlanInfo() {
/*    */   }
/*    */ 
/*    */   public PlanInfo(CalendarPlan cp) {
/* 57 */     this.planId = cp.getPlanId();
/* 58 */     this.content = cp.getContent();
/*    */ 
/* 60 */     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
/* 61 */     this.durationTime = (sdf.format(cp.getStartTime()) + "--" + sdf.format(cp.getEndTime()));
/* 62 */     this.urgent = cp.getUrgentName();
/* 63 */     this.status = cp.getStatusName();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.task.PlanInfo
 * JD-Core Version:    0.6.0
 */
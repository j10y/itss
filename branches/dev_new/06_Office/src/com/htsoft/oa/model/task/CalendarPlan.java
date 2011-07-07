/*     */ package com.htsoft.oa.model.task;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class CalendarPlan extends BaseModel
/*     */ {
/*  23 */   public static final Short STATUS_UNFINISHED = Short.valueOf((short) 0);
/*     */ 
/*  25 */   public static final Short STATUS_FINISHED = Short.valueOf((short) 1);
/*     */ 
/*  29 */   public static final Short URGENT_COMMON = Short.valueOf((short) 0);
/*     */ 
/*  31 */   public static final Short URGENT_IMPORTANT = Short.valueOf((short) 1);
/*     */ 
/*  33 */   public static final Short URGENT_INST = Short.valueOf((short) 2);
/*     */ 
/*     */   @Expose
/*     */   protected Long planId;
/*     */ 
/*     */   @Expose
/*     */   protected Date startTime;
/*     */ 
/*     */   @Expose
/*     */   protected Date endTime;
/*     */ 
/*     */   @Expose
/*     */   protected Short urgent;
/*     */ 
/*     */   @Expose
/*     */   protected String content;
/*     */ 
/*     */   @Expose
/*     */   protected Short status;
/*     */ 
/*     */   @Expose
/*     */   protected String fullname;
/*     */ 
/*     */   @Expose
/*     */   protected Long assignerId;
/*     */ 
/*     */   @Expose
/*     */   protected String assignerName;
/*     */ 
/*     */   @Expose
/*     */   protected String feedback;
/*     */ 
/*     */   @Expose
/*     */   protected Short showStyle;
/*     */ 
/*     */   @Expose
/*     */   protected Short taskType;
/*     */ 
/*     */   @Expose
/*     */   protected Long userId;
/*     */ 
/*  37 */   public String getColor() { if (STATUS_FINISHED.equals(this.status)) {
/*  38 */       return "#D5E4BD";
/*     */     }
/*  40 */     if (URGENT_INST.equals(this.urgent))
/*  41 */       return "#94B98D";
/*  42 */     if (URGENT_IMPORTANT.equals(this.urgent)) {
/*  43 */       return "#FFBDB4";
/*     */     }
/*  45 */     return "#7799BF";
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  84 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long userId) {
/*  88 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */   public String getStatusName() {
/*  92 */     if (this.status.equals(STATUS_FINISHED)) {
/*  93 */       return "完成";
/*     */     }
/*  95 */     return "未完成";
/*     */   }
/*     */ 
/*     */   public String getUrgentName()
/*     */   {
/* 100 */     if (this.urgent.equals(URGENT_COMMON))
/* 101 */       return "一般";
/* 102 */     if (this.urgent.equals(URGENT_IMPORTANT)) {
/* 103 */       return "重要";
/*     */     }
/* 105 */     return "紧急";
/*     */   }
/*     */ 
/*     */   public CalendarPlan()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CalendarPlan(Long in_planId)
/*     */   {
/* 122 */     setPlanId(in_planId);
/*     */   }
/*     */ 
/*     */   public Long getPlanId()
/*     */   {
/* 140 */     return this.planId;
/*     */   }
/*     */ 
/*     */   public void setPlanId(Long aValue)
/*     */   {
/* 147 */     this.planId = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/* 155 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date aValue)
/*     */   {
/* 162 */     this.startTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 170 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date aValue)
/*     */   {
/* 177 */     this.endTime = aValue;
/*     */   }
/*     */ 
/*     */   public Short getUrgent()
/*     */   {
/* 188 */     return this.urgent;
/*     */   }
/*     */ 
/*     */   public void setUrgent(Short aValue)
/*     */   {
/* 196 */     this.urgent = aValue;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 204 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 212 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 222 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 230 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 259 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 266 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public Long getAssignerId()
/*     */   {
/* 274 */     return this.assignerId;
/*     */   }
/*     */ 
/*     */   public void setAssignerId(Long aValue)
/*     */   {
/* 282 */     this.assignerId = aValue;
/*     */   }
/*     */ 
/*     */   public String getAssignerName()
/*     */   {
/* 290 */     return this.assignerName;
/*     */   }
/*     */ 
/*     */   public void setAssignerName(String aValue)
/*     */   {
/* 297 */     this.assignerName = aValue;
/*     */   }
/*     */ 
/*     */   public String getFeedback()
/*     */   {
/* 305 */     return this.feedback;
/*     */   }
/*     */ 
/*     */   public void setFeedback(String aValue)
/*     */   {
/* 312 */     this.feedback = aValue;
/*     */   }
/*     */ 
/*     */   public Short getShowStyle()
/*     */   {
/* 322 */     return this.showStyle;
/*     */   }
/*     */ 
/*     */   public void setShowStyle(Short aValue)
/*     */   {
/* 330 */     this.showStyle = aValue;
/*     */   }
/*     */ 
/*     */   public Short getTaskType()
/*     */   {
/* 340 */     return this.taskType;
/*     */   }
/*     */ 
/*     */   public void setTaskType(Short aValue)
/*     */   {
/* 348 */     this.taskType = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 355 */     if (!(object instanceof CalendarPlan)) {
/* 356 */       return false;
/*     */     }
/* 358 */     CalendarPlan rhs = (CalendarPlan)object;
/* 359 */     return new EqualsBuilder()
/* 360 */       .append(this.planId, rhs.planId)
/* 361 */       .append(this.startTime, rhs.startTime)
/* 362 */       .append(this.endTime, rhs.endTime)
/* 363 */       .append(this.urgent, rhs.urgent)
/* 364 */       .append(this.content, rhs.content)
/* 365 */       .append(this.status, rhs.status)
/* 366 */       .append(this.fullname, rhs.fullname)
/* 367 */       .append(this.userId, rhs.userId)
/* 368 */       .append(this.assignerId, rhs.assignerId)
/* 369 */       .append(this.assignerName, rhs.assignerName)
/* 370 */       .append(this.feedback, rhs.feedback)
/* 371 */       .append(this.showStyle, rhs.showStyle)
/* 372 */       .append(this.taskType, rhs.taskType)
/* 373 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 380 */     return new HashCodeBuilder(-82280557, -700257973)
/* 381 */       .append(this.planId)
/* 382 */       .append(this.startTime)
/* 383 */       .append(this.endTime)
/* 384 */       .append(this.urgent)
/* 385 */       .append(this.content)
/* 386 */       .append(this.status)
/* 387 */       .append(this.fullname)
/* 388 */       .append(this.userId)
/* 389 */       .append(this.assignerId)
/* 390 */       .append(this.assignerName)
/* 391 */       .append(this.feedback)
/* 392 */       .append(this.showStyle)
/* 393 */       .append(this.taskType)
/* 394 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 401 */     return new ToStringBuilder(this)
/* 402 */       .append("planId", this.planId)
/* 403 */       .append("startTime", this.startTime)
/* 404 */       .append("endTime", this.endTime)
/* 405 */       .append("urgent", this.urgent)
/* 406 */       .append("content", this.content)
/* 407 */       .append("status", this.status)
/* 408 */       .append("fullname", this.fullname)
/* 409 */       .append("assignerId", this.assignerId)
/* 410 */       .append("userId", this.userId)
/* 411 */       .append("assignerName", this.assignerName)
/* 412 */       .append("feedback", this.feedback)
/* 413 */       .append("showStyle", this.showStyle)
/* 414 */       .append("taskType", this.taskType)
/* 415 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.task.CalendarPlan
 * JD-Core Version:    0.6.0
 */
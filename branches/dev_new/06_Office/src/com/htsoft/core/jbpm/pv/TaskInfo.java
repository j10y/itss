/*     */ package com.htsoft.core.jbpm.pv;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import org.jbpm.pvm.internal.model.ExecutionImpl;
/*     */ import org.jbpm.pvm.internal.task.TaskImpl;
/*     */ 
/*     */ public class TaskInfo
/*     */ {
/*     */   private String taskName;
/*     */   private String activityName;
/*     */   private String assignee;
/*     */   private Date createTime;
/*     */   private Date dueDate;
/*     */   private String executionId;
/*     */   private String pdId;
/*     */   private Long taskId;
/*  24 */   private Short isMultipleTask = 0;
/*     */ 
/*  27 */   private String candidateUsers = "";
/*     */ 
/*  29 */   private String candidateRoles = "";
/*     */ 
/*     */   public TaskInfo() {
/*     */   }
/*     */ 
/*     */   public TaskInfo(TaskImpl taskImpl) {
/*  35 */     this.taskName = taskImpl.getActivityName();
/*  36 */     this.activityName = taskImpl.getActivityName();
/*  37 */     this.assignee = taskImpl.getAssignee();
/*  38 */     this.dueDate = taskImpl.getDuedate();
/*  39 */     this.createTime = taskImpl.getCreateTime();
/*  40 */     if (taskImpl.getSuperTask() != null)
/*  41 */       taskImpl.getSuperTask().getProcessInstance().getId();
/*     */     else {
/*  43 */       this.pdId = taskImpl.getProcessInstance().getId();
/*     */     }
/*  45 */     this.executionId = taskImpl.getExecutionId();
/*  46 */     this.taskId = Long.valueOf(taskImpl.getDbid());
/*     */ 
/*  48 */     if (taskImpl.getParticipations().size() > 0)
/*  49 */       this.isMultipleTask = 1;
/*     */   }
/*     */ 
/*     */   public String getActivityName()
/*     */   {
/*  54 */     return this.activityName;
/*     */   }
/*     */ 
/*     */   public void setActivityName(String activityName) {
/*  58 */     this.activityName = activityName;
/*     */   }
/*     */ 
/*     */   public String getAssignee() {
/*  62 */     return this.assignee;
/*     */   }
/*     */ 
/*     */   public void setAssignee(String assignee) {
/*  66 */     this.assignee = assignee;
/*     */   }
/*     */ 
/*     */   public Date getCreateTime() {
/*  70 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(Date createTime) {
/*  74 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public Date getDueDate() {
/*  78 */     return this.dueDate;
/*     */   }
/*     */ 
/*     */   public void setDueDate(Date dueDate) {
/*  82 */     this.dueDate = dueDate;
/*     */   }
/*     */ 
/*     */   public String getExecutionId() {
/*  86 */     return this.executionId;
/*     */   }
/*     */ 
/*     */   public void setExecutionId(String executionId) {
/*  90 */     this.executionId = executionId;
/*     */   }
/*     */ 
/*     */   public String getCandidateUsers() {
/*  94 */     return this.candidateUsers;
/*     */   }
/*     */ 
/*     */   public void setCandidateUsers(String candidateUsers) {
/*  98 */     this.candidateUsers = candidateUsers;
/*     */   }
/*     */ 
/*     */   public String getCandidateRoles() {
/* 102 */     return this.candidateRoles;
/*     */   }
/*     */ 
/*     */   public void setCandidateRoles(String candidateRoles) {
/* 106 */     this.candidateRoles = candidateRoles;
/*     */   }
/*     */ 
/*     */   public Long getTaskId() {
/* 110 */     return this.taskId;
/*     */   }
/*     */ 
/*     */   public void setTaskId(Long taskId) {
/* 114 */     this.taskId = taskId;
/*     */   }
/*     */ 
/*     */   public Short getIsMultipleTask() {
/* 118 */     return this.isMultipleTask;
/*     */   }
/*     */ 
/*     */   public void setIsMultipleTask(Short isMultipleTask) {
/* 122 */     this.isMultipleTask = isMultipleTask;
/*     */   }
/*     */ 
/*     */   public String getPdId() {
/* 126 */     return this.pdId;
/*     */   }
/*     */ 
/*     */   public void setPdId(String pdId) {
/* 130 */     this.pdId = pdId;
/*     */   }
/*     */ 
/*     */   public String getTaskName() {
/* 134 */     return this.taskName;
/*     */   }
/*     */ 
/*     */   public void setTaskName(String taskName) {
/* 138 */     this.taskName = taskName;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.core.jbpm.pv.TaskInfo
 * JD-Core Version:    0.6.0
 */
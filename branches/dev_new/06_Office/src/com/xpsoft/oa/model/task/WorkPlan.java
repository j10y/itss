/*     */ package com.xpsoft.oa.model.task;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.model.system.FileAttach;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class WorkPlan extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long planId;
/*     */ 
/*     */   @Expose
/*     */   protected String planName;
/*     */ 
/*     */   @Expose
/*     */   protected String planContent;
/*     */ 
/*     */   @Expose
/*     */   protected Date startTime;
/*     */ 
/*     */   @Expose
/*     */   protected Date endTime;
/*     */ 
/*     */   @Expose
/*     */   protected String issueScope;
/*     */ 
/*     */   @Expose
/*     */   protected String participants;
/*     */ 
/*     */   @Expose
/*     */   protected String principal;
/*     */ 
/*     */   @Expose
/*     */   protected String note;
/*     */ 
/*     */   @Expose
/*     */   protected Short status;
/*     */ 
/*     */   @Expose
/*     */   protected Short isPersonal;
/*     */ 
/*     */   @Expose
/*     */   protected String icon;
/*     */ 
/*     */   @Expose
/*     */   protected PlanType planType;
/*     */ 
/*     */   @Expose
/*     */   protected AppUser appUser;
/*     */ 
/*     */   @Expose
/*  53 */   protected Set<FileAttach> planFiles = new HashSet();
/*  54 */   protected Set<PlanAttend> planAttends = new HashSet();
/*     */ 
/*     */   public WorkPlan()
/*     */   {
/*     */   }
/*     */ 
/*     */   public WorkPlan(Long in_planId)
/*     */   {
/*  69 */     setPlanId(in_planId);
/*     */   }
/*     */ 
/*     */   public Set<PlanAttend> getPlanAttends()
/*     */   {
/*  74 */     return this.planAttends;
/*     */   }
/*     */ 
/*     */   public void setPlanAttends(Set<PlanAttend> planAttends) {
/*  78 */     this.planAttends = planAttends;
/*     */   }
/*     */ 
/*     */   public PlanType getPlanType() {
/*  82 */     return this.planType;
/*     */   }
/*     */ 
/*     */   public void setPlanType(PlanType in_planType) {
/*  86 */     this.planType = in_planType;
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser() {
/*  90 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  94 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Set<FileAttach> getPlanFiles() {
/*  98 */     return this.planFiles;
/*     */   }
/*     */ 
/*     */   public void setPlanFiles(Set<FileAttach> in_planFiles) {
/* 102 */     this.planFiles = in_planFiles;
/*     */   }
/*     */ 
/*     */   public Long getPlanId()
/*     */   {
/* 111 */     return this.planId;
/*     */   }
/*     */ 
/*     */   public void setPlanId(Long aValue)
/*     */   {
/* 118 */     this.planId = aValue;
/*     */   }
/*     */ 
/*     */   public String getPlanName()
/*     */   {
/* 126 */     return this.planName;
/*     */   }
/*     */ 
/*     */   public void setPlanName(String aValue)
/*     */   {
/* 134 */     this.planName = aValue;
/*     */   }
/*     */ 
/*     */   public String getPlanContent()
/*     */   {
/* 142 */     return this.planContent;
/*     */   }
/*     */ 
/*     */   public void setPlanContent(String aValue)
/*     */   {
/* 149 */     this.planContent = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/* 157 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date aValue)
/*     */   {
/* 165 */     this.startTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 173 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date aValue)
/*     */   {
/* 181 */     this.endTime = aValue;
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/* 188 */     return getPlanType() == null ? null : getPlanType().getTypeId();
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/* 195 */     if (aValue == null) {
/* 196 */       this.planType = null;
/* 197 */     } else if (this.planType == null) {
/* 198 */       this.planType = new PlanType(aValue);
/* 199 */       this.planType.setVersion(new Integer(0));
/*     */     } else {
/* 201 */       this.planType.setTypeId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 209 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 216 */     if (aValue == null) {
/* 217 */       this.appUser = null;
/* 218 */     } else if (this.appUser == null) {
/* 219 */       this.appUser = new AppUser(aValue);
/* 220 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 222 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getIssueScope()
/*     */   {
/* 234 */     return this.issueScope;
/*     */   }
/*     */ 
/*     */   public void setIssueScope(String aValue)
/*     */   {
/* 241 */     this.issueScope = aValue;
/*     */   }
/*     */ 
/*     */   public String getParticipants()
/*     */   {
/* 251 */     return this.participants;
/*     */   }
/*     */ 
/*     */   public void setParticipants(String aValue)
/*     */   {
/* 258 */     this.participants = aValue;
/*     */   }
/*     */ 
/*     */   public String getPrincipal()
/*     */   {
/* 266 */     return this.principal;
/*     */   }
/*     */ 
/*     */   public void setPrincipal(String aValue)
/*     */   {
/* 274 */     this.principal = aValue;
/*     */   }
/*     */ 
/*     */   public String getNote()
/*     */   {
/* 282 */     return this.note;
/*     */   }
/*     */ 
/*     */   public void setNote(String aValue)
/*     */   {
/* 289 */     this.note = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 299 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 307 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsPersonal()
/*     */   {
/* 317 */     return this.isPersonal;
/*     */   }
/*     */ 
/*     */   public void setIsPersonal(Short aValue)
/*     */   {
/* 325 */     this.isPersonal = aValue;
/*     */   }
/*     */ 
/*     */   public String getIcon()
/*     */   {
/* 333 */     return this.icon;
/*     */   }
/*     */ 
/*     */   public void setIcon(String aValue)
/*     */   {
/* 340 */     this.icon = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 347 */     if (!(object instanceof WorkPlan)) {
/* 348 */       return false;
/*     */     }
/* 350 */     WorkPlan rhs = (WorkPlan)object;
/* 351 */     return new EqualsBuilder()
/* 352 */       .append(this.planId, rhs.planId)
/* 353 */       .append(this.planName, rhs.planName)
/* 354 */       .append(this.planContent, rhs.planContent)
/* 355 */       .append(this.startTime, rhs.startTime)
/* 356 */       .append(this.endTime, rhs.endTime)
/* 357 */       .append(this.issueScope, rhs.issueScope)
/* 358 */       .append(this.participants, rhs.participants)
/* 359 */       .append(this.principal, rhs.principal)
/* 360 */       .append(this.note, rhs.note)
/* 361 */       .append(this.status, rhs.status)
/* 362 */       .append(this.isPersonal, rhs.isPersonal)
/* 363 */       .append(this.icon, rhs.icon)
/* 364 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 371 */     return new HashCodeBuilder(-82280557, -700257973)
/* 372 */       .append(this.planId)
/* 373 */       .append(this.planName)
/* 374 */       .append(this.planContent)
/* 375 */       .append(this.startTime)
/* 376 */       .append(this.endTime)
/* 377 */       .append(this.issueScope)
/* 378 */       .append(this.participants)
/* 379 */       .append(this.principal)
/* 380 */       .append(this.note)
/* 381 */       .append(this.status)
/* 382 */       .append(this.isPersonal)
/* 383 */       .append(this.icon)
/* 384 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 391 */     return new ToStringBuilder(this)
/* 392 */       .append("planId", this.planId)
/* 393 */       .append("planName", this.planName)
/* 394 */       .append("planContent", this.planContent)
/* 395 */       .append("startTime", this.startTime)
/* 396 */       .append("endTime", this.endTime)
/* 397 */       .append("issueScope", this.issueScope)
/* 398 */       .append("participants", this.participants)
/* 399 */       .append("principal", this.principal)
/* 400 */       .append("note", this.note)
/* 401 */       .append("status", this.status)
/* 402 */       .append("isPersonal", this.isPersonal)
/* 403 */       .append("icon", this.icon)
/* 404 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.task.WorkPlan
 * JD-Core Version:    0.6.0
 */
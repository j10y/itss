/*     */ package com.xpsoft.oa.model.task;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.model.system.Department;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class PlanAttend extends BaseModel
/*     */ {
/*     */   protected Long attendId;
/*     */   protected Short isDep;
/*     */   protected Short isPrimary;
/*     */   protected WorkPlan workPlan;
/*     */   protected Department department;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public PlanAttend()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PlanAttend(Long in_attendId)
/*     */   {
/*  41 */     setAttendId(in_attendId);
/*     */   }
/*     */ 
/*     */   public WorkPlan getWorkPlan()
/*     */   {
/*  46 */     return this.workPlan;
/*     */   }
/*     */ 
/*     */   public void setWorkPlan(WorkPlan in_workPlan) {
/*  50 */     this.workPlan = in_workPlan;
/*     */   }
/*     */ 
/*     */   public Department getDepartment() {
/*  54 */     return this.department;
/*     */   }
/*     */ 
/*     */   public void setDepartment(Department in_department) {
/*  58 */     this.department = in_department;
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser() {
/*  62 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  66 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getAttendId()
/*     */   {
/*  75 */     return this.attendId;
/*     */   }
/*     */ 
/*     */   public void setAttendId(Long aValue)
/*     */   {
/*  82 */     this.attendId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getDepId()
/*     */   {
/*  89 */     return getDepartment() == null ? null : getDepartment().getDepId();
/*     */   }
/*     */ 
/*     */   public void setDepId(Long aValue)
/*     */   {
/*  96 */     if (aValue == null) {
/*  97 */       this.department = null;
/*  98 */     } else if (this.department == null) {
/*  99 */       this.department = new Department(aValue);
/* 100 */       this.department.setVersion(new Integer(0));
/*     */     } else {
/* 102 */       this.department.setDepId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 110 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 117 */     if (aValue == null) {
/* 118 */       this.appUser = null;
/* 119 */     } else if (this.appUser == null) {
/* 120 */       this.appUser = new AppUser(aValue);
/* 121 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 123 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getPlanId()
/*     */   {
/* 131 */     return getWorkPlan() == null ? null : getWorkPlan().getPlanId();
/*     */   }
/*     */ 
/*     */   public void setPlanId(Long aValue)
/*     */   {
/* 138 */     if (aValue == null) {
/* 139 */       this.workPlan = null;
/* 140 */     } else if (this.workPlan == null) {
/* 141 */       this.workPlan = new WorkPlan(aValue);
/* 142 */       this.workPlan.setVersion(new Integer(0));
/*     */     } else {
/* 144 */       this.workPlan.setPlanId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Short getIsDep()
/*     */   {
/* 153 */     return this.isDep;
/*     */   }
/*     */ 
/*     */   public void setIsDep(Short aValue)
/*     */   {
/* 161 */     this.isDep = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsPrimary()
/*     */   {
/* 169 */     return this.isPrimary;
/*     */   }
/*     */ 
/*     */   public void setIsPrimary(Short aValue)
/*     */   {
/* 176 */     this.isPrimary = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 183 */     if (!(object instanceof PlanAttend)) {
/* 184 */       return false;
/*     */     }
/* 186 */     PlanAttend rhs = (PlanAttend)object;
/* 187 */     return new EqualsBuilder()
/* 188 */       .append(this.attendId, rhs.attendId)
/* 189 */       .append(this.isDep, rhs.isDep)
/* 190 */       .append(this.isPrimary, rhs.isPrimary)
/* 191 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 198 */     return new HashCodeBuilder(-82280557, -700257973)
/* 199 */       .append(this.attendId)
/* 200 */       .append(this.isDep)
/* 201 */       .append(this.isPrimary)
/* 202 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 209 */     return new ToStringBuilder(this)
/* 210 */       .append("attendId", this.attendId)
/* 211 */       .append("isDep", this.isDep)
/* 212 */       .append("isPrimary", this.isPrimary)
/* 213 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.task.PlanAttend
 * JD-Core Version:    0.6.0
 */
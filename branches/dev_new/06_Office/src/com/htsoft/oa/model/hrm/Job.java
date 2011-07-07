/*     */ package com.htsoft.oa.model.hrm;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.Department;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Job extends BaseModel
/*     */ {
/*  19 */   public static short DELFLAG_NOT = 0;
/*  20 */   public static short DELFLAG_HAD = 1;
/*     */   protected Long jobId;
/*     */   protected String jobName;
/*     */   protected String memo;
/*     */   protected Short delFlag;
/*     */   protected Department department;
/*  28 */   protected Set empProfiles = new HashSet();
/*     */ 
/*     */   public Job()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Job(Long in_jobId)
/*     */   {
/*  43 */     setJobId(in_jobId);
/*     */   }
/*     */ 
/*     */   public Department getDepartment()
/*     */   {
/*  48 */     return this.department;
/*     */   }
/*     */ 
/*     */   public void setDepartment(Department in_department) {
/*  52 */     this.department = in_department;
/*     */   }
/*     */ 
/*     */   public Set getEmpProfiles() {
/*  56 */     return this.empProfiles;
/*     */   }
/*     */ 
/*     */   public void setEmpProfiles(Set in_empProfiles) {
/*  60 */     this.empProfiles = in_empProfiles;
/*     */   }
/*     */ 
/*     */   public Long getJobId()
/*     */   {
/*  69 */     return this.jobId;
/*     */   }
/*     */ 
/*     */   public void setJobId(Long aValue)
/*     */   {
/*  76 */     this.jobId = aValue;
/*     */   }
/*     */ 
/*     */   public String getJobName()
/*     */   {
/*  84 */     return this.jobName;
/*     */   }
/*     */ 
/*     */   public void setJobName(String aValue)
/*     */   {
/*  92 */     this.jobName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getDepId()
/*     */   {
/*  99 */     return getDepartment() == null ? null : getDepartment().getDepId();
/*     */   }
/*     */ 
/*     */   public void setDepId(Long aValue)
/*     */   {
/* 106 */     if (aValue == null) {
/* 107 */       this.department = null;
/* 108 */     } else if (this.department == null) {
/* 109 */       this.department = new Department(aValue);
/* 110 */       this.department.setVersion(new Integer(0));
/*     */     } else {
/* 112 */       this.department.setDepId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getMemo()
/*     */   {
/* 121 */     return this.memo;
/*     */   }
/*     */ 
/*     */   public void setMemo(String aValue)
/*     */   {
/* 128 */     this.memo = aValue;
/*     */   }
/*     */ 
/*     */   public Short getDelFlag() {
/* 132 */     return this.delFlag;
/*     */   }
/*     */ 
/*     */   public void setDelFlag(Short delFlag) {
/* 136 */     this.delFlag = delFlag;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 143 */     if (!(object instanceof Job)) {
/* 144 */       return false;
/*     */     }
/* 146 */     Job rhs = (Job)object;
/* 147 */     return new EqualsBuilder()
/* 148 */       .append(this.jobId, rhs.jobId)
/* 149 */       .append(this.jobName, rhs.jobName)
/* 150 */       .append(this.memo, rhs.memo)
/* 151 */       .append(this.delFlag, rhs.delFlag)
/* 152 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 159 */     return new HashCodeBuilder(-82280557, -700257973)
/* 160 */       .append(this.jobId)
/* 161 */       .append(this.jobName)
/* 162 */       .append(this.memo)
/* 163 */       .append(this.delFlag)
/* 164 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 171 */     return new ToStringBuilder(this)
/* 172 */       .append("jobId", this.jobId)
/* 173 */       .append("jobName", this.jobName)
/* 174 */       .append("memo", this.memo)
/* 175 */       .append("delFlag", this.delFlag)
/* 176 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.hrm.Job
 * JD-Core Version:    0.6.0
 */
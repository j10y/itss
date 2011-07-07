/*     */ package com.htsoft.oa.model.personal;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Duty extends BaseModel
/*     */ {
/*     */   protected Long dutyId;
/*     */   protected String fullname;
/*     */   protected Date startTime;
/*     */   protected Date endTime;
/*     */   protected DutySystem dutySystem;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public Duty()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Duty(Long in_dutyId)
/*     */   {
/*  41 */     setDutyId(in_dutyId);
/*     */   }
/*     */ 
/*     */   public DutySystem getDutySystem()
/*     */   {
/*  46 */     return this.dutySystem;
/*     */   }
/*     */ 
/*     */   public void setDutySystem(DutySystem in_dutySystem) {
/*  50 */     this.dutySystem = in_dutySystem;
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser() {
/*  54 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  58 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getDutyId()
/*     */   {
/*  67 */     return this.dutyId;
/*     */   }
/*     */ 
/*     */   public void setDutyId(Long aValue)
/*     */   {
/*  74 */     this.dutyId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  81 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/*  88 */     if (aValue == null) {
/*  89 */       this.appUser = null;
/*  90 */     } else if (this.appUser == null) {
/*  91 */       this.appUser = new AppUser(aValue);
/*  92 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/*  94 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 103 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 111 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public Long getSystemId()
/*     */   {
/* 118 */     return getDutySystem() == null ? null : getDutySystem().getSystemId();
/*     */   }
/*     */ 
/*     */   public void setSystemId(Long aValue)
/*     */   {
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/* 140 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date aValue)
/*     */   {
/* 148 */     this.startTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 156 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date aValue)
/*     */   {
/* 163 */     this.endTime = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 170 */     if (!(object instanceof Duty)) {
/* 171 */       return false;
/*     */     }
/* 173 */     Duty rhs = (Duty)object;
/* 174 */     return new EqualsBuilder()
/* 175 */       .append(this.dutyId, rhs.dutyId)
/* 176 */       .append(this.fullname, rhs.fullname)
/* 177 */       .append(this.startTime, rhs.startTime)
/* 178 */       .append(this.endTime, rhs.endTime)
/* 179 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 186 */     return new HashCodeBuilder(-82280557, -700257973)
/* 187 */       .append(this.dutyId)
/* 188 */       .append(this.fullname)
/* 189 */       .append(this.startTime)
/* 190 */       .append(this.endTime)
/* 191 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 198 */     return new ToStringBuilder(this)
/* 199 */       .append("dutyId", this.dutyId)
/* 200 */       .append("fullname", this.fullname)
/* 201 */       .append("startTime", this.startTime)
/* 202 */       .append("endTime", this.endTime)
/* 203 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.personal.Duty
 * JD-Core Version:    0.6.0
 */
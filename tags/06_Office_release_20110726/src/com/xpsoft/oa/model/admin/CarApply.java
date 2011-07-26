/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class CarApply extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long applyId;
/*     */ 
/*     */   @Expose
/*     */   protected String department;
/*     */ 
/*     */   @Expose
/*     */   protected String userFullname;
/*     */ 
/*     */   @Expose
/*     */   protected Date applyDate;
/*     */ 
/*     */   @Expose
/*     */   protected String reason;
/*     */ 
/*     */   @Expose
/*     */   protected Date startTime;
/*     */ 
/*     */   @Expose
/*     */   protected Date endTime;
/*     */ 
/*     */   @Expose
/*     */   protected String proposer;
/*     */ 
/*     */   @Expose
/*     */   protected Long userId;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal mileage;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal oilUse;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */ 
/*     */   @Expose
/*     */   protected Short approvalStatus;
/*     */ 
/*     */   @Expose
/*     */   protected Car car;
/*     */ 
/*     */   public CarApply()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CarApply(Long in_applyId)
/*     */   {
/*  65 */     setApplyId(in_applyId);
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  70 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long userId) {
/*  74 */     this.userId = userId;
/*     */   }
/*     */ 
/*     */   public Car getCar() {
/*  78 */     return this.car;
/*     */   }
/*     */ 
/*     */   public void setCar(Car in_car) {
/*  82 */     this.car = in_car;
/*     */   }
/*     */ 
/*     */   public Long getApplyId()
/*     */   {
/*  91 */     return this.applyId;
/*     */   }
/*     */ 
/*     */   public void setApplyId(Long aValue)
/*     */   {
/*  98 */     this.applyId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getCarId()
/*     */   {
/* 105 */     return getCar() == null ? null : getCar().getCarId();
/*     */   }
/*     */ 
/*     */   public void setCarId(Long aValue)
/*     */   {
/* 112 */     if (aValue == null) {
/* 113 */       this.car = null;
/* 114 */     } else if (this.car == null) {
/* 115 */       this.car = new Car(aValue);
/* 116 */       this.car.setVersion(new Integer(0));
/*     */     } else {
/* 118 */       this.car.setCarId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getDepartment()
/*     */   {
/* 127 */     return this.department;
/*     */   }
/*     */ 
/*     */   public void setDepartment(String aValue)
/*     */   {
/* 135 */     this.department = aValue;
/*     */   }
/*     */ 
/*     */   public String getUserFullname()
/*     */   {
/* 143 */     return this.userFullname;
/*     */   }
/*     */ 
/*     */   public void setUserFullname(String aValue)
/*     */   {
/* 151 */     this.userFullname = aValue;
/*     */   }
/*     */ 
/*     */   public Date getApplyDate()
/*     */   {
/* 159 */     return this.applyDate;
/*     */   }
/*     */ 
/*     */   public void setApplyDate(Date aValue)
/*     */   {
/* 167 */     this.applyDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getReason()
/*     */   {
/* 175 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public void setReason(String aValue)
/*     */   {
/* 183 */     this.reason = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/* 191 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date aValue)
/*     */   {
/* 199 */     this.startTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 207 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date aValue)
/*     */   {
/* 214 */     this.endTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getProposer()
/*     */   {
/* 222 */     return this.proposer;
/*     */   }
/*     */ 
/*     */   public void setProposer(String aValue)
/*     */   {
/* 230 */     this.proposer = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getMileage()
/*     */   {
/* 238 */     return this.mileage;
/*     */   }
/*     */ 
/*     */   public void setMileage(BigDecimal aValue)
/*     */   {
/* 245 */     this.mileage = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getOilUse()
/*     */   {
/* 253 */     return this.oilUse;
/*     */   }
/*     */ 
/*     */   public void setOilUse(BigDecimal aValue)
/*     */   {
/* 260 */     this.oilUse = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 268 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 275 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public Short getApprovalStatus()
/*     */   {
/* 283 */     return this.approvalStatus;
/*     */   }
/*     */ 
/*     */   public void setApprovalStatus(Short aValue)
/*     */   {
/* 291 */     this.approvalStatus = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 298 */     if (!(object instanceof CarApply)) {
/* 299 */       return false;
/*     */     }
/* 301 */     CarApply rhs = (CarApply)object;
/* 302 */     return new EqualsBuilder()
/* 303 */       .append(this.applyId, rhs.applyId)
/* 304 */       .append(this.department, rhs.department)
/* 305 */       .append(this.userFullname, rhs.userFullname)
/* 306 */       .append(this.applyDate, rhs.applyDate)
/* 307 */       .append(this.reason, rhs.reason)
/* 308 */       .append(this.startTime, rhs.startTime)
/* 309 */       .append(this.endTime, rhs.endTime)
/* 310 */       .append(this.proposer, rhs.proposer)
/* 311 */       .append(this.userId, rhs.userId)
/* 312 */       .append(this.mileage, rhs.mileage)
/* 313 */       .append(this.oilUse, rhs.oilUse)
/* 314 */       .append(this.notes, rhs.notes)
/* 315 */       .append(this.approvalStatus, rhs.approvalStatus)
/* 316 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 323 */     return new HashCodeBuilder(-82280557, -700257973)
/* 324 */       .append(this.applyId)
/* 325 */       .append(this.department)
/* 326 */       .append(this.userFullname)
/* 327 */       .append(this.applyDate)
/* 328 */       .append(this.reason)
/* 329 */       .append(this.startTime)
/* 330 */       .append(this.endTime)
/* 331 */       .append(this.proposer)
/* 332 */       .append(this.userId)
/* 333 */       .append(this.mileage)
/* 334 */       .append(this.oilUse)
/* 335 */       .append(this.notes)
/* 336 */       .append(this.approvalStatus)
/* 337 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 344 */     return new ToStringBuilder(this)
/* 345 */       .append("applyId", this.applyId)
/* 346 */       .append("department", this.department)
/* 347 */       .append("userFullname", this.userFullname)
/* 348 */       .append("applyDate", this.applyDate)
/* 349 */       .append("reason", this.reason)
/* 350 */       .append("startTime", this.startTime)
/* 351 */       .append("endTime", this.endTime)
/* 352 */       .append("proposer", this.proposer)
/* 353 */       .append("userId", this.userId)
/* 354 */       .append("mileage", this.mileage)
/* 355 */       .append("oilUse", this.oilUse)
/* 356 */       .append("notes", this.notes)
/* 357 */       .append("approvalStatus", this.approvalStatus)
/* 358 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.CarApply
 * JD-Core Version:    0.6.0
 */
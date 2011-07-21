/*     */ package com.xpsoft.oa.model.hrm;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class JobChange extends BaseModel
/*     */ {
/*     */   protected Long changeId;
/*     */   protected Long profileId;
/*     */   protected String profileNo;
/*     */   protected String userName;
/*     */   protected Long orgJobId;
/*     */   protected String orgJobName;
/*     */   protected Long newJobId;
/*     */   protected String newJobName;
/*     */   protected Long orgStandardId;
/*     */   protected Long newStandardId;
/*     */   protected String orgStandardNo;
/*     */   protected String orgStandardName;
/*     */   protected Long orgDepId;
/*     */   protected String orgDepName;
/*     */   protected BigDecimal orgTotalMoney;
/*     */   protected String newStandardNo;
/*     */   protected String newStandardName;
/*     */   protected Long newDepId;
/*     */   protected String newDepName;
/*     */   protected BigDecimal newTotalMoney;
/*     */   protected String changeReason;
/*     */   protected String regName;
/*     */   protected Date regTime;
/*     */   protected String checkName;
/*     */   protected Date checkTime;
/*     */   protected String checkOpinion;
/*     */   protected Short status;
/*     */   protected String memo;
/*     */ 
/*     */   public JobChange()
/*     */   {
/*     */   }
/*     */ 
/*     */   public JobChange(Long in_changeId)
/*     */   {
/*  62 */     setChangeId(in_changeId);
/*     */   }
/*     */ 
/*     */   public Long getChangeId()
/*     */   {
/*  72 */     return this.changeId;
/*     */   }
/*     */ 
/*     */   public Long getOrgStandardId()
/*     */   {
/*  78 */     return this.orgStandardId;
/*     */   }
/*     */ 
/*     */   public void setOrgStandardId(Long orgStandardId) {
/*  82 */     this.orgStandardId = orgStandardId;
/*     */   }
/*     */ 
/*     */   public Long getNewStandardId() {
/*  86 */     return this.newStandardId;
/*     */   }
/*     */ 
/*     */   public void setNewStandardId(Long newStandardId) {
/*  90 */     this.newStandardId = newStandardId;
/*     */   }
/*     */ 
/*     */   public void setChangeId(Long aValue)
/*     */   {
/*  97 */     this.changeId = aValue;
/*     */   }
/*     */ 
/*     */   public Date getRegTime() {
/* 101 */     return this.regTime;
/*     */   }
/*     */ 
/*     */   public void setRegTime(Date regTime) {
/* 105 */     this.regTime = regTime;
/*     */   }
/*     */ 
/*     */   public Long getProfileId()
/*     */   {
/* 114 */     return this.profileId;
/*     */   }
/*     */ 
/*     */   public void setProfileId(Long aValue)
/*     */   {
/* 122 */     this.profileId = aValue;
/*     */   }
/*     */ 
/*     */   public String getProfileNo()
/*     */   {
/* 130 */     return this.profileNo;
/*     */   }
/*     */ 
/*     */   public void setProfileNo(String aValue)
/*     */   {
/* 138 */     this.profileNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getUserName()
/*     */   {
/* 146 */     return this.userName;
/*     */   }
/*     */ 
/*     */   public void setUserName(String aValue)
/*     */   {
/* 153 */     this.userName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getOrgJobId()
/*     */   {
/* 161 */     return this.orgJobId;
/*     */   }
/*     */ 
/*     */   public void setOrgJobId(Long aValue)
/*     */   {
/* 169 */     this.orgJobId = aValue;
/*     */   }
/*     */ 
/*     */   public String getOrgJobName()
/*     */   {
/* 177 */     return this.orgJobName;
/*     */   }
/*     */ 
/*     */   public void setOrgJobName(String aValue)
/*     */   {
/* 185 */     this.orgJobName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getNewJobId()
/*     */   {
/* 193 */     return this.newJobId;
/*     */   }
/*     */ 
/*     */   public void setNewJobId(Long aValue)
/*     */   {
/* 201 */     this.newJobId = aValue;
/*     */   }
/*     */ 
/*     */   public String getNewJobName()
/*     */   {
/* 209 */     return this.newJobName;
/*     */   }
/*     */ 
/*     */   public void setNewJobName(String aValue)
/*     */   {
/* 217 */     this.newJobName = aValue;
/*     */   }
/*     */ 
/*     */   public String getOrgStandardNo()
/*     */   {
/* 225 */     return this.orgStandardNo;
/*     */   }
/*     */ 
/*     */   public void setOrgStandardNo(String aValue)
/*     */   {
/* 232 */     this.orgStandardNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getOrgStandardName()
/*     */   {
/* 240 */     return this.orgStandardName;
/*     */   }
/*     */ 
/*     */   public void setOrgStandardName(String aValue)
/*     */   {
/* 247 */     this.orgStandardName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getOrgDepId()
/*     */   {
/* 255 */     return this.orgDepId;
/*     */   }
/*     */ 
/*     */   public void setOrgDepId(Long aValue)
/*     */   {
/* 262 */     this.orgDepId = aValue;
/*     */   }
/*     */ 
/*     */   public String getOrgDepName()
/*     */   {
/* 270 */     return this.orgDepName;
/*     */   }
/*     */ 
/*     */   public void setOrgDepName(String aValue)
/*     */   {
/* 277 */     this.orgDepName = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getOrgTotalMoney()
/*     */   {
/* 285 */     return this.orgTotalMoney;
/*     */   }
/*     */ 
/*     */   public void setOrgTotalMoney(BigDecimal aValue)
/*     */   {
/* 292 */     this.orgTotalMoney = aValue;
/*     */   }
/*     */ 
/*     */   public String getNewStandardNo()
/*     */   {
/* 300 */     return this.newStandardNo;
/*     */   }
/*     */ 
/*     */   public void setNewStandardNo(String aValue)
/*     */   {
/* 307 */     this.newStandardNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getNewStandardName()
/*     */   {
/* 315 */     return this.newStandardName;
/*     */   }
/*     */ 
/*     */   public void setNewStandardName(String aValue)
/*     */   {
/* 322 */     this.newStandardName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getNewDepId()
/*     */   {
/* 330 */     return this.newDepId;
/*     */   }
/*     */ 
/*     */   public void setNewDepId(Long aValue)
/*     */   {
/* 337 */     this.newDepId = aValue;
/*     */   }
/*     */ 
/*     */   public String getNewDepName()
/*     */   {
/* 345 */     return this.newDepName;
/*     */   }
/*     */ 
/*     */   public void setNewDepName(String aValue)
/*     */   {
/* 352 */     this.newDepName = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getNewTotalMoney()
/*     */   {
/* 360 */     return this.newTotalMoney;
/*     */   }
/*     */ 
/*     */   public void setNewTotalMoney(BigDecimal aValue)
/*     */   {
/* 367 */     this.newTotalMoney = aValue;
/*     */   }
/*     */ 
/*     */   public String getChangeReason()
/*     */   {
/* 375 */     return this.changeReason;
/*     */   }
/*     */ 
/*     */   public void setChangeReason(String aValue)
/*     */   {
/* 382 */     this.changeReason = aValue;
/*     */   }
/*     */ 
/*     */   public String getRegName()
/*     */   {
/* 390 */     return this.regName;
/*     */   }
/*     */ 
/*     */   public void setRegName(String aValue)
/*     */   {
/* 397 */     this.regName = aValue;
/*     */   }
/*     */ 
/*     */   public String getCheckName()
/*     */   {
/* 405 */     return this.checkName;
/*     */   }
/*     */ 
/*     */   public void setCheckName(String aValue)
/*     */   {
/* 412 */     this.checkName = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCheckTime()
/*     */   {
/* 420 */     return this.checkTime;
/*     */   }
/*     */ 
/*     */   public void setCheckTime(Date aValue)
/*     */   {
/* 427 */     this.checkTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getCheckOpinion()
/*     */   {
/* 435 */     return this.checkOpinion;
/*     */   }
/*     */ 
/*     */   public void setCheckOpinion(String aValue)
/*     */   {
/* 442 */     this.checkOpinion = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 456 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 463 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public String getMemo()
/*     */   {
/* 471 */     return this.memo;
/*     */   }
/*     */ 
/*     */   public void setMemo(String aValue)
/*     */   {
/* 478 */     this.memo = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 485 */     if (!(object instanceof JobChange)) {
/* 486 */       return false;
/*     */     }
/* 488 */     JobChange rhs = (JobChange)object;
/* 489 */     return new EqualsBuilder()
/* 490 */       .append(this.changeId, rhs.changeId)
/* 491 */       .append(this.profileId, rhs.profileId)
/* 492 */       .append(this.profileNo, rhs.profileNo)
/* 493 */       .append(this.userName, rhs.userName)
/* 494 */       .append(this.orgJobId, rhs.orgJobId)
/* 495 */       .append(this.orgStandardId, rhs.orgStandardId)
/* 496 */       .append(this.newStandardId, rhs.newStandardId)
/* 497 */       .append(this.orgJobName, rhs.orgJobName)
/* 498 */       .append(this.newJobId, rhs.newJobId)
/* 499 */       .append(this.newJobName, rhs.newJobName)
/* 500 */       .append(this.orgStandardNo, rhs.orgStandardNo)
/* 501 */       .append(this.orgStandardName, rhs.orgStandardName)
/* 502 */       .append(this.orgDepId, rhs.orgDepId)
/* 503 */       .append(this.orgDepName, rhs.orgDepName)
/* 504 */       .append(this.orgTotalMoney, rhs.orgTotalMoney)
/* 505 */       .append(this.newStandardNo, rhs.newStandardNo)
/* 506 */       .append(this.newStandardName, rhs.newStandardName)
/* 507 */       .append(this.newDepId, rhs.newDepId)
/* 508 */       .append(this.newDepName, rhs.newDepName)
/* 509 */       .append(this.newTotalMoney, rhs.newTotalMoney)
/* 510 */       .append(this.changeReason, rhs.changeReason)
/* 511 */       .append(this.regName, rhs.regName)
/* 512 */       .append(this.regTime, rhs.regTime)
/* 513 */       .append(this.checkName, rhs.checkName)
/* 514 */       .append(this.checkTime, rhs.checkTime)
/* 515 */       .append(this.checkOpinion, rhs.checkOpinion)
/* 516 */       .append(this.status, rhs.status)
/* 517 */       .append(this.memo, rhs.memo)
/* 518 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 525 */     return new HashCodeBuilder(-82280557, -700257973)
/* 526 */       .append(this.changeId)
/* 527 */       .append(this.profileId)
/* 528 */       .append(this.profileNo)
/* 529 */       .append(this.userName)
/* 530 */       .append(this.orgJobId)
/* 531 */       .append(this.orgJobName)
/* 532 */       .append(this.newJobId)
/* 533 */       .append(this.newJobName)
/* 534 */       .append(this.orgStandardNo)
/* 535 */       .append(this.orgStandardName)
/* 536 */       .append(this.orgDepId)
/* 537 */       .append(this.orgStandardId)
/* 538 */       .append(this.newStandardId)
/* 539 */       .append(this.orgDepName)
/* 540 */       .append(this.orgTotalMoney)
/* 541 */       .append(this.newStandardNo)
/* 542 */       .append(this.newStandardName)
/* 543 */       .append(this.newDepId)
/* 544 */       .append(this.newDepName)
/* 545 */       .append(this.newTotalMoney)
/* 546 */       .append(this.changeReason)
/* 547 */       .append(this.regName)
/* 548 */       .append(this.regTime)
/* 549 */       .append(this.checkName)
/* 550 */       .append(this.checkTime)
/* 551 */       .append(this.checkOpinion)
/* 552 */       .append(this.status)
/* 553 */       .append(this.memo)
/* 554 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 561 */     return new ToStringBuilder(this)
/* 562 */       .append("changeId", this.changeId)
/* 563 */       .append("profileId", this.profileId)
/* 564 */       .append("profileNo", this.profileNo)
/* 565 */       .append("userName", this.userName)
/* 566 */       .append("orgJobId", this.orgJobId)
/* 567 */       .append("orgJobName", this.orgJobName)
/* 568 */       .append("newJobId", this.newJobId)
/* 569 */       .append("newJobName", this.newJobName)
/* 570 */       .append("orgStandardId", this.orgStandardId)
/* 571 */       .append("newStandardId", this.newStandardId)
/* 572 */       .append("orgStandardNo", this.orgStandardNo)
/* 573 */       .append("orgStandardName", this.orgStandardName)
/* 574 */       .append("orgDepId", this.orgDepId)
/* 575 */       .append("orgDepName", this.orgDepName)
/* 576 */       .append("orgTotalMoney", this.orgTotalMoney)
/* 577 */       .append("newStandardNo", this.newStandardNo)
/* 578 */       .append("newStandardName", this.newStandardName)
/* 579 */       .append("newDepId", this.newDepId)
/* 580 */       .append("newDepName", this.newDepName)
/* 581 */       .append("newTotalMoney", this.newTotalMoney)
/* 582 */       .append("changeReason", this.changeReason)
/* 583 */       .append("regName", this.regName)
/* 584 */       .append("regTime", this.regTime)
/* 585 */       .append("checkName", this.checkName)
/* 586 */       .append("checkTime", this.checkTime)
/* 587 */       .append("checkOpinion", this.checkOpinion)
/* 588 */       .append("status", this.status)
/* 589 */       .append("memo", this.memo)
/* 590 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.hrm.JobChange
 * JD-Core Version:    0.6.0
 */
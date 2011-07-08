/*     */ package com.xpsoft.oa.model.hrm;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class StandSalary extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long standardId;
/*     */ 
/*     */   @Expose
/*     */   protected String standardNo;
/*     */ 
/*     */   @Expose
/*     */   protected String standardName;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal totalMoney;
/*     */ 
/*     */   @Expose
/*     */   protected String framer;
/*     */ 
/*     */   @Expose
/*     */   protected Date setdownTime;
/*     */ 
/*     */   @Expose
/*     */   protected String checkName;
/*     */ 
/*     */   @Expose
/*     */   protected Date checkTime;
/*     */ 
/*     */   @Expose
/*     */   protected String modifyName;
/*     */ 
/*     */   @Expose
/*     */   protected Date modifyTime;
/*     */ 
/*     */   @Expose
/*     */   protected String checkOpinion;
/*     */ 
/*     */   @Expose
/*     */   protected Short status;
/*     */ 
/*     */   @Expose
/*     */   protected String memo;
/*  45 */   protected Set standSalaryItems = new HashSet();
/*     */ 
/*     */   public StandSalary()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StandSalary(Long in_standardId)
/*     */   {
/*  60 */     setStandardId(in_standardId);
/*     */   }
/*     */ 
/*     */   public Set getStandSalaryItems()
/*     */   {
/*  65 */     return this.standSalaryItems;
/*     */   }
/*     */ 
/*     */   public void setStandSalaryItems(Set in_standSalaryItems) {
/*  69 */     this.standSalaryItems = in_standSalaryItems;
/*     */   }
/*     */ 
/*     */   public Long getStandardId()
/*     */   {
/*  78 */     return this.standardId;
/*     */   }
/*     */ 
/*     */   public void setStandardId(Long aValue)
/*     */   {
/*  85 */     this.standardId = aValue;
/*     */   }
/*     */ 
/*     */   public String getStandardNo()
/*     */   {
/*  94 */     return this.standardNo;
/*     */   }
/*     */ 
/*     */   public void setStandardNo(String aValue)
/*     */   {
/* 102 */     this.standardNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getStandardName()
/*     */   {
/* 110 */     return this.standardName;
/*     */   }
/*     */ 
/*     */   public void setStandardName(String aValue)
/*     */   {
/* 118 */     this.standardName = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getTotalMoney()
/*     */   {
/* 126 */     return this.totalMoney;
/*     */   }
/*     */ 
/*     */   public void setTotalMoney(BigDecimal aValue)
/*     */   {
/* 134 */     this.totalMoney = aValue;
/*     */   }
/*     */ 
/*     */   public String getFramer()
/*     */   {
/* 142 */     return this.framer;
/*     */   }
/*     */ 
/*     */   public void setFramer(String aValue)
/*     */   {
/* 149 */     this.framer = aValue;
/*     */   }
/*     */ 
/*     */   public Date getSetdownTime()
/*     */   {
/* 157 */     return this.setdownTime;
/*     */   }
/*     */ 
/*     */   public void setSetdownTime(Date aValue)
/*     */   {
/* 164 */     this.setdownTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getCheckName()
/*     */   {
/* 172 */     return this.checkName;
/*     */   }
/*     */ 
/*     */   public void setCheckName(String aValue)
/*     */   {
/* 179 */     this.checkName = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCheckTime()
/*     */   {
/* 187 */     return this.checkTime;
/*     */   }
/*     */ 
/*     */   public void setCheckTime(Date aValue)
/*     */   {
/* 194 */     this.checkTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getModifyName()
/*     */   {
/* 202 */     return this.modifyName;
/*     */   }
/*     */ 
/*     */   public void setModifyName(String aValue)
/*     */   {
/* 209 */     this.modifyName = aValue;
/*     */   }
/*     */ 
/*     */   public Date getModifyTime()
/*     */   {
/* 217 */     return this.modifyTime;
/*     */   }
/*     */ 
/*     */   public void setModifyTime(Date aValue)
/*     */   {
/* 224 */     this.modifyTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getCheckOpinion()
/*     */   {
/* 232 */     return this.checkOpinion;
/*     */   }
/*     */ 
/*     */   public void setCheckOpinion(String aValue)
/*     */   {
/* 239 */     this.checkOpinion = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 249 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 257 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public String getMemo()
/*     */   {
/* 265 */     return this.memo;
/*     */   }
/*     */ 
/*     */   public void setMemo(String aValue)
/*     */   {
/* 272 */     this.memo = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 279 */     if (!(object instanceof StandSalary)) {
/* 280 */       return false;
/*     */     }
/* 282 */     StandSalary rhs = (StandSalary)object;
/* 283 */     return new EqualsBuilder()
/* 284 */       .append(this.standardId, rhs.standardId)
/* 285 */       .append(this.standardNo, rhs.standardNo)
/* 286 */       .append(this.standardName, rhs.standardName)
/* 287 */       .append(this.totalMoney, rhs.totalMoney)
/* 288 */       .append(this.framer, rhs.framer)
/* 289 */       .append(this.setdownTime, rhs.setdownTime)
/* 290 */       .append(this.checkName, rhs.checkName)
/* 291 */       .append(this.checkTime, rhs.checkTime)
/* 292 */       .append(this.modifyName, rhs.modifyName)
/* 293 */       .append(this.modifyTime, rhs.modifyTime)
/* 294 */       .append(this.checkOpinion, rhs.checkOpinion)
/* 295 */       .append(this.status, rhs.status)
/* 296 */       .append(this.memo, rhs.memo)
/* 297 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 304 */     return new HashCodeBuilder(-82280557, -700257973)
/* 305 */       .append(this.standardId)
/* 306 */       .append(this.standardNo)
/* 307 */       .append(this.standardName)
/* 308 */       .append(this.totalMoney)
/* 309 */       .append(this.framer)
/* 310 */       .append(this.setdownTime)
/* 311 */       .append(this.checkName)
/* 312 */       .append(this.checkTime)
/* 313 */       .append(this.modifyName)
/* 314 */       .append(this.modifyTime)
/* 315 */       .append(this.checkOpinion)
/* 316 */       .append(this.status)
/* 317 */       .append(this.memo)
/* 318 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 325 */     return new ToStringBuilder(this)
/* 326 */       .append("standardId", this.standardId)
/* 327 */       .append("standardNo", this.standardNo)
/* 328 */       .append("standardName", this.standardName)
/* 329 */       .append("totalMoney", this.totalMoney)
/* 330 */       .append("framer", this.framer)
/* 331 */       .append("setdownTime", this.setdownTime)
/* 332 */       .append("checkName", this.checkName)
/* 333 */       .append("checkTime", this.checkTime)
/* 334 */       .append("modifyName", this.modifyName)
/* 335 */       .append("modifyTime", this.modifyTime)
/* 336 */       .append("checkOpinion", this.checkOpinion)
/* 337 */       .append("status", this.status)
/* 338 */       .append("memo", this.memo)
/* 339 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.hrm.StandSalary
 * JD-Core Version:    0.6.0
 */
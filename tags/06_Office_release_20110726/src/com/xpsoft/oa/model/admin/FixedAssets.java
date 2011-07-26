/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class FixedAssets extends BaseModel
/*     */ {
/*     */   protected Long assetsId;
/*     */   protected String assetsNo;
/*     */   protected String assetsName;
/*     */   protected String model;
/*     */   protected String manufacturer;
/*     */   protected Date manuDate;
/*     */   protected Date buyDate;
/*     */   protected String beDep;
/*     */   protected String custodian;
/*     */   protected String notes;
/*     */   protected BigDecimal remainValRate;
/*     */   protected Date startDepre;
/*     */   protected BigDecimal intendTerm;
/*     */   protected BigDecimal intendWorkGross;
/*     */   protected String workGrossUnit;
/*     */   protected BigDecimal assetValue;
/*     */   protected BigDecimal assetCurValue;
/*     */   protected BigDecimal depreRate;
/*     */   protected BigDecimal defPerWorkGross;
/*     */   protected DepreType depreType;
/*     */   protected AssetsType assetsType;
/*  44 */   protected Set depreRecords = new HashSet();
/*     */ 
/*     */   public FixedAssets()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FixedAssets(Long in_assetsId)
/*     */   {
/*  59 */     setAssetsId(in_assetsId);
/*     */   }
/*     */ 
/*     */   public BigDecimal getDepreRate()
/*     */   {
/*  67 */     return this.depreRate;
/*     */   }
/*     */ 
/*     */   public void setDepreRate(BigDecimal aValue)
/*     */   {
/*  75 */     this.depreRate = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getDefPerWorkGross() {
/*  79 */     return this.defPerWorkGross;
/*     */   }
/*     */ 
/*     */   public void setDefPerWorkGross(BigDecimal defPerWorkGross) {
/*  83 */     this.defPerWorkGross = defPerWorkGross;
/*     */   }
/*     */ 
/*     */   public DepreType getDepreType() {
/*  87 */     return this.depreType;
/*     */   }
/*     */ 
/*     */   public void setDepreType(DepreType in_depreType) {
/*  91 */     this.depreType = in_depreType;
/*     */   }
/*     */ 
/*     */   public AssetsType getAssetsType() {
/*  95 */     return this.assetsType;
/*     */   }
/*     */ 
/*     */   public void setAssetsType(AssetsType in_assetsType) {
/*  99 */     this.assetsType = in_assetsType;
/*     */   }
/*     */ 
/*     */   public Set getDepreRecords() {
/* 103 */     return this.depreRecords;
/*     */   }
/*     */ 
/*     */   public void setDepreRecords(Set in_depreRecords) {
/* 107 */     this.depreRecords = in_depreRecords;
/*     */   }
/*     */ 
/*     */   public Long getAssetsId()
/*     */   {
/* 116 */     return this.assetsId;
/*     */   }
/*     */ 
/*     */   public void setAssetsId(Long aValue)
/*     */   {
/* 123 */     this.assetsId = aValue;
/*     */   }
/*     */ 
/*     */   public String getAssetsNo()
/*     */   {
/* 131 */     return this.assetsNo;
/*     */   }
/*     */ 
/*     */   public void setAssetsNo(String aValue)
/*     */   {
/* 138 */     this.assetsNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getAssetsName()
/*     */   {
/* 146 */     return this.assetsName;
/*     */   }
/*     */ 
/*     */   public void setAssetsName(String aValue)
/*     */   {
/* 154 */     this.assetsName = aValue;
/*     */   }
/*     */ 
/*     */   public String getModel()
/*     */   {
/* 162 */     return this.model;
/*     */   }
/*     */ 
/*     */   public void setModel(String aValue)
/*     */   {
/* 169 */     this.model = aValue;
/*     */   }
/*     */ 
/*     */   public Long getAssetsTypeId()
/*     */   {
/* 176 */     return getAssetsType() == null ? null : getAssetsType().getAssetsTypeId();
/*     */   }
/*     */ 
/*     */   public void setAssetsTypeId(Long aValue)
/*     */   {
/* 183 */     if (aValue == null) {
/* 184 */       this.assetsType = null;
/* 185 */     } else if (this.assetsType == null) {
/* 186 */       this.assetsType = new AssetsType(aValue);
/* 187 */       this.assetsType.setVersion(new Integer(0));
/*     */     } else {
/* 189 */       this.assetsType.setAssetsTypeId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getManufacturer()
/*     */   {
/* 198 */     return this.manufacturer;
/*     */   }
/*     */ 
/*     */   public void setManufacturer(String aValue)
/*     */   {
/* 205 */     this.manufacturer = aValue;
/*     */   }
/*     */ 
/*     */   public Date getManuDate()
/*     */   {
/* 213 */     return this.manuDate;
/*     */   }
/*     */ 
/*     */   public void setManuDate(Date aValue)
/*     */   {
/* 220 */     this.manuDate = aValue;
/*     */   }
/*     */ 
/*     */   public Date getBuyDate()
/*     */   {
/* 228 */     return this.buyDate;
/*     */   }
/*     */ 
/*     */   public void setBuyDate(Date aValue)
/*     */   {
/* 236 */     this.buyDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getBeDep()
/*     */   {
/* 244 */     return this.beDep;
/*     */   }
/*     */ 
/*     */   public void setBeDep(String aValue)
/*     */   {
/* 252 */     this.beDep = aValue;
/*     */   }
/*     */ 
/*     */   public String getCustodian()
/*     */   {
/* 260 */     return this.custodian;
/*     */   }
/*     */ 
/*     */   public void setCustodian(String aValue)
/*     */   {
/* 267 */     this.custodian = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 275 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 282 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getRemainValRate()
/*     */   {
/* 290 */     return this.remainValRate;
/*     */   }
/*     */ 
/*     */   public void setRemainValRate(BigDecimal aValue)
/*     */   {
/* 298 */     this.remainValRate = aValue;
/*     */   }
/*     */ 
/*     */   public Long getDepreTypeId()
/*     */   {
/* 305 */     return getDepreType() == null ? null : getDepreType().getDepreTypeId();
/*     */   }
/*     */ 
/*     */   public void setDepreTypeId(Long aValue)
/*     */   {
/* 312 */     if (aValue == null) {
/* 313 */       this.depreType = null;
/* 314 */     } else if (this.depreType == null) {
/* 315 */       this.depreType = new DepreType(aValue);
/* 316 */       this.depreType.setVersion(new Integer(0));
/*     */     } else {
/* 318 */       this.depreType.setDepreTypeId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getStartDepre()
/*     */   {
/* 327 */     return this.startDepre;
/*     */   }
/*     */ 
/*     */   public void setStartDepre(Date aValue)
/*     */   {
/* 334 */     this.startDepre = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getIntendTerm()
/*     */   {
/* 342 */     return this.intendTerm;
/*     */   }
/*     */ 
/*     */   public void setIntendTerm(BigDecimal aValue)
/*     */   {
/* 349 */     this.intendTerm = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getIntendWorkGross()
/*     */   {
/* 357 */     return this.intendWorkGross;
/*     */   }
/*     */ 
/*     */   public void setIntendWorkGross(BigDecimal aValue)
/*     */   {
/* 364 */     this.intendWorkGross = aValue;
/*     */   }
/*     */ 
/*     */   public String getWorkGrossUnit()
/*     */   {
/* 372 */     return this.workGrossUnit;
/*     */   }
/*     */ 
/*     */   public void setWorkGrossUnit(String aValue)
/*     */   {
/* 379 */     this.workGrossUnit = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getAssetValue()
/*     */   {
/* 387 */     return this.assetValue;
/*     */   }
/*     */ 
/*     */   public void setAssetValue(BigDecimal aValue)
/*     */   {
/* 395 */     this.assetValue = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getAssetCurValue()
/*     */   {
/* 403 */     return this.assetCurValue;
/*     */   }
/*     */ 
/*     */   public void setAssetCurValue(BigDecimal aValue)
/*     */   {
/* 411 */     this.assetCurValue = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 418 */     if (!(object instanceof FixedAssets)) {
/* 419 */       return false;
/*     */     }
/* 421 */     FixedAssets rhs = (FixedAssets)object;
/* 422 */     return new EqualsBuilder()
/* 423 */       .append(this.assetsId, rhs.assetsId)
/* 424 */       .append(this.assetsNo, rhs.assetsNo)
/* 425 */       .append(this.assetsName, rhs.assetsName)
/* 426 */       .append(this.model, rhs.model)
/* 427 */       .append(this.manufacturer, rhs.manufacturer)
/* 428 */       .append(this.manuDate, rhs.manuDate)
/* 429 */       .append(this.buyDate, rhs.buyDate)
/* 430 */       .append(this.beDep, rhs.beDep)
/* 431 */       .append(this.custodian, rhs.custodian)
/* 432 */       .append(this.notes, rhs.notes)
/* 433 */       .append(this.depreRate, rhs.depreRate)
/* 434 */       .append(this.remainValRate, rhs.remainValRate)
/* 435 */       .append(this.startDepre, rhs.startDepre)
/* 436 */       .append(this.intendTerm, rhs.intendTerm)
/* 437 */       .append(this.intendWorkGross, rhs.intendWorkGross)
/* 438 */       .append(this.workGrossUnit, rhs.workGrossUnit)
/* 439 */       .append(this.assetValue, rhs.assetValue)
/* 440 */       .append(this.assetCurValue, rhs.assetCurValue)
/* 441 */       .append(this.defPerWorkGross, rhs.defPerWorkGross)
/* 442 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 449 */     return new HashCodeBuilder(-82280557, -700257973)
/* 450 */       .append(this.assetsId)
/* 451 */       .append(this.assetsNo)
/* 452 */       .append(this.assetsName)
/* 453 */       .append(this.model)
/* 454 */       .append(this.manufacturer)
/* 455 */       .append(this.manuDate)
/* 456 */       .append(this.buyDate)
/* 457 */       .append(this.beDep)
/* 458 */       .append(this.custodian)
/* 459 */       .append(this.notes)
/* 460 */       .append(this.depreRate)
/* 461 */       .append(this.remainValRate)
/* 462 */       .append(this.startDepre)
/* 463 */       .append(this.intendTerm)
/* 464 */       .append(this.intendWorkGross)
/* 465 */       .append(this.workGrossUnit)
/* 466 */       .append(this.assetValue)
/* 467 */       .append(this.defPerWorkGross)
/* 468 */       .append(this.assetCurValue)
/* 469 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 476 */     return new ToStringBuilder(this)
/* 477 */       .append("assetsId", this.assetsId)
/* 478 */       .append("assetsNo", this.assetsNo)
/* 479 */       .append("assetsName", this.assetsName)
/* 480 */       .append("model", this.model)
/* 481 */       .append("manufacturer", this.manufacturer)
/* 482 */       .append("manuDate", this.manuDate)
/* 483 */       .append("buyDate", this.buyDate)
/* 484 */       .append("beDep", this.beDep)
/* 485 */       .append("custodian", this.custodian)
/* 486 */       .append("notes", this.notes)
/* 487 */       .append("remainValRate", this.remainValRate)
/* 488 */       .append("startDepre", this.startDepre)
/* 489 */       .append("intendTerm", this.intendTerm)
/* 490 */       .append("intendWorkGross", this.intendWorkGross)
/* 491 */       .append("workGrossUnit", this.workGrossUnit)
/* 492 */       .append("assetValue", this.assetValue)
/* 493 */       .append("depreRate", this.depreRate)
/* 494 */       .append("defPerWorkGross", this.defPerWorkGross)
/* 495 */       .append("assetCurValue", this.assetCurValue)
/* 496 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.FixedAssets
 * JD-Core Version:    0.6.0
 */
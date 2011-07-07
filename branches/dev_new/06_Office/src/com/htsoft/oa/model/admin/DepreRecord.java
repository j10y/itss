/*     */ package com.htsoft.oa.model.admin;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class DepreRecord extends BaseModel
/*     */ {
/*     */   protected Long recordId;
/*     */   protected BigDecimal workCapacity;
/*     */   protected BigDecimal depreAmount;
/*     */   protected Date calTime;
/*     */   protected FixedAssets fixedAssets;
/*     */ 
/*     */   public DepreRecord()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DepreRecord(Long in_recordId)
/*     */   {
/*  40 */     setRecordId(in_recordId);
/*     */   }
/*     */ 
/*     */   public FixedAssets getFixedAssets()
/*     */   {
/*  45 */     return this.fixedAssets;
/*     */   }
/*     */ 
/*     */   public void setFixedAssets(FixedAssets in_fixedAssets) {
/*  49 */     this.fixedAssets = in_fixedAssets;
/*     */   }
/*     */ 
/*     */   public Long getRecordId()
/*     */   {
/*  58 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long aValue)
/*     */   {
/*  65 */     this.recordId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getAssetsId()
/*     */   {
/*  72 */     return getFixedAssets() == null ? null : getFixedAssets().getAssetsId();
/*     */   }
/*     */ 
/*     */   public void setAssetsId(Long aValue)
/*     */   {
/*  79 */     if (aValue == null) {
/*  80 */       this.fixedAssets = null;
/*  81 */     } else if (this.fixedAssets == null) {
/*  82 */       this.fixedAssets = new FixedAssets(aValue);
/*  83 */       this.fixedAssets.setVersion(new Integer(0));
/*     */     } else {
/*  85 */       this.fixedAssets.setAssetsId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public BigDecimal getWorkCapacity()
/*     */   {
/*  94 */     return this.workCapacity;
/*     */   }
/*     */ 
/*     */   public void setWorkCapacity(BigDecimal aValue)
/*     */   {
/* 101 */     this.workCapacity = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getDepreAmount()
/*     */   {
/* 109 */     return this.depreAmount;
/*     */   }
/*     */ 
/*     */   public void setDepreAmount(BigDecimal aValue)
/*     */   {
/* 117 */     this.depreAmount = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCalTime()
/*     */   {
/* 125 */     return this.calTime;
/*     */   }
/*     */ 
/*     */   public void setCalTime(Date aValue)
/*     */   {
/* 133 */     this.calTime = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 140 */     if (!(object instanceof DepreRecord)) {
/* 141 */       return false;
/*     */     }
/* 143 */     DepreRecord rhs = (DepreRecord)object;
/* 144 */     return new EqualsBuilder()
/* 145 */       .append(this.recordId, rhs.recordId)
/* 146 */       .append(this.workCapacity, rhs.workCapacity)
/* 147 */       .append(this.depreAmount, rhs.depreAmount)
/* 148 */       .append(this.calTime, rhs.calTime)
/* 149 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 156 */     return new HashCodeBuilder(-82280557, -700257973)
/* 157 */       .append(this.recordId)
/* 158 */       .append(this.workCapacity)
/* 159 */       .append(this.depreAmount)
/* 160 */       .append(this.calTime)
/* 161 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 168 */     return new ToStringBuilder(this)
/* 169 */       .append("recordId", this.recordId)
/* 170 */       .append("workCapacity", this.workCapacity)
/* 171 */       .append("depreAmount", this.depreAmount)
/* 172 */       .append("calTime", this.calTime)
/* 173 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.admin.DepreRecord
 * JD-Core Version:    0.6.0
 */
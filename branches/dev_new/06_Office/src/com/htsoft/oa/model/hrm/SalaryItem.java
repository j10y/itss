/*     */ package com.htsoft.oa.model.hrm;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class SalaryItem extends BaseModel
/*     */ {
/*     */   protected Long salaryItemId;
/*     */   protected String itemName;
/*     */   protected BigDecimal defaultVal;
/*     */ 
/*     */   public SalaryItem()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SalaryItem(Long in_salaryItemId)
/*     */   {
/*  34 */     setSalaryItemId(in_salaryItemId);
/*     */   }
/*     */ 
/*     */   public Long getSalaryItemId()
/*     */   {
/*  44 */     return this.salaryItemId;
/*     */   }
/*     */ 
/*     */   public void setSalaryItemId(Long aValue)
/*     */   {
/*  51 */     this.salaryItemId = aValue;
/*     */   }
/*     */ 
/*     */   public String getItemName()
/*     */   {
/*  59 */     return this.itemName;
/*     */   }
/*     */ 
/*     */   public void setItemName(String aValue)
/*     */   {
/*  67 */     this.itemName = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getDefaultVal()
/*     */   {
/*  75 */     return this.defaultVal;
/*     */   }
/*     */ 
/*     */   public void setDefaultVal(BigDecimal aValue)
/*     */   {
/*  83 */     this.defaultVal = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/*  90 */     if (!(object instanceof SalaryItem)) {
/*  91 */       return false;
/*     */     }
/*  93 */     SalaryItem rhs = (SalaryItem)object;
/*  94 */     return new EqualsBuilder()
/*  95 */       .append(this.salaryItemId, rhs.salaryItemId)
/*  96 */       .append(this.itemName, rhs.itemName)
/*  97 */       .append(this.defaultVal, rhs.defaultVal)
/*  98 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 105 */     return new HashCodeBuilder(-82280557, -700257973)
/* 106 */       .append(this.salaryItemId)
/* 107 */       .append(this.itemName)
/* 108 */       .append(this.defaultVal)
/* 109 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 116 */     return new ToStringBuilder(this)
/* 117 */       .append("salaryItemId", this.salaryItemId)
/* 118 */       .append("itemName", this.itemName)
/* 119 */       .append("defaultVal", this.defaultVal)
/* 120 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.hrm.SalaryItem
 * JD-Core Version:    0.6.0
 */
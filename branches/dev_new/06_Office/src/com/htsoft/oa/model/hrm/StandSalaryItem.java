/*     */ package com.htsoft.oa.model.hrm;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class StandSalaryItem extends BaseModel
/*     */ {
/*     */   protected Long itemId;
/*     */   protected String itemName;
/*     */   protected BigDecimal amount;
/*     */   protected Long salaryItemId;
/*     */   protected StandSalary standSalary;
/*     */ 
/*     */   public StandSalaryItem()
/*     */   {
/*     */   }
/*     */ 
/*     */   public StandSalaryItem(Long in_itemId)
/*     */   {
/*  36 */     setItemId(in_itemId);
/*     */   }
/*     */ 
/*     */   public StandSalary getStandSalary()
/*     */   {
/*  41 */     return this.standSalary;
/*     */   }
/*     */ 
/*     */   public void setStandSalary(StandSalary in_standSalary) {
/*  45 */     this.standSalary = in_standSalary;
/*     */   }
/*     */ 
/*     */   public Long getItemId()
/*     */   {
/*  54 */     return this.itemId;
/*     */   }
/*     */ 
/*     */   public void setItemId(Long aValue)
/*     */   {
/*  61 */     this.itemId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getStandardId()
/*     */   {
/*  68 */     return getStandSalary() == null ? null : getStandSalary().getStandardId();
/*     */   }
/*     */ 
/*     */   public void setStandardId(Long aValue)
/*     */   {
/*  75 */     if (aValue == null) {
/*  76 */       this.standSalary = null;
/*  77 */     } else if (this.standSalary == null) {
/*  78 */       this.standSalary = new StandSalary(aValue);
/*  79 */       this.standSalary.setVersion(new Integer(0));
/*     */     } else {
/*  81 */       this.standSalary.setStandardId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getItemName()
/*     */   {
/*  90 */     return this.itemName;
/*     */   }
/*     */ 
/*     */   public void setItemName(String aValue)
/*     */   {
/*  98 */     this.itemName = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getAmount()
/*     */   {
/* 106 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(BigDecimal aValue)
/*     */   {
/* 114 */     this.amount = aValue;
/*     */   }
/*     */ 
/*     */   public Long getSalaryItemId()
/*     */   {
/* 123 */     return this.salaryItemId;
/*     */   }
/*     */ 
/*     */   public void setSalaryItemId(Long aValue)
/*     */   {
/* 130 */     this.salaryItemId = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 137 */     if (!(object instanceof StandSalaryItem)) {
/* 138 */       return false;
/*     */     }
/* 140 */     StandSalaryItem rhs = (StandSalaryItem)object;
/* 141 */     return new EqualsBuilder()
/* 142 */       .append(this.itemId, rhs.itemId)
/* 143 */       .append(this.itemName, rhs.itemName)
/* 144 */       .append(this.amount, rhs.amount)
/* 145 */       .append(this.salaryItemId, rhs.salaryItemId)
/* 146 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 153 */     return new HashCodeBuilder(-82280557, -700257973)
/* 154 */       .append(this.itemId)
/* 155 */       .append(this.itemName)
/* 156 */       .append(this.amount)
/* 157 */       .append(this.salaryItemId)
/* 158 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 165 */     return new ToStringBuilder(this)
/* 166 */       .append("itemId", this.itemId)
/* 167 */       .append("itemName", this.itemName)
/* 168 */       .append("amount", this.amount)
/* 169 */       .append("salaryItemId", this.salaryItemId)
/* 170 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.hrm.StandSalaryItem
 * JD-Core Version:    0.6.0
 */
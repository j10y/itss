/*     */ package com.xpsoft.oa.model.customer;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ContractConfig extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long configId;
/*     */ 
/*     */   @Expose
/*     */   protected String itemName;
/*     */ 
/*     */   @Expose
/*     */   protected String itemSpec;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal amount;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */   protected Contract contract;
/*     */ 
/*     */   public ContractConfig()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ContractConfig(Long in_configId)
/*     */   {
/*  49 */     setConfigId(in_configId);
/*     */   }
/*     */ 
/*     */   public Contract getContract()
/*     */   {
/*  54 */     return this.contract;
/*     */   }
/*     */ 
/*     */   public void setContract(Contract in_contract) {
/*  58 */     this.contract = in_contract;
/*     */   }
/*     */ 
/*     */   public Long getConfigId()
/*     */   {
/*  67 */     return this.configId;
/*     */   }
/*     */ 
/*     */   public void setConfigId(Long aValue)
/*     */   {
/*  74 */     this.configId = aValue;
/*     */   }
/*     */ 
/*     */   public String getItemName()
/*     */   {
/*  82 */     return this.itemName;
/*     */   }
/*     */ 
/*     */   public void setItemName(String aValue)
/*     */   {
/*  90 */     this.itemName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getContractId()
/*     */   {
/*  97 */     return getContract() == null ? null : getContract().getContractId();
/*     */   }
/*     */ 
/*     */   public void setContractId(Long aValue)
/*     */   {
/* 104 */     if (aValue == null) {
/* 105 */       this.contract = null;
/* 106 */     } else if (this.contract == null) {
/* 107 */       this.contract = new Contract(aValue);
/* 108 */       this.contract.setVersion(new Integer(0));
/*     */     } else {
/* 110 */       this.contract.setContractId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getItemSpec()
/*     */   {
/* 119 */     return this.itemSpec;
/*     */   }
/*     */ 
/*     */   public void setItemSpec(String aValue)
/*     */   {
/* 127 */     this.itemSpec = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getAmount()
/*     */   {
/* 135 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(BigDecimal aValue)
/*     */   {
/* 143 */     this.amount = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 151 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 158 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 166 */     if (!(object instanceof ContractConfig)) {
/* 167 */       return false;
/*     */     }
/* 169 */     ContractConfig rhs = (ContractConfig)object;
/* 170 */     return new EqualsBuilder()
/* 171 */       .append(this.configId, rhs.configId)
/* 172 */       .append(this.itemName, rhs.itemName)
/* 173 */       .append(this.itemSpec, rhs.itemSpec)
/* 174 */       .append(this.amount, rhs.amount)
/* 175 */       .append(this.notes, rhs.notes)
/* 176 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 183 */     return new HashCodeBuilder(-82280557, -700257973)
/* 184 */       .append(this.configId)
/* 185 */       .append(this.itemName)
/* 186 */       .append(this.itemSpec)
/* 187 */       .append(this.amount)
/* 188 */       .append(this.notes)
/* 189 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 196 */     return new ToStringBuilder(this)
/* 197 */       .append("configId", this.configId)
/* 198 */       .append("itemName", this.itemName)
/* 199 */       .append("itemSpec", this.itemSpec)
/* 200 */       .append("amount", this.amount)
/* 201 */       .append("notes", this.notes)
/* 202 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.customer.ContractConfig
 * JD-Core Version:    0.6.0
 */
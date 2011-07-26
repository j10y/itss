/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class CartRepair extends BaseModel
/*     */ {
/*     */   protected Long repairId;
/*     */   protected Date repairDate;
/*     */   protected String reason;
/*     */   protected String executant;
/*     */   protected String notes;
/*     */   protected String repairType;
/*     */   protected BigDecimal fee;
/*     */   protected Car car;
/*     */ 
/*     */   public CartRepair()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CartRepair(Long in_repairId)
/*     */   {
/*  43 */     setRepairId(in_repairId);
/*     */   }
/*     */ 
/*     */   public Car getCar()
/*     */   {
/*  48 */     return this.car;
/*     */   }
/*     */ 
/*     */   public void setCar(Car in_car) {
/*  52 */     this.car = in_car;
/*     */   }
/*     */ 
/*     */   public Long getRepairId()
/*     */   {
/*  61 */     return this.repairId;
/*     */   }
/*     */ 
/*     */   public void setRepairId(Long aValue)
/*     */   {
/*  68 */     this.repairId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getCarId()
/*     */   {
/*  75 */     return getCar() == null ? null : getCar().getCarId();
/*     */   }
/*     */ 
/*     */   public void setCarId(Long aValue)
/*     */   {
/*  82 */     if (aValue == null) {
/*  83 */       this.car = null;
/*  84 */     } else if (this.car == null) {
/*  85 */       this.car = new Car(aValue);
/*  86 */       this.car.setVersion(new Integer(0));
/*     */     } else {
/*  88 */       this.car.setCarId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getRepairDate()
/*     */   {
/*  97 */     return this.repairDate;
/*     */   }
/*     */ 
/*     */   public void setRepairDate(Date aValue)
/*     */   {
/* 105 */     this.repairDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getReason()
/*     */   {
/* 113 */     return this.reason;
/*     */   }
/*     */ 
/*     */   public void setReason(String aValue)
/*     */   {
/* 121 */     this.reason = aValue;
/*     */   }
/*     */ 
/*     */   public String getExecutant()
/*     */   {
/* 129 */     return this.executant;
/*     */   }
/*     */ 
/*     */   public void setExecutant(String aValue)
/*     */   {
/* 137 */     this.executant = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 145 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 152 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public String getRepairType()
/*     */   {
/* 162 */     return this.repairType;
/*     */   }
/*     */ 
/*     */   public void setRepairType(String aValue)
/*     */   {
/* 170 */     this.repairType = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getFee()
/*     */   {
/* 178 */     return this.fee;
/*     */   }
/*     */ 
/*     */   public void setFee(BigDecimal aValue)
/*     */   {
/* 185 */     this.fee = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 192 */     if (!(object instanceof CartRepair)) {
/* 193 */       return false;
/*     */     }
/* 195 */     CartRepair rhs = (CartRepair)object;
/* 196 */     return new EqualsBuilder()
/* 197 */       .append(this.repairId, rhs.repairId)
/* 198 */       .append(this.repairDate, rhs.repairDate)
/* 199 */       .append(this.reason, rhs.reason)
/* 200 */       .append(this.executant, rhs.executant)
/* 201 */       .append(this.notes, rhs.notes)
/* 202 */       .append(this.repairType, rhs.repairType)
/* 203 */       .append(this.fee, rhs.fee)
/* 204 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 211 */     return new HashCodeBuilder(-82280557, -700257973)
/* 212 */       .append(this.repairId)
/* 213 */       .append(this.repairDate)
/* 214 */       .append(this.reason)
/* 215 */       .append(this.executant)
/* 216 */       .append(this.notes)
/* 217 */       .append(this.repairType)
/* 218 */       .append(this.fee)
/* 219 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 226 */     return new ToStringBuilder(this)
/* 227 */       .append("repairId", this.repairId)
/* 228 */       .append("repairDate", this.repairDate)
/* 229 */       .append("reason", this.reason)
/* 230 */       .append("executant", this.executant)
/* 231 */       .append("notes", this.notes)
/* 232 */       .append("repairType", this.repairType)
/* 233 */       .append("fee", this.fee)
/* 234 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.CartRepair
 * JD-Core Version:    0.6.0
 */
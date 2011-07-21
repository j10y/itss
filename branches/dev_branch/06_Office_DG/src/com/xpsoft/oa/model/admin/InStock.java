/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class InStock extends BaseModel
/*     */ {
/*     */   protected Long buyId;
/*     */   protected String providerName;
/*     */   protected String stockNo;
/*     */   protected BigDecimal price;
/*     */   protected Integer inCounts;
/*     */   protected BigDecimal amount;
/*     */   protected Date inDate;
/*     */   protected String buyer;
/*     */   protected OfficeGoods officeGoods;
/*     */ 
/*     */   public InStock()
/*     */   {
/*     */   }
/*     */ 
/*     */   public InStock(Long in_buyId)
/*     */   {
/*  44 */     setBuyId(in_buyId);
/*     */   }
/*     */ 
/*     */   public OfficeGoods getOfficeGoods()
/*     */   {
/*  49 */     return this.officeGoods;
/*     */   }
/*     */ 
/*     */   public void setOfficeGoods(OfficeGoods in_officeGoods) {
/*  53 */     this.officeGoods = in_officeGoods;
/*     */   }
/*     */ 
/*     */   public Long getBuyId()
/*     */   {
/*  62 */     return this.buyId;
/*     */   }
/*     */ 
/*     */   public void setBuyId(Long aValue)
/*     */   {
/*  69 */     this.buyId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getGoodsId()
/*     */   {
/*  76 */     return getOfficeGoods() == null ? null : getOfficeGoods().getGoodsId();
/*     */   }
/*     */ 
/*     */   public void setGoodsId(Long aValue)
/*     */   {
/*  83 */     if (aValue == null) {
/*  84 */       this.officeGoods = null;
/*  85 */     } else if (this.officeGoods == null) {
/*  86 */       this.officeGoods = new OfficeGoods(aValue);
/*  87 */       this.officeGoods.setVersion(new Integer(0));
/*     */     } else {
/*  89 */       this.officeGoods.setGoodsId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getProviderName()
/*     */   {
/*  98 */     return this.providerName;
/*     */   }
/*     */ 
/*     */   public void setProviderName(String aValue)
/*     */   {
/* 105 */     this.providerName = aValue;
/*     */   }
/*     */ 
/*     */   public String getStockNo()
/*     */   {
/* 113 */     return this.stockNo;
/*     */   }
/*     */ 
/*     */   public void setStockNo(String aValue)
/*     */   {
/* 121 */     this.stockNo = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getPrice()
/*     */   {
/* 129 */     return this.price;
/*     */   }
/*     */ 
/*     */   public void setPrice(BigDecimal aValue)
/*     */   {
/* 136 */     this.price = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getInCounts()
/*     */   {
/* 144 */     return this.inCounts;
/*     */   }
/*     */ 
/*     */   public void setInCounts(Integer aValue)
/*     */   {
/* 151 */     this.inCounts = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getAmount()
/*     */   {
/* 159 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public void setAmount(BigDecimal aValue)
/*     */   {
/* 167 */     this.amount = aValue;
/*     */   }
/*     */ 
/*     */   public Date getInDate()
/*     */   {
/* 175 */     return this.inDate;
/*     */   }
/*     */ 
/*     */   public void setInDate(Date aValue)
/*     */   {
/* 183 */     this.inDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getBuyer()
/*     */   {
/* 191 */     return this.buyer;
/*     */   }
/*     */ 
/*     */   public void setBuyer(String aValue)
/*     */   {
/* 198 */     this.buyer = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 205 */     if (!(object instanceof InStock)) {
/* 206 */       return false;
/*     */     }
/* 208 */     InStock rhs = (InStock)object;
/* 209 */     return new EqualsBuilder()
/* 210 */       .append(this.buyId, rhs.buyId)
/* 211 */       .append(this.providerName, rhs.providerName)
/* 212 */       .append(this.stockNo, rhs.stockNo)
/* 213 */       .append(this.price, rhs.price)
/* 214 */       .append(this.inCounts, rhs.inCounts)
/* 215 */       .append(this.amount, rhs.amount)
/* 216 */       .append(this.inDate, rhs.inDate)
/* 217 */       .append(this.buyer, rhs.buyer)
/* 218 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 225 */     return new HashCodeBuilder(-82280557, -700257973)
/* 226 */       .append(this.buyId)
/* 227 */       .append(this.providerName)
/* 228 */       .append(this.stockNo)
/* 229 */       .append(this.price)
/* 230 */       .append(this.inCounts)
/* 231 */       .append(this.amount)
/* 232 */       .append(this.inDate)
/* 233 */       .append(this.buyer)
/* 234 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 241 */     return new ToStringBuilder(this)
/* 242 */       .append("buyId", this.buyId)
/* 243 */       .append("providerName", this.providerName)
/* 244 */       .append("stockNo", this.stockNo)
/* 245 */       .append("price", this.price)
/* 246 */       .append("inCounts", this.inCounts)
/* 247 */       .append("amount", this.amount)
/* 248 */       .append("inDate", this.inDate)
/* 249 */       .append("buyer", this.buyer)
/* 250 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.InStock
 * JD-Core Version:    0.6.0
 */
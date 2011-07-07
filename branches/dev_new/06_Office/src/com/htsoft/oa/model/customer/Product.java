/*     */ package com.htsoft.oa.model.customer;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Product extends BaseModel
/*     */ {
/*     */   protected Long productId;
/*     */   protected String productName;
/*     */   protected String productModel;
/*     */   protected String unit;
/*     */   protected BigDecimal costPrice;
/*     */   protected BigDecimal salesPrice;
/*     */   protected String productDesc;
/*     */   protected Date createtime;
/*     */   protected Date updatetime;
/*     */   protected Provider provider;
/*     */ 
/*     */   public Product()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Product(Long in_productId)
/*     */   {
/*  45 */     setProductId(in_productId);
/*     */   }
/*     */ 
/*     */   public Provider getProvider()
/*     */   {
/*  50 */     return this.provider;
/*     */   }
/*     */ 
/*     */   public void setProvider(Provider in_provider) {
/*  54 */     this.provider = in_provider;
/*     */   }
/*     */ 
/*     */   public Long getProductId()
/*     */   {
/*  63 */     return this.productId;
/*     */   }
/*     */ 
/*     */   public void setProductId(Long aValue)
/*     */   {
/*  70 */     this.productId = aValue;
/*     */   }
/*     */ 
/*     */   public String getProductName()
/*     */   {
/*  78 */     return this.productName;
/*     */   }
/*     */ 
/*     */   public void setProductName(String aValue)
/*     */   {
/*  86 */     this.productName = aValue;
/*     */   }
/*     */ 
/*     */   public String getProductModel()
/*     */   {
/*  94 */     return this.productModel;
/*     */   }
/*     */ 
/*     */   public void setProductModel(String aValue)
/*     */   {
/* 101 */     this.productModel = aValue;
/*     */   }
/*     */ 
/*     */   public String getUnit()
/*     */   {
/* 109 */     return this.unit;
/*     */   }
/*     */ 
/*     */   public void setUnit(String aValue)
/*     */   {
/* 116 */     this.unit = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getCostPrice()
/*     */   {
/* 124 */     return this.costPrice;
/*     */   }
/*     */ 
/*     */   public void setCostPrice(BigDecimal aValue)
/*     */   {
/* 131 */     this.costPrice = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getSalesPrice()
/*     */   {
/* 139 */     return this.salesPrice;
/*     */   }
/*     */ 
/*     */   public void setSalesPrice(BigDecimal aValue)
/*     */   {
/* 146 */     this.salesPrice = aValue;
/*     */   }
/*     */ 
/*     */   public String getProductDesc()
/*     */   {
/* 154 */     return this.productDesc;
/*     */   }
/*     */ 
/*     */   public void setProductDesc(String aValue)
/*     */   {
/* 161 */     this.productDesc = aValue;
/*     */   }
/*     */ 
/*     */   public Long getProviderId()
/*     */   {
/* 168 */     return getProvider() == null ? null : getProvider().getProviderId();
/*     */   }
/*     */ 
/*     */   public void setProviderId(Long aValue)
/*     */   {
/* 175 */     if (aValue == null) {
/* 176 */       this.provider = null;
/* 177 */     } else if (this.provider == null) {
/* 178 */       this.provider = new Provider(aValue);
/* 179 */       this.provider.setVersion(new Integer(0));
/*     */     } else {
/* 181 */       this.provider.setProviderId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 190 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 198 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getUpdatetime() {
/* 202 */     return this.updatetime;
/*     */   }
/*     */ 
/*     */   public void setUpdatetime(Date updatetime) {
/* 206 */     this.updatetime = updatetime;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 213 */     if (!(object instanceof Product)) {
/* 214 */       return false;
/*     */     }
/* 216 */     Product rhs = (Product)object;
/* 217 */     return new EqualsBuilder()
/* 218 */       .append(this.productId, rhs.productId)
/* 219 */       .append(this.productName, rhs.productName)
/* 220 */       .append(this.productModel, rhs.productModel)
/* 221 */       .append(this.unit, rhs.unit)
/* 222 */       .append(this.costPrice, rhs.costPrice)
/* 223 */       .append(this.salesPrice, rhs.salesPrice)
/* 224 */       .append(this.productDesc, rhs.productDesc)
/* 225 */       .append(this.createtime, rhs.createtime)
/* 226 */       .append(this.updatetime, rhs.updatetime)
/* 227 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 234 */     return new HashCodeBuilder(-82280557, -700257973)
/* 235 */       .append(this.productId)
/* 236 */       .append(this.productName)
/* 237 */       .append(this.productModel)
/* 238 */       .append(this.unit)
/* 239 */       .append(this.costPrice)
/* 240 */       .append(this.salesPrice)
/* 241 */       .append(this.productDesc)
/* 242 */       .append(this.createtime)
/* 243 */       .append(this.updatetime)
/* 244 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 251 */     return new ToStringBuilder(this)
/* 252 */       .append("productId", this.productId)
/* 253 */       .append("productName", this.productName)
/* 254 */       .append("productModel", this.productModel)
/* 255 */       .append("unit", this.unit)
/* 256 */       .append("costPrice", this.costPrice)
/* 257 */       .append("salesPrice", this.salesPrice)
/* 258 */       .append("productDesc", this.productDesc)
/* 259 */       .append("createtime", this.createtime)
/* 260 */       .append("updatetime", this.updatetime)
/* 261 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.customer.Product
 * JD-Core Version:    0.6.0
 */
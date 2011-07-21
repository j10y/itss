/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class OfficeGoods extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long goodsId;
/*     */ 
/*     */   @Expose
/*     */   protected String goodsName;
/*     */ 
/*     */   @Expose
/*     */   protected String goodsNo;
/*     */ 
/*     */   @Expose
/*     */   protected String specifications;
/*     */ 
/*     */   @Expose
/*     */   protected String unit;
/*     */ 
/*     */   @Expose
/*     */   protected Short isWarning;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */ 
/*     */   @Expose
/*     */   protected Integer stockCounts;
/*     */ 
/*     */   @Expose
/*     */   protected Integer warnCounts;
/*     */ 
/*     */   @Expose
/*     */   protected OfficeGoodsType officeGoodsType;
/*  43 */   protected Set goodsApplys = new HashSet();
/*  44 */   protected Set inStocks = new HashSet();
/*     */ 
/*  46 */   public Set getGoodsApplys() { return this.goodsApplys; }
/*     */ 
/*     */   public void setGoodsApplys(Set goodsApplys)
/*     */   {
/*  50 */     this.goodsApplys = goodsApplys;
/*     */   }
/*     */ 
/*     */   public Set getInStocks() {
/*  54 */     return this.inStocks;
/*     */   }
/*     */ 
/*     */   public void setInStocks(Set inStocks) {
/*  58 */     this.inStocks = inStocks;
/*     */   }
/*     */ 
/*     */   public OfficeGoods()
/*     */   {
/*     */   }
/*     */ 
/*     */   public OfficeGoods(Long in_goodsId)
/*     */   {
/*  76 */     setGoodsId(in_goodsId);
/*     */   }
/*     */ 
/*     */   public OfficeGoodsType getOfficeGoodsType()
/*     */   {
/*  82 */     return this.officeGoodsType;
/*     */   }
/*     */ 
/*     */   public void setOfficeGoodsType(OfficeGoodsType in_officeGoodsType) {
/*  86 */     this.officeGoodsType = in_officeGoodsType;
/*     */   }
/*     */ 
/*     */   public Integer getWarnCounts()
/*     */   {
/* 105 */     return this.warnCounts;
/*     */   }
/*     */ 
/*     */   public void setWarnCounts(Integer warnCounts) {
/* 109 */     this.warnCounts = warnCounts;
/*     */   }
/*     */ 
/*     */   public Long getGoodsId()
/*     */   {
/* 117 */     return this.goodsId;
/*     */   }
/*     */ 
/*     */   public void setGoodsId(Long aValue)
/*     */   {
/* 124 */     this.goodsId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/* 131 */     return getOfficeGoodsType() == null ? null : getOfficeGoodsType().getTypeId();
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/* 138 */     if (aValue == null) {
/* 139 */       this.officeGoodsType = null;
/* 140 */     } else if (this.officeGoodsType == null) {
/* 141 */       this.officeGoodsType = new OfficeGoodsType(aValue);
/* 142 */       this.officeGoodsType.setVersion(new Integer(0));
/*     */     } else {
/* 144 */       this.officeGoodsType.setTypeId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getGoodsName()
/*     */   {
/* 153 */     return this.goodsName;
/*     */   }
/*     */ 
/*     */   public void setGoodsName(String aValue)
/*     */   {
/* 161 */     this.goodsName = aValue;
/*     */   }
/*     */ 
/*     */   public String getGoodsNo()
/*     */   {
/* 169 */     return this.goodsNo;
/*     */   }
/*     */ 
/*     */   public void setGoodsNo(String aValue)
/*     */   {
/* 177 */     this.goodsNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getSpecifications()
/*     */   {
/* 185 */     return this.specifications;
/*     */   }
/*     */ 
/*     */   public void setSpecifications(String aValue)
/*     */   {
/* 193 */     this.specifications = aValue;
/*     */   }
/*     */ 
/*     */   public String getUnit()
/*     */   {
/* 201 */     return this.unit;
/*     */   }
/*     */ 
/*     */   public void setUnit(String aValue)
/*     */   {
/* 209 */     this.unit = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsWarning()
/*     */   {
/* 217 */     return this.isWarning;
/*     */   }
/*     */ 
/*     */   public void setIsWarning(Short aValue)
/*     */   {
/* 225 */     this.isWarning = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 233 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 240 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getStockCounts()
/*     */   {
/* 248 */     return this.stockCounts;
/*     */   }
/*     */ 
/*     */   public void setStockCounts(Integer aValue)
/*     */   {
/* 256 */     this.stockCounts = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 263 */     if (!(object instanceof OfficeGoods)) {
/* 264 */       return false;
/*     */     }
/* 266 */     OfficeGoods rhs = (OfficeGoods)object;
/* 267 */     return new EqualsBuilder()
/* 268 */       .append(this.goodsId, rhs.goodsId)
/* 269 */       .append(this.goodsName, rhs.goodsName)
/* 270 */       .append(this.goodsNo, rhs.goodsNo)
/* 271 */       .append(this.specifications, rhs.specifications)
/* 272 */       .append(this.unit, rhs.unit)
/* 273 */       .append(this.isWarning, rhs.isWarning)
/* 274 */       .append(this.notes, rhs.notes)
/* 275 */       .append(this.stockCounts, rhs.stockCounts)
/* 276 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 283 */     return new HashCodeBuilder(-82280557, -700257973)
/* 284 */       .append(this.goodsId)
/* 285 */       .append(this.goodsName)
/* 286 */       .append(this.goodsNo)
/* 287 */       .append(this.specifications)
/* 288 */       .append(this.unit)
/* 289 */       .append(this.isWarning)
/* 290 */       .append(this.notes)
/* 291 */       .append(this.stockCounts)
/* 292 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 299 */     return new ToStringBuilder(this)
/* 300 */       .append("goodsId", this.goodsId)
/* 301 */       .append("goodsName", this.goodsName)
/* 302 */       .append("goodsNo", this.goodsNo)
/* 303 */       .append("specifications", this.specifications)
/* 304 */       .append("unit", this.unit)
/* 305 */       .append("isWarning", this.isWarning)
/* 306 */       .append("notes", this.notes)
/* 307 */       .append("stockCounts", this.stockCounts)
/* 308 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.OfficeGoods
 * JD-Core Version:    0.6.0
 */
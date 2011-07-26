/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Region extends BaseModel
/*     */ {
/*     */   protected Long regionId;
/*     */   protected String regionName;
/*     */   protected Short regionType;
/*     */   protected Long parentId;
/*     */ 
/*     */   public Region()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Region(Long in_regionId)
/*     */   {
/*  39 */     setRegionId(in_regionId);
/*     */   }
/*     */ 
/*     */   public Long getRegionId()
/*     */   {
/*  49 */     return this.regionId;
/*     */   }
/*     */ 
/*     */   public void setRegionId(Long aValue)
/*     */   {
/*  56 */     this.regionId = aValue;
/*     */   }
/*     */ 
/*     */   public String getRegionName()
/*     */   {
/*  64 */     return this.regionName;
/*     */   }
/*     */ 
/*     */   public void setRegionName(String aValue)
/*     */   {
/*  72 */     this.regionName = aValue;
/*     */   }
/*     */ 
/*     */   public Short getRegionType()
/*     */   {
/*  82 */     return this.regionType;
/*     */   }
/*     */ 
/*     */   public void setRegionType(Short aValue)
/*     */   {
/*  90 */     this.regionType = aValue;
/*     */   }
/*     */ 
/*     */   public Long getParentId()
/*     */   {
/*  98 */     return this.parentId;
/*     */   }
/*     */ 
/*     */   public void setParentId(Long aValue)
/*     */   {
/* 105 */     this.parentId = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 112 */     if (!(object instanceof Region)) {
/* 113 */       return false;
/*     */     }
/* 115 */     Region rhs = (Region)object;
/* 116 */     return new EqualsBuilder()
/* 117 */       .append(this.regionId, rhs.regionId)
/* 118 */       .append(this.regionName, rhs.regionName)
/* 119 */       .append(this.regionType, rhs.regionType)
/* 120 */       .append(this.parentId, rhs.parentId)
/* 121 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 128 */     return new HashCodeBuilder(-82280557, -700257973)
/* 129 */       .append(this.regionId)
/* 130 */       .append(this.regionName)
/* 131 */       .append(this.regionType)
/* 132 */       .append(this.parentId)
/* 133 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 140 */     return new ToStringBuilder(this)
/* 141 */       .append("regionId", this.regionId)
/* 142 */       .append("regionName", this.regionName)
/* 143 */       .append("regionType", this.regionType)
/* 144 */       .append("parentId", this.parentId)
/* 145 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.Region
 * JD-Core Version:    0.6.0
 */
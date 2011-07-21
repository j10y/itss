/*     */ package com.xpsoft.oa.model.admin;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class AssetsType extends BaseModel
/*     */ {
/*     */   protected Long assetsTypeId;
/*     */   protected String typeName;
/*     */ 
/*     */   public AssetsType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AssetsType(Long in_assetsTypeId)
/*     */   {
/*  38 */     setAssetsTypeId(in_assetsTypeId);
/*     */   }
/*     */ 
/*     */   public Long getAssetsTypeId()
/*     */   {
/*  56 */     return this.assetsTypeId;
/*     */   }
/*     */ 
/*     */   public void setAssetsTypeId(Long aValue)
/*     */   {
/*  63 */     this.assetsTypeId = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/*  71 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String aValue)
/*     */   {
/*  79 */     this.typeName = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/*  86 */     if (!(object instanceof AssetsType)) {
/*  87 */       return false;
/*     */     }
/*  89 */     AssetsType rhs = (AssetsType)object;
/*  90 */     return new EqualsBuilder()
/*  91 */       .append(this.assetsTypeId, rhs.assetsTypeId)
/*  92 */       .append(this.typeName, rhs.typeName)
/*  93 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 100 */     return new HashCodeBuilder(-82280557, -700257973)
/* 101 */       .append(this.assetsTypeId)
/* 102 */       .append(this.typeName)
/* 103 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 110 */     return new ToStringBuilder(this)
/* 111 */       .append("assetsTypeId", this.assetsTypeId)
/* 112 */       .append("typeName", this.typeName)
/* 113 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.admin.AssetsType
 * JD-Core Version:    0.6.0
 */
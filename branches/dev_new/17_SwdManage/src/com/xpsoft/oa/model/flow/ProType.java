/*     */ package com.xpsoft.oa.model.flow;
/*     */ 
/*     */ import flexjson.JSON;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ProType
/*     */ {
/*     */   protected Long typeId;
/*     */   protected String typeName;
/*     */ 
/*     */   public ProType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ProType(Long in_typeId)
/*     */   {
/*  40 */     setTypeId(in_typeId);
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  58 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/*  65 */     this.typeId = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/*  73 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String aValue)
/*     */   {
/*  81 */     this.typeName = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/*  88 */     if (!(object instanceof ProType)) {
/*  89 */       return false;
/*     */     }
/*  91 */     ProType rhs = (ProType)object;
/*  92 */     return new EqualsBuilder()
/*  93 */       .append(this.typeId, rhs.typeId)
/*  94 */       .append(this.typeName, rhs.typeName)
/*  95 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 102 */     return new HashCodeBuilder(-82280557, -700257973)
/* 103 */       .append(this.typeId)
/* 104 */       .append(this.typeName)
/* 105 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return new ToStringBuilder(this)
/* 113 */       .append("typeId", this.typeId)
/* 114 */       .append("typeName", this.typeName)
/* 115 */       .toString();
/*     */   }
/*     */ 
/*     */   @JSON(include=false)
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 123 */     return "typeId";
/*     */   }
/*     */ 
/*     */   @JSON
/*     */   public Long getId()
/*     */   {
/* 131 */     return this.typeId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.flow.ProType
 * JD-Core Version:    0.6.0
 */
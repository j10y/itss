/*     */ package com.xpsoft.oa.model.task;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class PlanType extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long typeId;
/*     */ 
/*     */   @Expose
/*     */   protected String typeName;
/*     */ 
/*     */   public PlanType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PlanType(Long in_typeId)
/*     */   {
/*  42 */     setTypeId(in_typeId);
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  60 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/*  67 */     this.typeId = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/*  75 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String aValue)
/*     */   {
/*  82 */     this.typeName = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/*  89 */     if (!(object instanceof PlanType)) {
/*  90 */       return false;
/*     */     }
/*  92 */     PlanType rhs = (PlanType)object;
/*  93 */     return new EqualsBuilder()
/*  94 */       .append(this.typeId, rhs.typeId)
/*  95 */       .append(this.typeName, rhs.typeName)
/*  96 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 103 */     return new HashCodeBuilder(-82280557, -700257973)
/* 104 */       .append(this.typeId)
/* 105 */       .append(this.typeName)
/* 106 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 113 */     return new ToStringBuilder(this)
/* 114 */       .append("typeId", this.typeId)
/* 115 */       .append("typeName", this.typeName)
/* 116 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.task.PlanType
 * JD-Core Version:    0.6.0
 */
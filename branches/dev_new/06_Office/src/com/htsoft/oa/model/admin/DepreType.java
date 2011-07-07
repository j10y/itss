/*     */ package com.htsoft.oa.model.admin;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class DepreType extends BaseModel
/*     */ {
/*     */   protected Long depreTypeId;
/*     */   protected String typeName;
/*     */   protected Integer deprePeriod;
/*     */   protected String typeDesc;
/*     */   protected Short calMethod;
/*     */ 
/*     */   public DepreType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DepreType(Long in_depreTypeId)
/*     */   {
/*  42 */     setDepreTypeId(in_depreTypeId);
/*     */   }
/*     */ 
/*     */   public Long getDepreTypeId()
/*     */   {
/*  60 */     return this.depreTypeId;
/*     */   }
/*     */ 
/*     */   public void setDepreTypeId(Long aValue)
/*     */   {
/*  67 */     this.depreTypeId = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/*  75 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String aValue)
/*     */   {
/*  83 */     this.typeName = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDeprePeriod()
/*     */   {
/*  93 */     return this.deprePeriod;
/*     */   }
/*     */ 
/*     */   public void setDeprePeriod(Integer aValue)
/*     */   {
/* 101 */     this.deprePeriod = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeDesc()
/*     */   {
/* 109 */     return this.typeDesc;
/*     */   }
/*     */ 
/*     */   public void setTypeDesc(String aValue)
/*     */   {
/* 116 */     this.typeDesc = aValue;
/*     */   }
/*     */ 
/*     */   public Short getCalMethod()
/*     */   {
/* 128 */     return this.calMethod;
/*     */   }
/*     */ 
/*     */   public void setCalMethod(Short aValue)
/*     */   {
/* 136 */     this.calMethod = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 143 */     if (!(object instanceof DepreType)) {
/* 144 */       return false;
/*     */     }
/* 146 */     DepreType rhs = (DepreType)object;
/* 147 */     return new EqualsBuilder()
/* 148 */       .append(this.depreTypeId, rhs.depreTypeId)
/* 149 */       .append(this.typeName, rhs.typeName)
/* 150 */       .append(this.deprePeriod, rhs.deprePeriod)
/* 151 */       .append(this.typeDesc, rhs.typeDesc)
/* 152 */       .append(this.calMethod, rhs.calMethod)
/* 153 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 160 */     return new HashCodeBuilder(-82280557, -700257973)
/* 161 */       .append(this.depreTypeId)
/* 162 */       .append(this.typeName)
/* 163 */       .append(this.deprePeriod)
/* 164 */       .append(this.typeDesc)
/* 165 */       .append(this.calMethod)
/* 166 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 173 */     return new ToStringBuilder(this)
/* 174 */       .append("depreTypeId", this.depreTypeId)
/* 175 */       .append("typeName", this.typeName)
/* 176 */       .append("deprePeriod", this.deprePeriod)
/* 177 */       .append("typeDesc", this.typeDesc)
/* 178 */       .append("calMethod", this.calMethod)
/* 179 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.admin.DepreType
 * JD-Core Version:    0.6.0
 */
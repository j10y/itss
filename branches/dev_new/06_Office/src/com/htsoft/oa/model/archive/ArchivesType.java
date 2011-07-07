/*     */ package com.htsoft.oa.model.archive;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ArchivesType extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long typeId;
/*     */ 
/*     */   @Expose
/*     */   protected String typeName;
/*     */ 
/*     */   @Expose
/*     */   protected String typeDesc;
/*  28 */   protected Set archTemplates = new HashSet();
/*     */ 
/*     */   public ArchivesType()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArchivesType(Long in_typeId)
/*     */   {
/*  43 */     setTypeId(in_typeId);
/*     */   }
/*     */ 
/*     */   public Set getArchTemplates()
/*     */   {
/*  48 */     return this.archTemplates;
/*     */   }
/*     */ 
/*     */   public void setArchTemplates(Set in_archTemplates) {
/*  52 */     this.archTemplates = in_archTemplates;
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  61 */     return this.typeId;
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/*  68 */     this.typeId = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeName()
/*     */   {
/*  76 */     return this.typeName;
/*     */   }
/*     */ 
/*     */   public void setTypeName(String aValue)
/*     */   {
/*  84 */     this.typeName = aValue;
/*     */   }
/*     */ 
/*     */   public String getTypeDesc()
/*     */   {
/*  92 */     return this.typeDesc;
/*     */   }
/*     */ 
/*     */   public void setTypeDesc(String aValue)
/*     */   {
/*  99 */     this.typeDesc = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 106 */     if (!(object instanceof ArchivesType)) {
/* 107 */       return false;
/*     */     }
/* 109 */     ArchivesType rhs = (ArchivesType)object;
/* 110 */     return new EqualsBuilder()
/* 111 */       .append(this.typeId, rhs.typeId)
/* 112 */       .append(this.typeName, rhs.typeName)
/* 113 */       .append(this.typeDesc, rhs.typeDesc)
/* 114 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 121 */     return new HashCodeBuilder(-82280557, -700257973)
/* 122 */       .append(this.typeId)
/* 123 */       .append(this.typeName)
/* 124 */       .append(this.typeDesc)
/* 125 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 132 */     return new ToStringBuilder(this)
/* 133 */       .append("typeId", this.typeId)
/* 134 */       .append("typeName", this.typeName)
/* 135 */       .append("typeDesc", this.typeDesc)
/* 136 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.archive.ArchivesType
 * JD-Core Version:    0.6.0
 */
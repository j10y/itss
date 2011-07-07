/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Dictionary extends BaseModel
/*     */ {
/*     */   protected Long dicId;
/*     */   protected String itemName;
/*     */   protected String itemValue;
/*     */   protected String descp;
/*     */ 
/*     */   public Dictionary()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Dictionary(Long in_dicId)
/*     */   {
/*  35 */     setDicId(in_dicId);
/*     */   }
/*     */ 
/*     */   public Long getDicId()
/*     */   {
/*  45 */     return this.dicId;
/*     */   }
/*     */ 
/*     */   public void setDicId(Long aValue)
/*     */   {
/*  52 */     this.dicId = aValue;
/*     */   }
/*     */ 
/*     */   public String getItemName()
/*     */   {
/*  60 */     return this.itemName;
/*     */   }
/*     */ 
/*     */   public void setItemName(String aValue)
/*     */   {
/*  68 */     this.itemName = aValue;
/*     */   }
/*     */ 
/*     */   public String getItemValue()
/*     */   {
/*  76 */     return this.itemValue;
/*     */   }
/*     */ 
/*     */   public void setItemValue(String aValue)
/*     */   {
/*  84 */     this.itemValue = aValue;
/*     */   }
/*     */ 
/*     */   public String getDescp()
/*     */   {
/*  92 */     return this.descp;
/*     */   }
/*     */ 
/*     */   public void setDescp(String aValue)
/*     */   {
/*  99 */     this.descp = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 106 */     if (!(object instanceof Dictionary)) {
/* 107 */       return false;
/*     */     }
/* 109 */     Dictionary rhs = (Dictionary)object;
/* 110 */     return new EqualsBuilder()
/* 111 */       .append(this.dicId, rhs.dicId)
/* 112 */       .append(this.itemName, rhs.itemName)
/* 113 */       .append(this.itemValue, rhs.itemValue)
/* 114 */       .append(this.descp, rhs.descp)
/* 115 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 122 */     return new HashCodeBuilder(-82280557, -700257973)
/* 123 */       .append(this.dicId)
/* 124 */       .append(this.itemName)
/* 125 */       .append(this.itemValue)
/* 126 */       .append(this.descp)
/* 127 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 134 */     return new ToStringBuilder(this)
/* 135 */       .append("dicId", this.dicId)
/* 136 */       .append("itemName", this.itemName)
/* 137 */       .append("itemValue", this.itemValue)
/* 138 */       .append("descp", this.descp)
/* 139 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.Dictionary
 * JD-Core Version:    0.6.0
 */
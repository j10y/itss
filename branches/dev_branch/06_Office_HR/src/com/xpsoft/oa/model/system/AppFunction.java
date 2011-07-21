/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class AppFunction extends BaseModel
/*     */ {
/*     */   protected Long functionId;
/*     */   protected String funKey;
/*     */   protected String funName;
/*  24 */   protected Set<FunUrl> funUrls = new HashSet();
/*     */ 
/*     */   public AppFunction()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AppFunction(String funKey, String funName)
/*     */   {
/*  36 */     this.funKey = funKey;
/*  37 */     this.funName = funName;
/*     */   }
/*     */ 
/*     */   public AppFunction(Long in_functionId)
/*     */   {
/*  48 */     setFunctionId(in_functionId);
/*     */   }
/*     */ 
/*     */   public Set<FunUrl> getFunUrls()
/*     */   {
/*  53 */     return this.funUrls;
/*     */   }
/*     */ 
/*     */   public void setFunUrls(Set in_funUrls) {
/*  57 */     this.funUrls = in_funUrls;
/*     */   }
/*     */ 
/*     */   public Long getFunctionId()
/*     */   {
/*  65 */     return this.functionId;
/*     */   }
/*     */ 
/*     */   public void setFunctionId(Long aValue)
/*     */   {
/*  72 */     this.functionId = aValue;
/*     */   }
/*     */ 
/*     */   public String getFunKey()
/*     */   {
/*  80 */     return this.funKey;
/*     */   }
/*     */ 
/*     */   public void setFunKey(String aValue)
/*     */   {
/*  88 */     this.funKey = aValue;
/*     */   }
/*     */ 
/*     */   public String getFunName()
/*     */   {
/*  96 */     return this.funName;
/*     */   }
/*     */ 
/*     */   public void setFunName(String aValue)
/*     */   {
/* 104 */     this.funName = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 111 */     if (!(object instanceof AppFunction)) {
/* 112 */       return false;
/*     */     }
/* 114 */     AppFunction rhs = (AppFunction)object;
/* 115 */     return new EqualsBuilder()
/* 116 */       .append(this.functionId, rhs.functionId)
/* 117 */       .append(this.funKey, rhs.funKey)
/* 118 */       .append(this.funName, rhs.funName)
/* 119 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 126 */     return new HashCodeBuilder(-82280557, -700257973)
/* 127 */       .append(this.functionId)
/* 128 */       .append(this.funKey)
/* 129 */       .append(this.funName)
/* 130 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 137 */     return new ToStringBuilder(this)
/* 138 */       .append("functionId", this.functionId)
/* 139 */       .append("funKey", this.funKey)
/* 140 */       .append("funName", this.funName)
/* 141 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.AppFunction
 * JD-Core Version:    0.6.0
 */
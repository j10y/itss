/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class FunUrl extends BaseModel
/*     */ {
/*     */   protected Long urlId;
/*     */   protected String urlPath;
/*     */   protected AppFunction appFunction;
/*     */ 
/*     */   public FunUrl()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FunUrl(String urlPath)
/*     */   {
/*  33 */     this.urlPath = urlPath;
/*     */   }
/*     */ 
/*     */   public FunUrl(Long in_urlId)
/*     */   {
/*  42 */     setUrlId(in_urlId);
/*     */   }
/*     */ 
/*     */   public AppFunction getAppFunction()
/*     */   {
/*  47 */     return this.appFunction;
/*     */   }
/*     */ 
/*     */   public void setAppFunction(AppFunction in_appFunction) {
/*  51 */     this.appFunction = in_appFunction;
/*     */   }
/*     */ 
/*     */   public Long getUrlId()
/*     */   {
/*  60 */     return this.urlId;
/*     */   }
/*     */ 
/*     */   public void setUrlId(Long aValue)
/*     */   {
/*  67 */     this.urlId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getFunctionId()
/*     */   {
/*  74 */     return getAppFunction() == null ? null : getAppFunction().getFunctionId();
/*     */   }
/*     */ 
/*     */   public void setFunctionId(Long aValue)
/*     */   {
/*  81 */     if (aValue == null) {
/*  82 */       this.appFunction = null;
/*  83 */     } else if (this.appFunction == null) {
/*  84 */       this.appFunction = new AppFunction(aValue);
/*  85 */       this.appFunction.setVersion(new Integer(0));
/*     */     } else {
/*  87 */       this.appFunction.setFunctionId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getUrlPath()
/*     */   {
/*  96 */     return this.urlPath;
/*     */   }
/*     */ 
/*     */   public void setUrlPath(String aValue)
/*     */   {
/* 104 */     this.urlPath = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 111 */     if (!(object instanceof FunUrl)) {
/* 112 */       return false;
/*     */     }
/* 114 */     FunUrl rhs = (FunUrl)object;
/* 115 */     return new EqualsBuilder()
/* 116 */       .append(this.urlId, rhs.urlId)
/* 117 */       .append(this.urlPath, rhs.urlPath)
/* 118 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 125 */     return new HashCodeBuilder(-82280557, -700257973)
/* 126 */       .append(this.urlId)
/* 127 */       .append(this.urlPath)
/* 128 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 135 */     return new ToStringBuilder(this)
/* 136 */       .append("urlId", this.urlId)
/* 137 */       .append("urlPath", this.urlPath)
/* 138 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.FunUrl
 * JD-Core Version:    0.6.0
 */
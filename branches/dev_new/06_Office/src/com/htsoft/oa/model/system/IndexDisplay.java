/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class IndexDisplay extends BaseModel
/*     */ {
/*     */   protected Long indexId;
/*     */   protected String portalId;
/*     */   protected Integer colNum;
/*     */   protected Integer rowNum;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public IndexDisplay()
/*     */   {
/*     */   }
/*     */ 
/*     */   public IndexDisplay(Long in_indexId)
/*     */   {
/*  39 */     setIndexId(in_indexId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  44 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  48 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getIndexId()
/*     */   {
/*  57 */     return this.indexId;
/*     */   }
/*     */ 
/*     */   public void setIndexId(Long aValue)
/*     */   {
/*  64 */     this.indexId = aValue;
/*     */   }
/*     */ 
/*     */   public String getPortalId()
/*     */   {
/*  72 */     return this.portalId;
/*     */   }
/*     */ 
/*     */   public void setPortalId(String aValue)
/*     */   {
/*  80 */     this.portalId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  87 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/*  94 */     if (aValue == null) {
/*  95 */       this.appUser = null;
/*  96 */     } else if (this.appUser == null) {
/*  97 */       this.appUser = new AppUser(aValue);
/*  98 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 100 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getColNum()
/*     */   {
/* 109 */     return this.colNum;
/*     */   }
/*     */ 
/*     */   public void setColNum(Integer aValue)
/*     */   {
/* 117 */     this.colNum = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getRowNum()
/*     */   {
/* 125 */     return this.rowNum;
/*     */   }
/*     */ 
/*     */   public void setRowNum(Integer aValue)
/*     */   {
/* 133 */     this.rowNum = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 140 */     if (!(object instanceof IndexDisplay)) {
/* 141 */       return false;
/*     */     }
/* 143 */     IndexDisplay rhs = (IndexDisplay)object;
/* 144 */     return new EqualsBuilder()
/* 145 */       .append(this.indexId, rhs.indexId)
/* 146 */       .append(this.portalId, rhs.portalId)
/* 147 */       .append(this.colNum, rhs.colNum)
/* 148 */       .append(this.rowNum, rhs.rowNum)
/* 149 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 156 */     return new HashCodeBuilder(-82280557, -700257973)
/* 157 */       .append(this.indexId)
/* 158 */       .append(this.portalId)
/* 159 */       .append(this.colNum)
/* 160 */       .append(this.rowNum)
/* 161 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 168 */     return new ToStringBuilder(this)
/* 169 */       .append("indexId", this.indexId)
/* 170 */       .append("portalId", this.portalId)
/* 171 */       .append("colNum", this.colNum)
/* 172 */       .append("rowNum", this.rowNum)
/* 173 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.IndexDisplay
 * JD-Core Version:    0.6.0
 */
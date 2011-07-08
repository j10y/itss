/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class UserSub extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long subId;
/*     */ 
/*     */   @Expose
/*     */   protected AppUser subAppUser;
/*     */ 
/*     */   @Expose
/*     */   protected Long userId;
/*     */ 
/*     */   public UserSub()
/*     */   {
/*     */   }
/*     */ 
/*     */   public UserSub(Long in_subId)
/*     */   {
/*  46 */     setSubId(in_subId);
/*     */   }
/*     */ 
/*     */   public AppUser getSubAppUser()
/*     */   {
/*  64 */     return this.subAppUser;
/*     */   }
/*     */ 
/*     */   public void setSubAppUser(AppUser subAppUser) {
/*  68 */     this.subAppUser = subAppUser;
/*     */   }
/*     */ 
/*     */   public Long getSubId()
/*     */   {
/*  76 */     return this.subId;
/*     */   }
/*     */ 
/*     */   public void setSubId(Long aValue)
/*     */   {
/*  83 */     this.subId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getSubUserId()
/*     */   {
/*  90 */     return getSubAppUser() == null ? null : this.subAppUser.getUserId();
/*     */   }
/*     */ 
/*     */   public void setSubUserId(Long aValue)
/*     */   {
/*  97 */     if (aValue == null) {
/*  98 */       this.subAppUser = null;
/*  99 */     } else if (this.subAppUser == null) {
/* 100 */       this.subAppUser = new AppUser(aValue);
/* 101 */       this.subAppUser.setVersion(new Integer(0));
/*     */     } else {
/* 103 */       this.subAppUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 113 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 129 */     this.userId = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 136 */     if (!(object instanceof UserSub)) {
/* 137 */       return false;
/*     */     }
/* 139 */     UserSub rhs = (UserSub)object;
/* 140 */     return new EqualsBuilder()
/* 141 */       .append(this.subId, rhs.subId)
/* 142 */       .append(this.userId, rhs.userId)
/* 143 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 150 */     return new HashCodeBuilder(-82280557, -700257973)
/* 151 */       .append(this.subId)
/* 152 */       .append(this.userId)
/* 153 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 160 */     return new ToStringBuilder(this)
/* 161 */       .append("subId", this.subId)
/* 162 */       .append("userId", this.userId)
/* 163 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.UserSub
 * JD-Core Version:    0.6.0
 */
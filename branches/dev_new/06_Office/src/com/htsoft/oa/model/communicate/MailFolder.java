/*     */ package com.htsoft.oa.model.communicate;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class MailFolder extends BaseModel
/*     */ {
/*     */   protected Long folderId;
/*     */   protected String folderName;
/*     */   protected Long parentId;
/*     */   protected Integer depLevel;
/*     */   protected String path;
/*     */   protected Short isPublic;
/*     */   protected Short folderType;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public MailFolder()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MailFolder(Long in_folderId)
/*     */   {
/*  45 */     setFolderId(in_folderId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  50 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  54 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getFolderId()
/*     */   {
/*  71 */     return this.folderId;
/*     */   }
/*     */ 
/*     */   public void setFolderId(Long aValue)
/*     */   {
/*  78 */     this.folderId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  85 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/*  92 */     if (aValue == null) {
/*  93 */       this.appUser = null;
/*  94 */     } else if (this.appUser == null) {
/*  95 */       this.appUser = new AppUser(aValue);
/*  96 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/*  98 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFolderName()
/*     */   {
/* 107 */     return this.folderName;
/*     */   }
/*     */ 
/*     */   public void setFolderName(String aValue)
/*     */   {
/* 115 */     this.folderName = aValue;
/*     */   }
/*     */ 
/*     */   public Long getParentId()
/*     */   {
/* 123 */     return this.parentId;
/*     */   }
/*     */ 
/*     */   public void setParentId(Long aValue)
/*     */   {
/* 130 */     this.parentId = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getDepLevel()
/*     */   {
/* 138 */     return this.depLevel;
/*     */   }
/*     */ 
/*     */   public void setDepLevel(Integer aValue)
/*     */   {
/* 146 */     this.depLevel = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsPublic()
/*     */   {
/* 155 */     return this.isPublic;
/*     */   }
/*     */ 
/*     */   public void setIsPublic(Short aValue)
/*     */   {
/* 163 */     this.isPublic = aValue;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 169 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String path) {
/* 173 */     this.path = path;
/*     */   }
/*     */ 
/*     */   public Short getFolderType()
/*     */   {
/* 186 */     return this.folderType;
/*     */   }
/*     */ 
/*     */   public void setFolderType(Short aValue)
/*     */   {
/* 194 */     this.folderType = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 201 */     if (!(object instanceof MailFolder)) {
/* 202 */       return false;
/*     */     }
/* 204 */     MailFolder rhs = (MailFolder)object;
/* 205 */     return new EqualsBuilder()
/* 206 */       .append(this.folderId, rhs.folderId)
/* 207 */       .append(this.folderName, rhs.folderName)
/* 208 */       .append(this.parentId, rhs.parentId)
/* 209 */       .append(this.depLevel, rhs.depLevel)
/* 210 */       .append(this.isPublic, rhs.isPublic)
/* 211 */       .append(this.folderType, rhs.folderType)
/* 212 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 219 */     return new HashCodeBuilder(-82280557, -700257973)
/* 220 */       .append(this.folderId)
/* 221 */       .append(this.folderName)
/* 222 */       .append(this.parentId)
/* 223 */       .append(this.depLevel)
/* 224 */       .append(this.isPublic)
/* 225 */       .append(this.folderType)
/* 226 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 233 */     return new ToStringBuilder(this)
/* 234 */       .append("folderId", this.folderId)
/* 235 */       .append("folderName", this.folderName)
/* 236 */       .append("parentId", this.parentId)
/* 237 */       .append("depLevel", this.depLevel)
/* 238 */       .append("isPublic", this.isPublic)
/* 239 */       .append("folderType", this.folderType)
/* 240 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 247 */     return "folderId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 254 */     return this.folderId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.communicate.MailFolder
 * JD-Core Version:    0.6.0
 */
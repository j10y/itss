/*     */ package com.xpsoft.oa.model.communicate;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class MailBox extends BaseModel
/*     */ {
/*     */   protected Long boxId;
/*     */   protected Date sendTime;
/*     */   protected Short delFlag;
/*     */   protected Short readFlag;
/*     */   protected String note;
/*     */   protected Mail mail;
/*     */   protected AppUser appUser;
/*     */   protected MailFolder mailFolder;
/*     */   protected Short replyFlag;
/*     */ 
/*     */   public MailBox()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MailBox(Long in_boxId)
/*     */   {
/*  44 */     setBoxId(in_boxId);
/*     */   }
/*     */ 
/*     */   public Mail getMail()
/*     */   {
/*  49 */     return this.mail;
/*     */   }
/*     */ 
/*     */   public void setMail(Mail in_mail) {
/*  53 */     this.mail = in_mail;
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser() {
/*  57 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  61 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public MailFolder getMailFolder() {
/*  65 */     return this.mailFolder;
/*     */   }
/*     */ 
/*     */   public void setMailFolder(MailFolder in_mailFolder) {
/*  69 */     this.mailFolder = in_mailFolder;
/*     */   }
/*     */ 
/*     */   public Long getBoxId()
/*     */   {
/*  78 */     return this.boxId;
/*     */   }
/*     */ 
/*     */   public void setBoxId(Long aValue)
/*     */   {
/*  85 */     this.boxId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getMailId()
/*     */   {
/*  92 */     return getMail() == null ? null : getMail().getMailId();
/*     */   }
/*     */ 
/*     */   public void setMailId(Long aValue)
/*     */   {
/*  99 */     if (aValue == null) {
/* 100 */       this.mail = null;
/* 101 */     } else if (this.mail == null) {
/* 102 */       this.mail = new Mail(aValue);
/* 103 */       this.mail.setVersion(new Integer(0));
/*     */     } else {
/* 105 */       this.mail.setMailId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getFolderId()
/*     */   {
/* 113 */     return getMailFolder() == null ? null : getMailFolder().getFolderId();
/*     */   }
/*     */ 
/*     */   public void setFolderId(Long aValue)
/*     */   {
/* 120 */     if (aValue == null) {
/* 121 */       this.mailFolder = null;
/* 122 */     } else if (this.mailFolder == null) {
/* 123 */       this.mailFolder = new MailFolder(aValue);
/* 124 */       this.mailFolder.setVersion(new Integer(0));
/*     */     } else {
/* 126 */       this.mailFolder.setFolderId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 134 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 141 */     if (aValue == null) {
/* 142 */       this.appUser = null;
/* 143 */     } else if (this.appUser == null) {
/* 144 */       this.appUser = new AppUser(aValue);
/* 145 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 147 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getSendTime()
/*     */   {
/* 156 */     return this.sendTime;
/*     */   }
/*     */ 
/*     */   public void setSendTime(Date aValue)
/*     */   {
/* 164 */     this.sendTime = aValue;
/*     */   }
/*     */ 
/*     */   public Short getDelFlag()
/*     */   {
/* 172 */     return this.delFlag;
/*     */   }
/*     */ 
/*     */   public void setDelFlag(Short aValue)
/*     */   {
/* 180 */     this.delFlag = aValue;
/*     */   }
/*     */ 
/*     */   public Short getReadFlag()
/*     */   {
/* 188 */     return this.readFlag;
/*     */   }
/*     */ 
/*     */   public void setReadFlag(Short aValue)
/*     */   {
/* 196 */     this.readFlag = aValue;
/*     */   }
/*     */ 
/*     */   public String getNote()
/*     */   {
/* 204 */     return this.note;
/*     */   }
/*     */ 
/*     */   public void setNote(String aValue)
/*     */   {
/* 211 */     this.note = aValue;
/*     */   }
/*     */ 
/*     */   public Short getReplyFlag()
/*     */   {
/* 216 */     return this.replyFlag;
/*     */   }
/*     */ 
/*     */   public void setReplyFlag(Short replyFlag) {
/* 220 */     this.replyFlag = replyFlag;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 227 */     if (!(object instanceof MailBox)) {
/* 228 */       return false;
/*     */     }
/* 230 */     MailBox rhs = (MailBox)object;
/* 231 */     return new EqualsBuilder()
/* 232 */       .append(this.boxId, rhs.boxId)
/* 233 */       .append(this.sendTime, rhs.sendTime)
/* 234 */       .append(this.delFlag, rhs.delFlag)
/* 235 */       .append(this.readFlag, rhs.readFlag)
/* 236 */       .append(this.note, rhs.note)
/* 237 */       .append(this.replyFlag, this.replyFlag)
/* 238 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 245 */     return new HashCodeBuilder(-82280557, -700257973)
/* 246 */       .append(this.boxId)
/* 247 */       .append(this.sendTime)
/* 248 */       .append(this.delFlag)
/* 249 */       .append(this.readFlag)
/* 250 */       .append(this.note)
/* 251 */       .append(this.replyFlag)
/* 252 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 259 */     return new ToStringBuilder(this)
/* 260 */       .append("boxId", this.boxId)
/* 261 */       .append("sendTime", this.sendTime)
/* 262 */       .append("delFlag", this.delFlag)
/* 263 */       .append("readFlag", this.readFlag)
/* 264 */       .append("note", this.note)
/* 265 */       .append("replyFlag", this.replyFlag)
/* 266 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 273 */     return "boxId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 280 */     return this.boxId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.communicate.MailBox
 * JD-Core Version:    0.6.0
 */
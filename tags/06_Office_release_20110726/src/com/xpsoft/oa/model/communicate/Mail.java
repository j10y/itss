/*     */ package com.xpsoft.oa.model.communicate;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.model.system.FileAttach;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Mail extends BaseModel
/*     */ {
/*     */   protected Long mailId;
/*     */   protected String sender;
/*     */   protected Short importantFlag;
/*     */   protected Date sendTime;
/*     */   protected String content;
/*     */   protected String subject;
/*     */   protected String copyToNames;
/*     */   protected String copyToIDs;
/*     */   protected String recipientNames;
/*     */   protected String recipientIDs;
/*     */   protected Short mailStatus;
/*     */   protected AppUser appSender;
/*     */   protected String fileIds;
/*     */   protected String filenames;
/*  41 */   protected Set<FileAttach> mailAttachs = new HashSet();
/*  42 */   protected Set<MailBox> mailBoxs = new HashSet();
/*     */ 
/*     */   public Mail()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Mail(Long in_mailId)
/*     */   {
/*  57 */     setMailId(in_mailId);
/*     */   }
/*     */ 
/*     */   public String getFileIds()
/*     */   {
/*  62 */     return this.fileIds;
/*     */   }
/*     */ 
/*     */   public void setFileIds(String fileIds) {
/*  66 */     this.fileIds = fileIds;
/*     */   }
/*     */ 
/*     */   public String getFilenames() {
/*  70 */     return this.filenames;
/*     */   }
/*     */ 
/*     */   public void setFilenames(String filenames) {
/*  74 */     this.filenames = filenames;
/*     */   }
/*     */ 
/*     */   public AppUser getAppSender() {
/*  78 */     return this.appSender;
/*     */   }
/*     */ 
/*     */   public void setAppSender(AppUser appSender) {
/*  82 */     this.appSender = appSender;
/*     */   }
/*     */ 
/*     */   public Set<FileAttach> getMailAttachs() {
/*  86 */     return this.mailAttachs;
/*     */   }
/*     */ 
/*     */   public void setMailAttachs(Set<FileAttach> mailAttachs) {
/*  90 */     this.mailAttachs = mailAttachs;
/*     */   }
/*     */ 
/*     */   public Set<MailBox> getMailBoxs() {
/*  94 */     return this.mailBoxs;
/*     */   }
/*     */ 
/*     */   public void setMailBoxs(Set<MailBox> mailBoxs) {
/*  98 */     this.mailBoxs = mailBoxs;
/*     */   }
/*     */ 
/*     */   public Long getMailId()
/*     */   {
/* 106 */     return this.mailId;
/*     */   }
/*     */ 
/*     */   public void setMailId(Long aValue)
/*     */   {
/* 113 */     this.mailId = aValue;
/*     */   }
/*     */ 
/*     */   public String getSender()
/*     */   {
/* 122 */     return this.sender;
/*     */   }
/*     */ 
/*     */   public void setSender(String sender) {
/* 126 */     this.sender = sender;
/*     */   }
/*     */ 
/*     */   public Short getImportantFlag()
/*     */   {
/* 136 */     return this.importantFlag;
/*     */   }
/*     */ 
/*     */   public void setImportantFlag(Short aValue)
/*     */   {
/* 144 */     this.importantFlag = aValue;
/*     */   }
/*     */ 
/*     */   public Date getSendTime()
/*     */   {
/* 152 */     return this.sendTime;
/*     */   }
/*     */ 
/*     */   public void setSendTime(Date aValue)
/*     */   {
/* 160 */     this.sendTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 168 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 176 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 184 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String aValue)
/*     */   {
/* 192 */     this.subject = aValue;
/*     */   }
/*     */ 
/*     */   public String getCopyToNames()
/*     */   {
/* 200 */     return this.copyToNames;
/*     */   }
/*     */ 
/*     */   public void setCopyToNames(String aValue)
/*     */   {
/* 207 */     this.copyToNames = aValue;
/*     */   }
/*     */ 
/*     */   public String getCopyToIDs()
/*     */   {
/* 215 */     return this.copyToIDs;
/*     */   }
/*     */ 
/*     */   public void setCopyToIDs(String aValue)
/*     */   {
/* 222 */     this.copyToIDs = aValue;
/*     */   }
/*     */ 
/*     */   public String getRecipientNames()
/*     */   {
/* 230 */     return this.recipientNames;
/*     */   }
/*     */ 
/*     */   public void setRecipientNames(String aValue)
/*     */   {
/* 238 */     this.recipientNames = aValue;
/*     */   }
/*     */ 
/*     */   public String getRecipientIDs()
/*     */   {
/* 246 */     return this.recipientIDs;
/*     */   }
/*     */ 
/*     */   public void setRecipientIDs(String aValue)
/*     */   {
/* 254 */     this.recipientIDs = aValue;
/*     */   }
/*     */ 
/*     */   public Short getMailStatus()
/*     */   {
/* 264 */     return this.mailStatus;
/*     */   }
/*     */ 
/*     */   public void setMailStatus(Short aValue)
/*     */   {
/* 272 */     this.mailStatus = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 279 */     if (!(object instanceof Mail)) {
/* 280 */       return false;
/*     */     }
/* 282 */     Mail rhs = (Mail)object;
/* 283 */     return new EqualsBuilder()
/* 284 */       .append(this.mailId, rhs.mailId)
/* 285 */       .append(this.sender, rhs.sender)
/* 286 */       .append(this.importantFlag, rhs.importantFlag)
/* 287 */       .append(this.sendTime, rhs.sendTime)
/* 288 */       .append(this.content, rhs.content)
/* 289 */       .append(this.subject, rhs.subject)
/* 290 */       .append(this.copyToNames, rhs.copyToNames)
/* 291 */       .append(this.copyToIDs, rhs.copyToIDs)
/* 292 */       .append(this.recipientNames, rhs.recipientNames)
/* 293 */       .append(this.recipientIDs, rhs.recipientIDs)
/* 294 */       .append(this.mailStatus, rhs.mailStatus)
/* 295 */       .append(this.fileIds, rhs.fileIds)
/* 296 */       .append(this.filenames, rhs.filenames)
/* 297 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 304 */     return new HashCodeBuilder(-82280557, -700257973)
/* 305 */       .append(this.mailId)
/* 306 */       .append(this.sender)
/* 307 */       .append(this.importantFlag)
/* 308 */       .append(this.sendTime)
/* 309 */       .append(this.content)
/* 310 */       .append(this.subject)
/* 311 */       .append(this.copyToNames)
/* 312 */       .append(this.copyToIDs)
/* 313 */       .append(this.recipientNames)
/* 314 */       .append(this.recipientIDs)
/* 315 */       .append(this.mailStatus)
/* 316 */       .append(this.fileIds)
/* 317 */       .append(this.filenames)
/* 318 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 325 */     return new ToStringBuilder(this)
/* 326 */       .append("mailId", this.mailId)
/* 327 */       .append("sender", this.sender)
/* 328 */       .append("importantFlag", this.importantFlag)
/* 329 */       .append("sendTime", this.sendTime)
/* 330 */       .append("content", this.content)
/* 331 */       .append("subject", this.subject)
/* 332 */       .append("copyToNames", this.copyToNames)
/* 333 */       .append("copyToIDs", this.copyToIDs)
/* 334 */       .append("recipientNames", this.recipientNames)
/* 335 */       .append("recipientIDs", this.recipientIDs)
/* 336 */       .append("mailStatus", this.mailStatus)
/* 337 */       .append("fileIds", this.fileIds)
/* 338 */       .append("filenames", this.filenames)
/* 339 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 346 */     return "mailId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 353 */     return this.mailId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.communicate.Mail
 * JD-Core Version:    0.6.0
 */
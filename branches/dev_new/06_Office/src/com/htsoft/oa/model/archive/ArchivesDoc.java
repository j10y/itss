/*     */ package com.htsoft.oa.model.archive;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ArchivesDoc extends BaseModel
/*     */ {
/*  22 */   public static short STATUS_MODIFY = 0;
/*  23 */   public static short STATUS_MODIFY_END = 1;
/*  24 */   public static int ORI_VERSION = 1;
/*     */ 
/*     */   @Expose
/*     */   protected Long docId;
/*     */ 
/*     */   @Expose
/*     */   protected String creator;
/*     */ 
/*     */   @Expose
/*     */   protected Long creatorId;
/*     */ 
/*     */   @Expose
/*     */   protected Long menderId;
/*     */ 
/*     */   @Expose
/*     */   protected String mender;
/*     */ 
/*     */   @Expose
/*     */   protected String docName;
/*     */ 
/*     */   @Expose
/*     */   protected Short docStatus;
/*     */ 
/*     */   @Expose
/*     */   protected Integer curVersion;
/*     */ 
/*     */   @Expose
/*     */   protected String docPath;
/*     */ 
/*     */   @Expose
/*     */   protected Date updatetime;
/*     */ 
/*     */   @Expose
/*     */   protected Date createtime;
/*     */ 
/*     */   @Expose
/*     */   protected FileAttach fileAttach;
/*     */   protected Archives archives;
/*  53 */   protected Set docHistorys = new HashSet();
/*     */ 
/*     */   public ArchivesDoc()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void initUsers(AppUser curUser)
/*     */   {
/*  64 */     setCreator(curUser.getFullname());
/*  65 */     setCreatorId(curUser.getUserId());
/*     */ 
/*  67 */     setMender(curUser.getFullname());
/*  68 */     setMenderId(curUser.getUserId());
/*     */   }
/*     */ 
/*     */   public ArchivesDoc(Long in_docId)
/*     */   {
/*  76 */     setDocId(in_docId);
/*     */   }
/*     */ 
/*     */   public Archives getArchives()
/*     */   {
/*  81 */     return this.archives;
/*     */   }
/*     */ 
/*     */   public void setArchives(Archives in_archives) {
/*  85 */     this.archives = in_archives;
/*     */   }
/*     */ 
/*     */   public FileAttach getFileAttach() {
/*  89 */     return this.fileAttach;
/*     */   }
/*     */ 
/*     */   public void setFileAttach(FileAttach in_fileAttach) {
/*  93 */     this.fileAttach = in_fileAttach;
/*     */   }
/*     */ 
/*     */   public Set getDocHistorys() {
/*  97 */     return this.docHistorys;
/*     */   }
/*     */ 
/*     */   public void setDocHistorys(Set in_docHistorys) {
/* 101 */     this.docHistorys = in_docHistorys;
/*     */   }
/*     */ 
/*     */   public Long getDocId()
/*     */   {
/* 110 */     return this.docId;
/*     */   }
/*     */ 
/*     */   public void setDocId(Long aValue)
/*     */   {
/* 117 */     this.docId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getFileId()
/*     */   {
/* 124 */     return getFileAttach() == null ? null : getFileAttach().getFileId();
/*     */   }
/*     */ 
/*     */   public void setFileId(Long aValue)
/*     */   {
/* 131 */     if (aValue == null) {
/* 132 */       this.fileAttach = null;
/* 133 */     } else if (this.fileAttach == null) {
/* 134 */       this.fileAttach = new FileAttach(aValue);
/* 135 */       this.fileAttach.setVersion(new Integer(0));
/*     */     } else {
/* 137 */       this.fileAttach.setFileId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getArchivesId()
/*     */   {
/* 145 */     return getArchives() == null ? null : getArchives().getArchivesId();
/*     */   }
/*     */ 
/*     */   public void setArchivesId(Long aValue)
/*     */   {
/* 152 */     if (aValue == null) {
/* 153 */       this.archives = null;
/* 154 */     } else if (this.archives == null) {
/* 155 */       this.archives = new Archives(aValue);
/* 156 */       this.archives.setVersion(new Integer(0));
/*     */     } else {
/* 158 */       this.archives.setArchivesId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCreator()
/*     */   {
/* 167 */     return this.creator;
/*     */   }
/*     */ 
/*     */   public void setCreator(String aValue)
/*     */   {
/* 174 */     this.creator = aValue;
/*     */   }
/*     */ 
/*     */   public Long getCreatorId()
/*     */   {
/* 182 */     return this.creatorId;
/*     */   }
/*     */ 
/*     */   public void setCreatorId(Long aValue)
/*     */   {
/* 189 */     this.creatorId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getMenderId()
/*     */   {
/* 197 */     return this.menderId;
/*     */   }
/*     */ 
/*     */   public void setMenderId(Long aValue)
/*     */   {
/* 204 */     this.menderId = aValue;
/*     */   }
/*     */ 
/*     */   public String getMender()
/*     */   {
/* 212 */     return this.mender;
/*     */   }
/*     */ 
/*     */   public void setMender(String aValue)
/*     */   {
/* 219 */     this.mender = aValue;
/*     */   }
/*     */ 
/*     */   public String getDocName()
/*     */   {
/* 227 */     return this.docName;
/*     */   }
/*     */ 
/*     */   public void setDocName(String aValue)
/*     */   {
/* 235 */     this.docName = aValue;
/*     */   }
/*     */ 
/*     */   public Short getDocStatus()
/*     */   {
/* 245 */     return this.docStatus;
/*     */   }
/*     */ 
/*     */   public void setDocStatus(Short aValue)
/*     */   {
/* 253 */     this.docStatus = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getCurVersion()
/*     */   {
/* 262 */     return this.curVersion;
/*     */   }
/*     */ 
/*     */   public void setCurVersion(Integer aValue)
/*     */   {
/* 270 */     this.curVersion = aValue;
/*     */   }
/*     */ 
/*     */   public String getDocPath()
/*     */   {
/* 278 */     return this.docPath;
/*     */   }
/*     */ 
/*     */   public void setDocPath(String aValue)
/*     */   {
/* 286 */     this.docPath = aValue;
/*     */   }
/*     */ 
/*     */   public Date getUpdatetime()
/*     */   {
/* 294 */     return this.updatetime;
/*     */   }
/*     */ 
/*     */   public void setUpdatetime(Date aValue)
/*     */   {
/* 302 */     this.updatetime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 310 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 318 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 325 */     if (!(object instanceof ArchivesDoc)) {
/* 326 */       return false;
/*     */     }
/* 328 */     ArchivesDoc rhs = (ArchivesDoc)object;
/* 329 */     return new EqualsBuilder()
/* 330 */       .append(this.docId, rhs.docId)
/* 331 */       .append(this.creator, rhs.creator)
/* 332 */       .append(this.creatorId, rhs.creatorId)
/* 333 */       .append(this.menderId, rhs.menderId)
/* 334 */       .append(this.mender, rhs.mender)
/* 335 */       .append(this.docName, rhs.docName)
/* 336 */       .append(this.docStatus, rhs.docStatus)
/* 337 */       .append(this.curVersion, rhs.curVersion)
/* 338 */       .append(this.docPath, rhs.docPath)
/* 339 */       .append(this.updatetime, rhs.updatetime)
/* 340 */       .append(this.createtime, rhs.createtime)
/* 341 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 348 */     return new HashCodeBuilder(-82280557, -700257973)
/* 349 */       .append(this.docId)
/* 350 */       .append(this.creator)
/* 351 */       .append(this.creatorId)
/* 352 */       .append(this.menderId)
/* 353 */       .append(this.mender)
/* 354 */       .append(this.docName)
/* 355 */       .append(this.docStatus)
/* 356 */       .append(this.curVersion)
/* 357 */       .append(this.docPath)
/* 358 */       .append(this.updatetime)
/* 359 */       .append(this.createtime)
/* 360 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 367 */     return new ToStringBuilder(this)
/* 368 */       .append("docId", this.docId)
/* 369 */       .append("creator", this.creator)
/* 370 */       .append("creatorId", this.creatorId)
/* 371 */       .append("menderId", this.menderId)
/* 372 */       .append("mender", this.mender)
/* 373 */       .append("docName", this.docName)
/* 374 */       .append("docStatus", this.docStatus)
/* 375 */       .append("curVersion", this.curVersion)
/* 376 */       .append("docPath", this.docPath)
/* 377 */       .append("updatetime", this.updatetime)
/* 378 */       .append("createtime", this.createtime)
/* 379 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.archive.ArchivesDoc
 * JD-Core Version:    0.6.0
 */
/*     */ package com.htsoft.oa.model.archive;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class DocHistory extends BaseModel
/*     */ {
/*     */   protected Long historyId;
/*     */   protected String docName;
/*     */   protected String path;
/*     */   protected Integer version;
/*     */   protected Date updatetime;
/*     */   protected String mender;
/*     */   protected FileAttach fileAttach;
/*     */   protected ArchivesDoc archivesDoc;
/*     */ 
/*     */   public DocHistory()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DocHistory(Long in_historyId)
/*     */   {
/*  42 */     setHistoryId(in_historyId);
/*     */   }
/*     */ 
/*     */   public FileAttach getFileAttach()
/*     */   {
/*  47 */     return this.fileAttach;
/*     */   }
/*     */ 
/*     */   public void setFileAttach(FileAttach in_fileAttach) {
/*  51 */     this.fileAttach = in_fileAttach;
/*     */   }
/*     */ 
/*     */   public ArchivesDoc getArchivesDoc() {
/*  55 */     return this.archivesDoc;
/*     */   }
/*     */ 
/*     */   public void setArchivesDoc(ArchivesDoc in_archivesDoc) {
/*  59 */     this.archivesDoc = in_archivesDoc;
/*     */   }
/*     */ 
/*     */   public Long getHistoryId()
/*     */   {
/*  68 */     return this.historyId;
/*     */   }
/*     */ 
/*     */   public void setHistoryId(Long aValue)
/*     */   {
/*  75 */     this.historyId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getDocId()
/*     */   {
/*  82 */     return getArchivesDoc() == null ? null : getArchivesDoc().getDocId();
/*     */   }
/*     */ 
/*     */   public void setDocId(Long aValue)
/*     */   {
/*  89 */     if (aValue == null) {
/*  90 */       this.archivesDoc = null;
/*  91 */     } else if (this.archivesDoc == null) {
/*  92 */       this.archivesDoc = new ArchivesDoc(aValue);
/*  93 */       this.archivesDoc.setVersion(new Integer(0));
/*     */     } else {
/*  95 */       this.archivesDoc.setDocId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getFileId()
/*     */   {
/* 103 */     return getFileAttach() == null ? null : getFileAttach().getFileId();
/*     */   }
/*     */ 
/*     */   public void setFileId(Long aValue)
/*     */   {
/* 110 */     if (aValue == null) {
/* 111 */       this.fileAttach = null;
/* 112 */     } else if (this.fileAttach == null) {
/* 113 */       this.fileAttach = new FileAttach(aValue);
/* 114 */       this.fileAttach.setVersion(new Integer(0));
/*     */     } else {
/* 116 */       this.fileAttach.setFileId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getDocName()
/*     */   {
/* 125 */     return this.docName;
/*     */   }
/*     */ 
/*     */   public void setDocName(String aValue)
/*     */   {
/* 133 */     this.docName = aValue;
/*     */   }
/*     */ 
/*     */   public String getPath()
/*     */   {
/* 141 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void setPath(String aValue)
/*     */   {
/* 149 */     this.path = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getVersion()
/*     */   {
/* 157 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(Integer aValue)
/*     */   {
/* 165 */     this.version = aValue;
/*     */   }
/*     */ 
/*     */   public Date getUpdatetime()
/*     */   {
/* 173 */     return this.updatetime;
/*     */   }
/*     */ 
/*     */   public void setUpdatetime(Date aValue)
/*     */   {
/* 181 */     this.updatetime = aValue;
/*     */   }
/*     */ 
/*     */   public String getMender()
/*     */   {
/* 189 */     return this.mender;
/*     */   }
/*     */ 
/*     */   public void setMender(String aValue)
/*     */   {
/* 197 */     this.mender = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 204 */     if (!(object instanceof DocHistory)) {
/* 205 */       return false;
/*     */     }
/* 207 */     DocHistory rhs = (DocHistory)object;
/* 208 */     return new EqualsBuilder()
/* 209 */       .append(this.historyId, rhs.historyId)
/* 210 */       .append(this.docName, rhs.docName)
/* 211 */       .append(this.path, rhs.path)
/* 212 */       .append(this.version, rhs.version)
/* 213 */       .append(this.updatetime, rhs.updatetime)
/* 214 */       .append(this.mender, rhs.mender)
/* 215 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 222 */     return new HashCodeBuilder(-82280557, -700257973)
/* 223 */       .append(this.historyId)
/* 224 */       .append(this.docName)
/* 225 */       .append(this.path)
/* 226 */       .append(this.version)
/* 227 */       .append(this.updatetime)
/* 228 */       .append(this.mender)
/* 229 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 236 */     return new ToStringBuilder(this)
/* 237 */       .append("historyId", this.historyId)
/* 238 */       .append("docName", this.docName)
/* 239 */       .append("path", this.path)
/* 240 */       .append("version", this.version)
/* 241 */       .append("updatetime", this.updatetime)
/* 242 */       .append("mender", this.mender)
/* 243 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.archive.DocHistory
 * JD-Core Version:    0.6.0
 */
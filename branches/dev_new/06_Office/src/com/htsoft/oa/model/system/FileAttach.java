/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class FileAttach extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long fileId;
/*     */ 
/*     */   @Expose
/*     */   protected String fileName;
/*     */ 
/*     */   @Expose
/*     */   protected String filePath;
/*     */ 
/*     */   @Expose
/*     */   protected Date createtime;
/*     */ 
/*     */   @Expose
/*     */   protected String ext;
/*     */ 
/*     */   @Expose
/*     */   protected String fileType;
/*     */ 
/*     */   @Expose
/*     */   protected String note;
/*     */ 
/*     */   @Expose
/*     */   protected String creator;
/*     */ 
/*     */   public FileAttach()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FileAttach(Long in_fileId)
/*     */   {
/*  53 */     setFileId(in_fileId);
/*     */   }
/*     */ 
/*     */   public Long getFileId()
/*     */   {
/*  63 */     return this.fileId;
/*     */   }
/*     */ 
/*     */   public void setFileId(Long aValue)
/*     */   {
/*  70 */     this.fileId = aValue;
/*     */   }
/*     */ 
/*     */   public String getFileName()
/*     */   {
/*  78 */     return this.fileName;
/*     */   }
/*     */ 
/*     */   public void setFileName(String aValue)
/*     */   {
/*  86 */     this.fileName = aValue;
/*     */   }
/*     */ 
/*     */   public String getFilePath()
/*     */   {
/*  94 */     return this.filePath;
/*     */   }
/*     */ 
/*     */   public void setFilePath(String aValue)
/*     */   {
/* 102 */     this.filePath = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 110 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 118 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public String getExt()
/*     */   {
/* 126 */     return this.ext;
/*     */   }
/*     */ 
/*     */   public void setExt(String aValue)
/*     */   {
/* 133 */     this.ext = aValue;
/*     */   }
/*     */ 
/*     */   public String getFileType()
/*     */   {
/* 142 */     return this.fileType;
/*     */   }
/*     */ 
/*     */   public void setFileType(String aValue)
/*     */   {
/* 150 */     this.fileType = aValue;
/*     */   }
/*     */ 
/*     */   public String getNote()
/*     */   {
/* 158 */     return this.note;
/*     */   }
/*     */ 
/*     */   public void setNote(String aValue)
/*     */   {
/* 165 */     this.note = aValue;
/*     */   }
/*     */ 
/*     */   public String getCreator()
/*     */   {
/* 173 */     return this.creator;
/*     */   }
/*     */ 
/*     */   public void setCreator(String aValue)
/*     */   {
/* 181 */     this.creator = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 188 */     if (!(object instanceof FileAttach)) {
/* 189 */       return false;
/*     */     }
/* 191 */     FileAttach rhs = (FileAttach)object;
/* 192 */     return new EqualsBuilder()
/* 193 */       .append(this.fileId, rhs.fileId)
/* 194 */       .append(this.fileName, rhs.fileName)
/* 195 */       .append(this.filePath, rhs.filePath)
/* 196 */       .append(this.createtime, rhs.createtime)
/* 197 */       .append(this.ext, rhs.ext)
/* 198 */       .append(this.fileType, rhs.fileType)
/* 199 */       .append(this.note, rhs.note)
/* 200 */       .append(this.creator, rhs.creator)
/* 201 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 208 */     return new HashCodeBuilder(-82280557, -700257973)
/* 209 */       .append(this.fileId)
/* 210 */       .append(this.fileName)
/* 211 */       .append(this.filePath)
/* 212 */       .append(this.createtime)
/* 213 */       .append(this.ext)
/* 214 */       .append(this.fileType)
/* 215 */       .append(this.note)
/* 216 */       .append(this.creator)
/* 217 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 224 */     return new ToStringBuilder(this)
/* 225 */       .append("fileId", this.fileId)
/* 226 */       .append("fileName", this.fileName)
/* 227 */       .append("filePath", this.filePath)
/* 228 */       .append("createtime", this.createtime)
/* 229 */       .append("ext", this.ext)
/* 230 */       .append("type", this.fileType)
/* 231 */       .append("note", this.note)
/* 232 */       .append("creator", this.creator)
/* 233 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 240 */     return "fileId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 247 */     return this.fileId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.FileAttach
 * JD-Core Version:    0.6.0
 */
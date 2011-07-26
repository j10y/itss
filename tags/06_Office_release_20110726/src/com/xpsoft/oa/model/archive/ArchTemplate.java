/*     */ package com.xpsoft.oa.model.archive;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.FileAttach;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ArchTemplate extends BaseModel
/*     */ {
/*     */   protected Long templateId;
/*     */   protected String tempName;
/*     */   protected String tempPath;
/*     */   protected FileAttach fileAttach;
/*     */   protected ArchivesType archivesType;
/*     */ 
/*     */   public ArchTemplate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArchTemplate(Long in_templateId)
/*     */   {
/*  39 */     setTemplateId(in_templateId);
/*     */   }
/*     */ 
/*     */   public FileAttach getFileAttach()
/*     */   {
/*  44 */     return this.fileAttach;
/*     */   }
/*     */ 
/*     */   public void setFileAttach(FileAttach in_fileAttach) {
/*  48 */     this.fileAttach = in_fileAttach;
/*     */   }
/*     */ 
/*     */   public ArchivesType getArchivesType() {
/*  52 */     return this.archivesType;
/*     */   }
/*     */ 
/*     */   public void setArchivesType(ArchivesType in_archivesType) {
/*  56 */     this.archivesType = in_archivesType;
/*     */   }
/*     */ 
/*     */   public Long getTemplateId()
/*     */   {
/*  65 */     return this.templateId;
/*     */   }
/*     */ 
/*     */   public void setTemplateId(Long aValue)
/*     */   {
/*  72 */     this.templateId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getTypeId()
/*     */   {
/*  79 */     return getArchivesType() == null ? null : getArchivesType().getTypeId();
/*     */   }
/*     */ 
/*     */   public void setTypeId(Long aValue)
/*     */   {
/*  86 */     if (aValue == null) {
/*  87 */       this.archivesType = null;
/*  88 */     } else if (this.archivesType == null) {
/*  89 */       this.archivesType = new ArchivesType(aValue);
/*  90 */       this.archivesType.setVersion(new Integer(0));
/*     */     } else {
/*  92 */       this.archivesType.setTypeId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTempName()
/*     */   {
/* 101 */     return this.tempName;
/*     */   }
/*     */ 
/*     */   public void setTempName(String aValue)
/*     */   {
/* 109 */     this.tempName = aValue;
/*     */   }
/*     */ 
/*     */   public String getTempPath()
/*     */   {
/* 117 */     return this.tempPath;
/*     */   }
/*     */ 
/*     */   public void setTempPath(String aValue)
/*     */   {
/* 125 */     this.tempPath = aValue;
/*     */   }
/*     */ 
/*     */   public Long getFileId()
/*     */   {
/* 132 */     return getFileAttach() == null ? null : getFileAttach().getFileId();
/*     */   }
/*     */ 
/*     */   public void setFileId(Long aValue)
/*     */   {
/* 139 */     if (aValue == null) {
/* 140 */       this.fileAttach = null;
/* 141 */     } else if (this.fileAttach == null) {
/* 142 */       this.fileAttach = new FileAttach(aValue);
/* 143 */       this.fileAttach.setVersion(new Integer(0));
/*     */     } else {
/* 145 */       this.fileAttach.setFileId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 153 */     if (!(object instanceof ArchTemplate)) {
/* 154 */       return false;
/*     */     }
/* 156 */     ArchTemplate rhs = (ArchTemplate)object;
/* 157 */     return new EqualsBuilder()
/* 158 */       .append(this.templateId, rhs.templateId)
/* 159 */       .append(this.tempName, rhs.tempName)
/* 160 */       .append(this.tempPath, rhs.tempPath)
/* 161 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 168 */     return new HashCodeBuilder(-82280557, -700257973)
/* 169 */       .append(this.templateId)
/* 170 */       .append(this.tempName)
/* 171 */       .append(this.tempPath)
/* 172 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 179 */     return new ToStringBuilder(this)
/* 180 */       .append("templateId", this.templateId)
/* 181 */       .append("tempName", this.tempName)
/* 182 */       .append("tempPath", this.tempPath)
/* 183 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.archive.ArchTemplate
 * JD-Core Version:    0.6.0
 */
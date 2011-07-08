/*     */ package com.xpsoft.oa.model.document;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class DocPrivilege extends BaseModel
/*     */ {
/*     */   protected Long privilegeId;
/*     */   protected Integer rights;
/*     */   protected Integer udrId;
/*     */   protected String udrName;
/*     */   protected Short flag;
/*     */   protected Short fdFlag;
/*     */   protected Document document;
/*     */   protected DocFolder docFolder;
/*     */ 
/*     */   public DocPrivilege()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DocPrivilege(Long in_privilegeId)
/*     */   {
/*  45 */     setPrivilegeId(in_privilegeId);
/*     */   }
/*     */ 
/*     */   public Document getDocument()
/*     */   {
/*  50 */     return this.document;
/*     */   }
/*     */ 
/*     */   public void setDocument(Document in_document) {
/*  54 */     this.document = in_document;
/*     */   }
/*     */ 
/*     */   public DocFolder getDocFolder() {
/*  58 */     return this.docFolder;
/*     */   }
/*     */ 
/*     */   public void setDocFolder(DocFolder in_docFolder) {
/*  62 */     this.docFolder = in_docFolder;
/*     */   }
/*     */ 
/*     */   public Long getPrivilegeId()
/*     */   {
/*  71 */     return this.privilegeId;
/*     */   }
/*     */ 
/*     */   public void setPrivilegeId(Long aValue)
/*     */   {
/*  78 */     this.privilegeId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getFolderId()
/*     */   {
/*  85 */     return getDocFolder() == null ? null : getDocFolder().getFolderId();
/*     */   }
/*     */ 
/*     */   public void setFolderId(Long aValue)
/*     */   {
/*  92 */     if (aValue == null) {
/*  93 */       this.docFolder = null;
/*  94 */     } else if (this.docFolder == null) {
/*  95 */       this.docFolder = new DocFolder(aValue);
/*  96 */       this.docFolder.setVersion(new Integer(0));
/*     */     } else {
/*  98 */       this.docFolder.setFolderId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getDocId()
/*     */   {
/* 106 */     return getDocument() == null ? null : getDocument().getDocId();
/*     */   }
/*     */ 
/*     */   public void setDocId(Long aValue)
/*     */   {
/* 113 */     if (aValue == null) {
/* 114 */       this.document = null;
/* 115 */     } else if (this.document == null) {
/* 116 */       this.document = new Document(aValue);
/* 117 */       this.document.setVersion(new Integer(0));
/*     */     } else {
/* 119 */       this.document.setDocId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getRights()
/*     */   {
/* 138 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public void setRights(Integer aValue)
/*     */   {
/* 146 */     this.rights = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getUdrId()
/*     */   {
/* 154 */     return this.udrId;
/*     */   }
/*     */ 
/*     */   public void setUdrId(Integer aValue)
/*     */   {
/* 161 */     this.udrId = aValue;
/*     */   }
/*     */ 
/*     */   public String getUdrName()
/*     */   {
/* 169 */     return this.udrName;
/*     */   }
/*     */ 
/*     */   public void setUdrName(String aValue)
/*     */   {
/* 176 */     this.udrName = aValue;
/*     */   }
/*     */ 
/*     */   public Short getFlag()
/*     */   {
/* 186 */     return this.flag;
/*     */   }
/*     */ 
/*     */   public void setFlag(Short aValue)
/*     */   {
/* 194 */     this.flag = aValue;
/*     */   }
/*     */ 
/*     */   public Short getFdFlag() {
/* 198 */     return this.fdFlag;
/*     */   }
/*     */ 
/*     */   public void setFdFlag(Short fdFlag) {
/* 202 */     this.fdFlag = fdFlag;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 209 */     if (!(object instanceof DocPrivilege)) {
/* 210 */       return false;
/*     */     }
/* 212 */     DocPrivilege rhs = (DocPrivilege)object;
/* 213 */     return new EqualsBuilder()
/* 214 */       .append(this.privilegeId, rhs.privilegeId)
/* 215 */       .append(this.rights, rhs.rights)
/* 216 */       .append(this.udrId, rhs.udrId)
/* 217 */       .append(this.udrName, rhs.udrName)
/* 218 */       .append(this.flag, rhs.flag)
/* 219 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 226 */     return new HashCodeBuilder(-82280557, -700257973)
/* 227 */       .append(this.privilegeId)
/* 228 */       .append(this.rights)
/* 229 */       .append(this.udrId)
/* 230 */       .append(this.udrName)
/* 231 */       .append(this.flag)
/* 232 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 239 */     return new ToStringBuilder(this)
/* 240 */       .append("privilegeId", this.privilegeId)
/* 241 */       .append("rights", this.rights)
/* 242 */       .append("udrId", this.udrId)
/* 243 */       .append("udrName", this.udrName)
/* 244 */       .append("flag", this.flag)
/* 245 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.document.DocPrivilege
 * JD-Core Version:    0.6.0
 */
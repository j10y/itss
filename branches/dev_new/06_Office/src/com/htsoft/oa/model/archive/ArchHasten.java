/*     */ package com.htsoft.oa.model.archive;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ArchHasten extends BaseModel
/*     */ {
/*     */   protected Long recordId;
/*     */   protected String content;
/*     */   protected Date createtime;
/*     */   protected String hastenFullname;
/*     */   protected String handlerFullname;
/*     */   protected Long handlerUserId;
/*     */   protected Archives archives;
/*     */ 
/*     */   public ArchHasten()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArchHasten(Long in_recordId)
/*     */   {
/*  41 */     setRecordId(in_recordId);
/*     */   }
/*     */ 
/*     */   public Archives getArchives()
/*     */   {
/*  46 */     return this.archives;
/*     */   }
/*     */ 
/*     */   public void setArchives(Archives in_archives) {
/*  50 */     this.archives = in_archives;
/*     */   }
/*     */ 
/*     */   public Long getRecordId()
/*     */   {
/*  59 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long aValue)
/*     */   {
/*  66 */     this.recordId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getArchivesId()
/*     */   {
/*  73 */     return getArchives() == null ? null : getArchives().getArchivesId();
/*     */   }
/*     */ 
/*     */   public void setArchivesId(Long aValue)
/*     */   {
/*  80 */     if (aValue == null) {
/*  81 */       this.archives = null;
/*  82 */     } else if (this.archives == null) {
/*  83 */       this.archives = new Archives(aValue);
/*  84 */       this.archives.setVersion(new Integer(0));
/*     */     } else {
/*  86 */       this.archives.setArchivesId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  95 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 102 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 110 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 117 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public String getHastenFullname()
/*     */   {
/* 125 */     return this.hastenFullname;
/*     */   }
/*     */ 
/*     */   public void setHastenFullname(String aValue)
/*     */   {
/* 132 */     this.hastenFullname = aValue;
/*     */   }
/*     */ 
/*     */   public String getHandlerFullname()
/*     */   {
/* 140 */     return this.handlerFullname;
/*     */   }
/*     */ 
/*     */   public void setHandlerFullname(String aValue)
/*     */   {
/* 147 */     this.handlerFullname = aValue;
/*     */   }
/*     */ 
/*     */   public Long getHandlerUserId()
/*     */   {
/* 155 */     return this.handlerUserId;
/*     */   }
/*     */ 
/*     */   public void setHandlerUserId(Long aValue)
/*     */   {
/* 162 */     this.handlerUserId = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 169 */     if (!(object instanceof ArchHasten)) {
/* 170 */       return false;
/*     */     }
/* 172 */     ArchHasten rhs = (ArchHasten)object;
/* 173 */     return new EqualsBuilder()
/* 174 */       .append(this.recordId, rhs.recordId)
/* 175 */       .append(this.content, rhs.content)
/* 176 */       .append(this.createtime, rhs.createtime)
/* 177 */       .append(this.hastenFullname, rhs.hastenFullname)
/* 178 */       .append(this.handlerFullname, rhs.handlerFullname)
/* 179 */       .append(this.handlerUserId, rhs.handlerUserId)
/* 180 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 187 */     return new HashCodeBuilder(-82280557, -700257973)
/* 188 */       .append(this.recordId)
/* 189 */       .append(this.content)
/* 190 */       .append(this.createtime)
/* 191 */       .append(this.hastenFullname)
/* 192 */       .append(this.handlerFullname)
/* 193 */       .append(this.handlerUserId)
/* 194 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 201 */     return new ToStringBuilder(this)
/* 202 */       .append("recordId", this.recordId)
/* 203 */       .append("content", this.content)
/* 204 */       .append("createtime", this.createtime)
/* 205 */       .append("hastenFullname", this.hastenFullname)
/* 206 */       .append("handlerFullname", this.handlerFullname)
/* 207 */       .append("handlerUserId", this.handlerUserId)
/* 208 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.archive.ArchHasten
 * JD-Core Version:    0.6.0
 */
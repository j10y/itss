/*     */ package com.htsoft.oa.model.archive;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class ArchivesAttend extends BaseModel
/*     */ {
/*     */   protected Long attendId;
/*     */   protected Long userID;
/*     */   protected String fullname;
/*     */   protected String attendType;
/*     */   protected Date executeTime;
/*     */   protected String memo;
/*     */   protected Archives archives;
/*     */ 
/*     */   public ArchivesAttend()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArchivesAttend(Long in_attendId)
/*     */   {
/*  41 */     setAttendId(in_attendId);
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
/*     */   public Long getAttendId()
/*     */   {
/*  59 */     return this.attendId;
/*     */   }
/*     */ 
/*     */   public void setAttendId(Long aValue)
/*     */   {
/*  66 */     this.attendId = aValue;
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
/*     */   public Long getUserID()
/*     */   {
/*  95 */     return this.userID;
/*     */   }
/*     */ 
/*     */   public void setUserID(Long aValue)
/*     */   {
/* 103 */     this.userID = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 111 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 119 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public String getAttendType()
/*     */   {
/* 131 */     return this.attendType;
/*     */   }
/*     */ 
/*     */   public void setAttendType(String aValue)
/*     */   {
/* 139 */     this.attendType = aValue;
/*     */   }
/*     */ 
/*     */   public Date getExecuteTime()
/*     */   {
/* 147 */     return this.executeTime;
/*     */   }
/*     */ 
/*     */   public void setExecuteTime(Date aValue)
/*     */   {
/* 154 */     this.executeTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getMemo()
/*     */   {
/* 162 */     return this.memo;
/*     */   }
/*     */ 
/*     */   public void setMemo(String aValue)
/*     */   {
/* 169 */     this.memo = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 176 */     if (!(object instanceof ArchivesAttend)) {
/* 177 */       return false;
/*     */     }
/* 179 */     ArchivesAttend rhs = (ArchivesAttend)object;
/* 180 */     return new EqualsBuilder()
/* 181 */       .append(this.attendId, rhs.attendId)
/* 182 */       .append(this.userID, rhs.userID)
/* 183 */       .append(this.fullname, rhs.fullname)
/* 184 */       .append(this.attendType, rhs.attendType)
/* 185 */       .append(this.executeTime, rhs.executeTime)
/* 186 */       .append(this.memo, rhs.memo)
/* 187 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 194 */     return new HashCodeBuilder(-82280557, -700257973)
/* 195 */       .append(this.attendId)
/* 196 */       .append(this.userID)
/* 197 */       .append(this.fullname)
/* 198 */       .append(this.attendType)
/* 199 */       .append(this.executeTime)
/* 200 */       .append(this.memo)
/* 201 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 208 */     return new ToStringBuilder(this)
/* 209 */       .append("attendId", this.attendId)
/* 210 */       .append("userID", this.userID)
/* 211 */       .append("fullname", this.fullname)
/* 212 */       .append("attendType", this.attendType)
/* 213 */       .append("executeTime", this.executeTime)
/* 214 */       .append("memo", this.memo)
/* 215 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.archive.ArchivesAttend
 * JD-Core Version:    0.6.0
 */
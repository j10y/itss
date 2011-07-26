/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class SystemLog extends BaseModel
/*     */ {
/*     */   protected Long logId;
/*     */   protected String username;
/*     */   protected Long userId;
/*     */   protected Date createtime;
/*     */   protected String exeOperation;
/*     */ 
/*     */   public SystemLog()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SystemLog(Long in_logId)
/*     */   {
/*  39 */     setLogId(in_logId);
/*     */   }
/*     */ 
/*     */   public Long getLogId()
/*     */   {
/*  49 */     return this.logId;
/*     */   }
/*     */ 
/*     */   public void setLogId(Long aValue)
/*     */   {
/*  56 */     this.logId = aValue;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/*  64 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String aValue)
/*     */   {
/*  72 */     this.username = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  80 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/*  88 */     this.userId = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/*  96 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 104 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public String getExeOperation()
/*     */   {
/* 112 */     return this.exeOperation;
/*     */   }
/*     */ 
/*     */   public void setExeOperation(String aValue)
/*     */   {
/* 120 */     this.exeOperation = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 127 */     if (!(object instanceof SystemLog)) {
/* 128 */       return false;
/*     */     }
/* 130 */     SystemLog rhs = (SystemLog)object;
/* 131 */     return new EqualsBuilder()
/* 132 */       .append(this.logId, rhs.logId)
/* 133 */       .append(this.username, rhs.username)
/* 134 */       .append(this.userId, rhs.userId)
/* 135 */       .append(this.createtime, rhs.createtime)
/* 136 */       .append(this.exeOperation, rhs.exeOperation)
/* 137 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 144 */     return new HashCodeBuilder(-82280557, -700257973)
/* 145 */       .append(this.logId)
/* 146 */       .append(this.username)
/* 147 */       .append(this.userId)
/* 148 */       .append(this.createtime)
/* 149 */       .append(this.exeOperation)
/* 150 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 157 */     return new ToStringBuilder(this)
/* 158 */       .append("logId", this.logId)
/* 159 */       .append("username", this.username)
/* 160 */       .append("userId", this.userId)
/* 161 */       .append("createtime", this.createtime)
/* 162 */       .append("exeOperation", this.exeOperation)
/* 163 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.SystemLog
 * JD-Core Version:    0.6.0
 */
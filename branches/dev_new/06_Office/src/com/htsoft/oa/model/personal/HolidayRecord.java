/*     */ package com.htsoft.oa.model.personal;
/*     */ 
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class HolidayRecord extends BaseModel
/*     */ {
/*     */   protected Long recordId;
/*     */   protected Date startTime;
/*     */   protected Date endTime;
/*     */   protected String descp;
/*     */   protected Long userId;
/*     */   protected String fullname;
/*     */   protected Short isAll;
/*  29 */   public static final Short IS_ALL_HOLIDAY = Short.valueOf((short) 1);
/*     */ 
/*  31 */   public static final Short IS_PERSONAL_HOLIDAY = Short.valueOf((short) 0);
/*     */ 
/*     */   public HolidayRecord()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HolidayRecord(Long in_recordId)
/*     */   {
/*  45 */     setRecordId(in_recordId);
/*     */   }
/*     */ 
/*     */   public String getDescp()
/*     */   {
/*  51 */     return this.descp;
/*     */   }
/*     */ 
/*     */   public void setDescp(String descp) {
/*  55 */     this.descp = descp;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/*  60 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String fullname) {
/*  64 */     this.fullname = fullname;
/*     */   }
/*     */ 
/*     */   public Long getRecordId()
/*     */   {
/*  72 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long aValue)
/*     */   {
/*  79 */     this.recordId = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/*  87 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date aValue)
/*     */   {
/*  95 */     this.startTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 103 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date aValue)
/*     */   {
/* 111 */     this.endTime = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 120 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 127 */     this.userId = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsAll()
/*     */   {
/* 137 */     return this.isAll;
/*     */   }
/*     */ 
/*     */   public void setIsAll(Short aValue)
/*     */   {
/* 145 */     this.isAll = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 152 */     if (!(object instanceof HolidayRecord)) {
/* 153 */       return false;
/*     */     }
/* 155 */     HolidayRecord rhs = (HolidayRecord)object;
/* 156 */     return new EqualsBuilder()
/* 157 */       .append(this.recordId, rhs.recordId)
/* 158 */       .append(this.startTime, rhs.startTime)
/* 159 */       .append(this.endTime, rhs.endTime)
/* 160 */       .append(this.userId, rhs.userId)
/* 161 */       .append(this.isAll, rhs.isAll)
/* 162 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 169 */     return new HashCodeBuilder(-82280557, -700257973)
/* 170 */       .append(this.recordId)
/* 171 */       .append(this.startTime)
/* 172 */       .append(this.endTime)
/* 173 */       .append(this.userId)
/* 174 */       .append(this.isAll)
/* 175 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 182 */     return new ToStringBuilder(this)
/* 183 */       .append("recordId", this.recordId)
/* 184 */       .append("startTime", this.startTime)
/* 185 */       .append("endTime", this.endTime)
/* 186 */       .append("userId", this.userId)
/* 187 */       .append("isAll", this.isAll)
/* 188 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.personal.HolidayRecord
 * JD-Core Version:    0.6.0
 */
/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Diary extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long diaryId;
/*     */ 
/*     */   @Expose
/*     */   protected Date dayTime;
/*     */ 
/*     */   @Expose
/*     */   protected String content;
/*     */ 
/*     */   @Expose
/*     */   protected Short diaryType;
/*     */ 
/*     */   @Expose
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public Diary()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Diary(Long in_diaryId)
/*     */   {
/*  47 */     setDiaryId(in_diaryId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  53 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser appUser) {
/*  57 */     this.appUser = appUser;
/*     */   }
/*     */ 
/*     */   public Long getDiaryId()
/*     */   {
/*  65 */     return this.diaryId;
/*     */   }
/*     */ 
/*     */   public void setDiaryId(Long aValue)
/*     */   {
/*  72 */     this.diaryId = aValue;
/*     */   }
/*     */ 
/*     */   public Date getDayTime()
/*     */   {
/*  80 */     return this.dayTime;
/*     */   }
/*     */ 
/*     */   public void setDayTime(Date aValue)
/*     */   {
/*  88 */     this.dayTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/*  96 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 104 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public Short getDiaryType()
/*     */   {
/* 112 */     return this.diaryType;
/*     */   }
/*     */ 
/*     */   public void setDiaryType(Short aValue)
/*     */   {
/* 120 */     this.diaryType = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 128 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 136 */     if (aValue == null) {
/* 137 */       this.appUser = null;
/* 138 */     } else if (this.appUser == null) {
/* 139 */       this.appUser = new AppUser(aValue);
/* 140 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 142 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 150 */     if (!(object instanceof Diary)) {
/* 151 */       return false;
/*     */     }
/* 153 */     Diary rhs = (Diary)object;
/* 154 */     return new EqualsBuilder()
/* 155 */       .append(this.diaryId, rhs.diaryId)
/* 156 */       .append(this.dayTime, rhs.dayTime)
/* 157 */       .append(this.content, rhs.content)
/* 158 */       .append(this.diaryType, rhs.diaryType)
/* 159 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 166 */     return new HashCodeBuilder(-82280557, -700257973)
/* 167 */       .append(this.diaryId)
/* 168 */       .append(this.dayTime)
/* 169 */       .append(this.content)
/* 170 */       .append(this.diaryType)
/* 171 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 178 */     return new ToStringBuilder(this)
/* 179 */       .append("diaryId", this.diaryId)
/* 180 */       .append("dayTime", this.dayTime)
/* 181 */       .append("content", this.content)
/* 182 */       .append("diaryType", this.diaryType)
/* 183 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 190 */     return "diaryId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 197 */     return this.diaryId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.Diary
 * JD-Core Version:    0.6.0
 */
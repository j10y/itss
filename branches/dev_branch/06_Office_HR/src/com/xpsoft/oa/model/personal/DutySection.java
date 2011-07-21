/*     */ package com.xpsoft.oa.model.personal;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ 
/*     */ public class DutySection extends BaseModel
/*     */ {
/*     */   protected Long sectionId;
/*     */   protected String sectionName;
/*     */   protected Date startSignin;
/*     */   protected Date dutyStartTime;
/*     */   protected Date endSignin;
/*     */   protected Date earlyOffTime;
/*     */   protected Date dutyEndTime;
/*     */   protected Date signOutTime;
/*     */   protected String startSignin1;
/*     */   protected String dutyStartTime1;
/*     */   protected String endSignin1;
/*     */   protected String earlyOffTime1;
/*     */   protected String dutyEndTime1;
/*     */   protected String signOutTime1;
/*  41 */   public final String datePattern = "yyyy-MM-dd HH:mm:ss";
/*     */ 
/*  43 */   private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
/*     */ 
/*     */   public DutySection()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DutySection(Long in_sectionId)
/*     */   {
/*  58 */     setSectionId(in_sectionId);
/*     */   }
/*     */ 
/*     */   public Long getSectionId()
/*     */   {
/*  68 */     return this.sectionId;
/*     */   }
/*     */ 
/*     */   public void setSectionId(Long aValue)
/*     */   {
/*  75 */     this.sectionId = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartSignin()
/*     */   {
/*  83 */     return this.startSignin;
/*     */   }
/*     */ 
/*     */   public void setStartSignin(Date aValue)
/*     */   {
/*  91 */     this.startSignin = aValue;
/*     */   }
/*     */ 
/*     */   public void setStartSignin1(String inVal)
/*     */   {
/*  99 */     this.startSignin1 = inVal;
/* 100 */     String finalVal = "1900-01-01 " + inVal + ":00";
/*     */     try {
/* 102 */       this.startSignin = DateUtils.parseDate(finalVal, new String[] { "yyyy-MM-dd HH:mm:ss" });
/*     */     } catch (Exception ex) {
/* 104 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getStartSignin1() {
/* 109 */     return this.sdf.format(this.startSignin);
/*     */   }
/*     */ 
/*     */   public String getDutyStartTime1() {
/* 113 */     return this.sdf.format(this.dutyStartTime);
/*     */   }
/*     */ 
/*     */   public String getEndSignin1() {
/* 117 */     return this.sdf.format(this.endSignin);
/*     */   }
/*     */ 
/*     */   public String getEarlyOffTime1() {
/* 121 */     return this.sdf.format(this.earlyOffTime);
/*     */   }
/*     */ 
/*     */   public String getDutyEndTime1() {
/* 125 */     return this.sdf.format(this.dutyEndTime);
/*     */   }
/*     */ 
/*     */   public String getSignOutTime1() {
/* 129 */     return this.sdf.format(this.signOutTime);
/*     */   }
/*     */ 
/*     */   public Date getDutyStartTime()
/*     */   {
/* 137 */     return this.dutyStartTime;
/*     */   }
/*     */ 
/*     */   public void setDutyStartTime(Date aValue)
/*     */   {
/* 145 */     this.dutyStartTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getSectionName() {
/* 149 */     return this.sectionName;
/*     */   }
/*     */ 
/*     */   public void setSectionName(String sectionName) {
/* 153 */     this.sectionName = sectionName;
/*     */   }
/*     */ 
/*     */   public void setDutyStartTime1(String inVal)
/*     */   {
/* 161 */     this.dutyStartTime1 = inVal;
/* 162 */     String finalVal = "1900-01-01 " + inVal + ":00";
/*     */     try {
/* 164 */       this.dutyStartTime = DateUtils.parseDate(finalVal, new String[] { "yyyy-MM-dd HH:mm:ss" });
/*     */     } catch (Exception ex) {
/* 166 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getEndSignin()
/*     */   {
/* 175 */     return this.endSignin;
/*     */   }
/*     */ 
/*     */   public void setEndSignin(Date aValue)
/*     */   {
/* 183 */     this.endSignin = aValue;
/*     */   }
/*     */ 
/*     */   public void setEndSignin1(String inVal)
/*     */   {
/* 191 */     this.endSignin1 = inVal;
/* 192 */     String finalVal = "1900-01-01 " + inVal + ":00";
/*     */     try {
/* 194 */       this.endSignin = DateUtils.parseDate(finalVal, new String[] { "yyyy-MM-dd HH:mm:ss" });
/*     */     } catch (Exception ex) {
/* 196 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getEarlyOffTime()
/*     */   {
/* 205 */     return this.earlyOffTime;
/*     */   }
/*     */ 
/*     */   public void setEarlyOffTime(Date aValue)
/*     */   {
/* 213 */     this.earlyOffTime = aValue;
/*     */   }
/*     */ 
/*     */   public void setEarlyOffTime1(String inVal)
/*     */   {
/* 221 */     this.earlyOffTime1 = inVal;
/* 222 */     String finalVal = "1900-01-01 " + inVal + ":00";
/*     */     try {
/* 224 */       this.earlyOffTime = DateUtils.parseDate(finalVal, new String[] { "yyyy-MM-dd HH:mm:ss" });
/*     */     } catch (Exception ex) {
/* 226 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getDutyEndTime()
/*     */   {
/* 235 */     return this.dutyEndTime;
/*     */   }
/*     */ 
/*     */   public void setDutyEndTime(Date aValue)
/*     */   {
/* 243 */     this.dutyEndTime = aValue;
/*     */   }
/*     */ 
/*     */   public void setDutyEndTime1(String inVal)
/*     */   {
/* 251 */     this.dutyEndTime1 = inVal;
/* 252 */     String finalVal = "1900-01-01 " + inVal + ":00";
/*     */     try {
/* 254 */       this.dutyEndTime = DateUtils.parseDate(finalVal, new String[] { "yyyy-MM-dd HH:mm:ss" });
/*     */     } catch (Exception ex) {
/* 256 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Date getSignOutTime()
/*     */   {
/* 265 */     return this.signOutTime;
/*     */   }
/*     */ 
/*     */   public void setSignOutTime(Date aValue)
/*     */   {
/* 273 */     this.signOutTime = aValue;
/*     */   }
/*     */ 
/*     */   public void setSignOutTime1(String inVal)
/*     */   {
/* 281 */     this.signOutTime1 = inVal;
/* 282 */     String finalVal = "1900-01-01 " + inVal + ":00";
/*     */     try {
/* 284 */       this.signOutTime = DateUtils.parseDate(finalVal, new String[] { "yyyy-MM-dd HH:mm:ss" });
/*     */     } catch (Exception ex) {
/* 286 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 294 */     if (!(object instanceof DutySection)) {
/* 295 */       return false;
/*     */     }
/* 297 */     DutySection rhs = (DutySection)object;
/* 298 */     return new EqualsBuilder()
/* 299 */       .append(this.sectionId, rhs.sectionId)
/* 300 */       .append(this.startSignin, rhs.startSignin)
/* 301 */       .append(this.dutyStartTime, rhs.dutyStartTime)
/* 302 */       .append(this.endSignin, rhs.endSignin)
/* 303 */       .append(this.earlyOffTime, rhs.earlyOffTime)
/* 304 */       .append(this.dutyEndTime, rhs.dutyEndTime)
/* 305 */       .append(this.signOutTime, rhs.signOutTime)
/* 306 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 313 */     return new HashCodeBuilder(-82280557, -700257973)
/* 314 */       .append(this.sectionId)
/* 315 */       .append(this.startSignin)
/* 316 */       .append(this.dutyStartTime)
/* 317 */       .append(this.endSignin)
/* 318 */       .append(this.earlyOffTime)
/* 319 */       .append(this.dutyEndTime)
/* 320 */       .append(this.signOutTime)
/* 321 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 328 */     return new ToStringBuilder(this)
/* 329 */       .append("sectionId", this.sectionId)
/* 330 */       .append("startSignin", this.startSignin)
/* 331 */       .append("dutyStartTime", this.dutyStartTime)
/* 332 */       .append("endSignin", this.endSignin)
/* 333 */       .append("earlyOffTime", this.earlyOffTime)
/* 334 */       .append("dutyEndTime", this.dutyEndTime)
/* 335 */       .append("signOutTime", this.signOutTime)
/* 336 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.personal.DutySection
 * JD-Core Version:    0.6.0
 */
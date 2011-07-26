/*     */ package com.xpsoft.oa.model.task;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Appointment extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long appointId;
/*     */ 
/*     */   @Expose
/*     */   protected String subject;
/*     */ 
/*     */   @Expose
/*     */   protected Date startTime;
/*     */ 
/*     */   @Expose
/*     */   protected Date endTime;
/*     */ 
/*     */   @Expose
/*     */   protected String content;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */ 
/*     */   @Expose
/*     */   protected String location;
/*     */ 
/*     */   @Expose
/*     */   protected String inviteEmails;
/*     */ 
/*     */   @Expose
/*     */   protected AppUser appUser;
/*     */ 
/*     */   public Appointment()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Appointment(Long in_appointId)
/*     */   {
/*  55 */     setAppointId(in_appointId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  60 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  64 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Long getAppointId()
/*     */   {
/*  73 */     return this.appointId;
/*     */   }
/*     */ 
/*     */   public void setAppointId(Long aValue)
/*     */   {
/*  80 */     this.appointId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/*  87 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/*  94 */     if (aValue == null) {
/*  95 */       this.appUser = null;
/*  96 */     } else if (this.appUser == null) {
/*  97 */       this.appUser = new AppUser(aValue);
/*  98 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 100 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 109 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String aValue)
/*     */   {
/* 117 */     this.subject = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartTime()
/*     */   {
/* 125 */     return this.startTime;
/*     */   }
/*     */ 
/*     */   public void setStartTime(Date aValue)
/*     */   {
/* 133 */     this.startTime = aValue;
/*     */   }
/*     */ 
/*     */   public Date getEndTime()
/*     */   {
/* 141 */     return this.endTime;
/*     */   }
/*     */ 
/*     */   public void setEndTime(Date aValue)
/*     */   {
/* 149 */     this.endTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 157 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String aValue)
/*     */   {
/* 165 */     this.content = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 173 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 180 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public String getLocation()
/*     */   {
/* 188 */     return this.location;
/*     */   }
/*     */ 
/*     */   public void setLocation(String aValue)
/*     */   {
/* 196 */     this.location = aValue;
/*     */   }
/*     */ 
/*     */   public String getInviteEmails()
/*     */   {
/* 204 */     return this.inviteEmails;
/*     */   }
/*     */ 
/*     */   public void setInviteEmails(String aValue)
/*     */   {
/* 211 */     this.inviteEmails = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 218 */     if (!(object instanceof Appointment)) {
/* 219 */       return false;
/*     */     }
/* 221 */     Appointment rhs = (Appointment)object;
/* 222 */     return new EqualsBuilder()
/* 223 */       .append(this.appointId, rhs.appointId)
/* 224 */       .append(this.subject, rhs.subject)
/* 225 */       .append(this.startTime, rhs.startTime)
/* 226 */       .append(this.endTime, rhs.endTime)
/* 227 */       .append(this.content, rhs.content)
/* 228 */       .append(this.notes, rhs.notes)
/* 229 */       .append(this.location, rhs.location)
/* 230 */       .append(this.inviteEmails, rhs.inviteEmails)
/* 231 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 238 */     return new HashCodeBuilder(-82280557, -700257973)
/* 239 */       .append(this.appointId)
/* 240 */       .append(this.subject)
/* 241 */       .append(this.startTime)
/* 242 */       .append(this.endTime)
/* 243 */       .append(this.content)
/* 244 */       .append(this.notes)
/* 245 */       .append(this.location)
/* 246 */       .append(this.inviteEmails)
/* 247 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 254 */     return new ToStringBuilder(this)
/* 255 */       .append("appointId", this.appointId)
/* 256 */       .append("subject", this.subject)
/* 257 */       .append("startTime", this.startTime)
/* 258 */       .append("endTime", this.endTime)
/* 259 */       .append("content", this.content)
/* 260 */       .append("notes", this.notes)
/* 261 */       .append("location", this.location)
/* 262 */       .append("inviteEmails", this.inviteEmails)
/* 263 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.task.Appointment
 * JD-Core Version:    0.6.0
 */
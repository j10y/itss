/*     */ package com.htsoft.oa.model.communicate;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class PhoneBook extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long phoneId;
/*     */ 
/*     */   @Expose
/*     */   protected String fullname;
/*     */ 
/*     */   @Expose
/*     */   protected String title;
/*     */ 
/*     */   @Expose
/*     */   protected Date birthday;
/*     */ 
/*     */   @Expose
/*     */   protected String nickName;
/*     */ 
/*     */   @Expose
/*     */   protected String duty;
/*     */ 
/*     */   @Expose
/*     */   protected String spouse;
/*     */ 
/*     */   @Expose
/*     */   protected String childs;
/*     */ 
/*     */   @Expose
/*     */   protected String companyName;
/*     */ 
/*     */   @Expose
/*     */   protected String companyAddress;
/*     */ 
/*     */   @Expose
/*     */   protected String companyPhone;
/*     */ 
/*     */   @Expose
/*     */   protected String companyFax;
/*     */ 
/*     */   @Expose
/*     */   protected String homeAddress;
/*     */ 
/*     */   @Expose
/*     */   protected String homeZip;
/*     */ 
/*     */   @Expose
/*     */   protected String mobile;
/*     */ 
/*     */   @Expose
/*     */   protected String phone;
/*     */ 
/*     */   @Expose
/*     */   protected String email;
/*     */ 
/*     */   @Expose
/*     */   protected String qqNumber;
/*     */ 
/*     */   @Expose
/*     */   protected String msn;
/*     */ 
/*     */   @Expose
/*     */   protected String note;
/*     */ 
/*     */   @Expose
/*     */   protected Short isShared;
/*     */   protected AppUser appUser;
/*     */ 
/*     */   @Expose
/*     */   protected PhoneGroup phoneGroup;
/*     */ 
/*     */   public PhoneBook()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PhoneBook(Long in_phoneId)
/*     */   {
/*  82 */     setPhoneId(in_phoneId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  87 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  91 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public PhoneGroup getPhoneGroup() {
/*  95 */     return this.phoneGroup;
/*     */   }
/*     */ 
/*     */   public void setPhoneGroup(PhoneGroup in_phoneGroup) {
/*  99 */     this.phoneGroup = in_phoneGroup;
/*     */   }
/*     */ 
/*     */   public Long getPhoneId()
/*     */   {
/* 108 */     return this.phoneId;
/*     */   }
/*     */ 
/*     */   public void setPhoneId(Long aValue)
/*     */   {
/* 115 */     this.phoneId = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 123 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 131 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 141 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String aValue)
/*     */   {
/* 149 */     this.title = aValue;
/*     */   }
/*     */ 
/*     */   public Date getBirthday()
/*     */   {
/* 157 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public void setBirthday(Date aValue)
/*     */   {
/* 164 */     this.birthday = aValue;
/*     */   }
/*     */ 
/*     */   public String getNickName()
/*     */   {
/* 172 */     return this.nickName;
/*     */   }
/*     */ 
/*     */   public void setNickName(String aValue)
/*     */   {
/* 179 */     this.nickName = aValue;
/*     */   }
/*     */ 
/*     */   public String getDuty()
/*     */   {
/* 187 */     return this.duty;
/*     */   }
/*     */ 
/*     */   public void setDuty(String aValue)
/*     */   {
/* 194 */     this.duty = aValue;
/*     */   }
/*     */ 
/*     */   public String getSpouse()
/*     */   {
/* 202 */     return this.spouse;
/*     */   }
/*     */ 
/*     */   public void setSpouse(String aValue)
/*     */   {
/* 209 */     this.spouse = aValue;
/*     */   }
/*     */ 
/*     */   public String getChilds()
/*     */   {
/* 217 */     return this.childs;
/*     */   }
/*     */ 
/*     */   public void setChilds(String aValue)
/*     */   {
/* 224 */     this.childs = aValue;
/*     */   }
/*     */ 
/*     */   public String getCompanyName()
/*     */   {
/* 232 */     return this.companyName;
/*     */   }
/*     */ 
/*     */   public void setCompanyName(String aValue)
/*     */   {
/* 239 */     this.companyName = aValue;
/*     */   }
/*     */ 
/*     */   public String getCompanyAddress()
/*     */   {
/* 247 */     return this.companyAddress;
/*     */   }
/*     */ 
/*     */   public void setCompanyAddress(String aValue)
/*     */   {
/* 254 */     this.companyAddress = aValue;
/*     */   }
/*     */ 
/*     */   public String getCompanyPhone()
/*     */   {
/* 262 */     return this.companyPhone;
/*     */   }
/*     */ 
/*     */   public void setCompanyPhone(String aValue)
/*     */   {
/* 269 */     this.companyPhone = aValue;
/*     */   }
/*     */ 
/*     */   public String getCompanyFax()
/*     */   {
/* 277 */     return this.companyFax;
/*     */   }
/*     */ 
/*     */   public void setCompanyFax(String aValue)
/*     */   {
/* 284 */     this.companyFax = aValue;
/*     */   }
/*     */ 
/*     */   public String getHomeAddress()
/*     */   {
/* 292 */     return this.homeAddress;
/*     */   }
/*     */ 
/*     */   public void setHomeAddress(String aValue)
/*     */   {
/* 299 */     this.homeAddress = aValue;
/*     */   }
/*     */ 
/*     */   public String getHomeZip()
/*     */   {
/* 307 */     return this.homeZip;
/*     */   }
/*     */ 
/*     */   public void setHomeZip(String aValue)
/*     */   {
/* 314 */     this.homeZip = aValue;
/*     */   }
/*     */ 
/*     */   public String getMobile()
/*     */   {
/* 322 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String aValue)
/*     */   {
/* 329 */     this.mobile = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 337 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 344 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 352 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String aValue)
/*     */   {
/* 359 */     this.email = aValue;
/*     */   }
/*     */ 
/*     */   public String getQqNumber()
/*     */   {
/* 367 */     return this.qqNumber;
/*     */   }
/*     */ 
/*     */   public void setQqNumber(String aValue)
/*     */   {
/* 374 */     this.qqNumber = aValue;
/*     */   }
/*     */ 
/*     */   public String getMsn()
/*     */   {
/* 382 */     return this.msn;
/*     */   }
/*     */ 
/*     */   public void setMsn(String aValue)
/*     */   {
/* 389 */     this.msn = aValue;
/*     */   }
/*     */ 
/*     */   public String getNote()
/*     */   {
/* 397 */     return this.note;
/*     */   }
/*     */ 
/*     */   public void setNote(String aValue)
/*     */   {
/* 404 */     this.note = aValue;
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 411 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 418 */     if (aValue == null) {
/* 419 */       this.appUser = null;
/* 420 */     } else if (this.appUser == null) {
/* 421 */       this.appUser = new AppUser(aValue);
/* 422 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 424 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getGroupId()
/*     */   {
/* 432 */     return getPhoneGroup() == null ? null : getPhoneGroup().getGroupId();
/*     */   }
/*     */ 
/*     */   public void setGroupId(Long aValue)
/*     */   {
/* 439 */     if (aValue == null) {
/* 440 */       this.phoneGroup = null;
/* 441 */     } else if (this.phoneGroup == null) {
/* 442 */       this.phoneGroup = new PhoneGroup(aValue);
/* 443 */       this.phoneGroup.setVersion(new Integer(0));
/*     */     } else {
/* 445 */       this.phoneGroup.setGroupId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Short getIsShared()
/*     */   {
/* 454 */     return this.isShared;
/*     */   }
/*     */ 
/*     */   public void setIsShared(Short aValue)
/*     */   {
/* 462 */     this.isShared = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 469 */     if (!(object instanceof PhoneBook)) {
/* 470 */       return false;
/*     */     }
/* 472 */     PhoneBook rhs = (PhoneBook)object;
/* 473 */     return new EqualsBuilder()
/* 474 */       .append(this.phoneId, rhs.phoneId)
/* 475 */       .append(this.fullname, rhs.fullname)
/* 476 */       .append(this.title, rhs.title)
/* 477 */       .append(this.birthday, rhs.birthday)
/* 478 */       .append(this.nickName, rhs.nickName)
/* 479 */       .append(this.duty, rhs.duty)
/* 480 */       .append(this.spouse, rhs.spouse)
/* 481 */       .append(this.childs, rhs.childs)
/* 482 */       .append(this.companyName, rhs.companyName)
/* 483 */       .append(this.companyAddress, rhs.companyAddress)
/* 484 */       .append(this.companyPhone, rhs.companyPhone)
/* 485 */       .append(this.companyFax, rhs.companyFax)
/* 486 */       .append(this.homeAddress, rhs.homeAddress)
/* 487 */       .append(this.homeZip, rhs.homeZip)
/* 488 */       .append(this.mobile, rhs.mobile)
/* 489 */       .append(this.phone, rhs.phone)
/* 490 */       .append(this.email, rhs.email)
/* 491 */       .append(this.qqNumber, rhs.qqNumber)
/* 492 */       .append(this.msn, rhs.msn)
/* 493 */       .append(this.note, rhs.note)
/* 494 */       .append(this.isShared, rhs.isShared)
/* 495 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 502 */     return new HashCodeBuilder(-82280557, -700257973)
/* 503 */       .append(this.phoneId)
/* 504 */       .append(this.fullname)
/* 505 */       .append(this.title)
/* 506 */       .append(this.birthday)
/* 507 */       .append(this.nickName)
/* 508 */       .append(this.duty)
/* 509 */       .append(this.spouse)
/* 510 */       .append(this.childs)
/* 511 */       .append(this.companyName)
/* 512 */       .append(this.companyAddress)
/* 513 */       .append(this.companyPhone)
/* 514 */       .append(this.companyFax)
/* 515 */       .append(this.homeAddress)
/* 516 */       .append(this.homeZip)
/* 517 */       .append(this.mobile)
/* 518 */       .append(this.phone)
/* 519 */       .append(this.email)
/* 520 */       .append(this.qqNumber)
/* 521 */       .append(this.msn)
/* 522 */       .append(this.note)
/* 523 */       .append(this.isShared)
/* 524 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 531 */     return new ToStringBuilder(this)
/* 532 */       .append("phoneId", this.phoneId)
/* 533 */       .append("fullname", this.fullname)
/* 534 */       .append("title", this.title)
/* 535 */       .append("birthday", this.birthday)
/* 536 */       .append("nickName", this.nickName)
/* 537 */       .append("duty", this.duty)
/* 538 */       .append("spouse", this.spouse)
/* 539 */       .append("childs", this.childs)
/* 540 */       .append("companyName", this.companyName)
/* 541 */       .append("companyAddress", this.companyAddress)
/* 542 */       .append("companyPhone", this.companyPhone)
/* 543 */       .append("companyFax", this.companyFax)
/* 544 */       .append("homeAddress", this.homeAddress)
/* 545 */       .append("homeZip", this.homeZip)
/* 546 */       .append("mobile", this.mobile)
/* 547 */       .append("phone", this.phone)
/* 548 */       .append("email", this.email)
/* 549 */       .append("qqNumber", this.qqNumber)
/* 550 */       .append("msn", this.msn)
/* 551 */       .append("note", this.note)
/* 552 */       .append("isShared", this.isShared)
/* 553 */       .toString();
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 560 */     return "phoneId";
/*     */   }
/*     */ 
/*     */   public Long getId()
/*     */   {
/* 567 */     return this.phoneId;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.communicate.PhoneBook
 * JD-Core Version:    0.6.0
 */
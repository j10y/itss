/*     */ package com.xpsoft.oa.model.customer;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class CusLinkman extends BaseModel
/*     */ {
/*     */   protected Long linkmanId;
/*     */   protected String fullname;
/*     */   protected Short sex;
/*     */   protected String position;
/*     */   protected String phone;
/*     */   protected String mobile;
/*     */   protected String email;
/*     */   protected String msn;
/*     */   protected String qq;
/*     */   protected String fax;
/*     */   protected Date birthday;
/*     */   protected String homeAddress;
/*     */   protected String homeZip;
/*     */   protected String homePhone;
/*     */   protected String hobby;
/*     */   protected Short isPrimary;
/*     */   protected String notes;
/*     */   protected Customer customer;
/*     */ 
/*     */   public CusLinkman()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CusLinkman(Long in_linkmanId)
/*     */   {
/*  54 */     setLinkmanId(in_linkmanId);
/*     */   }
/*     */ 
/*     */   public Customer getCustomer()
/*     */   {
/*  59 */     return this.customer;
/*     */   }
/*     */ 
/*     */   public void setCustomer(Customer in_customer) {
/*  63 */     this.customer = in_customer;
/*     */   }
/*     */ 
/*     */   public Long getLinkmanId()
/*     */   {
/*  72 */     return this.linkmanId;
/*     */   }
/*     */ 
/*     */   public void setLinkmanId(Long aValue)
/*     */   {
/*  79 */     this.linkmanId = aValue;
/*     */   }
/*     */ 
/*     */   public Long getCustomerId()
/*     */   {
/*  86 */     return getCustomer() == null ? null : getCustomer().getCustomerId();
/*     */   }
/*     */ 
/*     */   public void setCustomerId(Long aValue)
/*     */   {
/*  93 */     if (aValue == null) {
/*  94 */       this.customer = null;
/*  95 */     } else if (this.customer == null) {
/*  96 */       this.customer = new Customer(aValue);
/*  97 */       this.customer.setVersion(new Integer(0));
/*     */     } else {
/*  99 */       this.customer.setCustomerId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 108 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 116 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public Short getSex()
/*     */   {
/* 124 */     return this.sex;
/*     */   }
/*     */ 
/*     */   public void setSex(Short aValue)
/*     */   {
/* 132 */     this.sex = aValue;
/*     */   }
/*     */ 
/*     */   public String getPosition()
/*     */   {
/* 140 */     return this.position;
/*     */   }
/*     */ 
/*     */   public void setPosition(String aValue)
/*     */   {
/* 147 */     this.position = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 155 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 162 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getMobile()
/*     */   {
/* 170 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String aValue)
/*     */   {
/* 178 */     this.mobile = aValue;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 186 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String aValue)
/*     */   {
/* 193 */     this.email = aValue;
/*     */   }
/*     */ 
/*     */   public String getMsn()
/*     */   {
/* 201 */     return this.msn;
/*     */   }
/*     */ 
/*     */   public void setMsn(String msn) {
/* 205 */     this.msn = msn;
/*     */   }
/*     */ 
/*     */   public Date getBirthday()
/*     */   {
/* 219 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public String getQq() {
/* 223 */     return this.qq;
/*     */   }
/*     */ 
/*     */   public void setQq(String qq) {
/* 227 */     this.qq = qq;
/*     */   }
/*     */ 
/*     */   public String getFax() {
/* 231 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String fax) {
/* 235 */     this.fax = fax;
/*     */   }
/*     */ 
/*     */   public void setBirthday(Date aValue)
/*     */   {
/* 242 */     this.birthday = aValue;
/*     */   }
/*     */ 
/*     */   public String getHomeAddress()
/*     */   {
/* 250 */     return this.homeAddress;
/*     */   }
/*     */ 
/*     */   public void setHomeAddress(String aValue)
/*     */   {
/* 257 */     this.homeAddress = aValue;
/*     */   }
/*     */ 
/*     */   public String getHomeZip()
/*     */   {
/* 265 */     return this.homeZip;
/*     */   }
/*     */ 
/*     */   public void setHomeZip(String aValue)
/*     */   {
/* 272 */     this.homeZip = aValue;
/*     */   }
/*     */ 
/*     */   public String getHomePhone()
/*     */   {
/* 280 */     return this.homePhone;
/*     */   }
/*     */ 
/*     */   public void setHomePhone(String aValue)
/*     */   {
/* 287 */     this.homePhone = aValue;
/*     */   }
/*     */ 
/*     */   public String getHobby()
/*     */   {
/* 295 */     return this.hobby;
/*     */   }
/*     */ 
/*     */   public void setHobby(String aValue)
/*     */   {
/* 302 */     this.hobby = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsPrimary()
/*     */   {
/* 310 */     return this.isPrimary;
/*     */   }
/*     */ 
/*     */   public void setIsPrimary(Short aValue)
/*     */   {
/* 318 */     this.isPrimary = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 326 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 333 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 349 */     if (!(object instanceof CusLinkman)) {
/* 350 */       return false;
/*     */     }
/* 352 */     CusLinkman rhs = (CusLinkman)object;
/* 353 */     return new EqualsBuilder()
/* 354 */       .append(this.linkmanId, rhs.linkmanId)
/* 355 */       .append(this.fullname, rhs.fullname)
/* 356 */       .append(this.sex, rhs.sex)
/* 357 */       .append(this.position, rhs.position)
/* 358 */       .append(this.phone, rhs.phone)
/* 359 */       .append(this.mobile, rhs.mobile)
/* 360 */       .append(this.email, rhs.email)
/* 361 */       .append(this.msn, rhs.msn)
/* 362 */       .append(this.qq, rhs.qq)
/* 363 */       .append(this.fax, rhs.fax)
/* 364 */       .append(this.birthday, rhs.birthday)
/* 365 */       .append(this.homeAddress, rhs.homeAddress)
/* 366 */       .append(this.homeZip, rhs.homeZip)
/* 367 */       .append(this.homePhone, rhs.homePhone)
/* 368 */       .append(this.hobby, rhs.hobby)
/* 369 */       .append(this.isPrimary, rhs.isPrimary)
/* 370 */       .append(this.notes, rhs.notes)
/* 372 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 379 */     return new HashCodeBuilder(-82280557, -700257973)
/* 380 */       .append(this.linkmanId)
/* 381 */       .append(this.fullname)
/* 382 */       .append(this.sex)
/* 383 */       .append(this.position)
/* 384 */       .append(this.phone)
/* 385 */       .append(this.mobile)
/* 386 */       .append(this.email)
/* 387 */       .append(this.msn)
/* 388 */       .append(this.qq)
/* 389 */       .append(this.fax)
/* 390 */       .append(this.birthday)
/* 391 */       .append(this.homeAddress)
/* 392 */       .append(this.homeZip)
/* 393 */       .append(this.homePhone)
/* 394 */       .append(this.hobby)
/* 395 */       .append(this.isPrimary)
/* 396 */       .append(this.notes)
/* 398 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 405 */     return new ToStringBuilder(this)
/* 406 */       .append("linkmanId", this.linkmanId)
/* 407 */       .append("fullname", this.fullname)
/* 408 */       .append("sex", this.sex)
/* 409 */       .append("position", this.position)
/* 410 */       .append("phone", this.phone)
/* 411 */       .append("mobile", this.mobile)
/* 412 */       .append("email", this.email)
/* 413 */       .append("msn", this.msn)
/* 414 */       .append("qq", this.qq)
/* 415 */       .append("fax", this.fax)
/* 416 */       .append("birthday", this.birthday)
/* 417 */       .append("homeAddress", this.homeAddress)
/* 418 */       .append("homeZip", this.homeZip)
/* 419 */       .append("homePhone", this.homePhone)
/* 420 */       .append("hobby", this.hobby)
/* 421 */       .append("isPrimary", this.isPrimary)
/* 422 */       .append("notes", this.notes)
/* 424 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.customer.CusLinkman
 * JD-Core Version:    0.6.0
 */
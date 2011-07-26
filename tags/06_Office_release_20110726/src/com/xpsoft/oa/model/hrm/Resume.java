/*     */ package com.xpsoft.oa.model.hrm;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import com.xpsoft.oa.model.system.FileAttach;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Resume extends BaseModel
/*     */ {
/*  22 */   public static String PASS = "通过";
/*  23 */   public static String NOTPASS = "不通过";
/*  24 */   public static String READY_INTERVIEW = "准备安排面试";
/*  25 */   public static String PASS_INTERVIEW = "通过面试";
/*     */   protected Long resumeId;
/*     */   protected String fullname;
/*     */   protected Integer age;
/*     */   protected Date birthday;
/*     */   protected String address;
/*     */   protected String zip;
/*     */   protected String sex;
/*     */   protected String position;
/*     */   protected String phone;
/*     */   protected String mobile;
/*     */   protected String email;
/*     */   protected String hobby;
/*     */   protected String religion;
/*     */   protected String party;
/*     */   protected String nationality;
/*     */   protected String race;
/*     */   protected String birthPlace;
/*     */   protected String eduCollege;
/*     */   protected String eduDegree;
/*     */   protected String eduMajor;
/*     */   protected Date startWorkDate;
/*     */   protected String idNo;
/*     */   protected String photo;
/*     */   protected String status;
/*     */   protected String memo;
/*     */   protected String registor;
/*     */   protected Date regTime;
/*     */   protected String workCase;
/*     */   protected String trainCase;
/*     */   protected String projectCase;
/*  57 */   protected Set<FileAttach> resumeFiles = new HashSet();
/*     */ 
/*     */   public Resume()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Resume(Long in_resumeId)
/*     */   {
/*  72 */     setResumeId(in_resumeId);
/*     */   }
/*     */ 
/*     */   public Set getResumeFiles()
/*     */   {
/*  77 */     return this.resumeFiles;
/*     */   }
/*     */ 
/*     */   public void setResumeFiles(Set in_resumeFiles) {
/*  81 */     this.resumeFiles = in_resumeFiles;
/*     */   }
/*     */ 
/*     */   public Long getResumeId()
/*     */   {
/*  90 */     return this.resumeId;
/*     */   }
/*     */ 
/*     */   public void setResumeId(Long aValue)
/*     */   {
/*  97 */     this.resumeId = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 105 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 113 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getAge()
/*     */   {
/* 121 */     return this.age;
/*     */   }
/*     */ 
/*     */   public void setAge(Integer aValue)
/*     */   {
/* 128 */     this.age = aValue;
/*     */   }
/*     */ 
/*     */   public Date getBirthday()
/*     */   {
/* 136 */     return this.birthday;
/*     */   }
/*     */ 
/*     */   public void setBirthday(Date aValue)
/*     */   {
/* 143 */     this.birthday = aValue;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 151 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String aValue)
/*     */   {
/* 158 */     this.address = aValue;
/*     */   }
/*     */ 
/*     */   public String getZip()
/*     */   {
/* 166 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(String aValue)
/*     */   {
/* 173 */     this.zip = aValue;
/*     */   }
/*     */ 
/*     */   public String getSex()
/*     */   {
/* 181 */     return this.sex;
/*     */   }
/*     */ 
/*     */   public void setSex(String aValue)
/*     */   {
/* 188 */     this.sex = aValue;
/*     */   }
/*     */ 
/*     */   public String getPosition()
/*     */   {
/* 196 */     return this.position;
/*     */   }
/*     */ 
/*     */   public void setPosition(String aValue)
/*     */   {
/* 203 */     this.position = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 211 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 218 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getMobile()
/*     */   {
/* 226 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String aValue)
/*     */   {
/* 233 */     this.mobile = aValue;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 241 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String aValue)
/*     */   {
/* 248 */     this.email = aValue;
/*     */   }
/*     */ 
/*     */   public String getHobby()
/*     */   {
/* 256 */     return this.hobby;
/*     */   }
/*     */ 
/*     */   public void setHobby(String aValue)
/*     */   {
/* 263 */     this.hobby = aValue;
/*     */   }
/*     */ 
/*     */   public String getReligion()
/*     */   {
/* 271 */     return this.religion;
/*     */   }
/*     */ 
/*     */   public void setReligion(String aValue)
/*     */   {
/* 278 */     this.religion = aValue;
/*     */   }
/*     */ 
/*     */   public String getParty()
/*     */   {
/* 286 */     return this.party;
/*     */   }
/*     */ 
/*     */   public void setParty(String aValue)
/*     */   {
/* 293 */     this.party = aValue;
/*     */   }
/*     */ 
/*     */   public String getNationality()
/*     */   {
/* 301 */     return this.nationality;
/*     */   }
/*     */ 
/*     */   public void setNationality(String aValue)
/*     */   {
/* 308 */     this.nationality = aValue;
/*     */   }
/*     */ 
/*     */   public String getRace()
/*     */   {
/* 316 */     return this.race;
/*     */   }
/*     */ 
/*     */   public void setRace(String aValue)
/*     */   {
/* 323 */     this.race = aValue;
/*     */   }
/*     */ 
/*     */   public String getBirthPlace()
/*     */   {
/* 331 */     return this.birthPlace;
/*     */   }
/*     */ 
/*     */   public void setBirthPlace(String aValue)
/*     */   {
/* 338 */     this.birthPlace = aValue;
/*     */   }
/*     */ 
/*     */   public String getEduCollege()
/*     */   {
/* 346 */     return this.eduCollege;
/*     */   }
/*     */ 
/*     */   public void setEduCollege(String aValue)
/*     */   {
/* 353 */     this.eduCollege = aValue;
/*     */   }
/*     */ 
/*     */   public String getEduDegree()
/*     */   {
/* 361 */     return this.eduDegree;
/*     */   }
/*     */ 
/*     */   public void setEduDegree(String aValue)
/*     */   {
/* 368 */     this.eduDegree = aValue;
/*     */   }
/*     */ 
/*     */   public String getEduMajor()
/*     */   {
/* 376 */     return this.eduMajor;
/*     */   }
/*     */ 
/*     */   public void setEduMajor(String aValue)
/*     */   {
/* 383 */     this.eduMajor = aValue;
/*     */   }
/*     */ 
/*     */   public Date getStartWorkDate()
/*     */   {
/* 391 */     return this.startWorkDate;
/*     */   }
/*     */ 
/*     */   public void setStartWorkDate(Date aValue)
/*     */   {
/* 398 */     this.startWorkDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getIdNo()
/*     */   {
/* 406 */     return this.idNo;
/*     */   }
/*     */ 
/*     */   public void setIdNo(String aValue)
/*     */   {
/* 413 */     this.idNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhoto()
/*     */   {
/* 421 */     return this.photo;
/*     */   }
/*     */ 
/*     */   public void setPhoto(String aValue)
/*     */   {
/* 428 */     this.photo = aValue;
/*     */   }
/*     */ 
/*     */   public String getStatus()
/*     */   {
/* 443 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(String aValue)
/*     */   {
/* 450 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public String getMemo()
/*     */   {
/* 458 */     return this.memo;
/*     */   }
/*     */ 
/*     */   public void setMemo(String aValue)
/*     */   {
/* 465 */     this.memo = aValue;
/*     */   }
/*     */ 
/*     */   public String getRegistor()
/*     */   {
/* 473 */     return this.registor;
/*     */   }
/*     */ 
/*     */   public void setRegistor(String aValue)
/*     */   {
/* 480 */     this.registor = aValue;
/*     */   }
/*     */ 
/*     */   public Date getRegTime()
/*     */   {
/* 488 */     return this.regTime;
/*     */   }
/*     */ 
/*     */   public void setRegTime(Date aValue)
/*     */   {
/* 495 */     this.regTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getWorkCase()
/*     */   {
/* 503 */     return this.workCase;
/*     */   }
/*     */ 
/*     */   public void setWorkCase(String aValue)
/*     */   {
/* 510 */     this.workCase = aValue;
/*     */   }
/*     */ 
/*     */   public String getTrainCase()
/*     */   {
/* 518 */     return this.trainCase;
/*     */   }
/*     */ 
/*     */   public void setTrainCase(String aValue)
/*     */   {
/* 525 */     this.trainCase = aValue;
/*     */   }
/*     */ 
/*     */   public String getProjectCase()
/*     */   {
/* 533 */     return this.projectCase;
/*     */   }
/*     */ 
/*     */   public void setProjectCase(String aValue)
/*     */   {
/* 540 */     this.projectCase = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 547 */     if (!(object instanceof Resume)) {
/* 548 */       return false;
/*     */     }
/* 550 */     Resume rhs = (Resume)object;
/* 551 */     return new EqualsBuilder()
/* 552 */       .append(this.resumeId, rhs.resumeId)
/* 553 */       .append(this.fullname, rhs.fullname)
/* 554 */       .append(this.age, rhs.age)
/* 555 */       .append(this.birthday, rhs.birthday)
/* 556 */       .append(this.address, rhs.address)
/* 557 */       .append(this.zip, rhs.zip)
/* 558 */       .append(this.sex, rhs.sex)
/* 559 */       .append(this.position, rhs.position)
/* 560 */       .append(this.phone, rhs.phone)
/* 561 */       .append(this.mobile, rhs.mobile)
/* 562 */       .append(this.email, rhs.email)
/* 563 */       .append(this.hobby, rhs.hobby)
/* 564 */       .append(this.religion, rhs.religion)
/* 565 */       .append(this.party, rhs.party)
/* 566 */       .append(this.nationality, rhs.nationality)
/* 567 */       .append(this.race, rhs.race)
/* 568 */       .append(this.birthPlace, rhs.birthPlace)
/* 569 */       .append(this.eduCollege, rhs.eduCollege)
/* 570 */       .append(this.eduDegree, rhs.eduDegree)
/* 571 */       .append(this.eduMajor, rhs.eduMajor)
/* 572 */       .append(this.startWorkDate, rhs.startWorkDate)
/* 573 */       .append(this.idNo, rhs.idNo)
/* 574 */       .append(this.photo, rhs.photo)
/* 575 */       .append(this.status, rhs.status)
/* 576 */       .append(this.memo, rhs.memo)
/* 577 */       .append(this.registor, rhs.registor)
/* 578 */       .append(this.regTime, rhs.regTime)
/* 579 */       .append(this.workCase, rhs.workCase)
/* 580 */       .append(this.trainCase, rhs.trainCase)
/* 581 */       .append(this.projectCase, rhs.projectCase)
/* 582 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 589 */     return new HashCodeBuilder(-82280557, -700257973)
/* 590 */       .append(this.resumeId)
/* 591 */       .append(this.fullname)
/* 592 */       .append(this.age)
/* 593 */       .append(this.birthday)
/* 594 */       .append(this.address)
/* 595 */       .append(this.zip)
/* 596 */       .append(this.sex)
/* 597 */       .append(this.position)
/* 598 */       .append(this.phone)
/* 599 */       .append(this.mobile)
/* 600 */       .append(this.email)
/* 601 */       .append(this.hobby)
/* 602 */       .append(this.religion)
/* 603 */       .append(this.party)
/* 604 */       .append(this.nationality)
/* 605 */       .append(this.race)
/* 606 */       .append(this.birthPlace)
/* 607 */       .append(this.eduCollege)
/* 608 */       .append(this.eduDegree)
/* 609 */       .append(this.eduMajor)
/* 610 */       .append(this.startWorkDate)
/* 611 */       .append(this.idNo)
/* 612 */       .append(this.photo)
/* 613 */       .append(this.status)
/* 614 */       .append(this.memo)
/* 615 */       .append(this.registor)
/* 616 */       .append(this.regTime)
/* 617 */       .append(this.workCase)
/* 618 */       .append(this.trainCase)
/* 619 */       .append(this.projectCase)
/* 620 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 627 */     return new ToStringBuilder(this)
/* 628 */       .append("resumeId", this.resumeId)
/* 629 */       .append("fullname", this.fullname)
/* 630 */       .append("age", this.age)
/* 631 */       .append("birthday", this.birthday)
/* 632 */       .append("address", this.address)
/* 633 */       .append("zip", this.zip)
/* 634 */       .append("sex", this.sex)
/* 635 */       .append("position", this.position)
/* 636 */       .append("phone", this.phone)
/* 637 */       .append("mobile", this.mobile)
/* 638 */       .append("email", this.email)
/* 639 */       .append("hobby", this.hobby)
/* 640 */       .append("religion", this.religion)
/* 641 */       .append("party", this.party)
/* 642 */       .append("nationality", this.nationality)
/* 643 */       .append("race", this.race)
/* 644 */       .append("birthPlace", this.birthPlace)
/* 645 */       .append("eduCollege", this.eduCollege)
/* 646 */       .append("eduDegree", this.eduDegree)
/* 647 */       .append("eduMajor", this.eduMajor)
/* 648 */       .append("startWorkDate", this.startWorkDate)
/* 649 */       .append("idNo", this.idNo)
/* 650 */       .append("photo", this.photo)
/* 651 */       .append("status", this.status)
/* 652 */       .append("memo", this.memo)
/* 653 */       .append("registor", this.registor)
/* 654 */       .append("regTime", this.regTime)
/* 655 */       .append("workCase", this.workCase)
/* 656 */       .append("trainCase", this.trainCase)
/* 657 */       .append("projectCase", this.projectCase)
/* 658 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.hrm.Resume
 * JD-Core Version:    0.6.0
 */
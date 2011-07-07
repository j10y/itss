/*     */ package com.htsoft.oa.model.customer;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Customer extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long customerId;
/*     */ 
/*     */   @Expose
/*     */   protected String customerNo;
/*     */ 
/*     */   @Expose
/*     */   protected String industryType;
/*     */ 
/*     */   @Expose
/*     */   protected String customerSource;
/*     */ 
/*     */   @Expose
/*     */   protected Short customerType;
/*     */ 
/*     */   @Expose
/*     */   protected Integer companyScale;
/*     */ 
/*     */   @Expose
/*     */   protected String customerName;
/*     */ 
/*     */   @Expose
/*     */   protected String customerManager;
/*     */ 
/*     */   @Expose
/*     */   protected String phone;
/*     */ 
/*     */   @Expose
/*     */   protected String fax;
/*     */ 
/*     */   @Expose
/*     */   protected String site;
/*     */ 
/*     */   @Expose
/*     */   protected String email;
/*     */ 
/*     */   @Expose
/*     */   protected String state;
/*     */ 
/*     */   @Expose
/*     */   protected String city;
/*     */ 
/*     */   @Expose
/*     */   protected String zip;
/*     */ 
/*     */   @Expose
/*     */   protected String address;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal registerFun;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal turnOver;
/*     */ 
/*     */   @Expose
/*     */   protected String currencyUnit;
/*     */ 
/*     */   @Expose
/*     */   protected String otherDesc;
/*     */ 
/*     */   @Expose
/*     */   protected String principal;
/*     */ 
/*     */   @Expose
/*     */   protected String openBank;
/*     */ 
/*     */   @Expose
/*     */   protected String accountsNo;
/*     */ 
/*     */   @Expose
/*     */   protected String taxNo;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */ 
/*     */   @Expose
/*     */   protected Short rights;
/*  75 */   protected Set<CusLinkman> cusLinkmans = new HashSet();
/*     */ 
/*  77 */   protected Set<Project> projects = new HashSet();
/*     */ 
/*  79 */   protected Set<CusConnection> cusConnections = new HashSet();
/*     */ 
/*     */   public Customer()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Customer(Long in_customerId)
/*     */   {
/*  94 */     setCustomerId(in_customerId);
/*     */   }
/*     */ 
/*     */   public Set<CusLinkman> getCusLinkmans()
/*     */   {
/*  99 */     return this.cusLinkmans;
/*     */   }
/*     */ 
/*     */   public void setCusLinkmans(Set<CusLinkman> in_cusLinkmans) {
/* 103 */     this.cusLinkmans = in_cusLinkmans;
/*     */   }
/*     */ 
/*     */   public Set<Project> getProjects()
/*     */   {
/* 108 */     return this.projects;
/*     */   }
/*     */ 
/*     */   public void setProjects(Set<Project> projects) {
/* 112 */     this.projects = projects;
/*     */   }
/*     */ 
/*     */   public Set<CusConnection> getCusConnections() {
/* 116 */     return this.cusConnections;
/*     */   }
/*     */ 
/*     */   public void setCusConnections(Set<CusConnection> cusConnections) {
/* 120 */     this.cusConnections = cusConnections;
/*     */   }
/*     */ 
/*     */   public Long getCustomerId()
/*     */   {
/* 128 */     return this.customerId;
/*     */   }
/*     */ 
/*     */   public void setCustomerId(Long aValue)
/*     */   {
/* 135 */     this.customerId = aValue;
/*     */   }
/*     */ 
/*     */   public String getCustomerNo()
/*     */   {
/* 144 */     return this.customerNo;
/*     */   }
/*     */ 
/*     */   public void setCustomerNo(String aValue)
/*     */   {
/* 152 */     this.customerNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getIndustryType()
/*     */   {
/* 161 */     return this.industryType;
/*     */   }
/*     */ 
/*     */   public void setIndustryType(String aValue)
/*     */   {
/* 169 */     this.industryType = aValue;
/*     */   }
/*     */ 
/*     */   public String getCustomerSource()
/*     */   {
/* 182 */     return this.customerSource;
/*     */   }
/*     */ 
/*     */   public void setCustomerSource(String aValue)
/*     */   {
/* 189 */     this.customerSource = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getCompanyScale() {
/* 193 */     return this.companyScale;
/*     */   }
/*     */ 
/*     */   public void setCompanyScale(Integer companyScale) {
/* 197 */     this.companyScale = companyScale;
/*     */   }
/*     */ 
/*     */   public Short getCustomerType()
/*     */   {
/* 208 */     return this.customerType;
/*     */   }
/*     */ 
/*     */   public void setCustomerType(Short aValue)
/*     */   {
/* 216 */     this.customerType = aValue;
/*     */   }
/*     */ 
/*     */   public String getCustomerName()
/*     */   {
/* 225 */     return this.customerName;
/*     */   }
/*     */ 
/*     */   public void setCustomerName(String aValue)
/*     */   {
/* 233 */     this.customerName = aValue;
/*     */   }
/*     */ 
/*     */   public String getCustomerManager()
/*     */   {
/* 241 */     return this.customerManager;
/*     */   }
/*     */ 
/*     */   public void setCustomerManager(String aValue)
/*     */   {
/* 249 */     this.customerManager = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 257 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 265 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getFax()
/*     */   {
/* 273 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String aValue)
/*     */   {
/* 280 */     this.fax = aValue;
/*     */   }
/*     */ 
/*     */   public String getSite()
/*     */   {
/* 288 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(String aValue)
/*     */   {
/* 295 */     this.site = aValue;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 303 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String aValue)
/*     */   {
/* 310 */     this.email = aValue;
/*     */   }
/*     */ 
/*     */   public String getState()
/*     */   {
/* 318 */     return this.state;
/*     */   }
/*     */ 
/*     */   public void setState(String aValue)
/*     */   {
/* 325 */     this.state = aValue;
/*     */   }
/*     */ 
/*     */   public String getCity()
/*     */   {
/* 333 */     return this.city;
/*     */   }
/*     */ 
/*     */   public void setCity(String aValue)
/*     */   {
/* 340 */     this.city = aValue;
/*     */   }
/*     */ 
/*     */   public String getZip()
/*     */   {
/* 348 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(String aValue)
/*     */   {
/* 355 */     this.zip = aValue;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 363 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String aValue)
/*     */   {
/* 370 */     this.address = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getRegisterFun()
/*     */   {
/* 375 */     return this.registerFun;
/*     */   }
/*     */ 
/*     */   public void setRegisterFun(BigDecimal registerFun) {
/* 379 */     this.registerFun = registerFun;
/*     */   }
/*     */ 
/*     */   public BigDecimal getTurnOver()
/*     */   {
/* 387 */     return this.turnOver;
/*     */   }
/*     */ 
/*     */   public void setTurnOver(BigDecimal aValue)
/*     */   {
/* 394 */     this.turnOver = aValue;
/*     */   }
/*     */ 
/*     */   public String getOtherDesc()
/*     */   {
/* 402 */     return this.otherDesc;
/*     */   }
/*     */ 
/*     */   public String getCurrencyUnit()
/*     */   {
/* 407 */     return this.currencyUnit;
/*     */   }
/*     */ 
/*     */   public void setCurrencyUnit(String currencyUnit) {
/* 411 */     this.currencyUnit = currencyUnit;
/*     */   }
/*     */ 
/*     */   public void setOtherDesc(String aValue)
/*     */   {
/* 418 */     this.otherDesc = aValue;
/*     */   }
/*     */ 
/*     */   public String getPrincipal()
/*     */   {
/* 426 */     return this.principal;
/*     */   }
/*     */ 
/*     */   public void setPrincipal(String aValue)
/*     */   {
/* 433 */     this.principal = aValue;
/*     */   }
/*     */ 
/*     */   public String getOpenBank()
/*     */   {
/* 441 */     return this.openBank;
/*     */   }
/*     */ 
/*     */   public void setOpenBank(String aValue)
/*     */   {
/* 448 */     this.openBank = aValue;
/*     */   }
/*     */ 
/*     */   public String getAccountsNo()
/*     */   {
/* 456 */     return this.accountsNo;
/*     */   }
/*     */ 
/*     */   public void setAccountsNo(String aValue)
/*     */   {
/* 463 */     this.accountsNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getTaxNo()
/*     */   {
/* 471 */     return this.taxNo;
/*     */   }
/*     */ 
/*     */   public void setTaxNo(String aValue)
/*     */   {
/* 478 */     this.taxNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 486 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 493 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public Short getRights()
/*     */   {
/* 503 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public void setRights(Short aValue)
/*     */   {
/* 511 */     this.rights = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 518 */     if (!(object instanceof Customer)) {
/* 519 */       return false;
/*     */     }
/* 521 */     Customer rhs = (Customer)object;
/* 522 */     return new EqualsBuilder()
/* 523 */       .append(this.customerId, rhs.customerId)
/* 524 */       .append(this.customerNo, rhs.customerNo)
/* 525 */       .append(this.industryType, rhs.industryType)
/* 526 */       .append(this.customerSource, rhs.customerSource)
/* 527 */       .append(this.customerType, rhs.customerType)
/* 528 */       .append(this.companyScale, rhs.companyScale)
/* 529 */       .append(this.customerName, rhs.customerName)
/* 530 */       .append(this.customerManager, rhs.customerManager)
/* 531 */       .append(this.phone, rhs.phone)
/* 532 */       .append(this.fax, rhs.fax)
/* 533 */       .append(this.site, rhs.site)
/* 534 */       .append(this.email, rhs.email)
/* 535 */       .append(this.state, rhs.state)
/* 536 */       .append(this.city, rhs.city)
/* 537 */       .append(this.zip, rhs.zip)
/* 538 */       .append(this.address, rhs.address)
/* 539 */       .append(this.registerFun, rhs.registerFun)
/* 540 */       .append(this.turnOver, rhs.turnOver)
/* 541 */       .append(this.currencyUnit, rhs.currencyUnit)
/* 542 */       .append(this.otherDesc, rhs.otherDesc)
/* 543 */       .append(this.principal, rhs.principal)
/* 544 */       .append(this.openBank, rhs.openBank)
/* 545 */       .append(this.accountsNo, rhs.accountsNo)
/* 546 */       .append(this.taxNo, rhs.taxNo)
/* 547 */       .append(this.notes, rhs.notes)
/* 548 */       .append(this.rights, rhs.rights)
/* 549 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 556 */     return new HashCodeBuilder(-82280557, -700257973)
/* 557 */       .append(this.customerId)
/* 558 */       .append(this.customerNo)
/* 559 */       .append(this.industryType)
/* 560 */       .append(this.customerSource)
/* 561 */       .append(this.customerType)
/* 562 */       .append(this.companyScale)
/* 563 */       .append(this.customerName)
/* 564 */       .append(this.customerManager)
/* 565 */       .append(this.phone)
/* 566 */       .append(this.fax)
/* 567 */       .append(this.site)
/* 568 */       .append(this.email)
/* 569 */       .append(this.state)
/* 570 */       .append(this.city)
/* 571 */       .append(this.zip)
/* 572 */       .append(this.address)
/* 573 */       .append(this.registerFun)
/* 574 */       .append(this.turnOver)
/* 575 */       .append(this.currencyUnit)
/* 576 */       .append(this.otherDesc)
/* 577 */       .append(this.principal)
/* 578 */       .append(this.openBank)
/* 579 */       .append(this.accountsNo)
/* 580 */       .append(this.taxNo)
/* 581 */       .append(this.notes)
/* 582 */       .append(this.rights)
/* 583 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 590 */     return new ToStringBuilder(this)
/* 591 */       .append("customerId", this.customerId)
/* 592 */       .append("customerNo", this.customerNo)
/* 593 */       .append("industryType", this.industryType)
/* 594 */       .append("customerSource", this.customerSource)
/* 595 */       .append("customerType", this.customerType)
/* 596 */       .append("companyScale", this.companyScale)
/* 597 */       .append("customerName", this.customerName)
/* 598 */       .append("customerManager", this.customerManager)
/* 599 */       .append("phone", this.phone)
/* 600 */       .append("fax", this.fax)
/* 601 */       .append("site", this.site)
/* 602 */       .append("email", this.email)
/* 603 */       .append("state", this.state)
/* 604 */       .append("city", this.city)
/* 605 */       .append("zip", this.zip)
/* 606 */       .append("address", this.address)
/* 607 */       .append("registerFun", this.registerFun)
/* 608 */       .append("turnOver", this.turnOver)
/* 609 */       .append("currencyUnit", this.currencyUnit)
/* 610 */       .append("otherDesc", this.otherDesc)
/* 611 */       .append("principal", this.principal)
/* 612 */       .append("openBank", this.openBank)
/* 613 */       .append("accountsNo", this.accountsNo)
/* 614 */       .append("taxNo", this.taxNo)
/* 615 */       .append("notes", this.notes)
/* 616 */       .append("rights", this.rights)
/* 617 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.customer.Customer
 * JD-Core Version:    0.6.0
 */
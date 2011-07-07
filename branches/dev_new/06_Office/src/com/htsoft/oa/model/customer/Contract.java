/*     */ package com.htsoft.oa.model.customer;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Contract extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long contractId;
/*     */ 
/*     */   @Expose
/*     */   protected String contractNo;
/*     */ 
/*     */   @Expose
/*     */   protected String subject;
/*     */ 
/*     */   @Expose
/*     */   protected BigDecimal contractAmount;
/*     */ 
/*     */   @Expose
/*     */   protected String mainItem;
/*     */ 
/*     */   @Expose
/*     */   protected String salesAfterItem;
/*     */ 
/*     */   @Expose
/*     */   protected Date validDate;
/*     */ 
/*     */   @Expose
/*     */   protected Date expireDate;
/*     */ 
/*     */   @Expose
/*     */   protected String serviceDep;
/*     */ 
/*     */   @Expose
/*     */   protected String serviceMan;
/*     */ 
/*     */   @Expose
/*     */   protected String signupUser;
/*     */ 
/*     */   @Expose
/*     */   protected Date signupTime;
/*     */ 
/*     */   @Expose
/*     */   protected String creator;
/*     */ 
/*     */   @Expose
/*     */   protected Date createtime;
/*     */ 
/*     */   @Expose
/*     */   protected String consignAddress;
/*     */ 
/*     */   @Expose
/*     */   protected String consignee;
/*     */ 
/*     */   @Expose
/*     */   protected Project project;
/*  57 */   protected Set contractConfigs = new HashSet();
/*     */ 
/*     */   @Expose
/*  59 */   protected Set contractFiles = new HashSet();
/*     */ 
/*     */   public Contract()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Contract(Long in_contractId)
/*     */   {
/*  74 */     setContractId(in_contractId);
/*     */   }
/*     */ 
/*     */   public Project getProject()
/*     */   {
/*  79 */     return this.project;
/*     */   }
/*     */ 
/*     */   public void setProject(Project in_project) {
/*  83 */     this.project = in_project;
/*     */   }
/*     */ 
/*     */   public Set getContractConfigs() {
/*  87 */     return this.contractConfigs;
/*     */   }
/*     */ 
/*     */   public void setContractConfigs(Set in_contractConfigs) {
/*  91 */     this.contractConfigs = in_contractConfigs;
/*     */   }
/*     */ 
/*     */   public Set getContractFiles() {
/*  95 */     return this.contractFiles;
/*     */   }
/*     */ 
/*     */   public void setContractFiles(Set in_contractFiles) {
/*  99 */     this.contractFiles = in_contractFiles;
/*     */   }
/*     */ 
/*     */   public Long getContractId()
/*     */   {
/* 108 */     return this.contractId;
/*     */   }
/*     */ 
/*     */   public void setContractId(Long aValue)
/*     */   {
/* 115 */     this.contractId = aValue;
/*     */   }
/*     */ 
/*     */   public String getContractNo()
/*     */   {
/* 123 */     return this.contractNo;
/*     */   }
/*     */ 
/*     */   public void setContractNo(String aValue)
/*     */   {
/* 131 */     this.contractNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 139 */     return this.subject;
/*     */   }
/*     */ 
/*     */   public void setSubject(String aValue)
/*     */   {
/* 147 */     this.subject = aValue;
/*     */   }
/*     */ 
/*     */   public BigDecimal getContractAmount()
/*     */   {
/* 155 */     return this.contractAmount;
/*     */   }
/*     */ 
/*     */   public void setContractAmount(BigDecimal aValue)
/*     */   {
/* 163 */     this.contractAmount = aValue;
/*     */   }
/*     */ 
/*     */   public String getMainItem()
/*     */   {
/* 171 */     return this.mainItem;
/*     */   }
/*     */ 
/*     */   public void setMainItem(String aValue)
/*     */   {
/* 178 */     this.mainItem = aValue;
/*     */   }
/*     */ 
/*     */   public String getSalesAfterItem()
/*     */   {
/* 186 */     return this.salesAfterItem;
/*     */   }
/*     */ 
/*     */   public void setSalesAfterItem(String aValue)
/*     */   {
/* 193 */     this.salesAfterItem = aValue;
/*     */   }
/*     */ 
/*     */   public Date getValidDate()
/*     */   {
/* 201 */     return this.validDate;
/*     */   }
/*     */ 
/*     */   public void setValidDate(Date aValue)
/*     */   {
/* 209 */     this.validDate = aValue;
/*     */   }
/*     */ 
/*     */   public Date getExpireDate()
/*     */   {
/* 217 */     return this.expireDate;
/*     */   }
/*     */ 
/*     */   public void setExpireDate(Date aValue)
/*     */   {
/* 225 */     this.expireDate = aValue;
/*     */   }
/*     */ 
/*     */   public String getServiceDep()
/*     */   {
/* 233 */     return this.serviceDep;
/*     */   }
/*     */ 
/*     */   public void setServiceDep(String aValue)
/*     */   {
/* 240 */     this.serviceDep = aValue;
/*     */   }
/*     */ 
/*     */   public String getServiceMan()
/*     */   {
/* 248 */     return this.serviceMan;
/*     */   }
/*     */ 
/*     */   public void setServiceMan(String aValue)
/*     */   {
/* 255 */     this.serviceMan = aValue;
/*     */   }
/*     */ 
/*     */   public String getSignupUser()
/*     */   {
/* 263 */     return this.signupUser;
/*     */   }
/*     */ 
/*     */   public void setSignupUser(String aValue)
/*     */   {
/* 271 */     this.signupUser = aValue;
/*     */   }
/*     */ 
/*     */   public Date getSignupTime()
/*     */   {
/* 279 */     return this.signupTime;
/*     */   }
/*     */ 
/*     */   public void setSignupTime(Date aValue)
/*     */   {
/* 287 */     this.signupTime = aValue;
/*     */   }
/*     */ 
/*     */   public String getCreator()
/*     */   {
/* 295 */     return this.creator;
/*     */   }
/*     */ 
/*     */   public void setCreator(String aValue)
/*     */   {
/* 303 */     this.creator = aValue;
/*     */   }
/*     */ 
/*     */   public Date getCreatetime()
/*     */   {
/* 311 */     return this.createtime;
/*     */   }
/*     */ 
/*     */   public void setCreatetime(Date aValue)
/*     */   {
/* 319 */     this.createtime = aValue;
/*     */   }
/*     */ 
/*     */   public Long getProjectId()
/*     */   {
/* 326 */     return getProject() == null ? null : getProject().getProjectId();
/*     */   }
/*     */ 
/*     */   public void setProjectId(Long aValue)
/*     */   {
/* 333 */     if (aValue == null) {
/* 334 */       this.project = null;
/* 335 */     } else if (this.project == null) {
/* 336 */       this.project = new Project(aValue);
/* 337 */       this.project.setVersion(new Integer(0));
/*     */     } else {
/* 339 */       this.project.setProjectId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getConsignAddress()
/*     */   {
/* 348 */     return this.consignAddress;
/*     */   }
/*     */ 
/*     */   public void setConsignAddress(String aValue)
/*     */   {
/* 355 */     this.consignAddress = aValue;
/*     */   }
/*     */ 
/*     */   public String getConsignee()
/*     */   {
/* 363 */     return this.consignee;
/*     */   }
/*     */ 
/*     */   public void setConsignee(String aValue)
/*     */   {
/* 370 */     this.consignee = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 377 */     if (!(object instanceof Contract)) {
/* 378 */       return false;
/*     */     }
/* 380 */     Contract rhs = (Contract)object;
/* 381 */     return new EqualsBuilder()
/* 382 */       .append(this.contractId, rhs.contractId)
/* 383 */       .append(this.contractNo, rhs.contractNo)
/* 384 */       .append(this.subject, rhs.subject)
/* 385 */       .append(this.contractAmount, rhs.contractAmount)
/* 386 */       .append(this.mainItem, rhs.mainItem)
/* 387 */       .append(this.salesAfterItem, rhs.salesAfterItem)
/* 388 */       .append(this.validDate, rhs.validDate)
/* 389 */       .append(this.expireDate, rhs.expireDate)
/* 390 */       .append(this.serviceDep, rhs.serviceDep)
/* 391 */       .append(this.serviceMan, rhs.serviceMan)
/* 392 */       .append(this.signupUser, rhs.signupUser)
/* 393 */       .append(this.signupTime, rhs.signupTime)
/* 394 */       .append(this.creator, rhs.creator)
/* 395 */       .append(this.createtime, rhs.createtime)
/* 396 */       .append(this.consignAddress, rhs.consignAddress)
/* 397 */       .append(this.consignee, rhs.consignee)
/* 398 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 405 */     return new HashCodeBuilder(-82280557, -700257973)
/* 406 */       .append(this.contractId)
/* 407 */       .append(this.contractNo)
/* 408 */       .append(this.subject)
/* 409 */       .append(this.contractAmount)
/* 410 */       .append(this.mainItem)
/* 411 */       .append(this.salesAfterItem)
/* 412 */       .append(this.validDate)
/* 413 */       .append(this.expireDate)
/* 414 */       .append(this.serviceDep)
/* 415 */       .append(this.serviceMan)
/* 416 */       .append(this.signupUser)
/* 417 */       .append(this.signupTime)
/* 418 */       .append(this.creator)
/* 419 */       .append(this.createtime)
/* 420 */       .append(this.consignAddress)
/* 421 */       .append(this.consignee)
/* 422 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 429 */     return new ToStringBuilder(this)
/* 430 */       .append("contactId", this.contractId)
/* 431 */       .append("contractNo", this.contractNo)
/* 432 */       .append("subject", this.subject)
/* 433 */       .append("contractAmount", this.contractAmount)
/* 434 */       .append("mainItem", this.mainItem)
/* 435 */       .append("salesAfterItem", this.salesAfterItem)
/* 436 */       .append("validDate", this.validDate)
/* 437 */       .append("expireDate", this.expireDate)
/* 438 */       .append("serviceDep", this.serviceDep)
/* 439 */       .append("serviceMan", this.serviceMan)
/* 440 */       .append("signupUser", this.signupUser)
/* 441 */       .append("signupTime", this.signupTime)
/* 442 */       .append("creator", this.creator)
/* 443 */       .append("createtime", this.createtime)
/* 444 */       .append("consignAddress", this.consignAddress)
/* 445 */       .append("consignee", this.consignee)
/* 446 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.customer.Contract
 * JD-Core Version:    0.6.0
 */
/*     */ package com.htsoft.oa.model.customer;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Project extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long projectId;
/*     */ 
/*     */   @Expose
/*     */   protected String projectName;
/*     */ 
/*     */   @Expose
/*     */   protected String projectNo;
/*     */ 
/*     */   @Expose
/*     */   protected String reqDesc;
/*     */ 
/*     */   @Expose
/*     */   protected Short isContract;
/*     */ 
/*     */   @Expose
/*     */   protected String fullname;
/*     */ 
/*     */   @Expose
/*     */   protected String mobile;
/*     */ 
/*     */   @Expose
/*     */   protected String phone;
/*     */ 
/*     */   @Expose
/*     */   protected String fax;
/*     */ 
/*     */   @Expose
/*     */   protected String otherContacts;
/*     */ 
/*     */   @Expose
/*     */   protected AppUser appUser;
/*     */ 
/*     */   @Expose
/*     */   protected Customer customer;
/*  47 */   protected Set contracts = new HashSet();
/*     */ 
/*     */   @Expose
/*  49 */   protected Set<FileAttach> projectFiles = new HashSet();
/*     */ 
/*     */   public Project()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Project(Long in_projectId)
/*     */   {
/*  64 */     setProjectId(in_projectId);
/*     */   }
/*     */ 
/*     */   public AppUser getAppUser()
/*     */   {
/*  69 */     return this.appUser;
/*     */   }
/*     */ 
/*     */   public void setAppUser(AppUser in_appUser) {
/*  73 */     this.appUser = in_appUser;
/*     */   }
/*     */ 
/*     */   public Customer getCustomer() {
/*  77 */     return this.customer;
/*     */   }
/*     */ 
/*     */   public void setCustomer(Customer in_customer) {
/*  81 */     this.customer = in_customer;
/*     */   }
/*     */ 
/*     */   public Set getContracts() {
/*  85 */     return this.contracts;
/*     */   }
/*     */ 
/*     */   public void setContracts(Set in_contracts) {
/*  89 */     this.contracts = in_contracts;
/*     */   }
/*     */ 
/*     */   public Set getProjectFiles() {
/*  93 */     return this.projectFiles;
/*     */   }
/*     */ 
/*     */   public void setProjectFiles(Set in_projectFiles) {
/*  97 */     this.projectFiles = in_projectFiles;
/*     */   }
/*     */ 
/*     */   public Long getProjectId()
/*     */   {
/* 106 */     return this.projectId;
/*     */   }
/*     */ 
/*     */   public void setProjectId(Long aValue)
/*     */   {
/* 113 */     this.projectId = aValue;
/*     */   }
/*     */ 
/*     */   public String getProjectName()
/*     */   {
/* 121 */     return this.projectName;
/*     */   }
/*     */ 
/*     */   public void setProjectName(String aValue)
/*     */   {
/* 129 */     this.projectName = aValue;
/*     */   }
/*     */ 
/*     */   public String getProjectNo()
/*     */   {
/* 137 */     return this.projectNo;
/*     */   }
/*     */ 
/*     */   public void setProjectNo(String aValue)
/*     */   {
/* 145 */     this.projectNo = aValue;
/*     */   }
/*     */ 
/*     */   public String getReqDesc()
/*     */   {
/* 153 */     return this.reqDesc;
/*     */   }
/*     */ 
/*     */   public void setReqDesc(String aValue)
/*     */   {
/* 160 */     this.reqDesc = aValue;
/*     */   }
/*     */ 
/*     */   public Short getIsContract()
/*     */   {
/* 168 */     return this.isContract;
/*     */   }
/*     */ 
/*     */   public void setIsContract(Short aValue)
/*     */   {
/* 176 */     this.isContract = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 184 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 192 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public String getMobile()
/*     */   {
/* 200 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String aValue)
/*     */   {
/* 207 */     this.mobile = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 215 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 222 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getFax()
/*     */   {
/* 230 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String aValue)
/*     */   {
/* 237 */     this.fax = aValue;
/*     */   }
/*     */ 
/*     */   public String getOtherContacts()
/*     */   {
/* 245 */     return this.otherContacts;
/*     */   }
/*     */ 
/*     */   public void setOtherContacts(String aValue)
/*     */   {
/* 252 */     this.otherContacts = aValue;
/*     */   }
/*     */ 
/*     */   public Long getCustomerId()
/*     */   {
/* 259 */     return getCustomer() == null ? null : getCustomer().getCustomerId();
/*     */   }
/*     */ 
/*     */   public void setCustomerId(Long aValue)
/*     */   {
/* 266 */     if (aValue == null) {
/* 267 */       this.customer = null;
/* 268 */     } else if (this.customer == null) {
/* 269 */       this.customer = new Customer(aValue);
/* 270 */       this.customer.setVersion(new Integer(0));
/*     */     } else {
/* 272 */       this.customer.setCustomerId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 280 */     return getAppUser() == null ? null : getAppUser().getUserId();
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 287 */     if (aValue == null) {
/* 288 */       this.appUser = null;
/* 289 */     } else if (this.appUser == null) {
/* 290 */       this.appUser = new AppUser(aValue);
/* 291 */       this.appUser.setVersion(new Integer(0));
/*     */     } else {
/* 293 */       this.appUser.setUserId(aValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 301 */     if (!(object instanceof Project)) {
/* 302 */       return false;
/*     */     }
/* 304 */     Project rhs = (Project)object;
/* 305 */     return new EqualsBuilder()
/* 306 */       .append(this.projectId, rhs.projectId)
/* 307 */       .append(this.projectName, rhs.projectName)
/* 308 */       .append(this.projectNo, rhs.projectNo)
/* 309 */       .append(this.reqDesc, rhs.reqDesc)
/* 310 */       .append(this.isContract, rhs.isContract)
/* 311 */       .append(this.fullname, rhs.fullname)
/* 312 */       .append(this.mobile, rhs.mobile)
/* 313 */       .append(this.phone, rhs.phone)
/* 314 */       .append(this.fax, rhs.fax)
/* 315 */       .append(this.otherContacts, rhs.otherContacts)
/* 316 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 323 */     return new HashCodeBuilder(-82280557, -700257973)
/* 324 */       .append(this.projectId)
/* 325 */       .append(this.projectName)
/* 326 */       .append(this.projectNo)
/* 327 */       .append(this.reqDesc)
/* 328 */       .append(this.isContract)
/* 329 */       .append(this.fullname)
/* 330 */       .append(this.mobile)
/* 331 */       .append(this.phone)
/* 332 */       .append(this.fax)
/* 333 */       .append(this.otherContacts)
/* 334 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 341 */     return new ToStringBuilder(this)
/* 342 */       .append("projectId", this.projectId)
/* 343 */       .append("projectName", this.projectName)
/* 344 */       .append("projectNo", this.projectNo)
/* 345 */       .append("reqDesc", this.reqDesc)
/* 346 */       .append("isContract", this.isContract)
/* 347 */       .append("fullname", this.fullname)
/* 348 */       .append("mobile", this.mobile)
/* 349 */       .append("phone", this.phone)
/* 350 */       .append("fax", this.fax)
/* 351 */       .append("otherContacts", this.otherContacts)
/* 352 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.customer.Project
 * JD-Core Version:    0.6.0
 */
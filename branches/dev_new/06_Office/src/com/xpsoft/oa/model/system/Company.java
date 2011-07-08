/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Company extends BaseModel
/*     */ {
/*     */   private Long companyId;
/*     */   private String companyNo;
/*     */   private String companyName;
/*     */   private String companyDesc;
/*     */   private String legalPerson;
/*     */   private Date setup;
/*     */   private String phone;
/*     */   private String fax;
/*     */   private String site;
/*     */   private String logo;
/*     */ 
/*     */   public Long getCompanyId()
/*     */   {
/*  29 */     return this.companyId;
/*     */   }
/*     */ 
/*     */   public void setCompanyId(Long companyId) {
/*  33 */     this.companyId = companyId;
/*     */   }
/*     */ 
/*     */   public String getCompanyNo() {
/*  37 */     return this.companyNo;
/*     */   }
/*     */ 
/*     */   public void setCompanyNo(String companyNo) {
/*  41 */     this.companyNo = companyNo;
/*     */   }
/*     */ 
/*     */   public String getCompanyName() {
/*  45 */     return this.companyName;
/*     */   }
/*     */ 
/*     */   public void setCompanyName(String companyName) {
/*  49 */     this.companyName = companyName;
/*     */   }
/*     */ 
/*     */   public String getCompanyDesc() {
/*  53 */     return this.companyDesc;
/*     */   }
/*     */ 
/*     */   public void setCompanyDesc(String companyDesc) {
/*  57 */     this.companyDesc = companyDesc;
/*     */   }
/*     */ 
/*     */   public String getLegalPerson() {
/*  61 */     return this.legalPerson;
/*     */   }
/*     */ 
/*     */   public void setLegalPerson(String legalPerson) {
/*  65 */     this.legalPerson = legalPerson;
/*     */   }
/*     */ 
/*     */   public Date getSetup() {
/*  69 */     return this.setup;
/*     */   }
/*     */ 
/*     */   public void setSetup(Date setup) {
/*  73 */     this.setup = setup;
/*     */   }
/*     */ 
/*     */   public String getPhone() {
/*  77 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String phone) {
/*  81 */     this.phone = phone;
/*     */   }
/*     */ 
/*     */   public String getFax() {
/*  85 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String fax) {
/*  89 */     this.fax = fax;
/*     */   }
/*     */ 
/*     */   public String getSite() {
/*  93 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(String site) {
/*  97 */     this.site = site;
/*     */   }
/*     */ 
/*     */   public String getLogo() {
/* 101 */     return this.logo;
/*     */   }
/*     */ 
/*     */   public void setLogo(String logo) {
/* 105 */     this.logo = logo;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.Company
 * JD-Core Version:    0.6.0
 */
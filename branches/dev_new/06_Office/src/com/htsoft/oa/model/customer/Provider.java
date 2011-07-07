/*     */ package com.htsoft.oa.model.customer;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.builder.EqualsBuilder;
/*     */ import org.apache.commons.lang.builder.HashCodeBuilder;
/*     */ import org.apache.commons.lang.builder.ToStringBuilder;
/*     */ 
/*     */ public class Provider extends BaseModel
/*     */ {
/*     */ 
/*     */   @Expose
/*     */   protected Long providerId;
/*     */ 
/*     */   @Expose
/*     */   protected String providerName;
/*     */ 
/*     */   @Expose
/*     */   protected String contactor;
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
/*     */   protected String address;
/*     */ 
/*     */   @Expose
/*     */   protected String zip;
/*     */ 
/*     */   @Expose
/*     */   protected String openBank;
/*     */ 
/*     */   @Expose
/*     */   protected String account;
/*     */ 
/*     */   @Expose
/*     */   protected String notes;
/*     */ 
/*     */   @Expose
/*     */   protected Integer rank;
/*  49 */   protected Set products = new HashSet();
/*     */ 
/*     */   public Provider()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Provider(Long in_providerId)
/*     */   {
/*  64 */     setProviderId(in_providerId);
/*     */   }
/*     */ 
/*     */   public Set getProducts()
/*     */   {
/*  69 */     return this.products;
/*     */   }
/*     */ 
/*     */   public void setProducts(Set in_products) {
/*  73 */     this.products = in_products;
/*     */   }
/*     */ 
/*     */   public Long getProviderId()
/*     */   {
/*  82 */     return this.providerId;
/*     */   }
/*     */ 
/*     */   public void setProviderId(Long aValue)
/*     */   {
/*  89 */     this.providerId = aValue;
/*     */   }
/*     */ 
/*     */   public String getProviderName()
/*     */   {
/*  97 */     return this.providerName;
/*     */   }
/*     */ 
/*     */   public void setProviderName(String aValue)
/*     */   {
/* 105 */     this.providerName = aValue;
/*     */   }
/*     */ 
/*     */   public String getContactor()
/*     */   {
/* 113 */     return this.contactor;
/*     */   }
/*     */ 
/*     */   public void setContactor(String aValue)
/*     */   {
/* 121 */     this.contactor = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 129 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 137 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getFax()
/*     */   {
/* 145 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String aValue)
/*     */   {
/* 152 */     this.fax = aValue;
/*     */   }
/*     */ 
/*     */   public String getSite()
/*     */   {
/* 160 */     return this.site;
/*     */   }
/*     */ 
/*     */   public void setSite(String aValue)
/*     */   {
/* 167 */     this.site = aValue;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 175 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String aValue)
/*     */   {
/* 182 */     this.email = aValue;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 190 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String aValue)
/*     */   {
/* 198 */     this.address = aValue;
/*     */   }
/*     */ 
/*     */   public String getZip()
/*     */   {
/* 206 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(String aValue)
/*     */   {
/* 213 */     this.zip = aValue;
/*     */   }
/*     */ 
/*     */   public String getOpenBank()
/*     */   {
/* 221 */     return this.openBank;
/*     */   }
/*     */ 
/*     */   public void setOpenBank(String aValue)
/*     */   {
/* 228 */     this.openBank = aValue;
/*     */   }
/*     */ 
/*     */   public String getAccount()
/*     */   {
/* 236 */     return this.account;
/*     */   }
/*     */ 
/*     */   public void setAccount(String aValue)
/*     */   {
/* 243 */     this.account = aValue;
/*     */   }
/*     */ 
/*     */   public String getNotes()
/*     */   {
/* 251 */     return this.notes;
/*     */   }
/*     */ 
/*     */   public void setNotes(String aValue)
/*     */   {
/* 258 */     this.notes = aValue;
/*     */   }
/*     */ 
/*     */   public Integer getRank()
/*     */   {
/* 271 */     return this.rank;
/*     */   }
/*     */ 
/*     */   public void setRank(Integer aValue)
/*     */   {
/* 278 */     this.rank = aValue;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 285 */     if (!(object instanceof Provider)) {
/* 286 */       return false;
/*     */     }
/* 288 */     Provider rhs = (Provider)object;
/* 289 */     return new EqualsBuilder()
/* 290 */       .append(this.providerId, rhs.providerId)
/* 291 */       .append(this.providerName, rhs.providerName)
/* 292 */       .append(this.contactor, rhs.contactor)
/* 293 */       .append(this.phone, rhs.phone)
/* 294 */       .append(this.fax, rhs.fax)
/* 295 */       .append(this.site, rhs.site)
/* 296 */       .append(this.email, rhs.email)
/* 297 */       .append(this.address, rhs.address)
/* 298 */       .append(this.zip, rhs.zip)
/* 299 */       .append(this.openBank, rhs.openBank)
/* 300 */       .append(this.account, rhs.account)
/* 301 */       .append(this.notes, rhs.notes)
/* 302 */       .append(this.rank, rhs.rank)
/* 303 */       .isEquals();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 310 */     return new HashCodeBuilder(-82280557, -700257973)
/* 311 */       .append(this.providerId)
/* 312 */       .append(this.providerName)
/* 313 */       .append(this.contactor)
/* 314 */       .append(this.phone)
/* 315 */       .append(this.fax)
/* 316 */       .append(this.site)
/* 317 */       .append(this.email)
/* 318 */       .append(this.address)
/* 319 */       .append(this.zip)
/* 320 */       .append(this.openBank)
/* 321 */       .append(this.account)
/* 322 */       .append(this.notes)
/* 323 */       .append(this.rank)
/* 324 */       .toHashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 331 */     return new ToStringBuilder(this)
/* 332 */       .append("providerId", this.providerId)
/* 333 */       .append("providerName", this.providerName)
/* 334 */       .append("contactor", this.contactor)
/* 335 */       .append("phone", this.phone)
/* 336 */       .append("fax", this.fax)
/* 337 */       .append("site", this.site)
/* 338 */       .append("email", this.email)
/* 339 */       .append("address", this.address)
/* 340 */       .append("zip", this.zip)
/* 341 */       .append("openBank", this.openBank)
/* 342 */       .append("account", this.account)
/* 343 */       .append("notes", this.notes)
/* 344 */       .append("rank", this.rank)
/* 345 */       .toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.customer.Provider
 * JD-Core Version:    0.6.0
 */
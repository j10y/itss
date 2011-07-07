/*     */ package com.htsoft.oa.model.system;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.htsoft.core.model.BaseModel;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.jbpm.api.identity.User;
/*     */ import org.springframework.security.GrantedAuthority;
/*     */ import org.springframework.security.GrantedAuthorityImpl;
/*     */ import org.springframework.security.userdetails.UserDetails;
/*     */ 
/*     */ public class AppUser extends BaseModel
/*     */   implements UserDetails, User
/*     */ {
/*  31 */   public static Long SYSTEM_USER = new Long(-1L);
/*     */ 
/*  35 */   public static Long SUPER_USER = new Long(1L);
/*     */ 
/*     */   @Expose
/*     */   protected Long userId;
/*     */ 
/*     */   @Expose
/*     */   protected String username;
/*     */   protected String password;
/*     */ 
/*     */   @Expose
/*     */   protected String email;
/*     */ 
/*     */   @Expose
/*     */   protected Department department;
/*     */ 
/*     */   @Expose
/*     */   protected String position;
/*     */ 
/*     */   @Expose
/*     */   protected String phone;
/*     */ 
/*     */   @Expose
/*     */   protected String mobile;
/*     */ 
/*     */   @Expose
/*     */   protected String fax;
/*     */ 
/*     */   @Expose
/*     */   protected String address;
/*     */ 
/*     */   @Expose
/*     */   protected String zip;
/*     */ 
/*     */   @Expose
/*     */   protected String photo;
/*     */ 
/*     */   @Expose
/*     */   protected Date accessionTime;
/*     */ 
/*     */   @Expose
/*     */   protected Short status;
/*     */ 
/*     */   @Expose
/*     */   protected String education;
/*     */ 
/*     */   @Expose
/*     */   protected Short title;
/*     */ 
/*     */   @Expose
/*     */   protected String fullname;
/*     */ 
/*     */   @Expose
/*     */   protected Short delFlag;
/*     */   private Set<AppRole> roles;
/*  77 */   private Set<String> rights = new HashSet();
/*     */ 
/*     */   public Set<String> getRights() {
/*  80 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public String getFunctionRights()
/*     */   {
/*  88 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*  90 */     Iterator it = this.rights.iterator();
/*     */ 
/*  92 */     while (it.hasNext()) {
/*  93 */       sb.append((String)it.next()).append(",");
/*     */     }
/*     */ 
/*  96 */     if (this.rights.size() > 0) {
/*  97 */       sb.deleteCharAt(sb.length() - 1);
/*     */     }
/*     */ 
/* 100 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void setRights(Set<String> rights) {
/* 104 */     this.rights = rights;
/*     */   }
/*     */ 
/*     */   public AppUser()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AppUser(Long in_userId)
/*     */   {
/* 120 */     setUserId(in_userId);
/*     */   }
/*     */ 
/*     */   public Long getUserId()
/*     */   {
/* 128 */     return this.userId;
/*     */   }
/*     */ 
/*     */   public void setUserId(Long aValue)
/*     */   {
/* 135 */     this.userId = aValue;
/*     */   }
/*     */ 
/*     */   public String getUsername()
/*     */   {
/* 143 */     return this.username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String aValue)
/*     */   {
/* 151 */     this.username = aValue;
/*     */   }
/*     */ 
/*     */   public String getPassword()
/*     */   {
/* 159 */     return this.password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String aValue)
/*     */   {
/* 167 */     this.password = aValue;
/*     */   }
/*     */ 
/*     */   public String getEmail()
/*     */   {
/* 175 */     return this.email;
/*     */   }
/*     */ 
/*     */   public void setEmail(String aValue)
/*     */   {
/* 183 */     this.email = aValue;
/*     */   }
/*     */ 
/*     */   public Department getDepartment()
/*     */   {
/* 189 */     return this.department;
/*     */   }
/*     */ 
/*     */   public void setDepartment(Department department) {
/* 193 */     this.department = department;
/*     */   }
/*     */ 
/*     */   public String getPosition()
/*     */   {
/* 201 */     return this.position;
/*     */   }
/*     */ 
/*     */   public void setPosition(String aValue)
/*     */   {
/* 208 */     this.position = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhone()
/*     */   {
/* 216 */     return this.phone;
/*     */   }
/*     */ 
/*     */   public void setPhone(String aValue)
/*     */   {
/* 223 */     this.phone = aValue;
/*     */   }
/*     */ 
/*     */   public String getMobile()
/*     */   {
/* 231 */     return this.mobile;
/*     */   }
/*     */ 
/*     */   public void setMobile(String aValue)
/*     */   {
/* 238 */     this.mobile = aValue;
/*     */   }
/*     */ 
/*     */   public String getFax()
/*     */   {
/* 246 */     return this.fax;
/*     */   }
/*     */ 
/*     */   public void setFax(String aValue)
/*     */   {
/* 253 */     this.fax = aValue;
/*     */   }
/*     */ 
/*     */   public String getAddress()
/*     */   {
/* 261 */     return this.address;
/*     */   }
/*     */ 
/*     */   public void setAddress(String aValue)
/*     */   {
/* 268 */     this.address = aValue;
/*     */   }
/*     */ 
/*     */   public String getZip()
/*     */   {
/* 276 */     return this.zip;
/*     */   }
/*     */ 
/*     */   public void setZip(String aValue)
/*     */   {
/* 283 */     this.zip = aValue;
/*     */   }
/*     */ 
/*     */   public String getPhoto()
/*     */   {
/* 291 */     return this.photo;
/*     */   }
/*     */ 
/*     */   public void setPhoto(String aValue)
/*     */   {
/* 298 */     this.photo = aValue;
/*     */   }
/*     */ 
/*     */   public Date getAccessionTime()
/*     */   {
/* 306 */     return this.accessionTime;
/*     */   }
/*     */ 
/*     */   public void setAccessionTime(Date aValue)
/*     */   {
/* 314 */     this.accessionTime = aValue;
/*     */   }
/*     */ 
/*     */   public Short getStatus()
/*     */   {
/* 322 */     return this.status;
/*     */   }
/*     */ 
/*     */   public void setStatus(Short aValue)
/*     */   {
/* 330 */     this.status = aValue;
/*     */   }
/*     */ 
/*     */   public String getEducation()
/*     */   {
/* 338 */     return this.education;
/*     */   }
/*     */ 
/*     */   public void setEducation(String aValue)
/*     */   {
/* 345 */     this.education = aValue;
/*     */   }
/*     */ 
/*     */   public Short getTitle()
/*     */   {
/* 353 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(Short aValue)
/*     */   {
/* 360 */     this.title = aValue;
/*     */   }
/*     */ 
/*     */   public String getFullname()
/*     */   {
/* 368 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public void setFullname(String aValue)
/*     */   {
/* 375 */     this.fullname = aValue;
/*     */   }
/*     */ 
/*     */   public Short getDelFlag()
/*     */   {
/* 383 */     return this.delFlag;
/*     */   }
/*     */ 
/*     */   public void setDelFlag(Short delFlag) {
/* 387 */     this.delFlag = delFlag;
/*     */   }
/*     */ 
/*     */   public String getFirstKeyColumnName()
/*     */   {
/* 394 */     return "userId";
/*     */   }
/*     */ 
/*     */   public Set<AppRole> getRoles()
/*     */   {
/* 400 */     return this.roles;
/*     */   }
/*     */ 
/*     */   public void setRoles(Set<AppRole> roles) {
/* 404 */     this.roles = roles;
/*     */   }
/*     */ 
/*     */   public GrantedAuthority[] getAuthorities() {
/* 408 */     GrantedAuthority[] rights = (GrantedAuthority[])this.roles.toArray(new GrantedAuthority[this.roles.size() + 1]);
/* 409 */     rights[(rights.length - 1)] = new GrantedAuthorityImpl("ROLE_PUBLIC");
/* 410 */     return rights;
/*     */   }
/*     */ 
/*     */   public boolean isAccountNonExpired() {
/* 414 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isAccountNonLocked() {
/* 418 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isCredentialsNonExpired() {
/* 422 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/* 427 */     return this.status.shortValue() == 1;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 438 */     return this.userId.toString();
/*     */   }
/*     */ 
/*     */   public String getBusinessEmail() {
/* 442 */     return this.email;
/*     */   }
/*     */ 
/*     */   public String getFamilyName()
/*     */   {
/* 447 */     return this.fullname;
/*     */   }
/*     */ 
/*     */   public String getGivenName()
/*     */   {
/* 452 */     return this.fullname;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.model.system.AppUser
 * JD-Core Version:    0.6.0
 */
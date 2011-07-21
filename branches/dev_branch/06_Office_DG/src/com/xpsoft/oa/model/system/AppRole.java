/*     */ package com.xpsoft.oa.model.system;
/*     */ 
/*     */ import com.google.gson.annotations.Expose;
/*     */ import com.xpsoft.core.model.BaseModel;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.jbpm.api.identity.Group;
/*     */ import org.springframework.security.GrantedAuthority;
/*     */ 
/*     */ public class AppRole extends BaseModel
/*     */   implements GrantedAuthority, Group
/*     */ {
/*  20 */   public static String ROLE_PUBLIC = "ROLE_PUBLIC";
/*     */ 
/*  22 */   public static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
/*     */ 
/*  27 */   public static final Long SUPER_ROLEID = Long.valueOf(-1L);
/*     */   public static final String SUPER_RIGHTS = "__ALL";
/*     */ 
/*     */   @Expose
/*     */   private Long roleId;
/*     */ 
/*     */   @Expose
/*     */   private String roleName;
/*     */ 
/*     */   @Expose
/*     */   private String roleDesc;
/*     */ 
/*     */   @Expose
/*     */   private Short status;
/*     */ 
/*     */   @Expose
/*     */   private Short isDefaultIn;
/*     */ 
/*     */   @Expose
/*     */   private String rights;
/*  46 */   private Set<AppFunction> functions = new HashSet();
/*  47 */   private Set<AppUser> appUsers = new HashSet();
/*     */ 
/*     */   public Short getIsDefaultIn()
/*     */   {
/*  53 */     return this.isDefaultIn;
/*     */   }
/*     */ 
/*     */   public void setIsDefaultIn(Short isDefaultIn) {
/*  57 */     this.isDefaultIn = isDefaultIn;
/*     */   }
/*     */ 
/*     */   public Set<AppUser> getAppUsers()
/*     */   {
/*  62 */     return this.appUsers;
/*     */   }
/*     */ 
/*     */   public void setAppUsers(Set<AppUser> appUsers) {
/*  66 */     this.appUsers = appUsers;
/*     */   }
/*     */ 
/*     */   public String getRights() {
/*  70 */     return this.rights;
/*     */   }
/*     */ 
/*     */   public void setRights(String rights) {
/*  74 */     this.rights = rights;
/*     */   }
/*     */ 
/*     */   public Long getRoleId() {
/*  78 */     return this.roleId;
/*     */   }
/*     */   public void setRoleId(Long roleId) {
/*  81 */     this.roleId = roleId;
/*     */   }
/*     */   public String getRoleName() {
/*  84 */     return this.roleName;
/*     */   }
/*     */   public void setRoleName(String roleName) {
/*  87 */     this.roleName = roleName;
/*     */   }
/*     */   public String getRoleDesc() {
/*  90 */     return this.roleDesc;
/*     */   }
/*     */   public void setRoleDesc(String roleDesc) {
/*  93 */     this.roleDesc = roleDesc;
/*     */   }
/*     */   public Short getStatus() {
/*  96 */     return this.status;
/*     */   }
/*     */   public void setStatus(Short status) {
/*  99 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public String getAuthority() {
/* 103 */     return this.roleName;
/*     */   }
/*     */ 
/*     */   public int compareTo(Object o) {
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getId()
/*     */   {
/* 113 */     return this.roleId.toString();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 119 */     return this.roleName;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 125 */     return "candidate";
/*     */   }
/*     */ 
/*     */   public Set<AppFunction> getFunctions() {
/* 129 */     return this.functions;
/*     */   }
/*     */ 
/*     */   public void setFunctions(Set<AppFunction> functions) {
/* 133 */     this.functions = functions;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.model.system.AppRole
 * JD-Core Version:    0.6.0
 */
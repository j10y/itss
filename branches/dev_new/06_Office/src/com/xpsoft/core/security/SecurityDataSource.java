/*    */ package com.xpsoft.core.security;
/*    */ 
/*    */ import com.xpsoft.oa.model.system.AppRole;
/*    */ import com.xpsoft.oa.service.system.AppRoleService;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class SecurityDataSource
/*    */ {
/*    */   private AppRoleService appRoleService;
/* 30 */   private HashSet<String> anonymousUrls = null;
/*    */ 
/* 34 */   private HashSet<String> publicUrls = null;
/*    */ 
/*    */   public void setAppRoleService(AppRoleService appRoleService)
/*    */   {
/* 24 */     this.appRoleService = appRoleService;
/*    */   }
/*    */ 
/*    */   public Set<String> getAnonymousUrls()
/*    */   {
/* 41 */     return this.anonymousUrls;
/*    */   }
/*    */ 
/*    */   public void setAnonymousUrls(Set<String> anonymousUrls) {
/* 45 */     this.anonymousUrls = ((HashSet)anonymousUrls);
/*    */   }
/*    */ 
/*    */   public HashSet<String> getPublicUrls() {
/* 49 */     return this.publicUrls;
/*    */   }
/*    */ 
/*    */   public void setPublicUrls(HashSet<String> publicUrls) {
/* 53 */     this.publicUrls = publicUrls;
/*    */   }
/*    */ 
/*    */   public HashMap<String, Set<String>> getDataSource()
/*    */   {
/* 61 */     HashMap tmap = this.appRoleService.getSecurityDataSource();
/* 62 */     tmap.put(AppRole.ROLE_ANONYMOUS, this.anonymousUrls);
/* 63 */     tmap.put(AppRole.ROLE_PUBLIC, this.publicUrls);
/* 64 */     return tmap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.security.SecurityDataSource
 * JD-Core Version:    0.6.0
 */
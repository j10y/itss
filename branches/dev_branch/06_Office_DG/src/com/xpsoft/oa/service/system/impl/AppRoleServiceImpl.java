/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.AppRoleDao;
/*    */ import com.xpsoft.oa.model.system.AppRole;
/*    */ import com.xpsoft.oa.service.system.AppRoleService;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AppRoleServiceImpl extends BaseServiceImpl<AppRole>
/*    */   implements AppRoleService
/*    */ {
/*    */   private AppRoleDao dao;
/*    */ 
/*    */   public AppRoleServiceImpl(AppRoleDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public AppRole getByRoleName(String roleName) {
/* 24 */     return this.dao.getByRoleName(roleName);
/*    */   }
/*    */ 
/*    */   public HashMap<String, Set<String>> getSecurityDataSource()
/*    */   {
/* 31 */     return this.dao.getSecurityDataSource();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.AppRoleServiceImpl
 * JD-Core Version:    0.6.0
 */
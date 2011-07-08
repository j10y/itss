/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.system.AppUserDao;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.service.system.AppUserService;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AppUserServiceImpl extends BaseServiceImpl<AppUser>
/*    */   implements AppUserService
/*    */ {
/*    */   private AppUserDao dao;
/*    */ 
/*    */   public AppUserServiceImpl(AppUserDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public AppUser findByUserName(String username)
/*    */   {
/* 25 */     return this.dao.findByUserName(username);
/*    */   }
/*    */ 
/*    */   public List findByDepartment(String path, PagingBean pb)
/*    */   {
/* 30 */     return this.dao.findByDepartment(path, pb);
/*    */   }
/*    */ 
/*    */   public List findByRole(Long roleId, PagingBean pb)
/*    */   {
/* 35 */     return this.dao.findByRole(roleId, pb);
/*    */   }
/*    */ 
/*    */   public List findByRoleId(Long roleId) {
/* 39 */     return this.dao.findByRole(roleId);
/*    */   }
/*    */ 
/*    */   public List<AppUser> findSubAppUser(String path, Set<Long> userIds, PagingBean pb)
/*    */   {
/* 45 */     return this.dao.findSubAppUser(path, userIds, pb);
/*    */   }
/*    */ 
/*    */   public List<AppUser> findSubAppUserByRole(Long roleId, Set<Long> userIds, PagingBean pb)
/*    */   {
/* 51 */     return this.dao.findSubAppUserByRole(roleId, userIds, pb);
/*    */   }
/*    */ 
/*    */   public List<AppUser> findByDepId(Long depId)
/*    */   {
/* 56 */     return this.dao.findByDepId(depId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.AppUserServiceImpl
 * JD-Core Version:    0.6.0
 */
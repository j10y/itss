/*    */ package com.htsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.archive.ArchRecUserDao;
/*    */ import com.htsoft.oa.model.archive.ArchRecUser;
/*    */ import com.htsoft.oa.service.archive.ArchRecUserService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchRecUserServiceImpl extends BaseServiceImpl<ArchRecUser>
/*    */   implements ArchRecUserService
/*    */ {
/*    */   private ArchRecUserDao dao;
/*    */ 
/*    */   public ArchRecUserServiceImpl(ArchRecUserDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List findDepAll()
/*    */   {
/* 23 */     return this.dao.findDepAll();
/*    */   }
/*    */ 
/*    */   public ArchRecUser getByDepId(Long depId)
/*    */   {
/* 28 */     return this.dao.getByDepId(depId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.archive.impl.ArchRecUserServiceImpl
 * JD-Core Version:    0.6.0
 */
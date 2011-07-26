/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.archive.ArchivesDao;
/*    */ import com.xpsoft.oa.model.archive.Archives;
/*    */ import com.xpsoft.oa.model.system.AppRole;
/*    */ import com.xpsoft.oa.service.archive.ArchivesService;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ArchivesServiceImpl extends BaseServiceImpl<Archives>
/*    */   implements ArchivesService
/*    */ {
/*    */   private ArchivesDao dao;
/*    */ 
/*    */   public ArchivesServiceImpl(ArchivesDao dao)
/*    */   {
/* 20 */     super(dao);
/* 21 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Archives> findByUserOrRole(Long userId, Set<AppRole> roles, PagingBean pb)
/*    */   {
/* 26 */     return this.dao.findByUserOrRole(userId, roles, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.ArchivesServiceImpl
 * JD-Core Version:    0.6.0
 */
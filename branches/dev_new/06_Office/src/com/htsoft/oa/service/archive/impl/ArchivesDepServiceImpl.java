/*    */ package com.htsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.archive.ArchivesDepDao;
/*    */ import com.htsoft.oa.model.archive.ArchivesDep;
/*    */ import com.htsoft.oa.service.archive.ArchivesDepService;
/*    */ 
/*    */ public class ArchivesDepServiceImpl extends BaseServiceImpl<ArchivesDep>
/*    */   implements ArchivesDepService
/*    */ {
/*    */   private ArchivesDepDao dao;
/*    */ 
/*    */   public ArchivesDepServiceImpl(ArchivesDepDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.archive.impl.ArchivesDepServiceImpl
 * JD-Core Version:    0.6.0
 */
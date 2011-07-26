/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchivesDocDao;
/*    */ import com.xpsoft.oa.model.archive.ArchivesDoc;
/*    */ import com.xpsoft.oa.service.archive.ArchivesDocService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchivesDocServiceImpl extends BaseServiceImpl<ArchivesDoc>
/*    */   implements ArchivesDocService
/*    */ {
/*    */   private ArchivesDocDao dao;
/*    */ 
/*    */   public ArchivesDocServiceImpl(ArchivesDocDao dao)
/*    */   {
/* 16 */     super(dao);
/* 17 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<ArchivesDoc> findByAid(Long archivesId)
/*    */   {
/* 22 */     return this.dao.findByAid(archivesId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.ArchivesDocServiceImpl
 * JD-Core Version:    0.6.0
 */
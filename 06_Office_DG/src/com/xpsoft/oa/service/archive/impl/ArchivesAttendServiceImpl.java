/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchivesAttendDao;
/*    */ import com.xpsoft.oa.model.archive.ArchivesAttend;
/*    */ import com.xpsoft.oa.service.archive.ArchivesAttendService;
/*    */ 
/*    */ public class ArchivesAttendServiceImpl extends BaseServiceImpl<ArchivesAttend>
/*    */   implements ArchivesAttendService
/*    */ {
/*    */   private ArchivesAttendDao dao;
/*    */ 
/*    */   public ArchivesAttendServiceImpl(ArchivesAttendDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.ArchivesAttendServiceImpl
 * JD-Core Version:    0.6.0
 */
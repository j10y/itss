/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchHastenDao;
/*    */ import com.xpsoft.oa.model.archive.ArchHasten;
/*    */ import com.xpsoft.oa.service.archive.ArchHastenService;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ArchHastenServiceImpl extends BaseServiceImpl<ArchHasten>
/*    */   implements ArchHastenService
/*    */ {
/*    */   private ArchHastenDao dao;
/*    */ 
/*    */   public ArchHastenServiceImpl(ArchHastenDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Date getLeastRecordByUser(Long archivesId)
/*    */   {
/* 23 */     return this.dao.getLeastRecordByUser(archivesId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.ArchHastenServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchivesTypeDao;
/*    */ import com.xpsoft.oa.model.archive.ArchivesType;
/*    */ import com.xpsoft.oa.service.archive.ArchivesTypeService;
/*    */ 
/*    */ public class ArchivesTypeServiceImpl extends BaseServiceImpl<ArchivesType>
/*    */   implements ArchivesTypeService
/*    */ {
/*    */   private ArchivesTypeDao dao;
/*    */ 
/*    */   public ArchivesTypeServiceImpl(ArchivesTypeDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.ArchivesTypeServiceImpl
 * JD-Core Version:    0.6.0
 */
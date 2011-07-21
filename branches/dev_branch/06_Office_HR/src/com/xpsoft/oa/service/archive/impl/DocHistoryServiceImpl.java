/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.archive.DocHistoryDao;
/*    */ import com.xpsoft.oa.model.archive.DocHistory;
/*    */ import com.xpsoft.oa.service.archive.DocHistoryService;
/*    */ 
/*    */ public class DocHistoryServiceImpl extends BaseServiceImpl<DocHistory>
/*    */   implements DocHistoryService
/*    */ {
/*    */   private DocHistoryDao dao;
/*    */ 
/*    */   public DocHistoryServiceImpl(DocHistoryDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.DocHistoryServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.archive.LeaderReadDao;
/*    */ import com.htsoft.oa.model.archive.LeaderRead;
/*    */ import com.htsoft.oa.service.archive.LeaderReadService;
/*    */ 
/*    */ public class LeaderReadServiceImpl extends BaseServiceImpl<LeaderRead>
/*    */   implements LeaderReadService
/*    */ {
/*    */   private LeaderReadDao dao;
/*    */ 
/*    */   public LeaderReadServiceImpl(LeaderReadDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.archive.impl.LeaderReadServiceImpl
 * JD-Core Version:    0.6.0
 */
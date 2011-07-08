/*    */ package com.xpsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.archive.LeaderReadDao;
/*    */ import com.xpsoft.oa.model.archive.LeaderRead;
/*    */ import com.xpsoft.oa.service.archive.LeaderReadService;
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
 * Qualified Name:     com.xpsoft.oa.service.archive.impl.LeaderReadServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.hrm.JobChangeDao;
/*    */ import com.xpsoft.oa.model.hrm.JobChange;
/*    */ import com.xpsoft.oa.service.hrm.JobChangeService;
/*    */ 
/*    */ public class JobChangeServiceImpl extends BaseServiceImpl<JobChange>
/*    */   implements JobChangeService
/*    */ {
/*    */   private JobChangeDao dao;
/*    */ 
/*    */   public JobChangeServiceImpl(JobChangeDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.impl.JobChangeServiceImpl
 * JD-Core Version:    0.6.0
 */
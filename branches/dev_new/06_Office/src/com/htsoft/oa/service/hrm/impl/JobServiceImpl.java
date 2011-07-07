/*    */ package com.htsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.hrm.JobDao;
/*    */ import com.htsoft.oa.model.hrm.Job;
/*    */ import com.htsoft.oa.service.hrm.JobService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class JobServiceImpl extends BaseServiceImpl<Job>
/*    */   implements JobService
/*    */ {
/*    */   private JobDao dao;
/*    */ 
/*    */   public JobServiceImpl(JobDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Job> findByDep(Long depId)
/*    */   {
/* 23 */     return this.dao.findByDep(depId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.hrm.impl.JobServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.dao.hrm.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.hrm.JobDao;
/*    */ import com.htsoft.oa.model.hrm.Job;
/*    */ import java.util.List;
/*    */ 
/*    */ public class JobDaoImpl extends BaseDaoImpl<Job>
/*    */   implements JobDao
/*    */ {
/*    */   public JobDaoImpl()
/*    */   {
/* 15 */     super(Job.class);
/*    */   }
/*    */ 
/*    */   public List<Job> findByDep(Long depId)
/*    */   {
/* 20 */     String hql = "from Job vo where vo.department.depId=?";
/* 21 */     Object[] objs = { depId };
/* 22 */     return findByHql(hql, objs);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.hrm.impl.JobDaoImpl
 * JD-Core Version:    0.6.0
 */
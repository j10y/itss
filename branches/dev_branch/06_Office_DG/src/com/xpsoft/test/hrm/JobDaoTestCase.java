/*    */ package com.xpsoft.test.hrm;
/*    */ 
/*    */ import com.xpsoft.oa.dao.hrm.JobDao;
/*    */ import com.xpsoft.oa.model.hrm.Job;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class JobDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private JobDao jobDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 22 */     Job job = new Job();
/*    */ 
/* 25 */     this.jobDao.save(job);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.hrm.JobDaoTestCase
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.test.task;
/*    */ 
/*    */ import com.xpsoft.oa.dao.task.WorkPlanDao;
/*    */ import com.xpsoft.oa.model.task.WorkPlan;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class WorkPlanDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private WorkPlanDao workPlanDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     WorkPlan workPlan = new WorkPlan();
/*    */ 
/* 22 */     this.workPlanDao.save(workPlan);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.task.WorkPlanDaoTestCase
 * JD-Core Version:    0.6.0
 */
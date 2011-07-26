/*    */ package com.xpsoft.test.task;
/*    */ 
/*    */ import com.xpsoft.oa.dao.task.PlanTypeDao;
/*    */ import com.xpsoft.oa.model.task.PlanType;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class PlanTypeDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private PlanTypeDao planTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     PlanType planType = new PlanType();
/*    */ 
/* 22 */     this.planTypeDao.save(planType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.task.PlanTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
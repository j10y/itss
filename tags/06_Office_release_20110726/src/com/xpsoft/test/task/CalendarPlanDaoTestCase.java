/*    */ package com.xpsoft.test.task;
/*    */ 
/*    */ import com.xpsoft.oa.dao.task.CalendarPlanDao;
/*    */ import com.xpsoft.oa.model.task.CalendarPlan;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class CalendarPlanDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private CalendarPlanDao calendarPlanDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     CalendarPlan calendarPlan = new CalendarPlan();
/*    */ 
/* 22 */     this.calendarPlanDao.save(calendarPlan);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.task.CalendarPlanDaoTestCase
 * JD-Core Version:    0.6.0
 */
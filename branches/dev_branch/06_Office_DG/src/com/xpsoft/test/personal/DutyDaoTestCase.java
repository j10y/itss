/*    */ package com.xpsoft.test.personal;
/*    */ 
/*    */ import com.xpsoft.oa.dao.personal.DutyDao;
/*    */ import com.xpsoft.oa.model.personal.Duty;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class DutyDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DutyDao dutyDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     Duty duty = new Duty();
/*    */ 
/* 22 */     this.dutyDao.save(duty);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.personal.DutyDaoTestCase
 * JD-Core Version:    0.6.0
 */
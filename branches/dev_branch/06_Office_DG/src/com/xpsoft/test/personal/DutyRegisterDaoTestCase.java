/*    */ package com.xpsoft.test.personal;
/*    */ 
/*    */ import com.xpsoft.oa.dao.personal.DutyRegisterDao;
/*    */ import com.xpsoft.oa.model.personal.DutyRegister;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class DutyRegisterDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DutyRegisterDao dutyRegisterDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     DutyRegister dutyRegister = new DutyRegister();
/*    */ 
/* 22 */     this.dutyRegisterDao.save(dutyRegister);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.personal.DutyRegisterDaoTestCase
 * JD-Core Version:    0.6.0
 */
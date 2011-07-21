/*    */ package com.xpsoft.test.personal;
/*    */ 
/*    */ import com.xpsoft.oa.dao.personal.DutySystemDao;
/*    */ import com.xpsoft.oa.model.personal.DutySystem;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class DutySystemDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DutySystemDao dutySystemDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     DutySystem dutySystem = new DutySystem();
/*    */ 
/* 22 */     this.dutySystemDao.save(dutySystem);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.personal.DutySystemDaoTestCase
 * JD-Core Version:    0.6.0
 */
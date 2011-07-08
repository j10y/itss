/*    */ package com.xpsoft.test.personal;
/*    */ 
/*    */ import com.xpsoft.oa.dao.personal.DutySectionDao;
/*    */ import com.xpsoft.oa.model.personal.DutySection;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class DutySectionDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DutySectionDao dutySectionDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     DutySection dutySection = new DutySection();
/*    */ 
/* 22 */     this.dutySectionDao.save(dutySection);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.personal.DutySectionDaoTestCase
 * JD-Core Version:    0.6.0
 */
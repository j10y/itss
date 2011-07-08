/*    */ package com.xpsoft.test.hrm;
/*    */ 
/*    */ import com.xpsoft.oa.dao.hrm.StandSalaryItemDao;
/*    */ import com.xpsoft.oa.model.hrm.StandSalaryItem;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class StandSalaryItemDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private StandSalaryItemDao standSalaryItemDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     StandSalaryItem standSalaryItem = new StandSalaryItem();
/*    */ 
/* 22 */     this.standSalaryItemDao.save(standSalaryItem);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.hrm.StandSalaryItemDaoTestCase
 * JD-Core Version:    0.6.0
 */
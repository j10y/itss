/*    */ package com.xpsoft.test.hrm;
/*    */ 
/*    */ import com.xpsoft.oa.dao.hrm.SalaryItemDao;
/*    */ import com.xpsoft.oa.model.hrm.SalaryItem;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class SalaryItemDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private SalaryItemDao salaryItemDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     SalaryItem salaryItem = new SalaryItem();
/*    */ 
/* 22 */     this.salaryItemDao.save(salaryItem);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.hrm.SalaryItemDaoTestCase
 * JD-Core Version:    0.6.0
 */
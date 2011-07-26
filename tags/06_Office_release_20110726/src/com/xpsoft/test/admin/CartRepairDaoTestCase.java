/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.xpsoft.oa.dao.admin.CartRepairDao;
/*    */ import com.xpsoft.oa.model.admin.CartRepair;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class CartRepairDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private CartRepairDao cartRepairDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     CartRepair cartRepair = new CartRepair();
/*    */ 
/* 22 */     this.cartRepairDao.save(cartRepair);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.CartRepairDaoTestCase
 * JD-Core Version:    0.6.0
 */
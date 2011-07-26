/*    */ package com.xpsoft.test.customer;
/*    */ 
/*    */ import com.xpsoft.oa.dao.customer.CustomerDao;
/*    */ import com.xpsoft.oa.model.customer.Customer;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class CustomerDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private CustomerDao customerDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     Customer customer = new Customer();
/* 20 */     customer.setCustomerName("Customer1");
/* 21 */     this.customerDao.save(customer);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.customer.CustomerDaoTestCase
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.test.customer;
/*    */ 
/*    */ import com.xpsoft.oa.dao.customer.CusLinkmanDao;
/*    */ import com.xpsoft.oa.model.customer.CusLinkman;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class CusLinkmanDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private CusLinkmanDao cusLinkmanDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     CusLinkman cusLinkman = new CusLinkman();
/*    */ 
/* 22 */     this.cusLinkmanDao.save(cusLinkman);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.customer.CusLinkmanDaoTestCase
 * JD-Core Version:    0.6.0
 */
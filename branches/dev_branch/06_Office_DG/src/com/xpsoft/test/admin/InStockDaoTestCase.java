/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.xpsoft.oa.dao.admin.InStockDao;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ 
/*    */ public class InStockDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private InStockDao inStockDao;
/*    */ 
/*    */   @Test
/*    */   public void test1()
/*    */   {
/* 19 */     Integer inCount = this.inStockDao.findInCountByBuyId(Long.valueOf(1L));
/* 20 */     System.out.println(inCount);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.InStockDaoTestCase
 * JD-Core Version:    0.6.0
 */
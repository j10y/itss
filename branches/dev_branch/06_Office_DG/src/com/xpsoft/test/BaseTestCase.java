/*    */ package com.xpsoft.test;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.junit.runner.RunWith;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ import org.springframework.test.context.ContextConfiguration;
/*    */ import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/*    */ import org.springframework.test.context.transaction.TransactionConfiguration;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @RunWith(SpringJUnit4ClassRunner.class)
/*    */ @ContextConfiguration(locations={"classpath:conf/app-test.xml"})
/*    */ @TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
/*    */ @Transactional
/*    */ public class BaseTestCase
/*    */ {
/*    */   @Rollback(false)
/*    */   public void test()
/*    */   {
/* 18 */     System.out.println("Hello!");
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.BaseTestCase
 * JD-Core Version:    0.6.0
 */
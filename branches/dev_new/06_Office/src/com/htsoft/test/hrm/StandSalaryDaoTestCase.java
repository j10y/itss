/*    */ package com.htsoft.test.hrm;
/*    */ 
/*    */ import com.htsoft.oa.dao.hrm.StandSalaryDao;
/*    */ import com.htsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import java.math.BigDecimal;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class StandSalaryDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private StandSalaryDao standSalaryDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 24 */     BigDecimal abc = new BigDecimal("0");
/* 25 */     BigDecimal abc1 = new BigDecimal("1");
/* 26 */     BigDecimal abc2 = new BigDecimal("2");
/*    */ 
/* 28 */     System.out.println(abc.add(abc1).add(abc2));
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.hrm.StandSalaryDaoTestCase
 * JD-Core Version:    0.6.0
 */
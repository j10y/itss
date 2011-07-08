/*    */ package com.xpsoft.test.system;
/*    */ 
/*    */ import com.xpsoft.oa.dao.system.AppFunctionDao;
/*    */ import com.xpsoft.oa.model.system.AppFunction;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class AppFunctionDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private AppFunctionDao appFunctionDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 20 */     AppFunction appFunction = new AppFunction();
/*    */ 
/* 26 */     this.appFunctionDao.save(appFunction);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.system.AppFunctionDaoTestCase
 * JD-Core Version:    0.6.0
 */
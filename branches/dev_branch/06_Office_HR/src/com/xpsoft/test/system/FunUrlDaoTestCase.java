/*    */ package com.xpsoft.test.system;
/*    */ 
/*    */ import com.xpsoft.oa.dao.system.FunUrlDao;
/*    */ import com.xpsoft.oa.model.system.FunUrl;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class FunUrlDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private FunUrlDao funUrlDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     FunUrl funUrl = new FunUrl();
/*    */ 
/* 22 */     this.funUrlDao.save(funUrl);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.system.FunUrlDaoTestCase
 * JD-Core Version:    0.6.0
 */
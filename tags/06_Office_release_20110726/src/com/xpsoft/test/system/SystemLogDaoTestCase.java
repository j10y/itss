/*    */ package com.xpsoft.test.system;
/*    */ 
/*    */ import com.xpsoft.oa.dao.system.SystemLogDao;
/*    */ import com.xpsoft.oa.model.system.SystemLog;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class SystemLogDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private SystemLogDao systemLogDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 22 */     SystemLog systemLog = new SystemLog();
/*    */ 
/* 25 */     this.systemLogDao.save(systemLog);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.system.SystemLogDaoTestCase
 * JD-Core Version:    0.6.0
 */
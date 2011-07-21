/*    */ package com.xpsoft.test.system;
/*    */ 
/*    */ import com.xpsoft.oa.dao.system.FileAttachDao;
/*    */ import com.xpsoft.oa.model.system.FileAttach;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class FileAttachDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private FileAttachDao fileAttachDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     FileAttach fileAttach = new FileAttach();
/*    */ 
/* 23 */     this.fileAttachDao.removeByPath("communicate/mail/200910/2a4367669a1a4ea2b811013ceed199ce.png");
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.system.FileAttachDaoTestCase
 * JD-Core Version:    0.6.0
 */
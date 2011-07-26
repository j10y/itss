/*    */ package com.xpsoft.test.communicate;
/*    */ 
/*    */ import com.xpsoft.oa.dao.communicate.PhoneGroupDao;
/*    */ import com.xpsoft.oa.model.communicate.PhoneGroup;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class PhoneGroupDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private PhoneGroupDao phoneGroupDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     PhoneGroup phoneGroup = new PhoneGroup();
/*    */ 
/* 22 */     this.phoneGroupDao.save(phoneGroup);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.communicate.PhoneGroupDaoTestCase
 * JD-Core Version:    0.6.0
 */
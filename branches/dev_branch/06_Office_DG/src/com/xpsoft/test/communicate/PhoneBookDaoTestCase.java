/*    */ package com.xpsoft.test.communicate;
/*    */ 
/*    */ import com.xpsoft.oa.dao.communicate.PhoneBookDao;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import flexjson.JSONSerializer;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ 
/*    */ public class PhoneBookDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private PhoneBookDao phoneBookDao;
/*    */ 
/*    */   @Test
/*    */   public void test()
/*    */   {
/* 36 */     List phoneBook = this.phoneBookDao.getAll();
/*    */ 
/* 38 */     JSONSerializer serializer = new JSONSerializer();
/*    */ 
/* 40 */     System.out.println("josn:" + serializer.exclude(new String[] { "class", "phoneGroup", "appUser.department" }).prettyPrint(phoneBook));
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.communicate.PhoneBookDaoTestCase
 * JD-Core Version:    0.6.0
 */
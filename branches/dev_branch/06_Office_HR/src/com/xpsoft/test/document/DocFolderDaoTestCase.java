/*    */ package com.xpsoft.test.document;
/*    */ 
/*    */ import com.xpsoft.oa.dao.document.DocFolderDao;
/*    */ import com.xpsoft.oa.dao.system.AppUserDao;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ 
/*    */ public class DocFolderDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DocFolderDao docFolderDao;
/*    */ 
/*    */   @Resource
/*    */   private AppUserDao appUserDao;
/*    */ 
/*    */   @Test
/*    */   public void move()
/*    */   {
/* 52 */     String st = "1.2.3.6.5.";
/* 53 */     boolean ss = Pattern.compile("1.3").matcher(st).find();
/* 54 */     System.out.println(ss);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.document.DocFolderDaoTestCase
 * JD-Core Version:    0.6.0
 */
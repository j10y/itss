/*    */ package com.xpsoft.test.system;
/*    */ 
/*    */ import com.xpsoft.oa.dao.system.DictionaryDao;
/*    */ import com.xpsoft.oa.model.system.Dictionary;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class DictionaryDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DictionaryDao dictionaryDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     Dictionary dictionary = new Dictionary();
/*    */ 
/* 22 */     this.dictionaryDao.save(dictionary);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.system.DictionaryDaoTestCase
 * JD-Core Version:    0.6.0
 */
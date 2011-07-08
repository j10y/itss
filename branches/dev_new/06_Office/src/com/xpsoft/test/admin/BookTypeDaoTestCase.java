/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.xpsoft.oa.dao.admin.BookTypeDao;
/*    */ import com.xpsoft.oa.model.admin.BookType;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class BookTypeDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private BookTypeDao bookTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     BookType bookType = new BookType();
/*    */ 
/* 22 */     this.bookTypeDao.save(bookType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.BookTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
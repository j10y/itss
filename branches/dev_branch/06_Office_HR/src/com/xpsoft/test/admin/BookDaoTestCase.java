/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.xpsoft.oa.dao.admin.BookDao;
/*    */ import com.xpsoft.oa.model.admin.Book;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class BookDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private BookDao bookDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     Book book = new Book();
/*    */ 
/* 22 */     this.bookDao.save(book);
/*    */   }
/* 26 */   @Test
/*    */   public void Testt() { List list = this.bookDao.getAll();
/* 27 */     Gson gson = new Gson();
/* 28 */     System.out.println(gson.toJson(list));
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.BookDaoTestCase
 * JD-Core Version:    0.6.0
 */
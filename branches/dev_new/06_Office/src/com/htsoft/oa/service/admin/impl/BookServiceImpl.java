/*    */ package com.htsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.core.web.paging.PagingBean;
/*    */ import com.htsoft.oa.dao.admin.BookDao;
/*    */ import com.htsoft.oa.model.admin.Book;
/*    */ import com.htsoft.oa.service.admin.BookService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BookServiceImpl extends BaseServiceImpl<Book>
/*    */   implements BookService
/*    */ {
/*    */   private BookDao dao;
/*    */ 
/*    */   public BookServiceImpl(BookDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Book> findByTypeId(Long typeId, PagingBean pb)
/*    */   {
/* 25 */     return this.dao.findByTypeId(typeId, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.admin.impl.BookServiceImpl
 * JD-Core Version:    0.6.0
 */
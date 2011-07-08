/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.BookSnDao;
/*    */ import com.xpsoft.oa.model.admin.BookSn;
/*    */ import com.xpsoft.oa.service.admin.BookSnService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BookSnServiceImpl extends BaseServiceImpl<BookSn>
/*    */   implements BookSnService
/*    */ {
/*    */   private BookSnDao dao;
/*    */ 
/*    */   public BookSnServiceImpl(BookSnDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<BookSn> findByBookId(Long bookId)
/*    */   {
/* 24 */     return this.dao.findByBookId(bookId);
/*    */   }
/*    */ 
/*    */   public List<BookSn> findBorrowByBookId(Long bookId)
/*    */   {
/* 29 */     return this.dao.findBorrowByBookId(bookId);
/*    */   }
/*    */ 
/*    */   public List<BookSn> findReturnByBookId(Long bookId)
/*    */   {
/* 34 */     return this.dao.findReturnByBookId(bookId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.BookSnServiceImpl
 * JD-Core Version:    0.6.0
 */
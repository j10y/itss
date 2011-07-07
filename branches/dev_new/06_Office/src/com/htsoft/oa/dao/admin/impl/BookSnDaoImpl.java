/*    */ package com.htsoft.oa.dao.admin.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.admin.BookSnDao;
/*    */ import com.htsoft.oa.model.admin.BookSn;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BookSnDaoImpl extends BaseDaoImpl<BookSn>
/*    */   implements BookSnDao
/*    */ {
/*    */   public BookSnDaoImpl()
/*    */   {
/* 17 */     super(BookSn.class);
/*    */   }
/*    */ 
/*    */   public List<BookSn> findByBookId(Long bookId)
/*    */   {
/* 22 */     String hql = "from BookSn b where b.book.bookId=?";
/* 23 */     Object[] params = { bookId };
/* 24 */     return findByHql("from BookSn b where b.book.bookId=?", params);
/*    */   }
/*    */ 
/*    */   public List<BookSn> findBorrowByBookId(Long bookId)
/*    */   {
/* 29 */     String hql = "from BookSn b where b.book.bookId=? and b.status=0";
/* 30 */     Object[] params = { bookId };
/* 31 */     return findByHql("from BookSn b where b.book.bookId=? and b.status=0", params);
/*    */   }
/*    */ 
/*    */   public List<BookSn> findReturnByBookId(Long bookId)
/*    */   {
/* 36 */     String hql = "from BookSn b where b.book.bookId=? and b.status=1";
/* 37 */     Object[] params = { bookId };
/* 38 */     return findByHql("from BookSn b where b.book.bookId=? and b.status=1", params);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.admin.impl.BookSnDaoImpl
 * JD-Core Version:    0.6.0
 */
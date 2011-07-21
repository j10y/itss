/*    */ package com.xpsoft.oa.dao.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.admin.BookDao;
/*    */ import com.xpsoft.oa.model.admin.Book;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BookDaoImpl extends BaseDaoImpl<Book>
/*    */   implements BookDao
/*    */ {
/*    */   public BookDaoImpl()
/*    */   {
/* 17 */     super(Book.class);
/*    */   }
/*    */ 
/*    */   public List<Book> findByTypeId(Long typeId, PagingBean pb)
/*    */   {
/* 22 */     String hql = "from Book b where b.bookType.typeId=?";
/* 23 */     Object[] params = { typeId };
/* 24 */     return findByHql("from Book b where b.bookType.typeId=?", params, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.impl.BookDaoImpl
 * JD-Core Version:    0.6.0
 */
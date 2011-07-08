/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.BookTypeDao;
/*    */ import com.xpsoft.oa.model.admin.BookType;
/*    */ import com.xpsoft.oa.service.admin.BookTypeService;
/*    */ 
/*    */ public class BookTypeServiceImpl extends BaseServiceImpl<BookType>
/*    */   implements BookTypeService
/*    */ {
/*    */   private BookTypeDao dao;
/*    */ 
/*    */   public BookTypeServiceImpl(BookTypeDao dao)
/*    */   {
/* 16 */     super(dao);
/* 17 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.BookTypeServiceImpl
 * JD-Core Version:    0.6.0
 */
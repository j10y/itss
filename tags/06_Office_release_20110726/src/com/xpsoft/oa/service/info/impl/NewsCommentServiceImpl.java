/*    */ package com.xpsoft.oa.service.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.info.NewsCommentDao;
/*    */ import com.xpsoft.oa.model.info.NewsComment;
/*    */ import com.xpsoft.oa.service.info.NewsCommentService;
/*    */ 
/*    */ public class NewsCommentServiceImpl extends BaseServiceImpl<NewsComment>
/*    */   implements NewsCommentService
/*    */ {
/*    */   private NewsCommentDao dao;
/*    */ 
/*    */   public NewsCommentServiceImpl(NewsCommentDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.impl.NewsCommentServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.service.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.NewsDao;
/*    */ import com.xpsoft.oa.model.info.News;
/*    */ import com.xpsoft.oa.service.info.NewsService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class NewsServiceImpl extends BaseServiceImpl<News>
/*    */   implements NewsService
/*    */ {
/*    */   private NewsDao newsDao;
/*    */ 
/*    */   public NewsServiceImpl(NewsDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.newsDao = dao;
/*    */   }
/*    */ 
/*    */   public List<News> findByTypeId(Long typeId, PagingBean pb)
/*    */   {
/* 25 */     return this.newsDao.findByTypeId(typeId, pb);
/*    */   }
/*    */ 
/*    */   public List<News> findBySearch(String searchContent, PagingBean pb)
/*    */   {
/* 30 */     return this.newsDao.findBySearch(searchContent, pb);
/*    */   }
/*    */ 
/*    */   public List<News> findImageNews(PagingBean pb)
/*    */   {
/* 35 */     return this.newsDao.findImageNews(pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.impl.NewsServiceImpl
 * JD-Core Version:    0.6.0
 */
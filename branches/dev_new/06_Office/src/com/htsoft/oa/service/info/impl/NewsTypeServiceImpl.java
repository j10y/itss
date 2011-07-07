/*    */ package com.htsoft.oa.service.info.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.core.web.paging.PagingBean;
/*    */ import com.htsoft.oa.dao.info.NewsTypeDao;
/*    */ import com.htsoft.oa.model.info.NewsType;
/*    */ import com.htsoft.oa.service.info.NewsTypeService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class NewsTypeServiceImpl extends BaseServiceImpl<NewsType>
/*    */   implements NewsTypeService
/*    */ {
/*    */   private NewsTypeDao newsTypeDao;
/*    */ 
/*    */   public NewsTypeServiceImpl(NewsTypeDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.newsTypeDao = dao;
/*    */   }
/*    */ 
/*    */   public Short getTop()
/*    */   {
/* 25 */     return this.newsTypeDao.getTop();
/*    */   }
/*    */ 
/*    */   public NewsType findBySn(Short sn)
/*    */   {
/* 30 */     return this.newsTypeDao.findBySn(sn);
/*    */   }
/*    */ 
/*    */   public List<NewsType> getAllBySn()
/*    */   {
/* 35 */     return this.newsTypeDao.getAllBySn();
/*    */   }
/*    */ 
/*    */   public List<NewsType> getAllBySn(PagingBean pb)
/*    */   {
/* 40 */     return this.newsTypeDao.getAllBySn(pb);
/*    */   }
/*    */ 
/*    */   public List<NewsType> findBySearch(NewsType newsType, PagingBean pb)
/*    */   {
/* 45 */     return this.newsTypeDao.findBySearch(newsType, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.info.impl.NewsTypeServiceImpl
 * JD-Core Version:    0.6.0
 */
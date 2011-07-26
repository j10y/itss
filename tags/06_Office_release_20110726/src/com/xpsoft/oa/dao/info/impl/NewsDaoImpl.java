/*    */ package com.xpsoft.oa.dao.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.Constants;
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.NewsDao;
/*    */ import com.xpsoft.oa.model.info.News;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class NewsDaoImpl extends BaseDaoImpl<News>
/*    */   implements NewsDao
/*    */ {
/*    */   public NewsDaoImpl()
/*    */   {
/* 19 */     super(News.class);
/*    */   }
/*    */ 
/*    */   public List<News> findByTypeId(Long typeId, PagingBean pb)
/*    */   {
/* 24 */     String hql = "from News n where n.newsType.typeId=?";
/* 25 */     Object[] params = { typeId };
/* 26 */     return findByHql("from News n where n.newsType.typeId=?", params, pb);
/*    */   }
/*    */ 
/*    */   public List<News> findBySearch(String searchContent, PagingBean pb)
/*    */   {
/* 31 */     ArrayList params = new ArrayList();
/* 32 */     StringBuffer hql = new StringBuffer("from News n where n.status = ?");
/* 33 */     params.add(Constants.FLAG_ACTIVATION);
/* 34 */     if (StringUtils.isNotEmpty(searchContent)) {
/* 35 */       hql.append(" and (n.subject like ? or n.content like ?)");
/* 36 */       params.add("%" + searchContent + "%");
/* 37 */       params.add("%" + searchContent + "%");
/*    */     }
/* 39 */     hql.append(" order by n.updateTime desc");
/* 40 */     return findByHql(hql.toString(), params.toArray(), pb);
/*    */   }
/*    */ 
/*    */   public List<News> findImageNews(PagingBean pb)
/*    */   {
/* 45 */     String hql = "from News vo where vo.isDeskImage=1 order by vo.updateTime desc";
/* 46 */     return findByHql(hql, new Object[0], pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.info.impl.NewsDaoImpl
 * JD-Core Version:    0.6.0
 */
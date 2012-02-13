/*    */ package com.xpsoft.oa.dao.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.NewsTypeDao;
/*    */ import com.xpsoft.oa.model.info.NewsType;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class NewsTypeDaoImpl extends BaseDaoImpl<NewsType>
/*    */   implements NewsTypeDao
/*    */ {
/*    */   public NewsTypeDaoImpl()
/*    */   {
/* 17 */     super(NewsType.class);
/*    */   }
/*    */ 
/*    */   public Short getTop()
/*    */   {
/* 25 */     String hql = "select max(sn) from NewsType";
/* 26 */     List list = findByHql("select max(sn) from NewsType");
/* 27 */     return (Short)list.get(0);
/*    */   }
/*    */ 
/*    */   public List<NewsType> getAllBySn()
/*    */   {
/* 34 */     String hql = "from NewsType nt order by nt.sn asc";
/* 35 */     return findByHql("from NewsType nt order by nt.sn asc");
/*    */   }
/*    */ 
/*    */   public List<NewsType> getAllBySn(PagingBean pb)
/*    */   {
/* 42 */     String hql = "from NewsType nt order by nt.sn asc";
/* 43 */     return findByHql("from NewsType nt order by nt.sn asc", null, pb);
/*    */   }
/*    */ 
/*    */   public NewsType findBySn(Short sn)
/*    */   {
/* 50 */     String hql = "from NewsType nt where nt.sn=?";
/* 51 */     Object[] objs = { sn };
/* 52 */     List list = findByHql("from NewsType nt where nt.sn=?", objs);
/* 53 */     return (NewsType)list.get(0);
/*    */   }
/*    */ 
/*    */   public List<NewsType> findBySearch(NewsType newsType, PagingBean pb)
/*    */   {
/* 60 */     StringBuffer hql = new StringBuffer("from NewsType nt where 1=1 ");
/* 61 */     List params = new ArrayList();
/* 62 */     if (newsType != null) {
/* 63 */       if ((!"".equals(newsType.getTypeName())) && (newsType.getTypeName() != null)) {
/* 64 */         hql.append("and nt.typeName like ?");
/* 65 */         params.add("%" + newsType.getTypeName() + "%");
/*    */       }
/* 67 */       if ((newsType.getSn() != null) && (newsType.getSn().shortValue() > 0)) {
/* 68 */         hql.append("and nt.sn = ?");
/* 69 */         params.add(newsType.getSn());
/*    */       }
/*    */     }
/* 72 */     hql.append("order by nt.sn asc");
/* 73 */     return findByHql(hql.toString(), params.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.info.impl.NewsTypeDaoImpl
 * JD-Core Version:    0.6.0
 */
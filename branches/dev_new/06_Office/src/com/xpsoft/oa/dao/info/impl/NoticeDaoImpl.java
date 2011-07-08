/*    */ package com.xpsoft.oa.dao.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.Constants;
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.NoticeDao;
/*    */ import com.xpsoft.oa.model.info.Notice;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class NoticeDaoImpl extends BaseDaoImpl<Notice>
/*    */   implements NoticeDao
/*    */ {
/*    */   public NoticeDaoImpl()
/*    */   {
/* 21 */     super(Notice.class);
/*    */   }
/*    */ 
/*    */   public List<Notice> findBySearch(Notice notice, Date from, Date to, PagingBean pb)
/*    */   {
/* 27 */     StringBuffer hql = new StringBuffer("from Notice notice where 1=1");
/* 28 */     List params = new ArrayList();
/* 29 */     if ((!"".equals(notice.getPostName())) && (notice.getPostName() != null)) {
/* 30 */       hql.append("and notice.postName like ?");
/* 31 */       params.add("%" + notice.getPostName() + "%");
/*    */     }
/* 33 */     if ((!"".equals(notice.getNoticeTitle())) && (notice.getNoticeTitle() != null)) {
/* 34 */       hql.append("and notice.noticeTitle like ?");
/* 35 */       params.add("%" + notice.getNoticeTitle() + "%");
/*    */     }
/* 37 */     if (from != null) {
/* 38 */       hql.append("and notice.effectivDate > ?");
/* 39 */       params.add(from);
/*    */     }
/* 41 */     if (to != null) {
/* 42 */       hql.append("and notice.expirationDate < ?");
/* 43 */       params.add(to);
/*    */     }
/* 45 */     return findByHql(hql.toString(), params.toArray(), pb);
/*    */   }
/*    */ 
/*    */   public List<Notice> findByNoticeId(Long noticeId, PagingBean pb)
/*    */   {
/* 50 */     String hql = "from Notice notice where notice.noticeId=?";
/* 51 */     Object[] params = { noticeId };
/* 52 */     return findByHql("from Notice notice where notice.noticeId=?", params, pb);
/*    */   }
/*    */ 
/*    */   public List<Notice> findBySearch(String searchContent, PagingBean pb)
/*    */   {
/* 57 */     ArrayList params = new ArrayList();
/* 58 */     StringBuffer hql = new StringBuffer("from Notice nt where nt.state = ?");
/* 59 */     params.add(Constants.FLAG_ACTIVATION);
/* 60 */     if (StringUtils.isNotEmpty(searchContent)) {
/* 61 */       hql.append(" and (nt.noticeTitle like ? or nt.noticeContent like ?)");
/* 62 */       params.add("%" + searchContent + "%");
/* 63 */       params.add("%" + searchContent + "%");
/*    */     }
/* 65 */     hql.append(" order by nt.noticeId desc");
/* 66 */     return findByHql(hql.toString(), params.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.info.impl.NoticeDaoImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.dao.communicate.impl;
/*    */ 
/*    */ import com.htsoft.core.Constants;
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.core.util.ContextUtil;
/*    */ import com.htsoft.core.web.paging.PagingBean;
/*    */ import com.htsoft.oa.dao.communicate.MailBoxDao;
/*    */ import com.htsoft.oa.model.communicate.MailBox;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class MailBoxDaoImpl extends BaseDaoImpl<MailBox>
/*    */   implements MailBoxDao
/*    */ {
/*    */   public MailBoxDaoImpl()
/*    */   {
/* 22 */     super(MailBox.class);
/*    */   }
/*    */ 
/*    */   public Long CountByFolderId(Long folderId)
/*    */   {
/* 27 */     String hql = "select count(*) from MailBox where folderId =?";
/*    */ 
/* 29 */     Query query = getSession().createQuery(hql);
/* 30 */     query.setLong(0, folderId.longValue());
/* 31 */     return (Long)query.uniqueResult();
/*    */   }
/*    */ 
/*    */   public List<MailBox> findByFolderId(Long folderId) {
/* 35 */     String hql = "from MailBox where folderId = ?";
/* 36 */     return findByHql(hql, new Object[] { folderId });
/*    */   }
/*    */ 
/*    */   public List<MailBox> findBySearch(String searchContent, PagingBean pb)
/*    */   {
/* 41 */     ArrayList params = new ArrayList();
/*    */ 
/* 43 */     StringBuffer hql = new StringBuffer("from MailBox mb where mb.delFlag = ? and mb.appUser.userId =?");
/* 44 */     params.add(Constants.FLAG_UNDELETED);
/* 45 */     params.add(ContextUtil.getCurrentUserId());
/*    */ 
/* 47 */     if (StringUtils.isNotEmpty(searchContent)) {
/* 48 */       hql.append(" and (mb.mail.subject like ? or mb.mail.content like ?)");
/* 49 */       params.add("%" + searchContent + "%");
/* 50 */       params.add("%" + searchContent + "%");
/*    */     }
/*    */ 
/* 53 */     hql.append(" order by mb.sendTime desc");
/* 54 */     return findByHql(hql.toString(), params.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.communicate.impl.MailBoxDaoImpl
 * JD-Core Version:    0.6.0
 */
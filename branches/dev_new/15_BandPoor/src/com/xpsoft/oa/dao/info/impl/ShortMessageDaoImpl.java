/*    */ package com.xpsoft.oa.dao.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.ShortMessageDao;
/*    */ import com.xpsoft.oa.model.info.ShortMessage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ShortMessageDaoImpl extends BaseDaoImpl<ShortMessage>
/*    */   implements ShortMessageDao
/*    */ {
/*    */   public ShortMessageDaoImpl()
/*    */   {
/* 23 */     super(ShortMessage.class);
/*    */   }
/*    */ 
/*    */   public List<ShortMessage> findAll(Long userId, PagingBean pb)
/*    */   {
/* 28 */     String hql = "from ShortMessage vo where vo.senderId=?";
/* 29 */     Object[] objs = { userId };
/* 30 */     return findByHql(hql, objs, pb);
/*    */   }
/*    */ 
/*    */   public List<ShortMessage> findByUser(Long userId)
/*    */   {
/* 35 */     String hql = "from ShortMessage vo where vo.senderId=?";
/* 36 */     Object[] objs = { userId };
/* 37 */     return findByHql(hql, objs);
/*    */   }
/*    */ 
/*    */   public List searchShortMessage(Long userId, ShortMessage shortMessage, Date from, Date to, PagingBean pb)
/*    */   {
/* 47 */     ArrayList paramList = new ArrayList();
/* 48 */     StringBuffer hql = new StringBuffer("select vo1,vo2 from InMessage vo1,ShortMessage vo2 where vo1.shortMessage=vo2 and vo1.delFlag=0 and vo1.userId=?");
/* 49 */     paramList.add(userId);
/* 50 */     if (shortMessage != null) {
/* 51 */       if (shortMessage.getMsgType() != null) {
/* 52 */         hql.append(" and vo2.msgType=?");
/* 53 */         paramList.add(shortMessage.getMsgType());
/*    */       }
/* 55 */       if (StringUtils.isNotEmpty(shortMessage.getSender())) {
/* 56 */         hql.append(" and vo2.sender=?");
/* 57 */         paramList.add(shortMessage.getSender());
/*    */       }
/*    */     }
/* 60 */     if (to != null) {
/* 61 */       hql.append("and vo2.sendTime <= ?");
/* 62 */       paramList.add(to);
/*    */     }
/* 64 */     if (from != null) {
/* 65 */       hql.append("and vo2.sendTime >= ?");
/* 66 */       paramList.add(from);
/*    */     }
/* 68 */     hql.append(" order by vo2.sendTime desc");
/* 69 */     return findByHql(hql.toString(), paramList.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.info.impl.ShortMessageDaoImpl
 * JD-Core Version:    0.6.0
 */
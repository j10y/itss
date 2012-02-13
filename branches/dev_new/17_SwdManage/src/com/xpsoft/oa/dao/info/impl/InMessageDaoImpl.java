 package com.xpsoft.oa.dao.info.impl;
 
 import com.xpsoft.core.dao.impl.BaseDaoImpl;
 import com.xpsoft.core.web.paging.PagingBean;
 import com.xpsoft.oa.dao.info.InMessageDao;
 import com.xpsoft.oa.model.info.InMessage;
 import com.xpsoft.oa.model.info.ShortMessage;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.hibernate.Query;
 import org.hibernate.Session;
 
 public class InMessageDaoImpl extends BaseDaoImpl<InMessage>
   implements InMessageDao
 {
   public InMessageDaoImpl()
   {
/*  26 */     super(InMessage.class);
   }
 
   public InMessage findByRead(Long userId)
   {
/*  34 */     String hql = "from InMessage vo where vo.readFlag=0 and vo.delFlag=0 and vo.userId=?";
/*  35 */     Object[] objs = { userId };
/*  36 */     List list = findByHql(hql, objs);
/*  37 */     if (list.size() > 0) {
/*  38 */       return (InMessage)list.get(list.size() - 1);
     }
/*  40 */     return null;
   }
 
   public Integer findByReadFlag(Long userId)
   {
/*  47 */     String sql = "select count(*) from InMessage vo where vo.readFlag=0 and vo.delFlag=0 and vo.userId=" + userId;
/*  48 */     Query query = getSession().createQuery(sql);
/*  49 */     return Integer.valueOf(Integer.parseInt(query.list().iterator().next().toString()));
   }
 
   public List<InMessage> findAll(Long userId, PagingBean pb)
   {
/*  54 */     String hql = "from InMessage vo where vo.userId=?";
/*  55 */     Object[] objs = { userId };
/*  56 */     return findByHql(hql, objs, pb);
   }
 
   public List<InMessage> findByShortMessage(ShortMessage shortMessage, PagingBean pb)
   {
/*  61 */     String hql = "from InMessage vo1,ShortMessage vo2 where vo1.shortMessage=?";
/*  62 */     Object[] objs = { shortMessage };
/*  63 */     return findByHql(hql, objs, pb);
   }
 
   public List findByUser(Long userId, PagingBean pb)
   {
/*  68 */     String hql = "select vo1,vo2 from InMessage vo1,ShortMessage vo2 where vo1.shortMessage=vo2 and vo2.msgType=1 and vo2.senderId=? order by vo2.sendTime desc";
/*  69 */     Object[] objs = { userId };
/*  70 */     return findByHql(hql, objs, pb);
   }
 
   public List findByUser(Long userId)
   {
/*  75 */     String hql = "select vo1,vo2 from InMessage vo1,ShortMessage vo2 where vo1.shortMessage=vo2 and vo2.senderId=?";
/*  76 */     Object[] objs = { userId };
/*  77 */     return findByHql(hql, objs);
   }
 
   public List searchInMessage(Long userId, InMessage inMessage, ShortMessage shortMessage, Date from, Date to, PagingBean pb)
   {
/*  88 */     StringBuffer hql = new StringBuffer("select vo1 ,vo2 from InMessage vo1,ShortMessage vo2 where vo1.shortMessage=vo2 and vo2.msgType=1 and vo2.senderId=?");
/*  89 */     ArrayList paramList = new ArrayList();
/*  90 */     paramList.add(userId);
/*  91 */     if (to != null) {
/*  92 */       hql.append("and vo2.sendTime <= ?");
/*  93 */       paramList.add(to);
     }
/*  95 */     if (from != null) {
/*  96 */       hql.append("and vo2.sendTime >= ?");
/*  97 */       paramList.add(from);
     }
/*  99 */     if ((shortMessage != null) && 
/* 100 */       (shortMessage.getMsgType() != null)) {
/* 101 */       hql.append(" and vo2.msgType=?");
/* 102 */       paramList.add(shortMessage.getMsgType());
     }
 
/* 105 */     if ((inMessage != null) && 
/* 106 */       (StringUtils.isNotEmpty(inMessage.getUserFullname()))) {
/* 107 */       hql.append(" and vo1.userFullname=?");
/* 108 */       paramList.add(inMessage.getUserFullname());
     }
 
/* 111 */     hql.append(" order by vo2.sendTime desc");
 
/* 113 */     return findByHql(hql.toString(), paramList.toArray(), pb);
   }
 
   public InMessage findLatest(Long userId)
   {
/* 118 */     String hql = "from InMessage vo where vo.delFlag=0 and vo.userId=?";
/* 119 */     Object[] objs = { userId };
/* 120 */     List list = findByHql(hql, objs);
/* 121 */     if (list.size() > 0) {
/* 122 */       return (InMessage)list.get(list.size() - 1);
     }
/* 124 */     return null;
   }
 }

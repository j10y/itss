/*    */ package com.xpsoft.oa.dao.communicate.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.communicate.PhoneBookDao;
/*    */ import com.xpsoft.oa.model.communicate.PhoneBook;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class PhoneBookDaoImpl extends BaseDaoImpl<PhoneBook>
/*    */   implements PhoneBookDao
/*    */ {
/*    */   public PhoneBookDaoImpl()
/*    */   {
/* 19 */     super(PhoneBook.class);
/*    */   }
/*    */ 
/*    */   public List<PhoneBook> sharedPhoneBooks(String fullname, String ownerName, PagingBean pb)
/*    */   {
/* 24 */     StringBuffer hql = new StringBuffer("select pb from PhoneBook pb,PhoneGroup pg where pb.phoneGroup=pg and (pg.isShared=1 or pb.isShared=1)");
/* 25 */     List list = new ArrayList();
/* 26 */     if (StringUtils.isNotEmpty(fullname)) {
/* 27 */       hql.append(" and pb.fullname like ?");
/* 28 */       list.add("%" + fullname + "%");
/*    */     }
/* 30 */     if (StringUtils.isNotEmpty(ownerName)) {
/* 31 */       hql.append(" and pb.appUser.fullname like ?");
/* 32 */       list.add("%" + ownerName + "%");
/*    */     }
/* 34 */     return findByHql(hql.toString(), list.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.communicate.impl.PhoneBookDaoImpl
 * JD-Core Version:    0.6.0
 */
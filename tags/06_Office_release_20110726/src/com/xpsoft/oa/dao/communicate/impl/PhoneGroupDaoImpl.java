/*    */ package com.xpsoft.oa.dao.communicate.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.communicate.PhoneGroupDao;
/*    */ import com.xpsoft.oa.model.communicate.PhoneGroup;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PhoneGroupDaoImpl extends BaseDaoImpl<PhoneGroup>
/*    */   implements PhoneGroupDao
/*    */ {
/*    */   public PhoneGroupDaoImpl()
/*    */   {
/* 15 */     super(PhoneGroup.class);
/*    */   }
/*    */ 
/*    */   public Integer findLastSn(Long userId)
/*    */   {
/* 20 */     String hql = "select max(sn) from PhoneGroup vo where vo.appUser.userId=?";
/* 21 */     Object[] object = { userId };
/* 22 */     List list = findByHql(hql, object);
/* 23 */     return (Integer)list.get(0);
/*    */   }
/*    */ 
/*    */   public PhoneGroup findBySn(Integer sn, Long userId)
/*    */   {
/* 28 */     String hql = "select vo from PhoneGroup vo where vo.appUser.userId=? and vo.sn=?";
/* 29 */     Object[] object = { userId, sn };
/* 30 */     List list = findByHql(hql, object);
/* 31 */     return (PhoneGroup)list.get(0);
/*    */   }
/*    */ 
/*    */   public List<PhoneGroup> findBySnUp(Integer sn, Long userId)
/*    */   {
/* 36 */     String hql = "from PhoneGroup vo where vo.appUser.userId=? and vo.sn<?";
/* 37 */     Object[] object = { userId, sn };
/* 38 */     return findByHql(hql, object);
/*    */   }
/*    */ 
/*    */   public List<PhoneGroup> findBySnDown(Integer sn, Long userId)
/*    */   {
/* 43 */     String hql = "from PhoneGroup vo where vo.appUser.userId=? and vo.sn>?";
/* 44 */     Object[] object = { userId, sn };
/* 45 */     return findByHql(hql, object);
/*    */   }
/*    */ 
/*    */   public List<PhoneGroup> getAll(Long userId)
/*    */   {
/* 50 */     String hql = "from PhoneGroup vo where vo.appUser.userId=? order by vo.sn asc";
/* 51 */     Object[] object = { userId };
/* 52 */     return findByHql(hql, object);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.communicate.impl.PhoneGroupDaoImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.archive.ArchRecUserDao;
/*    */ import com.htsoft.oa.model.archive.ArchRecUser;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchRecUserDaoImpl extends BaseDaoImpl<ArchRecUser>
/*    */   implements ArchRecUserDao
/*    */ {
/*    */   public ArchRecUserDaoImpl()
/*    */   {
/* 15 */     super(ArchRecUser.class);
/*    */   }
/*    */ 
/*    */   public List findDepAll()
/*    */   {
/* 20 */     String hql = "select ar,dp from ArchRecUser ar right join ar.department dp";
/* 21 */     return findByHql(hql);
/*    */   }
/*    */ 
/*    */   public ArchRecUser getByDepId(Long depId)
/*    */   {
/* 26 */     String hql = "from ArchRecUser ar where ar.department.depId =?";
/* 27 */     List list = findByHql(hql, new Object[] { depId });
/* 28 */     if (list.size() > 0) {
/* 29 */       return (ArchRecUser)list.get(0);
/*    */     }
/* 31 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.archive.impl.ArchRecUserDaoImpl
 * JD-Core Version:    0.6.0
 */
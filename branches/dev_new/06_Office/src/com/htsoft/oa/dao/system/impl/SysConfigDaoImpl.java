/*    */ package com.htsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.system.SysConfigDao;
/*    */ import com.htsoft.oa.model.system.SysConfig;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class SysConfigDaoImpl extends BaseDaoImpl<SysConfig>
/*    */   implements SysConfigDao
/*    */ {
/*    */   public SysConfigDaoImpl()
/*    */   {
/* 17 */     super(SysConfig.class);
/*    */   }
/*    */ 
/*    */   public SysConfig findByKey(String key)
/*    */   {
/* 22 */     String hql = "from SysConfig vo where vo.configKey=?";
/* 23 */     Object[] objs = { key };
/* 24 */     List list = findByHql(hql, objs);
/* 25 */     return (SysConfig)list.get(0);
/*    */   }
/*    */ 
/*    */   public List<SysConfig> findConfigByTypeName(String typeName)
/*    */   {
/* 30 */     String hql = "from SysConfig vo where vo.typeName=?";
/* 31 */     Object[] objs = { typeName };
/* 32 */     return findByHql(hql, objs);
/*    */   }
/*    */ 
/*    */   public List findTypeNames()
/*    */   {
/* 37 */     String sql = "select vo.typeName from SysConfig vo group by vo.typeName";
/* 38 */     Query query = getSession().createQuery(sql);
/* 39 */     return query.list();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.system.impl.SysConfigDaoImpl
 * JD-Core Version:    0.6.0
 */
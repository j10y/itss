/*    */ package com.htsoft.oa.dao.flow.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.flow.ProDefinitionDao;
/*    */ import com.htsoft.oa.model.flow.ProDefinition;
/*    */ 
/*    */ public class ProDefinitionDaoImpl extends BaseDaoImpl<ProDefinition>
/*    */   implements ProDefinitionDao
/*    */ {
/*    */   public ProDefinitionDaoImpl()
/*    */   {
/* 13 */     super(ProDefinition.class);
/*    */   }
/*    */ 
/*    */   public ProDefinition getByDeployId(String deployId) {
/* 17 */     String hql = "from ProDefinition pd where pd.deployId=?";
/* 18 */     return (ProDefinition)findUnique(hql, new Object[] { deployId });
/*    */   }
/*    */ 
/*    */   public ProDefinition getByName(String name) {
/* 22 */     String hql = "from ProDefinition pd where pd.name=?";
/* 23 */     return (ProDefinition)findUnique(hql, new Object[] { name });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.flow.impl.ProDefinitionDaoImpl
 * JD-Core Version:    0.6.0
 */
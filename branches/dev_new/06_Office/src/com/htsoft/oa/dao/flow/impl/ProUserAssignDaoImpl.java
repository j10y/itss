/*    */ package com.htsoft.oa.dao.flow.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.flow.ProUserAssignDao;
/*    */ import com.htsoft.oa.model.flow.ProUserAssign;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ProUserAssignDaoImpl extends BaseDaoImpl<ProUserAssign>
/*    */   implements ProUserAssignDao
/*    */ {
/*    */   public ProUserAssignDaoImpl()
/*    */   {
/* 15 */     super(ProUserAssign.class);
/*    */   }
/*    */ 
/*    */   public List<ProUserAssign> getByDeployId(String deployId) {
/* 19 */     String hql = "from ProUserAssign pua where pua.deployId=?";
/* 20 */     return findByHql(hql, new Object[] { deployId });
/*    */   }
/*    */ 
/*    */   public ProUserAssign getByDeployIdActivityName(String deployId, String activityName)
/*    */   {
/* 28 */     String hql = "from ProUserAssign pua where pua.deployId=? and pua.activityName=?";
/* 29 */     return (ProUserAssign)findUnique(hql, new Object[] { deployId, activityName });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.flow.impl.ProUserAssignDaoImpl
 * JD-Core Version:    0.6.0
 */
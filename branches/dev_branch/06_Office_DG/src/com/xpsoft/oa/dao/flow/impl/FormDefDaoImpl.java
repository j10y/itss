/*    */ package com.xpsoft.oa.dao.flow.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.flow.FormDefDao;
/*    */ import com.xpsoft.oa.model.flow.FormDef;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FormDefDaoImpl extends BaseDaoImpl<FormDef>
/*    */   implements FormDefDao
/*    */ {
/*    */   public FormDefDaoImpl()
/*    */   {
/* 15 */     super(FormDef.class);
/*    */   }
/*    */ 
/*    */   public List<FormDef> getByDeployId(String deployId)
/*    */   {
/* 20 */     String hql = "from FormDef fd where deployId=?";
/* 21 */     return findByHql(hql, new Object[] { deployId });
/*    */   }
/*    */ 
/*    */   public FormDef getByDeployIdActivityName(String deployId, String activityName)
/*    */   {
/* 31 */     String hql = "from FormDef fd where fd.deployId=? and fd.activityName=?";
/* 32 */     return (FormDef)findUnique(hql, new Object[] { deployId, activityName });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.flow.impl.FormDefDaoImpl
 * JD-Core Version:    0.6.0
 */
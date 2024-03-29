/*    */ package com.xpsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.system.AppFunctionDao;
/*    */ import com.xpsoft.oa.model.system.AppFunction;
/*    */ 
/*    */ public class AppFunctionDaoImpl extends BaseDaoImpl<AppFunction>
/*    */   implements AppFunctionDao
/*    */ {
/*    */   public AppFunctionDaoImpl()
/*    */   {
/* 13 */     super(AppFunction.class);
/*    */   }
/*    */ 
/*    */   public AppFunction getByKey(String key)
/*    */   {
/* 21 */     String hql = "from AppFunction af where af.funKey=?";
/* 22 */     return (AppFunction)findUnique(hql, new String[] { key });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.impl.AppFunctionDaoImpl
 * JD-Core Version:    0.6.0
 */
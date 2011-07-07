/*    */ package com.htsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.system.FunUrlDao;
/*    */ import com.htsoft.oa.model.system.FunUrl;
/*    */ 
/*    */ public class FunUrlDaoImpl extends BaseDaoImpl<FunUrl>
/*    */   implements FunUrlDao
/*    */ {
/*    */   public FunUrlDaoImpl()
/*    */   {
/* 13 */     super(FunUrl.class);
/*    */   }
/*    */ 
/*    */   public FunUrl getByPathFunId(String path, Long funId)
/*    */   {
/* 21 */     String hql = "from FunUrl fu where fu.urlPath=? and fu.appFunction.functionId=? ";
/* 22 */     return (FunUrl)findUnique(hql, new Object[] { path, funId });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.system.impl.FunUrlDaoImpl
 * JD-Core Version:    0.6.0
 */
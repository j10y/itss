/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.AppFunctionDao;
/*    */ import com.xpsoft.oa.model.system.AppFunction;
/*    */ import com.xpsoft.oa.service.system.AppFunctionService;
/*    */ 
/*    */ public class AppFunctionServiceImpl extends BaseServiceImpl<AppFunction>
/*    */   implements AppFunctionService
/*    */ {
/*    */   private AppFunctionDao dao;
/*    */ 
/*    */   public AppFunctionServiceImpl(AppFunctionDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public AppFunction getByKey(String key)
/*    */   {
/* 24 */     return this.dao.getByKey(key);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.AppFunctionServiceImpl
 * JD-Core Version:    0.6.0
 */
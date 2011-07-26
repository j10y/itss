/*    */ package com.xpsoft.oa.service.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.info.AppTipsDao;
/*    */ import com.xpsoft.oa.model.info.AppTips;
/*    */ import com.xpsoft.oa.service.info.AppTipsService;
/*    */ 
/*    */ public class AppTipsServiceImpl extends BaseServiceImpl<AppTips>
/*    */   implements AppTipsService
/*    */ {
/*    */   private AppTipsDao dao;
/*    */ 
/*    */   public AppTipsServiceImpl(AppTipsDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.impl.AppTipsServiceImpl
 * JD-Core Version:    0.6.0
 */
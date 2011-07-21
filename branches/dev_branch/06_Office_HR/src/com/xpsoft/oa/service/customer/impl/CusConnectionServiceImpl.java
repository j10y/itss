/*    */ package com.xpsoft.oa.service.customer.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.customer.CusConnectionDao;
/*    */ import com.xpsoft.oa.model.customer.CusConnection;
/*    */ import com.xpsoft.oa.service.customer.CusConnectionService;
/*    */ 
/*    */ public class CusConnectionServiceImpl extends BaseServiceImpl<CusConnection>
/*    */   implements CusConnectionService
/*    */ {
/*    */   private CusConnectionDao dao;
/*    */ 
/*    */   public CusConnectionServiceImpl(CusConnectionDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.impl.CusConnectionServiceImpl
 * JD-Core Version:    0.6.0
 */
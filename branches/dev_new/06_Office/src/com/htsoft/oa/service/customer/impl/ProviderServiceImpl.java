/*    */ package com.htsoft.oa.service.customer.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.customer.ProviderDao;
/*    */ import com.htsoft.oa.model.customer.Provider;
/*    */ import com.htsoft.oa.service.customer.ProviderService;
/*    */ 
/*    */ public class ProviderServiceImpl extends BaseServiceImpl<Provider>
/*    */   implements ProviderService
/*    */ {
/*    */   private ProviderDao dao;
/*    */ 
/*    */   public ProviderServiceImpl(ProviderDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.customer.impl.ProviderServiceImpl
 * JD-Core Version:    0.6.0
 */
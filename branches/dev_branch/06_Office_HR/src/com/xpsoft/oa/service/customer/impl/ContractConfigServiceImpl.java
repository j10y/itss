/*    */ package com.xpsoft.oa.service.customer.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.customer.ContractConfigDao;
/*    */ import com.xpsoft.oa.model.customer.ContractConfig;
/*    */ import com.xpsoft.oa.service.customer.ContractConfigService;
/*    */ 
/*    */ public class ContractConfigServiceImpl extends BaseServiceImpl<ContractConfig>
/*    */   implements ContractConfigService
/*    */ {
/*    */   private ContractConfigDao dao;
/*    */ 
/*    */   public ContractConfigServiceImpl(ContractConfigDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.impl.ContractConfigServiceImpl
 * JD-Core Version:    0.6.0
 */
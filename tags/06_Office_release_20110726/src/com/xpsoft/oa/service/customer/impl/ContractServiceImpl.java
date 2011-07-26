/*    */ package com.xpsoft.oa.service.customer.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.customer.ContractDao;
/*    */ import com.xpsoft.oa.model.customer.Contract;
/*    */ import com.xpsoft.oa.service.customer.ContractService;
/*    */ 
/*    */ public class ContractServiceImpl extends BaseServiceImpl<Contract>
/*    */   implements ContractService
/*    */ {
/*    */   private ContractDao dao;
/*    */ 
/*    */   public ContractServiceImpl(ContractDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.impl.ContractServiceImpl
 * JD-Core Version:    0.6.0
 */
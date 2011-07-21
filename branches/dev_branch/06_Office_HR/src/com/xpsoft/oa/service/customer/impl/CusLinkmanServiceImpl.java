/*    */ package com.xpsoft.oa.service.customer.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.customer.CusLinkmanDao;
/*    */ import com.xpsoft.oa.model.customer.CusLinkman;
/*    */ import com.xpsoft.oa.service.customer.CusLinkmanService;
/*    */ 
/*    */ public class CusLinkmanServiceImpl extends BaseServiceImpl<CusLinkman>
/*    */   implements CusLinkmanService
/*    */ {
/*    */   private CusLinkmanDao dao;
/*    */ 
/*    */   public CusLinkmanServiceImpl(CusLinkmanDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public boolean checkMainCusLinkman(Long customerId, Long linkmanId)
/*    */   {
/* 21 */     return this.dao.checkMainCusLinkman(customerId, linkmanId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.impl.CusLinkmanServiceImpl
 * JD-Core Version:    0.6.0
 */
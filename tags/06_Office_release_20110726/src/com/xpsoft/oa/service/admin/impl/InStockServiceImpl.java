/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.InStockDao;
/*    */ import com.xpsoft.oa.model.admin.InStock;
/*    */ import com.xpsoft.oa.service.admin.InStockService;
/*    */ 
/*    */ public class InStockServiceImpl extends BaseServiceImpl<InStock>
/*    */   implements InStockService
/*    */ {
/*    */   private InStockDao dao;
/*    */ 
/*    */   public InStockServiceImpl(InStockDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Integer findInCountByBuyId(Long buyId)
/*    */   {
/* 21 */     return this.dao.findInCountByBuyId(buyId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.InStockServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.admin.CartRepairDao;
/*    */ import com.htsoft.oa.model.admin.CartRepair;
/*    */ import com.htsoft.oa.service.admin.CartRepairService;
/*    */ 
/*    */ public class CartRepairServiceImpl extends BaseServiceImpl<CartRepair>
/*    */   implements CartRepairService
/*    */ {
/*    */   private CartRepairDao dao;
/*    */ 
/*    */   public CartRepairServiceImpl(CartRepairDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.admin.impl.CartRepairServiceImpl
 * JD-Core Version:    0.6.0
 */
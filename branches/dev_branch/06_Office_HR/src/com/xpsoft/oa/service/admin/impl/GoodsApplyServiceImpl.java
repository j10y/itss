/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.GoodsApplyDao;
/*    */ import com.xpsoft.oa.model.admin.GoodsApply;
/*    */ import com.xpsoft.oa.service.admin.GoodsApplyService;
/*    */ 
/*    */ public class GoodsApplyServiceImpl extends BaseServiceImpl<GoodsApply>
/*    */   implements GoodsApplyService
/*    */ {
/*    */   private GoodsApplyDao dao;
/*    */ 
/*    */   public GoodsApplyServiceImpl(GoodsApplyDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.GoodsApplyServiceImpl
 * JD-Core Version:    0.6.0
 */
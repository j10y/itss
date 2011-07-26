/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.CarApplyDao;
/*    */ import com.xpsoft.oa.model.admin.CarApply;
/*    */ import com.xpsoft.oa.service.admin.CarApplyService;
/*    */ 
/*    */ public class CarApplyServiceImpl extends BaseServiceImpl<CarApply>
/*    */   implements CarApplyService
/*    */ {
/*    */   private CarApplyDao dao;
/*    */ 
/*    */   public CarApplyServiceImpl(CarApplyDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.CarApplyServiceImpl
 * JD-Core Version:    0.6.0
 */
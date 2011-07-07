/*    */ package com.htsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.admin.CarApplyDao;
/*    */ import com.htsoft.oa.model.admin.CarApply;
/*    */ import com.htsoft.oa.service.admin.CarApplyService;
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
 * Qualified Name:     com.htsoft.oa.service.admin.impl.CarApplyServiceImpl
 * JD-Core Version:    0.6.0
 */
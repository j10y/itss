/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.CarDao;
/*    */ import com.xpsoft.oa.model.admin.Car;
/*    */ import com.xpsoft.oa.service.admin.CarService;
/*    */ 
/*    */ public class CarServiceImpl extends BaseServiceImpl<Car>
/*    */   implements CarService
/*    */ {
/*    */   private CarDao dao;
/*    */ 
/*    */   public CarServiceImpl(CarDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.CarServiceImpl
 * JD-Core Version:    0.6.0
 */
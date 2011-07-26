/*    */ package com.xpsoft.oa.service.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.task.PlanTypeDao;
/*    */ import com.xpsoft.oa.model.task.PlanType;
/*    */ import com.xpsoft.oa.service.task.PlanTypeService;
/*    */ 
/*    */ public class PlanTypeServiceImpl extends BaseServiceImpl<PlanType>
/*    */   implements PlanTypeService
/*    */ {
/*    */   private PlanTypeDao dao;
/*    */ 
/*    */   public PlanTypeServiceImpl(PlanTypeDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.task.impl.PlanTypeServiceImpl
 * JD-Core Version:    0.6.0
 */
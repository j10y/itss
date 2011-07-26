/*    */ package com.xpsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.hrm.SalaryPayoffDao;
/*    */ import com.xpsoft.oa.model.hrm.SalaryPayoff;
/*    */ import com.xpsoft.oa.service.hrm.SalaryPayoffService;
/*    */ 
/*    */ public class SalaryPayoffServiceImpl extends BaseServiceImpl<SalaryPayoff>
/*    */   implements SalaryPayoffService
/*    */ {
/*    */   private SalaryPayoffDao dao;
/*    */ 
/*    */   public SalaryPayoffServiceImpl(SalaryPayoffDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.impl.SalaryPayoffServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.hrm.StandSalaryDao;
/*    */ import com.htsoft.oa.model.hrm.StandSalary;
/*    */ import com.htsoft.oa.service.hrm.StandSalaryService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StandSalaryServiceImpl extends BaseServiceImpl<StandSalary>
/*    */   implements StandSalaryService
/*    */ {
/*    */   private StandSalaryDao dao;
/*    */ 
/*    */   public StandSalaryServiceImpl(StandSalaryDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public boolean checkStandNo(String standardNo)
/*    */   {
/* 23 */     return this.dao.checkStandNo(standardNo);
/*    */   }
/*    */ 
/*    */   public List<StandSalary> findByPassCheck()
/*    */   {
/* 28 */     return this.dao.findByPassCheck();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.hrm.impl.StandSalaryServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.hrm.StandSalaryItemDao;
/*    */ import com.htsoft.oa.model.hrm.StandSalaryItem;
/*    */ import com.htsoft.oa.service.hrm.StandSalaryItemService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StandSalaryItemServiceImpl extends BaseServiceImpl<StandSalaryItem>
/*    */   implements StandSalaryItemService
/*    */ {
/*    */   private StandSalaryItemDao dao;
/*    */ 
/*    */   public StandSalaryItemServiceImpl(StandSalaryItemDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<StandSalaryItem> getAllByStandardId(Long standardId)
/*    */   {
/* 23 */     return this.dao.getAllByStandardId(standardId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.hrm.impl.StandSalaryItemServiceImpl
 * JD-Core Version:    0.6.0
 */
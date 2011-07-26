/*    */ package com.xpsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.hrm.StandSalaryItemDao;
/*    */ import com.xpsoft.oa.model.hrm.StandSalaryItem;
/*    */ import com.xpsoft.oa.service.hrm.StandSalaryItemService;
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
 * Qualified Name:     com.xpsoft.oa.service.hrm.impl.StandSalaryItemServiceImpl
 * JD-Core Version:    0.6.0
 */
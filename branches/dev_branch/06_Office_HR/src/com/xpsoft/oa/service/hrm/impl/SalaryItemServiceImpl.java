/*    */ package com.xpsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.hrm.SalaryItemDao;
/*    */ import com.xpsoft.oa.model.hrm.SalaryItem;
/*    */ import com.xpsoft.oa.service.hrm.SalaryItemService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SalaryItemServiceImpl extends BaseServiceImpl<SalaryItem>
/*    */   implements SalaryItemService
/*    */ {
/*    */   private SalaryItemDao dao;
/*    */ 
/*    */   public SalaryItemServiceImpl(SalaryItemDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<SalaryItem> getAllExcludeId(String excludeIds, PagingBean pb)
/*    */   {
/* 24 */     return this.dao.getAllExcludeId(excludeIds, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.impl.SalaryItemServiceImpl
 * JD-Core Version:    0.6.0
 */
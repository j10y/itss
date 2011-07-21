/*    */ package com.xpsoft.oa.service.personal.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.personal.DutySectionDao;
/*    */ import com.xpsoft.oa.model.personal.DutySection;
/*    */ import com.xpsoft.oa.service.personal.DutySectionService;
/*    */ 
/*    */ public class DutySectionServiceImpl extends BaseServiceImpl<DutySection>
/*    */   implements DutySectionService
/*    */ {
/*    */   private DutySectionDao dao;
/*    */ 
/*    */   public DutySectionServiceImpl(DutySectionDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.personal.impl.DutySectionServiceImpl
 * JD-Core Version:    0.6.0
 */
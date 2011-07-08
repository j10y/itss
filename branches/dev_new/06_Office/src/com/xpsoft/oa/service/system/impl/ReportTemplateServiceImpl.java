/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.ReportTemplateDao;
/*    */ import com.xpsoft.oa.model.system.ReportTemplate;
/*    */ import com.xpsoft.oa.service.system.ReportTemplateService;
/*    */ 
/*    */ public class ReportTemplateServiceImpl extends BaseServiceImpl<ReportTemplate>
/*    */   implements ReportTemplateService
/*    */ {
/*    */   private ReportTemplateDao dao;
/*    */ 
/*    */   public ReportTemplateServiceImpl(ReportTemplateDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.ReportTemplateServiceImpl
 * JD-Core Version:    0.6.0
 */
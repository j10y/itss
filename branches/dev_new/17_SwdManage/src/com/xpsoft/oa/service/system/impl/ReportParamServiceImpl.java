/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.ReportParamDao;
/*    */ import com.xpsoft.oa.model.system.ReportParam;
/*    */ import com.xpsoft.oa.service.system.ReportParamService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReportParamServiceImpl extends BaseServiceImpl<ReportParam>
/*    */   implements ReportParamService
/*    */ {
/*    */   private ReportParamDao dao;
/*    */ 
/*    */   public ReportParamServiceImpl(ReportParamDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<ReportParam> findByRepTemp(Long reportId)
/*    */   {
/* 23 */     return this.dao.findByRepTemp(reportId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.ReportParamServiceImpl
 * JD-Core Version:    0.6.0
 */
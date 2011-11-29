/*    */ package com.xpsoft.oa.service.flow.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.flow.ProcessFormDao;
/*    */ import com.xpsoft.oa.model.flow.ProcessForm;
/*    */ import com.xpsoft.oa.service.flow.ProcessFormService;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ProcessFormServiceImpl extends BaseServiceImpl<ProcessForm>
/*    */   implements ProcessFormService
/*    */ {
/*    */   private ProcessFormDao dao;
/*    */ 
/*    */   public ProcessFormServiceImpl(ProcessFormDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List getByRunId(Long runId)
/*    */   {
/* 28 */     return this.dao.getByRunId(runId);
/*    */   }
/*    */ 
/*    */   public ProcessForm getByRunIdActivityName(Long runId, String activityName)
/*    */   {
/* 38 */     return this.dao.getByRunIdActivityName(runId, activityName);
/*    */   }
/*    */ 
/*    */   public Long getActvityExeTimes(Long runId, String activityName)
/*    */   {
/* 46 */     return this.dao.getActvityExeTimes(runId, activityName);
/*    */   }
/*    */ 
/*    */   public Map getVariables(Long runId)
/*    */   {
/* 55 */     return this.dao.getVariables(runId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.flow.impl.ProcessFormServiceImpl
 * JD-Core Version:    0.6.0
 */
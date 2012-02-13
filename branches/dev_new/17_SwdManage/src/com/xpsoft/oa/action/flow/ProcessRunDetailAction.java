/*    */ package com.xpsoft.oa.action.flow;
/*    */ 
/*    */ import com.xpsoft.core.web.action.BaseAction;
/*    */ import com.xpsoft.oa.model.flow.ProcessRun;
/*    */ import com.xpsoft.oa.service.flow.JbpmService;
/*    */ import com.xpsoft.oa.service.flow.ProcessFormService;
/*    */ import com.xpsoft.oa.service.flow.ProcessRunService;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.jbpm.api.ProcessInstance;
/*    */ 
/*    */ public class ProcessRunDetailAction extends BaseAction
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProcessRunService processRunService;
/*    */ 
/*    */   @Resource
/*    */   private ProcessFormService processFormService;
/*    */ 
/*    */   @Resource
/*    */   private JbpmService jbpmService;
/*    */   private Long runId;
/*    */   private Long taskId;
/*    */ 
/*    */   public Long getRunId()
/*    */   {
/* 35 */     return this.runId;
/*    */   }
/*    */ 
/*    */   public void setRunId(Long runId) {
/* 39 */     this.runId = runId;
/*    */   }
/*    */ 
/*    */   public Long getTaskId()
/*    */   {
/* 45 */     return this.taskId;
/*    */   }
/*    */ 
/*    */   public void setTaskId(Long taskId) {
/* 49 */     this.taskId = taskId;
/*    */   }
/*    */ 
/*    */   public String execute() throws Exception
/*    */   {
/* 54 */     ProcessRun processRun = null;
/* 55 */     if (this.runId == null) {
/* 56 */       ProcessInstance pis = this.jbpmService.getProcessInstanceByTaskId(this.taskId.toString());
/* 57 */       processRun = this.processRunService.getByPiId(pis.getId());
/* 58 */       getRequest().setAttribute("processRun", processRun);
/* 59 */       this.runId = processRun.getRunId();
/*    */     } else {
/* 61 */       processRun = (ProcessRun)this.processRunService.get(this.runId);
/*    */     }
/* 63 */     List pfList = this.processFormService.getByRunId(this.runId);
/*    */ 
/* 65 */     getRequest().setAttribute("pfList", pfList);
/*    */ 
/* 67 */     return "success";
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.flow.ProcessRunDetailAction
 * JD-Core Version:    0.6.0
 */
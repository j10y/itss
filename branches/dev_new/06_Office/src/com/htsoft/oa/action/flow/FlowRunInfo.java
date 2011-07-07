/*     */ package com.htsoft.oa.action.flow;
/*     */ 
/*     */ import com.htsoft.core.jbpm.pv.ParamField;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class FlowRunInfo
/*     */ {
/*  18 */   private Map variables = new HashMap();
/*     */ 
/*  23 */   private Map<String, ParamField> paramFields = new HashMap();
/*     */ 
/*  28 */   private boolean isStartFlow = false;
/*     */   private HttpServletRequest request;
/*  38 */   private String processName = "通用";
/*     */ 
/*  44 */   private String activityName = "开始";
/*     */ 
/*  49 */   private String destName = null;
/*     */   private String transitionName;
/*     */   private String taskId;
/*     */   private String piId;
/*     */ 
/*     */   public FlowRunInfo(HttpServletRequest req)
/*     */   {
/*  71 */     if ("true".equals(req.getParameter("startFlow"))) {
/*  72 */       this.isStartFlow = true;
/*     */     }
/*     */     else {
/*  75 */       String signUserIds = req.getParameter("signUserIds");
/*  76 */       if (StringUtils.isNotEmpty(signUserIds)) {
/*  77 */         this.variables.put("signUserIds", signUserIds);
/*     */       }
/*     */ 
/*  80 */       String flowAssignId = req.getParameter("flowAssignId");
/*  81 */       if (StringUtils.isNotEmpty(flowAssignId)) {
/*  82 */         this.variables.put("flowAssignId", flowAssignId);
/*     */       }
/*     */ 
/*  85 */       String signUserId = req.getParameter("");
/*     */ 
/*  88 */       String pTaskId = req.getParameter("taskId");
/*  89 */       if (StringUtils.isNotEmpty(pTaskId)) {
/*  90 */         this.taskId = pTaskId;
/*     */       }
/*     */ 
/*  94 */       String pPiId = req.getParameter("piId");
/*     */ 
/*  96 */       if (StringUtils.isNotEmpty(pPiId)) {
/*  97 */         this.piId = pPiId;
/*     */       }
/*     */ 
/* 101 */       String pActivityName = req.getParameter("activityName");
/* 102 */       if (StringUtils.isNotEmpty(pActivityName)) {
/* 103 */         this.activityName = pActivityName;
/*     */       }
/*     */ 
/* 106 */       String pDestName = req.getParameter("destName");
/*     */ 
/* 108 */       if (StringUtils.isNotEmpty(pDestName)) {
/* 109 */         this.destName = pDestName;
/*     */       }
/*     */ 
/* 113 */       String pSignName = req.getParameter("signalName");
/* 114 */       if (StringUtils.isNotEmpty(pSignName))
/* 115 */         this.transitionName = pSignName;
/*     */     }
/*     */   }
/*     */ 
/*     */   public FlowRunInfo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Map getVariables()
/*     */   {
/* 126 */     return this.variables;
/*     */   }
/*     */ 
/*     */   public void setVariables(Map variables) {
/* 130 */     this.variables = variables;
/*     */   }
/*     */ 
/*     */   public boolean isStartFlow() {
/* 134 */     return this.isStartFlow;
/*     */   }
/*     */ 
/*     */   public void setStartFlow(boolean isStartFlow) {
/* 138 */     this.isStartFlow = isStartFlow;
/*     */   }
/*     */ 
/*     */   public HttpServletRequest getRequest() {
/* 142 */     return this.request;
/*     */   }
/*     */ 
/*     */   public void setRequest(HttpServletRequest request) {
/* 146 */     this.request = request;
/*     */   }
/*     */ 
/*     */   public String getProcessName() {
/* 150 */     return this.processName;
/*     */   }
/*     */ 
/*     */   public void setProcessName(String processName) {
/* 154 */     this.processName = processName;
/*     */   }
/*     */ 
/*     */   public String getActivityName() {
/* 158 */     return this.activityName;
/*     */   }
/*     */ 
/*     */   public void setActivityName(String activityName) {
/* 162 */     this.activityName = activityName;
/*     */   }
/*     */ 
/*     */   public Map<String, ParamField> getParamFields() {
/* 166 */     return this.paramFields;
/*     */   }
/*     */ 
/*     */   public void setParamFields(Map<String, ParamField> paramFields) {
/* 170 */     this.paramFields = paramFields;
/*     */   }
/*     */ 
/*     */   public String getTransitionName() {
/* 174 */     return this.transitionName;
/*     */   }
/*     */ 
/*     */   public void setTransitionName(String transitionName) {
/* 178 */     this.transitionName = transitionName;
/*     */   }
/*     */ 
/*     */   public String getTaskId() {
/* 182 */     return this.taskId;
/*     */   }
/*     */ 
/*     */   public void setTaskId(String taskId) {
/* 186 */     this.taskId = taskId;
/*     */   }
/*     */ 
/*     */   public String getPiId() {
/* 190 */     return this.piId;
/*     */   }
/*     */ 
/*     */   public void setPiId(String piId) {
/* 194 */     this.piId = piId;
/*     */   }
/*     */ 
/*     */   public String getDestName() {
/* 198 */     return this.destName;
/*     */   }
/*     */ 
/*     */   public void setDestName(String destName) {
/* 202 */     this.destName = destName;
/*     */   }
/*     */ 
/*     */   public void setdAssignId(String assignId)
/*     */   {
/* 210 */     this.variables.put("flowAssignId", assignId);
/*     */   }
/*     */ 
/*     */   public void setMultipleTask(String userIds)
/*     */   {
/* 218 */     this.variables.put("signUserIds", userIds);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.flow.FlowRunInfo
 * JD-Core Version:    0.6.0
 */
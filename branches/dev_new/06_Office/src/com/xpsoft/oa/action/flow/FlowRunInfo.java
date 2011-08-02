 package com.xpsoft.oa.action.flow;
 
 import com.xpsoft.core.jbpm.pv.ParamField;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.lang.StringUtils;
 
 public class FlowRunInfo
 {
   private Map variables = new HashMap();
 
   private Map<String, ParamField> paramFields = new HashMap();
 
   private boolean isStartFlow = false;
   private HttpServletRequest request;
   private String processName = "通用";
 
   private String activityName = "开始";
 
   private String destName = null;
   private String transitionName;
   private String taskId;
   private String piId;
 
   public FlowRunInfo(HttpServletRequest req)
   {
     if ("true".equals(req.getParameter("startFlow"))) {
       this.isStartFlow = true;
				String flowAssignId = req.getParameter("flowAssignId");
			    if (StringUtils.isNotEmpty(flowAssignId)) {
			         this.variables.put("flowAssignId", flowAssignId);
			    }
			 }
     else {
       String signUserIds = req.getParameter("signUserIds");
       if (StringUtils.isNotEmpty(signUserIds)) {
         this.variables.put("signUserIds", signUserIds);
       }
 
       String flowAssignId = req.getParameter("flowAssignId");
       if (StringUtils.isNotEmpty(flowAssignId)) {
         this.variables.put("flowAssignId", flowAssignId);
       }

 
       String pTaskId = req.getParameter("taskId");
       if (StringUtils.isNotEmpty(pTaskId)) {
         this.taskId = pTaskId;
       }
 
       String pPiId = req.getParameter("piId");
 
       if (StringUtils.isNotEmpty(pPiId)) {
         this.piId = pPiId;
       }
 
       String pActivityName = req.getParameter("activityName");
       if (StringUtils.isNotEmpty(pActivityName)) {
         this.activityName = pActivityName;
       }
 
       String pDestName = req.getParameter("destName");
 
       if (StringUtils.isNotEmpty(pDestName)) {
         this.destName = pDestName;
       }
 
       String pSignName = req.getParameter("signalName");
       if (StringUtils.isNotEmpty(pSignName))
         this.transitionName = pSignName;
     }
   }
   public FlowRunInfo(Map parmap)
   {
     if ("true".equals(parmap.get("startFlow")+"")) {
       this.isStartFlow = true;
				String flowAssignId = parmap.get("flowAssignId")+"";
			    if (StringUtils.isNotEmpty(flowAssignId)) {
			         this.variables.put("flowAssignId", flowAssignId);
			    }
			 }
     else {
       String signUserIds = parmap.get("signUserIds")+"";
       if (StringUtils.isNotEmpty(signUserIds)) {
         this.variables.put("signUserIds", signUserIds);
       }
 
       String flowAssignId = parmap.get("flowAssignId")+"";
       if (StringUtils.isNotEmpty(flowAssignId)) {
         this.variables.put("flowAssignId", flowAssignId);
       }

 
       String pTaskId = parmap.get("taskId")+"";
       if (StringUtils.isNotEmpty(pTaskId)) {
         this.taskId = pTaskId;
       }
 
       String pPiId = parmap.get("piId")+"";
 
       if (StringUtils.isNotEmpty(pPiId)) {
         this.piId = pPiId;
       }
 
       String pActivityName = parmap.get("activityName")+"";
       if (StringUtils.isNotEmpty(pActivityName)) {
         this.activityName = pActivityName;
       }
 
       String pDestName = parmap.get("destName")+"";
 
       if (StringUtils.isNotEmpty(pDestName)) {
         this.destName = pDestName;
       }
 
       String pSignName = parmap.get("signalName")+"";
       if (StringUtils.isNotEmpty(pSignName))
         this.transitionName = pSignName;
     }
   }
   public FlowRunInfo()
   {
   }
 
   public Map getVariables()
   {
/* 126 */     return this.variables;
   }
 
   public void setVariables(Map variables) {
/* 130 */     this.variables = variables;
   }
 
   public boolean isStartFlow() {
/* 134 */     return this.isStartFlow;
   }
 
   public void setStartFlow(boolean isStartFlow) {
/* 138 */     this.isStartFlow = isStartFlow;
   }
 
   public HttpServletRequest getRequest() {
/* 142 */     return this.request;
   }
 
   public void setRequest(HttpServletRequest request) {
/* 146 */     this.request = request;
   }
 
   public String getProcessName() {
/* 150 */     return this.processName;
   }
 
   public void setProcessName(String processName) {
/* 154 */     this.processName = processName;
   }
 
   public String getActivityName() {
/* 158 */     return this.activityName;
   }
 
   public void setActivityName(String activityName) {
/* 162 */     this.activityName = activityName;
   }
 
   public Map<String, ParamField> getParamFields() {
/* 166 */     return this.paramFields;
   }
 
   public void setParamFields(Map<String, ParamField> paramFields) {
/* 170 */     this.paramFields = paramFields;
   }
 
   public String getTransitionName() {
/* 174 */     return this.transitionName;
   }
 
   public void setTransitionName(String transitionName) {
/* 178 */     this.transitionName = transitionName;
   }
 
   public String getTaskId() {
/* 182 */     return this.taskId;
   }
 
   public void setTaskId(String taskId) {
/* 186 */     this.taskId = taskId;
   }
 
   public String getPiId() {
/* 190 */     return this.piId;
   }
 
   public void setPiId(String piId) {
/* 194 */     this.piId = piId;
   }
 
   public String getDestName() {
/* 198 */     return this.destName;
   }
 
   public void setDestName(String destName) {
/* 202 */     this.destName = destName;
   }
 
   public void setdAssignId(String assignId)
   {
/* 210 */     this.variables.put("flowAssignId", assignId);
   }
 
   public void setMultipleTask(String userIds)
   {
/* 218 */     this.variables.put("signUserIds", userIds);
   }
 }

/*     */ package com.xpsoft.oa.action.flow;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.jbpm.jpdl.Node;
/*     */ import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.archive.ArchivesDoc;
/*     */ import com.xpsoft.oa.model.flow.ProDefinition;
/*     */ import com.xpsoft.oa.model.flow.ProUserAssign;
/*     */ import com.xpsoft.oa.service.flow.JbpmService;
/*     */ import com.xpsoft.oa.service.flow.ProDefinitionService;
/*     */ import com.xpsoft.oa.service.flow.ProUserAssignService;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ProUserAssignAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ProUserAssignService proUserAssignService;
/*     */ 
/*     */   @Resource
/*     */   private JbpmService jbpmService;
/*     */ 
/*     */   @Resource
/*     */   private ProDefinitionService proDefinitionService;
/*     */   private ProUserAssign proUserAssign;
/*     */   private Long assignId;
/*     */ 
/*     */   public void setJbpmService(JbpmService jbpmService)
/*     */   {
/*  35 */     this.jbpmService = jbpmService;
/*     */   }
/*     */ 
/*     */   public Long getAssignId()
/*     */   {
/*  42 */     return this.assignId;
/*     */   }
/*     */ 
/*     */   public void setAssignId(Long assignId) {
/*  46 */     this.assignId = assignId;
/*     */   }
/*     */ 
/*     */   public ProUserAssign getProUserAssign() {
/*  50 */     return this.proUserAssign;
/*     */   }
/*     */ 
/*     */   public void setProUserAssign(ProUserAssign proUserAssign) {
/*  54 */     this.proUserAssign = proUserAssign;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  62 */     String defId = getRequest().getParameter("defId");
/*     */ 
/*  64 */     ProDefinition proDefinition = (ProDefinition)this.proDefinitionService.get(new Long(defId));
/*     */ 
/*  66 */     List nodes = this.jbpmService.getTaskNodesByDefId(new Long(defId));
/*     */ 
/*  68 */     List nodesAssignList = this.proUserAssignService.getByDeployId(proDefinition.getDeployId());
/*     */ 
/*  70 */     StringBuffer buff = new StringBuffer("{result:[");
/*     */ 
/*  72 */     for (int i = 0; i < nodes.size(); i++) {
/*  73 */       String nodeName = ((Node)nodes.get(i)).getName();
/*  74 */       buff.append("{activityName:'").append(nodeName).append("',deployId:'" + proDefinition.getDeployId()).append("'");
/*  75 */       for (int j = 0; j < nodesAssignList.size(); j++) {
/*  76 */         ProUserAssign assign = (ProUserAssign)nodesAssignList.get(j);
/*  77 */         if (!nodeName.equals(assign.getActivityName())) continue;
/*  78 */         buff.append(",assignId:'").append(assign.getAssignId())
/*  79 */           .append("',userId:'").append(assign.getUserId())
/*  80 */           .append("',username:'").append(assign.getUsername())
/*  81 */           .append("',roleId:'").append(assign.getRoleId())
/*  82 */           .append("',roleName:'").append(assign.getRoleName())
/*  83 */           .append("'");
/*  84 */         break;
/*     */       }
/*     */ 
/*  87 */       buff.append("},");
/*     */     }
/*     */ 
/*  90 */     if (!nodes.isEmpty()) {
/*  91 */       buff.deleteCharAt(buff.length() - 1);
/*     */     }
/*     */ 
/*  94 */     buff.append("]}");
/*     */ 
/*  96 */     setJsonString(buff.toString());
/*     */ 
/*  98 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 106 */     ProUserAssign proUserAssign = (ProUserAssign)this.proUserAssignService.get(this.assignId);
/*     */ 
/* 108 */     Gson gson = new Gson();
/*     */ 
/* 110 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 111 */     sb.append(gson.toJson(proUserAssign));
/* 112 */     sb.append("}");
/* 113 */     setJsonString(sb.toString());
/*     */ 
/* 115 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 121 */     String data = getRequest().getParameter("data");
/*     */ 
/* 123 */     if (StringUtils.isNotEmpty(data)) {
/* 124 */       Gson gson = new Gson();
/* 125 */       ProUserAssign[] assigns = gson.fromJson(data, ProUserAssign[].class);
/* 126 */       for (ProUserAssign assign : assigns) {
/* 127 */         if (assign.getAssignId().longValue() == -1L) {
/* 128 */           assign.setAssignId(null);
/*     */         }
/* 130 */         this.proUserAssignService.save(assign);
/*     */       }
/*     */     }
/*     */ 
/* 134 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.flow.ProUserAssignAction
 * JD-Core Version:    0.6.0
 */
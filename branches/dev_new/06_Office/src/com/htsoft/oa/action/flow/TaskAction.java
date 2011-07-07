/*     */ package com.htsoft.oa.action.flow;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.info.ShortMessage;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.service.info.ShortMessageService;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.jbpm.api.task.Task;
/*     */ 
/*     */ public class TaskAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource(name="flowTaskService")
/*     */   private com.htsoft.oa.service.flow.TaskService flowTaskService;
/*     */ 
/*     */   @Resource
/*     */   private org.jbpm.api.TaskService taskService;
/*     */ 
/*     */   @Resource
/*     */   private ShortMessageService shortMessageService;
/*     */ 
/*     */   public String list()
/*     */   {
/*  45 */     PagingBean pb = new PagingBean(this.start.intValue(), this.limit.intValue());
/*  46 */     List tasks = this.flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(), pb);
/*     */ 
/*  48 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  49 */       .append(pb.getTotalItems()).append(",result:");
/*     */ 
/*  51 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/*  53 */     buff.append(gson.toJson(tasks));
/*     */ 
/*  55 */     buff.append("}");
/*     */ 
/*  57 */     setJsonString(buff.toString());
/*  58 */     return "success";
/*     */   }
/*     */ 
/*     */   public String change() {
/*  62 */     HttpServletRequest request = getRequest();
/*  63 */     String taskId = request.getParameter("taskId");
/*  64 */     String userId = request.getParameter("userId");
/*  65 */     String curUserId = ContextUtil.getCurrentUserId().toString();
/*  66 */     Task task = this.taskService.getTask(taskId);
/*  67 */     if ((task != null) && (curUserId.equals(task.getAssignee()))) {
/*  68 */       this.taskService.assignTask(taskId, userId);
/*  69 */       String msg = request.getParameter("msg");
/*  70 */       if (StringUtils.isNotEmpty(msg))
/*     */       {
/*  72 */         this.shortMessageService.save(AppUser.SYSTEM_USER, userId, msg, ShortMessage.MSG_TYPE_TASK);
/*     */       }
/*     */     }
/*  75 */     setJsonString("{success:true}");
/*  76 */     return "success";
/*     */   }
/*     */ 
/*     */   public String unlock()
/*     */   {
/*  84 */     String taskId = getRequest().getParameter("taskId");
/*  85 */     Task task = this.taskService.getTask(taskId);
/*     */ 
/*  87 */     String curUserId = ContextUtil.getCurrentUserId().toString();
/*     */ 
/*  89 */     if ((task != null) && (curUserId.equals(task.getAssignee()))) {
/*  90 */       this.taskService.assignTask(task.getId(), null);
/*  91 */       setJsonString("{success:true,unlocked:true}");
/*     */     } else {
/*  93 */       setJsonString("{success:true,unlocked:false}");
/*     */     }
/*     */ 
/*  96 */     return "success";
/*     */   }
/*     */ 
/*     */   public String lock()
/*     */   {
/* 105 */     String taskId = getRequest().getParameter("taskId");
/* 106 */     Task task = this.taskService.getTask(taskId);
/*     */ 
/* 108 */     if ((task != null) && (task.getAssignee() == null)) {
/* 109 */       this.taskService.assignTask(task.getId(), ContextUtil.getCurrentUserId().toString());
/* 110 */       setJsonString("{success:true,hasAssigned:false}");
/*     */     } else {
/* 112 */       setJsonString("{success:true,hasAssigned:true}");
/*     */     }
/*     */ 
/* 115 */     return "success";
/*     */   }
/*     */ 
/*     */   public String display()
/*     */   {
/* 120 */     PagingBean pb = new PagingBean(0, 8);
/* 121 */     List tasks = this.flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(), pb);
/* 122 */     getRequest().setAttribute("taskList", tasks);
/* 123 */     return "display";
/*     */   }
/*     */ 
/*     */   public String check()
/*     */   {
/* 132 */     String taskId = getRequest().getParameter("taskId");
/* 133 */     Task task = this.taskService.getTask(taskId);
/* 134 */     String cruUserId = ContextUtil.getCurrentUserId().toString();
/* 135 */     if ((task != null) && (task.getAssignee() != null) && (task.getAssignee().equals(cruUserId))) {
/* 136 */       setJsonString("{success:true}");
/* 137 */     } else if ((task != null) && (task.getAssignee() == null)) {
/* 138 */       this.taskService.assignTask(task.getId(), cruUserId);
/* 139 */       setJsonString("{success:true,assigned:true}");
/*     */     } else {
/* 141 */       setJsonString("{success:true,assigned:false}");
/*     */     }
/* 143 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.flow.TaskAction
 * JD-Core Version:    0.6.0
 */
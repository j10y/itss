/*     */ package com.htsoft.oa.action.customer;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.customer.Customer;
/*     */ import com.htsoft.oa.model.customer.Project;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import com.htsoft.oa.service.customer.ProjectService;
/*     */ import com.htsoft.oa.service.system.FileAttachService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ProjectAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ProjectService projectService;
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */   private Project project;
/*     */   private Long projectId;
/*     */   private String projectNo;
/*     */   private String projectAttachIDs;
/*     */ 
/*     */   public Long getProjectId()
/*     */   {
/*  43 */     return this.projectId;
/*     */   }
/*     */ 
/*     */   public void setProjectId(Long projectId) {
/*  47 */     this.projectId = projectId;
/*     */   }
/*     */ 
/*     */   public Project getProject() {
/*  51 */     return this.project;
/*     */   }
/*     */ 
/*     */   public void setProject(Project project) {
/*  55 */     this.project = project;
/*     */   }
/*     */ 
/*     */   public String getProjectNo() {
/*  59 */     return this.projectNo;
/*     */   }
/*     */ 
/*     */   public void setProjectNo(String projectNo) {
/*  63 */     this.projectNo = projectNo;
/*     */   }
/*     */ 
/*     */   public String getProjectAttachIDs() {
/*  67 */     return this.projectAttachIDs;
/*     */   }
/*     */ 
/*     */   public void setProjectAttachIDs(String projectAttachIDs) {
/*  71 */     this.projectAttachIDs = projectAttachIDs;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  79 */     QueryFilter filter = new QueryFilter(getRequest());
/*  80 */     List list = this.projectService.getAll(filter);
/*     */ 
/*  83 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  84 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  87 */     JSONSerializer json = new JSONSerializer();
/*  88 */     buff.append(json.exclude(new String[] { "class", "appUser.department", "contracts" }).serialize(list));
/*  89 */     buff.append("}");
/*     */ 
/*  91 */     this.jsonString = buff.toString();
/*     */ 
/*  93 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 101 */     String[] ids = getRequest().getParameterValues("ids");
/* 102 */     if (ids != null) {
/* 103 */       for (String id : ids) {
/* 104 */         this.projectService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/* 108 */     this.jsonString = "{success:true}";
/*     */ 
/* 110 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 118 */     Project project = (Project)this.projectService.get(this.projectId);
/*     */ 
/* 120 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/* 123 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 124 */     sb.append(gson.toJson(project));
/* 125 */     sb.append(",userId:'" + project.getAppUser().getUserId() + "'");
/* 126 */     sb.append(",salesman:'" + project.getAppUser().getFullname() + "'");
/* 127 */     sb.append(",customerName:'" + project.getCustomer().getCustomerName() + "'");
/* 128 */     sb.append(",customerId:'" + project.getCustomerId() + "'");
/* 129 */     sb.append("}");
/* 130 */     setJsonString(sb.toString());
/*     */ 
/* 132 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 138 */     boolean pass = false;
/* 139 */     StringBuffer buff = new StringBuffer("{");
/* 140 */     if (this.projectId != null) {
/* 141 */       if (this.projectService.checkProjectNo(this.project.getProjectNo()))
/* 142 */         pass = true;
/* 143 */       else buff.append("msg:'项目号已存在,请重新填写!',"); 
/*     */     }
/*     */     else {
/* 145 */       pass = true;
/*     */     }
/*     */ 
/* 148 */     if (pass)
/*     */     {
/* 150 */       String[] fileIDs = getProjectAttachIDs().split(",");
/* 151 */       Set projectAttachs = new HashSet();
/* 152 */       for (String id : fileIDs) {
/* 153 */         if (!id.equals("")) {
/* 154 */           projectAttachs.add((FileAttach)this.fileAttachService.get(new Long(id)));
/*     */         }
/*     */       }
/* 157 */       this.project.setProjectFiles(projectAttachs);
/*     */ 
/* 159 */       this.projectService.save(this.project);
/* 160 */       buff.append("success:true,projectId:" + this.project.getProjectId() + "}");
/*     */     } else {
/* 162 */       buff.append("failure:true}");
/*     */     }
/* 164 */     setJsonString(buff.toString());
/* 165 */     return "success";
/*     */   }
/*     */ 
/*     */   public String number()
/*     */   {
/* 173 */     SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
/* 174 */     String projectNo = date.format(new Date());
/* 175 */     setJsonString("{success:true,projectNo:'" + projectNo + "'}");
/* 176 */     return "success";
/*     */   }
/*     */ 
/*     */   public String check()
/*     */   {
/* 184 */     boolean pass = false;
/* 185 */     pass = this.projectService.checkProjectNo(this.projectNo);
/* 186 */     if (pass)
/* 187 */       setJsonString("{success:true,pass:true}");
/*     */     else {
/* 189 */       setJsonString("{success:true,pass:false}");
/*     */     }
/* 191 */     return "success";
/*     */   }
/*     */ 
/*     */   public String removeFile()
/*     */   {
/* 199 */     setProject((Project)this.projectService.get(this.projectId));
/* 200 */     Set projectFiles = this.project.getProjectFiles();
/* 201 */     FileAttach removeFile = (FileAttach)this.fileAttachService.get(new Long(this.projectAttachIDs));
/* 202 */     projectFiles.remove(removeFile);
/* 203 */     this.project.setProjectFiles(projectFiles);
/* 204 */     this.projectService.save(this.project);
/* 205 */     setJsonString("{success:true}");
/* 206 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.customer.ProjectAction
 * JD-Core Version:    0.6.0
 */
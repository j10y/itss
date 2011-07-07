/*     */ package com.htsoft.oa.action.hrm;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.hrm.Resume;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import com.htsoft.oa.service.hrm.ResumeService;
/*     */ import com.htsoft.oa.service.system.FileAttachService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ResumeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ResumeService resumeService;
/*     */   private Resume resume;
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */   private Long resumeId;
/*     */ 
/*     */   public Long getResumeId()
/*     */   {
/*  42 */     return this.resumeId;
/*     */   }
/*     */ 
/*     */   public void setResumeId(Long resumeId) {
/*  46 */     this.resumeId = resumeId;
/*     */   }
/*     */ 
/*     */   public Resume getResume() {
/*  50 */     return this.resume;
/*     */   }
/*     */ 
/*     */   public void setResume(Resume resume) {
/*  54 */     this.resume = resume;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  62 */     QueryFilter filter = new QueryFilter(getRequest());
/*  63 */     List<Resume> list = this.resumeService.getAll(filter);
/*     */ 
/*  65 */     Type type = new TypeToken<List<Resume>>() {}.getType();
/*  66 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  67 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  69 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*  70 */     buff.append(gson.toJson(list, type));
/*  71 */     buff.append("}");
/*     */ 
/*  73 */     this.jsonString = buff.toString();
/*     */ 
/*  75 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  83 */     String[] ids = getRequest().getParameterValues("ids");
/*  84 */     if (ids != null) {
/*  85 */       for (String id : ids) {
/*  86 */         this.resumeService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  90 */     this.jsonString = "{success:true}";
/*     */ 
/*  92 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 100 */     Resume resume = (Resume)this.resumeService.get(this.resumeId);
/*     */ 
/* 102 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/* 104 */     StringBuffer sb = new StringBuffer("{success:true,data:[");
/* 105 */     sb.append(gson.toJson(resume));
/* 106 */     sb.append("]}");
/* 107 */     setJsonString(sb.toString());
/*     */ 
/* 109 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 115 */     String fileIds = getRequest().getParameter("fileIds");
/* 116 */     if (this.resume.getResumeId() == null) {
/* 117 */       AppUser appUser = ContextUtil.getCurrentUser();
/* 118 */       this.resume.setRegistor(appUser.getFullname());
/* 119 */       this.resume.setRegTime(new Date());
/*     */     }
/* 121 */     if (StringUtils.isNotEmpty(fileIds)) {
/* 122 */       this.resume.getResumeFiles().clear();
/* 123 */       String[] ids = fileIds.split(",");
/* 124 */       for (int i = 0; i < ids.length; i++) {
/* 125 */         FileAttach fileAttach = (FileAttach)this.fileAttachService.get(new Long(ids[i]));
/* 126 */         this.resume.getResumeFiles().add(fileAttach);
/*     */       }
/*     */     }
/* 129 */     this.resumeService.save(this.resume);
/* 130 */     setJsonString("{success:true}");
/* 131 */     return "success";
/*     */   }
/*     */ 
/*     */   public String delphoto()
/*     */   {
/* 138 */     String strResumeId = getRequest().getParameter("resumeId");
/* 139 */     if (StringUtils.isNotEmpty(strResumeId)) {
/* 140 */       this.resume = ((Resume)this.resumeService.get(new Long(strResumeId)));
/* 141 */       this.resume.setPhoto("");
/* 142 */       this.resumeService.save(this.resume);
/* 143 */       setJsonString("{success:true}");
/*     */     }
/* 145 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.hrm.ResumeAction
 * JD-Core Version:    0.6.0
 */
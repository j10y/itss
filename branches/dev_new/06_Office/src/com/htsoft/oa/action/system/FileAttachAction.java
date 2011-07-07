/*     */ package com.htsoft.oa.action.system;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import com.htsoft.oa.service.system.FileAttachService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class FileAttachAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */   private FileAttach fileAttach;
/*     */   private Long fileId;
/*     */   private String filePath;
/*     */ 
/*     */   public Long getFileId()
/*     */   {
/*  34 */     return this.fileId;
/*     */   }
/*     */ 
/*     */   public void setFileId(Long fileId) {
/*  38 */     this.fileId = fileId;
/*     */   }
/*     */ 
/*     */   public FileAttach getFileAttach() {
/*  42 */     return this.fileAttach;
/*     */   }
/*     */ 
/*     */   public void setFileAttach(FileAttach fileAttach) {
/*  46 */     this.fileAttach = fileAttach;
/*     */   }
/*     */ 
/*     */   public String getFilePath() {
/*  50 */     return this.filePath;
/*     */   }
/*     */ 
/*     */   public void setFilePath(String filePath) {
/*  54 */     this.filePath = filePath;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  62 */     QueryFilter filter = new QueryFilter(getRequest());
/*  63 */     List<FileAttach> list = this.fileAttachService.getAll(filter);
/*     */ 
/*  65 */     Type type = new TypeToken<List<FileAttach>>() {  }.getType();
/*  66 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  67 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  69 */     Gson gson = new Gson();
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
/*  86 */         this.fileAttachService.remove(new Long(id));
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
/* 100 */     FileAttach fileAttach = (FileAttach)this.fileAttachService.get(this.fileId);
/* 101 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/* 103 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 104 */     sb.append(gson.toJson(fileAttach));
/* 105 */     sb.append("}");
/* 106 */     setJsonString(sb.toString());
/*     */ 
/* 108 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 114 */     this.fileAttachService.save(this.fileAttach);
/* 115 */     setJsonString("{success:true}");
/* 116 */     return "success";
/*     */   }
/*     */ 
/*     */   public String delete()
/*     */   {
/* 123 */     this.fileAttachService.removeByPath(this.filePath);
/* 124 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.system.FileAttachAction
 * JD-Core Version:    0.6.0
 */
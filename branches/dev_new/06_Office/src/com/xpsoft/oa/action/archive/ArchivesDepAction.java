/*     */ package com.xpsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.archive.ArchivesDep;
/*     */ import com.xpsoft.oa.service.archive.ArchivesDepService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ArchivesDepAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ArchivesDepService archivesDepService;
/*     */   private ArchivesDep archivesDep;
/*     */   private Long archDepId;
/*     */ 
/*     */   public Long getArchDepId()
/*     */   {
/*  37 */     return this.archDepId;
/*     */   }
/*     */ 
/*     */   public void setArchDepId(Long archDepId) {
/*  41 */     this.archDepId = archDepId;
/*     */   }
/*     */ 
/*     */   public ArchivesDep getArchivesDep() {
/*  45 */     return this.archivesDep;
/*     */   }
/*     */ 
/*     */   public void setArchivesDep(ArchivesDep archivesDep) {
/*  49 */     this.archivesDep = archivesDep;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  56 */     QueryFilter filter = new QueryFilter(getRequest());
/*  57 */     filter.addFilter("Q_signUserID_L_EQ", ContextUtil.getCurrentUserId().toString());
/*  58 */     List list = this.archivesDepService.getAll(filter);
/*     */ 
/*  64 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  65 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  66 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "archives.createtime" });
/*  67 */     buff.append(json.serialize(list));
/*     */ 
/*  70 */     buff.append("}");
/*     */ 
/*  72 */     this.jsonString = buff.toString();
/*     */ 
/*  74 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  82 */     String[] ids = getRequest().getParameterValues("ids");
/*  83 */     if (ids != null) {
/*  84 */       for (String id : ids) {
/*  85 */         this.archivesDepService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  89 */     this.jsonString = "{success:true}";
/*     */ 
/*  91 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  99 */     ArchivesDep archivesDep = (ArchivesDep)this.archivesDepService.get(this.archDepId);
/*     */ 
/* 101 */     Gson gson = new Gson();
/*     */ 
/* 103 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 104 */     sb.append(gson.toJson(archivesDep));
/* 105 */     sb.append("}");
/* 106 */     setJsonString(sb.toString());
/*     */ 
/* 108 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 114 */     this.archivesDepService.save(this.archivesDep);
/* 115 */     setJsonString("{success:true}");
/* 116 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.archive.ArchivesDepAction
 * JD-Core Version:    0.6.0
 */
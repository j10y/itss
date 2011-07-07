/*     */ package com.htsoft.oa.action.archive;
/*     */ 
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.JsonUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.archive.ArchTemplate;
/*     */ import com.htsoft.oa.service.archive.ArchTemplateService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ArchTemplateAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ArchTemplateService archTemplateService;
/*     */   private ArchTemplate archTemplate;
/*     */   private Long templateId;
/*     */ 
/*     */   public Long getTemplateId()
/*     */   {
/*  32 */     return this.templateId;
/*     */   }
/*     */ 
/*     */   public void setTemplateId(Long templateId) {
/*  36 */     this.templateId = templateId;
/*     */   }
/*     */ 
/*     */   public ArchTemplate getArchTemplate() {
/*  40 */     return this.archTemplate;
/*     */   }
/*     */ 
/*     */   public void setArchTemplate(ArchTemplate archTemplate) {
/*  44 */     this.archTemplate = archTemplate;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
/*  53 */     List list = this.archTemplateService.getAll(filter);
/*     */ 
/*  55 */     JSONSerializer jsonSerializer = JsonUtil.getJSONSerializer(new String[0]);
/*     */ 
/*  57 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  58 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  60 */     buff.append(jsonSerializer.serialize(list));
/*  61 */     buff.append("}");
/*     */ 
/*  63 */     this.jsonString = buff.toString();
/*     */ 
/*  65 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  73 */     String[] ids = getRequest().getParameterValues("ids");
/*  74 */     if (ids != null) {
/*  75 */       for (String id : ids) {
/*  76 */         this.archTemplateService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  80 */     this.jsonString = "{success:true}";
/*     */ 
/*  82 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  90 */     ArchTemplate archTemplate = (ArchTemplate)this.archTemplateService.get(this.templateId);
/*     */ 
/*  92 */     JSONSerializer jsonSerializer = JsonUtil.getJSONSerializer(new String[0]);
/*     */ 
/*  94 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  95 */     sb.append(jsonSerializer.serialize(archTemplate));
/*  96 */     sb.append("}");
/*  97 */     setJsonString(sb.toString());
/*     */ 
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 105 */     this.archTemplateService.save(this.archTemplate);
/* 106 */     setJsonString("{success:true}");
/* 107 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.archive.ArchTemplateAction
 * JD-Core Version:    0.6.0
 */
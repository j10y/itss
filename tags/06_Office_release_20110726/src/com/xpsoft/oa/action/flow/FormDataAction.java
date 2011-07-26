/*     */ package com.xpsoft.oa.action.flow;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.flow.FormData;
/*     */ import com.xpsoft.oa.service.flow.FormDataService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class FormDataAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private FormDataService formDataService;
/*     */   private FormData formData;
/*     */   private Long dataId;
/*     */ 
/*     */   public Long getDataId()
/*     */   {
/*  32 */     return this.dataId;
/*     */   }
/*     */ 
/*     */   public void setDataId(Long dataId) {
/*  36 */     this.dataId = dataId;
/*     */   }
/*     */ 
/*     */   public FormData getFormData() {
/*  40 */     return this.formData;
/*     */   }
/*     */ 
/*     */   public void setFormData(FormData formData) {
/*  44 */     this.formData = formData;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
/*  53 */     List<FormData> list = this.formDataService.getAll(filter);
/*     */ 
/*  55 */     Type type = new TypeToken<List<FormData>>() {  }.getType();
/*  56 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  57 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  59 */     Gson gson = new Gson();
/*  60 */     buff.append(gson.toJson(list, type));
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
/*  76 */         this.formDataService.remove(new Long(id));
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
/*  90 */     FormData formData = (FormData)this.formDataService.get(this.dataId);
/*     */ 
/*  92 */     Gson gson = new Gson();
/*     */ 
/*  94 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  95 */     sb.append(gson.toJson(formData));
/*  96 */     sb.append("}");
/*  97 */     setJsonString(sb.toString());
/*     */ 
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 105 */     this.formDataService.save(this.formData);
/* 106 */     setJsonString("{success:true}");
/* 107 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.flow.FormDataAction
 * JD-Core Version:    0.6.0
 */
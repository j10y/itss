/*     */ package com.htsoft.oa.action.system;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.system.FunUrl;
/*     */ import com.htsoft.oa.service.system.FunUrlService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class FunUrlAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private FunUrlService funUrlService;
/*     */   private FunUrl funUrl;
/*     */   private Long urlId;
/*     */ 
/*     */   public Long getUrlId()
/*     */   {
/*  32 */     return this.urlId;
/*     */   }
/*     */ 
/*     */   public void setUrlId(Long urlId) {
/*  36 */     this.urlId = urlId;
/*     */   }
/*     */ 
/*     */   public FunUrl getFunUrl() {
/*  40 */     return this.funUrl;
/*     */   }
/*     */ 
/*     */   public void setFunUrl(FunUrl funUrl) {
/*  44 */     this.funUrl = funUrl;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
/*  53 */     List<FunUrl> list = this.funUrlService.getAll(filter);
/*     */ 
/*  55 */     Type type = new TypeToken<List<FunUrl>>() {  }
/*  55 */     .getType();
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
/*  76 */         this.funUrlService.remove(new Long(id));
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
/*  90 */     FunUrl funUrl = (FunUrl)this.funUrlService.get(this.urlId);
/*     */ 
/*  92 */     Gson gson = new Gson();
/*     */ 
/*  94 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  95 */     sb.append(gson.toJson(funUrl));
/*  96 */     sb.append("}");
/*  97 */     setJsonString(sb.toString());
/*     */ 
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 105 */     this.funUrlService.save(this.funUrl);
/* 106 */     setJsonString("{success:true}");
/* 107 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.system.FunUrlAction
 * JD-Core Version:    0.6.0
 */
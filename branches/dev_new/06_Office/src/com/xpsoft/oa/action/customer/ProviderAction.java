/*     */ package com.xpsoft.oa.action.customer;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.customer.Provider;
/*     */ import com.xpsoft.oa.service.customer.ProviderService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ProviderAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ProviderService providerService;
/*     */   private Provider provider;
/*     */   private Long providerId;
/*     */ 
/*     */   public Long getProviderId()
/*     */   {
/*  34 */     return this.providerId;
/*     */   }
/*     */ 
/*     */   public void setProviderId(Long providerId) {
/*  38 */     this.providerId = providerId;
/*     */   }
/*     */ 
/*     */   public Provider getProvider() {
/*  42 */     return this.provider;
/*     */   }
/*     */ 
/*     */   public void setProvider(Provider provider) {
/*  46 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  54 */     QueryFilter filter = new QueryFilter(getRequest());
/*  55 */     List<Provider> list = this.providerService.getAll(filter);
/*     */ 
/*  57 */     Type type = new TypeToken<List<Provider>>() {  }
/*  57 */     .getType();
/*  58 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  59 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  61 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*  62 */     buff.append(gson.toJson(list, type));
/*  63 */     buff.append("}");
/*     */ 
/*  65 */     this.jsonString = buff.toString();
/*     */ 
/*  67 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  75 */     String[] ids = getRequest().getParameterValues("ids");
/*  76 */     if (ids != null) {
/*  77 */       for (String id : ids) {
/*  78 */         this.providerService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  82 */     this.jsonString = "{success:true}";
/*     */ 
/*  84 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  92 */     Provider provider = (Provider)this.providerService.get(this.providerId);
/*     */ 
/*  94 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*     */ 
/*  96 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  97 */     sb.append(gson.toJson(provider));
/*  98 */     sb.append("}");
/*  99 */     setJsonString(sb.toString());
/*     */ 
/* 101 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 107 */     this.providerService.save(this.provider);
/* 108 */     setJsonString("{success:true}");
/* 109 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.customer.ProviderAction
 * JD-Core Version:    0.6.0
 */
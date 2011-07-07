/*     */ package com.htsoft.oa.action.customer;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.customer.ContractConfig;
/*     */ import com.htsoft.oa.service.customer.ContractConfigService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ContractConfigAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ContractConfigService contractConfigService;
/*     */   private ContractConfig contractConfig;
/*     */   private Long configId;
/*     */ 
/*     */   public Long getConfigId()
/*     */   {
/*  33 */     return this.configId;
/*     */   }
/*     */ 
/*     */   public void setConfigId(Long configId) {
/*  37 */     this.configId = configId;
/*     */   }
/*     */ 
/*     */   public ContractConfig getContractConfig() {
/*  41 */     return this.contractConfig;
/*     */   }
/*     */ 
/*     */   public void setContractConfig(ContractConfig contractConfig) {
/*  45 */     this.contractConfig = contractConfig;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  53 */     QueryFilter filter = new QueryFilter(getRequest());
/*  54 */     List<ContractConfig> list = this.contractConfigService.getAll(filter);
/*     */ 
/*  56 */     Type type = new TypeToken<List<ContractConfig>>() {  }
/*  56 */     .getType();
/*  57 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  58 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  60 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*  61 */     buff.append(gson.toJson(list, type));
/*  62 */     buff.append("}");
/*     */ 
/*  64 */     this.jsonString = buff.toString();
/*     */ 
/*  66 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  74 */     String[] ids = getRequest().getParameterValues("ids");
/*  75 */     if (ids != null) {
/*  76 */       for (String id : ids) {
/*  77 */         this.contractConfigService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  81 */     this.jsonString = "{success:true}";
/*     */ 
/*  83 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  91 */     ContractConfig contractConfig = (ContractConfig)this.contractConfigService.get(this.configId);
/*     */ 
/*  93 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
/*     */ 
/*  95 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  96 */     sb.append(gson.toJson(contractConfig));
/*  97 */     sb.append("}");
/*  98 */     setJsonString(sb.toString());
/*     */ 
/* 100 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 106 */     this.contractConfigService.save(this.contractConfig);
/* 107 */     setJsonString("{success:true}");
/* 108 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.customer.ContractConfigAction
 * JD-Core Version:    0.6.0
 */
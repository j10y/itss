/*     */ package com.htsoft.oa.action.hrm;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.hrm.SalaryItem;
/*     */ import com.htsoft.oa.service.hrm.SalaryItemService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class SalaryItemAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private SalaryItemService salaryItemService;
/*     */   private SalaryItem salaryItem;
/*     */   private Long salaryItemId;
/*     */ 
/*     */   public Long getSalaryItemId()
/*     */   {
/*  35 */     return this.salaryItemId;
/*     */   }
/*     */ 
/*     */   public void setSalaryItemId(Long salaryItemId) {
/*  39 */     this.salaryItemId = salaryItemId;
/*     */   }
/*     */ 
/*     */   public SalaryItem getSalaryItem() {
/*  43 */     return this.salaryItem;
/*     */   }
/*     */ 
/*     */   public void setSalaryItem(SalaryItem salaryItem) {
/*  47 */     this.salaryItem = salaryItem;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  54 */     PagingBean pb = getInitPagingBean();
/*  55 */     String ids = getRequest().getParameter("exclude");
/*  56 */     if (StringUtils.isNotEmpty(ids)) {
/*  57 */       ids = ids.substring(0, ids.length() - 1);
/*     */     }
/*     */ 
/*  61 */     List<SalaryItem> list = this.salaryItemService.getAllExcludeId(ids, pb);
/*     */ 
/*  63 */     Type type = new TypeToken<List<SalaryItem>>() {  }
/*  63 */     .getType();
/*  64 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  65 */       .append(pb.getTotalItems()).append(",result:");
/*     */ 
/*  67 */     Gson gson = new Gson();
/*  68 */     buff.append(gson.toJson(list, type));
/*  69 */     buff.append("}");
/*     */ 
/*  71 */     this.jsonString = buff.toString();
/*     */ 
/*  73 */     return "success";
/*     */   }
/*     */ 
/*     */   public String search()
/*     */   {
/*  80 */     QueryFilter filter = new QueryFilter(getRequest());
/*  81 */     List<SalaryItem> list = this.salaryItemService.getAll(filter);
/*     */ 
/*  83 */     Type type = new TypeToken<List<SalaryItem>>() {  }
/*  83 */     .getType();
/*  84 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  85 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  87 */     Gson gson = new Gson();
/*  88 */     buff.append(gson.toJson(list, type));
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
/* 104 */         this.salaryItemService.remove(new Long(id));
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
/* 118 */     SalaryItem salaryItem = (SalaryItem)this.salaryItemService.get(this.salaryItemId);
/*     */ 
/* 120 */     Gson gson = new Gson();
/*     */ 
/* 122 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 123 */     sb.append(gson.toJson(salaryItem));
/* 124 */     sb.append("}");
/* 125 */     setJsonString(sb.toString());
/*     */ 
/* 127 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 133 */     this.salaryItemService.save(this.salaryItem);
/* 134 */     setJsonString("{success:true}");
/* 135 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.hrm.SalaryItemAction
 * JD-Core Version:    0.6.0
 */
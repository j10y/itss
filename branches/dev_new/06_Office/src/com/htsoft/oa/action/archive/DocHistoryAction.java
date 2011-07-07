/*     */ package com.htsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.JsonUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.archive.DocHistory;
/*     */ import com.htsoft.oa.service.archive.DocHistoryService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class DocHistoryAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DocHistoryService docHistoryService;
/*     */   private DocHistory docHistory;
/*     */   private Long historyId;
/*     */ 
/*     */   public Long getHistoryId()
/*     */   {
/*  35 */     return this.historyId;
/*     */   }
/*     */ 
/*     */   public void setHistoryId(Long historyId) {
/*  39 */     this.historyId = historyId;
/*     */   }
/*     */ 
/*     */   public DocHistory getDocHistory() {
/*  43 */     return this.docHistory;
/*     */   }
/*     */ 
/*     */   public void setDocHistory(DocHistory docHistory) {
/*  47 */     this.docHistory = docHistory;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  55 */     QueryFilter filter = new QueryFilter(getRequest());
/*  56 */     List list = this.docHistoryService.getAll(filter);
/*     */ 
/*  59 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  60 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  64 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "updatetime" });
/*  65 */     buff.append(json.serialize(list));
/*  66 */     buff.append("}");
/*     */ 
/*  68 */     this.jsonString = buff.toString();
/*     */ 
/*  70 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  78 */     String[] ids = getRequest().getParameterValues("ids");
/*  79 */     if (ids != null) {
/*  80 */       for (String id : ids) {
/*  81 */         this.docHistoryService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  85 */     this.jsonString = "{success:true}";
/*     */ 
/*  87 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  95 */     DocHistory docHistory = (DocHistory)this.docHistoryService.get(this.historyId);
/*     */ 
/*  97 */     Gson gson = new Gson();
/*     */ 
/*  99 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 100 */     sb.append(gson.toJson(docHistory));
/* 101 */     sb.append("}");
/* 102 */     setJsonString(sb.toString());
/*     */ 
/* 104 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 110 */     this.docHistoryService.save(this.docHistory);
/* 111 */     setJsonString("{success:true}");
/* 112 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.archive.DocHistoryAction
 * JD-Core Version:    0.6.0
 */
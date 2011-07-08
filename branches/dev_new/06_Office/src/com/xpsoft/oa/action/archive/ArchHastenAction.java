/*     */ package com.xpsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.archive.ArchHasten;
/*     */ import com.xpsoft.oa.service.archive.ArchHastenService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ArchHastenAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ArchHastenService archHastenService;
/*     */   private ArchHasten archHasten;
/*     */   private Long record;
/*     */ 
/*     */   public Long getRecord()
/*     */   {
/*  32 */     return this.record;
/*     */   }
/*     */ 
/*     */   public void setRecord(Long record) {
/*  36 */     this.record = record;
/*     */   }
/*     */ 
/*     */   public ArchHasten getArchHasten() {
/*  40 */     return this.archHasten;
/*     */   }
/*     */ 
/*     */   public void setArchHasten(ArchHasten archHasten) {
/*  44 */     this.archHasten = archHasten;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  52 */     QueryFilter filter = new QueryFilter(getRequest());
/*  53 */     List<ArchHasten> list = this.archHastenService.getAll(filter);
/*     */ 
/*  55 */     Type type = new TypeToken<List<ArchHasten>>() {  }
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
/*  76 */         this.archHastenService.remove(new Long(id));
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
/*  90 */     ArchHasten archHasten = (ArchHasten)this.archHastenService.get(this.record);
/*     */ 
/*  92 */     Gson gson = new Gson();
/*     */ 
/*  94 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  95 */     sb.append(gson.toJson(archHasten));
/*  96 */     sb.append("}");
/*  97 */     setJsonString(sb.toString());
/*     */ 
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 105 */     this.archHastenService.save(this.archHasten);
/* 106 */     setJsonString("{success:true}");
/* 107 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.archive.ArchHastenAction
 * JD-Core Version:    0.6.0
 */
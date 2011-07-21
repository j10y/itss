/*     */ package com.xpsoft.oa.action.hrm;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.oa.model.hrm.StandSalaryItem;
/*     */ import com.xpsoft.oa.service.hrm.StandSalaryItemService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class StandSalaryItemAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private StandSalaryItemService standSalaryItemService;
/*     */   private StandSalaryItem standSalaryItem;
/*     */   private Long itemId;
/*     */   private Long standardId;
/*     */ 
/*     */   public Long getStandardId()
/*     */   {
/*  35 */     return this.standardId;
/*     */   }
/*     */ 
/*     */   public void setStandardId(Long standardId) {
/*  39 */     this.standardId = standardId;
/*     */   }
/*     */ 
/*     */   public Long getItemId() {
/*  43 */     return this.itemId;
/*     */   }
/*     */ 
/*     */   public void setItemId(Long itemId) {
/*  47 */     this.itemId = itemId;
/*     */   }
/*     */ 
/*     */   public StandSalaryItem getStandSalaryItem() {
/*  51 */     return this.standSalaryItem;
/*     */   }
/*     */ 
/*     */   public void setStandSalaryItem(StandSalaryItem standSalaryItem) {
/*  55 */     this.standSalaryItem = standSalaryItem;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  63 */     QueryFilter filter = new QueryFilter(getRequest());
/*  64 */     List<StandSalaryItem> list = null;
/*  65 */     if (this.standardId != null) {
/*  66 */       list = this.standSalaryItemService.getAllByStandardId(this.standardId);
/*     */     }
/*  68 */     Type type = new TypeToken<List<StandSalaryItem>>() {  }
/*  68 */     .getType();
/*  69 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  70 */       .append(list.size()).append(",result:");
/*     */ 
/*  72 */     Gson gson = new Gson();
/*  73 */     buff.append(gson.toJson(list, type));
/*  74 */     buff.append("}");
/*     */ 
/*  76 */     this.jsonString = buff.toString();
/*     */ 
/*  78 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  86 */     String[] ids = getRequest().getParameterValues("ids");
/*  87 */     if (ids != null) {
/*  88 */       for (String id : ids) {
/*  89 */         this.standSalaryItemService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  93 */     this.jsonString = "{success:true}";
/*     */ 
/*  95 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 103 */     StandSalaryItem standSalaryItem = (StandSalaryItem)this.standSalaryItemService.get(this.itemId);
/*     */ 
/* 105 */     Gson gson = new Gson();
/*     */ 
/* 107 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 108 */     sb.append(gson.toJson(standSalaryItem));
/* 109 */     sb.append("}");
/* 110 */     setJsonString(sb.toString());
/*     */ 
/* 112 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 118 */     this.standSalaryItemService.save(this.standSalaryItem);
/* 119 */     setJsonString("{success:true}");
/* 120 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.hrm.StandSalaryItemAction
 * JD-Core Version:    0.6.0
 */
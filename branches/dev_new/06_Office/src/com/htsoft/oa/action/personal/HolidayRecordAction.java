/*     */ package com.htsoft.oa.action.personal;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.personal.HolidayRecord;
/*     */ import com.htsoft.oa.service.personal.HolidayRecordService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class HolidayRecordAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private HolidayRecordService holidayRecordService;
/*     */   private HolidayRecord holidayRecord;
/*     */   private Long recordId;
/*     */ 
/*     */   public Long getRecordId()
/*     */   {
/*  33 */     return this.recordId;
/*     */   }
/*     */ 
/*     */   public void setRecordId(Long recordId) {
/*  37 */     this.recordId = recordId;
/*     */   }
/*     */ 
/*     */   public HolidayRecord getHolidayRecord() {
/*  41 */     return this.holidayRecord;
/*     */   }
/*     */ 
/*     */   public void setHolidayRecord(HolidayRecord holidayRecord) {
/*  45 */     this.holidayRecord = holidayRecord;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  53 */     QueryFilter filter = new QueryFilter(getRequest());
/*  54 */     List<HolidayRecord> list = this.holidayRecordService.getAll(filter);
/*     */ 
/*  56 */     Type type = new TypeToken<List<HolidayRecord>>() {  }
/*  56 */     .getType();
/*  57 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  58 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  60 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
/*  77 */         this.holidayRecordService.remove(new Long(id));
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
/*  91 */     HolidayRecord holidayRecord = (HolidayRecord)this.holidayRecordService.get(this.recordId);
/*  92 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/*  94 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  95 */     sb.append(gson.toJson(holidayRecord));
/*  96 */     sb.append("}");
/*  97 */     setJsonString(sb.toString());
/*     */ 
/*  99 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 105 */     if (this.holidayRecord.getIsAll() == null)
/* 106 */       this.holidayRecord.setIsAll(HolidayRecord.IS_PERSONAL_HOLIDAY);
/*     */     else {
/* 108 */       this.holidayRecord.setIsAll(HolidayRecord.IS_ALL_HOLIDAY);
/*     */     }
/* 110 */     this.holidayRecordService.save(this.holidayRecord);
/*     */ 
/* 112 */     setJsonString("{success:true}");
/* 113 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.personal.HolidayRecordAction
 * JD-Core Version:    0.6.0
 */
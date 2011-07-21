/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.DepreType;
/*     */ import com.xpsoft.oa.service.admin.DepreTypeService;
/*     */ import com.xpsoft.oa.service.admin.FixedAssetsService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class DepreTypeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DepreTypeService depreTypeService;
/*     */   private DepreType depreType;
/*     */ 
/*     */   @Resource
/*     */   private FixedAssetsService fixedAssetsService;
/*     */   private Long depreTypeId;
/*     */ 
/*     */   public Long getDepreTypeId()
/*     */   {
/*  36 */     return this.depreTypeId;
/*     */   }
/*     */ 
/*     */   public void setDepreTypeId(Long depreTypeId) {
/*  40 */     this.depreTypeId = depreTypeId;
/*     */   }
/*     */ 
/*     */   public DepreType getDepreType() {
/*  44 */     return this.depreType;
/*     */   }
/*     */ 
/*     */   public void setDepreType(DepreType depreType) {
/*  48 */     this.depreType = depreType;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  56 */     QueryFilter filter = new QueryFilter(getRequest());
/*  57 */     List<DepreType> list = this.depreTypeService.getAll(filter);
/*  58 */     Type type = new TypeToken<List<DepreType>>() {  }
/*  58 */     .getType();
/*  59 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  60 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  61 */     Gson gson = new Gson();
/*  62 */     buff.append(gson.toJson(list, type));
/*  63 */     buff.append("}");
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
/*  77 */         QueryFilter filter = new QueryFilter(getRequest());
/*  78 */         filter.addFilter("Q_depreType.depreTypeId_L_EQ", id);
/*  79 */         List list = this.fixedAssetsService.getAll(filter);
/*  80 */         if (list.size() > 0) {
/*  81 */           this.jsonString = "{success:false,message:'该折算类型下还有资产，请把该资产移走后，再进行删除！'}";
/*  82 */           return "success";
/*     */         }
/*  84 */         this.depreTypeService.remove(new Long(id));
/*     */       }
/*     */     }
/*  87 */     this.jsonString = "{success:true}";
/*  88 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  96 */     DepreType depreType = (DepreType)this.depreTypeService.get(this.depreTypeId);
/*  97 */     Gson gson = new Gson();
/*     */ 
/*  99 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 100 */     sb.append(gson.toJson(depreType));
/* 101 */     sb.append("}");
/* 102 */     setJsonString(sb.toString());
/* 103 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 109 */     this.depreTypeService.save(this.depreType);
/* 110 */     setJsonString("{success:true}");
/* 111 */     return "success";
/*     */   }
/*     */ 
/*     */   public String combox()
/*     */   {
/* 119 */     List<DepreType> list = this.depreTypeService.getAll();
/* 120 */     StringBuffer buff = new StringBuffer("[");
/* 121 */     for (DepreType depreType : list) {
/* 122 */       buff.append("['" + depreType.getDepreTypeId() + "','" + depreType.getTypeName() + "','" + depreType.getCalMethod() + "'],");
/*     */     }
/* 124 */     if (list.size() > 0) {
/* 125 */       buff.deleteCharAt(buff.length() - 1);
/*     */     }
/* 127 */     buff.append("]");
/* 128 */     setJsonString(buff.toString());
/* 129 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.DepreTypeAction
 * JD-Core Version:    0.6.0
 */
/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.DepreType;
/*     */ import com.xpsoft.oa.model.admin.FixedAssets;
/*     */ import com.xpsoft.oa.service.admin.DepreRecordService;
/*     */ import com.xpsoft.oa.service.admin.DepreTypeService;
/*     */ import com.xpsoft.oa.service.admin.FixedAssetsService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class FixedAssetsAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private FixedAssetsService fixedAssetsService;
/*     */   private FixedAssets fixedAssets;
/*     */ 
/*     */   @Resource
/*     */   private DepreRecordService depreRecordService;
/*     */ 
/*     */   @Resource
/*     */   private DepreTypeService depreTypeService;
/*     */   private Long assetsId;
/*     */ 
/*     */   public Long getAssetsId()
/*     */   {
/*  55 */     return this.assetsId;
/*     */   }
/*     */ 
/*     */   public void setAssetsId(Long assetsId) {
/*  59 */     this.assetsId = assetsId;
/*     */   }
/*     */ 
/*     */   public FixedAssets getFixedAssets() {
/*  63 */     return this.fixedAssets;
/*     */   }
/*     */ 
/*     */   public void setFixedAssets(FixedAssets fixedAssets) {
/*  67 */     this.fixedAssets = fixedAssets;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  75 */     QueryFilter filter = new QueryFilter(getRequest());
/*  76 */     List list = this.fixedAssetsService.getAll(filter);
/*  77 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  78 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  79 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "buyDate", "startDepre", "manuDate" });
/*  80 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  81 */     buff.append("}");
/*  82 */     this.jsonString = buff.toString();
/*  83 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  91 */     String[] ids = getRequest().getParameterValues("ids");
/*  92 */     if (ids != null) {
/*  93 */       for (String id : ids)
/*     */       {
/* 101 */         this.fixedAssetsService.remove(new Long(id));
/*     */       }
/*     */     }
/* 104 */     this.jsonString = "{success:true}";
/*     */ 
/* 106 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 114 */     FixedAssets fixedAssets = (FixedAssets)this.fixedAssetsService.get(this.assetsId);
/* 115 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 116 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "manuDate", "buyDate", "startDepre" });
/* 117 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(fixedAssets));
/* 118 */     sb.append("}");
/* 119 */     setJsonString(sb.toString());
/* 120 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 126 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-SSSS");
/* 127 */     if (this.fixedAssets.getAssetsId() == null) {
/* 128 */       this.fixedAssets.setAssetsNo(sdf.format(new Date()));
/*     */     }
/* 130 */     Long typeId = this.fixedAssets.getDepreType().getDepreTypeId();
/* 131 */     if (typeId != null) {
/* 132 */       DepreType depreType = (DepreType)this.depreTypeService.get(typeId);
/* 133 */       if (depreType.getCalMethod().shortValue() != 2) {
/* 134 */         BigDecimal remainRate = this.fixedAssets.getRemainValRate();
/* 135 */         BigDecimal depreRate = new BigDecimal("1").subtract(remainRate.divide(new BigDecimal("100"))).divide(this.fixedAssets.getIntendTerm(), 2, 2);
/* 136 */         this.fixedAssets.setDepreRate(depreRate);
/*     */       }
/*     */     }
/* 139 */     this.fixedAssetsService.save(this.fixedAssets);
/* 140 */     setJsonString("{success:true}");
/* 141 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.FixedAssetsAction
 * JD-Core Version:    0.6.0
 */
/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.OfficeGoods;
/*     */ import com.xpsoft.oa.service.admin.OfficeGoodsService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class OfficeGoodsAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private OfficeGoodsService officeGoodsService;
/*     */   private OfficeGoods officeGoods;
/*     */   private Long goodsId;
/*     */ 
/*     */   public Long getGoodsId()
/*     */   {
/*  38 */     return this.goodsId;
/*     */   }
/*     */ 
/*     */   public void setGoodsId(Long goodsId) {
/*  42 */     this.goodsId = goodsId;
/*     */   }
/*     */ 
/*     */   public OfficeGoods getOfficeGoods() {
/*  46 */     return this.officeGoods;
/*     */   }
/*     */ 
/*     */   public void setOfficeGoods(OfficeGoods officeGoods) {
/*  50 */     this.officeGoods = officeGoods;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  58 */     QueryFilter filter = new QueryFilter(getRequest());
/*  59 */     List list = this.officeGoodsService.getAll(filter);
/*  60 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  61 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  62 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[0]);
/*  63 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  64 */     buff.append("}");
/*  65 */     this.jsonString = buff.toString();
/*  66 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  74 */     String[] ids = getRequest().getParameterValues("ids");
/*  75 */     if (ids != null) {
/*  76 */       for (String id : ids) {
/*  77 */         this.officeGoodsService.remove(new Long(id));
/*     */       }
/*     */     }
/*  80 */     this.jsonString = "{success:true}";
/*  81 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  89 */     OfficeGoods officeGoods = (OfficeGoods)this.officeGoodsService.get(this.goodsId);
/*  90 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  91 */     JSONSerializer serializer = new JSONSerializer();
/*  92 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(officeGoods));
/*  93 */     sb.append("}");
/*  94 */     setJsonString(sb.toString());
/*  95 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 101 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-SSSS");
/* 102 */     if (this.officeGoods.getGoodsId() == null) {
/* 103 */       this.officeGoods.setGoodsNo(sdf.format(new Date()));
/* 104 */       this.officeGoods.setStockCounts(Integer.valueOf(0));
/*     */     }
/* 106 */     this.officeGoodsService.save(this.officeGoods);
/* 107 */     setJsonString("{success:true}");
/* 108 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.OfficeGoodsAction
 * JD-Core Version:    0.6.0
 */
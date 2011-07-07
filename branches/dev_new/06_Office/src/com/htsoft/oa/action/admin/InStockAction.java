/*     */ package com.htsoft.oa.action.admin;
/*     */ 
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.JsonUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.admin.InStock;
/*     */ import com.htsoft.oa.model.admin.OfficeGoods;
/*     */ import com.htsoft.oa.service.admin.InStockService;
/*     */ import com.htsoft.oa.service.admin.OfficeGoodsService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.math.BigDecimal;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class InStockAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private InStockService inStockService;
/*     */   private InStock inStock;
/*     */ 
/*     */   @Resource
/*     */   private OfficeGoodsService officeGoodsService;
/*     */   private Long buyId;
/*     */ 
/*     */   public Long getBuyId()
/*     */   {
/*  44 */     return this.buyId;
/*     */   }
/*     */ 
/*     */   public void setBuyId(Long buyId) {
/*  48 */     this.buyId = buyId;
/*     */   }
/*     */ 
/*     */   public InStock getInStock() {
/*  52 */     return this.inStock;
/*     */   }
/*     */ 
/*     */   public void setInStock(InStock inStock) {
/*  56 */     this.inStock = inStock;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  64 */     QueryFilter filter = new QueryFilter(getRequest());
/*  65 */     List list = this.inStockService.getAll(filter);
/*  66 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  67 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  68 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "inDate" });
/*  69 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  70 */     buff.append("}");
/*  71 */     this.jsonString = buff.toString();
/*  72 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  80 */     String[] ids = getRequest().getParameterValues("ids");
/*  81 */     if (ids != null) {
/*  82 */       for (String id : ids) {
/*  83 */         this.inStockService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  87 */     this.jsonString = "{success:true}";
/*     */ 
/*  89 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  97 */     InStock inStock = (InStock)this.inStockService.get(this.buyId);
/*  98 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  99 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "inDate" });
/* 100 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(inStock));
/* 101 */     sb.append("}");
/* 102 */     setJsonString(sb.toString());
/* 103 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 109 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
/* 110 */     this.inStock.setStockNo(sdf.format(new Date()));
/* 111 */     Integer inCount = this.inStock.getInCounts();
/* 112 */     BigDecimal price = this.inStock.getPrice();
/* 113 */     BigDecimal amount = null;
/* 114 */     if ((inCount != null) && (price != null)) {
/* 115 */       amount = price.multiply(BigDecimal.valueOf(inCount.intValue()));
/*     */     }
/* 117 */     this.inStock.setAmount(amount);
/* 118 */     Long goodsId = this.inStock.getGoodsId();
/* 119 */     OfficeGoods goods = (OfficeGoods)this.officeGoodsService.get(goodsId);
/* 120 */     if (this.inStock.getBuyId() == null) {
/* 121 */       goods.setStockCounts(Integer.valueOf(goods.getStockCounts().intValue() + this.inStock.getInCounts().intValue()));
/*     */     } else {
/* 123 */       Integer newInCount = this.inStock.getInCounts();
/* 124 */       Integer oldInCount = this.inStockService.findInCountByBuyId(this.inStock.getBuyId());
/* 125 */       if (!oldInCount.equals(newInCount)) {
/* 126 */         goods.setStockCounts(Integer.valueOf(goods.getStockCounts().intValue() - oldInCount.intValue() + newInCount.intValue()));
/*     */       }
/*     */     }
/* 129 */     this.inStockService.save(this.inStock);
/* 130 */     this.officeGoodsService.save(goods);
/* 131 */     setJsonString("{success:true}");
/* 132 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.admin.InStockAction
 * JD-Core Version:    0.6.0
 */
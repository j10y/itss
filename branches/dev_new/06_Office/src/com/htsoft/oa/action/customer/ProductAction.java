/*     */ package com.htsoft.oa.action.customer;
/*     */ 
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.JsonUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.customer.Product;
/*     */ import com.htsoft.oa.service.customer.ProductService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class ProductAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ProductService productService;
/*     */   private Product product;
/*     */   private Long productId;
/*     */ 
/*     */   public Long getProductId()
/*     */   {
/*  34 */     return this.productId;
/*     */   }
/*     */ 
/*     */   public void setProductId(Long productId) {
/*  38 */     this.productId = productId;
/*     */   }
/*     */ 
/*     */   public Product getProduct() {
/*  42 */     return this.product;
/*     */   }
/*     */ 
/*     */   public void setProduct(Product product) {
/*  46 */     this.product = product;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  54 */     QueryFilter filter = new QueryFilter(getRequest());
/*  55 */     List list = this.productService.getAll(filter);
/*     */ 
/*  58 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  59 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  62 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "createtime", "updatetime" });
/*  63 */     buff.append(json.exclude(new String[] { "class" }).serialize(list));
/*  64 */     buff.append("}");
/*     */ 
/*  66 */     this.jsonString = buff.toString();
/*     */ 
/*  68 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  76 */     String[] ids = getRequest().getParameterValues("ids");
/*  77 */     if (ids != null) {
/*  78 */       for (String id : ids) {
/*  79 */         this.productService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  83 */     this.jsonString = "{success:true}";
/*     */ 
/*  85 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  93 */     Product product = (Product)this.productService.get(this.productId);
/*     */ 
/*  96 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "createtime", "updatetime" });
/*     */ 
/*  98 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  99 */     sb.append(json.exclude(new String[] { "class" }).serialize(product));
/* 100 */     sb.append("}");
/* 101 */     setJsonString(sb.toString());
/*     */ 
/* 103 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 109 */     this.product.setUpdatetime(new Date());
/* 110 */     this.productService.save(this.product);
/* 111 */     setJsonString("{success:true}");
/* 112 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.customer.ProductAction
 * JD-Core Version:    0.6.0
 */
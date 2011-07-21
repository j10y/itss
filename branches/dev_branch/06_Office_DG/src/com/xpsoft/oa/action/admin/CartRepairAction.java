/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.CartRepair;
/*     */ import com.xpsoft.oa.service.admin.CartRepairService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CartRepairAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private CartRepairService cartRepairService;
/*     */   private CartRepair cartRepair;
/*     */   private Long repairId;
/*     */ 
/*     */   public Long getRepairId()
/*     */   {
/*  36 */     return this.repairId;
/*     */   }
/*     */ 
/*     */   public void setRepairId(Long repairId) {
/*  40 */     this.repairId = repairId;
/*     */   }
/*     */ 
/*     */   public CartRepair getCartRepair() {
/*  44 */     return this.cartRepair;
/*     */   }
/*     */ 
/*     */   public void setCartRepair(CartRepair cartRepair) {
/*  48 */     this.cartRepair = cartRepair;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  56 */     QueryFilter filter = new QueryFilter(getRequest());
/*  57 */     List list = this.cartRepairService.getAll(filter);
/*  58 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  59 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  60 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "repairDate" });
/*  61 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  62 */     buff.append("}");
/*  63 */     this.jsonString = buff.toString();
/*  64 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  72 */     String[] ids = getRequest().getParameterValues("ids");
/*  73 */     if (ids != null) {
/*  74 */       for (String id : ids) {
/*  75 */         this.cartRepairService.remove(new Long(id));
/*     */       }
/*     */     }
/*  78 */     this.jsonString = "{success:true}";
/*  79 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  87 */     CartRepair cartRepair = (CartRepair)this.cartRepairService.get(this.repairId);
/*  88 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  89 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "repairDate" });
/*  90 */     sb.append(serializer.exclude(new String[] { "class", "car.cartRepairs" }).serialize(cartRepair));
/*  91 */     sb.append("}");
/*  92 */     setJsonString(sb.toString());
/*  93 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/*  99 */     this.cartRepairService.save(this.cartRepair);
/* 100 */     setJsonString("{success:true}");
/* 101 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.CartRepairAction
 * JD-Core Version:    0.6.0
 */
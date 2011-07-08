/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.GoodsApply;
/*     */ import com.xpsoft.oa.model.admin.OfficeGoods;
/*     */ import com.xpsoft.oa.model.info.ShortMessage;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.admin.GoodsApplyService;
/*     */ import com.xpsoft.oa.service.admin.OfficeGoodsService;
/*     */ import com.xpsoft.oa.service.info.ShortMessageService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class GoodsApplyAction extends BaseAction
/*     */ {
/*  37 */   private static short PASS_APPLY = 1;
/*  38 */   private static short NOTPASS_APPLY = 0;
/*     */ 
/*     */   @Resource
/*     */   private GoodsApplyService goodsApplyService;
/*     */   private GoodsApply goodsApply;
/*     */ 
/*     */   @Resource
/*     */   private ShortMessageService shortMessageService;
/*     */ 
/*     */   @Resource
/*     */   private OfficeGoodsService officeGoodsService;
/*     */   private Long applyId;
/*     */ 
/*  50 */   public Long getApplyId() { return this.applyId; }
/*     */ 
/*     */   public void setApplyId(Long applyId)
/*     */   {
/*  54 */     this.applyId = applyId;
/*     */   }
/*     */ 
/*     */   public GoodsApply getGoodsApply() {
/*  58 */     return this.goodsApply;
/*     */   }
/*     */ 
/*     */   public void setGoodsApply(GoodsApply goodsApply) {
/*  62 */     this.goodsApply = goodsApply;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  70 */     QueryFilter filter = new QueryFilter(getRequest());
/*  71 */     List list = this.goodsApplyService.getAll(filter);
/*  72 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  73 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  74 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "applyDate" });
/*  75 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  76 */     buff.append("}");
/*  77 */     this.jsonString = buff.toString();
/*  78 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  86 */     String[] ids = getRequest().getParameterValues("ids");
/*  87 */     if (ids != null) {
/*  88 */       for (String id : ids) {
/*  89 */         this.goodsApplyService.remove(new Long(id));
/*     */       }
/*     */     }
/*  92 */     this.jsonString = "{success:true}";
/*  93 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 101 */     GoodsApply goodsApply = (GoodsApply)this.goodsApplyService.get(this.applyId);
/* 102 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 103 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "applyDate" });
/* 104 */     sb.append(serializer.exclude(new String[] { "class" }).serialize(goodsApply));
/* 105 */     sb.append("}");
/* 106 */     setJsonString(sb.toString());
/* 107 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 113 */     OfficeGoods officeGoods = (OfficeGoods)this.officeGoodsService.get(this.goodsApply.getOfficeGoods().getGoodsId());
/* 114 */     Integer con = this.goodsApply.getUseCounts();
/* 115 */     Integer least = Integer.valueOf(officeGoods.getStockCounts().intValue() - con.intValue());
/* 116 */     if (least.intValue() > 0) {
/* 117 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss-SSSS");
/* 118 */       this.goodsApply.setApplyNo("GA" + sdf.format(new Date()));
/* 119 */       if (this.goodsApply.getApplyId() == null) {
/* 120 */         this.goodsApply.setApprovalStatus(Short.valueOf(NOTPASS_APPLY));
/*     */       }
/* 122 */       this.goodsApplyService.save(this.goodsApply);
/* 123 */       if (this.goodsApply.getApprovalStatus().shortValue() == PASS_APPLY) {
/* 124 */         Long receiveId = this.goodsApply.getUserId();
/* 125 */         String content = "你申请的办公用品为" + officeGoods.getGoodsName() + "已经通过审批，请查收";
/* 126 */         this.shortMessageService.save(AppUser.SYSTEM_USER, receiveId.toString(), content, ShortMessage.MSG_TYPE_SYS);
/* 127 */         officeGoods.setStockCounts(least);
/* 128 */         this.officeGoodsService.save(officeGoods);
/*     */       }
/* 130 */       setJsonString("{success:true}");
/*     */     } else {
/* 132 */       setJsonString("{success:false,message:'库存不足!'}");
/*     */     }
/* 134 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.GoodsApplyAction
 * JD-Core Version:    0.6.0
 */
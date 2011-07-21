/*     */ package com.xpsoft.oa.action.admin;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.admin.Car;
/*     */ import com.xpsoft.oa.model.admin.CarApply;
/*     */ import com.xpsoft.oa.model.info.ShortMessage;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.admin.CarApplyService;
/*     */ import com.xpsoft.oa.service.admin.CarService;
/*     */ import com.xpsoft.oa.service.info.ShortMessageService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CarApplyAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private CarApplyService carApplyService;
/*     */   private CarApply carApply;
/*     */ 
/*     */   @Resource
/*     */   private ShortMessageService shortMessageService;
/*     */ 
/*     */   @Resource
/*     */   private CarService carService;
/*     */   private Long applyId;
/*     */ 
/*     */   public Long getApplyId()
/*     */   {
/*  49 */     return this.applyId;
/*     */   }
/*     */ 
/*     */   public void setApplyId(Long applyId) {
/*  53 */     this.applyId = applyId;
/*     */   }
/*     */ 
/*     */   public CarApply getCarApply() {
/*  57 */     return this.carApply;
/*     */   }
/*     */ 
/*     */   public void setCarApply(CarApply carApply) {
/*  61 */     this.carApply = carApply;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  69 */     QueryFilter filter = new QueryFilter(getRequest());
/*  70 */     List list = this.carApplyService.getAll(filter);
/*  71 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  72 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  73 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "applyDate", "startTime", "endTime" });
/*  74 */     buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
/*  75 */     buff.append("}");
/*  76 */     this.jsonString = buff.toString();
/*  77 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  85 */     String[] ids = getRequest().getParameterValues("ids");
/*  86 */     if (ids != null) {
/*  87 */       for (String id : ids) {
/*  88 */         this.carApplyService.remove(new Long(id));
/*     */       }
/*     */     }
/*  91 */     this.jsonString = "{success:true}";
/*  92 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 100 */     CarApply carApply = (CarApply)this.carApplyService.get(this.applyId);
/* 101 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 102 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "applyDate", "startTime", "endTime" });
/* 103 */     sb.append(serializer.exclude(new String[] { "class", "car.carApplys" }).serialize(carApply));
/* 104 */     sb.append("}");
/* 105 */     setJsonString(sb.toString());
/* 106 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 115 */     this.carApplyService.save(this.carApply);
/* 116 */     if (this.carApply.getApprovalStatus().shortValue() == Car.PASS_APPLY) {
/* 117 */       Long receiveId = this.carApply.getUserId();
/* 118 */       Car car = (Car)this.carService.get(this.carApply.getCar().getCarId());
/* 119 */       String content = "你申请的车牌号为" + car.getCarNo() + "已经通过审批，请注意查收";
/* 120 */       this.shortMessageService.save(AppUser.SYSTEM_USER, receiveId.toString(), content, ShortMessage.MSG_TYPE_SYS);
/*     */     }
/* 122 */     setJsonString("{success:true}");
/* 123 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.admin.CarApplyAction
 * JD-Core Version:    0.6.0
 */
/*     */ package com.xpsoft.oa.action.customer;
/*     */ 
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.util.JsonUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.customer.CusConnection;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.customer.CusConnectionService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class CusConnectionAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private CusConnectionService cusConnectionService;
/*     */   private CusConnection cusConnection;
/*     */   private Long connId;
/*     */ 
/*     */   public Long getConnId()
/*     */   {
/*  37 */     return this.connId;
/*     */   }
/*     */ 
/*     */   public void setConnId(Long connId) {
/*  41 */     this.connId = connId;
/*     */   }
/*     */ 
/*     */   public CusConnection getCusConnection() {
/*  45 */     return this.cusConnection;
/*     */   }
/*     */ 
/*     */   public void setCusConnection(CusConnection cusConnection) {
/*  49 */     this.cusConnection = cusConnection;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  57 */     QueryFilter filter = new QueryFilter(getRequest());
/*  58 */     List list = this.cusConnectionService.getAll(filter);
/*     */ 
/*  60 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  61 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  63 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "startDate", "endDate" });
/*  64 */     buff.append(json.exclude(new String[] { "class" }).serialize(list));
/*  65 */     buff.append("}");
/*     */ 
/*  67 */     this.jsonString = buff.toString();
/*     */ 
/*  69 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  77 */     String[] ids = getRequest().getParameterValues("ids");
/*  78 */     if (ids != null) {
/*  79 */       for (String id : ids) {
/*  80 */         this.cusConnectionService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  84 */     this.jsonString = "{success:true}";
/*     */ 
/*  86 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  94 */     CusConnection cusConnection = (CusConnection)this.cusConnectionService.get(this.connId);
/*     */ 
/*  96 */     JSONSerializer json = JsonUtil.getJSONSerializer(new String[] { "startDate", "endDate" });
/*     */ 
/*  98 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  99 */     sb.append(json.exclude(new String[] { "class" }).serialize(cusConnection));
/* 100 */     sb.append("}");
/* 101 */     setJsonString(sb.toString());
/*     */ 
/* 103 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 109 */     boolean pass = false;
/* 110 */     StringBuffer buff = new StringBuffer("{");
/* 111 */     if (this.cusConnection.getStartDate().getTime() < this.cusConnection.getEndDate().getTime())
/* 112 */       pass = true;
/* 113 */     else buff.append("msg:'交往结束日期不能早于开始日期!',");
/*     */ 
/* 115 */     if (pass) {
/* 116 */       this.cusConnection.setCreator(ContextUtil.getCurrentUser().getFullname());
/* 117 */       this.cusConnectionService.save(this.cusConnection);
/* 118 */       buff.append("success:true}");
/*     */     } else {
/* 120 */       buff.append("failure:true}");
/*     */     }
/* 122 */     setJsonString(buff.toString());
/* 123 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.customer.CusConnectionAction
 * JD-Core Version:    0.6.0
 */
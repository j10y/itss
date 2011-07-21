/*     */ package com.xpsoft.oa.action.system;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.oa.model.system.ReportParam;
/*     */ import com.xpsoft.oa.service.system.ReportParamService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ReportParamAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ReportParamService reportParamService;
/*     */   private ReportParam reportParam;
/*     */   private Long paramId;
/*     */ 
/*     */   public Long getParamId()
/*     */   {
/*  34 */     return this.paramId;
/*     */   }
/*     */ 
/*     */   public void setParamId(Long paramId) {
/*  38 */     this.paramId = paramId;
/*     */   }
/*     */ 
/*     */   public ReportParam getReportParam() {
/*  42 */     return this.reportParam;
/*     */   }
/*     */ 
/*     */   public void setReportParam(ReportParam reportParam) {
/*  46 */     this.reportParam = reportParam;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  53 */     String strReportId = getRequest().getParameter("reportId");
/*  54 */     if (StringUtils.isNotEmpty(strReportId)) {
/*  55 */       List<ReportParam> list = this.reportParamService.findByRepTemp(new Long(strReportId));
/*     */ 
/*  57 */       Type type = new TypeToken<List<ReportParam>>() {  }
/*  57 */       .getType();
/*  58 */       StringBuffer buff = new StringBuffer("{success:true,")
/*  59 */         .append("result:");
/*  60 */       Gson gson = new Gson();
/*  61 */       buff.append(gson.toJson(list, type));
/*  62 */       buff.append("}");
/*  63 */       this.jsonString = buff.toString();
/*     */     }
/*     */ 
/*  67 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  75 */     String[] ids = getRequest().getParameterValues("ids");
/*  76 */     if (ids != null) {
/*  77 */       for (String id : ids) {
/*  78 */         this.reportParamService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  82 */     this.jsonString = "{success:true}";
/*     */ 
/*  84 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  92 */     ReportParam reportParam = (ReportParam)this.reportParamService.get(this.paramId);
/*     */ 
/*  94 */     Gson gson = new Gson();
/*     */ 
/*  96 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/*  97 */     sb.append(gson.toJson(reportParam));
/*  98 */     sb.append("}");
/*  99 */     setJsonString(sb.toString());
/*     */ 
/* 101 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 107 */     this.reportParamService.save(this.reportParam);
/* 108 */     setJsonString("{success:true}");
/* 109 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.system.ReportParamAction
 * JD-Core Version:    0.6.0
 */
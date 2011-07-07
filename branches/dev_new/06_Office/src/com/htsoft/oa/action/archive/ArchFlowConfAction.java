/*     */ package com.htsoft.oa.action.archive;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.archive.ArchFlowConf;
/*     */ import com.htsoft.oa.service.archive.ArchFlowConfService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ArchFlowConfAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private ArchFlowConfService archFlowConfService;
/*     */   private ArchFlowConf archFlowConf;
/*     */   private Long configId;
/*     */ 
/*     */   public Long getConfigId()
/*     */   {
/*  35 */     return this.configId;
/*     */   }
/*     */ 
/*     */   public void setConfigId(Long configId) {
/*  39 */     this.configId = configId;
/*     */   }
/*     */ 
/*     */   public ArchFlowConf getArchFlowConf() {
/*  43 */     return this.archFlowConf;
/*     */   }
/*     */ 
/*     */   public void setArchFlowConf(ArchFlowConf archFlowConf) {
/*  47 */     this.archFlowConf = archFlowConf;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  55 */     QueryFilter filter = new QueryFilter(getRequest());
/*  56 */     List<ArchFlowConf> list = this.archFlowConfService.getAll(filter);
/*     */ 
/*  58 */     Type type = new TypeToken<List<ArchFlowConf>>() {  }
/*  58 */     .getType();
/*  59 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  60 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  62 */     Gson gson = new Gson();
/*  63 */     buff.append(gson.toJson(list, type));
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
/*  79 */         this.archFlowConfService.remove(new Long(id));
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
/* 101 */     ArchFlowConf sendFlowConf = this.archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
/* 102 */     ArchFlowConf recFlowConf = this.archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
/* 103 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 104 */     if (sendFlowConf != null)
/* 105 */       sb.append("{'sendProcessId':'" + sendFlowConf.getProcessDefId() + "','sendProcessName':'" + sendFlowConf.getProcessName() + "'");
/*     */     else {
/* 107 */       sb.append("{'sendProcessId':'','sendProcessName':''");
/*     */     }
/* 109 */     if (recFlowConf != null)
/* 110 */       sb.append(",'recProcessId':'" + recFlowConf.getProcessDefId() + "','recProcessName':'" + recFlowConf.getProcessName() + "'}}");
/*     */     else {
/* 112 */       sb.append(",'recProcessId':'','recProcessName':''}}");
/*     */     }
/* 114 */     setJsonString(sb.toString());
/* 115 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 121 */     String sendId = getRequest().getParameter("sendProcessId");
/* 122 */     String sendName = getRequest().getParameter("sendProcessName");
/* 123 */     String recId = getRequest().getParameter("recProcessId");
/* 124 */     String recName = getRequest().getParameter("recProcessName");
/* 125 */     if ((StringUtils.isNotEmpty(sendId)) && (StringUtils.isNotEmpty(sendName))) {
/* 126 */       this.archFlowConf = this.archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
/* 127 */       if (this.archFlowConf == null) {
/* 128 */         this.archFlowConf = new ArchFlowConf();
/* 129 */         this.archFlowConf.setArchType(ArchFlowConf.ARCH_SEND_TYPE);
/*     */       }
/* 131 */       this.archFlowConf.setProcessDefId(new Long(sendId));
/* 132 */       this.archFlowConf.setProcessName(sendName);
/* 133 */       this.archFlowConfService.save(this.archFlowConf);
/*     */     }
/* 135 */     if ((StringUtils.isNotEmpty(recId)) && (StringUtils.isNotEmpty(recName))) {
/* 136 */       this.archFlowConf = this.archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
/* 137 */       if (this.archFlowConf == null) {
/* 138 */         this.archFlowConf = new ArchFlowConf();
/* 139 */         this.archFlowConf.setArchType(ArchFlowConf.ARCH_REC_TYPE);
/*     */       }
/* 141 */       this.archFlowConf.setProcessDefId(new Long(recId));
/* 142 */       this.archFlowConf.setProcessName(recName);
/* 143 */       this.archFlowConfService.save(this.archFlowConf);
/*     */     }
/* 145 */     setJsonString("{success:true}");
/* 146 */     return "success";
/*     */   }
/*     */ 
/*     */   public String getFlow()
/*     */   {
/* 153 */     String type = getRequest().getParameter("flowType");
/* 154 */     StringBuffer sb = new StringBuffer();
/* 155 */     if (type.equals(ArchFlowConf.ARCH_SEND_TYPE.toString())) {
/* 156 */       this.archFlowConf = this.archFlowConfService.getByFlowType(ArchFlowConf.ARCH_SEND_TYPE);
/*     */     }
/*     */     else {
/* 159 */       this.archFlowConf = this.archFlowConfService.getByFlowType(ArchFlowConf.ARCH_REC_TYPE);
/*     */     }
/* 161 */     if (this.archFlowConf != null)
/* 162 */       sb.append("{success:true,defId:").append(this.archFlowConf.getProcessDefId()).append("}");
/*     */     else {
/* 164 */       sb.append("{success:false,'message':'你还没设定流程'}");
/*     */     }
/* 166 */     setJsonString(sb.toString());
/* 167 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.archive.ArchFlowConfAction
 * JD-Core Version:    0.6.0
 */
/*     */ package com.xpsoft.oa.action.hrm;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.hrm.HireIssue;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.hrm.HireIssueService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HireIssueAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private HireIssueService hireIssueService;
/*     */   private HireIssue hireIssue;
/*     */   private Long hireId;
/*     */ 
/*     */   public Long getHireId()
/*     */   {
/*  38 */     return this.hireId;
/*     */   }
/*     */ 
/*     */   public void setHireId(Long hireId) {
/*  42 */     this.hireId = hireId;
/*     */   }
/*     */ 
/*     */   public HireIssue getHireIssue() {
/*  46 */     return this.hireIssue;
/*     */   }
/*     */ 
/*     */   public void setHireIssue(HireIssue hireIssue) {
/*  50 */     this.hireIssue = hireIssue;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  58 */     QueryFilter filter = new QueryFilter(getRequest());
/*  59 */     List<HireIssue> list = this.hireIssueService.getAll(filter);
/*     */ 
/*  61 */     Type type = new TypeToken<List<HireIssue>>() {}.getType();
/*  62 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  63 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  65 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*  66 */     buff.append(gson.toJson(list, type));
/*  67 */     buff.append("}");
/*     */ 
/*  69 */     this.jsonString = buff.toString();
/*     */ 
/*  71 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  79 */     String[] ids = getRequest().getParameterValues("ids");
/*  80 */     if (ids != null) {
/*  81 */       for (String id : ids) {
/*  82 */         this.hireIssueService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  86 */     this.jsonString = "{success:true}";
/*     */ 
/*  88 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/*  96 */     HireIssue hireIssue = (HireIssue)this.hireIssueService.get(this.hireId);
/*     */ 
/*  98 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/* 100 */     StringBuffer sb = new StringBuffer("{success:true,data:[");
/* 101 */     sb.append(gson.toJson(hireIssue));
/* 102 */     sb.append("]}");
/* 103 */     setJsonString(sb.toString());
/*     */ 
/* 105 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 111 */     AppUser user = ContextUtil.getCurrentUser();
/* 112 */     if (this.hireIssue.getHireId() == null) {
/* 113 */       this.hireIssue.setRegFullname(user.getFullname());
/* 114 */       this.hireIssue.setRegDate(new Date());
/*     */     } else {
/* 116 */       this.hireIssue.setModifyFullname(user.getFullname());
/* 117 */       this.hireIssue.setModifyDate(new Date());
/*     */     }
/* 119 */     this.hireIssue.setStatus(HireIssue.NOTYETPASS_CHECK);
/* 120 */     this.hireIssueService.save(this.hireIssue);
/* 121 */     setJsonString("{success:true}");
/* 122 */     return "success";
/*     */   }
/*     */ 
/*     */   public String load()
/*     */   {
/* 130 */     String strHireId = getRequest().getParameter("hireId");
/* 131 */     if (StringUtils.isNotEmpty(strHireId)) {
/* 132 */       this.hireIssue = ((HireIssue)this.hireIssueService.get(new Long(strHireId)));
/*     */     }
/* 134 */     return "load";
/*     */   }
/*     */ 
/*     */   public String check()
/*     */   {
/* 142 */     String status = getRequest().getParameter("status");
/* 143 */     String strHireId = getRequest().getParameter("hireId");
/* 144 */     String checkOpinion = getRequest().getParameter("checkOpinion");
/* 145 */     if (StringUtils.isNotEmpty(strHireId)) {
/* 146 */       AppUser appUser = ContextUtil.getCurrentUser();
/* 147 */       this.hireIssue = ((HireIssue)this.hireIssueService.get(new Long(strHireId)));
/* 148 */       this.hireIssue.setCheckFullname(appUser.getFullname());
/* 149 */       this.hireIssue.setCheckDate(new Date());
/* 150 */       this.hireIssue.setCheckOpinion(checkOpinion);
/* 151 */       if (StringUtils.isNotEmpty(status)) {
/* 152 */         this.hireIssue.setStatus(Short.valueOf(status));
/* 153 */         this.hireIssueService.save(this.hireIssue);
/* 154 */         setJsonString("{success:true}");
/*     */       } else {
/* 156 */         setJsonString("{success:false}");
/*     */       }
/*     */     } else {
/* 159 */       setJsonString("{success:false}");
/*     */     }
/* 161 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.hrm.HireIssueAction
 * JD-Core Version:    0.6.0
 */
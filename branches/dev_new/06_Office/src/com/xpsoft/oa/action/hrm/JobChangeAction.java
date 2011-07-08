/*     */ package com.xpsoft.oa.action.hrm;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.xpsoft.core.command.QueryFilter;
/*     */ import com.xpsoft.core.util.ContextUtil;
/*     */ import com.xpsoft.core.web.action.BaseAction;
/*     */ import com.xpsoft.core.web.paging.PagingBean;
/*     */ import com.xpsoft.oa.model.hrm.EmpProfile;
/*     */ import com.xpsoft.oa.model.hrm.JobChange;
/*     */ import com.xpsoft.oa.model.system.AppUser;
/*     */ import com.xpsoft.oa.service.hrm.EmpProfileService;
/*     */ import com.xpsoft.oa.service.hrm.JobChangeService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class JobChangeAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private JobChangeService jobChangeService;
/*     */   private JobChange jobChange;
/*     */ 
/*     */   @Resource
/*     */   private EmpProfileService empProfileService;
/*     */   private Long changeId;
/*     */ 
/*     */   public Long getChangeId()
/*     */   {
/*  42 */     return this.changeId;
/*     */   }
/*     */ 
/*     */   public void setChangeId(Long changeId) {
/*  46 */     this.changeId = changeId;
/*     */   }
/*     */ 
/*     */   public JobChange getJobChange() {
/*  50 */     return this.jobChange;
/*     */   }
/*     */ 
/*     */   public void setJobChange(JobChange jobChange) {
/*  54 */     this.jobChange = jobChange;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  62 */     QueryFilter filter = new QueryFilter(getRequest());
/*  63 */     List<JobChange> list = this.jobChangeService.getAll(filter);
/*     */ 
/*  65 */     Type type = new TypeToken<List<JobChange>>() {  }
/*  65 */     .getType();
/*  66 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  67 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  69 */     Gson gson = new Gson();
/*  70 */     buff.append(gson.toJson(list, type));
/*  71 */     buff.append("}");
/*     */ 
/*  73 */     this.jsonString = buff.toString();
/*     */ 
/*  75 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  83 */     String[] ids = getRequest().getParameterValues("ids");
/*  84 */     if (ids != null) {
/*  85 */       for (String id : ids) {
/*  86 */         this.jobChangeService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  90 */     this.jsonString = "{success:true}";
/*     */ 
/*  92 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 100 */     JobChange jobChange = (JobChange)this.jobChangeService.get(this.changeId);
/*     */ 
/* 102 */     Gson gson = new Gson();
/*     */ 
/* 104 */     StringBuffer sb = new StringBuffer("{success:true,data:[");
/* 105 */     sb.append(gson.toJson(jobChange));
/* 106 */     sb.append("]}");
/* 107 */     setJsonString(sb.toString());
/*     */ 
/* 109 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 115 */     this.jobChange.setRegName(ContextUtil.getCurrentUser().getFullname());
/* 116 */     this.jobChange.setRegTime(new Date());
/* 117 */     this.jobChangeService.save(this.jobChange);
/* 118 */     setJsonString("{success:true}");
/* 119 */     return "success";
/*     */   }
/*     */ 
/*     */   public String load()
/*     */   {
/* 126 */     String strId = getRequest().getParameter("changeId");
/* 127 */     if (StringUtils.isNotEmpty(strId)) {
/* 128 */       this.jobChange = ((JobChange)this.jobChangeService.get(new Long(strId)));
/*     */     }
/* 130 */     return "load";
/*     */   }
/*     */ 
/*     */   public String check()
/*     */   {
/* 138 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 139 */     Short status = this.jobChange.getStatus();
/* 140 */     String changeOpinion = this.jobChange.getCheckOpinion();
/* 141 */     Long changeId = this.jobChange.getChangeId();
/* 142 */     if (changeId != null) {
/* 143 */       this.jobChange = ((JobChange)this.jobChangeService.get(changeId));
/* 144 */       this.jobChange.setStatus(status);
/* 145 */       this.jobChange.setCheckOpinion(changeOpinion);
/* 146 */       this.jobChange.setCheckName(appUser.getFullname());
/* 147 */       this.jobChange.setCheckTime(new Date());
/* 148 */       this.jobChangeService.save(this.jobChange);
/* 149 */       if (status.shortValue() == 1) {
/* 150 */         Long profileId = this.jobChange.getProfileId();
/* 151 */         if (profileId != null) {
/* 152 */           EmpProfile empProfile = (EmpProfile)this.empProfileService.get(profileId);
/* 153 */           empProfile.setJobId(this.jobChange.getNewJobId());
/* 154 */           empProfile.setPosition(this.jobChange.getNewJobName());
/* 155 */           empProfile.setDepId(this.jobChange.getNewDepId());
/* 156 */           empProfile.setDepName(this.jobChange.getNewDepName());
/* 157 */           empProfile.setStandardId(this.jobChange.getNewStandardId());
/* 158 */           empProfile.setStandardMiNo(this.jobChange.getNewStandardNo());
/* 159 */           empProfile.setStandardName(this.jobChange.getNewStandardName());
/* 160 */           empProfile.setStandardMoney(this.jobChange.getNewTotalMoney());
/* 161 */           this.empProfileService.merge(empProfile);
/*     */         }
/*     */       }
/* 164 */       setJsonString("{success:true}");
/*     */     } else {
/* 166 */       setJsonString("{success:false}");
/*     */     }
/* 168 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.hrm.JobChangeAction
 * JD-Core Version:    0.6.0
 */
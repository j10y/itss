/*     */ package com.htsoft.oa.action.task;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.util.JsonUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.model.system.Department;
/*     */ import com.htsoft.oa.model.system.FileAttach;
/*     */ import com.htsoft.oa.model.task.PlanAttend;
/*     */ import com.htsoft.oa.model.task.WorkPlan;
/*     */ import com.htsoft.oa.service.system.AppUserService;
/*     */ import com.htsoft.oa.service.system.DepartmentService;
/*     */ import com.htsoft.oa.service.system.FileAttachService;
/*     */ import com.htsoft.oa.service.task.PlanAttendService;
/*     */ import com.htsoft.oa.service.task.WorkPlanService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class WorkPlanAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private WorkPlanService workPlanService;
/*     */   private WorkPlan workPlan;
/*     */ 
/*     */   @Resource
/*     */   private FileAttachService fileAttachService;
/*     */ 
/*     */   @Resource
/*     */   private DepartmentService departmentService;
/*     */ 
/*     */   @Resource
/*     */   private PlanAttendService planAttendService;
/*     */ 
/*     */   @Resource
/*     */   private AppUserService appUserService;
/*     */   private Long planId;
/*  52 */   static short ISDEPARTMENT = 1;
/*  53 */   static short NOTDEPARTMENT = 0;
/*  54 */   static short ISPRIMARY = 1;
/*  55 */   static short NOTPRIMARY = 0;
/*     */ 
/*     */   public Long getPlanId() {
/*  58 */     return this.planId;
/*     */   }
/*     */ 
/*     */   public void setPlanId(Long planId) {
/*  62 */     this.planId = planId;
/*     */   }
/*     */ 
/*     */   public WorkPlan getWorkPlan() {
/*  66 */     return this.workPlan;
/*     */   }
/*     */ 
/*     */   public void setWorkPlan(WorkPlan workPlan) {
/*  70 */     this.workPlan = workPlan;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  77 */     Long userId = ContextUtil.getCurrentUserId();
/*  78 */     QueryFilter filter = new QueryFilter(getRequest());
/*  79 */     filter.addFilter("Q_appUser.userId_L_EQ", userId.toString());
/*  80 */     List list = this.workPlanService.getAll(filter);
/*  81 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  82 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*  83 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "startTime", "endTime" });
/*  84 */     buff.append(serializer.exclude(new String[] { "class", "appUser.password" }).serialize(
/*  85 */       list));
/*  86 */     buff.append("}");
/*     */ 
/*  88 */     this.jsonString = buff.toString();
/*     */ 
/*  90 */     return "success";
/*     */   }
/*     */ 
/*     */   public String personal()
/*     */   {
/*  98 */     QueryFilter filter = new QueryFilter(getRequest());
/*  99 */     Long userId = ContextUtil.getCurrentUserId();
/* 100 */     filter.addFilter("Q_appUser.userId_L_EQ", userId.toString());
/* 101 */     filter.addFilter("Q_isPersonal_SN_EQ", "1");
/* 102 */     filter.addFilter("Q_status_SN_EQ", "1");
/* 103 */     List list = this.workPlanService.getAll(filter);
/* 104 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 105 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/* 106 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "startTime", "endTime" });
/* 107 */     buff.append(serializer.exclude(new String[] { "class", "appUser.password", "department" }).serialize(
/* 108 */       list));
/* 109 */     buff.append("}");
/* 110 */     this.jsonString = buff.toString();
/* 111 */     return "success";
/*     */   }
/*     */ 
/*     */   public String department()
/*     */   {
/* 118 */     PagingBean pb = getInitPagingBean();
/* 119 */     AppUser user = ContextUtil.getCurrentUser();
/* 120 */     List list = this.workPlanService.findByDepartment(this.workPlan, user, pb);
/* 121 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 122 */       .append(pb.getTotalItems()).append(",result:");
/* 123 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "startTime", "endTime" });
/* 124 */     buff.append(serializer.exclude(new String[] { "class", "appUser.password", "department" }).serialize(
/* 125 */       list));
/* 126 */     buff.append("}");
/* 127 */     this.jsonString = buff.toString();
/* 128 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 136 */     String[] ids = getRequest().getParameterValues("ids");
/* 137 */     if (ids != null) {
/* 138 */       for (String id : ids) {
/* 139 */         this.workPlanService.remove(new Long(id));
/*     */       }
/*     */     }
/* 142 */     this.jsonString = "{success:true}";
/* 143 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 151 */     WorkPlan workPlan = (WorkPlan)this.workPlanService.get(this.planId);
/* 152 */     Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/* 154 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 155 */     sb.append(gson.toJson(workPlan));
/* 156 */     sb.append("}");
/* 157 */     setJsonString(sb.toString());
/* 158 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 164 */     String issueScopeIds = getRequest().getParameter("issueScopeIds");
/* 165 */     String participantsIds = getRequest().getParameter("participantsIds");
/* 166 */     String principalIds = getRequest().getParameter("principalIds");
/* 167 */     String fileIds = getRequest().getParameter("planFileIds");
/* 168 */     short isPersonal = this.workPlan.getIsPersonal().shortValue();
/* 169 */     this.workPlan.getPlanFiles().clear();
/* 170 */     if (StringUtils.isNotEmpty(fileIds)) {
/* 171 */       String[] fIds = fileIds.split(",");
/* 172 */       for (int i = 0; i < fIds.length; i++) {
/* 173 */         FileAttach fileAttach = (FileAttach)this.fileAttachService.get(new Long(fIds[i]));
/* 174 */         this.workPlan.getPlanFiles().add(fileAttach);
/*     */       }
/*     */     }
/* 177 */     this.workPlan.setPlanFiles(this.workPlan.getPlanFiles());
/* 178 */     AppUser appUser = ContextUtil.getCurrentUser();
/* 179 */     this.workPlan.setAppUser(appUser);
/* 180 */     if (isPersonal == 1) {
/* 181 */       this.workPlan.setPrincipal(appUser.getFullname());
/*     */     }
/* 183 */     this.workPlanService.save(this.workPlan);
/* 184 */     if (isPersonal != 1) {
/* 185 */       if (StringUtils.isNotEmpty(issueScopeIds)) {
/* 186 */         boolean b = this.planAttendService.deletePlanAttend(this.workPlan.getPlanId(), Short.valueOf(ISDEPARTMENT), Short.valueOf(NOTPRIMARY));
/* 187 */         if (b) {
/* 188 */           String[] strIssueScopeId = issueScopeIds.split(",");
/* 189 */           for (int i = 0; i < strIssueScopeId.length; i++) {
/* 190 */             if (StringUtils.isNotEmpty(strIssueScopeId[i])) {
/* 191 */               Long depId = new Long(strIssueScopeId[i]);
/* 192 */               PlanAttend pa = new PlanAttend();
/* 193 */               pa.setDepartment((Department)this.departmentService.get(depId));
/* 194 */               pa.setWorkPlan(this.workPlan);
/* 195 */               pa.setIsDep(Short.valueOf(ISDEPARTMENT));
/* 196 */               pa.setIsPrimary(Short.valueOf(NOTPRIMARY));
/* 197 */               this.planAttendService.save(pa);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 202 */       if (StringUtils.isNotEmpty(participantsIds)) {
/* 203 */         boolean b = this.planAttendService.deletePlanAttend(this.workPlan.getPlanId(), Short.valueOf(NOTDEPARTMENT), Short.valueOf(NOTPRIMARY));
/* 204 */         if (b) {
/* 205 */           String[] strParticipantsId = participantsIds.split(",");
/* 206 */           for (int i = 0; i < strParticipantsId.length; i++) {
/* 207 */             if (StringUtils.isNotEmpty(strParticipantsId[i])) {
/* 208 */               Long userId = new Long(strParticipantsId[i]);
/* 209 */               PlanAttend pa = new PlanAttend();
/* 210 */               pa.setAppUser((AppUser)this.appUserService.get(userId));
/* 211 */               pa.setIsDep(Short.valueOf(NOTDEPARTMENT));
/* 212 */               pa.setWorkPlan(this.workPlan);
/* 213 */               pa.setIsPrimary(Short.valueOf(NOTPRIMARY));
/* 214 */               this.planAttendService.save(pa);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 219 */       if (StringUtils.isNotEmpty(principalIds)) {
/* 220 */         boolean b = this.planAttendService.deletePlanAttend(this.workPlan.getPlanId(), Short.valueOf(NOTDEPARTMENT), Short.valueOf(ISPRIMARY));
/* 221 */         if (b) {
/* 222 */           String[] strPrincipalId = principalIds.split(",");
/* 223 */           for (int i = 0; i < strPrincipalId.length; i++) {
/* 224 */             if (StringUtils.isNotEmpty(strPrincipalId[i])) {
/* 225 */               Long userId = new Long(strPrincipalId[i]);
/* 226 */               PlanAttend pa = new PlanAttend();
/* 227 */               pa.setAppUser((AppUser)this.appUserService.get(userId));
/* 228 */               pa.setIsDep(Short.valueOf(NOTDEPARTMENT));
/* 229 */               pa.setWorkPlan(this.workPlan);
/* 230 */               pa.setIsPrimary(Short.valueOf(ISPRIMARY));
/* 231 */               this.planAttendService.save(pa);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 237 */     setJsonString("{success:true}");
/* 238 */     return "success";
/*     */   }
/*     */ 
/*     */   public String show()
/*     */   {
/* 245 */     String strPlanId = getRequest().getParameter("planId");
/* 246 */     if (StringUtils.isNotEmpty(strPlanId)) {
/* 247 */       Long planId = new Long(strPlanId);
/* 248 */       this.workPlan = ((WorkPlan)this.workPlanService.get(planId));
/*     */     }
/* 250 */     return "show";
/*     */   }
/*     */ 
/*     */   public String display()
/*     */   {
/* 258 */     QueryFilter filter = new QueryFilter(getRequest());
/* 259 */     Long userId = ContextUtil.getCurrentUserId();
/* 260 */     filter.addFilter("Q_appUser.userId_L_EQ", userId.toString());
/* 261 */     filter.addFilter("Q_isPersonal_SN_EQ", "1");
/* 262 */     filter.addFilter("Q_status_SN_EQ", "1");
/* 263 */     filter.addSorted("planId", "desc");
/* 264 */     List list = this.workPlanService.getAll(filter);
/* 265 */     getRequest().setAttribute("planList", list);
/* 266 */     return "display";
/*     */   }
/*     */ 
/*     */   public String displayDep()
/*     */   {
/* 274 */     PagingBean pb = new PagingBean(0, 8);
/* 275 */     AppUser user = ContextUtil.getCurrentUser();
/* 276 */     List list = this.workPlanService.findByDepartment(this.workPlan, user, pb);
/* 277 */     getRequest().setAttribute("planList", list);
/* 278 */     return "displayDep";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.task.WorkPlanAction
 * JD-Core Version:    0.6.0
 */
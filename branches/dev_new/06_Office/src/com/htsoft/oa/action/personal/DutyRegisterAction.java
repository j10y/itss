/*     */ package com.htsoft.oa.action.personal;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.util.DateUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.personal.Duty;
/*     */ import com.htsoft.oa.model.personal.DutyRegister;
/*     */ import com.htsoft.oa.model.personal.DutySection;
/*     */ import com.htsoft.oa.model.personal.DutySystem;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.service.personal.DutyRegisterService;
/*     */ import com.htsoft.oa.service.personal.DutySectionService;
/*     */ import com.htsoft.oa.service.personal.DutyService;
/*     */ import com.htsoft.oa.service.personal.DutySystemService;
/*     */ import com.htsoft.oa.service.system.AppUserService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class DutyRegisterAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DutyRegisterService dutyRegisterService;
/*     */ 
/*     */   @Resource
/*     */   private DutyService dutyService;
/*     */ 
/*     */   @Resource
/*     */   private DutySystemService dutySystemService;
/*     */ 
/*     */   @Resource
/*     */   private DutySectionService dutySectionService;
/*     */ 
/*     */   @Resource
/*     */   private AppUserService appUserService;
/*     */   private DutyRegister dutyRegister;
/*     */   private Long registerId;
/*     */ 
/*     */   public Long getRegisterId()
/*     */   {
/*  56 */     return this.registerId;
/*     */   }
/*     */ 
/*     */   public void setRegisterId(Long registerId) {
/*  60 */     this.registerId = registerId;
/*     */   }
/*     */ 
/*     */   public DutyRegister getDutyRegister() {
/*  64 */     return this.dutyRegister;
/*     */   }
/*     */ 
/*     */   public void setDutyRegister(DutyRegister dutyRegister) {
/*  68 */     this.dutyRegister = dutyRegister;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  76 */     QueryFilter filter = new QueryFilter(getRequest());
/*  77 */     List<DutyRegister> list = this.dutyRegisterService.getAll(filter);
/*     */ 
/*  79 */     Type type = new TypeToken<List<DutyRegister>>() {  }
/*  79 */     .getType();
/*  80 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  81 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  83 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
/*  84 */       .excludeFieldsWithoutExposeAnnotation().create();
/*  85 */     buff.append(gson.toJson(list, type));
/*  86 */     buff.append("}");
/*     */ 
/*  88 */     this.jsonString = buff.toString();
/*     */ 
/*  90 */     return "success";
/*     */   }
/*     */ 
/*     */   public String person()
/*     */   {
/*  97 */     QueryFilter filter = new QueryFilter(getRequest());
/*     */ 
/*  99 */     filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/* 100 */     filter.addSorted("registerDate", "desc");
/*     */ 
/* 102 */     List<DutyRegister> list = this.dutyRegisterService.getAll(filter);
/*     */ 
/* 104 */     Type type = new TypeToken<List<DutyRegister>>() {  }
/* 104 */     .getType();
/* 105 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/* 106 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/* 108 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
/* 109 */       .excludeFieldsWithoutExposeAnnotation().create();
/*     */ 
/* 111 */     buff.append(gson.toJson(list, type));
/* 112 */     buff.append("}");
/*     */ 
/* 114 */     this.jsonString = buff.toString();
/*     */ 
/* 116 */     return "success";
/*     */   }
/*     */ 
/*     */   public String today()
/*     */   {
/* 124 */     StringBuffer sb = new StringBuffer();
/*     */ 
/* 126 */     DutySystem dutySystem = null;
/*     */ 
/* 128 */     Duty duty = this.dutyService.getCurUserDuty(ContextUtil.getCurrentUserId());
/*     */ 
/* 130 */     if (duty != null)
/* 131 */       dutySystem = duty.getDutySystem();
/*     */     else {
/* 133 */       dutySystem = this.dutySystemService.getDefaultDutySystem();
/*     */     }
/*     */ 
/* 136 */     if (dutySystem == null) {
/* 137 */       setJsonString("{success:true,exception:'尚未为用户设置排班，请联系管理员!'}");
/*     */     }
/*     */     else {
/* 140 */       AppUser curUser = ContextUtil.getCurrentUser();
/*     */ 
/* 142 */       String dutySetting = dutySystem.getSystemSetting();
/*     */ 
/* 144 */       String[] day7Sections = dutySetting.split("[|]");
/*     */ 
/* 146 */       Calendar curCal = Calendar.getInstance();
/*     */ 
/* 149 */       int curDay = curCal.get(7);
/*     */ 
/* 151 */       String[] curDaySections = day7Sections[(curDay - 1)].split("[,]");
/*     */ 
/* 153 */       sb.append("{success:true,result:[");
/* 154 */       for (int i = 0; i < curDaySections.length; i++) {
/* 155 */         if ("-".equals(curDaySections[i])) {
/*     */           continue;
/*     */         }
/* 158 */         DutySection dutySection = (DutySection)this.dutySectionService.get(new Long(curDaySections[i]));
/*     */ 
/* 161 */         DutyRegister signInReg = this.dutyRegisterService.getTodayUserRegister(curUser.getUserId(), DutyRegister.SIGN_IN, dutySection.getSectionId());
/* 162 */         DutyRegister signOffReg = this.dutyRegisterService.getTodayUserRegister(curUser.getUserId(), DutyRegister.SIGN_OFF, dutySection.getSectionId());
/* 163 */         if (i > 0) sb.append(",");
/* 164 */         sb.append("{sectionId:'").append(dutySection.getSectionId())
/* 165 */           .append("',systemName:'").append(dutySection.getSectionName())
/* 166 */           .append("',startSignin:'").append(dutySection.getStartSignin1())
/* 167 */           .append("',dutyStartTime:'").append(dutySection.getDutyStartTime1())
/* 168 */           .append("',endSignin:'").append(dutySection.getEndSignin1())
/* 169 */           .append("',earlyOffTime:'").append(dutySection.getEarlyOffTime1())
/* 170 */           .append("',dutyEndTime:'").append(dutySection.getDutyEndTime1())
/* 171 */           .append("',signOutTime:'").append(dutySection.getSignOutTime1()).append("'");
/*     */ 
/* 175 */         if (signInReg != null) {
/* 176 */           sb.append(",signInTime:'").append(signInReg.getRegisterTime())
/* 177 */             .append("',signInFlag:'").append(signInReg.getRegFlag())
/* 178 */             .append("',allowSignIn:'0'");
/*     */         } else {
/* 180 */           sb.append(",signInTime:'',signInFlag:''");
/*     */ 
/* 182 */           Calendar startSignInCal = Calendar.getInstance();
/* 183 */           startSignInCal.setTime(dutySection.getStartSignin());
/* 184 */           DateUtil.copyYearMonthDay(startSignInCal, curCal);
/*     */ 
/* 186 */           Calendar endSignInCal = Calendar.getInstance();
/* 187 */           endSignInCal.setTime(dutySection.getEndSignin());
/* 188 */           DateUtil.copyYearMonthDay(endSignInCal, curCal);
/*     */ 
/* 190 */           int startCmpResult = curCal.compareTo(startSignInCal);
/* 191 */           int endCmpResult = curCal.compareTo(endSignInCal);
/* 192 */           if ((startCmpResult >= 0) && (endCmpResult <= 0))
/* 193 */             sb.append(",allowSignIn:'1'");
/* 194 */           else if (startCmpResult < 0)
/* 195 */             sb.append(",allowSignIn:'-1'");
/*     */           else {
/* 197 */             sb.append(",allowSignIn:'0'");
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 202 */         if (signOffReg != null) {
/* 203 */           sb.append(",signOffTime:'").append(signOffReg.getRegisterTime())
/* 204 */             .append("',signOffFlag:'").append(signOffReg.getRegFlag())
/* 205 */             .append("',allowSignOff:'0'");
/*     */         } else {
/* 207 */           sb.append(",signOffTime:'',signOffFlag:''");
/*     */ 
/* 210 */           Calendar startSignOffCal = Calendar.getInstance();
/* 211 */           startSignOffCal.setTime(dutySection.getEarlyOffTime());
/* 212 */           DateUtil.copyYearMonthDay(startSignOffCal, curCal);
/*     */ 
/* 214 */           Calendar endSignOffCal = Calendar.getInstance();
/* 215 */           endSignOffCal.setTime(dutySection.getSignOutTime());
/* 216 */           DateUtil.copyYearMonthDay(endSignOffCal, curCal);
/*     */ 
/* 218 */           int startCmpResult = curCal.compareTo(startSignOffCal);
/* 219 */           int endCmpResult = curCal.compareTo(endSignOffCal);
/*     */ 
/* 221 */           if ((startCmpResult >= 0) && (endCmpResult <= 0))
/* 222 */             sb.append(",allowSignOff:'1'");
/* 223 */           else if (startCmpResult < 0)
/* 224 */             sb.append(",allowSignOff:'-1'");
/*     */           else {
/* 226 */             sb.append(",allowSignOff:'0'");
/*     */           }
/*     */         }
/* 229 */         sb.append("}");
/*     */       }
/* 231 */       sb.append("]}");
/*     */ 
/* 233 */       setJsonString(sb.toString());
/*     */     }
/* 235 */     this.dutySystemService.evict(dutySystem);
/*     */ 
/* 237 */     return "success";
/*     */   }
/*     */ 
/*     */   public String signIn()
/*     */   {
/* 244 */     String sectionId = getRequest().getParameter("sectionId");
/* 245 */     this.dutyRegisterService.signInOff(new Long(sectionId), DutyRegister.SIGN_IN, ContextUtil.getCurrentUser(), new Date());
/* 246 */     this.jsonString = "{success:true}";
/* 247 */     return "success";
/*     */   }
/*     */ 
/*     */   public String signOff()
/*     */   {
/* 254 */     String sectionId = getRequest().getParameter("sectionId");
/* 255 */     this.dutyRegisterService.signInOff(new Long(sectionId), DutyRegister.SIGN_OFF, ContextUtil.getCurrentUser(), new Date());
/* 256 */     this.jsonString = "{success:true}";
/* 257 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/* 266 */     String[] ids = getRequest().getParameterValues("ids");
/* 267 */     if (ids != null) {
/* 268 */       for (String id : ids) {
/* 269 */         this.dutyRegisterService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/* 273 */     this.jsonString = "{success:true}";
/*     */ 
/* 275 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 283 */     DutyRegister dutyRegister = (DutyRegister)this.dutyRegisterService.get(this.registerId);
/*     */ 
/* 285 */     Gson gson = new Gson();
/*     */ 
/* 287 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 288 */     sb.append(gson.toJson(dutyRegister));
/* 289 */     sb.append("}");
/* 290 */     setJsonString(sb.toString());
/*     */ 
/* 292 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 299 */     HttpServletRequest request = getRequest();
/*     */ 
/* 301 */     String userIds = request.getParameter("userIds");
/* 302 */     Long sectionId = new Long(request.getParameter("sectionId"));
/* 303 */     Short inOffFlag = new Short(request.getParameter("inOffFlag"));
/*     */ 
/* 305 */     Date registerDate = DateUtil.parseDate(request.getParameter("registerDate"));
/*     */ 
/* 307 */     String[] uIds = userIds.split("[,]");
/* 308 */     for (int i = 0; i < uIds.length; i++) {
/* 309 */       AppUser appUser = (AppUser)this.appUserService.get(new Long(uIds[i]));
/* 310 */       this.dutyRegisterService.signInOff(sectionId, inOffFlag, appUser, registerDate);
/*     */     }
/* 312 */     setJsonString("{success:true}");
/* 313 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.personal.DutyRegisterAction
 * JD-Core Version:    0.6.0
 */
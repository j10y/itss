/*     */ package com.htsoft.oa.action.task;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.ContextUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.task.Appointment;
/*     */ import com.htsoft.oa.service.system.AppUserService;
/*     */ import com.htsoft.oa.service.task.AppointmentService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ public class AppointmentAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private AppointmentService appointmentService;
/*     */   private Appointment appointment;
/*     */ 
/*     */   @Resource
/*     */   private AppUserService appUserService;
/*     */   private Long appointId;
/*     */   private List<Appointment> list;
/*     */ 
/*     */   public List<Appointment> getList()
/*     */   {
/*  34 */     return this.list;
/*     */   }
/*     */ 
/*     */   public void setList(List<Appointment> list) {
/*  38 */     this.list = list;
/*     */   }
/*     */ 
/*     */   public Long getAppointId() {
/*  42 */     return this.appointId;
/*     */   }
/*     */ 
/*     */   public void setAppointId(Long appointId) {
/*  46 */     this.appointId = appointId;
/*     */   }
/*     */ 
/*     */   public Appointment getAppointment() {
/*  50 */     return this.appointment;
/*     */   }
/*     */ 
/*     */   public void setAppointment(Appointment appointment) {
/*  54 */     this.appointment = appointment;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  62 */     QueryFilter filter = new QueryFilter(getRequest());
/*  63 */     filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/*  64 */     List<Appointment> list = this.appointmentService.getAll(filter);
/*     */ 
/*  66 */     Type type = new TypeToken<List<Appointment>>() {  }
/*  66 */     .getType();
/*  67 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  68 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  70 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();
/*  71 */     buff.append(gson.toJson(list, type));
/*  72 */     buff.append("}");
/*     */ 
/*  74 */     this.jsonString = buff.toString();
/*     */ 
/*  76 */     return "success";
/*     */   }
/*     */ 
/*     */   public String multiDel()
/*     */   {
/*  84 */     String[] ids = getRequest().getParameterValues("ids");
/*  85 */     if (ids != null) {
/*  86 */       for (String id : ids) {
/*  87 */         this.appointmentService.remove(new Long(id));
/*     */       }
/*     */     }
/*     */ 
/*  91 */     this.jsonString = "{success:true}";
/*     */ 
/*  93 */     return "success";
/*     */   }
/*     */ 
/*     */   public String get()
/*     */   {
/* 101 */     Appointment appointment = (Appointment)this.appointmentService.get(this.appointId);
/*     */ 
/* 103 */     Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
/*     */ 
/* 105 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 106 */     sb.append(gson.toJson(appointment));
/* 107 */     sb.append("}");
/* 108 */     setJsonString(sb.toString());
/*     */ 
/* 110 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 116 */     this.appointment.setAppUser(ContextUtil.getCurrentUser());
/* 117 */     this.appointmentService.save(this.appointment);
/* 118 */     setJsonString("{success:true}");
/* 119 */     return "success";
/*     */   }
/*     */ 
/*     */   public String display()
/*     */   {
/* 128 */     QueryFilter filter = new QueryFilter(getRequest());
/* 129 */     filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
/* 130 */     filter.addSorted("appointId", "desc");
/* 131 */     List list = this.appointmentService.getAll(filter);
/* 132 */     getRequest().setAttribute("appointmentList", list);
/* 133 */     return "display";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.task.AppointmentAction
 * JD-Core Version:    0.6.0
 */
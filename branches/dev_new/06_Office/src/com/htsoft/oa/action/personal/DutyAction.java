/*     */ package com.htsoft.oa.action.personal;
/*     */ 
/*     */ import com.htsoft.core.command.QueryFilter;
/*     */ import com.htsoft.core.util.BeanUtil;
/*     */ import com.htsoft.core.util.JsonUtil;
/*     */ import com.htsoft.core.web.action.BaseAction;
/*     */ import com.htsoft.core.web.paging.PagingBean;
/*     */ import com.htsoft.oa.model.personal.Duty;
/*     */ import com.htsoft.oa.model.personal.DutySystem;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.service.personal.DutyService;
/*     */ import com.htsoft.oa.service.personal.DutySystemService;
/*     */ import com.htsoft.oa.service.system.AppUserService;
/*     */ import flexjson.JSONSerializer;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.logging.Log;
/*     */ 
/*     */ public class DutyAction extends BaseAction
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DutyService dutyService;
/*     */ 
/*     */   @Resource
/*     */   private DutySystemService dutySystemService;
/*     */ 
/*     */   @Resource
/*     */   private AppUserService appUserService;
/*     */   private Duty duty;
/*     */   private Long dutyId;
/*     */ 
/*     */   public Long getDutyId()
/*     */   {
/*  44 */     return this.dutyId;
/*     */   }
/*     */ 
/*     */   public void setDutyId(Long dutyId) {
/*  48 */     this.dutyId = dutyId;
/*     */   }
/*     */ 
/*     */   public Duty getDuty() {
/*  52 */     return this.duty;
/*     */   }
/*     */ 
/*     */   public void setDuty(Duty duty) {
/*  56 */     this.duty = duty;
/*     */   }
/*     */ 
/*     */   public String list()
/*     */   {
/*  64 */     QueryFilter filter = new QueryFilter(getRequest());
/*  65 */     List list = this.dutyService.getAll(filter);
/*     */ 
/*  67 */     StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
/*  68 */       .append(filter.getPagingBean().getTotalItems()).append(",result:");
/*     */ 
/*  70 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "startTime", "endTime" });
/*  71 */     buff.append(serializer.serialize(list));
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
/*  87 */         this.dutyService.remove(new Long(id));
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
/* 101 */     Duty duty = (Duty)this.dutyService.get(this.dutyId);
/* 102 */     JSONSerializer serializer = JsonUtil.getJSONSerializer(new String[] { "startTime", "endTime" });
/*     */ 
/* 105 */     StringBuffer sb = new StringBuffer("{success:true,data:");
/* 106 */     sb.append(serializer.serialize(duty));
/* 107 */     sb.append("}");
/* 108 */     setJsonString(sb.toString());
/*     */ 
/* 110 */     return "success";
/*     */   }
/*     */ 
/*     */   public String save()
/*     */   {
/* 117 */     String systemId = getRequest().getParameter("dutySystemId");
/*     */ 
/* 119 */     if (StringUtils.isNotEmpty(systemId)) {
/* 120 */       DutySystem dutySystem = (DutySystem)this.dutySystemService.get(new Long(systemId));
/* 121 */       this.duty.setDutySystem(dutySystem);
/*     */     }
/*     */ 
/* 124 */     String userId = getRequest().getParameter("duty.userId");
/*     */ 
/* 126 */     String[] uIds = userId.split("[,]");
/*     */ 
/* 129 */     StringBuffer ssb = new StringBuffer("");
/* 130 */     boolean isExcept = false;
/* 131 */     if (uIds != null) {
/* 132 */       for (int i = 0; i < uIds.length; i++) {
/* 133 */         AppUser user = (AppUser)this.appUserService.get(new Long(uIds[i]));
/* 134 */         Duty uDuty = new Duty();
/*     */         try
/*     */         {
/* 137 */           BeanUtil.copyNotNullProperties(uDuty, this.duty);
/*     */ 
/* 139 */           boolean isExist = false;
/* 140 */           if (uDuty.getDutyId() == null) {
/* 141 */             isExist = this.dutyService.isExistDutyForUser(user.getUserId(), uDuty.getStartTime(), uDuty.getEndTime());
/*     */           }
/* 143 */           if (isExist) {
/* 144 */             isExcept = true;
/* 145 */             ssb.append(user.getFullname()).append(",");
/*     */           } else {
/* 147 */             uDuty.setAppUser(user);
/* 148 */             uDuty.setFullname(user.getFullname());
/* 149 */             this.dutyService.save(uDuty);
/*     */           }
/*     */         }
/*     */         catch (Exception ex) {
/* 153 */           this.logger.error("error:" + ex.getMessage());
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 159 */     if (isExcept) {
/* 160 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 161 */       ssb.append("在该时间(").append(sdf.format(this.duty.getStartTime())).append("至")
/* 162 */         .append(sdf.format(this.duty.getEndTime())).append(")内已经存在班制!");
/*     */     }
/*     */ 
/* 165 */     setJsonString("{success:true,exception:'" + ssb.toString() + "'}");
/* 166 */     return "success";
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.action.personal.DutyAction
 * JD-Core Version:    0.6.0
 */
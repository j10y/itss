/*     */ package com.htsoft.oa.service.personal.impl;
/*     */ 
/*     */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*     */ import com.htsoft.core.util.DateUtil;
/*     */ import com.htsoft.oa.dao.personal.DutyRegisterDao;
/*     */ import com.htsoft.oa.dao.personal.DutySectionDao;
/*     */ import com.htsoft.oa.model.personal.DutyRegister;
/*     */ import com.htsoft.oa.model.personal.DutySection;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.oa.service.personal.DutyRegisterService;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import javax.annotation.Resource;
/*     */ 
/*     */ public class DutyRegisterServiceImpl extends BaseServiceImpl<DutyRegister>
/*     */   implements DutyRegisterService
/*     */ {
/*     */   private DutyRegisterDao dao;
/*     */ 
/*     */   @Resource
/*     */   private DutySectionDao dutySectionDao;
/*     */ 
/*     */   public DutyRegisterServiceImpl(DutyRegisterDao dao)
/*     */   {
/*  33 */     super(dao);
/*  34 */     this.dao = dao;
/*     */   }
/*     */ 
/*     */   public void signInOff(Long sectionId, Short signInOff, AppUser curUser, Date registerDate)
/*     */   {
/*  42 */     DutySection dutySection = (DutySection)this.dutySectionDao.get(sectionId);
/*     */ 
/*  47 */     DutyRegister dutyRegister = this.dao.getTodayUserRegister(curUser.getUserId(), signInOff, sectionId);
/*  48 */     if (dutyRegister != null) {
/*  49 */       return;
/*     */     }
/*     */ 
/*  52 */     DutyRegister register = new DutyRegister();
/*  53 */     register.setAppUser(curUser);
/*  54 */     register.setFullname(curUser.getFullname());
/*     */ 
/*  56 */     Calendar regiserCal = Calendar.getInstance();
/*  57 */     regiserCal.setTime(registerDate);
/*  58 */     register.setRegisterDate(registerDate);
/*  59 */     register.setDayOfWeek(Integer.valueOf(regiserCal.get(7)));
/*  60 */     register.setInOffFlag(signInOff);
/*     */ 
/*  62 */     register.setDutySection(dutySection);
/*     */ 
/*  65 */     Calendar startCalendar = Calendar.getInstance();
/*  66 */     if (DutyRegister.SIGN_IN.equals(signInOff))
/*  67 */       startCalendar.setTime(dutySection.getDutyStartTime());
/*     */     else {
/*  69 */       startCalendar.setTime(dutySection.getDutyEndTime());
/*     */     }
/*     */ 
/*  72 */     DateUtil.copyYearMonthDay(startCalendar, regiserCal);
/*     */ 
/*  75 */     if (DutyRegister.SIGN_IN.equals(signInOff)) {
/*  76 */       if (regiserCal.compareTo(startCalendar) > 0) {
/*  77 */         register.setRegFlag(DutyRegister.REG_FLAG_LATE);
/*     */ 
/*  79 */         Long minis = Long.valueOf((regiserCal.getTimeInMillis() - startCalendar.getTimeInMillis()) / 60000L);
/*  80 */         register.setRegMins(Integer.valueOf(minis.intValue()));
/*     */       } else {
/*  82 */         register.setRegFlag(DutyRegister.REG_FLAG_NORMAL);
/*  83 */         register.setRegMins(Integer.valueOf(0));
/*     */       }
/*     */     }
/*  86 */     else if (regiserCal.compareTo(startCalendar) < 0) {
/*  87 */       register.setRegFlag(DutyRegister.REG_FLAG_EARLY_OFF);
/*     */ 
/*  89 */       Long minis = Long.valueOf((startCalendar.getTimeInMillis() - regiserCal.getTimeInMillis()) / 60000L);
/*  90 */       register.setRegMins(Integer.valueOf(minis.intValue()));
/*     */     } else {
/*  92 */       register.setRegFlag(DutyRegister.REG_FLAG_NORMAL);
/*  93 */       register.setRegMins(Integer.valueOf(0));
/*     */     }
/*     */ 
/*  97 */     save(register);
/*     */   }
/*     */ 
/*     */   public DutyRegister getTodayUserRegister(Long userId, Short inOffFlag, Long sectionId)
/*     */   {
/* 108 */     return this.dao.getTodayUserRegister(userId, inOffFlag, sectionId);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.personal.impl.DutyRegisterServiceImpl
 * JD-Core Version:    0.6.0
 */
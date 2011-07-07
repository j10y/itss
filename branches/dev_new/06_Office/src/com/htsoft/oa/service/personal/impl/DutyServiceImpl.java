/*    */ package com.htsoft.oa.service.personal.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.personal.DutyDao;
/*    */ import com.htsoft.oa.model.personal.Duty;
/*    */ import com.htsoft.oa.service.personal.DutyService;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DutyServiceImpl extends BaseServiceImpl<Duty>
/*    */   implements DutyService
/*    */ {
/*    */   private DutyDao dao;
/*    */ 
/*    */   public DutyServiceImpl(DutyDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public boolean isExistDutyForUser(Long userId, Date startTime, Date endTime)
/*    */   {
/* 27 */     List dutyList = this.dao.getUserDutyByTime(userId, startTime, endTime);
/* 28 */     return dutyList.size() > 0;
/*    */   }
/*    */ 
/*    */   public Duty getCurUserDuty(Long userId)
/*    */   {
/* 38 */     List dutyList = this.dao.getCurUserDuty(userId);
/* 39 */     if (dutyList.size() > 0) {
/* 40 */       return (Duty)dutyList.get(0);
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.personal.impl.DutyServiceImpl
 * JD-Core Version:    0.6.0
 */
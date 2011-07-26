/*    */ package com.xpsoft.oa.service.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.task.CalendarPlanDao;
/*    */ import com.xpsoft.oa.model.task.CalendarPlan;
/*    */ import com.xpsoft.oa.service.task.CalendarPlanService;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CalendarPlanServiceImpl extends BaseServiceImpl<CalendarPlan>
/*    */   implements CalendarPlanService
/*    */ {
/*    */   private CalendarPlanDao dao;
/*    */ 
/*    */   public CalendarPlanServiceImpl(CalendarPlanDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<CalendarPlan> getTodayPlans(Long userId, PagingBean pb)
/*    */   {
/* 30 */     return this.dao.getTodayPlans(userId, pb);
/*    */   }
/*    */ 
/*    */   public List<CalendarPlan> getByPeriod(Long userId, Date startTime, Date endTime)
/*    */   {
/* 38 */     return this.dao.getByPeriod(userId, startTime, endTime);
/*    */   }
/*    */ 
/*    */   public List showCalendarPlanByUserId(Long userId, PagingBean pb)
/*    */   {
/* 44 */     return this.dao.showCalendarPlanByUserId(userId, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.task.impl.CalendarPlanServiceImpl
 * JD-Core Version:    0.6.0
 */
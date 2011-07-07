/*    */ package com.htsoft.oa.dao.task.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.core.web.paging.PagingBean;
/*    */ import com.htsoft.oa.dao.task.CalendarPlanDao;
/*    */ import com.htsoft.oa.model.task.CalendarPlan;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CalendarPlanDaoImpl extends BaseDaoImpl<CalendarPlan>
/*    */   implements CalendarPlanDao
/*    */ {
/*    */   public CalendarPlanDaoImpl()
/*    */   {
/* 18 */     super(CalendarPlan.class);
/*    */   }
/*    */ 
/*    */   public List<CalendarPlan> getTodayPlans(Long userId, PagingBean pb)
/*    */   {
/* 28 */     String hql = "from CalendarPlan cp where cp.userId=? and cp.startTime<=? and cp.endTime>=?";
/*    */ 
/* 30 */     Date curDate = new Date();
/*    */ 
/* 32 */     return findByHql(hql, new Object[] { userId, curDate, curDate }, pb);
/*    */   }
/*    */ 
/*    */   public List<CalendarPlan> getByPeriod(Long userId, Date startTime, Date endTime)
/*    */   {
/* 39 */     String hql = "from CalendarPlan cp where cp.userId=? and ((cp.startTime>=? and cp.startTime<=?) or (cp.endTime>=? and cp.endTime<=?)) order by cp.planId desc";
/* 40 */     return findByHql(hql, new Object[] { userId, startTime, endTime, startTime, endTime });
/*    */   }
/*    */ 
/*    */   public List showCalendarPlanByUserId(Long userId, PagingBean pb)
/*    */   {
/* 46 */     ArrayList paramList = new ArrayList();
/* 47 */     StringBuffer hql = new StringBuffer("select vo from CalendarPlan vo where vo.userId=?");
/* 48 */     paramList.add(userId);
/* 49 */     hql.append(" order by planId desc");
/* 50 */     return findByHql(hql.toString(), paramList.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.task.impl.CalendarPlanDaoImpl
 * JD-Core Version:    0.6.0
 */
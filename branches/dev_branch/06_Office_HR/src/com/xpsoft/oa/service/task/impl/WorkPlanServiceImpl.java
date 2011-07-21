/*    */ package com.xpsoft.oa.service.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.task.WorkPlanDao;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.model.task.WorkPlan;
/*    */ import com.xpsoft.oa.service.task.WorkPlanService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class WorkPlanServiceImpl extends BaseServiceImpl<WorkPlan>
/*    */   implements WorkPlanService
/*    */ {
/*    */   private WorkPlanDao dao;
/*    */ 
/*    */   public WorkPlanServiceImpl(WorkPlanDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<WorkPlan> findByDepartment(WorkPlan workPlan, AppUser user, PagingBean pb)
/*    */   {
/* 25 */     return this.dao.findByDepartment(workPlan, user, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.task.impl.WorkPlanServiceImpl
 * JD-Core Version:    0.6.0
 */
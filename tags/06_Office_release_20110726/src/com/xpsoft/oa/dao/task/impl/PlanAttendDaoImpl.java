/*    */ package com.xpsoft.oa.dao.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.task.PlanAttendDao;
/*    */ import com.xpsoft.oa.model.task.PlanAttend;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PlanAttendDaoImpl extends BaseDaoImpl<PlanAttend>
/*    */   implements PlanAttendDao
/*    */ {
/*    */   public PlanAttendDaoImpl()
/*    */   {
/* 16 */     super(PlanAttend.class);
/*    */   }
/*    */ 
/*    */   public List<PlanAttend> FindPlanAttend(Long planId, Short isDep, Short isPrimary)
/*    */   {
/* 21 */     StringBuffer hql = new StringBuffer("from PlanAttend vo where vo.workPlan.planId=?");
/* 22 */     ArrayList list = new ArrayList();
/* 23 */     list.add(planId);
/* 24 */     if (isDep != null) {
/* 25 */       hql.append(" and vo.isDep=?");
/* 26 */       list.add(isDep);
/*    */     }
/* 28 */     if (isPrimary != null) {
/* 29 */       hql.append(" and vo.isPrimary=?");
/* 30 */       list.add(isPrimary);
/*    */     }
/* 32 */     return findByHql(hql.toString(), list.toArray());
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.task.impl.PlanAttendDaoImpl
 * JD-Core Version:    0.6.0
 */
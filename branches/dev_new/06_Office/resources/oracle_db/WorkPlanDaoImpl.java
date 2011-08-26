/*    */ package com.xpsoft.oa.dao.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.task.WorkPlanDao;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.model.system.Department;
/*    */ import com.xpsoft.oa.model.task.PlanType;
/*    */ import com.xpsoft.oa.model.task.WorkPlan;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class WorkPlanDaoImpl extends BaseDaoImpl<WorkPlan>
/*    */   implements WorkPlanDao
/*    */ {
/*    */   public WorkPlanDaoImpl()
/*    */   {
/* 22 */     super(WorkPlan.class);
/*    */   }
/*    */ 
/*    */   public List<WorkPlan> findByDepartment(WorkPlan workPlan, AppUser user, PagingBean pb)
/*    */   {
/* 27 */     StringBuffer sb = new StringBuffer();
/* 28 */     ArrayList list = new ArrayList();
/* 29 */     if (!user.getRights().contains("__ALL")) {
/* 30 */       sb.append("select wp from WorkPlan wp,PlanAttend pa where pa.workPlan=wp and wp.status=1 and wp.isPersonal=0 and ((pa.appUser.userId=? and pa.isDep=0)");
/* 31 */       Department dep = user.getDepartment();
/* 32 */       list.add(user.getUserId());
/* 33 */       if (dep != null) {
/* 34 */         String path = dep.getPath();
/* 35 */         if (StringUtils.isNotEmpty(path)) {
/* 36 */           StringBuffer buff = new StringBuffer(path.replace(".", ","));
/* 37 */           buff.deleteCharAt(buff.length() - 1);
/* 38 */           sb.append(" or (pa.department.depId in (" + buff.toString() + ") and pa.isDep=1)");
/*    */         }
/*    */       }
/* 41 */       sb.append(")");
/*    */     } else {
/* 43 */       sb.append("select wp from WorkPlan wp where wp.status=1 and wp.isPersonal=0");
/*    */     }
/* 45 */     if (workPlan != null) {
/* 46 */       if (StringUtils.isNotEmpty(workPlan.getPlanName())) {
/* 47 */         sb.append(" and wp.planName like ?");
/* 48 */         list.add("%" + workPlan.getPlanName() + "%");
/*    */       }
/* 50 */       if (StringUtils.isNotEmpty(workPlan.getPrincipal())) {
/* 51 */         sb.append(" and wp.principal like ?");
/* 52 */         list.add("%" + workPlan.getPrincipal() + "%");
/*    */       }
/* 54 */       if ((workPlan.getPlanType() != null) && 
/* 55 */         (workPlan.getPlanType().getTypeId() != null)) {
/* 56 */         sb.append(" and wp.planType.typeId = ?");
/* 57 */         list.add(workPlan.getPlanType().getTypeId());
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 62 */     //sb.append(" group by wp.planId");
/* 63 */     return findByHql(sb.toString(), list.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.task.impl.WorkPlanDaoImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.dao.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.task.AppointmentDao;
/*    */ import com.xpsoft.oa.model.task.Appointment;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AppointmentDaoImpl extends BaseDaoImpl<Appointment>
/*    */   implements AppointmentDao
/*    */ {
/*    */   public AppointmentDaoImpl()
/*    */   {
/* 17 */     super(Appointment.class);
/*    */   }
/*    */ 
/*    */   public List showAppointmentByUserId(Long userId, PagingBean pb)
/*    */   {
/* 24 */     ArrayList paramList = new ArrayList();
/* 25 */     StringBuffer hql = new StringBuffer("select vo from Appointment vo where vo.appUser.userId=?");
/* 26 */     paramList.add(userId);
/* 27 */     hql.append(" order by vo.appointId desc");
/*    */ 
/* 29 */     return findByHql(hql.toString(), paramList.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.task.impl.AppointmentDaoImpl
 * JD-Core Version:    0.6.0
 */
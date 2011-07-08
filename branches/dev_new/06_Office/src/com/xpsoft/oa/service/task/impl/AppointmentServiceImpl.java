/*    */ package com.xpsoft.oa.service.task.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.task.AppointmentDao;
/*    */ import com.xpsoft.oa.model.task.Appointment;
/*    */ import com.xpsoft.oa.service.task.AppointmentService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class AppointmentServiceImpl extends BaseServiceImpl<Appointment>
/*    */   implements AppointmentService
/*    */ {
/*    */   private AppointmentDao dao;
/*    */ 
/*    */   public AppointmentServiceImpl(AppointmentDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List showAppointmentByUserId(Long userId, PagingBean pb)
/*    */   {
/* 25 */     return this.dao.showAppointmentByUserId(userId, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.task.impl.AppointmentServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.oa.dao.personal.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.core.util.DateUtil;
/*    */ import com.htsoft.oa.dao.personal.DutyRegisterDao;
/*    */ import com.htsoft.oa.model.personal.DutyRegister;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DutyRegisterDaoImpl extends BaseDaoImpl<DutyRegister>
/*    */   implements DutyRegisterDao
/*    */ {
/*    */   public DutyRegisterDaoImpl()
/*    */   {
/* 18 */     super(DutyRegister.class);
/*    */   }
/*    */ 
/*    */   public DutyRegister getTodayUserRegister(Long userId, Short inOffFlag, Long sectionId)
/*    */   {
/* 28 */     String hql = "from DutyRegister dr where dr.appUser.userId=? and dr.registerDate>=? and dr.registerDate<=? and dr.inOffFlag=? and dr.dutySection.sectionId=?";
/* 29 */     Calendar cal = Calendar.getInstance();
/* 30 */     Date startTime = DateUtil.setStartDay(cal).getTime();
/* 31 */     Date endTime = DateUtil.setEndDay(cal).getTime();
/* 32 */     List list = findByHql(hql, new Object[] { userId, startTime, endTime, inOffFlag, sectionId });
/*    */ 
/* 34 */     if (list.size() > 0) {
/* 35 */       return (DutyRegister)list.get(0);
/*    */     }
/*    */ 
/* 38 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.personal.impl.DutyRegisterDaoImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.SystemLogDao;
/*    */ import com.xpsoft.oa.model.system.SystemLog;
/*    */ import com.xpsoft.oa.service.system.SystemLogService;
/*    */ 
/*    */ public class SystemLogServiceImpl extends BaseServiceImpl<SystemLog>
/*    */   implements SystemLogService
/*    */ {
/*    */   private SystemLogDao dao;
/*    */ 
/*    */   public SystemLogServiceImpl(SystemLogDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.SystemLogServiceImpl
 * JD-Core Version:    0.6.0
 */
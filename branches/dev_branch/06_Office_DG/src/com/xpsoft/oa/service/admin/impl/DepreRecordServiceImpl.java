/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.DepreRecordDao;
/*    */ import com.xpsoft.oa.model.admin.DepreRecord;
/*    */ import com.xpsoft.oa.service.admin.DepreRecordService;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class DepreRecordServiceImpl extends BaseServiceImpl<DepreRecord>
/*    */   implements DepreRecordService
/*    */ {
/*    */   private DepreRecordDao dao;
/*    */ 
/*    */   public DepreRecordServiceImpl(DepreRecordDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Date findMaxDate(Long assetsId)
/*    */   {
/* 23 */     return this.dao.findMaxDate(assetsId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.DepreRecordServiceImpl
 * JD-Core Version:    0.6.0
 */
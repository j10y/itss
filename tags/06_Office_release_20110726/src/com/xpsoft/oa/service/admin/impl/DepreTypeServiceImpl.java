/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.DepreTypeDao;
/*    */ import com.xpsoft.oa.model.admin.DepreType;
/*    */ import com.xpsoft.oa.service.admin.DepreTypeService;
/*    */ 
/*    */ public class DepreTypeServiceImpl extends BaseServiceImpl<DepreType>
/*    */   implements DepreTypeService
/*    */ {
/*    */   private DepreTypeDao dao;
/*    */ 
/*    */   public DepreTypeServiceImpl(DepreTypeDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.DepreTypeServiceImpl
 * JD-Core Version:    0.6.0
 */
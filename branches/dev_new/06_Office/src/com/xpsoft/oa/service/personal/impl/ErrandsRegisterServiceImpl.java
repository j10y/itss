/*    */ package com.xpsoft.oa.service.personal.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.personal.ErrandsRegisterDao;
/*    */ import com.xpsoft.oa.model.personal.ErrandsRegister;
/*    */ import com.xpsoft.oa.service.personal.ErrandsRegisterService;
/*    */ 
/*    */ public class ErrandsRegisterServiceImpl extends BaseServiceImpl<ErrandsRegister>
/*    */   implements ErrandsRegisterService
/*    */ {
/*    */   private ErrandsRegisterDao dao;
/*    */ 
/*    */   public ErrandsRegisterServiceImpl(ErrandsRegisterDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.personal.impl.ErrandsRegisterServiceImpl
 * JD-Core Version:    0.6.0
 */
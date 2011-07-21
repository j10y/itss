/*    */ package com.xpsoft.oa.service.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.admin.FixedAssetsDao;
/*    */ import com.xpsoft.oa.model.admin.FixedAssets;
/*    */ import com.xpsoft.oa.service.admin.FixedAssetsService;
/*    */ 
/*    */ public class FixedAssetsServiceImpl extends BaseServiceImpl<FixedAssets>
/*    */   implements FixedAssetsService
/*    */ {
/*    */   private FixedAssetsDao dao;
/*    */ 
/*    */   public FixedAssetsServiceImpl(FixedAssetsDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.impl.FixedAssetsServiceImpl
 * JD-Core Version:    0.6.0
 */
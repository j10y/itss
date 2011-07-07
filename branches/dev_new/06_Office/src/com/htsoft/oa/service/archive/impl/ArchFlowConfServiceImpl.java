/*    */ package com.htsoft.oa.service.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.archive.ArchFlowConfDao;
/*    */ import com.htsoft.oa.model.archive.ArchFlowConf;
/*    */ import com.htsoft.oa.service.archive.ArchFlowConfService;
/*    */ 
/*    */ public class ArchFlowConfServiceImpl extends BaseServiceImpl<ArchFlowConf>
/*    */   implements ArchFlowConfService
/*    */ {
/*    */   private ArchFlowConfDao dao;
/*    */ 
/*    */   public ArchFlowConfServiceImpl(ArchFlowConfDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public ArchFlowConf getByFlowType(Short archType)
/*    */   {
/* 21 */     return this.dao.getByFlowType(archType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.archive.impl.ArchFlowConfServiceImpl
 * JD-Core Version:    0.6.0
 */
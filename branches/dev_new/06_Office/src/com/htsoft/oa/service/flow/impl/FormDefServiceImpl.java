/*    */ package com.htsoft.oa.service.flow.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.flow.FormDefDao;
/*    */ import com.htsoft.oa.model.flow.FormDef;
/*    */ import com.htsoft.oa.service.flow.FormDefService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FormDefServiceImpl extends BaseServiceImpl<FormDef>
/*    */   implements FormDefService
/*    */ {
/*    */   private FormDefDao dao;
/*    */ 
/*    */   public FormDefServiceImpl(FormDefDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<FormDef> getByDeployId(String deployId)
/*    */   {
/* 26 */     return this.dao.getByDeployId(deployId);
/*    */   }
/*    */ 
/*    */   public FormDef getByDeployIdActivityName(String deployId, String activityName)
/*    */   {
/* 36 */     return this.dao.getByDeployIdActivityName(deployId, activityName);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.flow.impl.FormDefServiceImpl
 * JD-Core Version:    0.6.0
 */
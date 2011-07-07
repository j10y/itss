/*    */ package com.htsoft.oa.service.flow.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.flow.ProDefinitionDao;
/*    */ import com.htsoft.oa.model.flow.ProDefinition;
/*    */ import com.htsoft.oa.service.flow.ProDefinitionService;
/*    */ 
/*    */ public class ProDefinitionServiceImpl extends BaseServiceImpl<ProDefinition>
/*    */   implements ProDefinitionService
/*    */ {
/*    */   private ProDefinitionDao dao;
/*    */ 
/*    */   public ProDefinitionServiceImpl(ProDefinitionDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public ProDefinition getByDeployId(String deployId) {
/* 20 */     return this.dao.getByDeployId(deployId);
/*    */   }
/*    */ 
/*    */   public ProDefinition getByName(String name) {
/* 24 */     return this.dao.getByName(name);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.flow.impl.ProDefinitionServiceImpl
 * JD-Core Version:    0.6.0
 */
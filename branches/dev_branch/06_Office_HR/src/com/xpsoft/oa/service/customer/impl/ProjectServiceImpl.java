/*    */ package com.xpsoft.oa.service.customer.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.customer.ProjectDao;
/*    */ import com.xpsoft.oa.model.customer.Project;
/*    */ import com.xpsoft.oa.service.customer.ProjectService;
/*    */ 
/*    */ public class ProjectServiceImpl extends BaseServiceImpl<Project>
/*    */   implements ProjectService
/*    */ {
/*    */   private ProjectDao dao;
/*    */ 
/*    */   public ProjectServiceImpl(ProjectDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public boolean checkProjectNo(String projectNo)
/*    */   {
/* 21 */     return this.dao.checkProjectNo(projectNo);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.impl.ProjectServiceImpl
 * JD-Core Version:    0.6.0
 */
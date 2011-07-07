/*    */ package com.htsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.hrm.ResumeDao;
/*    */ import com.htsoft.oa.model.hrm.Resume;
/*    */ import com.htsoft.oa.service.hrm.ResumeService;
/*    */ 
/*    */ public class ResumeServiceImpl extends BaseServiceImpl<Resume>
/*    */   implements ResumeService
/*    */ {
/*    */   private ResumeDao dao;
/*    */ 
/*    */   public ResumeServiceImpl(ResumeDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.hrm.impl.ResumeServiceImpl
 * JD-Core Version:    0.6.0
 */
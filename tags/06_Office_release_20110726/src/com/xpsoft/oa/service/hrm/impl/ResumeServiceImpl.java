/*    */ package com.xpsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.hrm.ResumeDao;
/*    */ import com.xpsoft.oa.model.hrm.Resume;
/*    */ import com.xpsoft.oa.service.hrm.ResumeService;
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
 * Qualified Name:     com.xpsoft.oa.service.hrm.impl.ResumeServiceImpl
 * JD-Core Version:    0.6.0
 */
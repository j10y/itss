/*    */ package com.xpsoft.oa.service.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.hrm.EmpProfileDao;
/*    */ import com.xpsoft.oa.model.hrm.EmpProfile;
/*    */ import com.xpsoft.oa.service.hrm.EmpProfileService;
/*    */ 
/*    */ public class EmpProfileServiceImpl extends BaseServiceImpl<EmpProfile>
/*    */   implements EmpProfileService
/*    */ {
/*    */   private EmpProfileDao dao;
/*    */ 
/*    */   public EmpProfileServiceImpl(EmpProfileDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public boolean checkProfileNo(String profileNo)
/*    */   {
/* 21 */     return this.dao.checkProfileNo(profileNo);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.impl.EmpProfileServiceImpl
 * JD-Core Version:    0.6.0
 */
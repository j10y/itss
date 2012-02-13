/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.CompanyDao;
/*    */ import com.xpsoft.oa.model.system.Company;
/*    */ import com.xpsoft.oa.service.system.CompanyService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CompanyServiceImpl extends BaseServiceImpl<Company>
/*    */   implements CompanyService
/*    */ {
/*    */   private CompanyDao companyDao;
/*    */ 
/*    */   public CompanyServiceImpl(CompanyDao companyDao)
/*    */   {
/* 21 */     super(companyDao);
/* 22 */     this.companyDao = companyDao;
/*    */   }
/*    */ 
/*    */   public List<Company> findCompany()
/*    */   {
/* 28 */     return this.companyDao.findCompany();
/*    */   }
/*    */ 
/*    */   public List<Company> findByHql(String hql)
/*    */   {
/* 34 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.CompanyServiceImpl
 * JD-Core Version:    0.6.0
 */
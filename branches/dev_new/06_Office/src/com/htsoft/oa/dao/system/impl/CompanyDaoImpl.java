/*    */ package com.htsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.system.CompanyDao;
/*    */ import com.htsoft.oa.model.system.Company;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CompanyDaoImpl extends BaseDaoImpl<Company>
/*    */   implements CompanyDao
/*    */ {
/*    */   public CompanyDaoImpl()
/*    */   {
/* 15 */     super(Company.class);
/*    */   }
/*    */ 
/*    */   public List<Company> findCompany() {
/* 19 */     String hql = "from Company c";
/* 20 */     return findByHql(hql);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.system.impl.CompanyDaoImpl
 * JD-Core Version:    0.6.0
 */
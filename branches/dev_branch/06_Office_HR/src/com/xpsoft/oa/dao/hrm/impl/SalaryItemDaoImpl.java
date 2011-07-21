/*    */ package com.xpsoft.oa.dao.hrm.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.hrm.SalaryItemDao;
/*    */ import com.xpsoft.oa.model.hrm.SalaryItem;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class SalaryItemDaoImpl extends BaseDaoImpl<SalaryItem>
/*    */   implements SalaryItemDao
/*    */ {
/*    */   public SalaryItemDaoImpl()
/*    */   {
/* 20 */     super(SalaryItem.class);
/*    */   }
/*    */ 
/*    */   public List<SalaryItem> getAllExcludeId(String excludeIds, PagingBean pb)
/*    */   {
/* 25 */     String hql = "from SalaryItem ";
/* 26 */     if (StringUtils.isNotEmpty(excludeIds)) {
/* 27 */       hql = hql + "where salaryItemId not in(" + excludeIds + ")";
/*    */     }
/* 29 */     return findByHql(hql, null, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.hrm.impl.SalaryItemDaoImpl
 * JD-Core Version:    0.6.0
 */
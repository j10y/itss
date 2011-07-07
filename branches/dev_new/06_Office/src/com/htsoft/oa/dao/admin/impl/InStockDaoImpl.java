/*    */ package com.htsoft.oa.dao.admin.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.admin.InStockDao;
/*    */ import com.htsoft.oa.model.admin.InStock;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.hibernate.Query;
/*    */ import org.hibernate.Session;
/*    */ 
/*    */ public class InStockDaoImpl extends BaseDaoImpl<InStock>
/*    */   implements InStockDao
/*    */ {
/*    */   public InStockDaoImpl()
/*    */   {
/* 17 */     super(InStock.class);
/*    */   }
/*    */ 
/*    */   public Integer findInCountByBuyId(Long buyId)
/*    */   {
/* 22 */     String hql = "select vo.inCounts from InStock vo where vo.buyId=?";
/* 23 */     Query query = getSession().createQuery(hql);
/* 24 */     query.setLong(0, buyId.longValue());
/* 25 */     Integer inCount = Integer.valueOf(Integer.parseInt(query.list().iterator().next().toString()));
/* 26 */     return inCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.admin.impl.InStockDaoImpl
 * JD-Core Version:    0.6.0
 */
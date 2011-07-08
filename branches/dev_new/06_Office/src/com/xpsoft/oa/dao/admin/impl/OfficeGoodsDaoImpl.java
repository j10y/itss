/*    */ package com.xpsoft.oa.dao.admin.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.admin.OfficeGoodsDao;
/*    */ import com.xpsoft.oa.model.admin.OfficeGoods;
/*    */ import java.util.List;
/*    */ 
/*    */ public class OfficeGoodsDaoImpl extends BaseDaoImpl<OfficeGoods>
/*    */   implements OfficeGoodsDao
/*    */ {
/*    */   public OfficeGoodsDaoImpl()
/*    */   {
/* 15 */     super(OfficeGoods.class);
/*    */   }
/*    */ 
/*    */   public List<OfficeGoods> findByWarm()
/*    */   {
/* 20 */     String hql = "from OfficeGoods vo where ((vo.stockCounts<=vo.warnCounts and vo.isWarning=1) or (vo.stockCounts<=0))";
/* 21 */     return findByHql(hql);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.impl.OfficeGoodsDaoImpl
 * JD-Core Version:    0.6.0
 */
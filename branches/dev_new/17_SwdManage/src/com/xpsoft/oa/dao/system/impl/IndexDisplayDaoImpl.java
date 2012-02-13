/*    */ package com.xpsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.system.IndexDisplayDao;
/*    */ import com.xpsoft.oa.model.system.IndexDisplay;
/*    */ import java.util.List;
/*    */ 
/*    */ public class IndexDisplayDaoImpl extends BaseDaoImpl<IndexDisplay>
/*    */   implements IndexDisplayDao
/*    */ {
/*    */   public IndexDisplayDaoImpl()
/*    */   {
/* 15 */     super(IndexDisplay.class);
/*    */   }
/*    */ 
/*    */   public List<IndexDisplay> findByUser(Long userId)
/*    */   {
/* 20 */     String hql = "from IndexDisplay vo where vo.appUser.userId=?";
/* 21 */     return findByHql(hql, new Object[] { userId });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.impl.IndexDisplayDaoImpl
 * JD-Core Version:    0.6.0
 */
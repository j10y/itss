/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.IndexDisplayDao;
/*    */ import com.xpsoft.oa.model.system.IndexDisplay;
/*    */ import com.xpsoft.oa.service.system.IndexDisplayService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class IndexDisplayServiceImpl extends BaseServiceImpl<IndexDisplay>
/*    */   implements IndexDisplayService
/*    */ {
/*    */   private IndexDisplayDao dao;
/*    */ 
/*    */   public IndexDisplayServiceImpl(IndexDisplayDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<IndexDisplay> findByUser(Long userId)
/*    */   {
/* 23 */     return this.dao.findByUser(userId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.IndexDisplayServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.service.flow.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.flow.ProTypeDao;
/*    */ import com.xpsoft.oa.model.flow.ProType;
/*    */ import com.xpsoft.oa.service.flow.ProTypeService;
/*    */ 
/*    */ public class ProTypeServiceImpl extends BaseServiceImpl<ProType>
/*    */   implements ProTypeService
/*    */ {
/*    */   private ProTypeDao dao;
/*    */ 
/*    */   public ProTypeServiceImpl(ProTypeDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.flow.impl.ProTypeServiceImpl
 * JD-Core Version:    0.6.0
 */
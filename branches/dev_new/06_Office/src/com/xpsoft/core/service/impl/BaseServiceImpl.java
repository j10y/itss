/*    */ package com.xpsoft.core.service.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.GenericDao;
/*    */ import com.xpsoft.core.service.BaseService;
/*    */ 
/*    */ public class BaseServiceImpl<T> extends GenericServiceImpl<T, Long>
/*    */   implements BaseService<T>
/*    */ {
/*    */   public BaseServiceImpl(GenericDao dao)
/*    */   {
/* 12 */     super(dao);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.service.impl.BaseServiceImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.core.service.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.GenericDao;
/*    */ import com.htsoft.core.service.BaseService;
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
 * Qualified Name:     com.htsoft.core.service.impl.BaseServiceImpl
 * JD-Core Version:    0.6.0
 */
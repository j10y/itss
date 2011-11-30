/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import java.util.Set;

import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.oa.dao.system.FunUrlDao;
import com.xpsoft.oa.model.system.FunUrl;
import com.xpsoft.oa.service.system.FunUrlService;
/*    */ 
/*    */ public class FunUrlServiceImpl extends BaseServiceImpl<FunUrl>
/*    */   implements FunUrlService
/*    */ {
/*    */   private FunUrlDao dao;
/*    */ 
/*    */   public FunUrlServiceImpl(FunUrlDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public FunUrl getByPathFunId(String path, Long funId)
/*    */   {
/* 24 */     return this.dao.getByPathFunId(path, funId);
/*    */   }

			public Set<String> getAdminDataSource() {
        	  return this.dao.getAdminDataSource();
           }
           
           
           
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.FunUrlServiceImpl
 * JD-Core Version:    0.6.0
 */
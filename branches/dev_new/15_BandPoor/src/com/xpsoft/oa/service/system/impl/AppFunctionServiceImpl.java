/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
/*    */ import com.xpsoft.oa.dao.system.AppFunctionDao;
/*    */ import com.xpsoft.oa.model.system.AppFunction;
import com.xpsoft.oa.model.system.FunUrl;
import com.xpsoft.oa.service.system.AppFunctionService;
import com.xpsoft.oa.service.system.FunUrlService;
/*    */ 
/*    */ public class AppFunctionServiceImpl extends BaseServiceImpl<AppFunction>
/*    */   implements AppFunctionService
/*    */ {
/*    */   private AppFunctionDao dao;
/*    */ 
/*    */   public AppFunctionServiceImpl(AppFunctionDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public AppFunction getByKey(String key)
/*    */   {
/* 24 */     return this.dao.getByKey(key);
/*    */   }

			
           public void updateFunUrl(String[] funUrls,Long functionId){
        	FunUrlService fus =  (FunUrlService)AppUtil.getBean("funUrlService");
       		fus.removeDatabySql("Delete from fun_url where functionId= "+functionId);
       		for(String funurl:funUrls){
                   FunUrl fu_ = new FunUrl();
                   fu_.setFunctionId(functionId);
                   fu_.setUrlPath(funurl);
                   //fu_.setAppFunction(null);
       			  fus.save(fu_);
       		}
           }
           
           private void removeUrlsByFunctionId(Long id){
        	   if (id != null) {
					removeDatabySql("Delete from fun_url where functionId= "+id);
       		}
           }
           
           private void removeAppFunctionById(Long id){
        	   if (id != null) {
       				removeDatabySql("Delete from app_function where functionId= "+id);
       		}
           }
           public void remove(Long id){
        	   removeUrlsByFunctionId(id);
        	   removeAppFunctionById(id);
           }
           
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.AppFunctionServiceImpl
 * JD-Core Version:    0.6.0
 */
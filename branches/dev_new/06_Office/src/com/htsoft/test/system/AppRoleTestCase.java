/*    */ package com.htsoft.test.system;
/*    */ 
/*    */ import com.htsoft.oa.model.system.AppFunction;
/*    */ import com.htsoft.oa.model.system.AppRole;
/*    */ import com.htsoft.oa.service.system.AppFunctionService;
/*    */ import com.htsoft.oa.service.system.AppRoleService;
/*    */ import com.htsoft.test.BaseTestCase;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ 
/*    */ public class AppRoleTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private AppRoleService appRoleService;
/*    */ 
/*    */   @Resource
/*    */   private AppFunctionService appFunctionService;
/*    */ 
/*    */   public void testMerge()
/*    */   {
/* 21 */     AppRole role = new AppRole();
/* 22 */     role.setRoleId(Long.valueOf(1L));
/* 23 */     role.setStatus(Short.valueOf((short) 0));
/* 24 */     this.appRoleService.merge(role);
/*    */   }
/*    */   @Test
/*    */   public void updateFunctions() {
/* 29 */     AppRole role = (AppRole)this.appRoleService.get(Long.valueOf(3L));
/* 30 */     for (int id = 1; id <= 2; id++) {
/* 31 */       AppFunction appFunction = (AppFunction)this.appFunctionService.get(new Long(id));
/* 32 */       role.getFunctions().add(appFunction);
/*    */     }
/* 34 */     this.appRoleService.save(role);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.system.AppRoleTestCase
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.xpsoft.oa.dao.admin.AssetsTypeDao;
/*    */ import com.xpsoft.oa.model.admin.AssetsType;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class AssetsTypeDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private AssetsTypeDao assetsTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     AssetsType assetsType = new AssetsType();
/*    */ 
/* 22 */     this.assetsTypeDao.save(assetsType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.AssetsTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
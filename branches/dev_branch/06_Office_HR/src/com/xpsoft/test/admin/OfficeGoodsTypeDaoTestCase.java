/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.xpsoft.oa.dao.admin.OfficeGoodsTypeDao;
/*    */ import com.xpsoft.oa.model.admin.OfficeGoodsType;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class OfficeGoodsTypeDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private OfficeGoodsTypeDao officeGoodsTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     OfficeGoodsType officeGoodsType = new OfficeGoodsType();
/*    */ 
/* 22 */     this.officeGoodsTypeDao.save(officeGoodsType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.OfficeGoodsTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.test.admin;
/*    */ 
/*    */ import com.xpsoft.oa.dao.admin.CarDao;
/*    */ import com.xpsoft.oa.model.admin.Car;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class CarDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private CarDao carDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     Car car = new Car();
/*    */ 
/* 22 */     this.carDao.save(car);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.admin.CarDaoTestCase
 * JD-Core Version:    0.6.0
 */
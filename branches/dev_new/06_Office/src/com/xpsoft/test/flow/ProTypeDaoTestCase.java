/*    */ package com.xpsoft.test.flow;
/*    */ 
/*    */ import com.xpsoft.oa.dao.flow.ProTypeDao;
/*    */ import com.xpsoft.oa.model.flow.ProType;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ProTypeDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProTypeDao proTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     ProType proType = new ProType();
/*    */ 
/* 22 */     this.proTypeDao.save(proType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.flow.ProTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.test.flow;
/*    */ 
/*    */ import com.xpsoft.oa.dao.flow.ProUserAssignDao;
/*    */ import com.xpsoft.oa.model.flow.ProUserAssign;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ProUserAssignDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProUserAssignDao proUserAssignDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     ProUserAssign proUserAssign = new ProUserAssign();
/*    */ 
/* 22 */     this.proUserAssignDao.save(proUserAssign);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.flow.ProUserAssignDaoTestCase
 * JD-Core Version:    0.6.0
 */
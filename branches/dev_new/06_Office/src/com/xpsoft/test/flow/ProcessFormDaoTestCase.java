/*    */ package com.xpsoft.test.flow;
/*    */ 
/*    */ import com.xpsoft.oa.dao.flow.ProcessFormDao;
/*    */ import com.xpsoft.oa.model.flow.ProcessForm;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ProcessFormDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProcessFormDao processFormDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     ProcessForm processForm = new ProcessForm();
/*    */ 
/* 22 */     this.processFormDao.save(processForm);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.flow.ProcessFormDaoTestCase
 * JD-Core Version:    0.6.0
 */
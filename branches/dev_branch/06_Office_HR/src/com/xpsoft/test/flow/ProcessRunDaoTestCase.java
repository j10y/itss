/*    */ package com.xpsoft.test.flow;
/*    */ 
/*    */ import com.xpsoft.oa.dao.flow.ProcessRunDao;
/*    */ import com.xpsoft.oa.model.flow.ProcessRun;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ProcessRunDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProcessRunDao processRunDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 19 */     ProcessRun processRun = new ProcessRun();
/*    */ 
/* 22 */     this.processRunDao.save(processRun);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.flow.ProcessRunDaoTestCase
 * JD-Core Version:    0.6.0
 */
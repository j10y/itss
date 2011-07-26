/*    */ package com.xpsoft.test.archive;
/*    */ 
/*    */ import com.xpsoft.oa.dao.archive.ArchTemplateDao;
/*    */ import com.xpsoft.oa.model.archive.ArchTemplate;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ArchTemplateDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ArchTemplateDao archTemplateDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 22 */     ArchTemplate archTemplate = new ArchTemplate();
/*    */ 
/* 25 */     this.archTemplateDao.save(archTemplate);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.archive.ArchTemplateDaoTestCase
 * JD-Core Version:    0.6.0
 */
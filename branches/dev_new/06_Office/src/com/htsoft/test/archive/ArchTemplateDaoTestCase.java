/*    */ package com.htsoft.test.archive;
/*    */ 
/*    */ import com.htsoft.oa.dao.archive.ArchTemplateDao;
/*    */ import com.htsoft.oa.model.archive.ArchTemplate;
/*    */ import com.htsoft.test.BaseTestCase;
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
 * Qualified Name:     com.htsoft.test.archive.ArchTemplateDaoTestCase
 * JD-Core Version:    0.6.0
 */
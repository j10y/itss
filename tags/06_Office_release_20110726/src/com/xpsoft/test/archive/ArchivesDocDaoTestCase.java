/*    */ package com.xpsoft.test.archive;
/*    */ 
/*    */ import com.xpsoft.oa.dao.archive.ArchivesDocDao;
/*    */ import com.xpsoft.oa.model.archive.ArchivesDoc;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ArchivesDocDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ArchivesDocDao archivesDocDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 22 */     ArchivesDoc archivesDoc = new ArchivesDoc();
/*    */ 
/* 25 */     this.archivesDocDao.save(archivesDoc);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.archive.ArchivesDocDaoTestCase
 * JD-Core Version:    0.6.0
 */
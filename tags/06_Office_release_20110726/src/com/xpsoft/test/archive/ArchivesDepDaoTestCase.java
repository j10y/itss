/*    */ package com.xpsoft.test.archive;
/*    */ 
/*    */ import com.xpsoft.oa.dao.archive.ArchivesDepDao;
/*    */ import com.xpsoft.oa.model.archive.ArchivesDep;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ArchivesDepDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ArchivesDepDao archivesDepDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 22 */     ArchivesDep archivesDep = new ArchivesDep();
/*    */ 
/* 25 */     this.archivesDepDao.save(archivesDep);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.archive.ArchivesDepDaoTestCase
 * JD-Core Version:    0.6.0
 */
/*    */ package com.htsoft.test.archive;
/*    */ 
/*    */ import com.htsoft.oa.dao.archive.ArchRecTypeDao;
/*    */ import com.htsoft.oa.model.archive.ArchRecType;
/*    */ import com.htsoft.test.BaseTestCase;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ import org.springframework.test.annotation.Rollback;
/*    */ 
/*    */ public class ArchRecTypeDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ArchRecTypeDao archRecTypeDao;
/*    */ 
/*    */   @Test
/*    */   @Rollback(false)
/*    */   public void add()
/*    */   {
/* 22 */     ArchRecType archRecType = new ArchRecType();
/*    */ 
/* 25 */     this.archRecTypeDao.save(archRecType);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.archive.ArchRecTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
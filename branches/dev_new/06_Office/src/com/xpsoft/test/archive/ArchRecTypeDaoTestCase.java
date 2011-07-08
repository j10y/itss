/*    */ package com.xpsoft.test.archive;
/*    */ 
/*    */ import com.xpsoft.oa.dao.archive.ArchRecTypeDao;
/*    */ import com.xpsoft.oa.model.archive.ArchRecType;
/*    */ import com.xpsoft.test.BaseTestCase;
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
 * Qualified Name:     com.xpsoft.test.archive.ArchRecTypeDaoTestCase
 * JD-Core Version:    0.6.0
 */
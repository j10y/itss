/*     */ package com.htsoft.test.document;
/*     */ 
/*     */ import com.htsoft.oa.dao.document.DocFolderDao;
/*     */ import com.htsoft.oa.dao.document.DocPrivilegeDao;
/*     */ import com.htsoft.oa.dao.document.DocumentDao;
/*     */ import com.htsoft.oa.dao.system.AppUserDao;
/*     */ import com.htsoft.oa.model.system.AppUser;
/*     */ import com.htsoft.test.BaseTestCase;
/*     */ import java.io.PrintStream;
/*     */ import javax.annotation.Resource;
/*     */ import org.junit.Test;
/*     */ 
/*     */ public class DocPrivilegeDaoTestCase extends BaseTestCase
/*     */ {
/*     */ 
/*     */   @Resource
/*     */   private DocPrivilegeDao docPrivilegeDao;
/*     */ 
/*     */   @Resource
/*     */   private DocumentDao documentDao;
/*     */ 
/*     */   @Resource
/*     */   private DocFolderDao docFolderDao;
/*     */ 
/*     */   @Resource
/*     */   private AppUserDao dao;
/*     */ 
/*     */   @Test
/*     */   public void str()
/*     */   {
/* 163 */     AppUser user = (AppUser)this.dao.get(Long.valueOf(2L));
/* 164 */     Integer right = this.docPrivilegeDao.getRightsByDocument(user, Long.valueOf(1L));
/* 165 */     System.out.println(right);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.test.document.DocPrivilegeDaoTestCase
 * JD-Core Version:    0.6.0
 */
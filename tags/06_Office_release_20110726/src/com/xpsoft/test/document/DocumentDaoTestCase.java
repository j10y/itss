/*    */ package com.xpsoft.test.document;
/*    */ 
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.document.DocumentDao;
/*    */ import com.xpsoft.oa.dao.system.AppUserDao;
/*    */ import com.xpsoft.oa.model.document.Document;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.test.BaseTestCase;
/*    */ import java.io.PrintStream;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ import org.junit.Test;
/*    */ 
/*    */ public class DocumentDaoTestCase extends BaseTestCase
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private DocumentDao documentDao;
/*    */ 
/*    */   @Resource
/*    */   private AppUserDao appUserDao;
/*    */ 
/*    */   @Test
/*    */   public void tesss()
/*    */   {
/* 55 */     AppUser user = (AppUser)this.appUserDao.get(Long.valueOf(2L));
/*    */ 
/* 89 */     PagingBean pb = new PagingBean(0, 6);
/* 90 */     Document document = (Document)this.documentDao.get(Long.valueOf(6L));
/* 91 */     List docs = this.documentDao.findByPersonal(Long.valueOf(2L), null, null, null, null, pb);
/* 92 */     System.out.println("size:" + docs.size());
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.test.document.DocumentDaoTestCase
 * JD-Core Version:    0.6.0
 */
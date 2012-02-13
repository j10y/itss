/*    */ package com.xpsoft.oa.service.document.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.document.DocPrivilegeDao;
/*    */ import com.xpsoft.oa.dao.document.DocumentDao;
/*    */ import com.xpsoft.oa.model.document.Document;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.service.document.DocumentService;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ 
/*    */ public class DocumentServiceImpl extends BaseServiceImpl<Document>
/*    */   implements DocumentService
/*    */ {
/*    */   private DocumentDao dao;
/*    */ 
/*    */   @Resource
/*    */   private DocPrivilegeDao docPrivilegeDao;
/*    */ 
/*    */   public DocumentServiceImpl(DocumentDao dao)
/*    */   {
/* 26 */     super(dao);
/* 27 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Document> findByIsShared(Document document, Date from, Date to, Long userId, ArrayList<Long> roleIds, Long depId, PagingBean pb)
/*    */   {
/* 33 */     return this.dao.findByIsShared(document, from, to, userId, roleIds, depId, pb);
/*    */   }
/*    */ 
/*    */   public List<Document> findByPublic(String path, Document document, Date from, Date to, AppUser appUser, PagingBean pb)
/*    */   {
/* 38 */     return this.dao.findByPublic(path, document, from, to, appUser, pb);
/*    */   }
/*    */ 
/*    */   public List<Document> findByPersonal(Long userId, Document document, Date from, Date to, String path, PagingBean pb)
/*    */   {
/* 44 */     return this.dao.findByPersonal(userId, document, from, to, path, pb);
/*    */   }
/*    */ 
/*    */   public List<Document> findByFolder(String path)
/*    */   {
/* 49 */     return this.dao.findByFolder(path);
/*    */   }
/*    */ 
/*    */   public List<Document> searchDocument(AppUser appUser, String content, PagingBean pb)
/*    */   {
/* 55 */     boolean isHaveData = false;
/* 56 */     Integer count = this.docPrivilegeDao.countPrivilege();
/* 57 */     if (count.intValue() > 0) {
/* 58 */       isHaveData = true;
/*    */     }
/* 60 */     return this.dao.searchDocument(appUser, content, isHaveData, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.document.impl.DocumentServiceImpl
 * JD-Core Version:    0.6.0
 */
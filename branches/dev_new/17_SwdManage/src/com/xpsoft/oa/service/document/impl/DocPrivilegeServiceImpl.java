/*    */ package com.xpsoft.oa.service.document.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.document.DocPrivilegeDao;
/*    */ import com.xpsoft.oa.model.document.DocPrivilege;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.service.document.DocPrivilegeService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DocPrivilegeServiceImpl extends BaseServiceImpl<DocPrivilege>
/*    */   implements DocPrivilegeService
/*    */ {
/*    */   private DocPrivilegeDao dao;
/*    */ 
/*    */   public DocPrivilegeServiceImpl(DocPrivilegeDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<DocPrivilege> getAll(DocPrivilege docPrivilege, Long folderId, PagingBean pb)
/*    */   {
/* 26 */     return this.dao.getAll(docPrivilege, folderId, pb);
/*    */   }
/*    */ 
/*    */   public List<Integer> getRightsByFolder(AppUser user, Long folderId)
/*    */   {
/* 31 */     return this.dao.getRightsByFolder(user, folderId);
/*    */   }
/*    */ 
/*    */   public Integer getRightsByDocument(AppUser user, Long docId)
/*    */   {
/* 36 */     return this.dao.getRightsByDocument(user, docId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.document.impl.DocPrivilegeServiceImpl
 * JD-Core Version:    0.6.0
 */
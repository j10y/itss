/*    */ package com.htsoft.oa.service.document.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.document.DocFolderDao;
/*    */ import com.htsoft.oa.model.document.DocFolder;
/*    */ import com.htsoft.oa.service.document.DocFolderService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DocFolderServiceImpl extends BaseServiceImpl<DocFolder>
/*    */   implements DocFolderService
/*    */ {
/*    */   private DocFolderDao dao;
/*    */ 
/*    */   public DocFolderServiceImpl(DocFolderDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<DocFolder> getUserFolderByParentId(Long userId, Long parentId) {
/* 22 */     return this.dao.getUserFolderByParentId(userId, parentId);
/*    */   }
/*    */ 
/*    */   public List<DocFolder> getFolderLikePath(String path)
/*    */   {
/* 31 */     return this.dao.getFolderLikePath(path);
/*    */   }
/*    */ 
/*    */   public List<DocFolder> getPublicFolderByParentId(Long parentId)
/*    */   {
/* 36 */     return this.dao.getPublicFolderByParentId(parentId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.document.impl.DocFolderServiceImpl
 * JD-Core Version:    0.6.0
 */
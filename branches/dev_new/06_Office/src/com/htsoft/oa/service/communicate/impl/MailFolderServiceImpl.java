/*    */ package com.htsoft.oa.service.communicate.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.communicate.MailFolderDao;
/*    */ import com.htsoft.oa.model.communicate.MailFolder;
/*    */ import com.htsoft.oa.service.communicate.MailFolderService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MailFolderServiceImpl extends BaseServiceImpl<MailFolder>
/*    */   implements MailFolderService
/*    */ {
/*    */   private MailFolderDao dao;
/*    */ 
/*    */   public MailFolderServiceImpl(MailFolderDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<MailFolder> getUserFolderByParentId(Long curUserId, Long parentId)
/*    */   {
/* 25 */     return this.dao.getUserFolderByParentId(curUserId, parentId);
/*    */   }
/*    */ 
/*    */   public List<MailFolder> getAllUserFolderByParentId(Long userId, Long parentId)
/*    */   {
/* 31 */     return this.dao.getAllUserFolderByParentId(userId, parentId);
/*    */   }
/*    */ 
/*    */   public List<MailFolder> getFolderLikePath(String path)
/*    */   {
/* 36 */     return this.dao.getFolderLikePath(path);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.communicate.impl.MailFolderServiceImpl
 * JD-Core Version:    0.6.0
 */
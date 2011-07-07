/*    */ package com.htsoft.oa.dao.communicate.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.communicate.MailFolderDao;
/*    */ import com.htsoft.oa.model.communicate.MailFolder;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MailFolderDaoImpl extends BaseDaoImpl<MailFolder>
/*    */   implements MailFolderDao
/*    */ {
/*    */   public MailFolderDaoImpl()
/*    */   {
/* 16 */     super(MailFolder.class);
/*    */   }
/*    */ 
/*    */   public List<MailFolder> getUserFolderByParentId(Long userId, Long parentId)
/*    */   {
/* 23 */     String hql = "from MailFolder mf where mf.appUser.userId=? and parentId=?";
/* 24 */     return findByHql(hql, new Object[] { userId, parentId });
/*    */   }
/*    */ 
/*    */   public List<MailFolder> getAllUserFolderByParentId(Long userId, Long parentId)
/*    */   {
/* 33 */     String hql = "from MailFolder mf where mf.appUser.userId=? and parentId=? or userId is null";
/* 34 */     return findByHql(hql, new Object[] { userId, parentId });
/*    */   }
/*    */ 
/*    */   public List<MailFolder> getFolderLikePath(String path)
/*    */   {
/* 44 */     String hql = "from MailFolder mf where mf.path like ?";
/* 45 */     return findByHql(hql, new Object[] { path + '%' });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.communicate.impl.MailFolderDaoImpl
 * JD-Core Version:    0.6.0
 */
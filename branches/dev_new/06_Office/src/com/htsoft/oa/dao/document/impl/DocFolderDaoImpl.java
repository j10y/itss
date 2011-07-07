/*    */ package com.htsoft.oa.dao.document.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.document.DocFolderDao;
/*    */ import com.htsoft.oa.model.document.DocFolder;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DocFolderDaoImpl extends BaseDaoImpl<DocFolder>
/*    */   implements DocFolderDao
/*    */ {
/*    */   public DocFolderDaoImpl()
/*    */   {
/* 14 */     super(DocFolder.class);
/*    */   }
/*    */ 
/*    */   public List<DocFolder> getUserFolderByParentId(Long userId, Long parentId)
/*    */   {
/* 25 */     String hql = "from DocFolder df where df.isShared=0 and df.appUser.userId=? and parentId=?";
/* 26 */     return findByHql(hql, new Object[] { userId, parentId });
/*    */   }
/*    */ 
/*    */   public List<DocFolder> getFolderLikePath(String path)
/*    */   {
/* 34 */     String hql = "from DocFolder df where df.path like ?";
/* 35 */     return findByHql(hql, new Object[] { path + '%' });
/*    */   }
/*    */ 
/*    */   public List<DocFolder> getPublicFolderByParentId(Long parentId)
/*    */   {
/* 40 */     String hql = "from DocFolder df where df.isShared=1 and parentId=? ";
/* 41 */     return findByHql(hql, new Object[] { parentId });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.document.impl.DocFolderDaoImpl
 * JD-Core Version:    0.6.0
 */
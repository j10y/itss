/*    */ package com.htsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.oa.dao.archive.ArchivesHandleDao;
/*    */ import com.htsoft.oa.model.archive.ArchivesHandle;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchivesHandleDaoImpl extends BaseDaoImpl<ArchivesHandle>
/*    */   implements ArchivesHandleDao
/*    */ {
/*    */   public ArchivesHandleDaoImpl()
/*    */   {
/* 15 */     super(ArchivesHandle.class);
/*    */   }
/*    */ 
/*    */   public ArchivesHandle findByUAIds(Long userId, Long archiveId)
/*    */   {
/* 20 */     String hql = "from ArchivesHandle vo where vo.userId=? and vo.archives.archivesId=?";
/* 21 */     Object[] objs = { userId, archiveId };
/* 22 */     List list = findByHql(hql, objs);
/* 23 */     if (list.size() > 0) {
/* 24 */       return (ArchivesHandle)list.get(0);
/*    */     }
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   public List<ArchivesHandle> findByAid(Long archiveId)
/*    */   {
/* 32 */     String hql = "from ArchivesHandle vo where vo.archives.archivesId=?";
/* 33 */     Object[] objs = { archiveId };
/* 34 */     return findByHql(hql, objs);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.archive.impl.ArchivesHandleDaoImpl
 * JD-Core Version:    0.6.0
 */
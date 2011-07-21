/*    */ package com.xpsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchivesDocDao;
/*    */ import com.xpsoft.oa.model.archive.ArchivesDoc;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchivesDocDaoImpl extends BaseDaoImpl<ArchivesDoc>
/*    */   implements ArchivesDocDao
/*    */ {
/*    */   public ArchivesDocDaoImpl()
/*    */   {
/* 15 */     super(ArchivesDoc.class);
/*    */   }
/*    */ 
/*    */   public List<ArchivesDoc> findByAid(Long archivesId)
/*    */   {
/* 20 */     String hql = "from ArchivesDoc vo where vo.archives.archivesId=?";
/* 21 */     Object[] objs = { archivesId };
/* 22 */     return findByHql(hql, objs);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.impl.ArchivesDocDaoImpl
 * JD-Core Version:    0.6.0
 */
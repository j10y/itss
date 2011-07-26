/*    */ package com.xpsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchHastenDao;
/*    */ import com.xpsoft.oa.model.archive.ArchHasten;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchHastenDaoImpl extends BaseDaoImpl<ArchHasten>
/*    */   implements ArchHastenDao
/*    */ {
/*    */   public ArchHastenDaoImpl()
/*    */   {
/* 16 */     super(ArchHasten.class);
/*    */   }
/*    */ 
/*    */   public Date getLeastRecordByUser(Long archivesId)
/*    */   {
/* 21 */     String hql = "from ArchHasten vo where vo.archives.archivesId=? order by vo.createtime desc";
/* 22 */     List list = findByHql(hql, new Object[] { archivesId });
/* 23 */     if (list.size() > 0) {
/* 24 */       return ((ArchHasten)list.get(0)).getCreatetime();
/*    */     }
/* 26 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.impl.ArchHastenDaoImpl
 * JD-Core Version:    0.6.0
 */
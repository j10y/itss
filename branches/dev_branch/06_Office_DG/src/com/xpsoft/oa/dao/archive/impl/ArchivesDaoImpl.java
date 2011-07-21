/*    */ package com.xpsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.archive.ArchivesDao;
/*    */ import com.xpsoft.oa.model.archive.Archives;
/*    */ import com.xpsoft.oa.model.system.AppRole;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ArchivesDaoImpl extends BaseDaoImpl<Archives>
/*    */   implements ArchivesDao
/*    */ {
/*    */   public ArchivesDaoImpl()
/*    */   {
/* 19 */     super(Archives.class);
/*    */   }
/*    */ 
/*    */   public List<Archives> findByUserOrRole(Long userId, Set<AppRole> roles, PagingBean pb)
/*    */   {
/* 25 */     Iterator it = roles.iterator();
/* 26 */     StringBuffer sb = new StringBuffer();
/* 27 */     while (it.hasNext()) {
/* 28 */       if (sb.length() > 0) {
/* 29 */         sb.append(",");
/*    */       }
/* 31 */       sb.append(((AppRole)it.next()).getRoleId().toString());
/*    */     }
/* 33 */     StringBuffer hql = new StringBuffer("select vo1 from Archives vo1,ArchDispatch vo2 where vo2.archives=vo1 and vo2.archUserType=2 and (vo2.userId=?");
/* 34 */     if (sb.length() > 0) {
/* 35 */       hql.append(" or vo2.disRoleId in (" + sb + ")");
/*    */     }
/* 37 */     hql.append(")  group by vo1");
/* 38 */     Object[] objs = { userId };
/* 39 */     return findByHql(hql.toString(), objs, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.impl.ArchivesDaoImpl
 * JD-Core Version:    0.6.0
 */
/*    */ package com.xpsoft.oa.dao.archive.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.archive.ArchFlowConfDao;
/*    */ import com.xpsoft.oa.model.archive.ArchFlowConf;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ArchFlowConfDaoImpl extends BaseDaoImpl<ArchFlowConf>
/*    */   implements ArchFlowConfDao
/*    */ {
/*    */   public ArchFlowConfDaoImpl()
/*    */   {
/* 15 */     super(ArchFlowConf.class);
/*    */   }
/*    */ 
/*    */   public ArchFlowConf getByFlowType(Short archType)
/*    */   {
/* 20 */     String hql = "from ArchFlowConf vo where vo.archType=?";
/* 21 */     Object[] objs = { archType };
/* 22 */     List list = findByHql(hql, objs);
/* 23 */     if (list.size() == 1) {
/* 24 */       return (ArchFlowConf)list.get(0);
/*    */     }
/* 26 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.impl.ArchFlowConfDaoImpl
 * JD-Core Version:    0.6.0
 */
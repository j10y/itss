/*    */ package com.xpsoft.oa.dao.flow.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.flow.ProcessRunDao;
/*    */ import com.xpsoft.oa.model.flow.ProcessRun;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ProcessRunDaoImpl extends BaseDaoImpl<ProcessRun>
/*    */   implements ProcessRunDao
/*    */ {
/*    */   public ProcessRunDaoImpl()
/*    */   {
/* 19 */     super(ProcessRun.class);
/*    */   }
/*    */ 
/*    */   public ProcessRun getByPiId(String piId)
/*    */   {
/* 27 */     String hql = "from ProcessRun pr where pr.piId=?";
/* 28 */     return (ProcessRun)findUnique(hql, new Object[] { piId });
/*    */   }
/*    */ 
/*    */   public List<ProcessRun> getByDefId(Long defId, PagingBean pb)
/*    */   {
/* 38 */     String hql = " from ProcessRun pr where pr.proDefinition.defId=? ";
/* 39 */     return findByHql(hql, new Object[] { defId }, pb);
/*    */   }
/*    */ 
/*    */   public List<ProcessRun> getByUserIdSubject(Long userId, String subject, PagingBean pb)
/*    */   {
/* 51 */     ArrayList params = new ArrayList();
/* 52 */     //String hql = "select pr from ProcessRun as pr join pr.processForms as pf where pf.creatorId=? group by pr.runId order by pr.createtime desc";
			String hql = "select pr from ProcessRun as pr join pr.processForms as pf where pf.creatorId=? order by pr.createtime desc";
/* 53 */     params.add(userId);
/* 54 */     if (StringUtils.isNotEmpty(subject)) {
/* 55 */       hql = hql + " and pr.subject like ?";
/* 56 */       params.add("%" + subject + "%");
/*    */     }
/*    */ 
/* 59 */     return findByHql(hql, params.toArray(), pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.flow.impl.ProcessRunDaoImpl
 * JD-Core Version:    0.6.0
 */
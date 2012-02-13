/*    */ package com.xpsoft.oa.dao.flow.impl;
/*    */ 
/*    */ import com.xpsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.xpsoft.oa.dao.flow.FormDataDao;
/*    */ import com.xpsoft.oa.model.flow.FormData;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FormDataDaoImpl extends BaseDaoImpl<FormData>
/*    */   implements FormDataDao
/*    */ {
/*    */   public FormDataDaoImpl()
/*    */   {
/* 15 */     super(FormData.class);
/*    */   }
/*    */ 
/*    */   public List<FormData> getByRunIdActivityName(Long runId, String activityName)
/*    */   {
/* 25 */     String hql = "from FormData fd where fd.processForm.processRun.runId=? and fd.processForm.activityName=?";
/* 26 */     return findByHql(hql, new Object[] { runId, activityName });
/*    */   }
/*    */ 
/*    */   public FormData getByFormIdFieldName(Long formId, String fieldName)
/*    */   {
/* 33 */     String hql = "from FormData fd where fd.processForm.formId=? and fd.fieldName=?";
/* 34 */     return (FormData)findUnique(hql, new Object[] { formId, fieldName });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.flow.impl.FormDataDaoImpl
 * JD-Core Version:    0.6.0
 */
 package com.xpsoft.oa.dao.flow.impl;
 
 import com.xpsoft.core.dao.impl.BaseDaoImpl;
 import com.xpsoft.oa.dao.flow.ProcessFormDao;
 import com.xpsoft.oa.model.flow.FormData;
 import com.xpsoft.oa.model.flow.ProcessForm;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 
 public class ProcessFormDaoImpl extends BaseDaoImpl<ProcessForm>
   implements ProcessFormDao
 {
   public ProcessFormDaoImpl()
   {
/* 20 */     super(ProcessForm.class);
   }
 
   public List getByRunId(Long runId)
   {
/* 27 */     String hql = "from ProcessForm pf where pf.processRun.runId=? order by pf.formId asc";
/* 28 */     return findByHql(hql, new Object[] { runId });
   }
 
   public ProcessForm getByRunIdActivityName(Long runId, String activityName)
   {
/* 37 */     Integer maxSn = Integer.valueOf(getActvityExeTimes(runId, activityName).intValue());
/* 38 */     String hql = "from ProcessForm pf where pf.processRun.runId=? and pf.activityName=? and pf.sn=?";
/* 39 */     return (ProcessForm)findUnique(hql, new Object[] { runId, activityName, maxSn });
   }
 
   public Map getVariables(Long runId)
   {
/* 48 */     Map variables = new HashMap();
/* 49 */     String hql = "from ProcessForm pf where pf.processRun.runId=? order by pf.createtime desc";
/* 50 */     List<ProcessForm> forms = findByHql(hql, new Object[] { runId });
 
/* 52 */     for (ProcessForm form : forms) {
/* 53 */       Iterator formDataIt = form.getFormDatas().iterator();
/* 54 */       while (formDataIt.hasNext()) {
/* 55 */         FormData formData = (FormData)formDataIt.next();
/* 56 */         if (!variables.containsKey(formData.getFieldName())) {
/* 57 */           variables.put(formData.getFieldName(), formData.getVal());
         }
       }
     }
/* 61 */     return variables;
   }
 
   public Long getActvityExeTimes(Long runId, String activityName)
   {
/* 69 */     String hql = "select count(pf.formId) from ProcessForm pf where pf.processRun.runId=? and pf.activityName=? ";
/* 70 */     return (Long)findUnique(hql, new Object[] { runId, activityName });
   }
 }

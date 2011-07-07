/*    */ package com.htsoft.oa.service.flow.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.flow.FormDataDao;
/*    */ import com.htsoft.oa.model.flow.FormData;
/*    */ import com.htsoft.oa.service.flow.FormDataService;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FormDataServiceImpl extends BaseServiceImpl<FormData>
/*    */   implements FormDataService
/*    */ {
/*    */   private FormDataDao dao;
/*    */ 
/*    */   public FormDataServiceImpl(FormDataDao dao)
/*    */   {
/* 19 */     super(dao);
/* 20 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Map<String, Object> getFromDataMap(Long runId, String activityName)
/*    */   {
/* 27 */     List<FormData> list = this.dao.getByRunIdActivityName(runId, activityName);
/* 28 */     Map dataMap = new HashMap();
/* 29 */     for (FormData form : list) {
/* 30 */       dataMap.put(form.getFieldName(), form.getValue());
/*    */     }
/* 32 */     return dataMap;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.flow.impl.FormDataServiceImpl
 * JD-Core Version:    0.6.0
 */
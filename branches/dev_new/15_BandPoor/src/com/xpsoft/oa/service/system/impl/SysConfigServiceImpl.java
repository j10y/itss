/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.system.SysConfigDao;
/*    */ import com.xpsoft.oa.model.system.SysConfig;
/*    */ import com.xpsoft.oa.service.system.SysConfigService;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig>
/*    */   implements SysConfigService
/*    */ {
/*    */   private SysConfigDao dao;
/*    */ 
/*    */   public SysConfigServiceImpl(SysConfigDao dao)
/*    */   {
/* 20 */     super(dao);
/* 21 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public SysConfig findByKey(String key)
/*    */   {
/* 26 */     return this.dao.findByKey(key);
/*    */   }
/*    */ 
/*    */   public Map findByType()
/*    */   {
/* 31 */     List<String> list = this.dao.findTypeNames();
/* 32 */     Map cList = new HashMap();
/* 33 */     for (String typeName : list) {
/* 34 */       List confList = this.dao.findConfigByTypeName(typeName);
/* 35 */       cList.put(typeName, confList);
/*    */     }
/* 37 */     return cList;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.SysConfigServiceImpl
 * JD-Core Version:    0.6.0
 */
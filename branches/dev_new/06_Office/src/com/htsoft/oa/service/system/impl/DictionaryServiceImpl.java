/*    */ package com.htsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.system.DictionaryDao;
/*    */ import com.htsoft.oa.model.system.Dictionary;
/*    */ import com.htsoft.oa.service.system.DictionaryService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary>
/*    */   implements DictionaryService
/*    */ {
/*    */   private DictionaryDao dao;
/*    */ 
/*    */   public DictionaryServiceImpl(DictionaryDao dao)
/*    */   {
/* 14 */     super(dao);
/* 15 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<String> getAllItems()
/*    */   {
/* 20 */     return this.dao.getAllItems();
/*    */   }
/*    */ 
/*    */   public List<String> getAllByItemName(String itemName)
/*    */   {
/* 25 */     return this.dao.getAllByItemName(itemName);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.system.impl.DictionaryServiceImpl
 * JD-Core Version:    0.6.0
 */
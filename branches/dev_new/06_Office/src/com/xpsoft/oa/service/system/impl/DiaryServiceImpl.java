/*    */ package com.xpsoft.oa.service.system.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.system.DiaryDao;
/*    */ import com.xpsoft.oa.model.system.Diary;
/*    */ import com.xpsoft.oa.service.system.DiaryService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DiaryServiceImpl extends BaseServiceImpl<Diary>
/*    */   implements DiaryService
/*    */ {
/*    */   private DiaryDao dao;
/*    */ 
/*    */   public DiaryServiceImpl(DiaryDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<Diary> getAllBySn(PagingBean pb)
/*    */   {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */   public List<Diary> getSubDiary(String userIds, PagingBean pb)
/*    */   {
/* 31 */     return this.dao.getSubDiary(userIds, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.impl.DiaryServiceImpl
 * JD-Core Version:    0.6.0
 */
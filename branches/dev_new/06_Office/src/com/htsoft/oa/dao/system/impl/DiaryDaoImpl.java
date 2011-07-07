/*    */ package com.htsoft.oa.dao.system.impl;
/*    */ 
/*    */ import com.htsoft.core.dao.impl.BaseDaoImpl;
/*    */ import com.htsoft.core.web.paging.PagingBean;
/*    */ import com.htsoft.oa.dao.system.DiaryDao;
/*    */ import com.htsoft.oa.model.system.Diary;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DiaryDaoImpl extends BaseDaoImpl<Diary>
/*    */   implements DiaryDao
/*    */ {
/*    */   public DiaryDaoImpl()
/*    */   {
/* 16 */     super(Diary.class);
/*    */   }
/*    */ 
/*    */   public List<Diary> getSubDiary(String userIds, PagingBean pb)
/*    */   {
/* 21 */     String hql = "from Diary vo where vo.appUser.userId in (" + userIds + ") and vo.diaryType=1";
/* 22 */     return findByHql(hql, null, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.system.impl.DiaryDaoImpl
 * JD-Core Version:    0.6.0
 */
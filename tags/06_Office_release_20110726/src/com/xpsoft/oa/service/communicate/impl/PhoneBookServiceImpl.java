/*    */ package com.xpsoft.oa.service.communicate.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.communicate.PhoneBookDao;
/*    */ import com.xpsoft.oa.model.communicate.PhoneBook;
/*    */ import com.xpsoft.oa.service.communicate.PhoneBookService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PhoneBookServiceImpl extends BaseServiceImpl<PhoneBook>
/*    */   implements PhoneBookService
/*    */ {
/*    */   private PhoneBookDao dao;
/*    */ 
/*    */   public PhoneBookServiceImpl(PhoneBookDao dao)
/*    */   {
/* 18 */     super(dao);
/* 19 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public List<PhoneBook> sharedPhoneBooks(String fullname, String ownerName, PagingBean pb)
/*    */   {
/* 25 */     return this.dao.sharedPhoneBooks(fullname, ownerName, pb);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.communicate.impl.PhoneBookServiceImpl
 * JD-Core Version:    0.6.0
 */
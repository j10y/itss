/*    */ package com.xpsoft.oa.service.communicate.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.communicate.MailDao;
/*    */ import com.xpsoft.oa.model.communicate.Mail;
/*    */ import com.xpsoft.oa.service.communicate.MailService;
/*    */ 
/*    */ public class MailServiceImpl extends BaseServiceImpl<Mail>
/*    */   implements MailService
/*    */ {
/*    */   private MailDao dao;
/*    */ 
/*    */   public MailServiceImpl(MailDao dao)
/*    */   {
/* 15 */     super(dao);
/* 16 */     this.dao = dao;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.communicate.impl.MailServiceImpl
 * JD-Core Version:    0.6.0
 */
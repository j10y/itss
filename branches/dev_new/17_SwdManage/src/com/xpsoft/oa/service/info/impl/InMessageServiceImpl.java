/*    */ package com.xpsoft.oa.service.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.InMessageDao;
/*    */ import com.xpsoft.oa.model.info.InMessage;
/*    */ import com.xpsoft.oa.model.info.ShortMessage;
/*    */ import com.xpsoft.oa.service.info.InMessageService;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class InMessageServiceImpl extends BaseServiceImpl<InMessage>
/*    */   implements InMessageService
/*    */ {
/*    */   private InMessageDao dao;
/*    */ 
/*    */   public InMessageServiceImpl(InMessageDao dao)
/*    */   {
/* 22 */     super(dao);
/* 23 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public InMessage findByRead(Long userId) {
/* 27 */     return this.dao.findByRead(userId);
/*    */   }
/*    */ 
/*    */   public Integer findByReadFlag(Long userId) {
/* 31 */     return this.dao.findByReadFlag(userId);
/*    */   }
/*    */ 
/*    */   public List<InMessage> findAll(Long userId, PagingBean pb) {
/* 35 */     return this.dao.findAll(userId, pb);
/*    */   }
/*    */ 
/*    */   public List findByUser(Long userId, PagingBean pb) {
/* 39 */     return this.dao.findByUser(userId, pb);
/*    */   }
/*    */ 
/*    */   public List searchInMessage(Long userId, InMessage inMessage, ShortMessage shortMessage, Date from, Date to, PagingBean pb)
/*    */   {
/* 44 */     return this.dao.searchInMessage(userId, inMessage, shortMessage, from, to, pb);
/*    */   }
/*    */ 
/*    */   public InMessage findLatest(Long userId) {
/* 48 */     return this.dao.findLatest(userId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.impl.InMessageServiceImpl
 * JD-Core Version:    0.6.0
 */
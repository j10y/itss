/*    */ package com.xpsoft.oa.service.info.impl;
/*    */ 
/*    */ import com.xpsoft.core.Constants;
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.core.web.paging.PagingBean;
/*    */ import com.xpsoft.oa.dao.info.InMessageDao;
/*    */ import com.xpsoft.oa.dao.info.ShortMessageDao;
/*    */ import com.xpsoft.oa.dao.system.AppUserDao;
/*    */ import com.xpsoft.oa.model.info.InMessage;
/*    */ import com.xpsoft.oa.model.info.ShortMessage;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.service.info.ShortMessageService;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.annotation.Resource;
/*    */ 
/*    */ public class ShortMessageServiceImpl extends BaseServiceImpl<ShortMessage>
/*    */   implements ShortMessageService
/*    */ {
/*    */   private ShortMessageDao messageDao;
/*    */ 
/*    */   @Resource
/*    */   private InMessageDao inMessageDao;
/*    */ 
/*    */   @Resource
/*    */   private AppUserDao appUserDao;
/*    */ 
/*    */   public ShortMessageServiceImpl(ShortMessageDao dao)
/*    */   {
/* 33 */     super(dao);
/* 34 */     this.messageDao = dao;
/*    */   }
/*    */ 
/*    */   public List<ShortMessage> findAll(Long userId, PagingBean pb)
/*    */   {
/* 39 */     return this.messageDao.findAll(userId, pb);
/*    */   }
/*    */ 
/*    */   public List<ShortMessage> findByUser(Long userId)
/*    */   {
/* 44 */     return this.messageDao.findByUser(userId);
/*    */   }
/*    */ 
/*    */   public List searchShortMessage(Long userId, ShortMessage shortMessage, Date from, Date to, PagingBean pb)
/*    */   {
/* 50 */     return this.messageDao.searchShortMessage(userId, shortMessage, from, to, pb);
/*    */   }
/*    */ 
/*    */   public ShortMessage save(Long senderId, String receiveIds, String content, Short msgType)
/*    */   {
/* 55 */     ShortMessage shortMessage = new ShortMessage();
/* 56 */     shortMessage.setContent(content);
/* 57 */     shortMessage.setMsgType(msgType);
/* 58 */     AppUser curUser = (AppUser)this.appUserDao.get(senderId);
/* 59 */     shortMessage.setSender(curUser.getFullname());
/* 60 */     shortMessage.setSenderId(curUser.getUserId());
/* 61 */     shortMessage.setSendTime(new Date());
/*    */ 
/* 63 */     this.messageDao.save(shortMessage);
/*    */ 
/* 65 */     String[] reIds = receiveIds.split("[,]");
/* 66 */     if (reIds != null)
/*    */     {
/* 68 */       for (String userId : reIds) {
/* 69 */         InMessage inMsg = new InMessage();
/* 70 */         inMsg.setDelFlag(Constants.FLAG_UNDELETED);
/* 71 */         inMsg.setReadFlag(InMessage.FLAG_UNREAD);
/* 72 */         inMsg.setShortMessage(shortMessage);
/* 73 */         AppUser receiveUser = (AppUser)this.appUserDao.get(new Long(userId));
/*    */ 
/* 75 */         inMsg.setUserId(receiveUser.getUserId());
/* 76 */         inMsg.setUserFullname(receiveUser.getFullname());
/* 77 */         this.inMessageDao.save(inMsg);
/*    */       }
/*    */     }
/*    */ 
/* 81 */     return shortMessage;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.impl.ShortMessageServiceImpl
 * JD-Core Version:    0.6.0
 */
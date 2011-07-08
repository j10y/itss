/*    */ package com.xpsoft.oa.service.communicate.impl;
/*    */ 
/*    */ import com.xpsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.xpsoft.oa.dao.communicate.PhoneGroupDao;
/*    */ import com.xpsoft.oa.model.communicate.PhoneGroup;
/*    */ import com.xpsoft.oa.service.communicate.PhoneGroupService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PhoneGroupServiceImpl extends BaseServiceImpl<PhoneGroup>
/*    */   implements PhoneGroupService
/*    */ {
/*    */   private PhoneGroupDao dao;
/*    */ 
/*    */   public PhoneGroupServiceImpl(PhoneGroupDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public Integer findLastSn(Long userId)
/*    */   {
/* 23 */     return this.dao.findLastSn(userId);
/*    */   }
/*    */ 
/*    */   public PhoneGroup findBySn(Integer sn, Long userId)
/*    */   {
/* 28 */     return this.dao.findBySn(sn, userId);
/*    */   }
/*    */ 
/*    */   public List<PhoneGroup> findBySnUp(Integer sn, Long userId)
/*    */   {
/* 33 */     return this.dao.findBySnUp(sn, userId);
/*    */   }
/*    */ 
/*    */   public List<PhoneGroup> findBySnDown(Integer sn, Long userId)
/*    */   {
/* 38 */     return this.dao.findBySnDown(sn, userId);
/*    */   }
/*    */ 
/*    */   public List<PhoneGroup> getAll(Long userId)
/*    */   {
/* 43 */     return this.dao.getAll(userId);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.communicate.impl.PhoneGroupServiceImpl
 * JD-Core Version:    0.6.0
 */
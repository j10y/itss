/*    */ package com.htsoft.oa.service.personal.impl;
/*    */ 
/*    */ import com.htsoft.core.service.impl.BaseServiceImpl;
/*    */ import com.htsoft.oa.dao.personal.DutySystemDao;
/*    */ import com.htsoft.oa.model.personal.DutySystem;
/*    */ import com.htsoft.oa.service.personal.DutySystemService;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DutySystemServiceImpl extends BaseServiceImpl<DutySystem>
/*    */   implements DutySystemService
/*    */ {
/*    */   private DutySystemDao dao;
/*    */ 
/*    */   public DutySystemServiceImpl(DutySystemDao dao)
/*    */   {
/* 17 */     super(dao);
/* 18 */     this.dao = dao;
/*    */   }
/*    */ 
/*    */   public DutySystem save(DutySystem duty)
/*    */   {
/* 25 */     if (DutySystem.DEFAULT.equals(duty.getIsDefault())) {
/* 26 */       this.dao.updateForNotDefult();
/*    */     }
/* 28 */     this.dao.save(duty);
/* 29 */     return duty;
/*    */   }
/*    */ 
/*    */   public DutySystem getDefaultDutySystem()
/*    */   {
/* 37 */     List list = this.dao.getDefaultDutySystem();
/* 38 */     if (list.size() > 0) {
/* 39 */       return (DutySystem)list.get(0);
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.personal.impl.DutySystemServiceImpl
 * JD-Core Version:    0.6.0
 */
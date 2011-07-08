/*    */ package com.xpsoft.oa.workflow.event;
/*    */ 
/*    */ import com.xpsoft.core.util.AppUtil;
/*    */ import com.xpsoft.oa.model.info.ShortMessage;
/*    */ import com.xpsoft.oa.model.personal.ErrandsRegister;
/*    */ import com.xpsoft.oa.model.system.AppUser;
/*    */ import com.xpsoft.oa.service.info.ShortMessageService;
/*    */ import com.xpsoft.oa.service.personal.ErrandsRegisterService;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.jbpm.api.listener.EventListener;
/*    */ import org.jbpm.api.listener.EventListenerExecution;
/*    */ import org.jbpm.api.model.OpenProcessInstance;
/*    */ 
/*    */ public class HolidayApprovalListener
/*    */   implements EventListener
/*    */ {
/* 26 */   private Log logger = LogFactory.getLog(HolidayApprovalListener.class);
/*    */   Short choice;
/*    */ 
/*    */   public void notify(EventListenerExecution execution)
/*    */     throws Exception
/*    */   {
/* 36 */     if (this.logger.isDebugEnabled()) {
/* 37 */       this.logger.info("enter the HolidayApprovalListener notify method...");
/*    */     }
/*    */ 
/* 40 */     OpenProcessInstance pi = execution.getProcessInstance();
/*    */ 
/* 42 */     Long dateId = (Long)pi.getVariable("dateId");
/*    */ 
/* 44 */     String superOption = (String)pi.getVariable("superOption");
/*    */ 
/* 46 */     if (dateId != null)
/*    */     {
/* 48 */       ErrandsRegisterService erService = (ErrandsRegisterService)AppUtil.getBean("errandsRegisterService");
/* 49 */       ShortMessageService smService = (ShortMessageService)AppUtil.getBean("shortMessageService");
/* 50 */       ErrandsRegister errandsRegister = (ErrandsRegister)erService.get(dateId);
/*    */ 
/* 52 */       if (errandsRegister != null) {
/* 53 */         errandsRegister.setStatus(this.choice);
/* 54 */         errandsRegister.setApprovalOption(superOption);
/* 55 */         erService.save(errandsRegister);
/*    */ 
/* 58 */         smService.save(AppUser.SYSTEM_USER, errandsRegister.getUserId().toString(), "你的请假申请  （" + errandsRegister.getDescp() + "），最终审批意见：" + superOption, ShortMessage.MSG_TYPE_SYS);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.workflow.event.HolidayApprovalListener
 * JD-Core Version:    0.6.0
 */
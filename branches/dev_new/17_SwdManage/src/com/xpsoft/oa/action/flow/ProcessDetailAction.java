/*    */ package com.xpsoft.oa.action.flow;
/*    */ 
/*    */ import com.xpsoft.core.web.action.BaseAction;
/*    */ import com.xpsoft.oa.model.flow.ProDefinition;
/*    */ import com.xpsoft.oa.service.flow.ProDefinitionService;
/*    */ import javax.annotation.Resource;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ProcessDetailAction extends BaseAction
/*    */ {
/*    */ 
/*    */   @Resource
/*    */   private ProDefinitionService proDefinitionService;
/*    */   private ProDefinition proDefinition;
/*    */ 
/*    */   public ProDefinition getProDefinition()
/*    */   {
/* 24 */     return this.proDefinition;
/*    */   }
/*    */ 
/*    */   public void setProDefinition(ProDefinition proDefinition) {
/* 28 */     this.proDefinition = proDefinition;
/*    */   }
/*    */ 
/*    */   public String execute() throws Exception
/*    */   {
/* 33 */     String defId = getRequest().getParameter("defId");
/* 34 */     this.proDefinition = ((ProDefinition)this.proDefinitionService.get(new Long(defId)));
/* 35 */     return "success";
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.action.flow.ProcessDetailAction
 * JD-Core Version:    0.6.0
 */
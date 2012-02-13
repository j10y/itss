/*    */ package com.xpsoft.core.command;
/*    */ 
/*    */ import com.xpsoft.core.util.ParamUtil;
/*    */ import java.util.Enumeration;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class ReportFilter
/*    */ {
/* 12 */   Map<String, Object> variables = new HashMap();
/*    */ 
/*    */   public ReportFilter() {
/*    */   }
/*    */ 
/*    */   public ReportFilter(HttpServletRequest request) {
/* 18 */     Enumeration paramEnu = request.getParameterNames();
/* 19 */     while (paramEnu.hasMoreElements()) {
/* 20 */       String paramName = (String)paramEnu.nextElement();
/*    */ 
/* 22 */       if (paramName.startsWith("Q_")) {
/* 23 */         String paramValue = request.getParameter(paramName);
/* 24 */         addFilter(paramName, paramValue);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void addFilter(String paramName, String value) {
/* 30 */     String[] fieldInfo = paramName.split("[_]");
/* 31 */     if (fieldInfo.length == 3)
/* 32 */       this.variables.put(fieldInfo[1], ParamUtil.convertObject(fieldInfo[2], value));
/*    */   }
/*    */ 
/*    */   public Map<String, Object> getVariables()
/*    */   {
/* 37 */     return this.variables;
/*    */   }
/*    */ 
/*    */   public void setVariables(Map<String, Object> variables) {
/* 41 */     this.variables = variables;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.command.ReportFilter
 * JD-Core Version:    0.6.0
 */
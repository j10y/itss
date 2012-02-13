/*    */ package com.xpsoft.core.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Enumeration;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class ResponseHeaderFilter
/*    */   implements Filter
/*    */ {
/*    */   private FilterConfig fc;
/*    */ 
/*    */   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 22 */     HttpServletResponse response = (HttpServletResponse)res;
/*    */ 
/* 24 */     for (Enumeration e = this.fc.getInitParameterNames(); e.hasMoreElements(); ) {
/* 25 */       String headerName = (String)e.nextElement();
/* 26 */       response.addHeader(headerName, this.fc.getInitParameter(headerName));
/*    */     }
/*    */ 
/* 29 */     chain.doFilter(req, response);
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig filterConfig) {
/* 33 */     this.fc = filterConfig;
/*    */   }
/*    */ 
/*    */   public void destroy() {
/* 37 */     this.fc = null;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.web.filter.ResponseHeaderFilter
 * JD-Core Version:    0.6.0
 */
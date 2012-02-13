/*    */ package com.xpsoft.core.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class GzipJsFilter
/*    */   implements Filter
/*    */ {
/* 20 */   Map headers = new HashMap();
/*    */ 
/*    */   public void destroy() {
/*    */   }
/*    */   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
/* 25 */     if ((req instanceof HttpServletRequest))
/* 26 */       doFilter((HttpServletRequest)req, (HttpServletResponse)res, chain);
/*    */     else
/* 28 */       chain.doFilter(req, res);
/*    */   }
/*    */ 
/*    */   public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 34 */     request.setCharacterEncoding("UTF-8");
/* 35 */     for (Iterator it = this.headers.entrySet().iterator(); it.hasNext(); ) {
/* 36 */       Map.Entry entry = (Map.Entry)it.next();
/* 37 */       response.addHeader((String)entry.getKey(), (String)entry.getValue());
/*    */     }
/* 39 */     chain.doFilter(request, response);
/*    */   }
/*    */ 
/*    */   public void init(FilterConfig config) throws ServletException {
/* 43 */     String headersStr = config.getInitParameter("headers");
/* 44 */     String[] headers = headersStr.split(",");
/* 45 */     for (int i = 0; i < headers.length; i++) {
/* 46 */       String[] temp = headers[i].split("=");
/* 47 */       this.headers.put(temp[0].trim(), temp[1].trim());
/*    */     }
/*    */   }
/*    */ }

/*    */ package com.xpsoft.core.web.filter;
/*    */ 
/*    */ import com.xpsoft.core.security.SecurityDataSource;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.springframework.security.AccessDeniedException;
/*    */ import org.springframework.security.Authentication;
/*    */ import org.springframework.security.GrantedAuthority;
/*    */ import org.springframework.security.context.SecurityContext;
/*    */ import org.springframework.security.context.SecurityContextHolder;
/*    */ import org.springframework.util.StringUtils;
/*    */ import org.springframework.web.filter.OncePerRequestFilter;
/*    */ 
/*    */ public class SecurityInterceptorFilter extends OncePerRequestFilter
/*    */ {
/* 28 */   private HashMap<String, Set<String>> roleUrlsMap = null;
/*    */   private SecurityDataSource securityDataSource;
/*    */ 
/*    */   public void setSecurityDataSource(SecurityDataSource securityDataSource)
/*    */   {
/* 33 */     this.securityDataSource = securityDataSource;
/*    */   }
/*    */ 
/*    */   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
/*    */     throws ServletException, IOException
/*    */   {
/* 39 */     String url = request.getRequestURI();
/*    */ 
/* 41 */     if (StringUtils.hasLength(request.getContextPath())) {
/* 42 */       String contextPath = request.getContextPath();
/* 43 */       int index = url.indexOf(contextPath);
/* 44 */       if (index != -1) {
/* 45 */         url = url.substring(index + contextPath.length());
/*    */       }
/*    */     }
/* 48 */     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
/*    */ 
/* 50 */     boolean isSuperUser = false;
/* 51 */     for (int i = 0; i < auth.getAuthorities().length; i++)
/*    */     {
/* 53 */       if ("超级管理员".equals(auth.getAuthorities()[i].getAuthority())) {
/* 54 */         isSuperUser = true;
/* 55 */         break;
/*    */       }
/*    */     }
/* 58 */     if ((!isSuperUser) && 
/* 59 */       (!isUrlGrantedRight(url, auth))) {
/* 60 */       if (this.logger.isDebugEnabled()) {
/* 61 */         this.logger.info("ungranted url:" + url);
/*    */       }
/* 63 */       throw new AccessDeniedException("Access is denied! Url:" + url + " User:" + SecurityContextHolder.getContext().getAuthentication().getName());
/*    */     }
/*    */ 
/* 66 */     if (this.logger.isDebugEnabled()) {
/* 67 */       this.logger.debug("pass the url:" + url);
/*    */     }
/*    */ 
/* 70 */     chain.doFilter(request, response);
/*    */   }
/*    */ 
/*    */   private boolean isUrlGrantedRight(String url, Authentication auth)
/*    */   {
/* 80 */     for (GrantedAuthority ga : auth.getAuthorities()) {
/* 81 */       Set urlSet = (Set)this.roleUrlsMap.get(ga.getAuthority());
/*    */ 
/* 83 */       if ((urlSet != null) && (urlSet.contains(url))) {
/* 84 */         return true;
/*    */       }
/*    */     }
/* 87 */     return false;
/*    */   }
/*    */ 
/*    */   public void loadDataSource() {
/* 91 */     this.roleUrlsMap = this.securityDataSource.getDataSource();
/*    */   }
/*    */ 
/*    */   public void afterPropertiesSet() throws ServletException
/*    */   {
/* 96 */     loadDataSource();
/* 97 */     if (this.roleUrlsMap == null)
/* 98 */       throw new RuntimeException("没有进行设置系统的权限匹配数据源");
/*    */   }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.web.filter.SecurityInterceptorFilter
 * JD-Core Version:    0.6.0
 */
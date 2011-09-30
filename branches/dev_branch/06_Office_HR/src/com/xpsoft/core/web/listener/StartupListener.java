package com.xpsoft.core.web.listener;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xpsoft.core.security.SecurityDataSource;
import com.xpsoft.core.util.AppUtil;

public class StartupListener extends ContextLoaderListener {
	private static Log logger = LogFactory
			.getLog(StartupListener.class);

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		AppUtil.init(event.getServletContext());

		boolean isAynMenu = AppUtil.getIsSynMenu();

		if (isAynMenu)
			AppUtil.synMenu();
		
		//add by jack for cas resource loaded when application startup at 2011-9-30 begin
		//测试用，上线后去除
		//ServletContext servletContext = event.getServletContext();
		//SecurityDataSource sds = this.getSecurityManager(servletContext);
		//Map<String, Set<String>> urlAuthorities = sds.getDataSource();
		//servletContext.setAttribute("urlAuthorities", urlAuthorities);
		//add by jack for cas resource loaded when application startup at 2011-9-30 end
		
	}
	
	 protected SecurityDataSource getSecurityManager(ServletContext servletContext) {
		 return (SecurityDataSource) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean("securityDataSource");
	 }
}

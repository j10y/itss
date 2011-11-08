package com.xp.commonpart.util;


import java.util.Locale;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 覆盖ContextLoaderListener，当Spring的ContextLoaderListener被容器加载以后，
 * 取出ApplicationContext，置入ApplicationContext存放器ContextHolder�?
 * @Class Name PmcContextLoaderListener
 */
public class DefaultContextLoaderListener extends ContextLoaderListener {

	private final Log logger = LogFactory.getLog(getClass());
	
	public void contextInitialized(ServletContextEvent event) {
		
		super.contextInitialized(event);
		
		logger.info("load WebApplicationContext into ContextHolder");
		
		WebApplicationContext context = WebApplicationContextUtils.   
	    getWebApplicationContext(event.getServletContext()); 
		
		ContextHolder.getInstance().setApplicationContext(context);  
		ContextHolder.getInstance().setLocal(Locale.getDefault());		
	}
}

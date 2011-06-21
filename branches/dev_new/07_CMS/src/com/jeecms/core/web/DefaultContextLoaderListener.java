package com.jeecms.core.web;

import java.util.Locale;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 常规引用，方便使用
 * @Class Name DefaultContextLoaderListener
 * @Author Jack
 * @Create In 2007-11-12
 */
public class DefaultContextLoaderListener extends ContextLoaderListener {

	private final Log logger = LogFactory.getLog(getClass());
	
	@SuppressWarnings("static-access")
	public void contextInitialized(ServletContextEvent event) {
		
		super.contextInitialized(event);

		logger.info("load WebApplicationContext into ContextHolder");
		
		WebApplicationContext context = WebApplicationContextUtils.   
	    getWebApplicationContext(event.getServletContext()); 
		ContextHolder.getInstance().setApplicationContext(context);  
		ContextHolder.getInstance().setLocal(Locale.getDefault());
		
	}
}

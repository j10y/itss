package com.xpsoft.framework.web.listener;
 

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.xpsoft.framework.cache.CacheManager;
import com.xpsoft.framework.context.ContextHolder;
import com.xpsoft.framework.util.PropertiesUtil;


/**
 * 覆盖ContextLoaderListener，当Spring的ContextLoaderListener被容器加载以后，
 * 取出ApplicationContext，置入ApplicationContext存放器ContextHolder，
 * @Class Name DefaultContextLoaderListener
 * @Author likang
 * @Create In Jul 20, 2010
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
		//加载缓存
		CacheManager.getInstance();
		//初始化页码大小
		initPageSize(event);
		ServletContext servletContext = event.getServletContext();
	}
	
	/**
	 * 初始化页码
	 * @Methods Name initPageSize
	 * @Create In Aug 20, 2010 By likang void
	 */
	private void initPageSize(ServletContextEvent event) {
		// TODO Auto-generated method stub
		int pageSize = Integer.parseInt(PropertiesUtil.getProperties("system.pageSize", "10"));
		event.getServletContext().setAttribute("pageSize", pageSize);
	}
	
}


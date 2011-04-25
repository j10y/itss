package com.digitalchina.info.framework.web.listener;

import java.util.Locale;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.security.cache.AcegiCacheService;
import com.digitalchina.info.framework.util.PropertiesUtil;

/**
 * 覆盖ContextLoaderListener，当Spring的ContextLoaderListener被容器加载以后，
 * 取出ApplicationContext，置入ApplicationContext存放器ContextHolder，
 * @Class Name PmcContextLoaderListener
 * @Author xiaofeng
 * @Create In 2007-11-12
 */
public class DefaultContextLoaderListener extends ContextLoaderListener {

	private final Log logger = LogFactory.getLog(getClass());
	
	public void contextInitialized(ServletContextEvent event) {
		
		super.contextInitialized(event);
		//add by liuying at 20100602 for 保存服务绝对路径 start
		String mainPath=event.getServletContext().getRealPath("/");
		System.setProperty("mainPath", mainPath);
		//add by liuying at 20100602 for 保存服务绝对路径 end
		logger.info("load WebApplicationContext into ContextHolder");
		
		WebApplicationContext context = WebApplicationContextUtils.   
	    getWebApplicationContext(event.getServletContext()); 
		ContextHolder.getInstance().setApplicationContext(context);  
		ContextHolder.getInstance().setLocal(Locale.getDefault());
		String debug=PropertiesUtil.getProperties("system.rulebase.debug", "true");
		if(debug.equals("false")){
			ContextHolder.getInstance().getRuleBase();
		}
		AcegiCacheService acegiCacheMng = (AcegiCacheService)context.getBean("acegiCacheService");
//		acegiCacheMng.initUserCache();
//		acegiCacheMng.initResourceCache();		
	}


	

}

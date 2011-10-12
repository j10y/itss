package com.zsgj.info.framework.web.listener;

import java.util.Locale;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.security.cache.AcegiCacheService;
import com.zsgj.info.framework.util.PropertiesUtil;

/**
 * ����ContextLoaderListener����Spring��ContextLoaderListener�����������Ժ�
 * ȡ��ApplicationContext������ApplicationContext�����ContextHolder��
 * @Class Name PmcContextLoaderListener
 * @Author xiaofeng
 * @Create In 2007-11-12
 */
public class DefaultContextLoaderListener extends ContextLoaderListener {

	private final Log logger = LogFactory.getLog(getClass());
	
	@SuppressWarnings("static-access")
	public void contextInitialized(ServletContextEvent event) {
		
		super.contextInitialized(event);
		//add by liuying at 20100602 for ����������·�� start
		String mainPath=event.getServletContext().getRealPath("/");
		System.setProperty("mainPath", mainPath);
		//add by liuying at 20100602 for ����������·�� end
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
		if(!"false".equalsIgnoreCase(PropertiesUtil.getProperties("system.security.isUserCache", "false"))){
			acegiCacheMng.initUserCache();
		}
		if(!"false".equalsIgnoreCase(PropertiesUtil.getProperties("system.security.isResourceCache", "false"))){
			acegiCacheMng.initResourceCache();	
		}
	}


	

}
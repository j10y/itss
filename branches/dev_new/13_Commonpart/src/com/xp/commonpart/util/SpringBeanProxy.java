package com.xp.commonpart.util;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class SpringBeanProxy {
	private static Logger logger = Logger.getLogger(SpringBeanProxy.class);
	private static ApplicationContext applicationContext = null;
	
	static synchronized void setApplicationContext(ApplicationContext applicationContext) {
		SpringBeanProxy.applicationContext = applicationContext;
	}
	
	/**
	 * 通过bean名称获得相应的bean对象
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		if (applicationContext == null) {
			logger.info("Application Environment error!");
			throw new RuntimeException("Application Environment error!");
		}
		return applicationContext.getBean(beanName);
	}
}

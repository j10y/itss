package com.jeecms.core.web;

import java.util.Locale;

import org.springframework.context.ApplicationContext;


/**
 * ApplicationContext操作
 * @Class Name ContextHolder
 * @Author jack
 * @Create In 2007-11-12
 */
public class ContextHolder {

	private final static ContextHolder instance = new ContextHolder();

	private static ApplicationContext ac;
	
	private static Locale local;
	
	private ContextHolder() {
	}

	public static ContextHolder getInstance() {
		return instance;
	}

	@SuppressWarnings("static-access")
	public synchronized void setApplicationContext(ApplicationContext ac) {
		this.ac = ac;
	}

	public static ApplicationContext getApplicationContext() {
		return ac;
	}
	
	/**
	 * 获取Bean定义
	 * @Methods Name getBean
	 * @Create In 2008-10-6 By sa
	 * @param name
	 * @return Object
	 */
	@SuppressWarnings("static-access")
	public static Object getBean(String name){
		return ContextHolder.getInstance().getApplicationContext().getBean(name);
	}

	public static Locale getLocal() {
		return local;
	}

	public static void setLocal(Locale local) {
		ContextHolder.local = local;
	}
}

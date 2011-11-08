package com.xp.commonpart.util;


import java.util.Locale;

import org.springframework.context.ApplicationContext;


/**
 * ApplicationContext存放�?, 便于当容器启动以后，在任意位置获得ApplicationContext
 * @Class Name ContextHolder
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

	public synchronized void setApplicationContext(ApplicationContext ac) {
		this.ac = ac;
	}

	public static ApplicationContext getApplicationContext() {
		return ac;
	}
	
	/**
	 * 提供bean定义的名称，返回spring管理的bean
	 * @Methods Name getBean
	 * @param name
	 * @return Object
	 */
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

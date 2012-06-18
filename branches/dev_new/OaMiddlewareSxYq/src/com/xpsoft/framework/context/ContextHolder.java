package com.xpsoft.framework.context;

import java.util.Locale;

import org.springframework.context.ApplicationContext;

/**
 * ApplicationContext存放器, 便于当容器启动以后，在任意位置获得ApplicationContext
 * @Class Name ContextHolder
 * @Author likang
 * @Create In Jul 20, 2010
 */
public class ContextHolder {
	
	//单例模式
	private final static ContextHolder instance = new ContextHolder();
	
	//Spring上下文
	private static ApplicationContext ac;
	
	//本地区域
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
	 * @Create In Jul 20, 2010 By likang
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


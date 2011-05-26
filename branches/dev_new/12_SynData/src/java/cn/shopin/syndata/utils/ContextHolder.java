package cn.shopin.syndata.utils;

import java.util.Locale;

import org.springframework.context.ApplicationContext;


/**
 * ApplicationContext存放器, 便于当容器启动以后，在任意位置获得ApplicationContext
 * @Class Name ContextHolder
 * @Author xiaofeng
 * @Create In 2007-11-12
 */
public class ContextHolder {

	private final static ContextHolder instance = new ContextHolder();

	private static ApplicationContext ac;
	
	private static Locale local;
	
	private static boolean isRuleBaseSetting;
	
	/**
	 * @Return the boolean isRuleBaseSetting
	 */
	public static boolean isRuleBaseSetting() {
		return isRuleBaseSetting;
	}

	/**
	 * @Param boolean isRuleBaseSetting to set
	 */
	public static void setRuleBaseSetting(boolean isRuleBaseSetting) {
		ContextHolder.isRuleBaseSetting = isRuleBaseSetting;
	}

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
	 * 提供bean定义的名称，返回spring管理的bean
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

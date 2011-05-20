package com.zsgj.info.framework.workflow.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.ApplicationContext;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.workflow.WorkflowConstants;
/**
 * 工作流配置类，用于总体的参数配置
 * @Class Name JbpmConfig
 * @Author yang
 * @Create In 2008-6-10
 */
public class JbpmConfig implements WorkflowConstants{
	//定制的Action所在包
	public static String ACTION_PACKAGE = null;
	
	//流程图所在包
	public static String JPDL_PACKAGE = null;
	
	//SPRING容器中的SessionFactory名称
	public static String SPRING_SESSION_FACTORY_NAME = null;
		
	//Action变化感知超时
	public static String ACTION_TIMEOUT = "10";
	
	//查看是否有配置文件存在
	//如果有，用配置文件数据覆盖原数据
	static {
		init();
	}	
	
	//初始化，可以在外部重新初始化
	public static void init() {
		ACTION_PACKAGE = getProperties("workflow.ACTION_PACKAGE",DEFAULT_ACTION_PACKAGE).trim();
		JPDL_PACKAGE = getProperties("workflow.JPDL_PACKAGE",DEFAULT_JPDL_PACKAGE).trim(); 
		ACTION_TIMEOUT = getProperties("workflow.ACTION_TIMEOUT","60000").trim(); 
		SPRING_SESSION_FACTORY_NAME = getProperties("workflow.SPRING_SESSION_FACTORY_NAME","pmcSessionFactory").trim(); 
	}
	
	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	@SuppressWarnings("static-access")
	private static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try{
			message = appContext.getMessage(Key, new Object[0],ContextHolder.getInstance().getLocal());
			return (message != null && !message.equals("") ? message : defaultValue);
		}catch(Exception e){
			return defaultValue;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
	}

}

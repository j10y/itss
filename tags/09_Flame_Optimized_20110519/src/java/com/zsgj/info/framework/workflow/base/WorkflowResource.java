package com.zsgj.info.framework.workflow.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WorkflowResource {
	private static Properties prop;
	
	static {
		//加载默认的属性文件
		prop = new Properties();
		InputStream in = WorkflowResource.class.getClassLoader().getResourceAsStream("workflowResource.properties");			
		try {
			prop.load(in);
		} catch (IOException e) {
			System.out.println("No workflowResource.properties file defined error");
		}
	}
	
	
	
	public static String getMessage(String msgCode) {
		return prop.getProperty(msgCode).trim();
	}
	
	public static String getMessage(String msgCode, String Default) {
		return prop.getProperty(msgCode,Default).trim();
	}

	
	public static void loadPropertyFile(String resourceName) {		
		//加载指定的属性文件
		prop = new Properties();
		InputStream in = WorkflowResource.class.getClassLoader().getResourceAsStream(resourceName);			
		try {
			prop.load(in);
		} catch (IOException e) {
			System.out.println("No "+resourceName+" file defined error");
		}	
	}

	/**
	 * @Methods Name main
	 * @Create In Mar 23, 2009 By Administrator
	 * @param args 
	 * @ReturnType void
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

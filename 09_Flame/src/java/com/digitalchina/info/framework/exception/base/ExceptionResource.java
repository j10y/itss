package com.digitalchina.info.framework.exception.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.digitalchina.info.framework.exception.ExceptionMessageFactory;

/**
 * �û��Զ�����Դ�ļ�������
 * @Class Name ExceptionResource
 * @Author zhangpeng
 * @Create In Mar 6, 2008
 */
public class ExceptionResource {

	private static Properties prop;
	
	public static String getExceptionMessage(String errorCode) {
		return prop.getProperty(errorCode).trim();
	}
	
	public static String getExceptionMessage(String errorCode, String Default) {
		return prop.getProperty(errorCode,Default).trim();
	}

	public ExceptionResource() {
		
		//���ط����쳣�����ļ�
		prop = new Properties();
		InputStream in = ExceptionMessageFactory.class
				.getClassLoader().getResourceAsStream("serviceException.properties");	
		
		try {
			prop.load(in);
		} catch (IOException e) {
			System.out.println("No serviceException.properties defined error");
		}
		
	}
	
	public ExceptionResource(String resourceName) {
		
		//���ط����쳣�����ļ�
		prop = new Properties();
		InputStream in = ExceptionMessageFactory.class
				.getClassLoader().getResourceAsStream(resourceName);	
		
		try {
			prop.load(in);
		} catch (IOException e) {
			System.out.println("No serviceException.properties defined error");
		}
		
	}
}

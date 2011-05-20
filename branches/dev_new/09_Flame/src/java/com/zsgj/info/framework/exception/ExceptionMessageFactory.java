// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExceptionMessageFactory.java

package com.zsgj.info.framework.exception;

import com.zsgj.info.framework.exception.base.ExceptionFrameWorkResource;
import com.zsgj.info.framework.exception.base.ExceptionResource;

/**
 * ������Ϣ����
 * @Class Name ExceptionMessageFactory
 * @author xiaofeng
 * @Create In 2007-10-30
 * TODO
 */
public class ExceptionMessageFactory {
	
	private static ExceptionFrameWorkResource appResource;
	
	private static ExceptionResource customerResource;
	
	ExceptionMessageFactory(boolean isDefault,String path){
		if(isDefault){
			appResource = new ExceptionFrameWorkResource();
		}else{
			customerResource = new ExceptionResource(path);
		}
	}
	
	/**
	 * ��ȡ�쳣��Ϣʵ��
	 * @Methods Name getInstance
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param isDefault �Ƿ�ΪĬ��ϵͳ��Դ
	 * @param path �ͻ��Զ�����Դ�ļ�λ��
	 * @return ExceptionMessageFactory
	 */
	public static ExceptionMessageFactory getInstance(boolean isDefault,String path){
		return new ExceptionMessageFactory(isDefault,path);
	}
	
	/**
	 * ��ȡ�쳣��Ϣ
	 * @Methods Name getExceptionMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key �쳣��ϢKey
	 * @return �쳣��Ϣ
	 */
	@SuppressWarnings("static-access")
	public static String getExceptionMessage(String Key){
		if(appResource != null){
			return appResource.getFrameWorkMessage(Key);
		}else if(customerResource != null){
			return customerResource.getExceptionMessage(Key);
		}else{
			return "";
		}
	}
	
	/**
	 * ��ȡ�쳣��Ϣ
	 * @Methods Name getExceptionMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key �쳣��ϢKey
	 * @param Default Ĭ���쳣��Ϣ
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getExceptionMessage(String Key, String Default){
		if(appResource != null){
			return appResource.getFrameWorkMessage(Key,Default);
		}else if(customerResource != null){
			return customerResource.getExceptionMessage(Key,Default);
		}else{
			return "";
		}
	}
}

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ExceptionMessageFactory.java

package com.zsgj.info.framework.exception;

import com.zsgj.info.framework.exception.base.ExceptionFrameWorkResource;
import com.zsgj.info.framework.exception.base.ExceptionResource;

/**
 * 错误消息工厂
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
	 * 获取异常消息实体
	 * @Methods Name getInstance
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param isDefault 是否为默认系统资源
	 * @param path 客户自定义资源文件位置
	 * @return ExceptionMessageFactory
	 */
	public static ExceptionMessageFactory getInstance(boolean isDefault,String path){
		return new ExceptionMessageFactory(isDefault,path);
	}
	
	/**
	 * 获取异常信息
	 * @Methods Name getExceptionMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key 异常信息Key
	 * @return 异常信息
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
	 * 获取异常信息
	 * @Methods Name getExceptionMessage
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key 异常信息Key
	 * @param Default 默认异常信息
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

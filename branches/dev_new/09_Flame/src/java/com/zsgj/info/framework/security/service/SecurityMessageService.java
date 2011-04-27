package com.zsgj.info.framework.security.service;

public interface SecurityMessageService {
	/**
	 * 安全模块信息服务获取详细信息
	 * @Methods Name getMessage
	 * @Create In 2008-5-9 By zhangpeng
	 * @param key 资源文件KEy
	 * @param defaults 默认信息
	 * @return String
	 */
	public String getMessage(String key,String defaults);
	
	/**
	 * 安全模块信息服务获取详细信息
	 * @Methods Name getMessage
	 * @Create In 2008-5-9 By zhangpeng
	 * @param key 资源文件KEy
	 * @return String
	 */
	public String getMessage(String key);
}

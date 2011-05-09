package com.zsgj.info.framework.security.service;

public interface LDAPAuthenticationService {
	
	/**
	 * 有效性验证
	 * @param username 用户名  
	 * @param pwd 密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticated(String username,String pwd);
	
	/**
	 * 有效性验证
	 * @param domain Ldap服务器路径,可以使名称或者IP地址
	 * @param username 用户名
	 * @param pwd 密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticated(String domain ,String username,String pwd);
	
	
	/**
	 * Web Servers有效性验证
	 * @param UserName 用户名
	 * @param Pwd 密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticatedByWebServer(String UserName,String Pwd);
	
	
	/**
	 * 有效性验证
	 * @param domain Ldap服务器路径,可以使名称或者IP地址
	 * @param username 用户名
	 * @param pwd 密码
	 * @return 成功返回true
	 */
	boolean IsAuthenticatedByLdap(String domain, String username, String pwd);
	
	/**
	 * 有效性验证
	 * 
	 * @param username
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 成功返回true
	 */
	public boolean IsAuthenticatedByLdap(String username, String pwd);
}

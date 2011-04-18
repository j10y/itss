package com.digitalchina.info.framework.message.sametime;

import javax.servlet.http.HttpSession;

public interface SameTimeService {
	
	/**
	 * 登陆SameTime
	 * @Methods Name login
	 * @Create In Feb 1, 2008 By Iceman
	 * @param loginUser 用户名
	 * @param passWord 密码
	 * @param jsMethod 页面JS方法名
	 * @param reqSession void
	 */
	public void login(String loginUser,String passWord,HttpSession reqSession,String jsMethod);
	
	/**
	 * 登出SameTime
	 * @Methods Name logout
	 * @Create In Feb 1, 2008 By Iceman void
	 */
	public void logout();
	
	/**
	 * 是否已经登陆成功
	 * @Methods Name isLoginScuess
	 * @Create In Mar 5, 2008 By zhangpeng
	 * @return boolean
	 */
	public boolean isLoginScuess();
	
	/**
	 * 是否发生错误
	 * @Methods Name isHasError
	 * @Create In Mar 5, 2008 By zhangpeng
	 * @return boolean
	 */
	public boolean isHasError();
}

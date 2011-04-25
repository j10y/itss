/**
 * @Probject Name: FrameworkB2B
 * @Path: com.digitalchina.info.framework.security.serviceAuthentication.java
 * @Create By 张鹏
 * @Create In Sep 25, 2008 12:02:36 PM
 * TODO
 */
package com.digitalchina.info.framework.security.service;

import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * @Class Name Authentication
 * @Author 张鹏
 * @Create In Sep 25, 2008
 */
public interface AuthenticationCust extends org.acegisecurity.Authentication {
	
	/**
	 * 获取当前登录用户
	 * @Methods Name getCurrentUserInfo
	 * @Create In Sep 26, 2008 By 张鹏
	 * @return UserInfo
	 */
	public UserInfo getCurrentUserInfo();
	
	/**
	 * 修改当前登录用户
	 * @Methods Name setCurrentUserInfo
	 * @Create In Sep 26, 2008 By 张鹏 void
	 */
	public void setCurrentUserInfo(UserInfo userInfo);
}

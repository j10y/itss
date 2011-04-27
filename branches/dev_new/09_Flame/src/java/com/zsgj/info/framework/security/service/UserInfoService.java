/**
 * @Probject Name: 10_InfoFramework
 * @Path: com.digitalchina.info.framework.security.ServiceUserInfoService.java
 * @Create By zhangpeng
 * @Create In Apr 3, 2008 10:33:44 AM
 * TODO
 */
package com.zsgj.info.framework.security.service;

import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * @Class Name UserInfoService
 * @Author zhangpeng
 * @Create In Apr 3, 2008
 */
public interface UserInfoService {
	
	/**
	 * 根据用户登陆名称或取用户详细信息
	 * @Methods Name findUserInfoByUserName
	 * @Create In Apr 3, 2008 By zhangpeng
	 * @param userName 用户名
	 * @return 
	 * UserInfo
	 */
	public UserInfo findUserInfoByUserName(String userName);
}

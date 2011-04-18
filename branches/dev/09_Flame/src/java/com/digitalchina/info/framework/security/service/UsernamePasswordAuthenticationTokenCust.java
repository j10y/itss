/**
 * @Probject Name: FrameworkB2B
 * @Path: com.digitalchina.info.framework.security.serviceUsernamePasswordAuthenticationToken.java
 * @Create By 张鹏
 * @Create In Sep 25, 2008 11:51:58 AM
 * TODO
 */
package com.digitalchina.info.framework.security.service;

import org.acegisecurity.GrantedAuthority;

import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * @Class Name UsernamePasswordAuthenticationToken
 * @Author 张鹏
 * @Create In Sep 25, 2008
 */
public class UsernamePasswordAuthenticationTokenCust extends
		org.acegisecurity.providers.UsernamePasswordAuthenticationToken implements AuthenticationCust{
	
	/**
	 * 当前登录用户信息
	 */
	private UserInfo currentUserInfo;
	
	public UsernamePasswordAuthenticationTokenCust(Object principal,
			Object credentials, GrantedAuthority[] authorities) {
		super(principal, credentials, authorities);
		// TODO Auto-generated constructor stub
	}
	
	public UsernamePasswordAuthenticationTokenCust(Object principal,
			Object credentials) {
		super(principal, credentials);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -1277043888301386615L;

	/**
	 * @Return the UserInfo currentUserInfo
	 */
	public UserInfo getCurrentUserInfo() {
		return currentUserInfo;
	}

	/**
	 * @Param UserInfo currentUserInfo to set
	 */
	public void setCurrentUserInfo(UserInfo currentUserInfo) {
		this.currentUserInfo = currentUserInfo;
	}
}

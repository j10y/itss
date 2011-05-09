/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.digitalchina.info.framework.security.service.implUser.java
 * @Create By zhangpeng
 * @Create In 2008-5-16 上午11:52:22
 * TODO
 */
package com.zsgj.info.framework.security.service.impl;

import org.acegisecurity.GrantedAuthority;

import com.zsgj.info.framework.security.entity.UserDetails;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * @Class Name User
 * @Author zhangpeng
 * @Create In 2008-5-16
 */
public class User extends org.acegisecurity.userdetails.User implements
		UserDetails {

	/**
	 * @Field long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否特殊类型用户
	 */
	private UserInfo currentUserInfo;

	public User(String username, String password, boolean enabled,
			GrantedAuthority[] authorities) throws IllegalArgumentException {
		super(username, password, enabled, true, true, authorities);
	}

	public User(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			GrantedAuthority[] authorities) throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, true, authorities);
	}

	public User(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, GrantedAuthority[] authorities,
			UserInfo currentUserInfo) throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);

		this.currentUserInfo = currentUserInfo;

	}
	
    public User(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, GrantedAuthority[] authorities)
            throws IllegalArgumentException {
    	super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.service.UserDetails#isSpecialUser()
	 */
	public boolean isSpecialUser() {
		// TODO Auto-generated method stub
		return this.currentUserInfo.isSpecialUserInfo();
	}

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

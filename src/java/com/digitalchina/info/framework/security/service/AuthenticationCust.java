/**
 * @Probject Name: FrameworkB2B
 * @Path: com.digitalchina.info.framework.security.serviceAuthentication.java
 * @Create By ����
 * @Create In Sep 25, 2008 12:02:36 PM
 * TODO
 */
package com.digitalchina.info.framework.security.service;

import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * @Class Name Authentication
 * @Author ����
 * @Create In Sep 25, 2008
 */
public interface AuthenticationCust extends org.acegisecurity.Authentication {
	
	/**
	 * ��ȡ��ǰ��¼�û�
	 * @Methods Name getCurrentUserInfo
	 * @Create In Sep 26, 2008 By ����
	 * @return UserInfo
	 */
	public UserInfo getCurrentUserInfo();
	
	/**
	 * �޸ĵ�ǰ��¼�û�
	 * @Methods Name setCurrentUserInfo
	 * @Create In Sep 26, 2008 By ���� void
	 */
	public void setCurrentUserInfo(UserInfo userInfo);
}

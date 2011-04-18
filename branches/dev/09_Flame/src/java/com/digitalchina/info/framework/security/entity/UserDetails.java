/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.digitalchina.info.framework.security.serviceUserDetails.java
 * @Create By zhangpeng
 * @Create In 2008-5-16 ����11:50:38
 * TODO
 */
package com.digitalchina.info.framework.security.entity;


/**
 * @Class Name UserDetails
 * @Author zhangpeng
 * @Create In 2008-5-16
 */
public interface UserDetails extends org.acegisecurity.userdetails.UserDetails {

	/**
	 * �ж��Ƿ������û�����
	 * @Methods Name isSpecialUser
	 * @Create In 2008-5-16 By zhangpeng
	 * @return boolean
	 */
	public boolean isSpecialUser();
	
	public UserInfo getCurrentUserInfo();
}

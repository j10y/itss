/**
 * @Probject Name: 10_InfoFramework_1
 * @Path: com.digitalchina.info.framework.security.serviceLogUserActionService.java
 * @Create By ����
 * @Create In 2009-7-27 ����02:40:40
 * TODO
 */
package com.digitalchina.info.framework.security.service;

import javax.servlet.http.HttpServletRequest;

import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ��¼�û���������ķ���
 * @Class Name LogUserActionService
 * @Author ����
 * @Create In 2009-7-27
 */
public interface LogUserActionService {
	
	/**
	 * ��¼�û�������־
	 * @Methods Name logUserAction
	 * @Create In 2009-7-27 By ����
	 * @param request void
	 */
	public void saveUserActionLog(HttpServletRequest request, UserInfo user);

}

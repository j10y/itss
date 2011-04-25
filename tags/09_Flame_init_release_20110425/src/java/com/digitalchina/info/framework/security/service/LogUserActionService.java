/**
 * @Probject Name: 10_InfoFramework_1
 * @Path: com.digitalchina.info.framework.security.serviceLogUserActionService.java
 * @Create By 张鹏
 * @Create In 2009-7-27 下午02:40:40
 * TODO
 */
package com.digitalchina.info.framework.security.service;

import javax.servlet.http.HttpServletRequest;

import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 记录用户具体操作的服务
 * @Class Name LogUserActionService
 * @Author 张鹏
 * @Create In 2009-7-27
 */
public interface LogUserActionService {
	
	/**
	 * 记录用户访问日志
	 * @Methods Name logUserAction
	 * @Create In 2009-7-27 By 张鹏
	 * @param request void
	 */
	public void saveUserActionLog(HttpServletRequest request, UserInfo user);

}

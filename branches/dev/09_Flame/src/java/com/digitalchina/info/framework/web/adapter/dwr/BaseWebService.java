/**
 * @Probject Name: 10_InfoFramework
 * @Path: com.digitalchina.info.framework.web.adapterBaseWebService.java
 * @Create By 张鹏
 * @Create In Aug 20, 2008 5:57:02 PM
 * TODO
 */
package com.digitalchina.info.framework.web.adapter.dwr;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.service.Service;

/**
 * @Class Name BaseWebService
 * @Author 张鹏
 * @Create In Aug 20, 2008
 */
public abstract class BaseWebService {
	
	protected final Log logger = LogFactory.getLog("actionlog");
	
	/**
	 * 返回spring管理的服务service
	 * @Methods Name getBean
	 * @Create In 2008-3-3 By peixf
	 * @param name
	 * @return Object
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
	
	/**
	 * 获取基础服务
	 * @Methods Name getBaseService
	 * @Create In 2008-4-11 By peixf
	 * @return Service
	 */
	protected Service getService(){
		return (Service) getBean("baseService");
	}

	/**
	 * 获取当前登陆用户
	 * @Methods Name getAuthentication
	 * @Create In Apr 3, 2008 By zhangpeng
	 * @return 
	 * Authentication
	 */
	protected Authentication  getAuthentication(){
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			return auth;
		}catch(Exception e){
			throw new ServiceException("获取用户信息失败！！");
		}
	}
	

	/**
	 * 获取资源文件信息
	 * 
	 * @Methods Name getProperties
	 * @Create In Mar 6, 2008 By zhangpeng
	 * @param Key
	 *            资源文件Key
	 * @param defaultValue
	 *            默认信息
	 * @return String
	 */
	protected String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try{
			message = appContext.getMessage(Key, new Object[0],
				ContextHolder.getInstance().getLocal());
			
			return (message != null && !message.equals("") ? message : defaultValue);
		}catch(Exception e){
			logger.error(e.getMessage());
			return defaultValue;
		}
	}
	
	/**
	 * 验证是否是系统管理员
	 * 
	 * @Methods Name isSystemAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isSystemAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(this.getProperties("system.adminkey.systemadmin", "AUTH_SYS_ADMIN"))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否是用户管理员
	 * 
	 * @Methods Name isUserAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isUserAdmin(GrantedAuthority[] userDetails) {
		for (int i = 0; i < userDetails.length; i++) {
			if (userDetails[i].equals(this.getProperties("system.adminkey.useradmin", "ROLE_USER_ADMIN"))) {
				return true;
			}
		}
		return false;
	}
}

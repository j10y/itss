/**
 * @Probject Name: FrameworkB2B
 * @Path: com.digitalchina.info.framework.aop.interceptorUserOrResourceChageAfter.java
 * @Create By ’≈≈Ù
 * @Create In Oct 8, 2008 12:31:11 PM
 * TODO
 */
package com.digitalchina.info.framework.aop.interceptor;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

import com.digitalchina.info.framework.security.Constants;
import com.digitalchina.info.framework.security.cache.AcegiCacheService;
import com.digitalchina.info.framework.security.entity.Authorization;
import com.digitalchina.info.framework.security.entity.Resource;
import com.digitalchina.info.framework.security.entity.Right;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserDetails;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * @Class Name UserOrResourceChageAfter
 * @Author ’≈≈Ù
 * @Create In Oct 8, 2008
 */
public class UserOrResourceChageAdvice implements AfterReturningAdvice {

	private AcegiCacheService acegiCacheService;

	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) throws Throwable {
		// TODO Auto-generated method stub

		if (arg1.getName().indexOf("save") != -1) {
			if (arg0 instanceof UserInfo) {
				UserInfo user = (UserInfo) arg0;
				UserDetails newUser = this.acegiCacheService.buildUserDetail(user);
				this.acegiCacheService.modifyUserInCache(newUser, newUser
						.getUsername());
			} else if (arg0 instanceof Role) {
				Role role = (Role) arg0;
				this.acegiCacheService.authPermissionInCache(role);
			} else if (arg0 instanceof Resource) {
				Resource resource = (Resource) arg0;
				String resString = "";
				this.acegiCacheService.modifyResourceInCache(resource,resString);
			}else if(arg0 instanceof Right){
				Right right = (Right)arg0;
				this.acegiCacheService.modifyRightInCache(right, right.getName());
				
			}else if(arg0 instanceof Authorization){
				Authorization auth = (Authorization)arg0;
				this.acegiCacheService.modifyPermiInCache(auth, auth.getName());
			}
		} else if (arg1.getName().indexOf("remove") != -1) {
			if (arg2[0] instanceof UserInfo) {
				UserInfo user = (UserInfo) arg2[0];
				this.acegiCacheService.removeUser(user.getUserName());
			} else if (arg2[0] instanceof Role) {
				Role role = (Role) arg2[0];
				this.acegiCacheService.removeRole(role);
			} else if (arg2[0] instanceof Resource) {
				Resource resource = (Resource) arg2[0];
				String resString = "";
				if (resource.getType().equals(Constants.RESOURCE_URL)) {
					resString = (resource.getClassName() + "/" + resource
							.getMethodName());
				} else if (resource.getType()
						.equals(Constants.RESOURCE_FUNCTION)) {
					resString = (resource.getClassName() + "." + resource
							.getMethodName());
				}
				this.acegiCacheService.removeResource(resString);
			}else if(arg2[0] instanceof Authorization){
				Authorization auth = (Authorization)arg2[0];
				this.acegiCacheService.removePermi(auth);
			}
		}
	}

	/**
	 * @Return the AcegiCacheService acegiCacheService
	 */
	public AcegiCacheService getAcegiCacheService() {
		return acegiCacheService;
	}

	/**
	 * @Param AcegiCacheService
	 *            acegiCacheService to set
	 */
	public void setAcegiCacheService(AcegiCacheService acegiCacheService) {
		this.acegiCacheService = acegiCacheService;
	}
	
	
}

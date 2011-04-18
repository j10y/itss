/**
 * @Probject Name: 10_InfoFramework_B2
 * @Path: com.digitalchina.info.framework.security.service.implSecurityMessageServiceImpl.java
 * @Create By zhangpeng
 * @Create In 2008-5-9 ÏÂÎç04:52:44
 * TODO
 */
package com.digitalchina.info.framework.security.service.impl;

import org.springframework.context.ApplicationContext;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.security.service.SecurityMessageService;
import com.digitalchina.info.framework.service.BaseService;


/**
 * @Class Name SecurityMessageServiceImpl
 * @Author zhangpeng
 * @Create In 2008-5-9
 */
public class SecurityMessageServiceImpl extends BaseService implements SecurityMessageService {
	
	
	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.service.SecurityMessageService#getMessage(java.lang.String, java.lang.String)
	 */
	public String getMessage(String key, String defaults) {
		// TODO Auto-generated method stub
		return this.getProperties(key,defaults);
	}

	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.service.SecurityMessageService#getMessage(java.lang.String)
	 */
	public String getMessage(String key) {
		// TODO Auto-generated method stub
		return this.getProperties(key);
	}

}

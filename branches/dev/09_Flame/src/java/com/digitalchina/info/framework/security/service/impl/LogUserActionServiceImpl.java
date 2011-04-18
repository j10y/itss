/**
 * @Probject Name: 10_InfoFramework_1
 * @Path: com.digitalchina.info.framework.security.service.implLogUserActionServiceImpl.java
 * @Create By ����
 * @Create In 2009-7-27 ����02:43:18
 * TODO
 */
package com.digitalchina.info.framework.security.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.digitalchina.info.framework.security.dao.SecurityManageDao;
import com.digitalchina.info.framework.security.entity.UserActionLog;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.LogUserActionService;
import com.digitalchina.info.framework.service.BaseService;

/**
 * @Class Name LogUserActionServiceImpl
 * @Author ����
 * @Create In 2009-7-27
 */
public class LogUserActionServiceImpl extends BaseService implements LogUserActionService {
	
	/**
	 * �������־��¼��
	 */
	protected final Log logger = LogFactory.getLog("servicelog");
	
	/**
	 * ��ȫ����
	 */
	private SecurityManageDao securityManageDao;
	
	/**
	 * @Return the SecurityManageDao securityManageDao
	 */
	public SecurityManageDao getSecurityManageDao() {
		return securityManageDao;
	}

	/**
	 * @Param SecurityManageDao securityManageDao to set
	 */
	public void setSecurityManageDao(SecurityManageDao securityManageDao) {
		this.securityManageDao = securityManageDao;
	}

	/* (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.service.LogUserActionService#logUserAction(javax.servlet.http.HttpServletRequest, com.digitalchina.info.framework.security.entity.UserInfo)
	 */
	public void saveUserActionLog(HttpServletRequest request, UserInfo user) {
		// TODO Auto-generated method stub
		if(user != null){
			String target = request.getRequestURI();
			String clientIP = request.getLocalAddr();
			
			UserActionLog ual = new UserActionLog();
			
			ual.setTargetURL(target);
			ual.setUserName(user.getRealName() + "(" + user.getUserName() + ")");
			ual.setClientIP(clientIP);
			
			ual.setActionDate(new Date());
			ual.setRemark(user.getRealName() + "(" + user.getUserName() + ")������:" + clientIP);
		
			logger.info(user.getRealName() + "(" + user.getUserName()+ ")������:" + request.getRequestURI());
			
			//this.save(ual);
			
			this.securityManageDao.saveUserActionLog(ual);
		}
	}

}

/**
 * @Probject Name: 10_InfoFramework
 * @Path: com.zsgj.info.framework.security.Service.implUserInfoServiceImpl.java
 * @Create By zhangpeng
 * @Create In Apr 3, 2008 10:35:08 AM
 * TODO
 */
package com.zsgj.info.framework.security.service.impl;

import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.dao.UserInfoDao;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.UserInfoService;
import com.zsgj.info.framework.service.BaseService;

/**
 * @Class Name UserInfoServiceImpl
 * @Author zhangpeng
 * @Create In Apr 3, 2008
 */
public class UserInfoServiceImpl extends BaseService implements UserInfoService {
	
	private UserInfoDao uid ;
	/**
	 * @Return the UserInfoDao uid
	 */
	public UserInfoDao getUid() {
		return uid;
	}
	/**
	 * @Param UserInfoDao uid to set
	 */
	public void setUid(UserInfoDao uid) {
		this.uid = uid;
	}
	/* (non-Javadoc)
	 * @see com.zsgj.info.framework.security.Service.UserInfoService#findUserInfoByUserName(java.lang.String)
	 */
	public UserInfo findUserInfoByUserName(String userName) {
		// TODO Auto-generated method stub
		try{
			UserInfo user = uid.selectUserByUserName(userName);
			return user;
		}catch(Exception e){
			throw new ServiceException("检索用户信息错误，请联系管理员！");
		}
	}

}

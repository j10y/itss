package com.digitalchina.info.framework.security.dao;

import java.util.List;

import com.digitalchina.info.framework.security.entity.Authorization;
import com.digitalchina.info.framework.security.entity.Right;
import com.digitalchina.info.framework.security.entity.UserInfo;

public interface UserInfoDao {

	UserInfo insertOrUpdateUser(UserInfo userInfo);
	
	UserInfo selectUserInfo(String userName, String password);
	
	UserInfo selectUserByUserName(String userName);
	
	List selectUserByRight(Right right);
	
	List selectUserByAuthorization(Authorization authr);
	
	List selectAllUsers();
	
	void clearUser(UserInfo userInfo);
	
}

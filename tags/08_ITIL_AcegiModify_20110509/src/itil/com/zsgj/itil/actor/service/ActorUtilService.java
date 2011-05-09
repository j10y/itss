package com.zsgj.itil.actor.service;

import com.zsgj.info.framework.dao.support.Page;

public interface ActorUtilService {
	
	/**
	 * 获取全部用户信息
	 * @Methods Name getAllUser
	 * @Create In Dec 15, 2009 By lee
	 * @param userName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getAllUser(String userName,int pageNo,int pageSize);
}

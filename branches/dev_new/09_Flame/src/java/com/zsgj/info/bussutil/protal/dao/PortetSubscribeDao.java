package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;

import com.zsgj.info.framework.dao.Dao;

/**
 * 用户portal订阅DAO，未避免想demo里服务中写DAO代码，故新增此DAO
 * @Class Name UserPortetSubscribeDao
 * @Author peixf
 * @Create In 2008-10-23
 */
public interface PortetSubscribeDao extends Dao {
	
	/**
	 * 保存用户portal订阅
	 * @Methods Name saveUserPortletSubscribe
	 * @Create In 2008-10-23 By sa
	 * @param portalId
	 * @param portletId void
	 */
	void saveUserPortletSubscribe(Serializable portalId,
			Serializable portletId);
	
	/**
	 * 删除用户portal订阅
	 * @Methods Name removeUserPorletSubscribe
	 * @Create In 2008-10-23 By sa
	 * @param portletId
	 * @param portalId void
	 */
	void removeUserPorletSubscribe(Serializable portletId,
			Serializable portalId);
}

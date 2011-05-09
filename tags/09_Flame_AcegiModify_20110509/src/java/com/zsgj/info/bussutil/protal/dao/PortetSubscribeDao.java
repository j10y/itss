package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;

import com.zsgj.info.framework.dao.Dao;

/**
 * �û�portal����DAO��δ������demo�������дDAO���룬��������DAO
 * @Class Name UserPortetSubscribeDao
 * @Author peixf
 * @Create In 2008-10-23
 */
public interface PortetSubscribeDao extends Dao {
	
	/**
	 * �����û�portal����
	 * @Methods Name saveUserPortletSubscribe
	 * @Create In 2008-10-23 By sa
	 * @param portalId
	 * @param portletId void
	 */
	void saveUserPortletSubscribe(Serializable portalId,
			Serializable portletId);
	
	/**
	 * ɾ���û�portal����
	 * @Methods Name removeUserPorletSubscribe
	 * @Create In 2008-10-23 By sa
	 * @param portletId
	 * @param portalId void
	 */
	void removeUserPorletSubscribe(Serializable portletId,
			Serializable portalId);
}

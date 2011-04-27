package com.zsgj.info.bussutil.protal.service;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.Portlet;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.Service;

public interface PortletService extends Service {
	public void saveOrUpdate(Portlet portlet);

	public Page getPortletsByPortalColumnId(Serializable portalColumnId);
	
	public Page getPortlets(int startIndex,int pageSize);
	
	public String getPortletsJson(int startIndex,int pageSize);
	/**
	 * �����û�ID,ȡ������portlet
	 * �����û�����
	 * userId���ڱ�ʶ��ǰportlet�Ƿ��Ѿ�������.
	 * @param userId
	 * @return
	 */
	public String getPagePortletsByUserIdForUserSubscribe(Serializable userId,Serializable portalId,int startIndex,int pageSize);
}

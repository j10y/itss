package com.digitalchina.info.bussutil.protal.service;

import java.io.Serializable;

import com.digitalchina.info.bussutil.protal.entity.Portal;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.Service;

public interface PortalService extends Service {
	public void saveOrUpdatePortal(Portal portal);

	public void removePortal(Serializable id);

	public Page getAllPortalsByPortalContainerId(
			Serializable portalContainerId);
	public String getAllPortalsByPortalContainerIdJson(Serializable portalContainerId);
	/**
	 * ����portal��ʽ�ı�
	 */
	public void savePortalColumnTemplateChange(Serializable portalId,Serializable portalColumnTemplateId);
	/**
	 * ȡ���û���ӵ����һ��portal���
	 * @param userId
	 * @return
	 */
	public Portal getLastPortalByUserId(Serializable userId);
}

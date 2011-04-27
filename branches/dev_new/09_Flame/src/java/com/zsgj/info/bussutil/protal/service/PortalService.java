package com.zsgj.info.bussutil.protal.service;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.Portal;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.Service;

public interface PortalService extends Service {
	public void saveOrUpdatePortal(Portal portal);

	public void removePortal(Serializable id);

	public Page getAllPortalsByPortalContainerId(
			Serializable portalContainerId);
	public String getAllPortalsByPortalContainerIdJson(Serializable portalContainerId);
	/**
	 * 保存portal版式改变
	 */
	public void savePortalColumnTemplateChange(Serializable portalId,Serializable portalColumnTemplateId);
	/**
	 * 取得用户添加的最后一个portal面板
	 * @param userId
	 * @return
	 */
	public Portal getLastPortalByUserId(Serializable userId);
}

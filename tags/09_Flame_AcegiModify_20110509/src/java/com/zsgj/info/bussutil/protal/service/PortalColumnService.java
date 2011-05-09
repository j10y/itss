package com.zsgj.info.bussutil.protal.service;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.PortalColumn;
import com.zsgj.info.bussutil.protal.entity.PortalColumnTemplate;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.Service;

public interface PortalColumnService extends Service {
	public Page getPortalColumnByPortalId(
			Serializable portalId);

	public void saveOrUpdate(PortalColumn portalColumn);
	public Page getAllPortalColumnTemplates();
	public String getAllPortalColumnTemplatesJson();
	/**
	 * 取得系统默认的样式表
	 * @return
	 */
	public PortalColumnTemplate getSystemDefaultPortalColumnTemplate();
	
	/**
	 * 取得一个portal的第一个portalColumn.即最左边第一列,新添加的portlet将添加到本列
	 */
	public PortalColumn getFirstPortalColumnByPortalId(Serializable portalId);
	
}

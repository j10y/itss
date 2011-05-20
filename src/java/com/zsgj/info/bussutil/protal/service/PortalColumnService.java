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
	 * ȡ��ϵͳĬ�ϵ���ʽ��
	 * @return
	 */
	public PortalColumnTemplate getSystemDefaultPortalColumnTemplate();
	
	/**
	 * ȡ��һ��portal�ĵ�һ��portalColumn.������ߵ�һ��,����ӵ�portlet����ӵ�����
	 */
	public PortalColumn getFirstPortalColumnByPortalId(Serializable portalId);
	
}

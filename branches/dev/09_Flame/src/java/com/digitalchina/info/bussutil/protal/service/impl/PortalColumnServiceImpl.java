package com.digitalchina.info.bussutil.protal.service.impl;

import java.io.Serializable;

import com.digitalchina.info.bussutil.protal.dao.PortalColumnDao;
import com.digitalchina.info.bussutil.protal.entity.PortalColumn;
import com.digitalchina.info.bussutil.protal.entity.PortalColumnTemplate;
import com.digitalchina.info.bussutil.protal.service.PortalColumnService;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.service.BaseService;

public class PortalColumnServiceImpl extends BaseService implements
		PortalColumnService {
    private PortalColumnDao portalColumnDao=null;
	public Page getPortalColumnByPortalId(
			Serializable portalId) {
		// TODO Auto-generated method stub
		return this.getPortalColumnDao().getPortalColumnByPortalId(portalId);
	}

	public void saveOrUpdate(PortalColumn portalColumn) {
		// TODO Auto-generated method stub
         this.getPortalColumnDao().saveOrUpdate(portalColumn);
	}

	public PortalColumnDao getPortalColumnDao() {
		return portalColumnDao;
	}

	public void setPortalColumnDao(PortalColumnDao portalColumnDao) {
		this.portalColumnDao = portalColumnDao;
	}

	public Page getAllPortalColumnTemplates() {
		// TODO Auto-generated method stub
		return this.getPortalColumnDao().getAllPortalColumnTemplates();
	}

	public String getAllPortalColumnTemplatesJson() {
//		参考portalDemo，框架Page中新增json方法，用户转换page中集合数据为json格式，以适应portal功能加入，但不知此方法是否适合项目中复杂的实体关联情况
//		值得研究一下，为我所用
		return this.getAllPortalColumnTemplates().json(); 
	}

	public PortalColumnTemplate getSystemDefaultPortalColumnTemplate() {
		// TODO Auto-generated method stub
		return this.getPortalColumnDao().getSystemDefaultPortalColumnTemplate();
	}

	public PortalColumn getFirstPortalColumnByPortalId(Serializable portalId) {
		// TODO Auto-generated method stub
		Page ps=this.getPortalColumnByPortalId(portalId);
		PortalColumn portalColumn=(PortalColumn)ps.getData().get(0);
		return portalColumn;
	}

}

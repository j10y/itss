package com.zsgj.info.bussutil.protal.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.zsgj.info.bussutil.protal.dao.PortalColumnDao;
import com.zsgj.info.bussutil.protal.entity.PortalColumn;
import com.zsgj.info.bussutil.protal.entity.PortalColumnTemplate;
import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;

public class PortalColumnDaoImpl extends BaseDao implements PortalColumnDao {

	public Page getPortalColumnByPortalId(Serializable portalId) {
		int pageNo = 1;
		int pageSize = 10;
		
		String hql = "from " + PortalColumn.class.getName()
				+ " p where p.portal.id=? order by p.createTime asc";
		//List items = this.getAllByHQL(hql, "portalId", portalId);
		//return new PaginationSupport(items); //原意为显示所有记录在一页
		long totalCount = super.totalCount(hql, new Long(portalId.toString()));
		pageSize = (int) totalCount;
		return super.pagedQuery(hql, pageNo, pageSize, new Long(portalId.toString()));
	}

	public PortalColumn saveOrUpdate(PortalColumn portalColumn) {
		// TODO Auto-generated method stub
		return (PortalColumn) super.save(portalColumn);

	}

	public Page getAllPortalColumnTemplates() {
		int pageNo = 1;
		int pageSize = 9999999;
		String hql = "from " + PortalColumnTemplate.class.getName()
				+ " p order by p.id asc";
		/*List items = this.getAllByHQL(hql);
		PaginationSupport ps = new PaginationSupport(items);*/  //原意为显示所有记录在一页
		//long totalCount = super.totalCount(hql, null);
		//pageSize = (int) totalCount;
		Page ps = super.pagedQuery(hql, pageNo, pageSize, null);
		return ps;
	}

	public PortalColumnTemplate getSystemDefaultPortalColumnTemplate() {
		PortalColumnTemplate result = null;
		/*Object o = this.getObject(PortalColumnTemplate.class, "marker",
		"default");*/
		/*Object o = super.findBy(PortalColumnTemplate.class, "marker",
				"default");*/
		List list = super.findBy(PortalColumnTemplate.class, "marker", "default");
		if(!list.isEmpty()){
			result = (PortalColumnTemplate) list.iterator().next();
		}
		return result;
	}

}

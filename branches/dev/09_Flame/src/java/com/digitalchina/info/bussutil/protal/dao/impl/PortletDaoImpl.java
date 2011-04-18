package com.digitalchina.info.bussutil.protal.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.digitalchina.info.bussutil.protal.dao.PortletDao;
import com.digitalchina.info.bussutil.protal.entity.Portlet;
import com.digitalchina.info.bussutil.protal.entity.PortletSubscribe;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;

public class PortletDaoImpl extends BaseDao implements PortletDao {

	public Page getPortletsByPortalColumnId(
			Serializable portalColumnId) {
		
		/*
		List result = new ArrayList(); 
		String hql = "from "
				+ PortletSubscribe.class.getName()
				+ " p where p.portalColumn.id=:portalColumnId order by p.order asc";
		List portletSubscribes = this.getAllByHQL(hql, "portalColumnId",
				portalColumnId);*/ 
				
		/*if (null != portletSubscribes) {//垃圾HQL
			Iterator it = portletSubscribes.iterator();
			while (it.hasNext()) {
				PortletSubscribe item = (PortletSubscribe) it.next();
				Portlet portlet = item.getProtlet();
				result.add(portlet);
			}
		}*/
		
		/*return new PaginationSupport(result);*/ //原意是显示所有的数据
		
		String hql = "select p.protlet from PortletSubscribe p " +
		"where p.portalColumn.id= ? order by p.order asc";

		List portlets = super.find(hql, new Long(portalColumnId.toString()));
		return new Page(1, portlets.size(), portlets.size(), portlets);
		
	}

	public void saveOrUpdate(Portlet portlet) {
		super.save(portlet);
	}

	public Page getPortlets(int startIndex) {
		String hql = "from " + Portlet.class.getName()
				+ " p order by p.id desc";
		/*return this.getPageByHQL(hql, startIndex);*/ //原代码的每页是15
		
		Page page = super.pagedQuery(hql, startIndex == 0 ? 1 : startIndex, 15, null);
		return page;
	}

	public Page getAllPortletsByPortalId(Serializable portalId) {
		/*String hql = "from "
				+ PortletSubscribe.class.getName()
				+ " p where p.portalColumn.portal.id=:portalId order by p.createTime desc";
		List portletSubscribes = this.getAllByHQL(hql, "portalId", portalId);
		List result = new ArrayList();
		if (CollectionUtils.isNotEmpty(portletSubscribes)) {
			for (int i = 0; i < portletSubscribes.size(); i++) {
				PortletSubscribe item = (PortletSubscribe) portletSubscribes
						.get(i);
				Portlet portlet = item.getProtlet();
				result.add(portlet);
			}
		}
		return new PaginationSupport(result);*/
		
		String hql = "select  p.protlet from PortletSubscribe p " +
				"where p.portalColumn.portal.id=? order by p.createTime desc";
		List portlets = super.find(hql, new Long(portalId.toString()));
		return new Page(1, portlets.size(), portlets.size(), portlets);
		
	}

	public Page getPortletSubscribeByPortalColumnId(
			Serializable portalColumnId) {
		// List result=new ArrayList();
		/*String hql = "from "
				+ PortletSubscribe.class.getName()
				+ " p where p.portalColumn.id=:portalColumnId order by p.order asc";
		List portletSubscribes = this.getAllByHQL(hql, "portalColumnId",
				portalColumnId);
		return new PaginationSupport(portletSubscribes);*/
		
		String hql = "from "
			+ PortletSubscribe.class.getName()
			+ " p where p.portalColumn.id= ? order by p.order asc";
		List portletSubscribes = super.find(hql, new Long(portalColumnId.toString()));
		return new Page(1, portletSubscribes.size(), portletSubscribes.size(), portletSubscribes);
		
	}

	public PortletSubscribe getPortletSubscribeByPortletAndPortleColumn(
			Serializable portalColumnId, Serializable portletId) {
		/*String hql = "from "
				+ PortletSubscribe.class.getName()
				+ " p where p.portalColumn.id=:portalColumnId and p.protlet.id=:portletId";
		List items = this.getAllByHQL(hql, new String[] { "portalColumnId",
				"portletId" }, new Object[] { portalColumnId, portletId });*/
		
		String hql = "from "
			+ PortletSubscribe.class.getName()
			+ " p where p.portalColumn.id=? and p.protlet.id=?";
		List items = super.find(hql, new Long(portalColumnId.toString()), new Long(portletId.toString()));
	
		if (CollectionUtils.isNotEmpty(items)) {
			return (PortletSubscribe) items.get(0);
		}
		return null;
	}

	public PortletSubscribe getPortletSubscribeByPortalAndPortalColumnAndPortlet(
			Serializable portletId, Serializable portalColumnId, Serializable portalId) {
			
		/*String hql = "from "
				+ PortletSubscribe.class.getName()
				+ " p where p.portalColumn.portal.id=:portalId  and p.protlet.id=:portletId";
		List items = this.getAllByHQL(hql, new String[] { "portalId",
				"portletId" }, new Object[] { portalId, portletId });*/
		
		String hql = "from "
			+ PortletSubscribe.class.getName()
			+ " p where p.portalColumn.portal.id=?  and p.protlet.id=?";
		List items = super.find(hql, new Long(portalId.toString()), new Long(portletId.toString()));
		
		if (CollectionUtils.isNotEmpty(items)) {
			return (PortletSubscribe) items.get(0);
		}
		return null;
	}

	public int getNextPortletSubscribeOrder(Serializable portalColumnId) {
		int o = 0;
		/*String hql = "select max(p.order) from "
				+ PortletSubscribe.class.getName()
				+ " p where p.portalColumn.id=:portalColumnId";
		List items = this.getAllByHQL(hql, "portalColumnId", portalColumnId);*/
		
		String hql = "select max(p.order) from "
			+ PortletSubscribe.class.getName()
			+ " p where p.portalColumn.id = ? ";
		List items = super.find(hql, new Long(portalColumnId.toString()));
	
		if (null != items && items.size() > 0) {
			if (items.get(0) != null) {
				o = (Integer) items.get(o) + 1;
			}
		}
		return o;
	}

	public List getAllPortletSubscribeByUserId(Serializable userId,Serializable portalId) {
		/*String hql = "from " + PortletSubscribe.class.getName()
				+ " p where p.portalColumn.portal.portalContainer.user.id=:userId " +
						"and p.portalColumn.portal.id=:portalId";
		List items = this.getAllByHQL(hql, new String[]{"userId","portalId"}, new Object[]{userId,portalId});*/
		
		
		String hql = "from " + PortletSubscribe.class.getName()
		+ " p where p.portalColumn.portal.portalContainer.userInfo.id=? " +
				"and p.portalColumn.portal.id=?";
		List items = super.find(hql, new Long(userId.toString()), new Long(portalId.toString()));
		return items;
	}

}

package com.digitalchina.info.bussutil.protal.dao.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.digitalchina.info.bussutil.protal.dao.PortetSubscribeDao;
import com.digitalchina.info.bussutil.protal.entity.PortletSubscribe;
import com.digitalchina.info.framework.dao.BaseDao;

public class PortetSubscribeDaoImpl extends BaseDao implements PortetSubscribeDao{

	public void removeUserPorletSubscribe(Serializable portletId, Serializable portalId) {
		String hql = "from "
			+ PortletSubscribe.class.getName()
			+ " p where p.protlet.id=? and p.portalColumn.portal.id=? ";
		List items = super.find(hql, new Long(portletId.toString()), new Long(portalId.toString()) );
		Set idRemove = new HashSet();
		if (!items.isEmpty()) {
			PortletSubscribe portletSubscribe=(PortletSubscribe) items.get(0);
			
			String sql="from "+PortletSubscribe.class.getName()
			+" p where p.portalColumn.id=? order by p.order asc";
			
			List list = super.find(sql, portletSubscribe.getPortalColumn().getId());
			
			//if(!list.isEmpty()){
			if(portletSubscribe.getOrder()<list.size()){
				for(int i=portletSubscribe.getOrder()+1;i<list.size();i++){
					PortletSubscribe item=(PortletSubscribe) list.get(i);
					item.setOrder(i-1);
					this.save(item);
				}
			}
			//this.remove(items);
			//}
			
		}
		for(int i=0; i<items.size(); i++){
			PortletSubscribe portletSubscribe=(PortletSubscribe) items.get(i);
			this.remove(portletSubscribe);
		}
		
	}

	public void saveUserPortletSubscribe(Serializable portalId, Serializable portletId) {
		/*PortalColumn portalColumn = (PortalColumn) this.getPortalColumnDao()
			.getPortalColumnByPortalId(portalId).getData().get(0); //getItems
		Portlet portlet = (Portlet) super.find(Portlet.class, String.valueOf(portletId)); //this.loadObject
		PortletSubscribe ps = new PortletSubscribe();
		int order = this.getPortletDao().getNextPortletSubscribeOrder(
				portalColumn.getId());
		ps.setPortalColumn(portalColumn);
		ps.setProtlet(portlet);
		ps.setName(portlet.getName());
		ps.setOrder(order);
		this.save(ps);*/
		
	}

}

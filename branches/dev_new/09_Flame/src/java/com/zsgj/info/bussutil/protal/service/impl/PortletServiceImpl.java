package com.zsgj.info.bussutil.protal.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.zsgj.info.bussutil.protal.dao.PortletDao;
import com.zsgj.info.bussutil.protal.entity.Portlet;
import com.zsgj.info.bussutil.protal.entity.PortletSubscribe;
import com.zsgj.info.bussutil.protal.service.PortletService;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.BaseService;

public class PortletServiceImpl extends BaseService implements PortletService {
    private PortletDao portletDao=null;
	public Page getPortletsByPortalColumnId(Serializable portalColumnId) {
		// TODO Auto-generated method stub
		return this.getPortletDao().getPortletsByPortalColumnId(portalColumnId);
	}

	public void saveOrUpdate(Portlet portlet) {
		// TODO Auto-generated method stub
        this.getPortletDao().saveOrUpdate(portlet);
	}

	public PortletDao getPortletDao() {
		return portletDao;
	}

	public void setPortletDao(PortletDao portletDao) {
		this.portletDao = portletDao;
	}

	public Page getPortlets(int startIndex,int pageSize) {
		// TODO Auto-generated method stub
		return this.getPortletDao().getPortlets(startIndex);
	}

	public String getPortletsJson(int startIndex,int pageSize) {
		// TODO Auto-generated method stub
		return this.getPortlets(startIndex,pageSize).json();
	}

	public String getPagePortletsByUserIdForUserSubscribe(Serializable userId,Serializable portalId,
			int startIndex, int pageSize) {
		// TODO Auto-generated method stub
	    Page ps=this.getPortlets(startIndex, pageSize);
	    List subscribedPortlets=this.getPortletDao().getAllPortletSubscribeByUserId(userId,portalId);
//	    List items=new ArrayList();
	    if(CollectionUtils.isNotEmpty(ps.getData())&&CollectionUtils.isNotEmpty(subscribedPortlets)){
	    	for(int i=0;i<ps.getData().size();i++){
	    		Portlet item=(Portlet)ps.getData().get(i);
	    		if(this.isContainsPortlet(item, subscribedPortlets)){
	    			item.setFlag("true");
	    			ps.getData().set(i, item);
	    		}
	    	}
	    }
		return ps.json();
	}
	/**
	 * 判断当前portlet是否已经被订阅.
	 * @param portlet
	 * @param portletSubscribes
	 * @return
	 */
	private boolean isContainsPortlet(Portlet portlet,List portletSubscribes){
		boolean result=false;
		for(int i=0;i<portletSubscribes.size();i++){
			PortletSubscribe item=(PortletSubscribe) portletSubscribes.get(i);
			if(item.getProtlet().getId().equals(portlet.getId())){
				result=true;
				break;
			}
		}
		return result;
	}


	
}

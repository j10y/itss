package com.digitalchina.info.bussutil.protal.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.digitalchina.info.bussutil.protal.dao.PortalDao;
import com.digitalchina.info.bussutil.protal.entity.Portal;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;

public class PortalDaoImpl  extends BaseDao implements PortalDao{

	public Page getAllPortalsByPortalContainerId(
			Serializable portalContainerId) {
		/*String hql="from "+Portal.class.getName()+" p where p.portalContainer.id=:portalContainerId order by p.createTime asc";
	    List items=this.getAllByHQL(hql, "portalContainerId", portalContainerId);
		return new PaginationSupport(items);*/
		
		String hql="from "+Portal.class.getName()
				+" p where p.portalContainer.id= ? order by p.createTime asc";
		int pageNo = 1;
		int pageSize = 10;
		//Long pcId = Long.valueOf(portalContainerId.toString());
		long totalCount = super.totalCount(hql, new Long(portalContainerId.toString()));
		pageSize = (int) totalCount;
		Page page = super.pagedQuery(hql, pageNo, pageSize, new Long(portalContainerId.toString()));
		return page;
		
		
	}
	public void removePortal(Portal portal) {
		super.remove(portal);
	}

	public Portal saveOrUpdatePortal(Portal portal) {
		return (Portal) super.save(portal);
	}
	public Portal getLastPortalByUserId(Serializable userId) {
	   /*String hql="from "+Portal.class.getName()+" p where p.portalContainer.user.id=:userId order by p.createTime desc";
	   List items=this.getAllByHQL(hql, "userId", userId);*/
	   String hql="from "+Portal.class.getName()
				+" p where p.portalContainer.userInfo.id = ? order by p.createTime desc";
	   List items = super.find(hql, new Long(userId.toString()));	
	   Portal portal=null;
	   if(CollectionUtils.isNotEmpty(items)){
		   portal=(Portal)items.get(0);
	   }
		return portal;
	}

}

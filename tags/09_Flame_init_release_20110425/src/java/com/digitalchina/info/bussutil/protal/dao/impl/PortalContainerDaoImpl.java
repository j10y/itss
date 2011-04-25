package com.digitalchina.info.bussutil.protal.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.digitalchina.info.bussutil.protal.dao.PortalContainerDao;
import com.digitalchina.info.bussutil.protal.entity.PortalContainer;
import com.digitalchina.info.bussutil.protal.entity.PortalStyle;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;

public class PortalContainerDaoImpl extends BaseDao implements
		PortalContainerDao {

	public PortalContainer getPortalContainerByUserId(Serializable userId) {
		/*String hql = "from " + PortalContainer.class.getName()
				+ " p where p.user.id=:userId";
		List items = this.getAllByHQL(hql, "userId", userId);*/
		String hql = "from " + PortalContainer.class.getName()
					+ " p where p.userInfo.id=?";
		List items = super.find(hql, new Long(userId.toString()));
		if (null != items && !items.isEmpty() && items.size() > 0) {
			return (PortalContainer) items.get(0);
		} else {
			return null;
		}
	}

	public PortalContainer saveOrUpdate(PortalContainer portalContainer) {
		return (PortalContainer) super.save(portalContainer);
	}

	public Page getPortalAllStyles() {
		int pageNo = 1;
		int pageSize = 10;
		
		String hql = "from " + PortalStyle.class.getName()
				+ " p order by p.id desc";
		/*List items = this.getAllByHQL(hql);
		PaginationSupport ps = new PaginationSupport(items); //此方法原意为显示所有记录一页
		return ps;*/
		long totalCount = super.totalCount(hql);
		pageSize = (int) totalCount;
		Page page = super.pagedQuery(hql, pageNo, pageSize, null);
		return page;
	}

	public Page getNewerPortalContainers(int startIndex,int pageSize) {
		// TODO Auto-generated method stub
		String hql="from "+PortalContainer.class.getName()+" p order by p.createTime desc";
		//Page ps=this.getPageByHQL(hql, pageSize, startIndex);
		Page ps = super.pagedQuery(hql, startIndex, pageSize, null);
		return ps;
	}


}

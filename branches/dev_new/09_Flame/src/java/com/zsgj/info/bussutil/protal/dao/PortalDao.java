package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.Portal;
import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

public interface PortalDao extends Dao {
	/**
	 * 保存或更新Portal
	 * @Methods Name saveOrUpdatePortal
	 * @Create In Feb 4, 2010 By lee
	 * @param portal
	 * @return Portal
	 */
   public Portal saveOrUpdatePortal(Portal portal);
   public void removePortal(Portal  portal);
   public Page getAllPortalsByPortalContainerId(Serializable portalContainerId);
   /*
    *  取得用户添加的最后一个portal面板
    */
   public Portal getLastPortalByUserId(Serializable userId);
}

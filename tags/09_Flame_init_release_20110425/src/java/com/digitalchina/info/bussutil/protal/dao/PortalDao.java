package com.digitalchina.info.bussutil.protal.dao;

import java.io.Serializable;

import com.digitalchina.info.bussutil.protal.entity.Portal;
import com.digitalchina.info.framework.dao.Dao;
import com.digitalchina.info.framework.dao.support.Page;

public interface PortalDao extends Dao {
	/**
	 * ��������Portal
	 * @Methods Name saveOrUpdatePortal
	 * @Create In Feb 4, 2010 By lee
	 * @param portal
	 * @return Portal
	 */
   public Portal saveOrUpdatePortal(Portal portal);
   public void removePortal(Portal  portal);
   public Page getAllPortalsByPortalContainerId(Serializable portalContainerId);
   /*
    *  ȡ���û���ӵ����һ��portal���
    */
   public Portal getLastPortalByUserId(Serializable userId);
}

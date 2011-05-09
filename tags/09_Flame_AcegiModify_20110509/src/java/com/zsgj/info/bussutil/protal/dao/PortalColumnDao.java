package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.PortalColumn;
import com.zsgj.info.bussutil.protal.entity.PortalColumnTemplate;
import com.zsgj.info.framework.dao.support.Page;

public interface PortalColumnDao/* extends Dao */{
  public Page getPortalColumnByPortalId(Serializable portalId);
  /**
   * ±£¥ÊPortalColumn
   * @Methods Name saveOrUpdate
   * @Create In Feb 4, 2010 By lee
   * @param portalColumn
   * @return PortalColumn
   */
  public PortalColumn saveOrUpdate(PortalColumn portalColumn);
  public Page getAllPortalColumnTemplates();
  public PortalColumnTemplate getSystemDefaultPortalColumnTemplate();
}

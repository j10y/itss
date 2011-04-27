package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;
import java.util.List;

import com.zsgj.info.bussutil.protal.entity.Portlet;
import com.zsgj.info.bussutil.protal.entity.PortletSubscribe;
import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

public interface PortletDao extends Dao {
  public void saveOrUpdate(Portlet portlet);
  /**
   * 根据portalColumn取得一个portalColumn拥有的所有portlet
   * @param portalColumnId
   * @return
   */
  public Page getPortletsByPortalColumnId(Serializable portalColumnId);
  public Page getPortletSubscribeByPortalColumnId(Serializable portalColumnId);
  public PortletSubscribe getPortletSubscribeByPortletAndPortleColumn(Serializable portalColumnId,Serializable portletId);
  public PortletSubscribe getPortletSubscribeByPortalAndPortalColumnAndPortlet(Serializable portletId,Serializable portalColumnId,Serializable portalId);
  public Page getPortlets(int startIndex);
  
  public int getNextPortletSubscribeOrder(Serializable portalColumnId);
  /**
   * 根据portal取得一个portal拥有的所有portlet
   * @param portalId
   * @return
   */
  public Page getAllPortletsByPortalId(Serializable portalId);
  /**
   * 取得一个用户拥有的所有portlet
   * @param userId
   * @return
   */
  public List getAllPortletSubscribeByUserId(Serializable userId,Serializable portalId);
}

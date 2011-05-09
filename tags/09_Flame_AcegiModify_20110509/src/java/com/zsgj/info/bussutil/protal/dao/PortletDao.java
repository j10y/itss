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
   * ����portalColumnȡ��һ��portalColumnӵ�е�����portlet
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
   * ����portalȡ��һ��portalӵ�е�����portlet
   * @param portalId
   * @return
   */
  public Page getAllPortletsByPortalId(Serializable portalId);
  /**
   * ȡ��һ���û�ӵ�е�����portlet
   * @param userId
   * @return
   */
  public List getAllPortletSubscribeByUserId(Serializable userId,Serializable portalId);
}

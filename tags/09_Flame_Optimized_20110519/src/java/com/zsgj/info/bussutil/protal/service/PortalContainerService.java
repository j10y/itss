package com.zsgj.info.bussutil.protal.service;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.PortalContainer;
import com.zsgj.info.bussutil.protal.entity.PortletSubscribe;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.Service;

public interface PortalContainerService extends Service {
	public void saveOrUpdatePortalContainer(PortalContainer portalContainer);

	public void removePortalContainer(Serializable portalContainerId);

	/**
	 * �����û�IDȡ���û�����.
	 * 
	 * @param userId
	 * @return
	 */
	public PortalContainer getPortalContainerByUserId(Serializable userId);

	/**
	 * �û���һ�ν���,Ϊ�û�����Ĭ��portalContainer,ͬʱ,��portalContaier���������
	 * 
	 * @param userId
	 */
	public void saveDefaultUserPortalContainer(Serializable userId);
	/**
	 * �û�����Զ���portalContainer.
	 * @param userId
	 */
	public void saveDefaultUserDefinePortalContainer(Serializable userId);

	public Page getPortalAllStyles();

	public String getPortalAllStylesJson();
	
	public boolean isExistsUserPortalContainer(Serializable userId);
	
	/**
	 * �û�portlet����
	 */
	public void saveUserPortletSubscribe(Serializable portalId,Serializable portletId);
	
	public void removeUserPorletSubscribe(Serializable portletId,Serializable portalId);
	
	/**
	 * ֻ���û����϶�ʱ�ı��portlet��λ��
	 */
	public void saveChangePortletPosition(Serializable portletId,Serializable portalColumnId,Serializable portalId,int index);
	
    /**
     * ȡ�����¼���portalContainer��������ע����û�blog
     * ��ʱ�������������
     * @return
    */
    public Page getNewerPortalContainers(int startIndex,int pageSize);
    
    /**
     * �������Porlet���Զ�ˢ��ʱ��
     * @Methods Name saveChangePortletPosition
     * @Create In Oct 30, 2008 By ����
     * @param portletId
     * @param portalColumnId
     * @param refershData void
     */
    public void saveChangePortletRefersh(Serializable portletId, Serializable portalColumnId, Long refershData);
    
    /**
     * ��ȡPortletSubscribe
     * @Methods Name getPortletSubscribeByPortletAndPortleColumn
     * @Create In Oct 30, 2008 By ����
     * @param portletId
     * @param portalColumnId
     * @return PortletSubscribe
     */
    public PortletSubscribe getPortletSubscribeByPortletAndPortleColumn(Serializable portletId, Serializable portalColumnId);
}

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
	 * 根据用户ID取得用户容器.
	 * 
	 * @param userId
	 * @return
	 */
	public PortalContainer getPortalContainerByUserId(Serializable userId);

	/**
	 * 用户第一次进入,为用户设置默认portalContainer,同时,向portalContaier中填充内容
	 * 
	 * @param userId
	 */
	public void saveDefaultUserPortalContainer(Serializable userId);
	/**
	 * 用户添加自定义portalContainer.
	 * @param userId
	 */
	public void saveDefaultUserDefinePortalContainer(Serializable userId);

	public Page getPortalAllStyles();

	public String getPortalAllStylesJson();
	
	public boolean isExistsUserPortalContainer(Serializable userId);
	
	/**
	 * 用户portlet订阅
	 */
	public void saveUserPortletSubscribe(Serializable portalId,Serializable portletId);
	
	public void removeUserPorletSubscribe(Serializable portletId,Serializable portalId);
	
	/**
	 * 只在用户在拖动时改变的portlet的位置
	 */
	public void saveChangePortletPosition(Serializable portletId,Serializable portalColumnId,Serializable portalId,int index);
	
    /**
     * 取得最新加入portalContainer，既最新注册的用户blog
     * 按时间进到倒序排序。
     * @return
    */
    public Page getNewerPortalContainers(int startIndex,int pageSize);
    
    /**
     * 保存各个Porlet的自动刷新时间
     * @Methods Name saveChangePortletPosition
     * @Create In Oct 30, 2008 By 张鹏
     * @param portletId
     * @param portalColumnId
     * @param refershData void
     */
    public void saveChangePortletRefersh(Serializable portletId, Serializable portalColumnId, Long refershData);
    
    /**
     * 获取PortletSubscribe
     * @Methods Name getPortletSubscribeByPortletAndPortleColumn
     * @Create In Oct 30, 2008 By 张鹏
     * @param portletId
     * @param portalColumnId
     * @return PortletSubscribe
     */
    public PortletSubscribe getPortletSubscribeByPortletAndPortleColumn(Serializable portletId, Serializable portalColumnId);
}

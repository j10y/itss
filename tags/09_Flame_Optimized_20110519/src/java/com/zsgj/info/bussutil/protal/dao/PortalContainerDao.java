package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.PortalContainer;
import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

public interface PortalContainerDao extends Dao {
   /**
    * 保存或更新PortalContainer
    * @Methods Name saveOrUpdate
    * @Modify In Feb 4, 2010 By lee
    * @param portalContainer
    * @return PortalContainer
    */
   public PortalContainer saveOrUpdate(PortalContainer portalContainer);
   public PortalContainer getPortalContainerByUserId(Serializable userId);
   public Page getPortalAllStyles();
   /**
    * 取得最新加入portalContainer，既最新注册的用户blog
    * 按时间进到倒序排序。
    * @return
    */
   public Page getNewerPortalContainers(int startIndex,int pageSize);
}

package com.zsgj.info.bussutil.protal.dao;

import java.io.Serializable;

import com.zsgj.info.bussutil.protal.entity.PortalContainer;
import com.zsgj.info.framework.dao.Dao;
import com.zsgj.info.framework.dao.support.Page;

public interface PortalContainerDao extends Dao {
   /**
    * ��������PortalContainer
    * @Methods Name saveOrUpdate
    * @Modify In Feb 4, 2010 By lee
    * @param portalContainer
    * @return PortalContainer
    */
   public PortalContainer saveOrUpdate(PortalContainer portalContainer);
   public PortalContainer getPortalContainerByUserId(Serializable userId);
   public Page getPortalAllStyles();
   /**
    * ȡ�����¼���portalContainer��������ע����û�blog
    * ��ʱ�������������
    * @return
    */
   public Page getNewerPortalContainers(int startIndex,int pageSize);
}

package com.zsgj.info.framework.workflow.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.workflow.UpdateWorkflowService;
import com.zsgj.info.framework.workflow.entity.PageModelConfigUnit;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;

/** 
 * @author ���� E-mail: yangtao@info.com
 * @version ����ʱ�䣺Mar 17, 2009 8:21:23 PM 
 * ��˵�� 
 */

public class UpdateWorkflowServiceImpl extends BaseDao implements UpdateWorkflowService{

	/**
	 * �õ����������б�
	 * @Methods Name getVirtualDefinitionInfo
	 * @Create In Mar 19, 2009 By Administrator
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	public Page getVirtualDefinitionInfo(Map requestParams, int pageNo,
			int pageSize, String orderBy, boolean isAsc) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(VirtualDefinitionInfo.class);
		String realProcessDesc=(String)requestParams.get("realDefinitionDesc");
		String virtualDefinitionDesc=(String)requestParams.get("virtualDefinitionDesc");
		if(realProcessDesc!=null&&!"".equals(realProcessDesc)){
			c.add(Restrictions.eq("realDefinitionDesc", realProcessDesc));
			
		}
		if(virtualDefinitionDesc!=null&&!"".equals(virtualDefinitionDesc)){
			c.add(Restrictions.eq("virtualDefinitionDesc", virtualDefinitionDesc));
		}
		
		
		Page page = this.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	/**
	 * �õ�ĳ���������̵�����ڵ���Ϣ
	 * @Methods Name getVirtualNodeInfo
	 * @Create In Mar 19, 2009 By Administrator
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	public Page getVirtualNodeInfo(Map requestParams, int pageNo, int pageSize,
			String orderBy, boolean isAsc) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(VirtualNodeInfo.class);
		VirtualDefinitionInfo v =(VirtualDefinitionInfo)requestParams.get("virtualDefinitionInfo");
		c.add(Restrictions.eq("virtualDefinitionInfo", v));
		Page page = this.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	/**
	 * �õ�pageModel�����������Ϣ
	 * @Methods Name getPageModelConfigUnit
	 * @Create In Mar 19, 2009 By Administrator
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	public Page getPageModelConfigUnit(Map requestParams, int pageNo,
			int pageSize, String orderBy, boolean isAsc) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(PageModelConfigUnit.class);
		String nodeId=(String)requestParams.get("nodeId");
		String virtualDefinitionInfoId=(String)requestParams.get("virtualDefinitionInfoId");
		c.add(Restrictions.eq("processId", Long.valueOf(virtualDefinitionInfoId)));
		c.add(Restrictions.eq("nodeId", Long.valueOf(nodeId)));
		Page page = this.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	
	/**
	 * �õ�ĳ���������̵���������ڵ���Ϣ
	 * @param vd
	 * @return
	 */
	public List<VirtualNodeInfo> getVirtualNodeInfo(VirtualDefinitionInfo vd) {
		// TODO Auto-generated method stub
		Criteria c = this.createCriteria(VirtualNodeInfo.class);
		c.add(Restrictions.eq("virtualDefinitionInfo", vd));
		return c.list();
	}

}

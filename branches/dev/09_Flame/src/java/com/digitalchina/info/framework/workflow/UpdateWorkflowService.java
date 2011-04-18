package com.digitalchina.info.framework.workflow;

import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;

/** 
 * @author 杨涛 E-mail: yangtao@info.com
 * @version 创建时间：Mar 17, 2009 8:19:08 PM 
 * 类说明 
 */

public interface UpdateWorkflowService {
	
	/**
	 * 得到虚拟流程列表
	 * @Methods Name getVirtualDefinitionInfo
	 * @Create In Mar 19, 2009 By Administrator
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	public Page getVirtualDefinitionInfo(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);
	
	/**
	 * 得到某个虚拟流程的虚拟节点信息
	 * @Methods Name getVirtualNodeInfo
	 * @Create In Mar 19, 2009 By Administrator
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	public Page getVirtualNodeInfo(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);
	
	/**
	 * 得到pageModel流程组件的信息
	 * @Methods Name getPageModelConfigUnit
	 * @Create In Mar 19, 2009 By Administrator
	 * @param requestParams
	 * @param pageNo
	 * @param pageSize
	 * @param orderBy
	 * @param isAsc
	 * @return Page
	 */
	public Page getPageModelConfigUnit(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);

}

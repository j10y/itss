package com.digitalchina.info.framework.workflow;

import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;

/** 
 * @author ���� E-mail: yangtao@info.com
 * @version ����ʱ�䣺Mar 17, 2009 8:19:08 PM 
 * ��˵�� 
 */

public interface UpdateWorkflowService {
	
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
	public Page getVirtualDefinitionInfo(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);
	
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
	public Page getVirtualNodeInfo(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);
	
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
	public Page getPageModelConfigUnit(Map requestParams, int pageNo, int pageSize, String orderBy, boolean isAsc);

}

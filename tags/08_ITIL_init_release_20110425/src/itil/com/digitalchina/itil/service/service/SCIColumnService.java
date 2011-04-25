package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.ServiceItemType;

/**
 * �������ֶ�
 * @author tongjp
 *
 */
public interface SCIColumnService {
	/**
	 * ͨ��id����SCIColumn
	 * @param id
	 * @author tongjp
	 * @return
	 */
	SCIColumn findSCIColumnById(String id);
	
	/**
	 * �������е�SCIColumn
	 * @author tongjp
	 * @return
	 */
	List findAllSCIColumn();
	
	/**
	 * ͨ��idɾ������
	 * @author tongjp
	 * @param id
	 */
	void removeSCIColumnById(String id);
	
	/**
	 * ͨ��һ��idsɾ������
	 * @author tongjp
	 * @param ids
	 */
	void removeSCIColumnByIds(String [] ids);
	
	/**
	 * ����sCIColumn
	 * @param sCIColumn
	 * @author tongjp
	 * @return
	 */
	SCIColumn saveSCIColumn(SCIColumn sCIColumn);
	
	List findSCIColumnByServiceItemType(ServiceItemType serviceItemType);
	
	Page findSCIColumnByServiceItemType(ServiceItemType serviceItemType,int pageNo, int pageSize);
	
}

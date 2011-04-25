package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.ServiceItemType;

/**
 * 服务项字段
 * @author tongjp
 *
 */
public interface SCIColumnService {
	/**
	 * 通过id查找SCIColumn
	 * @param id
	 * @author tongjp
	 * @return
	 */
	SCIColumn findSCIColumnById(String id);
	
	/**
	 * 查找所有的SCIColumn
	 * @author tongjp
	 * @return
	 */
	List findAllSCIColumn();
	
	/**
	 * 通过id删除对象
	 * @author tongjp
	 * @param id
	 */
	void removeSCIColumnById(String id);
	
	/**
	 * 通过一组ids删除对象
	 * @author tongjp
	 * @param ids
	 */
	void removeSCIColumnByIds(String [] ids);
	
	/**
	 * 保存sCIColumn
	 * @param sCIColumn
	 * @author tongjp
	 * @return
	 */
	SCIColumn saveSCIColumn(SCIColumn sCIColumn);
	
	List findSCIColumnByServiceItemType(ServiceItemType serviceItemType);
	
	Page findSCIColumnByServiceItemType(ServiceItemType serviceItemType,int pageNo, int pageSize);
	
}

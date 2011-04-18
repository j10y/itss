package com.digitalchina.itil.service.service;

import java.util.List;

import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.SCIDColumn;
import com.digitalchina.itil.service.entity.ServiceItem;

/**
 * 服务项数据字段服务
 * @Class Name SCIDColumnService
 * @Author lee
 * @Create In 2009-2-12
 */
public interface SCIDColumnService {
	/**
	 * 获取服务项数据相关的字段
	 * @Methods Name findSCIDColumnByServiceItem
	 * @Create In 2009-2-12 By lee
	 * @param serviceItem
	 * @return List
	 */
	List<SCIDColumn> findSCIDColumnByServiceItem(ServiceItem serviceItem);
	/**
	 * 获取服务项数据相关的字段组件字符串
	 * @Methods Name encode
	 * @Create In 2009-2-12 By lee
	 * @param columns
	 * @return String
	 */
	String encode(List<SCIDColumn> columns);
	/**
	 * 保存服务项特殊字段
	 * @Methods Name save
	 * @Create In 2009-2-12 By lee
	 * @param scidColumn
	 * @param SCIDColumn
	 */
	SCIDColumn save(SCIDColumn scidColumn);
	/**
	 * 保存服务项特殊字段的值
	 * @Methods Name save
	 * @Create In 2009-2-12 By lee
	 * @param serviceItem
	 * @param columnName
	 * @param value
	 * @return SCIDColumn
	 */
	SCIDColumn saveColumnValue(ServiceItem serviceItem,String columnName,String value);
	/**
	 * 保存服务项特殊字段（从服务项类型特殊字段导入）
	 * @Methods Name save
	 * @Create In 2009-2-12 By lee
	 * @param sciColumns
	 * @param serviceItem
	 */
	void save(List<SCIColumn> sciColumns,ServiceItem serviceItem);
	/**
	 * 通过传入实体SCIDColumn 删除SCIDColumn
	 * @author tongjp
	 * @param sCIDColumn
	 */
	void removeSCIDColumn(SCIDColumn sCIDColumn);
	/**
	 * 通过id查找SCIDColumn
	 * @author tongjp
	 * @param id
	 * @return
	 */
	SCIDColumn findSCIDColumnById(String id);
	
	/**
	 * 通过传入的数组ids删除对象
	 * @author tongjp
	 * @param ids
	 */
	void removeSCIDColumnByIds(String[] ids);
	/**
	 * 保存服务项字段
	 * @author tongjp
	 * @param scidColumn
	 * @return
	 */
	SCIDColumn saveSCIDColumn(SCIDColumn scidColumn);
	
	/**
	 * 保存SCIDColumn，在保存是做判断，如果保存是有相同字段名的就不保存，如果没有就保存。
	 * @Methods Name saveSCIDColumn
	 * @Create In Feb 26, 2009 By tongjp
	 * @param sciColumns
	 * @param serviceItem void
	 */
	void saveSCIDColumn(List<SCIColumn> sciColumns, ServiceItem serviceItem);
}

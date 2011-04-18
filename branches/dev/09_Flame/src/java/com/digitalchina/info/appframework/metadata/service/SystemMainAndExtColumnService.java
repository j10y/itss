package com.digitalchina.info.appframework.metadata.service;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;

/**
 * 系统主表和扩展表合并
 * @Class Name SystemMainAndExtColumnService
 * @Author tongjp
 * @Create In Jun 23, 2009
 */
public interface SystemMainAndExtColumnService {
	
	/**
	 * 通过主表获取所有字段
	 * @Methods Name findAllColumnBySysMainTable
	 * @Create In Jun 23, 2009 By tongjp
	 * @param smt
	 * @return List
	 */
	public List findAllColumnBySysMainTable(SystemMainTable smt);
	
	/**
	 * 通过主表和是否为扩展表的类型获取主表字段或扩展表字段
	 * @Methods Name findColumnByIsExtAndSysMainTable
	 * @Create In Jun 23, 2009 By tongjp
	 * @param isExtend
	 * @param smt
	 * @return List
	 */
	public List findColumnByIsExtAndSysMainTable(Integer isExtend,SystemMainTable smt);
	
	/**
	 * 通过扩展字段的id获取下拉列表
	 * @Methods Name findExtOptionDataByExtColId
	 * @Create In Jun 26, 2009 By tongjp
	 * @param ectColId
	 * @return List
	 */
	public List findExtOptionDataByExtColId(String ectColId);
	
	/**
	 * 通过主实体字段的id和扩展字段的id查处对应的扩展字段的数据
	 * @Methods Name findObjectByMainRowIdAndExtColId
	 * @Create In Jun 26, 2009 By tongjp
	 * @param mainRowId
	 * @param extid
	 * @return Object
	 */
	public Object findObjectByMainRowIdAndExtColId(Integer mainRowId,Integer extid);
	
	/**
	 * 通过id查处自定义的数据的值
	 * @Methods Name findOptionById
	 * @Create In Jun 26, 2009 By tongjp
	 * @param id
	 * @return Object
	 */
	public Object findOptionById(Long id);
	
	/**
	 * 保存实体
	 * @Methods Name saveExtOption
	 * @Create In Jun 29, 2009 By tongjp
	 * @param object
	 * @return Object
	 */
	public Object saveExtOption(Object object);
	
	/**
	 * 通过id删除自定义下拉列表的数据
	 * @Methods Name removeOptionById
	 * @param id
	 * @author tongjp
	 */
	public void removeOptionById(Long id);

}

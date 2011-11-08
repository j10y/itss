package com.xp.commonpart.dao;

import java.util.List;

import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;

public interface MainTableDao {
	/**
	 * 查询表的信息列表
	 */
	public List selectMainTable();
	
	/**
	 * 查询表字段的信息列表
	 * @param mainTable
	 * @return
	 */
	public List selectMainTableColumn(MainTable mainTable);
	
	/**
	 * 保存表信息
	 */
	public MainTable saveMainTable(MainTable mainTable);
	
	/**
	 * 删除表信息
	 * @param mainTable
	 */
	public void removeMainTable(MainTable mainTable);
	
	/**
	 * 通过表id获取表
	 * @param maintableid
	 * @return
	 */
	public MainTable selectMainTableByMainId(Long maintableid);
	/**
	 * 查询表字段信息的列表
	 * @return
	 */
	public List selectMainTableColumnList();
	/**
	 * 通过表id查询表字段信息,获取所有属于同一个表的表字段list
	 * @param mainTableId
	 * @return
	 */
	public List selectMainTableColumnById(Long mainTableId);
	
	/**
	 * 通过表字段的id获取表字段的信息
	 * @param columnid
	 * @return
	 */
	public MainTableColumn selectMainTableColumnByColumnId(Long columnid);
	
	/**
	 * 保存表字段信息
	 * @param mainTableColumn
	 * @return
	 */
	public MainTableColumn saveMainTableColumn(MainTableColumn mainTableColumn);
	
	/**
	 * 删除对象
	 * @param obj
	 */
	public void removeObject(Object obj);
	
}

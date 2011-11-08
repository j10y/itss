package com.xp.commonpart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;
import com.xp.commonpart.bean.MenuInfo;
import com.xp.commonpart.bean.TreeObject;

public interface MainTableService {
	/**
	 * 获取表列表
	 * @return
	 */
	public List findMainTableList();
	/**
	 * 保存表信息
	 * @param maintable
	 * @return
	 */
	public MainTable saveMainTable(MainTable maintable);
	/**
	 * 删除表信息
	 * @param maintable
	 */
	public void removeMainTable(MainTable maintable);
	/**
	 * 通过表id查询表信息
	 * @param maintableid
	 * @return
	 */
	public MainTable findMainTableByMainId(Long maintableid);
	/**
	 * 删除表通过表id
	 * @param maintableid
	 */
	public void removeMainTable(Long maintableid);
	/**
	 * 获取表字段列表信息
	 * @return
	 */
	public List findMainTableColumn();
	
	/**
	 * 获取表字段信息通过表id
	 */
	public List findMainTableColumnById(Long mainTableId);
	
	/**
	 * 通过表字段的id获取表字段的信息
	 * @param columnid
	 * @return
	 */
	public MainTableColumn findMainTableColumnByColumnId(Long columnid);
	
	/**
	 * 保存主表字段信息
	 * @param mainTableColumn
	 * @return
	 */
	public MainTableColumn saveMainTableColumn(MainTableColumn mainTableColumn);
	
	/**
	 * 删除字段通过字段id
	 * @param columnid
	 */
	public void removeMainTableColumnById(Long columnid);
	
	/**
	 * 获取普通树的的节点json
	 * @param tlist
	 * @return
	 */
	public String getTreeJson(List<TreeObject> tlist,String ischecked,String pid);
	
	/**
	 * 获取菜单树的节点json
	 * 
	 * @param tlist
	 * @return
	 */
	public String getMenuTreeJson(List<MenuInfo> tlist);
	
	/**
	 * 保存加载字段
	 * @param tableid
	 * @param maintable
	 * @return
	 */
	public List  saveLoadMainTableColumn(String tableid,MainTable maintable);
	
	public List saveMainTableColumns(HttpServletRequest request);
}

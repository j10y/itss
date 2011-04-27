package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 页面模型面板中间表
 * @Class Name PageModelPanelMiddleTable
 * @Author Administrator
 * @Create In Mar 10, 2009
 */
public class PageModelPanelMiddleTable extends BaseObject {
	private Long id;
	private PageModel pageModel; 			//configItemModel

	private SystemMainTable middleTable; //需求服务项关联表
	private SystemMainTableColumn subColumn; //关联表的配置项ID字段 
	private SystemMainTableColumn parentColumn;	//需求字段
	
	
	private PagePanel subPagePanel;		//配置项面板??
	private SystemMainTable subPanelTable; //配置项子表??
		
	private PagePanel parentPagePanel;		//需求面板
	private SystemMainTable parentPanelTable; //需求面板主表
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public SystemMainTable getMiddleTable() {
		return middleTable;
	}
	public void setMiddleTable(SystemMainTable middleTable) {
		this.middleTable = middleTable;
	}
	public SystemMainTableColumn getSubColumn() {
		return subColumn;
	}
	public void setSubColumn(SystemMainTableColumn subColumn) {
		this.subColumn = subColumn;
	}
	public PagePanel getSubPagePanel() {
		return subPagePanel;
	}
	public void setSubPagePanel(PagePanel subPagePanel) {
		this.subPagePanel = subPagePanel;
	}
	public SystemMainTable getSubPanelTable() {
		return subPanelTable;
	}
	public void setSubPanelTable(SystemMainTable subPanelTable) {
		this.subPanelTable = subPanelTable;
	}
	public SystemMainTableColumn getParentColumn() {
		return parentColumn;
	}
	public void setParentColumn(SystemMainTableColumn parentColumn) {
		this.parentColumn = parentColumn;
	}
	public PagePanel getParentPagePanel() {
		return parentPagePanel;
	}
	public void setParentPagePanel(PagePanel parentPagePanel) {
		this.parentPagePanel = parentPagePanel;
	}
	public SystemMainTable getParentPanelTable() {
		return parentPanelTable;
	}
	public void setParentPanelTable(SystemMainTable parentPanelTable) {
		this.parentPanelTable = parentPanelTable;
	}
	
}

package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 描述分组面板中的多个Panel之间的关系
 * @Class Name PageModelPanelTable
 * @Author sa
 * @Create In 2008-12-5
 */
public class PageGroupPanelTable extends BaseObject {
	private Long id;
	private PagePanel pagePanel; 			//分组面板，如内部客户分组面板
	
	private PagePanel subPagePanel;			//内部客户联系人面板
	private SystemMainTable subPanelTable; //内部客户联系
	private SystemMainTableColumn subPanelTableFColumn; //所属内部客户
	
	private PagePanel parentPagePanel;		//内部客户面板
	private SystemMainTable parentPanelTable; //内部客户
	private SystemMainTableColumn parentPanelTablePColumn;//内部客户的id字段
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public PageGroupPanelTable() {
		super();
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
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
	public SystemMainTableColumn getParentPanelTablePColumn() {
		return parentPanelTablePColumn;
	}
	public void setParentPanelTablePColumn(
			SystemMainTableColumn parentPanelTablePColumn) {
		this.parentPanelTablePColumn = parentPanelTablePColumn;
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
	public SystemMainTableColumn getSubPanelTableFColumn() {
		return subPanelTableFColumn;
	}
	public void setSubPanelTableFColumn(SystemMainTableColumn subPanelTableFColumn) {
		this.subPanelTableFColumn = subPanelTableFColumn;
	}
	
	
}

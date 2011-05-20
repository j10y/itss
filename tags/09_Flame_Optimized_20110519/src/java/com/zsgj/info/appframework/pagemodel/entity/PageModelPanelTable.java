package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 描述PageModel中的多个Panel之间的关系
 * @Class Name PageModelPanelTable
 * @Author sa
 * @Create In 2008-12-5
 */
public class PageModelPanelTable extends BaseObject {
	private Long id;
	private PageModel pageModel; 			//configItemModel
	
	private PagePanel subPagePanel;			//financePanel
	private SystemMainTable subPanelTable; //ConfigItemFinanceInfo
	private SystemMainTableColumn subPanelTableFColumn; //ConfigItemFinanceInfo.configItem
	
	private PagePanel parentPagePanel;		//configItemPanel
	private SystemMainTable parentPanelTable; //ConfigItem
	private SystemMainTableColumn parentPanelTablePColumn;//ConfigItem.id
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

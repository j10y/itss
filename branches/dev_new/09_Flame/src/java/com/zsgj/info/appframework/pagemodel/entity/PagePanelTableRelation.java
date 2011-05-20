package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

public class PagePanelTableRelation extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 8033293034792700847L;
	private Long id;
	private PagePanel pagePanel;
	private SystemMainTable systemMainTable;
	private SystemMainTableColumn systemMainTablePKColumn; //Áô´ýÀ©Õ¹
	/**
	 * @Return the SystemMainTableColumn systemMainTablePKColumn
	 */
	public SystemMainTableColumn getSystemMainTablePKColumn() {
		return systemMainTablePKColumn;
	}
	/**
	 * @Param SystemMainTableColumn systemMainTablePKColumn to set
	 */
	public void setSystemMainTablePKColumn(
			SystemMainTableColumn systemMainTablePKColumn) {
		this.systemMainTablePKColumn = systemMainTablePKColumn;
	}
	private SystemMainTableColumn foreignTableColumn;
	private SystemMainTable foreignTable;
	
	public SystemMainTable getForeignTable() {
		return foreignTable;
	}
	public void setForeignTable(SystemMainTable foreignTable) {
		this.foreignTable = foreignTable;
	}
	public SystemMainTableColumn getForeignTableColumn() {
		return foreignTableColumn;
	}
	public void setForeignTableColumn(SystemMainTableColumn foreignTableColumn) {
		this.foreignTableColumn = foreignTableColumn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	} 
	
}

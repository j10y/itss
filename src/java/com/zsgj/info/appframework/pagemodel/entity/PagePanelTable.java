package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

public class PagePanelTable extends BaseObject {
	private Long id;
	private PagePanel pagePanel;
	private SystemMainTable systemMainTable;
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

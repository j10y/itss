package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 查询面板与查询相关的配置信息
 * @Class Name PagePanelQueryInfo
 * @Author sa
 * @Create In 2009-3-12
 */
public class PagePanelQueryInfo extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 4309510288988703031L;
	private Long id;
	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Return the PagePanel resultPanel
	 */
	public PagePanel getResultPanel() {
		return resultPanel;
	}
	/**
	 * @Param PagePanel resultPanel to set
	 */
	public void setResultPanel(PagePanel resultPanel) {
		this.resultPanel = resultPanel;
	}
	/**
	 * @Return the SystemMainTable rootTable
	 */
	public SystemMainTable getRootTable() {
		return rootTable;
	}
	/**
	 * @Param SystemMainTable rootTable to set
	 */
	public void setRootTable(SystemMainTable rootTable) {
		this.rootTable = rootTable;
	}
	/**
	 * @Return the SystemMainTableColumn projectionColumn
	 */
	public SystemMainTableColumn getProjectionColumn() {
		return projectionColumn;
	}
	/**
	 * @Param SystemMainTableColumn projectionColumn to set
	 */
	public void setProjectionColumn(SystemMainTableColumn projectionColumn) {
		this.projectionColumn = projectionColumn;
	}
	/**
	 * @Return the long serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private PagePanel resultPanel;
	private SystemMainTable rootTable;
	private SystemMainTableColumn projectionColumn;
//	private Result
	
}

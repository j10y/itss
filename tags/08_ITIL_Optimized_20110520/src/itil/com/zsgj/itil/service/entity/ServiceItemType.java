package com.zsgj.itil.service.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.itil.config.entity.CIBaseType;

/**
 * 服务项类型
 * @Class Name ServiceItemType
 * @Author sa
 * @Create In 2009-2-11
 */
@SuppressWarnings("serial")
public class ServiceItemType extends CIBaseType{
	public static final Integer DEPLOYFLAG_YES=1;
	public static final Integer DEPLOYFLAG_NO=0;
	private Long id;
	private String name;
	private ServiceItemType parentServiceItemType;
	//specialFlag原来好像没用了，就用来标识事件中服务类型中的ERP常规，ERP个性化，账号申请是否显示
	private Integer specialFlag;

	private SystemMainTable systemMainTable; //需求表
	private String className;//2个冗余字段
	private String tableName;
	
	private PagePanel pagePanel;
	private PagePanel groupPanel;
	private Integer deployFlag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ServiceItemType getParentServiceItemType() {
		return parentServiceItemType;
	}
	public void setParentServiceItemType(ServiceItemType parentServiceItemType) {
		this.parentServiceItemType = parentServiceItemType;
	}

	public Integer getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(Integer specialFlag) {
		this.specialFlag = specialFlag;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	public PagePanel getGroupPanel() {
		return groupPanel;
	}
	public void setGroupPanel(PagePanel groupPanel) {
		this.groupPanel = groupPanel;
	}
	public Integer getDeployFlag() {
		return deployFlag;
	}
	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}
}

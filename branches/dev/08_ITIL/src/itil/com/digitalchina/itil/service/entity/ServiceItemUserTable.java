package com.digitalchina.itil.service.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 服务项数据的用户主表（需求表）关系实体
 * 发布列表页面可以通过读取ServiceItemUserTable显示所有SCID的需求表列表
 * 点击一个需求表后，通过ServiceItemUserTable的id获取SCID基本信息（只读）
 * 和需求表信息，进而对需求表进行维护，包括维护字段
 * 
 * @Class Name ServiceItemUserTable
 * @Author sa
 * @Create In 2009-2-23
 */
public class ServiceItemUserTable extends BaseObject {
	private Long id;
	//所属服务项类型，冗余字段
	private ServiceItemType serviceItemType;
	//给哪个服务项配置用户主表（需求表）
	private ServiceItem serviceItem; 
	//用户表（需求表）
	private SystemMainTable systemMainTable;
	private String className;//2个冗余字段，用于后台观察数据
	private String tableName;
	
	//表单面板
	private PagePanel pagePanel;
	//列表页面面板
	private PagePanel pageListPanel;
	
	//分组面板C
	private PagePanel groupPanel;
	
	//发布标记，注意发布时级联设置SystemMainTable里的deployFlag
	private Integer deployFlag;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getDeployFlag() {
		return deployFlag;
	}

	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}

	public PagePanel getGroupPanel() {
		return groupPanel;
	}

	public void setGroupPanel(PagePanel groupPanel) {
		this.groupPanel = groupPanel;
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

	public ServiceItem getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}

	public ServiceItemType getServiceItemType() {
		return serviceItemType;
	}

	public void setServiceItemType(ServiceItemType serviceItemType) {
		this.serviceItemType = serviceItemType;
	}

	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}

	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((deployFlag == null) ? 0 : deployFlag.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((serviceItem == null) ? 0 : serviceItem.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ServiceItemUserTable other = (ServiceItemUserTable) obj;
		if (deployFlag == null) {
			if (other.deployFlag != null)
				return false;
		} else if (!deployFlag.equals(other.deployFlag))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (serviceItem == null) {
			if (other.serviceItem != null)
				return false;
		} else if (!serviceItem.equals(other.serviceItem))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		return true;
	}

	public PagePanel getPageListPanel() {
		return pageListPanel;
	}

	public void setPageListPanel(PagePanel pageListPanel) {
		this.pageListPanel = pageListPanel;
	}
}

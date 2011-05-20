package com.zsgj.itil.config.entity;

import java.util.HashSet;
import java.util.Set;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * 配置项类型
 * @Class Name ConfigItemType
 * @Author sa
 * @Create In 2008-11-9
 */

public class ConfigItemType extends CIBaseType{
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -1082044318650029237L;
	public static final Integer DEPLOYFLAG_YES=1;
	public static final Integer DEPLOYFLAG_NO=0;
	public static final Long  DELIVERYTEAM_ID = 105L; //交付团队id是105
	public static final Long SERVICEPROVIDER_ID = 267L;//服务商id是267；
	public static final Long  SERVICEENGINEER_ID=228L;//服务工程师id 是228
	
	private Long id; 
	private String name;
	private ConfigItemType parentConfigItemType;
	private Integer serviceFlag;//服务标识
	
	/**
	 * @Return the Integer serviceFlag
	 */
	public Integer getServiceFlag() {
		return serviceFlag;
	}
	/**
	 * @Param Integer serviceFlag to set
	 */
	public void setServiceFlag(Integer serviceFlag) {
		this.serviceFlag = serviceFlag;
	}
	/**
	 * @Return the String pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @Param String pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	private String pid;
	
	private String className;
	private String tableName;
	private SystemMainTable systemMainTable;
	private PagePanel pagePanel;
	private PagePanel groupPanel;
	private Integer deployFlag;
	
	private Set<ConfigItemType> 
		childConfigItemTypes = new HashSet<ConfigItemType>();
	
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
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ConfigItemType other = (ConfigItemType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public Set<ConfigItemType> getChildConfigItemTypes() {
		return childConfigItemTypes;
	}
	public void setChildConfigItemTypes(Set<ConfigItemType> childConfigItemTypes) {
		this.childConfigItemTypes = childConfigItemTypes;
	}
	public ConfigItemType getParentConfigItemType() {
		return parentConfigItemType;
	}
	public void setParentConfigItemType(ConfigItemType parentConfigItemType) {
		this.parentConfigItemType = parentConfigItemType;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
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
	public ConfigItemType(long id) {
		super();
		this.id = id;
	}
	public ConfigItemType() {
	}
}

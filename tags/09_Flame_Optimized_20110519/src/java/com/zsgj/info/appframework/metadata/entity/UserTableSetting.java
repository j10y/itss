package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.security.entity.UserInfo;

public class UserTableSetting extends Column {

	private static final Long serialVersionUID = 2781399575809234181L;	
	public static final Integer LIST = new Integer(1);
	public static final Integer INPUT = new Integer(2);
	public static final Integer QUERY = new Integer(3);
	public static final Integer MUL_QUERY = new Integer(4); //复合查询字段的可见字段类型
	public static final Integer EXPORT = new Integer(5);
	
	private Long id;
	
	private SystemTableSetting sysTableSetting; //对应系统可见字段
	
	private SystemTableRoleColumn sysTableRoleColumn; //对应的系统角色可见字段
	
	private UserInfo userInfo;
	
	private SystemMainTable systemMainTable;
	private SystemMainTableColumn mainTableColumn;
	//private SystemMainTableExtColumn extendTableColumn;
	
	private Integer settingType;
	
	private SystemTableQuery systemTableQuery; //已废弃属性
	
	private Integer isDisplay;
	private Integer order;
	private String descn;
	
	private String lengthForPage; //搜索条件优势可能需要设置默认宽度以便于extJS界面调整
	private Integer isMustInput; //必须则不可以隐藏
	private String hiddenValue;
	
	public String getHiddenValue() {
		return hiddenValue;
	}
	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}
	public Integer getIsMustInput() {
		return isMustInput;
	}
	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}
	public String getLengthForPage() {
		return lengthForPage;
	}
	public void setLengthForPage(String lengthForPage) {
		this.lengthForPage = lengthForPage;
	}
	public Column getColumn(){
		if(this.getMainTableColumn()!=null){
			return this.getMainTableColumn();
		}
//		else if(this.getExtendTableColumn()!=null){
//			return this.getExtendTableColumn();
//		}
		else {
			return null;
		}
	}
	@Override
	public String getColumnName() {
		Column column = this.getColumn();
		return column.getColumnName();
	}

	@Override
	public ValidateType getValidateType() {
		return this.getColumn().getValidateType();
	}
	@Override
	public String getPropertyName() {
		Column column = this.getColumn();
		return column.getPropertyName();
	}
	public String getColumnCnName(){
		Column column = this.getColumn();
		return column.getColumnCnName();
	}
	
	public boolean isParentProperty() {
		if(this.getPropertyName().startsWith("parent")) return true;
		return false;
	}
	
	@Override
	public SystemMainTable getForeignTable() {
		return this.getColumn().getForeignTable();
	}
	
	@Override
	public SystemMainTableColumn getForeignTableKeyColumn() {
		Column column = this.getColumn();
		return column.getForeignTableKeyColumn();
	}

	@Override
	public SystemMainTableColumn getForeignTableValueColumn() {
		Column column = this.getColumn();
		return column.getForeignTableValueColumn();
	}
	
	@Override
	public Integer getIsUpdateItem() {
		Column column = this.getColumn();
		return column.getIsUpdateItem();
	}
	
	@Override
	public Integer getIsHiddenItem() {
		return this.getColumn().getIsHiddenItem();
	}
	
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((descn == null) ? 0 : descn.hashCode());
		//result = PRIME * result + ((extendTableColumn == null) ? 0 : extendTableColumn.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((isDisplay == null) ? 0 : isDisplay.hashCode());
		//result = PRIME * result + ((mainTableColumn == null) ? 0 : mainTableColumn.hashCode());
		result = PRIME * result + ((order == null) ? 0 : order.hashCode());
		result = PRIME * result + ((userInfo == null) ? 0 : userInfo.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserTableSetting other = (UserTableSetting) obj;
		if (descn == null) {
			if (other.descn != null)
				return false;
		} else if (!descn.equals(other.descn))
			return false;
		/*if (extendTableColumn == null) {
			if (other.extendTableColumn != null)
				return false;
		} else if (!extendTableColumn.equals(other.extendTableColumn))
			return false;*/
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDisplay == null) {
			if (other.isDisplay != null)
				return false;
		} else if (!isDisplay.equals(other.isDisplay))
			return false;
		/*if (mainTableColumn == null) {
			if (other.mainTableColumn != null)
				return false;
		} else if (!mainTableColumn.equals(other.mainTableColumn))
			return false;*/
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}
//	public SystemMainTableExtColumn getExtendTableColumn() {
//		return extendTableColumn;
//	}
//	public void setExtendTableColumn(SystemMainTableExtColumn extendTableColumn) {
//		this.extendTableColumn = extendTableColumn;
//	}
	public SystemMainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}
	public void setMainTableColumn(SystemMainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public Integer getSettingType() {
		return settingType;
	}
	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}
	public SystemTableQuery getSystemTableQuery() {
		return systemTableQuery;
	}
	public void setSystemTableQuery(SystemTableQuery systemTableQuery) {
		this.systemTableQuery = systemTableQuery;
	}
	public SystemTableSetting getSysTableSetting() {
		return sysTableSetting;
	}
	public void setSysTableSetting(SystemTableSetting sysTableSetting) {
		this.sysTableSetting = sysTableSetting;
	}
	public SystemTableRoleColumn getSysTableRoleColumn() {
		return sysTableRoleColumn;
	}
	public void setSysTableRoleColumn(SystemTableRoleColumn sysTableRoleColumn) {
		this.sysTableRoleColumn = sysTableRoleColumn;
	}
	
}
	
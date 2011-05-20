package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 用户表查询字段设置实体，一个查询可能包含多个表
 * @Class Name UserTableQueryColumn
 * @Author peixf
 * @Create In 2008-4-16
 */
public class UserTableQueryColumn extends Column {
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -4572716654431783497L;

	private Long id;
	
	private SystemTableQueryColumn sysTableQueryColumn; //对应的系统可见查询字段
	
	private SystemTableQuery systemTableQuery;
	
	private SystemTableQueryColumn systemTableQueryColumn;
	
	private SystemMainTable systemMainTable;

	private SystemMainTableColumn mainTableColumn;
//	private SystemMainTableExtColumn extendTableColumn;
	
	//private Integer matchMode; //此字段的查询匹配模式
	private MatchMode matchMode;
	
	private Integer isDisplay;
	private Integer order;
	
	private String lengthForPage; //搜索条件优势可能需要设置默认宽度以便于extJS界面调整
	private Integer isMustInput; //必须则不可以隐藏
	private Integer isHiddenItem; //是否是隐藏搜索域
	private String hiddenValue;  
	private UserInfo userInfo;
	
	
	//private Integer isHiddenItem; //是否是隐藏搜索域，考虑是查询功能的额外参数解决
//	此隐藏搜索域从请求或session取值的属性名称，如客户案例查询hidden value=${solutionId},即hiddenValue=solutionId
	//private String hiddenValue;  
	

	
	/*public String getMatchMode(){
		MatchMode matchMode = getSystemTableQueryColumn().getMatchMode();
		if(matchMode == null) return null;
		return matchMode.getMatchModeName();
	}*/
	
	public String getDescn() {
		return this.getColumn().getDescn();
	}
	
	public Column getColumn(){
		return this.getSystemTableQueryColumn().getColumn();
	}
	
	public String getColumnCnName(){
		return this.getSystemTableQueryColumn().getColumnCnName();
	}
	
	public String getColumnName(){
		return this.getSystemTableQueryColumn().getColumnName();
	}
	
	@Override
	public Integer getIsUpdateItem() {
		return this.getColumn().getIsUpdateItem();
	}
	
	@Override
	public ValidateType getValidateType() {
		return this.getColumn().getValidateType();
	}
	@Override
	public SystemMainTable getForeignTable() {
		Column column = this.getColumn();
		return column.getForeignTable();
	}
	
	@Override
	public Integer getIsHiddenItem() {
		return isHiddenItem;
	}
	
	@Override
	public SystemMainTableColumn getForeignTableKeyColumn() {
		Column column = this.getColumn();
		return column.getForeignTableKeyColumn();
	}
	public boolean isParentProperty() {
		if(this.getPropertyName().startsWith("parent")) return true;
		return false;
	}
	@Override
	public SystemMainTableColumn getForeignTableValueColumn() {
		Column column = this.getColumn();
		return column.getForeignTableValueColumn();
	}
	
	@Override
	public String getPropertyName() {
		return  this.getSystemTableQueryColumn().getPropertyName();
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((systemTableQuery == null) ? 0 : systemTableQuery.hashCode());
		result = PRIME * result + ((systemTableQueryColumn == null) ? 0 : systemTableQueryColumn.hashCode());
		result = PRIME * result + ((userInfo == null) ? 0 : userInfo.hashCode());
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
		final UserTableQueryColumn other = (UserTableQueryColumn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (systemTableQuery == null) {
			if (other.systemTableQuery != null)
				return false;
		} else if (!systemTableQuery.equals(other.systemTableQuery))
			return false;
		if (systemTableQueryColumn == null) {
			if (other.systemTableQueryColumn != null)
				return false;
		} else if (!systemTableQueryColumn.equals(other.systemTableQueryColumn))
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
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
	public SystemTableQuery getSystemTableQuery() {
		return systemTableQuery;
	}
	public void setSystemTableQuery(SystemTableQuery systemTableQuery) {
		this.systemTableQuery = systemTableQuery;
	}
	public SystemTableQueryColumn getSystemTableQueryColumn() {
		return systemTableQueryColumn;
	}
	public void setSystemTableQueryColumn(
			SystemTableQueryColumn systemTableQueryColumn) {
		this.systemTableQueryColumn = systemTableQueryColumn;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

//	public SystemMainTableExtColumn getExtendTableColumn() {
//		return extendTableColumn;
//	}
//
//	public void setExtendTableColumn(SystemMainTableExtColumn extendTableColumn) {
//		this.extendTableColumn = extendTableColumn;
//	}

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

	public SystemMainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}

	public void setMainTableColumn(SystemMainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}

	public MatchMode getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}

	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}

	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}

	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	public void setIsHiddenItem(Integer isHiddenItem) {
		this.isHiddenItem = isHiddenItem;
	}

	public SystemTableQueryColumn getSysTableQueryColumn() {
		return sysTableQueryColumn;
	}

	public void setSysTableQueryColumn(SystemTableQueryColumn sysTableQueryColumn) {
		this.sysTableQueryColumn = sysTableQueryColumn;
	}

	
	
}
	
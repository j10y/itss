package com.zsgj.info.appframework.metadata.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * 系统表查询字段设置实体，一个查询可能包含多个表
 * @Class Name UserTableQueryColumn
 * @Author peixf
 * @Create In 2008-4-16
 */
public class SystemTableQueryColumn extends Column {
	private static final Long serialVersionUID = -2888258840469197260L;
	

	public static final Integer MATCH_MODE_ANYWHERE = new Integer(1);
	public static final Integer MATCH_MODE_EXACT = new Integer(2);
	public static final Integer MATCH_MODE_START = new Integer(3);
	public static final Integer MATCH_MODE_END = new Integer(4);
	public static final Integer MATCH_MODE_BETWEEN = new Integer(5);

	
	private Long id;
	
	private SystemTableQuery systemTableQuery;
	
	private SystemMainTable systemMainTable;

	private SystemMainTableColumn mainTableColumn;
	//private SystemMainTableExtColumn extendTableColumn;
	
	//private Integer matchMode; //此字段的查询匹配模式
	private MatchMode matchMode;
	
	private Integer isDisplay;
	private Integer order;
	
	private String lengthForPage; //搜索条件优势可能需要设置默认宽度以便于extJS界面调整
	private Integer isHiddenItem; //是否是隐藏搜索域
//	此隐藏搜索域从请求或session取值的属性名称，如客户案例查询hidden value=${solutionId},即hiddenValue=solutionId
	private String hiddenValue;  
	private Integer isMustInput;
	
	private transient boolean isParentType = false;

	private Set<UserTableQueryColumn> userTableQueryColumns = new HashSet<UserTableQueryColumn>(0);

	
	@Override
	public String getDescn() {
		return this.getColumn().getDescn();
	}

	public Set<UserTableQueryColumn> getUserTableQueryColumns() {
		return userTableQueryColumns;
	}

	public void setUserTableQueryColumns(
			Set<UserTableQueryColumn> userTableQueryColumns) {
		this.userTableQueryColumns = userTableQueryColumns;
	}

	@Override
	public String getPropertyName() {
		Column column = this.getColumn();
		return column.getPropertyName();
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
	
	public boolean isParentProperty() {
		if(this.getPropertyName().startsWith("parent")) return true;
		return false;
	}
	
	@Override
	public ValidateType getValidateType() {
		return this.getColumn().getValidateType();
	}

	public Integer getIsHiddenItem() {
		return isHiddenItem;
	}

	public String getColumnCnName(){
		Column column = this.getColumn();
		return column.getColumnCnName();
	}
	
	public String getColumnName(){
		Column column = this.getColumn();
		return column.getColumnName();
	}
	
	@Override
	public SystemMainTable getForeignTable() {
		Column column = this.getColumn();
		return column.getForeignTable();
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
		return this.getColumn().getIsUpdateItem();
	}
	
	public boolean isSystemColumn(){
		if(this.getMainTableColumn().getIsExtColumn()==SystemMainTableColumn.isMain){
			return true;
		}
		return false;
	}
	
	public boolean isExtendColumn(){
		if(this.getMainTableColumn().getIsExtColumn()==SystemMainTableColumn.isExt){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		//result = PRIME * result + ((extendTableColumn == null) ? 0 : extendTableColumn.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((mainTableColumn == null) ? 0 : mainTableColumn.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
		result = PRIME * result + ((systemTableQuery == null) ? 0 : systemTableQuery.hashCode());
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
		final SystemTableQueryColumn other = (SystemTableQueryColumn) obj;
//		if (extendTableColumn == null) {
//			if (other.extendTableColumn != null)
//				return false;
//		} else if (!extendTableColumn.equals(other.extendTableColumn))
//			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mainTableColumn == null) {
			if (other.mainTableColumn != null)
				return false;
		} else if (!mainTableColumn.equals(other.mainTableColumn))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		if (systemTableQuery == null) {
			if (other.systemTableQuery != null)
				return false;
		} else if (!systemTableQuery.equals(other.systemTableQuery))
			return false;
		return true;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
//	public SystemMainTableExtColumn getExtendTableColumn() {
//		return extendTableColumn;
//	}
//	public void setExtendTableColumn(SystemMainTableExtColumn extendTableColumn) {
//		this.extendTableColumn = extendTableColumn;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemMainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}
	public void setMainTableColumn(SystemMainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}
	
	public SystemTableQuery getSystemTableQuery() {
		return systemTableQuery;
	}
	public void setSystemTableQuery(SystemTableQuery systemTableQuery) {
		this.systemTableQuery = systemTableQuery;
	}

	public boolean isParentType() {
		return isParentType;
	}

	public void setParentType(boolean isParentType) {
		this.isParentType = isParentType;
	}

	public MatchMode getMatchMode() {
		return matchMode;
	}
	public String getMatchModeStr(){
		MatchMode matchMode = this.getMatchMode();
		if(matchMode == null) return null;
		return matchMode.getMatchModeName();
	}
	public Integer getMatchModeAsInt() {
		if(this.matchMode==null) return null;
		return new Integer(this.matchMode.getId().intValue());
	}
	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}
	
	public void setMatchModeAsLong(Long matchModeId) {
		this.matchMode = new MatchMode(matchModeId);
	}
	
	public void setMatchMode(Integer matchModeId) {
		this.matchMode = new MatchMode(matchModeId);
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

	public Integer getIsMustInput() {
		return isMustInput;
	}

	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}

	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	public String getLengthForPage() {
		return lengthForPage;
	}

	public void setLengthForPage(String lengthForPage) {
		this.lengthForPage = lengthForPage;
	}

	public void setIsHiddenItem(Integer isHiddenItem) {
		this.isHiddenItem = isHiddenItem;
	}
}
	
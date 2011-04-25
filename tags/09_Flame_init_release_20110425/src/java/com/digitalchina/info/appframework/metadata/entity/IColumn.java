package com.digitalchina.info.appframework.metadata.entity;

public interface IColumn {
	public String getUploadUrl();
	public String getFileNamePrefix();
	public SystemMainTableColumn getSystemFileNameColumn();
	public SystemMainTableColumn getFileNameColumn();
	
	public String getColumnAlign();
	public SystemMainTableColumn getForeignTableParentColumn();
			
	public Integer getForeignTableValueColumnOrder();
	public String getHtmlEncodeFlag();

	public Integer getIsImeItem();
	public Integer getIsMustInput();
	public Integer getIsNullForeignColumn();
	public Integer getIsOutputItem();
	public Integer getIsSearchItem();
	public String getLengthForPage();
	public Integer getOrder();
	public PropertyType getPropertyType();
	public SystemMainTable getReferencedTable();
	public SystemMainTableColumn getReferencedTableKeyColumn();
	public SystemMainTableColumn getReferencedTableValueColumn();
	
	public SystemMainTable getSystemMainTable();
	public SystemMainTableColumnType getSystemMainTableColumnType();
	public String getTableName();

	public String getColumnCnName();
	public String getColumnName();
	public String getDescn();
	public SystemMainTable getForeignTable();
	public SystemMainTableColumn getForeignTableKeyColumn();
	public SystemMainTableColumn getForeignTableValueColumn();
	public Integer getIsHiddenItem();
	public Integer getIsUpdateItem();
	public String getPropertyName();
	public ValidateType getValidateType();
}

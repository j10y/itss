package com.digitalchina.info.appframework.metadata.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * �����ֶ����ͣ��ṩ����ϵͳ���ֶκ���չ�ֶε�ͳһ�ӿڡ� 
 * �����ֶ�����չ�ֶ���������������δͳһ���ԣ������Ӵ˳��� �࣬ǿ������ͳһ����ķ��ʽӿڡ�
 * 
 * @Class Name Column
 * @version 3 �����Ż���������
 * @Author peixf
 * @Create In 2008-7-1
 */
public abstract class Column extends BaseObject /*implements IColumn*/{
	public Integer getBigFlag() {
		return null;
	}
	
	public Integer getColumnSum() {
		return null;
	}
	
	public Integer getUniqueFlag() {
		return null;
	}

	public String getUploadUrl(){
		return null;
	}
	
	public String getFileNamePrefix(){
		return null;
	}
	
	public SystemMainTableColumn getSystemFileNameColumn(){
		return null;
	}
	
	public SystemMainTableColumn getFileNameColumn(){
		return null;
	}
	
	public String getColumnAlign() {
		return null;
	}

	public Integer getExtSelectType(){
		return null;
	}
	
	public SystemMainTableColumn getForeignTableParentColumn() {
		return null;
	}

	public Integer getForeignTableValueColumnOrder() {
		return null;
	}

	public String getHtmlEncodeFlag() {
		return null;
	}

	public Integer getIsImeItem() {
		return null;
	}

	public Integer getIsMustInput() {
		return null;
	}

	public Integer getIsNullForeignColumn() {
		return null;
	}

	public Integer getIsOutputItem() {
		return null;
	}

	public Integer getIsSearchItem() {
		return null;
	}

	public String getLengthForPage() {
		return null;
	}
	
	public String getHeightForPage() {
		return null;
	}

	public Integer getOrder() {
		return null;
	}

	public PropertyType getPropertyType() {
		return null;
	}

	public SystemMainTable getReferencedTable() {
		return null;
	}

	public SystemMainTableColumn getReferencedTableKeyColumn() {
		return null;
	}

	public SystemMainTableColumn getReferencedTableValueColumn() {
		return null;
	}
	
	public SystemMainTableColumn getReferencedTableParentColumn() {
		return null;
	}

	public Integer getReferencedTableValueColumnOrder(){
		return null;
	}
	
	public SystemMainTable getSystemMainTable() {
		return null;
	}

	public SystemMainTableColumnType getSystemMainTableColumnType() {
		return null;
	}

	public String getTableName() {
		return null;
	}

	public String getColumnCnName() {
		return null;
	}

	public String getColumnName() {
		return null;
	}

	public String getDescn() {
		return null;
	}

	public SystemMainTable getForeignTable() {
		return null;
	}

	public SystemMainTableColumn getForeignTableKeyColumn() {
		return null;
	}

	public SystemMainTableColumn getForeignTableValueColumn() {
		return null;
	}

	public Integer getIsHiddenItem() {
		return null;
	}

	public Integer getIsUpdateItem() {
		return null;
	}

	public String getPropertyName() {
		return null;
	}

	public ValidateType getValidateType() {
		return null;
	}
	
	public Integer getDiscFlag() {
		return null;
	}

	public SystemMainTableColumn getDiscColumn() {
		return null;
	}

	public SystemMainTable getForeignDiscTable() {
		return null;
	}
	
	public Integer getAbstractFlag() {
		return null;
	}

//	public Integer getUniqueFlag() {
//		return null;
//	}
//
//	public Integer getDateLogFlag() {
//		return null;
//	}
//
//
//	public Integer getUserLogFlag() {
//		return null;
//	}


}

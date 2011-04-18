package com.digitalchina.info.framework.dao;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;

/**
 * 区分字段共用引用基类
 * @Class Name BaseDiscObject
 * @Author sa
 * @Create In 2009-1-20
 */
public class DiscObject extends BaseObject {
	private Long id;
	private String discValue;
	private SystemMainTable systemMainTable;
	
	private SystemMainTableColumn primaryKeyColumn;
	private SystemMainTableColumn mainTableColumn;
	//private SystemMainTableExtColumn extendTableColumn;
	
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

	public String getDiscValue() {
		return discValue;
	}

	public void setDiscValue(String discValue) {
		this.discValue = discValue;
	}

//	public SystemMainTableExtColumn getExtendTableColumn() {
//		return extendTableColumn;
//	}
//
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

	public SystemMainTableColumn getPrimaryKeyColumn() {
		return primaryKeyColumn;
	}

	public void setPrimaryKeyColumn(SystemMainTableColumn primaryKeyColumn) {
		this.primaryKeyColumn = primaryKeyColumn;
	}

	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}

	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
}

package com.zsgj.info.appframework.metadata.entity;

/**
 * 主表外键引用多表映射表
 * @Class Name SystemMainTableColumnDiscInfo
 * @deprecated
 * @Author sa
 * @Create In 2008-12-8
 */
public class SystemMainTableColumnDisc {
	private Long id;
	private SystemMainTable systemMainTable;
	private SystemMainTableColumn abstractColumn;//隶属客户
	private SystemMainTableColumn discColumn;//客户类型
	
	private String discValue; //0内部客户
	private SystemMainTable abstractTable; //1外部客户
	
	public SystemMainTableColumn getAbstractColumn() {
		return abstractColumn;
	}
	public void setAbstractColumn(SystemMainTableColumn abstractColumn) {
		this.abstractColumn = abstractColumn;
	}

	public SystemMainTableColumn getDiscColumn() {
		return discColumn;
	}
	public void setDiscColumn(SystemMainTableColumn discColumn) {
		this.discColumn = discColumn;
	}
	public String getDiscValue() {
		return discValue;
	}
	public void setDiscValue(String discValue) {
		this.discValue = discValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public SystemMainTable getAbstractTable() {
		return abstractTable;
	}
	public void setAbstractTable(SystemMainTable abstractTable) {
		this.abstractTable = abstractTable;
	}
}

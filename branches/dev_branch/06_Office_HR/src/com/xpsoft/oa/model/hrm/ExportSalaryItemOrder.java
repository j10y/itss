package com.xpsoft.oa.model.hrm;

import com.xpsoft.core.model.BaseModel;

public class ExportSalaryItemOrder extends BaseModel{
	private Long id;
	private String exportName;
	private String fromTable;
	private String fromTableName;
	private Long fromTableType;
	private String fromTableTypeName;
	private Integer orderCol;
	private ExportSalary exportSalId;
	private Integer isDefaut;
	
	public String getFromTableName() {
		return fromTableName;
	}
	public void setFromTableName(String fromTableName) {
		this.fromTableName = fromTableName;
	}
	public String getFromTableTypeName() {
		return fromTableTypeName;
	}
	public void setFromTableTypeName(String fromTableTypeName) {
		this.fromTableTypeName = fromTableTypeName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExportName() {
		return exportName;
	}
	public void setExportName(String exportName) {
		this.exportName = exportName;
	}
	public String getFromTable() {
		return fromTable;
	}
	public void setFromTable(String fromTable) {
		this.fromTable = fromTable;
	}
	public Long getFromTableType() {
		return fromTableType;
	}
	public void setFromTableType(Long fromTableType) {
		this.fromTableType = fromTableType;
	}
	public Integer getOrderCol() {
		return orderCol;
	}
	public void setOrderCol(Integer orderCol) {
		this.orderCol = orderCol;
	}
	public ExportSalary getExportSalId() {
		return exportSalId;
	}
	public void setExportSalId(ExportSalary exportSalId) {
		this.exportSalId = exportSalId;
	}
	public Integer getIsDefaut() {
		return isDefaut;
	}
	public void setIsDefaut(Integer isDefaut) {
		this.isDefaut = isDefaut;
	}
	
	
}

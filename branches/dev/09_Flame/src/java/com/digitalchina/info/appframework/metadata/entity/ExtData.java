package com.digitalchina.info.appframework.metadata.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class ExtData extends BaseObject{
	private static final Long serialVersionUID = -1021390386911002253L;
	private Long id;
	private Integer extendTableId;//扩展字段id编号
	private Integer mainTableRowID;//主表实体数据的行号，对应实体的第几条数据
	private String extendTableData;//扩展字段存放的数据
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((extendTableData == null) ? 0 : extendTableData.hashCode());
		result = prime * result
				+ ((extendTableId == null) ? 0 :extendTableId.hashCode());
		result = prime * result
				+ ((mainTableRowID == null) ? 0 : mainTableRowID.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final ExtData other = (ExtData) obj;
		if (extendTableData == null) {
			if (other.extendTableData != null)
				return false;
		} else if (!extendTableData.equals(other.extendTableData))
			return false;
		if (extendTableId == null) {
			if (other.extendTableId != null)
				return false;
		} else if (!extendTableId.equals(other.extendTableId))
			return false;
		if (mainTableRowID == null) {
			if (other.mainTableRowID != null)
				return false;
		} else if (!mainTableRowID.equals(other.mainTableRowID))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getExtendTableId() {
		return extendTableId;
	}
	public void setExtendTableId(Integer extendTableId) {
		this.extendTableId = extendTableId;
	}
	public Integer getMainTableRowID() {
		return mainTableRowID;
	}
	public void setMainTableRowID(Integer mainTableRowID) {
		this.mainTableRowID = mainTableRowID;
	}
	public String getExtendTableData() {
		return extendTableData;
	}
	public void setExtendTableData(String extendTableData) {
		this.extendTableData = extendTableData;
	}
	
	
	
}

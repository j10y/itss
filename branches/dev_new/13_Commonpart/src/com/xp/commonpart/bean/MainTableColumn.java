package com.xp.commonpart.bean;

public class MainTableColumn {
	private Long columnid;//字段id，唯一
	private Long maintableid;//表id，属于那张表的字段
	private String columnName;//字段名
	private String propertyName;//属性名
	private String columnCName;//字段中文名
	private String isQuery;//是否属于可查询字段
	private Integer isQueryOrder;//是否属于可查询字段排序
	private String isUpdate;//是否可修改
	private Integer isUpdateOrder;//是否可修改
	private String isInsert;//是否可增加，如果不需要，但是又有那个字段的时候可隐藏
	private Integer isInsertOrder;
	private String isExport;//是否给导出
	private Integer isExportOrder;//是否给导出排序
	private String isList;//是否在列表中显示字段值
	private Integer isListOrder;//是否在列表中显示字段值排序
	private String propertyType;//属性类型，入文本框，如期类型
	private String typeSql;//如果是下拉类表类型，则写sql 查处下拉列表的实际值和显示值（select id,name from table）
	private String dataType;//数据类型，在数据库中存的格式
	private String isMust;//是否必填	
	private String tolerant;//默认值
	private String isChecked;//当时弹出树是否多选
	private String likescope;//1:%data 2:data% 3:%data%
	private String isUnique;//验证唯一性
	private String upColumnName;//根据父id,生成子ID
	private String validDataType;//验证类型
	private String columnLength;//列表列表宽度
	private String allTreeStatus;//是否把只要选择子的，父的都默认选上
	
	public String getAllTreeStatus() {
		return allTreeStatus;
	}
	public void setAllTreeStatus(String allTreeStatus) {
		this.allTreeStatus = allTreeStatus;
	}
	public String getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}
	public String getIsUnique() {
		return isUnique;
	}
	public void setIsUnique(String isUnique) {
		this.isUnique = isUnique;
	}
	public String getUpColumnName() {
		return upColumnName;
	}
	public void setUpColumnName(String upColumnName) {
		this.upColumnName = upColumnName;
	}
	public String getLikescope() {
		return likescope;
	}
	public void setLikescope(String likescope) {
		this.likescope = likescope;
	}
	public String getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}
	public String getTolerant() {
		return tolerant;
	}
	public void setTolerant(String tolerant) {
		this.tolerant = tolerant;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	public Integer getIsUpdateOrder() {
		return isUpdateOrder;
	}
	public void setIsUpdateOrder(Integer isUpdateOrder) {
		this.isUpdateOrder = isUpdateOrder;
	}
	public Integer getIsInsertOrder() {
		return isInsertOrder;
	}
	public void setIsInsertOrder(Integer isInsertOrder) {
		this.isInsertOrder = isInsertOrder;
	}
	public Integer getIsQueryOrder() {
		return isQueryOrder;
	}
	public void setIsQueryOrder(Integer isQueryOrder) {
		this.isQueryOrder = isQueryOrder;
	}
	public Integer getIsExportOrder() {
		return isExportOrder;
	}
	public void setIsExportOrder(Integer isExportOrder) {
		this.isExportOrder = isExportOrder;
	}
	public Integer getIsListOrder() {
		return isListOrder;
	}
	public void setIsListOrder(Integer isListOrder) {
		this.isListOrder = isListOrder;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Long getColumnid() {
		return columnid;
	}
	public void setColumnid(Long columnid) {
		this.columnid = columnid;
	}
	public Long getMaintableid() {
		return maintableid;
	}
	public void setMaintableid(Long maintableid) {
		this.maintableid = maintableid;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getColumnCName() {
		return columnCName;
	}
	public void setColumnCName(String columnCName) {
		this.columnCName = columnCName;
	}
	public String getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getIsInsert() {
		return isInsert;
	}
	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}
	public String getIsExport() {
		return isExport;
	}
	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}
	public String getIsList() {
		return isList;
	}
	public void setIsList(String isList) {
		this.isList = isList;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getTypeSql() {
		return typeSql;
	}
	public void setTypeSql(String typeSql) {
		this.typeSql = typeSql;
	}
	
	public String getValidDataType() {
		return validDataType;
	}
	public void setValidDataType(String validDataType) {
		this.validDataType = validDataType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnCName == null) ? 0 : columnCName.hashCode());
		result = prime * result
				+ ((columnLength == null) ? 0 : columnLength.hashCode());
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result
				+ ((columnid == null) ? 0 : columnid.hashCode());
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((isChecked == null) ? 0 : isChecked.hashCode());
		result = prime * result
				+ ((isExport == null) ? 0 : isExport.hashCode());
		result = prime * result
				+ ((isExportOrder == null) ? 0 : isExportOrder.hashCode());
		result = prime * result
				+ ((isInsert == null) ? 0 : isInsert.hashCode());
		result = prime * result
				+ ((isInsertOrder == null) ? 0 : isInsertOrder.hashCode());
		result = prime * result + ((isList == null) ? 0 : isList.hashCode());
		result = prime * result
				+ ((isListOrder == null) ? 0 : isListOrder.hashCode());
		result = prime * result + ((isMust == null) ? 0 : isMust.hashCode());
		result = prime * result + ((isQuery == null) ? 0 : isQuery.hashCode());
		result = prime * result
				+ ((isQueryOrder == null) ? 0 : isQueryOrder.hashCode());
		result = prime * result
				+ ((isUnique == null) ? 0 : isUnique.hashCode());
		result = prime * result
				+ ((isUpdate == null) ? 0 : isUpdate.hashCode());
		result = prime * result
				+ ((isUpdateOrder == null) ? 0 : isUpdateOrder.hashCode());
		result = prime * result
				+ ((likescope == null) ? 0 : likescope.hashCode());
		result = prime * result
				+ ((maintableid == null) ? 0 : maintableid.hashCode());
		result = prime * result
				+ ((propertyName == null) ? 0 : propertyName.hashCode());
		result = prime * result
				+ ((propertyType == null) ? 0 : propertyType.hashCode());
		result = prime * result
				+ ((tolerant == null) ? 0 : tolerant.hashCode());
		result = prime * result + ((typeSql == null) ? 0 : typeSql.hashCode());
		result = prime * result
				+ ((upColumnName == null) ? 0 : upColumnName.hashCode());
		result = prime * result
				+ ((validDataType == null) ? 0 : validDataType.hashCode());
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
		final MainTableColumn other = (MainTableColumn) obj;
		if (columnCName == null) {
			if (other.columnCName != null)
				return false;
		} else if (!columnCName.equals(other.columnCName))
			return false;
		if (columnLength == null) {
			if (other.columnLength != null)
				return false;
		} else if (!columnLength.equals(other.columnLength))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (columnid == null) {
			if (other.columnid != null)
				return false;
		} else if (!columnid.equals(other.columnid))
			return false;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (isChecked == null) {
			if (other.isChecked != null)
				return false;
		} else if (!isChecked.equals(other.isChecked))
			return false;
		if (isExport == null) {
			if (other.isExport != null)
				return false;
		} else if (!isExport.equals(other.isExport))
			return false;
		if (isExportOrder == null) {
			if (other.isExportOrder != null)
				return false;
		} else if (!isExportOrder.equals(other.isExportOrder))
			return false;
		if (isInsert == null) {
			if (other.isInsert != null)
				return false;
		} else if (!isInsert.equals(other.isInsert))
			return false;
		if (isInsertOrder == null) {
			if (other.isInsertOrder != null)
				return false;
		} else if (!isInsertOrder.equals(other.isInsertOrder))
			return false;
		if (isList == null) {
			if (other.isList != null)
				return false;
		} else if (!isList.equals(other.isList))
			return false;
		if (isListOrder == null) {
			if (other.isListOrder != null)
				return false;
		} else if (!isListOrder.equals(other.isListOrder))
			return false;
		if (isMust == null) {
			if (other.isMust != null)
				return false;
		} else if (!isMust.equals(other.isMust))
			return false;
		if (isQuery == null) {
			if (other.isQuery != null)
				return false;
		} else if (!isQuery.equals(other.isQuery))
			return false;
		if (isQueryOrder == null) {
			if (other.isQueryOrder != null)
				return false;
		} else if (!isQueryOrder.equals(other.isQueryOrder))
			return false;
		if (isUnique == null) {
			if (other.isUnique != null)
				return false;
		} else if (!isUnique.equals(other.isUnique))
			return false;
		if (isUpdate == null) {
			if (other.isUpdate != null)
				return false;
		} else if (!isUpdate.equals(other.isUpdate))
			return false;
		if (isUpdateOrder == null) {
			if (other.isUpdateOrder != null)
				return false;
		} else if (!isUpdateOrder.equals(other.isUpdateOrder))
			return false;
		if (likescope == null) {
			if (other.likescope != null)
				return false;
		} else if (!likescope.equals(other.likescope))
			return false;
		if (maintableid == null) {
			if (other.maintableid != null)
				return false;
		} else if (!maintableid.equals(other.maintableid))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		if (propertyType == null) {
			if (other.propertyType != null)
				return false;
		} else if (!propertyType.equals(other.propertyType))
			return false;
		if (tolerant == null) {
			if (other.tolerant != null)
				return false;
		} else if (!tolerant.equals(other.tolerant))
			return false;
		if (typeSql == null) {
			if (other.typeSql != null)
				return false;
		} else if (!typeSql.equals(other.typeSql))
			return false;
		if (upColumnName == null) {
			if (other.upColumnName != null)
				return false;
		} else if (!upColumnName.equals(other.upColumnName))
			return false;
		if (validDataType == null) {
			if (other.validDataType != null)
				return false;
		} else if (!validDataType.equals(other.validDataType))
			return false;
		return true;
	}
	
}

package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 字段类型实体，页面表单类型
 * @Class Name ColumnType
 * @Author peixf
 * @Create In 2008-4-1
 */
public class SystemMainTableColumnType extends BaseObject {
	private static final long serialVersionUID = -2779893386861133339L;
	
	private Long id;
	private String columnTypeName;
	private String columnTypeCnName;
	private String columnMetaTypeName;
	private String columnExtClassName;
	private Integer columnExtLeftCount;
	
	public String getColumnExtClassName() {
		return columnExtClassName;
	}

	public void setColumnExtClassName(String columnExtClassName) {
		this.columnExtClassName = columnExtClassName;
	}

	public Integer getColumnExtLeftCount() {
		return columnExtLeftCount;
	}

	public void setColumnExtLeftCount(Integer columnExtLeftCount) {
		this.columnExtLeftCount = columnExtLeftCount;
	}

	public String getColumnMetaTypeName() {
		return columnMetaTypeName;
	}

	public void setColumnMetaTypeName(String columnMetaTypeName) {
		this.columnMetaTypeName = columnMetaTypeName;
	}

	public SystemMainTableColumnType(){}
	
	public SystemMainTableColumnType(Long id){
		this.id = id;
	}
	
	public boolean isTextColumnType(){
		boolean res = false;
		if(columnTypeName.equalsIgnoreCase("text")){
			res = true;
		}else if(columnTypeName.equalsIgnoreCase("textArea")){
			res = true;
		}
		return res;
	}
	
	public boolean isMultiValueColumnType(){
		boolean res = false;
		if(columnTypeName.equalsIgnoreCase("multiSelect")){
			res = true;
		}
		if(columnTypeName.equalsIgnoreCase("file")){
			res = true;
		}
		return res;
	}
	
	public boolean isNumberColumnType(){
		boolean res = false;
		if(columnTypeName.equalsIgnoreCase("select")){
			res = true;
		}else if(columnTypeName.equalsIgnoreCase("radio")){
			res = true;
		}else if(columnTypeName.equalsIgnoreCase("checkbox")){
			res = true;
		}else if(columnTypeName.equalsIgnoreCase("yesNoSelect")){
			res = true;
		}else if(columnTypeName.equalsIgnoreCase("yesNoRadio")){
			res = true;
		}else if(columnTypeName.equalsIgnoreCase("hidden")){
			res = true;
		}
		return res;
	}
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((columnTypeCnName == null) ? 0 : columnTypeCnName.hashCode());
		result = PRIME * result + ((columnTypeName == null) ? 0 : columnTypeName.hashCode());
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
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
		final SystemMainTableColumnType other = (SystemMainTableColumnType) obj;
		if (columnTypeCnName == null) {
			if (other.columnTypeCnName != null)
				return false;
		} else if (!columnTypeCnName.equals(other.columnTypeCnName))
			return false;
		if (columnTypeName == null) {
			if (other.columnTypeName != null)
				return false;
		} else if (!columnTypeName.equals(other.columnTypeName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public String getColumnTypeCnName() {
		return columnTypeCnName;
	}
	public void setColumnTypeCnName(String columnTypeCnName) {
		this.columnTypeCnName = columnTypeCnName;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}



}

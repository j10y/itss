package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 字段属性类型实体,对java及数据库中的类型
 * @Class Name PropertyType
 * @Author peixf
 * @Create In 2008-7-16
 */
public class PropertyType extends BaseObject {
	private static final long serialVersionUID = -7484422948410184375L;
	
	private Long id;
	private String propertyTypeName;
	private String propertyTypeCnName;
	private String propertyTypeClass;
	private String hibernateTypeClass;
	private String sqlColumnType;
	
	public String getSqlColumnType() {
		return sqlColumnType;
	}
	public void setSqlColumnType(String sqlColumnType) {
		this.sqlColumnType = sqlColumnType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPropertyTypeClass() {
		return propertyTypeClass;
	}
	public void setPropertyTypeClass(String propertyTypeClass) {
		this.propertyTypeClass = propertyTypeClass;
	}
	public String getPropertyTypeCnName() {
		return propertyTypeCnName;
	}
	public void setPropertyTypeCnName(String propertyTypeCnName) {
		this.propertyTypeCnName = propertyTypeCnName;
	}
	public String getPropertyTypeName() {
		return propertyTypeName;
	}
	public void setPropertyTypeName(String propertyTypeName) {
		this.propertyTypeName = propertyTypeName;
	}
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((propertyTypeName == null) ? 0 : propertyTypeName.hashCode());
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
		final PropertyType other = (PropertyType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (propertyTypeName == null) {
			if (other.propertyTypeName != null)
				return false;
		} else if (!propertyTypeName.equals(other.propertyTypeName))
			return false;
		return true;
	}
	public String getHibernateTypeClass() {
		return hibernateTypeClass;
	}
	public void setHibernateTypeClass(String hibernateTypeClass) {
		this.hibernateTypeClass = hibernateTypeClass;
	}

}

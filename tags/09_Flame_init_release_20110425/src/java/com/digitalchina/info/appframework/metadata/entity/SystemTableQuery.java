package com.digitalchina.info.appframework.metadata.entity;

import java.util.HashSet;
import java.util.Set;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 系统表查询
 * @Class Name UserTableQuery
 * @Author peixf
 * @Create In 2008-4-16
 */
public class SystemTableQuery extends BaseObject{
	private static final long serialVersionUID = -7924162269704706760L;
	
	public static final Integer QUERY_TYPE_SINGLE = new Integer(1);
	public static final Integer QUERY_TYPE_MUTL = new Integer(2);
	
	private Long id;

	private String queryName; //查询名称关键字，程序使用
	private String queryCnName; //查询名称
	
	private Integer queryType; //查询类型：单表查询1，或复合查询2
	
	private SystemMainTable systemMainTable; //查询的主表，如果是复合查询，以此主表展开关联查询
	
	private Set queryColumns = new HashSet();

	public String getQueryCnName() {
		return queryCnName;
	}

	public void setQueryCnName(String queryCnName) {
		this.queryCnName = queryCnName;
	}

	public Set getQueryColumns() {
		return queryColumns;
	}

	public void setQueryColumns(Set queryColumns) {
		this.queryColumns = queryColumns;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}

	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((id == null) ? 0 : id.hashCode());
		result = PRIME * result + ((queryName == null) ? 0 : queryName.hashCode());
		result = PRIME * result + ((queryType == null) ? 0 : queryType.hashCode());
		result = PRIME * result + ((systemMainTable == null) ? 0 : systemMainTable.hashCode());
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
		final SystemTableQuery other = (SystemTableQuery) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (queryName == null) {
			if (other.queryName != null)
				return false;
		} else if (!queryName.equals(other.queryName))
			return false;
		if (queryType == null) {
			if (other.queryType != null)
				return false;
		} else if (!queryType.equals(other.queryType))
			return false;
		if (systemMainTable == null) {
			if (other.systemMainTable != null)
				return false;
		} else if (!systemMainTable.equals(other.systemMainTable))
			return false;
		return true;
	}


}

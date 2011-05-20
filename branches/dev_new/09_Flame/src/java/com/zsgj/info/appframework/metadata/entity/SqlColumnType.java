package com.zsgj.info.appframework.metadata.entity;

public class SqlColumnType {
	private Long id;
	private String sqlColumnTypeName;
	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Return the String sqlColumnTypeName
	 */
	public String getSqlColumnTypeName() {
		return sqlColumnTypeName;
	}
	/**
	 * @Param String sqlColumnTypeName to set
	 */
	public void setSqlColumnTypeName(String sqlColumnTypeName) {
		this.sqlColumnTypeName = sqlColumnTypeName;
	}
	/**
	 * @Return the String sqlColumnTypeCnName
	 */
	public String getSqlColumnTypeCnName() {
		return sqlColumnTypeCnName;
	}
	/**
	 * @Param String sqlColumnTypeCnName to set
	 */
	public void setSqlColumnTypeCnName(String sqlColumnTypeCnName) {
		this.sqlColumnTypeCnName = sqlColumnTypeCnName;
	}
	private String sqlColumnTypeCnName;
}

package com.zsgj.info.appframework.metadata.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Department;

/**
 * 系统主表编号生成器
 * @Class Name SystemMainTable
 * @Author peixf
 * @Create In 2008-3-17
 */
public class SystemMainTableIdBuilder extends BaseObject {
	private static final long serialVersionUID = 4837932019873585503L;

	private Long id;
	private SystemMainTable systemMainTable;
	private String tableName;
	private Department department;
	private String prefix;	//前缀
	private Long length;
	private String ruleFileName;	//规则文件名
	private Integer deployFlag; //是否启用
	private Date beginDate; //起始有效日期
	private Date endDate; //终止有效日期
	
	private String latestValue; //最新的编号值
	
	public Integer getDeployFlag() {
		return deployFlag;
	}
	public void setDeployFlag(Integer deployFlag) {
		this.deployFlag = deployFlag;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public String getRuleFileName() {
		return ruleFileName;
	}
	public void setRuleFileName(String ruleFileName) {
		this.ruleFileName = ruleFileName;
	}
	public String getLatestValue() {
		return latestValue;
	}
	public void setLatestValue(String latestValue) {
		this.latestValue = latestValue;
	}

	
}

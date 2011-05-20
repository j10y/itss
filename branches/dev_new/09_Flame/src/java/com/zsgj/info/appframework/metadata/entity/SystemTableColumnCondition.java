package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 系统字段默认查询条件
 * @Class Name SystemTableColumnQueryCondition
 * @Author sa
 * @Create In 2009-2-12
 */
public class SystemTableColumnCondition extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -6430578056848204622L;
	public static Integer LOGIC_TYPE_AND = 1;
	public static Integer LOGIC_TYPE_OR = 0;
	public static Integer LOGIC_TYPE_NOT = -1;
	
	private Long id;
	private SystemMainTable systemMainTable;
	private SystemMainTableColumn mainTableColumn;
	private Operator operator; //运算法=><like
	private String value; //条件值，可能是文本,数字或者对象的id
	private Integer logicType; //与或非
	
	private Integer tableDefaultFlag;
	
	
	public Integer getTableDefaultFlag() {
		return tableDefaultFlag;
	}
	public void setTableDefaultFlag(Integer tableDefaultFlag) {
		this.tableDefaultFlag = tableDefaultFlag;
	}
	//条件值类型
//	public SystemMainTableExtColumn getExtendTableColumn() {
//		return extendTableColumn;
//	}
//	public void setExtendTableColumn(SystemMainTableExtColumn extendTableColumn) {
//		this.extendTableColumn = extendTableColumn;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLogicType() {
		return logicType;
	}
	public void setLogicType(Integer logicType) {
		this.logicType = logicType;
	}
	public SystemMainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}
	public void setMainTableColumn(SystemMainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}

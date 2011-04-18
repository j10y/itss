package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.ArrayList;
import java.util.List;
public class Component {
	private String id; //增加组件id
	private String name;
	private boolean isDisplay;
	private boolean isReadOnly;
	private boolean isMustInput;
	private String vType;
	private String width;
	private String height;
	private String label;
	private Object value;
	private String link;
	private String allowBlank = "true";
	private String validator;
	//这3个字段是新增加的,relationship是关系域的完全类名
	private String displayFiled;
	private String valueFiled;
	private String relationship; 
	private String tableName;
	//区分字段
	private boolean isAbstract;
	private String disccId; //区分字段id,组件id是否追加model的id
	private String fdiscTable;
	//附件上传时间戳
	private String nowtime; //时间戳
	private String columnId; //附件字段id
	//复选框新增
	private Integer columns; //字段组件列布局
	//组件本身包含的组件
	private List<Component> items = new ArrayList<Component>();
	//布局参数
	private int colspan;
	private int rowspan;
	//独占一行标识
	private boolean isSingleLine;
	//fieldset数据
	private String fieldsetItem;
	

	public String getFieldsetItem() {
		return fieldsetItem;
	}

	public void setFieldsetItem(String fieldsetItem) {
		this.fieldsetItem = fieldsetItem;
	}
	public Component(String name,String label,String width) {	
		this.name = name;
		this.label = label;
		this.width = width;
	}
	public Component() {		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public boolean isMustInput() {
		return isMustInput;
	}

	public void setMustInput(boolean isMustInput) {
		this.isMustInput = isMustInput;
	}

	public String getVType() {
		return vType;
	}

	public void setVType(String type) {
		vType = type;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public String getDisplayFiled() {
		return displayFiled;
	}

	public void setDisplayFiled(String displayFiled) {
		this.displayFiled = displayFiled;
	}

	public String getValueFiled() {
		return valueFiled;
	}

	public void setValueFiled(String valueFiled) {
		this.valueFiled = valueFiled;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getDisccId() {
		return disccId;
	}

	public void setDisccId(String disccId) {
		this.disccId = disccId;
	}

	public String getFdiscTable() {
		return fdiscTable;
	}

	public void setFdiscTable(String fdiscTable) {
		this.fdiscTable = fdiscTable;
	}
	public String getNowtime() {
		return nowtime;
	}
	public void setNowtime(String nowtime) {
		this.nowtime = nowtime;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public List<Component> getItems() {
		return items;
	}

	public void setItems(List<Component> items) {
		this.items = items;
	}

	public boolean isSingleLine() {
		return isSingleLine;
	}

	public void setSingleLine(boolean isSingleLine) {
		this.isSingleLine = isSingleLine;
	}
	/**
	 * @Return the int colspan
	 */
	public int getColspan() {
		return colspan;
	}
	/**
	 * @Param int colspan to set
	 */
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	/**
	 * @Return the int rowspan
	 */
	public int getRowspan() {
		return rowspan;
	}
	/**
	 * @Param int rowspan to set
	 */
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
}

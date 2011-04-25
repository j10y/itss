package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.ArrayList;
import java.util.List;

public class PageParameter {
	//以下是model的属性
	private String modelName;
	private String modelTitle;
	private String modelTableName;
	private String pagePath;
	private String filePath;
	private String pagePathType;
	//以下是panel的属性
	private List<PageParameter> panels = new ArrayList<PageParameter>();
	private String panelname;
	private String panelTitle;
	private String panelTableName;
	private String readonlyFlag;
	private String clazz;
	private String xtype; 
	private String groupFlag;
	private String queryFlag;
	private String divFloat; 
	private String fcolumnPropName;
	private String pcolumnPropName;
	private String order;
	private List<PageParameter> childPagePanels=new ArrayList<PageParameter>();
	//新增的完全展开需要的属性
	private String item;//存储form组建用的
	//add by lee for debug comboBox in 20090703 begin
	private String itemLoad;//form中分页comboBox的重新渲染
	//add by lee for debug comboBox in 20090703 end
	private String formMapping;//存储数据映射
	private String columnItem;//列模型
	private String fields;//存储数据的列模型
	private String button;//按钮
	
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getFormMapping() {
		return formMapping;
	}
	public void setFormMapping(String formMapping) {
		this.formMapping = formMapping;
	}
	public String getColumnItem() {
		return columnItem;
	}
	public void setColumnItem(String columnItem) {
		this.columnItem = columnItem;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public String getFcolumnPropName() {
		return fcolumnPropName;
	}
	public void setFcolumnPropName(String fcolumnPropName) {
		this.fcolumnPropName = fcolumnPropName;
	}
	public String getPcolumnPropName() {
		return pcolumnPropName;
	}
	public void setPcolumnPropName(String pcolumnPropName) {
		this.pcolumnPropName = pcolumnPropName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List getPanels() {
		return panels;
	}
	public void setPanels(List panels) {
		this.panels = panels;
	}
	public List getChildPagePanels() {
		return childPagePanels;
	}
	public void setChildPagePanels(List childPagePanels) {
		this.childPagePanels = childPagePanels;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelTitle() {
		return modelTitle;
	}
	public void setModelTitle(String modelTitle) {
		this.modelTitle = modelTitle;
	}
	public String getModelTableName() {
		return modelTableName;
	}
	public void setModelTableName(String modelTableName) {
		this.modelTableName = modelTableName;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public String getPanelname() {
		return panelname;
	}
	public void setPanelname(String panelname) {
		this.panelname = panelname;
	}
	public String getPanelTitle() {
		return panelTitle;
	}
	public void setPanelTitle(String panelTitle) {
		this.panelTitle = panelTitle;
	}
	public String getPanelTableName() {
		return panelTableName;
	}
	public void setPanelTableName(String panelTableName) {
		this.panelTableName = panelTableName;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public String getGroupFlag() {
		return groupFlag;
	}
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public String getDivFloat() {
		return divFloat;
	}
	public void setDivFloat(String divFloat) {
		this.divFloat = divFloat;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getPagePathType() {
		return pagePathType;
	}
	public void setPagePathType(String pagePathType) {
		this.pagePathType = pagePathType;
	}
	public String getReadonlyFlag() {
		return readonlyFlag;
	}
	public void setReadonlyFlag(String readonlyFlag) {
		this.readonlyFlag = readonlyFlag;
	}
	public String getItemLoad() {
		return itemLoad;
	}
	public void setItemLoad(String itemLoad) {
		this.itemLoad = itemLoad;
	}
	
	
}

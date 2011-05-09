package com.zsgj.info.appframework.pagemodel.servlet;

import java.util.ArrayList;
import java.util.List;

public class PageParameter {
	//������model������
	private String modelName;
	private String modelTitle;
	private String modelTableName;
	private String pagePath;
	private String filePath;
	private String pagePathType;
	//������panel������
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
	//��������ȫչ����Ҫ������
	private String item;//�洢form�齨�õ�
	//add by lee for debug comboBox in 20090703 begin
	private String itemLoad;//form�з�ҳcomboBox��������Ⱦ
	//add by lee for debug comboBox in 20090703 end
	private String formMapping;//�洢����ӳ��
	private String columnItem;//��ģ��
	private String fields;//�洢���ݵ���ģ��
	private String button;//��ť
	
	/**
	 * @Return the String item
	 */
	public String getItem() {
		return item;
	}
	/**
	 * @Param String item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * @Return the String formMapping
	 */
	public String getFormMapping() {
		return formMapping;
	}
	/**
	 * @Param String formMapping to set
	 */
	public void setFormMapping(String formMapping) {
		this.formMapping = formMapping;
	}
	/**
	 * @Return the String columnItem
	 */
	public String getColumnItem() {
		return columnItem;
	}
	/**
	 * @Param String columnItem to set
	 */
	public void setColumnItem(String columnItem) {
		this.columnItem = columnItem;
	}
	/**
	 * @Return the String fields
	 */
	public String getFields() {
		return fields;
	}
	/**
	 * @Param String fields to set
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}
	/**
	 * @Return the String button
	 */
	public String getButton() {
		return button;
	}
	/**
	 * @Param String button to set
	 */
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

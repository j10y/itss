package com.digitalchina.info.appframework.pagemodel.entity;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.MatchMode;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelFieldSetService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * PageModel的字段, 默认在pageModel创建时，根据主表，自动取系统可见字段的所有字段
 * @Class Name PagePanelColumn
 * @Author sa
 * @Create In 2008-11-13
 */
public class PagePanelColumn extends BaseObject{
	public static Integer FIELD_SET_TRUE = 1;
	public static Integer FIELD_SET_FALSE = 0;
	private Long id;
	private PagePanel pagePanel;
	private SystemMainTable systemMainTable;
	//上级pagePanel字段
	private PagePanelColumn parentPagePanelColumn;
	//分组字段
	private SystemMainTableColumn mainTableColumn;
	//private SystemMainTableExtColumn extendTableColumn;
	//是否显示
	private Integer isDisplay;
	//排序
	private Integer order;
	//长度
	private String length;
	//是否必显
	private Integer isMustInput;
	//查询模式
	private MatchMode matchMode;
	//field标识
	private Integer fieldSetFlag;
	
	public MatchMode getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}

	public Column getColumn(){
		if(this.getMainTableColumn()!=null){
			return this.getMainTableColumn();
		}
//		else if(this.getExtendTableColumn()!=null){
//			return this.getExtendTableColumn();
//		}
		else {
			return null;
		}
	}
	
	public String getColumnCnName(){
		
		Column column = this.getColumn();
		if(column!=null){
			return column.getColumnCnName();
		}else{
			PagePanelFieldSetService ppfss = (PagePanelFieldSetService) ContextHolder.getBean("pagePanelFieldSetService");
			PagePanelFieldSet ppfs = ppfss.findPagePanelFieldSet(this);
			return ppfs.getTitle();
		}
	}
	
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
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	public Integer getIsMustInput() {
		if(this.getMainTableColumn()!=null){
			return this.getMainTableColumn().getIsMustInput();
		}
//		else if(this.getExtendTableColumn()!=null){
//			return this.getExtendTableColumn().getIsMustInput();
//		}
		else{
			return 1;
		}
	}
	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public SystemMainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}
	public void setMainTableColumn(SystemMainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}
	public PagePanelColumn getParentPagePanelColumn() {
		return parentPagePanelColumn;
	}
	public void setParentPagePanelColumn(PagePanelColumn parentPagePanelColumn) {
		this.parentPagePanelColumn = parentPagePanelColumn;
	}

	public Integer getFieldSetFlag() {
		return fieldSetFlag;
	}

	public void setFieldSetFlag(Integer fieldSetFlag) {
		this.fieldSetFlag = fieldSetFlag;
	}
}

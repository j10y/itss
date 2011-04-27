package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * ҳ��ģ������м��
 * @Class Name PageModelPanelMiddleTable
 * @Author Administrator
 * @Create In Mar 10, 2009
 */
public class PageModelPanelMiddleTable extends BaseObject {
	private Long id;
	private PageModel pageModel; 			//configItemModel

	private SystemMainTable middleTable; //��������������
	private SystemMainTableColumn subColumn; //�������������ID�ֶ� 
	private SystemMainTableColumn parentColumn;	//�����ֶ�
	
	
	private PagePanel subPagePanel;		//���������??
	private SystemMainTable subPanelTable; //�������ӱ�??
		
	private PagePanel parentPagePanel;		//�������
	private SystemMainTable parentPanelTable; //�����������
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	public SystemMainTable getMiddleTable() {
		return middleTable;
	}
	public void setMiddleTable(SystemMainTable middleTable) {
		this.middleTable = middleTable;
	}
	public SystemMainTableColumn getSubColumn() {
		return subColumn;
	}
	public void setSubColumn(SystemMainTableColumn subColumn) {
		this.subColumn = subColumn;
	}
	public PagePanel getSubPagePanel() {
		return subPagePanel;
	}
	public void setSubPagePanel(PagePanel subPagePanel) {
		this.subPagePanel = subPagePanel;
	}
	public SystemMainTable getSubPanelTable() {
		return subPanelTable;
	}
	public void setSubPanelTable(SystemMainTable subPanelTable) {
		this.subPanelTable = subPanelTable;
	}
	public SystemMainTableColumn getParentColumn() {
		return parentColumn;
	}
	public void setParentColumn(SystemMainTableColumn parentColumn) {
		this.parentColumn = parentColumn;
	}
	public PagePanel getParentPagePanel() {
		return parentPagePanel;
	}
	public void setParentPagePanel(PagePanel parentPagePanel) {
		this.parentPagePanel = parentPagePanel;
	}
	public SystemMainTable getParentPanelTable() {
		return parentPanelTable;
	}
	public void setParentPanelTable(SystemMainTable parentPanelTable) {
		this.parentPanelTable = parentPanelTable;
	}
	
}

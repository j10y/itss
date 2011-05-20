package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.framework.dao.BaseObject;

/**
 * ������������еĶ��Panel֮��Ĺ�ϵ
 * @Class Name PageModelPanelTable
 * @Author sa
 * @Create In 2008-12-5
 */
public class PageGroupPanelTable extends BaseObject {
	private Long id;
	private PagePanel pagePanel; 			//������壬���ڲ��ͻ��������
	
	private PagePanel subPagePanel;			//�ڲ��ͻ���ϵ�����
	private SystemMainTable subPanelTable; //�ڲ��ͻ���ϵ
	private SystemMainTableColumn subPanelTableFColumn; //�����ڲ��ͻ�
	
	private PagePanel parentPagePanel;		//�ڲ��ͻ����
	private SystemMainTable parentPanelTable; //�ڲ��ͻ�
	private SystemMainTableColumn parentPanelTablePColumn;//�ڲ��ͻ���id�ֶ�
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public PageGroupPanelTable() {
		super();
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
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
	public SystemMainTableColumn getParentPanelTablePColumn() {
		return parentPanelTablePColumn;
	}
	public void setParentPanelTablePColumn(
			SystemMainTableColumn parentPanelTablePColumn) {
		this.parentPanelTablePColumn = parentPanelTablePColumn;
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
	public SystemMainTableColumn getSubPanelTableFColumn() {
		return subPanelTableFColumn;
	}
	public void setSubPanelTableFColumn(SystemMainTableColumn subPanelTableFColumn) {
		this.subPanelTableFColumn = subPanelTableFColumn;
	}
	
	
}

package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 面板字段分组框
 * @Class Name PagePanelFeildSet
 * @Author Administrator
 * @Create In Apr 17, 2009
 */
public class PagePanelFieldSet extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -5985604121142610774L;
	private Long id;
	private PagePanel pagePanel;
	private PagePanelColumn pagePanelColumn;
	private String title;
	private String width;
	private String heigth;
	private String orderFlag;//不要了
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;                                
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeigth() {
		return heigth;
	}
	public void setHeigth(String heigth) {
		this.heigth = heigth;
	}
	public String getOrderFlag() {
		return orderFlag;
	}
	public void setOrderFlag(String orderFlag) {
		this.orderFlag = orderFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PagePanelColumn getPagePanelColumn() {
		return pagePanelColumn;
	}
	public void setPagePanelColumn(PagePanelColumn pagePanelColumn) {
		this.pagePanelColumn = pagePanelColumn;
	}
	
}

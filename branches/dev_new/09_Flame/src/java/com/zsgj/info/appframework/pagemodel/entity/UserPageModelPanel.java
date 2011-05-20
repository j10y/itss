package com.zsgj.info.appframework.pagemodel.entity;

import java.util.ArrayList;
import java.util.List;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

public class UserPageModelPanel extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -4180182873081388162L;
	private Long id;
	private UserInfo userInfo;
	private PagePanel pagePanel;
	private PagePanel parentPagePanel;
	//no mapping
	private List<PageModelPanel> childPagePanels = new ArrayList<PageModelPanel>();
	//是否显示
	private Integer isDisplay;
	//排序
	private Integer order;
	//是否必须显示
	private Integer isMustInput;
	//对齐方式
	private String divFloat;
	//布局方式
	private String layout;
	//高度
	private Integer height;
	//宽度
	private Integer width;
	public List<PageModelPanel> getChildPagePanels() {
		return childPagePanels;
	}
	public void setChildPagePanels(List<PageModelPanel> childPagePanels) {
		this.childPagePanels = childPagePanels;
	}
	public String getDivFloat() {
		return divFloat;
	}
	public void setDivFloat(String divFloat) {
		this.divFloat = divFloat;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
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
		return isMustInput;
	}
	public void setIsMustInput(Integer isMustInput) {
		this.isMustInput = isMustInput;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
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
	public PagePanel getParentPagePanel() {
		return parentPagePanel;
	}
	public void setParentPagePanel(PagePanel parentPagePanel) {
		this.parentPagePanel = parentPagePanel;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
}

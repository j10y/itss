package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 页面面板组件按钮
 * @Class Name PagePanelBtn
 * @Author sa
 * @Create In 2008-11-30
 */
public class PagePanelBtn extends BaseObject {
	private Long id;
	private PagePanel pagePanel;
	private PageBtnType btnType;
	private String btnName;
	private String method;
	private String link;
	private PageModel nextPageModel;
	
	private Integer openWinFlag; //新开window标志
	private String imageUrl; //图片路径，如果是图片按钮
	private Integer order;
//	是否显示
	private Integer isDisplay;
	private Integer valign;
	private Integer align;

	
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public PageBtnType getBtnType() {
		return btnType;
	}
	public void setBtnType(PageBtnType btnType) {
		this.btnType = btnType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public PageModel getNextPageModel() {
		return nextPageModel;
	}
	public void setNextPageModel(PageModel nextPageModel) {
		this.nextPageModel = nextPageModel;
	}
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Integer getAlign() {
		return align;
	}
	public void setAlign(Integer align) {
		this.align = align;
	}
	public Integer getValign() {
		return valign;
	}
	public void setValign(Integer valign) {
		this.valign = valign;
	}
	public Integer getOpenWinFlag() {
		return openWinFlag;
	}
	public void setOpenWinFlag(Integer openWinFlag) {
		this.openWinFlag = openWinFlag;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
}

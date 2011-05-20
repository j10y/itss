package com.zsgj.info.appframework.pagemodel.entity;

public class PagePanelParamIn {
	private Long id;
	private PageModel pageModel;
	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Return the PageModel pageModel
	 */
	public PageModel getPageModel() {
		return pageModel;
	}
	/**
	 * @Param PageModel pageModel to set
	 */
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	/**
	 * @Return the PagePanel pagePanel
	 */
	public PagePanel getPagePanel() {
		return pagePanel;
	}
	/**
	 * @Param PagePanel pagePanel to set
	 */
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	/**
	 * @Return the String paramName
	 */
	public String getParamName() {
		return paramName;
	}
	/**
	 * @Param String paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	/**
	 * @Return the String paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}
	/**
	 * @Param String paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	private PagePanel pagePanel;
	private String paramName;
	private String paramValue;
}

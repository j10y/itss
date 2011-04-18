package com.digitalchina.info.appframework.pagemodel.entity;

import com.digitalchina.info.framework.dao.BaseObject;

public class PageModelParamIn extends BaseObject{
	private Long id;
	private PageModel pageModel;
	private String paramName;
	private String paramValue;
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
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}

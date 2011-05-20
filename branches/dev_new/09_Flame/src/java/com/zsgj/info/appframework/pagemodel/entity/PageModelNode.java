package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class PageModelNode extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2430248427817786200L;
	private Long id;
	private String nodeName;
	private PageModel pageModel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public PageModel getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}
	
}

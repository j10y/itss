package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.framework.dao.BaseObject;

public class PageModelQuery extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -8832749811068455046L;
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
}

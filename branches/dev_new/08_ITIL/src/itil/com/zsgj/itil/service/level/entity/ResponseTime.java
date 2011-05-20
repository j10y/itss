package com.zsgj.itil.service.level.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 对事件的响应级别
 * @Class Name EventResponse
 * @Author sa
 * @Create In 2008-11-11
 */
public class ResponseTime extends BaseObject {
	private Long id;
	private String name;
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
	 * @Return the String name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @Param String name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}

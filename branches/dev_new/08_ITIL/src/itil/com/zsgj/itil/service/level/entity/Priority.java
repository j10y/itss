package com.zsgj.itil.service.level.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 事件的级别分类（Priority）标准
 * @Class Name Priority
 * @Author sa
 * @Create In 2008-11-11
 */
public class Priority extends BaseObject {
	private Long id;
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
	private String name;
}

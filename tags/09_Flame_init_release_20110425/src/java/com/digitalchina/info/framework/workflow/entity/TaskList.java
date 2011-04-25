package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * TaskList实体是显示某个用户下的所有任务
 * @Class Name TaskList
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class TaskList extends BaseObject{
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

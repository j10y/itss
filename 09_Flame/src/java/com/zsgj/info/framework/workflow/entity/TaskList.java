package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * TaskListʵ������ʾĳ���û��µ���������
 * @Class Name TaskList
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class TaskList extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -432855346168935129L;
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

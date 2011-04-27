package com.zsgj.info.framework.workflow.info;
/**
 * 对应实体的信息精简版
 * @Class Name BaseInfo
 * @Author yang
 * @Create In 2008-4-17
 */
public class BaseInfo {
	String name;
	long id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

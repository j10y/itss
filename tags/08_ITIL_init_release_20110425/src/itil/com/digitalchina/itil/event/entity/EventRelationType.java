package com.digitalchina.itil.event.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 事件关联类型
 * @Class Name EventRelationType
 * @Author sa
 * @Create In 2009-3-9
 */
public class EventRelationType extends BaseObject {
	public static String SAME = "A";
	public static String PARENT = "B";
	public static String CHILD = "C";
	public static String RELATION = "D";
	private Long id;
	private String name;
	private String typeFlag;
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
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

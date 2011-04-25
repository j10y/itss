package com.digitalchina.itil.event.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 问题关联类型
 * @Class Name ProblemRelationType
 * @Author daijf
 * @Create In 2009-4-9
 */
public class ProblemRelationType extends BaseObject {
	public static String SAME = "A";
	public static String PARENT = "B";
	public static String CHILD = "C";
	public static String RELATION = "D";
	
	private Long id;
	private String name;
	
	private String typeFlag;
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
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	
}

package com.zsgj.itil.config.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * �������ϵ�������̶ܳ�ʵ��
 * @Class Name CIRelationShipGrade
 * @Author sa
 * @Create In 2009-1-13
 */
public class CIRelationShipGrade extends BaseObject{
	private Long id;
	private String name;
	
	public String getName() {
		return name;
	} 
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}

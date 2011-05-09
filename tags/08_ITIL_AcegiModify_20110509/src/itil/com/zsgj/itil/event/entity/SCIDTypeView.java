package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * SCID¿‡–Õ ”Õº
 * @Class Name SCIDTypeView
 * @Author sa
 * @Create In 2009-3-12
 */
public class SCIDTypeView extends BaseObject {
	private Long id;
	private String name;
	private Integer typeFlag;
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
	public Integer getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}
	
}

package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * Handler���ʵ���ǰ�ĳ�ֹ��ܾ���Ĺҵ��ڵ��handler����
 * @Class Name Handler
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class Handler extends BaseObject{
	private Long id;
	private String name;
	private String clazz;
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
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	

}

package com.zsgj.itil.knowledge.entity;


import com.zsgj.info.framework.dao.BaseObject;

/**
 * �ļ�����
 * @Class Name KnowFileType
 * @Author huzh
 * @Create In May 31, 2010
 */
@SuppressWarnings("serial")
public class KnowFileType extends BaseObject {
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

package com.digitalchina.itil.knowledge.entity;


import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 文件类型
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

package com.digitalchina.info.framework.security.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ʡ��
 * @Class Name Province
 * @Author sa
 * @Create In May 24, 2009
 */
public class Province extends BaseObject {
	private Long id;
	private String name;
	private String keyword;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

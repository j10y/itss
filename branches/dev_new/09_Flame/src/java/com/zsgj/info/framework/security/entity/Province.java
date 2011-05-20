package com.zsgj.info.framework.security.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * Ê¡·Ý
 * @Class Name Province
 * @Author sa
 * @Create In May 24, 2009
 */
public class Province extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -1497302528287854135L;
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

package com.digitalchina.itil.finance.entity;

import com.digitalchina.info.framework.dao.BaseObject;
/**
 * 批处理信息分类
 * @Class Name BatchType
 * @Author lee
 * @Create In Apr 9, 2009
 */
public class BatchType extends BaseObject{
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

package com.digitalchina.itil.config.extci.entity;

import com.digitalchina.info.framework.dao.BaseObject;


/**
 * 服务商类型
 * @Class Name ServiceProviderType
 * @Author duxh
 * @Create In Jul 2, 2010
 */
public class ServiceProviderType extends BaseObject{
	
	private static final long serialVersionUID = -1867014909230679344L;
	
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

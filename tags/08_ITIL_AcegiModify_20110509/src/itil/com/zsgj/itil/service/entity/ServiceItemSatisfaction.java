package com.zsgj.itil.service.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**服务项满意度
 * @Class Name ServiceItemSatisfaction
 * @Author sa
 * @Create In 2009-2-27
 */
public class ServiceItemSatisfaction extends BaseObject {
	private Long id;
	private String name;
	private Integer level;

	public String getUniquePropName() {
		return "name";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

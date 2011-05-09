package com.zsgj.itil.event.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 事件使用的SCID数据表，后台映射到VIEW，联合CID和SCID两表的数据
 * @Class Name SCIDData
 * @Author sa
 * @Create In 2009-3-5
 */
public class SCIDData extends BaseObject {
	private Long id;
	private String name;
	private Integer flag;
	private Integer type;
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}

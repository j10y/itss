package com.zsgj.itil.knowledge.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.itil.event.entity.SCIDData;
import com.zsgj.itil.event.entity.SCIDType;

/**
 * 事件所有问题关联配置项都合并到知识表里。而且是处理完毕问题的
 * 配置项拿过来
 * @Class Name KnowledgeConfigItem
 * @Author sa
 * @Create In 2009-3-5
 */
public class KnowledgeConfigItem extends BaseObject {
	private Long id;
	private Knowledge knowledge;//知识
	private Integer type;//配置类或服务类
	private Long scitype;//配置项类型
	private Long sciddata;//配置项数据
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Knowledge getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getScitype() {
		return scitype;
	}
	public void setScitype(Long scitype) {
		this.scitype = scitype;
	}
	public Long getSciddata() {
		return sciddata;
	}
	public void setSciddata(Long sciddata) {
		this.sciddata = sciddata;
	}

	
	
}

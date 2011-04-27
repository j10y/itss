package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * DefinitionType标示流程定义的类型（具体按哪种类型分类还没有确定）
 * @Class Name DefinitionType
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class DefinitionType extends BaseObject{
	private Long id;
	private DefinitionType parenteType;
	private String name;
	private Integer deleteflag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DefinitionType getParenteType() {
		return parenteType;
	}
	public void setParenteType(DefinitionType parenteType) {
		this.parenteType = parenteType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}

}

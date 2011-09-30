package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaDatadictionary extends BaseModel {
	protected long id;
	protected String name;
	protected Long parentId;
	
	public final static Long BUSSINESS_GOAL = 5l;//业务目标
	public final static Long PEOPLE_MANAGEMENT_GOAL = 6l;//员工管理目标
	public final static Long INDIVIDUAL_DEVELOPMENT_GOAL = 14l;//个人发展目标
	
	public HrPaDatadictionary(){}
	
	public HrPaDatadictionary(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}

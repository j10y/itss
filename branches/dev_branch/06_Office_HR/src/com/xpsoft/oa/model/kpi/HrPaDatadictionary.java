package com.xpsoft.oa.model.kpi;

import com.xpsoft.core.model.BaseModel;

public class HrPaDatadictionary extends BaseModel {
	protected long id;
	protected String name;
	protected Long parentId;
	
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

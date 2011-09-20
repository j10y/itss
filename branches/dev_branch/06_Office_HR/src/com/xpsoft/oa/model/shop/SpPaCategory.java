package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaCategory extends BaseModel {
	protected long id;
	protected String name;
	protected long parentId;
	
	public SpPaCategory(){}

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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}

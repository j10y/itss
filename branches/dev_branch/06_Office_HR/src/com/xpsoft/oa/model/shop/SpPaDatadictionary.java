package com.xpsoft.oa.model.shop;

import com.xpsoft.core.model.BaseModel;

public class SpPaDatadictionary extends BaseModel {
	
	public static final Integer BASE_DATADICTIONARY = 1;//数据字典基础字段
	public static final Integer PA_TYPE = 2;//考核指标类型
	public static final Integer PA_FREQUENCY = 3;//考核频度
	public static final Integer PA_MODE = 4;//考核方式
	public static final Integer PBC_TYPE = 15;//PBC类型
	public static final Integer PA_QUALITATIVE_ASSESSMENT = 12;//定性考核
	public static final Integer PA_QUANTITATIVE_ASSESSMENT = 13;//定量考核
	
	protected long id;
	protected String name;
	protected long parentId;
	
	public SpPaDatadictionary(){}
	
	public SpPaDatadictionary(long id) {
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

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}

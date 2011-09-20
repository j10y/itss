package com.xpsoft.oa.service.shop;

import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaAssessmentcriteria;

public interface SpPaAssessmentcriteriaService extends BaseService<SpPaAssessmentcriteria>{
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public abstract Map<String, String> getKeyAndName();
	/*
	 * 判断关键字是否存在
	 * @param key
	 * 要判断的关键字
	 * */
	public boolean checkKey(String key);
}

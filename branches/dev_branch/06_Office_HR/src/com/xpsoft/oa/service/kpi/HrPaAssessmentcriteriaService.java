package com.xpsoft.oa.service.kpi;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;

public interface HrPaAssessmentcriteriaService extends BaseService<HrPaAssessmentcriteria>{
	/*
	 * 取得所有考核标准关键字和名称
	 * */
	public abstract Map<String, String> getKeyAndName(Long depId);
	/*
	 * 判断关键字是否存在
	 * @param key
	 * 要判断的关键字
	 * */
	public boolean checkKey(String key, long id);
	/*
	 * 批量导入考核标准
	 * */
	public boolean multiSave(List<HrPaAssessmentcriteria> list);
}

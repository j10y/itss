package com.xpsoft.oa.service.kpi;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;

public interface HrPaKpiPBC2UserService extends BaseService<HrPaKpiPBC2User> {
	/*
	 * 计算个人考核模板最终得分
	 * @param pbcId
	 * 个人考核模板ID
	 * */
	//public void calculateTotal(Long pbcId);
	/*
	 * 批量计算个人考核模板最终得分
	 * @param pbcIds
	 * 个人考核模板ID数组
	 * */
	public void multiCal(String pbcIds, Long depId);
	/*
	 * 获取所有定性考核指标未完成打分的记录
	 * @param depId
	 * 部门ID
	 * */
	public List<String> getAllUnfinished(Long depId);
}

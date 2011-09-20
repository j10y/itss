package com.xpsoft.oa.service.shop;

import java.util.List;
import java.util.Map;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2User;

public interface SpPaKpiPBC2UserService extends BaseService<SpPaKpiPBC2User> {
	/*
	 * 计算个人PBC最终得分
	 * @param pbcIds
	 * PBC ID
	 * */
	public String calTotalScore(Long pbcId);
	/*
	 * 获取所有定性考核指标未完成打分的记录
	 * @param depId
	 * 部门ID
	 * */
	public List<String> getAllUnfinished(Long depId);
}

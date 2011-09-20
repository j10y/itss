package com.xpsoft.oa.service.shop;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaKpiitem2user;

public interface SpPaKpiitem2userService extends BaseService<SpPaKpiitem2user> {
	/*
	 * 计算个人考核模板定性考核项的多人多次打分平均分数
	 * @param itemId
	 * 考核项ID
	 * */
	public void calculateAvg(Long itemId);
	/*
	 * 批量计算考核模板关联所有考核项平均分
	 * @param pbcId
	 * 考核模板ID
	 * */
	public void multiCal(Long pbcId);
	/*
	 * 批量保存
	 * @param list
	 * 要保存的数据集
	 * */
	public void multiSave(List<SpPaKpiitem2user> list);
}

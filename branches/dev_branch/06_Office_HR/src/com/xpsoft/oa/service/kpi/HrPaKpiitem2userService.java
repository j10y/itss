package com.xpsoft.oa.service.kpi;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;

public interface HrPaKpiitem2userService extends BaseService<HrPaKpiitem2user> {
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
}

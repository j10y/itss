package com.digitalchina.itil.finance.service;

import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.finance.entity.FinanceCostSchedules;

public interface UserCostInputService {
	/**
	 * 把页面参数放入到业务成本明细表中
	 * @param financeCostSchedules
	 * @param paramMap
	 */
	public void saveUserCostMesg(FinanceCostSchedules financeCostSchedules,Map paramMap,String costCenter);
	/**
	 * 得到所有的成本中心类型
	 * @return
	 */
	public Page findFinanceCostCenterBySpecialParam (int pageNo, int pageSize,String propertyValue);
	

}

package com.zsgj.itil.finance.service;

import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.finance.entity.FinanceCostSchedules;

public interface UserCostInputService {
	/**
	 * ��ҳ��������뵽ҵ��ɱ���ϸ����
	 * @param financeCostSchedules
	 * @param paramMap
	 */
	public void saveUserCostMesg(FinanceCostSchedules financeCostSchedules,Map paramMap,String costCenter);
	/**
	 * �õ����еĳɱ���������
	 * @return
	 */
	public Page findFinanceCostCenterBySpecialParam (int pageNo, int pageSize,String propertyValue);
	

}

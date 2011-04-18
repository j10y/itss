package com.digitalchina.itil.finance.service;

import java.util.Map;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.finance.entity.FinanceCostSchedules;

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

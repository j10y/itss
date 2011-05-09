package com.zsgj.itil.finance.dao;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.finance.entity.FinanceCostCenter;
import com.zsgj.itil.finance.entity.FinanceCostSchedules;
import com.zsgj.itil.finance.entity.FinanceCostType;

public interface UserCostInputDao {
	/**
	 * 用来保存财务明细表
	 * @param financeCostSchedules
	 */
	public void saveUserCostMesg(FinanceCostSchedules financeCostSchedules);
	/**
	 * 根据信息化管理部的名称查找其相应编号
	 * @return
	 */
	public String findInterPartmentCode();
	/**
	 * 从财务费用表中查找非ERP导入的费用类型
	 * @return
	 */
	public FinanceCostType findFinanceCostType();
	/**
	 * 得到所有的成本中心类型
	 * @return
	 */
	public Page findFinanceCostCenterBySpecialParam(int pageNo, int pageSize,String propertyValue);
	/**
	 * 根据成本中心编码找到成本中心
	 * @return
	 */
	public FinanceCostCenter findFinanceCostCenterByCode(String costCenterCode);

}

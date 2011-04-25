package com.digitalchina.itil.finance.dao;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.finance.entity.FinanceCostCenter;
import com.digitalchina.itil.finance.entity.FinanceCostSchedules;
import com.digitalchina.itil.finance.entity.FinanceCostType;

public interface UserCostInputDao {
	/**
	 * �������������ϸ��
	 * @param financeCostSchedules
	 */
	public void saveUserCostMesg(FinanceCostSchedules financeCostSchedules);
	/**
	 * ������Ϣ�����������Ʋ�������Ӧ���
	 * @return
	 */
	public String findInterPartmentCode();
	/**
	 * �Ӳ�����ñ��в��ҷ�ERP����ķ�������
	 * @return
	 */
	public FinanceCostType findFinanceCostType();
	/**
	 * �õ����еĳɱ���������
	 * @return
	 */
	public Page findFinanceCostCenterBySpecialParam(int pageNo, int pageSize,String propertyValue);
	/**
	 * ���ݳɱ����ı����ҵ��ɱ�����
	 * @return
	 */
	public FinanceCostCenter findFinanceCostCenterByCode(String costCenterCode);

}

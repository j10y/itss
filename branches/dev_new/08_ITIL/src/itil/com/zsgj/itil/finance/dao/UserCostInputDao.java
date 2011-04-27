package com.zsgj.itil.finance.dao;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.finance.entity.FinanceCostCenter;
import com.zsgj.itil.finance.entity.FinanceCostSchedules;
import com.zsgj.itil.finance.entity.FinanceCostType;

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

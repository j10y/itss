package com.zsgj.itil.require.dao;

import java.sql.Date;
import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.config.extlist.entity.SRExpendPlan;
import com.zsgj.itil.config.extlist.entity.SRIncomePlan;
import com.zsgj.itil.require.entity.RealIncome;
import com.zsgj.itil.require.entity.RealPayment;

/**
 * �������DAO
 * @Class Name BusinessAccountDao
 * @Author lee
 * @Create In Nov 25, 2009
 */
public interface BusinessAccountDao {

	/**
	 * ��ȡδ����ո���ƻ�����������
	 * @Methods Name findUnFinshedRequire
	 * @Create In Sep 9, 2009 By lee
	 * @param name
	 * @param num
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findUnFinshedRequire(String name, String num, int pageNo,int pageSize);
	/**
	 * ��ȡ������ո���ƻ�����������
	 * @Methods Name findFinshedRequire
	 * @Create In Sep 10, 2009 By lee
	 * @param name
	 * @param num
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findFinshedRequire(String name, String num, int pageNo,int pageSize);
	/**
	 * �����տ�ƻ�ID��ȡ�տ�ƻ�δ�տ���
	 * @Methods Name getIncomeBalanceByPlanId
	 * @Create In Sep 9, 2009 By lee
	 * @param palnId
	 * @return Double
	 */
	Double getIncomeBalanceByPlanId(String palnId);
	/**
	 * ���ݽ�������ID���տ�ƻ�ID��ȡ�տ�ƻ���ǰδ�տ���
	 * @Methods Name getCurIncomeBalance
	 * @Create In Oct 30, 2009 By lee
	 * @param baId
	 * @param palnId
	 * @return Double
	 */
	Double getCurIncomeBalance(String baId,String palnId);
	/**
	 * ���ݸ���ƻ�ID��ȡ����ƻ�δ������
	 * @Methods Name getExpendBalanceByPlanId
	 * @Create In Sep 9, 2009 By lee
	 * @param palnId
	 * @return Double
	 */
	Double getExpendBalanceByPlanId(String palnId);
	/**
	 * ���ݽ�������ID���տ�ƻ�ID��ȡ�տ�ƻ���ǰδ������
	 * @Methods Name getCurExpendBalance
	 * @Create In Oct 30, 2009 By lee
	 * @param baId
	 * @param palnId
	 * @return Double
	 */
	Double getCurExpendBalance(String baId,String palnId);
	/**
	 * �����տ�ƻ�
	 * @Methods Name saveIncomeUpdatePlan
	 * @Create In Sep 9, 2009 By lee
	 * @param id
	 * @param money
	 * @param startDate
	 * @param endDate void
	 */
	void saveIncomeUpdatePlan(String id, String money, String startDate,String endDate);
	/**
	 * ���¸���ƻ�
	 * @Methods Name saveExpendUpdatePlan
	 * @Create In Sep 9, 2009 By lee
	 * @param id
	 * @param money
	 * @param startDate
	 * @param endDate void
	 */
	void saveExpendUpdatePlan(String id, String money, String startDate,String endDate);
	
	/**
	 * ��ȡ�տ�ƻ�������ʷ
	 * @Methods Name listIncomeUpdatePlanHis
	 * @Create In Sep 9, 2009 By lee
	 * @param planId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page listIncomeUpdatePlanHis(String planId,int pageNo,int pageSize);
	/**
	 * ��ȡ����ƻ�������ʷ
	 * @Methods Name listExpendUpdatePlanHis
	 * @Create In Sep 9, 2009 By lee
	 * @param planId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page listExpendUpdatePlanHis(String planId,int pageNo,int pageSize);
	
	/**
	 * ��ȡδ��ɵ��տ�ƻ�
	 * @Methods Name findUnFinshedIncomePlan
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<SRIncomePlan>
	 */
	List<SRIncomePlan> findUnFinshedIncomePlan(String requireId);
	
	/**
	 * ��ȡδ��ɵĸ���ƻ�
	 * @Methods Name findUnFinshedExpendPlan
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<SRExpendPlan>
	 */
	List<SRExpendPlan> findUnFinshedExpendPlan(String requireId);
	
	/**
	 * ��ȡ��������µ��տ���Ŀ
	 * @Methods Name getRealIcomeMoneyByBaId
	 * @Create In Sep 9, 2009 By lee
	 * @param businessAccountId
	 * @return List<RealIcomeMoney>
	 */
	List<RealIncome> getRealIncomeByBaId(String businessAccountId);
	/**
	 * ��ȡ��������µĸ�����Ŀ
	 * @Methods Name getRealPaymentByBaId
	 * @Create In Sep 9, 2009 By lee
	 * @param businessAccountId
	 * @return List<RealPayment>
	 */
	List<RealPayment> getRealPaymentByBaId(String businessAccountId);
	
	/**
	 * ��ȡ������ص������տ���Ŀ
	 * @Methods Name getRealIncomeByReqId
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<RealIncome>
	 */
	List<RealIncome> getRealIncomeByReqId(String requireId);
	
	/**
	 * ��ȡ������ص����и�����Ŀ
	 * @Methods Name getRealPaymentByReqId
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<RealPayment>
	 */
	List<RealPayment> getRealPaymentByReqId(String requireId);
	/**
	 * ��ѯ�̱������б�
	 * @Methods Name findBusinessAccount
	 * @Create In Nov 9, 2009 By duxh
	 * @param applyNum
	 * @param requireId
	 * @param applyDate
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
	public Page findBusinessAccount(String applyNum, Long requireId, Date applyDate,Integer status, int start, int size);
}

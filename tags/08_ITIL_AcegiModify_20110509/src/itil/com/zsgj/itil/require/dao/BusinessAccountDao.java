package com.zsgj.itil.require.dao;

import java.sql.Date;
import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.itil.config.extlist.entity.SRExpendPlan;
import com.zsgj.itil.config.extlist.entity.SRIncomePlan;
import com.zsgj.itil.require.entity.RealIncome;
import com.zsgj.itil.require.entity.RealPayment;

/**
 * 商务结算DAO
 * @Class Name BusinessAccountDao
 * @Author lee
 * @Create In Nov 25, 2009
 */
public interface BusinessAccountDao {

	/**
	 * 获取未完成收付款计划的需求数据
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
	 * 获取已完成收付款计划的需求数据
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
	 * 根据收款计划ID获取收款计划未收款金额
	 * @Methods Name getIncomeBalanceByPlanId
	 * @Create In Sep 9, 2009 By lee
	 * @param palnId
	 * @return Double
	 */
	Double getIncomeBalanceByPlanId(String palnId);
	/**
	 * 根据结算申请ID，收款计划ID获取收款计划当前未收款金额
	 * @Methods Name getCurIncomeBalance
	 * @Create In Oct 30, 2009 By lee
	 * @param baId
	 * @param palnId
	 * @return Double
	 */
	Double getCurIncomeBalance(String baId,String palnId);
	/**
	 * 根据付款计划ID获取付款计划未付款金额
	 * @Methods Name getExpendBalanceByPlanId
	 * @Create In Sep 9, 2009 By lee
	 * @param palnId
	 * @return Double
	 */
	Double getExpendBalanceByPlanId(String palnId);
	/**
	 * 根据结算申请ID，收款计划ID获取收款计划当前未付款金额
	 * @Methods Name getCurExpendBalance
	 * @Create In Oct 30, 2009 By lee
	 * @param baId
	 * @param palnId
	 * @return Double
	 */
	Double getCurExpendBalance(String baId,String palnId);
	/**
	 * 更新收款计划
	 * @Methods Name saveIncomeUpdatePlan
	 * @Create In Sep 9, 2009 By lee
	 * @param id
	 * @param money
	 * @param startDate
	 * @param endDate void
	 */
	void saveIncomeUpdatePlan(String id, String money, String startDate,String endDate);
	/**
	 * 更新付款计划
	 * @Methods Name saveExpendUpdatePlan
	 * @Create In Sep 9, 2009 By lee
	 * @param id
	 * @param money
	 * @param startDate
	 * @param endDate void
	 */
	void saveExpendUpdatePlan(String id, String money, String startDate,String endDate);
	
	/**
	 * 获取收款计划更新历史
	 * @Methods Name listIncomeUpdatePlanHis
	 * @Create In Sep 9, 2009 By lee
	 * @param planId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page listIncomeUpdatePlanHis(String planId,int pageNo,int pageSize);
	/**
	 * 获取付款计划更新历史
	 * @Methods Name listExpendUpdatePlanHis
	 * @Create In Sep 9, 2009 By lee
	 * @param planId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page listExpendUpdatePlanHis(String planId,int pageNo,int pageSize);
	
	/**
	 * 获取未完成的收款计划
	 * @Methods Name findUnFinshedIncomePlan
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<SRIncomePlan>
	 */
	List<SRIncomePlan> findUnFinshedIncomePlan(String requireId);
	
	/**
	 * 获取未完成的付款计划
	 * @Methods Name findUnFinshedExpendPlan
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<SRExpendPlan>
	 */
	List<SRExpendPlan> findUnFinshedExpendPlan(String requireId);
	
	/**
	 * 获取商务结算下的收款条目
	 * @Methods Name getRealIcomeMoneyByBaId
	 * @Create In Sep 9, 2009 By lee
	 * @param businessAccountId
	 * @return List<RealIcomeMoney>
	 */
	List<RealIncome> getRealIncomeByBaId(String businessAccountId);
	/**
	 * 获取商务结算下的付款条目
	 * @Methods Name getRealPaymentByBaId
	 * @Create In Sep 9, 2009 By lee
	 * @param businessAccountId
	 * @return List<RealPayment>
	 */
	List<RealPayment> getRealPaymentByBaId(String businessAccountId);
	
	/**
	 * 获取需求相关的所有收款条目
	 * @Methods Name getRealIncomeByReqId
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<RealIncome>
	 */
	List<RealIncome> getRealIncomeByReqId(String requireId);
	
	/**
	 * 获取需求相关的所有付款条目
	 * @Methods Name getRealPaymentByReqId
	 * @Create In Sep 9, 2009 By lee
	 * @param requireId
	 * @return List<RealPayment>
	 */
	List<RealPayment> getRealPaymentByReqId(String requireId);
	/**
	 * 查询商本结算列表。
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

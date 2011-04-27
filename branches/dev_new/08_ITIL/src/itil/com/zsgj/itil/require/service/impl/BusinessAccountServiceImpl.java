package com.zsgj.itil.require.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.itil.config.extlist.entity.SRExpendPlan;
import com.zsgj.itil.config.extlist.entity.SRIncomePlan;
import com.zsgj.itil.require.dao.BusinessAccountDao;
import com.zsgj.itil.require.entity.BusinessAccount;
import com.zsgj.itil.require.entity.RealIncome;
import com.zsgj.itil.require.entity.RealPayment;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.require.entity.UpDatePlan;
import com.zsgj.itil.require.entity.UpDatePlanEvent;
import com.zsgj.itil.require.service.BusinessAccountService;
public class BusinessAccountServiceImpl extends BaseService implements BusinessAccountService{
	BusinessAccountDao businessAccountDao;
	public Page findFinshedRequire(String name, String num, int pageNo,int pageSize) {
		return businessAccountDao.findFinshedRequire(name, num, pageNo, pageSize);
	}
	public Page findUnFinshedRequire(String name, String num, int pageNo,int pageSize) {
		return  businessAccountDao.findUnFinshedRequire(name, num, pageNo, pageSize);
	}
	public Double getIncomeBalanceByPlanId(String palnId) {
		return  businessAccountDao.getIncomeBalanceByPlanId(palnId);
	}

	public Double getExpendBalanceByPlanId(String palnId) {
		return  businessAccountDao.getExpendBalanceByPlanId(palnId);
	}

	public void saveIncomeUpdatePlan(String id, String money, String startDate,String endDate) {
		businessAccountDao.saveIncomeUpdatePlan(id, money, startDate, endDate);
	}

	public void saveExpendUpdatePlan(String id, String money, String startDate,String endDate) {
		businessAccountDao.saveExpendUpdatePlan(id, money, startDate, endDate);
	}

	public Page listIncomeUpdatePlanHis(String planId, int pageNo, int pageSize) {
		return businessAccountDao.listIncomeUpdatePlanHis(planId, pageNo, pageSize);
	}

	public Page listExpendUpdatePlanHis(String planId, int pageNo, int pageSize) {
		return businessAccountDao.listExpendUpdatePlanHis(planId, pageNo, pageSize);
	}
	public List<SRIncomePlan> findUnFinshedIncomePlan(String requireId) {
		return businessAccountDao.findUnFinshedIncomePlan(requireId);
	}
	public List<SRExpendPlan> findUnFinshedExpendPlan(String requireId) {
		return businessAccountDao.findUnFinshedExpendPlan(requireId);
	}
	public List<RealIncome> getRealIncomeByBaId(String businessAccountId) {
		return businessAccountDao.getRealIncomeByBaId(businessAccountId);
	}
	public List<RealPayment> getRealPaymentByBaId(String businessAccountId) {
		return businessAccountDao.getRealPaymentByBaId(businessAccountId);
	}
	public List<RealIncome> getRealIncomeByReqId(String requireId) {
		return businessAccountDao.getRealIncomeByReqId(requireId);
	}
	public List<RealPayment> getRealPaymentByReqId(String requireId) {
		return businessAccountDao.getRealPaymentByReqId(requireId);
	}
	public Double getCurIncomeBalance(String baId, String palnId) {
		return businessAccountDao.getCurIncomeBalance(baId, palnId);
	}
	public Double getCurExpendBalance(String baId, String palnId) {
		return businessAccountDao.getCurExpendBalance(baId, palnId);
	}
	public Page findBusinessAccount(String applyNum, Long requireId, java.sql.Date applyDate,
			Integer status, int start, int size) {
		return businessAccountDao.findBusinessAccount(applyNum, requireId,applyDate, status, start, size);
	}
	public BusinessAccountDao getBusinessAccountDao() {
		return businessAccountDao;
	}
	public void setBusinessAccountDao(BusinessAccountDao businessAccountDao) {
		this.businessAccountDao = businessAccountDao;
	}

}

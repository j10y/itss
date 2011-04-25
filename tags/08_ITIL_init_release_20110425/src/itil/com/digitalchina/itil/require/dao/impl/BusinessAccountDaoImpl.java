package com.digitalchina.itil.require.dao.impl;

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

import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.config.extlist.entity.SRExpendPlan;
import com.digitalchina.itil.config.extlist.entity.SRIncomePlan;
import com.digitalchina.itil.require.dao.BusinessAccountDao;
import com.digitalchina.itil.require.entity.BusinessAccount;
import com.digitalchina.itil.require.entity.RealIncome;
import com.digitalchina.itil.require.entity.RealPayment;
import com.digitalchina.itil.require.entity.SpecialRequirement;
import com.digitalchina.itil.require.entity.UpDatePlan;
import com.digitalchina.itil.require.entity.UpDatePlanEvent;

public class BusinessAccountDaoImpl extends BaseDao implements BusinessAccountDao{

	public Page findFinshedRequire(String name, String num, int pageNo,int pageSize) {
		Set finshedRequireSet = new HashSet();	//有收支计划并且已经完成的
		Set requireSet = new HashSet();// 有收支计划且未完成的需求集合
		
		Criteria incomePlanc = super.getCriteria(SRIncomePlan.class);
		incomePlanc.add(Restrictions.eq("isFinish", Integer.valueOf(0)));
		List<SRIncomePlan> incomePlans = incomePlanc.list();
		for (SRIncomePlan plan : incomePlans) {
			requireSet.add(plan.getSpecialRequire().getId());// 放入未完成收款计划的需求
		}

		Criteria expendPlanc = super.getCriteria(SRExpendPlan.class);
		expendPlanc.add(Restrictions.eq("isFinish", Integer.valueOf(0)));
		List<SRExpendPlan> expendPlans = expendPlanc.list();
		for (SRExpendPlan plan : expendPlans) {
			requireSet.add(plan.getSpecialRequire().getId());// 放入未完成付款计划的需求
		}
		
		Criteria finishedIncomePlanc = super.getCriteria(SRIncomePlan.class);
		finishedIncomePlanc.add(Restrictions.eq("isFinish", Integer.valueOf(1)));
		List<SRIncomePlan> finishedIncomePlans = finishedIncomePlanc.list();
		for (SRIncomePlan plan : finishedIncomePlans) {
			if(!incomePlans.contains(plan)){
				finshedRequireSet.add(plan.getSpecialRequire().getId());// 放入完成收款计划的需求
			}
		}
		
		Criteria  finishedExpendPlanc = super.getCriteria(SRExpendPlan.class);
		finishedExpendPlanc.add(Restrictions.eq("isFinish", Integer.valueOf(1)));
		List<SRExpendPlan> finishedExpendPlans = finishedExpendPlanc.list();
		for (SRExpendPlan plan : finishedExpendPlans) {
			if(!expendPlans.contains(plan)){
				finshedRequireSet.add(plan.getSpecialRequire().getId());// 放入完成付款计划的需求
			}
		}
		
		Criteria c = super.getCriteria(SpecialRequirement.class);
		if (StringUtils.isNotBlank(name)) {
			c.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(num)) {
			c.add(Restrictions.ilike("requireId", num, MatchMode.ANYWHERE));
		}
		c.add(Restrictions.or(Restrictions.eq("status", Integer.valueOf(2)),Restrictions.eq("status", Integer.valueOf(1))));// 必须为进行中或已完成的需求
		if(finshedRequireSet.isEmpty()){
			c.add(Restrictions.eq("id", Long.valueOf(0)));
		}else{
			c.add(Restrictions.in("id", finshedRequireSet));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	public Page findUnFinshedRequire(String name, String num, int pageNo,
			int pageSize) {
		Set requireSet = new HashSet();// 有收支计划且未完成的需求集合

		Criteria incomePlanc = super.getCriteria(SRIncomePlan.class);
		incomePlanc.add(Restrictions.eq("isFinish", Integer.valueOf(0)));
		List<SRIncomePlan> incomePlans = incomePlanc.list();
		for (SRIncomePlan plan : incomePlans) {
			requireSet.add(plan.getSpecialRequire().getId());// 放入未完成收款计划的需求
		}

		Criteria expendPlanc = super.getCriteria(SRExpendPlan.class);
		expendPlanc.add(Restrictions.eq("isFinish", Integer.valueOf(0)));
		List<SRExpendPlan> expendPlans = expendPlanc.list();
		for (SRExpendPlan plan : expendPlans) {
			requireSet.add(plan.getSpecialRequire().getId());// 放入未完成付款计划的需求
		}

		Criteria c = super.getCriteria(SpecialRequirement.class);
		if (StringUtils.isNotBlank(name)) {
			c.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(num)) {
			//2010-09-16 modified by huzh for bug beign
//			c.add(Restrictions.ilike("requireId", num, MatchMode.ANYWHERE));
			c.add(Restrictions.ilike("applyNum", num, MatchMode.ANYWHERE));
			//2010-09-16 modified by huzh for bug end
		}
		c.add(Restrictions.or(Restrictions.eq("status", Integer.valueOf(2)),Restrictions.eq("status", Integer.valueOf(1))));// 必须为进行中或已完成的需求
		c.add(Restrictions.in("id", requireSet));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}
	public Double getIncomeBalanceByPlanId(String palnId) {
		Double amount = 0.0;
		SRIncomePlan plan = get(SRIncomePlan.class, Long.valueOf(palnId));
		amount = plan.getMoney();
		UpDatePlan updataplan = this.findUniqueBy(UpDatePlan.class, "incomePlan", plan);
		if(updataplan!=null){
			amount = updataplan.getMoney();
		}
		Criteria criteria = super.createCriteria(RealIncome.class);
		criteria.createAlias("this.businessAccount", "business").setFetchMode("business", FetchMode.JOIN);
		//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 begin
		//criteria.add(Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED));
		criteria.add(Restrictions.or(
				Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
				Restrictions.eq("business.status", BusinessAccount.STATUS_APPROVING)));
		//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 end
		criteria.add(Restrictions.eq("incomePlan", plan));
		List<RealIncome> list = criteria.list();
		if (list != null && list.size() != 0) {
			for (RealIncome money : list) {
				amount = amount - money.getRealMoney();
			}
		} 
		return amount;
	}

	public Double getExpendBalanceByPlanId(String palnId) {
		Double amount = 0.0;
		SRExpendPlan plan = get(SRExpendPlan.class, Long.valueOf(palnId));
		amount = plan.getMoney();
		UpDatePlan updataplan = this.findUniqueBy(UpDatePlan.class, "expendPlan", plan);
		if(updataplan!=null){
			amount = updataplan.getMoney();
		}
		Criteria criteria = super.createCriteria(RealPayment.class);
		criteria.createAlias("this.businessAccount", "business").setFetchMode("business", FetchMode.JOIN);
		//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 begin
		//criteria.add(Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED));
		criteria.add(Restrictions.or(
				Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
				Restrictions.eq("business.status", BusinessAccount.STATUS_APPROVING)));
		//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 end
		criteria.add(Restrictions.eq("expendPlan", plan));
		List<RealPayment> list = criteria.list();
		if (list != null && list.size() != 0) {
			for (RealPayment money : list) {
				amount = amount - money.getRealMoney();
			}
		} 
		return amount;
	}

	public void saveIncomeUpdatePlan(String id, String money, String startDate,String endDate) {
		UpDatePlan plan = null;
		UpDatePlanEvent event = new UpDatePlanEvent();
		event.setCreateUser(UserContext.getUserInfo());
		event.setCreateDate(new Date());
		event.setMoney(Double.parseDouble(money));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate2 = null;
		Date endDate2 = null;
		try {
			startDate2 = format.parse(startDate);
			endDate2 = format.parse(endDate);
		} catch (ParseException e) {
			System.out.println("传入的日期格式不正确");
			e.printStackTrace();
		}
		event.setStartDate(startDate2);
		event.setEndDate(endDate2);
		SRIncomePlan plan2 = super.get(SRIncomePlan.class,Long.parseLong(id));
		event.setIncomePlan(plan2);
		super.save(event);
		
		Criteria criteria = super.createCriteria(UpDatePlan.class);
		criteria.createAlias("this.incomePlan", "plan1").setFetchMode("plan1", FetchMode.JOIN);
		criteria.add(Restrictions.eq("plan1.id", Long.parseLong(id)));
		plan = (UpDatePlan) criteria.uniqueResult();
		if (plan == null) {
			plan  = new UpDatePlan();
		}
		plan.setStartDate(startDate2);
		plan.setEndDate(endDate2);
		plan.setMoney(Double.parseDouble(money));
		plan.setIncomePlan(plan2);
		super.save(plan);
	}

	public void saveExpendUpdatePlan(String id, String money, String startDate,String endDate) {
		UpDatePlan plan = null;
		UpDatePlanEvent event = new UpDatePlanEvent();
		event.setCreateUser(UserContext.getUserInfo());
		event.setCreateDate(new Date());
		event.setMoney(Double.parseDouble(money));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate2 = null;
		Date endDate2 = null;
		try {
			startDate2 = format.parse(startDate);
			endDate2 = format.parse(endDate);
		} catch (ParseException e) {
			System.out.println("传入的日期格式不正确");
			e.printStackTrace();
		}
		event.setStartDate(startDate2);
		event.setEndDate(endDate2);
		SRExpendPlan plan2 = get(SRExpendPlan.class,Long.parseLong(id));
		event.setExpendPlan(plan2);
		super.save(event);
		
		Criteria criteria = super.createCriteria(UpDatePlan.class);
		criteria.createAlias("this.expendPlan", "plan1").setFetchMode("plan1", FetchMode.JOIN);
		criteria.add(Restrictions.eq("plan1.id", Long.parseLong(id)));
		plan = (UpDatePlan) criteria.uniqueResult();
		if (plan == null) {
			plan  = new UpDatePlan();
		}
		plan.setStartDate(startDate2);
		plan.setEndDate(endDate2);
		plan.setMoney(Double.parseDouble(money));
		plan.setExpendPlan(plan2);
		super.save(plan);
	}

	public Page listIncomeUpdatePlanHis(String planId, int pageNo, int pageSize) {
		Page page = new Page();
		Criteria criteria = super.createCriteria(UpDatePlanEvent.class);
		criteria.createAlias("this.incomePlan", "plan").setFetchMode("plan", FetchMode.JOIN);
		criteria.add(Restrictions.eq("plan.id", Long.parseLong(planId)));
		criteria.addOrder(Order.desc("createDate"));
		page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}

	public Page listExpendUpdatePlanHis(String planId, int pageNo, int pageSize) {
		Page page = new Page();
		Criteria criteria = super.createCriteria(UpDatePlanEvent.class);
		criteria.createAlias("this.expendPlan", "plan").setFetchMode("plan", FetchMode.JOIN);
		criteria.add(Restrictions.eq("plan.id", Long.parseLong(planId)));
		criteria.addOrder(Order.desc("createDate"));
		page = super.pagedQuery(criteria, pageNo, pageSize);
		return page;
	}
	public List<SRIncomePlan> findUnFinshedIncomePlan(String requireId) {
		Criteria c = this.createCriteria(SRIncomePlan.class);
		c.add(Restrictions.eq("specialRequire.id", Long.valueOf(requireId)));
		c.add(Restrictions.eq("isFinish", Integer.valueOf(0)));//未完成的
		List<SRIncomePlan> plans = c.list();
		return plans;
	}
	public List<SRExpendPlan> findUnFinshedExpendPlan(String requireId) {
		Criteria c = this.createCriteria(SRExpendPlan.class);
		c.add(Restrictions.eq("specialRequire.id", Long.valueOf(requireId)));
		c.add(Restrictions.eq("isFinish", Integer.valueOf(0)));//未完成的
		List<SRExpendPlan> plans = c.list();
		return plans;
	}
	public List<RealIncome> getRealIncomeByBaId(String businessAccountId) {
		Criteria criteria = createCriteria(RealIncome.class);
		criteria.add(Restrictions.eq("this.businessAccount.id", Long.valueOf(businessAccountId)));
		criteria.setFetchMode("incomePlan", FetchMode.JOIN);
		criteria.setFetchMode("upDatePlan", FetchMode.JOIN);
		return criteria.list();
	}
	public List<RealPayment> getRealPaymentByBaId(String businessAccountId) {
		Criteria criteria = createCriteria(RealPayment.class);
		criteria.add(Restrictions.eq("this.businessAccount.id", Long.valueOf(businessAccountId)));
		criteria.setFetchMode("expendPlan", FetchMode.JOIN);
		criteria.setFetchMode("upDatePlan", FetchMode.JOIN);
		return criteria.list();
	}
	public List<RealIncome> getRealIncomeByReqId(String requireId) {
		Criteria criteria = createCriteria(RealIncome.class);
		criteria.createAlias("this.businessAccount", "business").setFetchMode("business", FetchMode.JOIN);
		criteria.add(Restrictions.eq("business.require.id", Long.valueOf(requireId)));
		//modify by lee for 审批中的也要显示 in 20091109 begin
		//criteria.add(Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED));
		criteria.add(Restrictions.or(
				Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
				Restrictions.eq("business.status", BusinessAccount.STATUS_APPROVING)));
		//modify by lee for 审批中的也要显示 in 20091109 end
		criteria.setFetchMode("incomePlan", FetchMode.JOIN);
		criteria.setFetchMode("upDatePlan", FetchMode.JOIN);
		return criteria.list();
	}
	public List<RealPayment> getRealPaymentByReqId(String requireId) {
		Criteria criteria = createCriteria(RealPayment.class);
		criteria.createAlias("this.businessAccount", "business").setFetchMode("business", FetchMode.JOIN);
		criteria.add(Restrictions.eq("business.require.id", Long.valueOf(requireId)));
		//modify by lee for 审批中的也要显示 in 20091109 begin
		//criteria.add(Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED));
		criteria.add(Restrictions.or(
				Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
				Restrictions.eq("business.status", BusinessAccount.STATUS_APPROVING)));
		//modify by lee for 审批中的也要显示 in 20091109 end
		criteria.setFetchMode("expendPlan", FetchMode.JOIN);
		criteria.setFetchMode("upDatePlan", FetchMode.JOIN);
		return criteria.list();
	}
	public Double getCurIncomeBalance(String baId, String palnId) {
		Double amount = 0.0;
		SRIncomePlan plan = get(SRIncomePlan.class, Long.valueOf(palnId));
		amount = plan.getMoney();
		UpDatePlan updataplan = this.findUniqueBy(UpDatePlan.class, "incomePlan", plan);
		if(updataplan!=null){
			amount = updataplan.getMoney();
		}
		Criteria criteria = super.createCriteria(RealIncome.class);
		criteria.createAlias("this.businessAccount", "business").setFetchMode("business", FetchMode.JOIN);
		criteria.add(Restrictions.or(
				//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 begin
				//Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
				Restrictions.or(
						Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
						Restrictions.eq("business.status", BusinessAccount.STATUS_APPROVING)),
				//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 end
				
				Restrictions.eq("business.id", Long.valueOf(baId))));
		criteria.add(Restrictions.eq("incomePlan", plan));
		List<RealIncome> list = criteria.list();
		if (list != null && list.size() != 0) {
			for (RealIncome money : list) {
				amount = amount - money.getRealMoney();
			}
		} 
		return amount;
	}
	public Double getCurExpendBalance(String baId, String palnId) {
		Double amount = 0.0;
		SRExpendPlan plan = get(SRExpendPlan.class, Long.valueOf(palnId));
		amount = plan.getMoney();
		UpDatePlan updataplan = this.findUniqueBy(UpDatePlan.class, "expendPlan", plan);
		if(updataplan!=null){
			amount = updataplan.getMoney();
		}
		Criteria criteria = super.createCriteria(RealPayment.class);
		criteria.createAlias("this.businessAccount", "business").setFetchMode("business", FetchMode.JOIN);
		criteria.add(Restrictions.or(
				//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 begin
				//Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
				Restrictions.or(
						Restrictions.eq("business.status", BusinessAccount.STATUS_FINISHED),
						Restrictions.eq("business.status", BusinessAccount.STATUS_APPROVING)),
				//modify by lee for 申请一旦提交金额从总额中扣除 in 20091109 end
				Restrictions.eq("business.id", Long.valueOf(baId))));
		criteria.add(Restrictions.eq("expendPlan", plan));
		List<RealPayment> list = criteria.list();
		if (list != null && list.size() != 0) {
			for (RealPayment money : list) {
				amount = amount - money.getRealMoney();
			}
		} 
		return amount;
	}
	public Page findBusinessAccount(String applyNum, Long requireId, java.sql.Date applyDate,
			Integer status, int start, int size) {
		Criteria count=createCriteria(BusinessAccount.class);
		if(applyNum.trim().length()!=0){
			count.add(Restrictions.eq("applyNum",applyNum));
		}
		if(requireId!=null){
			count.createAlias("require", "rq")
			.add(Restrictions.eq("rq.id", requireId));
		}
		if(applyDate!=null){
			count.add(Restrictions.eq("applyDate", applyDate));
		}
		if(status!=null){
			count.add(Restrictions.eq("status", status));
		}
		Long totalSize=((Integer)count.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		Criteria c=createCriteria(BusinessAccount.class)
					.createAlias("applyUser", "au")
					.setFetchMode("applyUser", FetchMode.JOIN)
					.setFetchMode("au.department", FetchMode.JOIN)
					.setFetchMode("require", FetchMode.JOIN)
					.setFirstResult(start)
					.setMaxResults(size);
		if(applyNum.trim().length()!=0){
			c.add(Restrictions.ilike("applyNum",applyNum,MatchMode.ANYWHERE));
		}
		if(requireId!=null){
			c.createAlias("require", "rq")
			.add(Restrictions.eq("rq.id", requireId));
		}
		if(applyDate!=null){
			c.add(Restrictions.eq("applyDate", applyDate));
		}
		if(status!=null){
			c.add(Restrictions.eq("status", status));
		}
		List<BusinessAccount> businessAccounts=c.list();
		return new Page(start,totalSize,size,businessAccounts);
	}
}

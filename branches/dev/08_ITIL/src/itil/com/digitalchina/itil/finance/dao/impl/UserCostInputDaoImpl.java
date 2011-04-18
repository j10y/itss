package com.digitalchina.itil.finance.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.itil.config.entity.ConfigItem;
import com.digitalchina.itil.finance.dao.UserCostInputDao;
import com.digitalchina.itil.finance.entity.FinanceConstant;
import com.digitalchina.itil.finance.entity.FinanceCostCenter;
import com.digitalchina.itil.finance.entity.FinanceCostSchedules;
import com.digitalchina.itil.finance.entity.FinanceCostType;

public class UserCostInputDaoImpl extends BaseDao implements UserCostInputDao{

	public void saveUserCostMesg(FinanceCostSchedules financeCostSchedules) {
		// TODO Auto-generated method stub
		try{
			super.save(financeCostSchedules);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public String findInterPartmentCode() {
		Criteria criteria = super.createCriteria(ConfigItem.class);
		criteria.add(Restrictions.like("name", "信息化管理部",MatchMode.ANYWHERE));
		ConfigItem configItem = (ConfigItem)criteria.uniqueResult();
		String configItemCode = configItem.getCisn();
		return configItemCode;
	}

	public FinanceCostType findFinanceCostType() {
		Criteria criteria = super.createCriteria(FinanceCostType.class);
		criteria.add(Restrictions.eq("costTypeCode", FinanceConstant.NOTERPTYPEUSERTOTALAMOUNT));
		FinanceCostType financeCostType = (FinanceCostType)criteria.uniqueResult();
		return financeCostType;
	}

	public Page findFinanceCostCenterBySpecialParam(int pageNo, int pageSize,String propertyValue) {
		Criteria c = super.getCriteria(FinanceCostCenter.class);
		c.add(Restrictions.like("CBZXDM",  propertyValue ,MatchMode.ANYWHERE));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public FinanceCostCenter findFinanceCostCenterByCode(String costCenterCode) {
		Criteria c = super.getCriteria(FinanceCostCenter.class);
		c.add(Restrictions.eq("id", Long.valueOf(costCenterCode)));
		FinanceCostCenter financeCostCenter = (FinanceCostCenter)c.uniqueResult();
		return financeCostCenter;
	}

}

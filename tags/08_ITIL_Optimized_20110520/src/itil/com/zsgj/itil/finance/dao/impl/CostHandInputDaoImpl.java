package com.zsgj.itil.finance.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.entity.ConfigItemStatus;
import com.zsgj.itil.finance.dao.CostHandInputDao;
import com.zsgj.itil.finance.entity.FinanceConstant;
import com.zsgj.itil.finance.entity.FinanceCostCenter;
import com.zsgj.itil.finance.entity.FinanceCostSchedules;
import com.zsgj.itil.finance.entity.FinanceCostType;
import com.zsgj.itil.service.entity.ServiceItem;

public class CostHandInputDaoImpl extends BaseDao implements CostHandInputDao{

	public Page selectConfigItem(int pageNo, int pageSize,String value) {
		
		List<String>  status=new ArrayList<String>();
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_ARCHIVED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_DISABLED);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_LOAN);
		status.add(ConfigItemStatus.CONFIGITEMSTATUS_STANDBY);
		Criteria c = super.getCriteria(ConfigItem.class);
		c.add(Restrictions.like("name", value.trim(), MatchMode.ANYWHERE));
		c.createAlias("configItemStatus", "configItemStatus");
		c.add(Restrictions.eq("status", ConfigItem.VALID_STATUS));
		c.add(Restrictions.not(Restrictions.in("configItemStatus.enname", status)));
		c.addOrder(Order.asc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
		
	}

	public Page selectServiceItem(int pageNo, int pageSize,String value) {
		Criteria c = super.getCriteria(ServiceItem.class);
		c.add(Restrictions.like("name", value.trim(), MatchMode.ANYWHERE));
		c.add(Restrictions.eq("deleteFlag", ServiceItem.DELETE_FALSE));
		c.addOrder(Order.asc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Page selectFinanceCostType(int pageNo, int pageSize,String value) {
		Criteria c = super.getCriteria(FinanceCostType.class);
		c.add(Restrictions.like("costTypeName", value.trim(), MatchMode.ANYWHERE));
		c.add(Restrictions.eq("isERPType", FinanceConstant.ERPTYPE));
		c.addOrder(Order.asc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Page selectReimbursement(int pageNo, int pageSize,String value) {
		Criteria c = super.getCriteria(UserInfo.class);
		c.add(Restrictions.eq("enabled", 1));
		c.add(Restrictions.like("userName", value.trim(), MatchMode.ANYWHERE));
		c.add(Restrictions.eq("isLock", 0));//这里要过滤后台被删除的用户
		c.add(Restrictions.sqlRestriction("externalFlag is null"));//外部用户不显示
		c.addOrder(Order.asc("userName"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Page selectFinanceCostCenter(int pageNo, int pageSize, String value) {
		Criteria c = super.getCriteria(FinanceCostCenter.class);
		c.add(Restrictions.like("CBZXMC", value.trim(), MatchMode.ANYWHERE));
		c.addOrder(Order.asc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Object findObjectByProperty(String s,Long id,String propertyName ,Object propertyValue) {
		Object obj = null ;
		Class clazz = null;
		try {
			clazz = Class.forName(s);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Criteria c = super.getCriteria(clazz);
		if(id!=null&&id!=0){
			c.add(Restrictions.eq("id", id));
		}
		if(propertyName!=null&&!"".equals(propertyName)){
			c.add(Restrictions.eq(propertyName, propertyValue));
		}
		List<Object> list = c.list();
		if(list.size()>0){
			obj = list.get(0);
		}
		return obj;
	}

	public Page selectList(Map<String, String> map, int pageNo,
			int pageSize) {
		Criteria c = super.getCriteria(FinanceCostSchedules.class);
		if(map.size()>0){
			String propertyValue = map.get("costReduceType");
			String tempDataSourse = map.get("costDataSource");
			if(!"".equals(propertyValue)&&propertyValue!=null){
				int costReduceType = Integer.valueOf(propertyValue);
				c.add(Restrictions.eq("costReduceType", costReduceType));
			}
			if(!"".equals(tempDataSourse)&&tempDataSourse!=null){
				int costDataSource = Integer.valueOf(tempDataSourse);
				c.add(Restrictions.eq("costDataSource", costDataSource));
			}
			
		}
		c.addOrder(Order.desc("id"));
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

}

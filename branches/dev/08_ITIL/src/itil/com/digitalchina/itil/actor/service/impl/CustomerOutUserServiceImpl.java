package com.digitalchina.itil.actor.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.actor.entity.Customer;
import com.digitalchina.itil.actor.entity.CustomerOutUserInfo;
import com.digitalchina.itil.actor.service.CustomerOutUserService;

public class CustomerOutUserServiceImpl extends BaseDao implements CustomerOutUserService{

	public Page findCustOutByCustName(String custName, String orderProp,
			boolean isAsc, int pageNo, int pageSize) {
		
		Criteria c = super.getCriteria(Customer.class);
		if(StringUtils.isNotBlank(custName)){
			c.add(Restrictions.like("customerName", custName, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		
		return page;
	}

	public Page findCustomerOutUser(Map params, int pageNo, int pageSize, String propName, boolean isAsc) {
		String username = (String) params.get("userName");
		String realname = (String) params.get("realname");
		Criteria dc = super.createCriteria(UserInfo.class, propName, isAsc);
		if(username!=null&& !username.equals("")){
			dc.add(Restrictions.ilike("userName", username, MatchMode.ANYWHERE));
		}
		if(realname!=null&& !realname.equals("")){
			dc.add(Restrictions.ilike("realName", realname, MatchMode.ANYWHERE));
		}
				
		Disjunction disj2 = Restrictions.disjunction(); //非锁定用户
		disj2.add(Restrictions.isNull("isLock")); 
		disj2.add(Restrictions.eq("isLock", Integer.valueOf(0)));
		
		dc.add(Restrictions.eq("externalFlag", Integer.valueOf(1))); //是外部用户
		//dc.add(disj2); //并且不是锁定的
		
		Page page = super.pagedQuery(dc, pageNo, pageSize);
		return page;
	}

	public List<CustomerOutUserInfo> findCustomerOutUser(Customer custOut, UserInfo userInfo) {
		Criteria c = super.createCriteria(CustomerOutUserInfo.class);
		c.add(Restrictions.eq("customerOut", custOut));
		c.add(Restrictions.eq("userInfo", userInfo));
		List<CustomerOutUserInfo> list = c.list();
		return list;
	}

	public CustomerOutUserInfo saveCustomerOutUserInfo(Customer custOut, UserInfo userInfo) {
		CustomerOutUserInfo cou = null;
		Criteria c = super.createCriteria(CustomerOutUserInfo.class);
		c.add(Restrictions.eq("customerOut", custOut));
		c.add(Restrictions.eq("userInfo", userInfo));
		c.setProjection(Projections.rowCount());
		Integer rowcount = (Integer) c.uniqueResult();
		if(rowcount==null|| rowcount.intValue()==0){
			c = super.createCriteria(CustomerOutUserInfo.class);
			c.add(Restrictions.eq("userInfo", userInfo));
			c.setProjection(Projections.rowCount());
			rowcount = (Integer) c.uniqueResult();
			if(rowcount!=null&& rowcount.intValue()>0){ //说明是修改了用户的所属外部客户字段
				super.executeUpdate("delete from CustomerOutUserInfo where userInfo=?", userInfo);
			}
			cou = new CustomerOutUserInfo();
			cou.setCustomerOut(custOut);
			cou.setUserInfo(userInfo);
			super.save(cou);
		}else{
			c.setProjection(null);
			c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			cou = (CustomerOutUserInfo) c.uniqueResult();
		}
		return cou;
	}

	public UserInfo saveUserInfoWithRoles(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}

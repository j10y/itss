package com.zsgj.itil.actor.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.zsgj.info.framework.dao.BaseDao;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.actor.dao.CustomerDao;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerOutUserInfo;

public class CustomerDaoImpl extends BaseDao implements CustomerDao{
	
	public List<Long> findCustIdsByUser(UserInfo userInfo) {
		List<Long> ids = new ArrayList<Long>();
		userInfo = super.get(UserInfo.class, userInfo.getId());
		
		Integer externalFlag = userInfo.getExternalFlag();
		if(externalFlag==null||externalFlag.intValue()==0){
			//获取当前人的隶属部门
			Department dept = userInfo.getDepartment();
			//存储由小到大的部门列表
			List<Department> deptList = new ArrayList<Department>();
			//放入当前部门
			deptList.add(dept);
			//递归获取上级部门，依顺序放入集合
			this.getParentDept(deptList, dept);
			for(Department deptItem : deptList){
				//用部门去获取内部客户
				Criteria c = super.getCriteria(Customer.class);
				c.add(Restrictions.eq("department", deptItem));
				List<Customer> list2 = c.list();
				if(!list2.isEmpty()){
					Customer customerIn = list2.iterator().next();
				}
				
				c.setProjection(Projections.property("this.id"));
				List<Long> list = c.list();
				if(!list.isEmpty()){
					Long custIn = list.iterator().next();
					ids.add(custIn);
				}
			}
			
		}else{//外部用户令考虑
			Long customerOut = null;
			Criteria c = super.getCriteria(CustomerOutUserInfo.class);
			c.add(Restrictions.eq("userInfo", userInfo));
			c.setProjection(Projections.property("customerOut.id"));
			List<Long> list = c.list();
			if(list.size()==1){
				customerOut = list.iterator().next();
			}
			ids.add(customerOut);
		}
		return ids;
	}
	
	private void getParentDept(List<Department> list, Department dept){
		Criteria c = super.getCriteria(Department.class);
		c.add(Restrictions.eq("id", dept.getId()));
		c.setFetchMode("parentDepartment", FetchMode.JOIN);
		Department result = (Department) c.uniqueResult();
		Department parent = result.getParentDepartment();
		
		if(parent!=null){
			//System.out.println("parent name : "+ parent.getDepartName());
			list.add(parent);
			//继续递归
			this.getParentDept(list, parent);
		}
	}

	public Long findCustIdByUser(UserInfo userInfo) {
		userInfo = super.get(UserInfo.class, userInfo.getId());
		Integer externalFlag = userInfo.getExternalFlag();
		if(externalFlag==null||externalFlag.intValue()==0){
			//获取当前人的隶属部门
			Department dept = userInfo.getDepartment();
			Customer customerIn = this.getCustomerInByDept(dept);
			return customerIn.getId();
		}else{
			Criteria c = super.getCriteria(CustomerOutUserInfo.class);
			c.add(Restrictions.eq("userInfo", userInfo));
			c.setProjection(Projections.property("customerOut.id"));
			List<Long> list = c.list();
			return list.get(0);
		}
	}

	public Customer getCustomerInByDept(Department dept) {
		dept = this.get(Department.class, dept.getId());
		Criteria c = super.getCriteria(Customer.class);
		c.add(Restrictions.eq("department", dept));
		List<Customer> customerIns = c.list();
		if(customerIns.isEmpty()){
			Department pdept = dept.getParentDepartment();
			if(pdept!=null){
				return getCustomerInByDept(dept.getParentDepartment());
			}else{
				return null;
			}
		}else{
			return customerIns.get(0);
		}
		
	}

	public Page getAllUser(String userName, int pageNo, int pageSize) {
		Criteria c = super.getCriteria(UserInfo.class);
		if(userName!=null&&userName!=""){
			c.add(Restrictions.like("userName", userName, MatchMode.ANYWHERE));
		}
		Page page = this.pagedQuery(c, pageNo, pageSize);
		return page;
	}
}

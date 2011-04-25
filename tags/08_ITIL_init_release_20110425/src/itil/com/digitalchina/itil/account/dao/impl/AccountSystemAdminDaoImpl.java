package com.digitalchina.itil.account.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.BaseDao;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.info.framework.security.entity.WorkSpace;
import com.digitalchina.itil.account.dao.AccountSystemAdminDao;
import com.digitalchina.itil.account.entity.AccountSBUOfficer;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.actor.entity.RAndBUserList;
import com.digitalchina.itil.config.extlist.entity.MailGroup;
import com.digitalchina.itil.config.extlist.entity.MobileTelAllowance;
import com.digitalchina.itil.config.extlist.entity.PlatFormHRCountSign;
import com.digitalchina.itil.config.extlist.entity.TelephoneCountSign;
import com.digitalchina.info.framework.dao.support.Page;

public class AccountSystemAdminDaoImpl extends BaseDao implements AccountSystemAdminDao {

	public Page findAllSameMailDept(String name, int pageNo,
			int pageSize) {
		Criteria c =super.createCriteria(SameMailDept.class);
		c.add(Restrictions.isNull("endDate"));
		if(name!= null){
			c.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

	public Page findMailGroup(String groupName, int pageNo, int pageSize) {
		Criteria c =super.createCriteria(MailGroup.class);
		if(groupName!= null){
			c.add(Restrictions.like("groupNewName", groupName,MatchMode.ANYWHERE));
		}
		c.add(Restrictions.eq("accountState", "1"));
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

	public Page findWorkSapce(String name, int pageNo, int pageSize) {
		Criteria c =super.createCriteria(WorkSpace.class);
		if(name!= null){
			c.add(Restrictions.like("name", name,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

	public Page findPlatFormHrSign(String department, int pageNo, int pageSize) {
		Criteria c =super.createCriteria(PlatFormHRCountSign.class);
		if(department!= null){
			c.add(Restrictions.like("department", department,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo,pageSize);
		return page;
	}

	public Page findTelephoneCountSignByDeptName(String deptname,int pageNo,int pageSize) {
		Criteria c = super.createCriteria(TelephoneCountSign.class);
		
		c.add(Restrictions.ne("countSignItcode","admin"));
		if(deptname!=null&&!"".equals(deptname)){
			c.add(Restrictions.like("department", deptname, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, pageNo, pageSize);
		return page;
	}

	public Page findAccountSBUOfficerByName(String deptname, int start,
			int pageSize) {
		Criteria c =super.createCriteria(AccountSBUOfficer.class);
		if(deptname!= null){
			c.add(Restrictions.like("processNameDescription", deptname,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, start,pageSize);
		return page;
	}

	public Page findMobileTelAllowance(String deptname, int start, int pageSize) {
		Criteria c =super.createCriteria(MobileTelAllowance.class);
		if(deptname!= null){
			c.add(Restrictions.like("postCode", deptname,MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, start,pageSize);
		return page;
	}

	public Page findRAndBUserListByNameAndType(String deptname, String type,
			int start, int pageSize) {
		Criteria c = super.createCriteria(RAndBUserList.class);
		if(deptname!=null&&!"".equals(deptname)){
			c.add(Restrictions.like("userName", deptname, MatchMode.ANYWHERE));
		}
		if(type!=null&&!"".equals(type)){
			c.add(Restrictions.like("type", type, MatchMode.ANYWHERE));
		}
		Page page = super.pagedQuery(c, start, pageSize);
		return page;
	}

}

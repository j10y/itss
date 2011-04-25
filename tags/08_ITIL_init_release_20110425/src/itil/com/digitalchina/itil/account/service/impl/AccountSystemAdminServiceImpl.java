package com.digitalchina.itil.account.service.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.SameMailDept;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.account.dao.AccountSystemAdminDao;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.account.service.AccountSystemAdminService;

public class AccountSystemAdminServiceImpl extends BaseService implements AccountSystemAdminService {
	private AccountSystemAdminDao systemAdminDao;
	
	public AccountSystemAdminDao getSystemAdminDao() {
		return systemAdminDao;
	}


	public void setSystemAdminDao(AccountSystemAdminDao systemAdminDao) {
		this.systemAdminDao = systemAdminDao;
	}


	public Page findSameMailDeptByPage(String name, int pageNo, int pageSize) {
		Page sameMailDept=systemAdminDao.findAllSameMailDept(name, pageNo, pageSize);
		return sameMailDept;
	}


	public Page findMailGroupByPage(String name, int pageNo, int pageSize) {
		Page mailGroup=systemAdminDao.findMailGroup(name, pageNo, pageSize);
		return mailGroup;
	}


	public Page findWorkSpaceByPage(String name, int pageNo, int pageSize) {
		Page workSpace=systemAdminDao.findWorkSapce(name, pageNo, pageSize);
		return workSpace;
	}


	public Page findPlatFormHrSignByPage(String department, int pageNo, int pageSize) {
		Page signUser=systemAdminDao.findPlatFormHrSign(department, pageNo, pageSize);
		return signUser;
	}


	public Page findTelephoneCountSignByDeptName(String deptname, int start,
			int pageSize) {
		return systemAdminDao.findTelephoneCountSignByDeptName(deptname,start,pageSize);
	}


	public Page findAccountSBUOfficerByName(String deptname, int start,
			int pageSize) {
		return systemAdminDao.findAccountSBUOfficerByName(deptname,start,pageSize);
	}


	public Page findMobileTelAllowance(String deptname, int start, int pageSize) {
		return systemAdminDao.findMobileTelAllowance(deptname,start,pageSize);
	}


	public Page findRAndBUserListByNameAndType(String deptname, String type,
			int start, int pageSize) {
		return systemAdminDao.findRAndBUserListByNameAndType(deptname,type,start,pageSize);
	}



}

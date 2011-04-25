package com.digitalchina.itil.account.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.itil.account.dao.AccountDao;
import com.digitalchina.itil.account.entity.AccountModifyDesc;
import com.digitalchina.itil.account.entity.AccountModifyRecord;
import com.digitalchina.itil.account.entity.AccountNewAppAdmin;
import com.digitalchina.itil.account.entity.AccountSBUOfficer;
import com.digitalchina.itil.account.entity.DCContacts;
import com.digitalchina.itil.account.entity.MobileTelephoneApply;
import com.digitalchina.itil.account.entity.PersonFormalAccount;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.account.entity.SystemAppAdmin;
import com.digitalchina.itil.account.service.AccountService;
import com.digitalchina.itil.config.extci.entity.AppAdministrator;
import com.digitalchina.itil.config.extlist.entity.MailForwardApply;
import com.digitalchina.itil.config.extlist.entity.MailGroup;
import com.digitalchina.itil.config.extlist.entity.WWWScanType;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;
import com.digitalchina.itil.require.entity.HRSAccountApply;
import com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis;
import com.digitalchina.itil.service.entity.ServiceItemProcess;

public class AccountServiceImpl extends BaseService implements AccountService{
	private AccountDao accountDao;

	public List<AccountSBUOfficer> findOfficer(String processNameDescription,
			String personScope) {
		return accountDao.findOfficer(processNameDescription,personScope);
	}

    public PersonFormalAccount findPersonAccount(String accountType,UserInfo applyUser) {
		return accountDao.findPersonAccount(accountType,applyUser);
		
	}
	public PersonFormalAccount findTelephoneAccount(String accountType,
			String telephoneNumber) {
		return accountDao.findTelephoneAccount(accountType,telephoneNumber);
	}

	public List findSpecailAccount(String accountType,
			UserInfo applyUser) {
		return accountDao.findSpecailAccount(accountType,applyUser);
	}

	public List findSpecailAccountByApplyId(String applyId) {
		return accountDao.findSpecailAccountByApplyId(applyId);
	}

	public List findAllPersonAccount(UserInfo userInfo) {
		return accountDao.findAllPersonAccount(userInfo);
	}

	public List findMailForwardAccount(UserInfo userInfo) {
		return accountDao.findMailForwardAccount(userInfo);
	}

	public List findAllSpecailAccount(UserInfo userInfo) {
		return accountDao.findAllSpecailAccount(userInfo);
	}

	public HRSAccountApply findHRSAccountByName(String accountName) {
		return accountDao.findHRSAccountByName(accountName);
	}

	public MailGroup findMailGroupByName(String accountName) {
		return accountDao.findMailGroupByName(accountName);
	}

	public MobileTelephoneApply findMobileTelephone(String accountType,
			UserInfo applyUser) {
		return accountDao.findMobileTelephone(accountType,applyUser);
	}

	public String importAccountDateExcel(String realPath,String accountType,String isnew) {
		return accountDao.importAccountDateExcel(realPath,accountType,isnew);
	}

	public List findSpecailAccountByType(String accountType) {
		return accountDao.findSpecailAccountByType(accountType);
	}

	public AccountModifyRecord findModifyRecord(String itCode,
			Integer accountFlag) {
		return accountDao.findModifyRecord(itCode, accountFlag);
	}

	public List findAllPersonAccountByAccountType(String accountType) {
		return accountDao.findAllPersonAccountByAccountType(accountType);
	}

	public SpecialAccount findSpecailAccountByAccountName(String accountType,
			String accountName) {
		return accountDao.findSpecailAccountByAccountName(accountType,accountName);
	}

	public AccountApplyMainTable findUserApply(ServiceItemProcess serviceItemProcess,
			UserInfo userInfo, String processType) {
		return accountDao.findUserApply(serviceItemProcess,userInfo,processType);
	}

	public List<AppAdministrator> findAppAdministratorByUser(UserInfo userInfo) {
		return accountDao.findAppAdministratorByUser(userInfo);
	}

	public Page findByPageQuerySameMailDept(String sameMailDeptName, int pageNo) {
		return accountDao.findByPageQuerySameMailDept(sameMailDeptName, pageNo);
	}

	public List<SystemAppAdmin> findAllAppAdministratorByUser(String itCode) {
		return accountDao.findAllAppAdministratorByUser(itCode);
	}

	public void runNewAppAdmin(AccountNewAppAdmin accountNewAppAdmin) {
		accountDao.runNewAppAdmin(accountNewAppAdmin);
	}

	public MailForwardApply findMailForwardByName(String accountName) {
		return accountDao.findMailForwardByName(accountName);
	}

	public Page findSystemAppAdminByPage(String itCode, int pageNo, int pageSize) {
		return accountDao.findSystemAppAdminByPage(itCode, pageNo, pageSize);
	}
	
	public Page findSpecailAccountByUser(String accountType,
			UserInfo applyUser,int pageNo,int pageSize) {
		return accountDao.findSpecailAccountByUser(accountType,
				applyUser,pageNo,pageSize);
	}
	public Page findWin7AccountByUser(Long accountId,
			UserInfo applyUser,int pageNo,int pageSize) {
		return accountDao.findWin7AccountByUser(accountId,
				applyUser,pageNo,pageSize);
	}

	public Page findByPageQueryHRSAccount(UserInfo userInfo, int pageNo,int pageSize) {
		return accountDao.findByPageQueryHRSAccount(userInfo, pageNo, pageSize);
	}

    public Page findByPageQueryMailGroup(String name, int pageNo, int pageSize) {
    	return accountDao.findByPageQueryMailGroup(name, pageNo, pageSize);
	}
    public Page findByPageQueryWin7PlatForm(String name, int pageNo, int pageSize) {
    	return accountDao.findByPageQueryWin7PlatForm(name, pageNo, pageSize);
	}
    public Page findByPageQueryDeviceType(String name, int pageNo, int pageSize) {
    	return accountDao.findByPageQueryDeviceType(name, pageNo, pageSize);
	}

	public DCContacts saveOrGetContacts(String itcode) {
		DCContacts employee = null;
		employee = (DCContacts) this.findUnique(DCContacts.class, "itcode", itcode.toUpperCase());
		if(employee==null&&itcode!= null&&itcode!= ""){
			employee = accountDao.saveUserToContacts(itcode);;
		}
		return employee;
	}
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public String importAccountDateExcel(String realPath) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page getAllTelephone(String number, int pageNo, int pageSize) {
		return accountDao.getAllTelephone(number,pageNo,pageSize);
	}
	public Page getAllDeptTelephone(String number, int pageNo, int pageSize) {
		return accountDao.getAllDeptTelephone(number,pageNo,pageSize);
	}
	
	public List<PersonFormalAccount> findAllTelephoneAccount(String string,
			String number) {
		return accountDao.findAllTelephoneAccount(string,number);
	}

	public List<PersonFormalAccount> getAllTelephoneAccount(String accountType,
			String telephoneNumber) {
		return accountDao.getAllTelephoneAccount(accountType,telephoneNumber);
	}

	public List<SpecialAccount> findAllSpecailAccountByAccountName(
			String accountType, String accountName) {
		return accountDao.findAllSpecailAccountByAccountName(accountType,accountName);
	}


	public Page findSpecailAccountByUsername(String string, String ownerName,
			String confirmUserName, int pageNo, int pageSize) {
		return  accountDao.findSpecailAccountByUsername(string,ownerName,confirmUserName,pageNo,pageSize);
	}

	public List<PersonFormalAccount> getAllDeptTelephoneAccount(
			String accountType, String telephoneNumber) {
		return accountDao.getAllDeptTelephoneAccount(accountType,telephoneNumber);
	}

	public List<AccountModifyDesc> findModifyDescByAccountId(String accountId,
			String string) {
		return accountDao.findModifyDescByAccountId(accountId,string);
	}

	public Page findByPageQueryAllSameMailDept(int pageNo) {
		return accountDao.findByPageQueryAllSameMailDept(pageNo);
	}

	public Page findByPageQuerySameMailDeptByName(String sameMailDeptName,
			int pageNo) {
		return accountDao.findByPageQuerySameMailDeptByName(sameMailDeptName,pageNo);
	}

	public Page getAllMailForward(String name, int pageNo, int pageSize) {
		return accountDao.getAllMailForward(name,pageNo,pageSize);
	}

	public List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(
			String dataId, String serviceItemId) {
		return accountDao.findServiceItemApplyAuditHis(dataId,serviceItemId);
	}
	
	public Page findAccountApply(String applyUser, String delegateApplyUser,
			String serviceItemProcess,String applyname, int pageNo, int pageSize) {
		return accountDao.findAccountApply(applyUser,delegateApplyUser,serviceItemProcess,applyname,pageNo,pageSize);
	}

	public Page findWWWDayDetail(String calendar, int start, int size) {
		return accountDao.selectWWWDayDetail(calendar, start, size);
	}
	public long findWWWMonth(){
		DateFormat df=new SimpleDateFormat("yyyyMM");
		return accountDao.selectWWWMonth(df.format(new Date()));
	}
	public Integer findWwwLimit(String itcode) {
		String wwwLimit = "";
		Long typeId = null;
		PersonFormalAccount	personAccount = accountDao.findPersonAccount("WWW’ ∫≈",itcode);
		if(personAccount!=null){
			typeId = personAccount.getWwwAccountValue()!=null?personAccount.getWwwAccountValue().getId():null;
		}else{
			SpecialAccount specialAccount = accountDao.findSpecailAccountByAccountName("¡Ÿ ±»À‘±WWW’ ∫≈",itcode);
			if(specialAccount!=null){
				typeId = specialAccount.getWwwAccountValue()!=null?specialAccount.getWwwAccountValue().getId():null;
			}
		}
		if(typeId!=null){
			WWWScanType type = (WWWScanType) this.find(WWWScanType.class, typeId.toString());
			if(type!=null){
				return type.getValue();
			}
		}
		return null;
	}
	public List<Object[]> findWWWMonthDetail(String yearAndMonth){
		return accountDao.selectWWWMonthDetail(yearAndMonth);
	}
	
}

package com.zsgj.itil.account.dao;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.account.entity.AccountModifyDesc;
import com.zsgj.itil.account.entity.AccountModifyRecord;
import com.zsgj.itil.account.entity.AccountNewAppAdmin;
import com.zsgj.itil.account.entity.AccountSBUOfficer;
import com.zsgj.itil.account.entity.DCContacts;
import com.zsgj.itil.account.entity.MobileTelephoneApply;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.entity.SystemAppAdmin;
import com.zsgj.itil.config.extci.entity.AppAdministrator;
import com.zsgj.itil.config.extlist.entity.MailForwardApply;
import com.zsgj.itil.config.extlist.entity.MailGroup;
import com.zsgj.itil.require.entity.AccountApplyMainTable;
import com.zsgj.itil.require.entity.HRSAccountApply;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemProcess;

/**
 * 帐号处理DAO
 * @Class Name AccountDao
 * @Author lee
 * @Create In Jan 19, 2010
 */
public interface AccountDao {
	/**
	 * 获取账号申请SBU审批人信息
	 * @Methods Name findOfficer
	 * @Create In Jun 1, 2009 By lee
	 * @param processName
	 * @param nodeName
	 * @return List<AccountSBUOfficer>
	 */
	List<AccountSBUOfficer> findOfficer(String processNameDescription,String personScope);
	
	
	/**
	 * 获取个人帐号信息
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    PersonFormalAccount findPersonAccount(String accountType,UserInfo applyUser);
    
    /**
	 * 获取个人座机信息通过 座机号码
	 * @Methods Name findTelephoneAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    PersonFormalAccount findTelephoneAccount(String accountType,String telephoneNumber);
    
    /**
	 * 获取个人帐号信息
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    List findAllPersonAccountByAccountType(String accountType);
    /**
	 * 获取特殊帐号信息
	 * @Methods Name findSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    List findSpecailAccount(String accountType,UserInfo applyUser);
    
    /**
	 * 获取特殊帐号信息 分页
	 * @Methods Name findSpecailAccountByUser
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    Page findSpecailAccountByUser(String accountType,UserInfo applyUser,int pageNo, int pageSize);
    
    Page findWin7AccountByUser(Long accountId,UserInfo applyUser,int pageNo, int pageSize);
    /**
	 * 获取特殊帐号信息根据帐号类型
	 * @Methods Name findSpecailAccountByType
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    List findSpecailAccountByType(String accountType);
    
    
    
    /**
	 * 获取特殊帐号信息通过申请编号
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    List findSpecailAccountByApplyId(String applyId);
    
    /**
	 * 获取员工所有个人帐号信息 
	 * @Methods Name findAllPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    List findAllPersonAccount(UserInfo userInfo);
    
    
    /**
	 * 获取员工所有特殊帐号信息 
	 * @Methods Name findAllSpecailAccount
	 * @Create In Aug 5, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    List findAllSpecailAccount(UserInfo userInfo);
    
    
    /**
	 * 获取邮件转发帐号信息 
	 * @Methods Name findAllPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    List findMailForwardAccount(UserInfo userInfo);
    
    
  
    
    /**
	 * 获取HRS帐号信息 通过帐号名
	 * @Methods Name findHRSAccountByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return HRSAccountApply
	 */
    HRSAccountApply findHRSAccountByName(String accountName);
    
    
    /**
	 * 获取邮件群组帐号信息 通过帐号名
	 * @Methods Name findMailGroupByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param accountName
	 * 
	 * @return MailGroup
	 */
    MailGroup findMailGroupByName(String accountName);
    
    /**
	 * 获取邮件转发帐号信息 通过帐号名
	 * @Methods Name findMailForwardByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param accountName
	 * 
	 * @return MailForwardApply
	 */
    MailForwardApply findMailForwardByName(String accountName);
    	
    	
    /**
	 * 获取员工手机信息
	 * @Methods Name findMobileTelephone
	 * @Create In Aug 20, 2009 By gaowen
	 * @param accountType
	 * @param applyUser
	 * @return MobileTelephoneApply
	 */
    	
    MobileTelephoneApply findMobileTelephone(String accountType,UserInfo applyUser);
    
    /**
     * 把excle的数据批量导入数据库
     * @author tongjp
     * @param realPath
     */
    String importAccountDateExcel(String realPath,String accountType,String isnew);
    String importAccountDateExcel(String realPath);
    
    AccountModifyRecord findModifyRecord(String itCode,Integer accountFlag);
    
    /**
	 * 获取特殊帐号信息
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    SpecialAccount findSpecailAccountByAccountName(String accountType,String accountName);
    
    AccountApplyMainTable findUserApply(ServiceItemProcess serviceItemProcess,UserInfo userInfo,String processType);
    
    /**
     * 获取跟用户有关的应用管理员类配置项（全部数据）
     * @Methods Name findAllAppAdministratorByUser
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return List<AppAdministrator>
     */
    List<SystemAppAdmin> findAllAppAdministratorByUser(String itCode);
    
    Page findSystemAppAdminByPage(String itCode,int pageNo, int pageSize);
    
    Page findByPageQuerySameMailDept(String sameMailDeptName,int pageNo) ;
    
    Page findByPageQueryHRSAccount(UserInfo userInfo,int pageNo,int pageSize) ;
    
    /**
     * 获取跟邮件群组名称（全部数据）
     * @Methods Name findByPageQueryMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    Page findByPageQueryMailGroup(String name,int pageNo,int pageSize) ;

	/**
	 * 通过Itcode获取用户信息
	 * @Methods Name findUserByItcode
	 * @Create In Jan 19, 2010 By lee
	 * @param itcode
	 * @return DCContacts
	 */
	DCContacts saveUserToContacts(String itcode);

	/**
	 * 获取应用管理员
	 * @Methods Name findAppAdministratorByUser
	 * @Create In Jan 19, 2010 By lee
	 * @param userInfo
	 * @return List<AppAdministrator>
	 */
	List<AppAdministrator> findAppAdministratorByUser(UserInfo userInfo);

	/**
	 * 新的应用管理员
	 * @Methods Name runNewAppAdmin
	 * @Create In Jan 19, 2010 By lee
	 * @param accountNewAppAdmin void
	 */
    void runNewAppAdmin(AccountNewAppAdmin accountNewAppAdmin);


	Page getAllTelephone(String number, int pageNo, int pageSize);
	Page getAllDeptTelephone(String number, int pageNo, int pageSize);
	/**
	 * 根据座机号码得到所有可用或不可用的座机帐号
	 * @Methods Name findAllApply
	 * @Create In Apr 7, 2010 By liuying
	 * @param serviceItemProcess
	 * @param processType
	 * @return List<AccountApplyMainTable>
	 */
	List<PersonFormalAccount> findAllTelephoneAccount(String string,
			String number);


	List<PersonFormalAccount> getAllTelephoneAccount(String accountType,
			String telephoneNumber);


	List<SpecialAccount> findAllSpecailAccountByAccountName(String accountType,
			String accountName);



	Page findSpecailAccountByUsername(String string, String ownerName,
			String confirmUserName, int pageNo, int pageSize);


	List<PersonFormalAccount> getAllDeptTelephoneAccount(String accountType,
			String telephoneNumber);


	Page findByPageQueryWin7PlatForm(String name, int pageNo, int pageSize);


	Page findByPageQueryDeviceType(String name, int pageNo, int pageSize);


	List<AccountModifyDesc> findModifyDescByAccountId(String accountId,
			String string);


	Page findByPageQueryAllSameMailDept(int pageNo);


	Page findByPageQuerySameMailDeptByName(String sameMailDeptName, int pageNo);


	Page getAllMailForward(String name, int pageNo, int pageSize);


	List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(String dataId,
			String serviceItemId);
			

	Page findAccountApply(String applyUser, String delegateApplyUser,
			String serviceItemProcess,String applyname, int pageNo, int pageSize);
			
	
	public Page selectWWWDayDetail(String calendar,int start,int size);
	public long selectWWWMonth(String yearAndMonth);
	/**
     * 获取正式帐号信息
     * @Methods Name findPersonAccount
     * @Create In Sep 13, 2010 By lee
     * @param accountType
     * @param accountName
     * @return PersonFormalAccount
     */
    PersonFormalAccount findPersonAccount(String accountType,String accountName);
    public List<Object[]> selectWWWMonthDetail(String yearAndMonth);
}

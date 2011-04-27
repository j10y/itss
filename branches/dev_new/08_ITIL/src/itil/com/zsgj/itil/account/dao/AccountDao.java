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
 * �ʺŴ���DAO
 * @Class Name AccountDao
 * @Author lee
 * @Create In Jan 19, 2010
 */
public interface AccountDao {
	/**
	 * ��ȡ�˺�����SBU��������Ϣ
	 * @Methods Name findOfficer
	 * @Create In Jun 1, 2009 By lee
	 * @param processName
	 * @param nodeName
	 * @return List<AccountSBUOfficer>
	 */
	List<AccountSBUOfficer> findOfficer(String processNameDescription,String personScope);
	
	
	/**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    PersonFormalAccount findPersonAccount(String accountType,UserInfo applyUser);
    
    /**
	 * ��ȡ����������Ϣͨ�� ��������
	 * @Methods Name findTelephoneAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    PersonFormalAccount findTelephoneAccount(String accountType,String telephoneNumber);
    
    /**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    List findAllPersonAccountByAccountType(String accountType);
    /**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    List findSpecailAccount(String accountType,UserInfo applyUser);
    
    /**
	 * ��ȡ�����ʺ���Ϣ ��ҳ
	 * @Methods Name findSpecailAccountByUser
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    Page findSpecailAccountByUser(String accountType,UserInfo applyUser,int pageNo, int pageSize);
    
    Page findWin7AccountByUser(Long accountId,UserInfo applyUser,int pageNo, int pageSize);
    /**
	 * ��ȡ�����ʺ���Ϣ�����ʺ�����
	 * @Methods Name findSpecailAccountByType
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    List findSpecailAccountByType(String accountType);
    
    
    
    /**
	 * ��ȡ�����ʺ���Ϣͨ��������
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    List findSpecailAccountByApplyId(String applyId);
    
    /**
	 * ��ȡԱ�����и����ʺ���Ϣ 
	 * @Methods Name findAllPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    List findAllPersonAccount(UserInfo userInfo);
    
    
    /**
	 * ��ȡԱ�����������ʺ���Ϣ 
	 * @Methods Name findAllSpecailAccount
	 * @Create In Aug 5, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    List findAllSpecailAccount(UserInfo userInfo);
    
    
    /**
	 * ��ȡ�ʼ�ת���ʺ���Ϣ 
	 * @Methods Name findAllPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    List findMailForwardAccount(UserInfo userInfo);
    
    
  
    
    /**
	 * ��ȡHRS�ʺ���Ϣ ͨ���ʺ���
	 * @Methods Name findHRSAccountByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return HRSAccountApply
	 */
    HRSAccountApply findHRSAccountByName(String accountName);
    
    
    /**
	 * ��ȡ�ʼ�Ⱥ���ʺ���Ϣ ͨ���ʺ���
	 * @Methods Name findMailGroupByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param accountName
	 * 
	 * @return MailGroup
	 */
    MailGroup findMailGroupByName(String accountName);
    
    /**
	 * ��ȡ�ʼ�ת���ʺ���Ϣ ͨ���ʺ���
	 * @Methods Name findMailForwardByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param accountName
	 * 
	 * @return MailForwardApply
	 */
    MailForwardApply findMailForwardByName(String accountName);
    	
    	
    /**
	 * ��ȡԱ���ֻ���Ϣ
	 * @Methods Name findMobileTelephone
	 * @Create In Aug 20, 2009 By gaowen
	 * @param accountType
	 * @param applyUser
	 * @return MobileTelephoneApply
	 */
    	
    MobileTelephoneApply findMobileTelephone(String accountType,UserInfo applyUser);
    
    /**
     * ��excle�����������������ݿ�
     * @author tongjp
     * @param realPath
     */
    String importAccountDateExcel(String realPath,String accountType,String isnew);
    String importAccountDateExcel(String realPath);
    
    AccountModifyRecord findModifyRecord(String itCode,Integer accountFlag);
    
    /**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    SpecialAccount findSpecailAccountByAccountName(String accountType,String accountName);
    
    AccountApplyMainTable findUserApply(ServiceItemProcess serviceItemProcess,UserInfo userInfo,String processType);
    
    /**
     * ��ȡ���û��йص�Ӧ�ù���Ա�������ȫ�����ݣ�
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
     * ��ȡ���ʼ�Ⱥ�����ƣ�ȫ�����ݣ�
     * @Methods Name findByPageQueryMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    Page findByPageQueryMailGroup(String name,int pageNo,int pageSize) ;

	/**
	 * ͨ��Itcode��ȡ�û���Ϣ
	 * @Methods Name findUserByItcode
	 * @Create In Jan 19, 2010 By lee
	 * @param itcode
	 * @return DCContacts
	 */
	DCContacts saveUserToContacts(String itcode);

	/**
	 * ��ȡӦ�ù���Ա
	 * @Methods Name findAppAdministratorByUser
	 * @Create In Jan 19, 2010 By lee
	 * @param userInfo
	 * @return List<AppAdministrator>
	 */
	List<AppAdministrator> findAppAdministratorByUser(UserInfo userInfo);

	/**
	 * �µ�Ӧ�ù���Ա
	 * @Methods Name runNewAppAdmin
	 * @Create In Jan 19, 2010 By lee
	 * @param accountNewAppAdmin void
	 */
    void runNewAppAdmin(AccountNewAppAdmin accountNewAppAdmin);


	Page getAllTelephone(String number, int pageNo, int pageSize);
	Page getAllDeptTelephone(String number, int pageNo, int pageSize);
	/**
	 * ������������õ����п��û򲻿��õ������ʺ�
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
     * ��ȡ��ʽ�ʺ���Ϣ
     * @Methods Name findPersonAccount
     * @Create In Sep 13, 2010 By lee
     * @param accountType
     * @param accountName
     * @return PersonFormalAccount
     */
    PersonFormalAccount findPersonAccount(String accountType,String accountName);
    public List<Object[]> selectWWWMonthDetail(String yearAndMonth);
}

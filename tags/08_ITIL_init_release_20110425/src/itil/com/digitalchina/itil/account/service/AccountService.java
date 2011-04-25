package com.digitalchina.itil.account.service;

import java.util.List;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.account.entity.AccountModifyDesc;
import com.digitalchina.itil.account.entity.AccountModifyRecord;
import com.digitalchina.itil.account.entity.AccountNewAppAdmin;
import com.digitalchina.itil.account.entity.AccountSBUOfficer;
import com.digitalchina.itil.account.entity.AccountType;
import com.digitalchina.itil.account.entity.DCContacts;
import com.digitalchina.itil.account.entity.MobileTelephoneApply;
import com.digitalchina.itil.account.entity.PersonFormalAccount;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.account.entity.SystemAppAdmin;
import com.digitalchina.itil.config.extci.entity.AppAdministrator;
import com.digitalchina.itil.config.extlist.entity.MailForwardApply;
import com.digitalchina.itil.config.extlist.entity.MailGroup;
import com.digitalchina.itil.require.entity.AccountApplyMainTable;
import com.digitalchina.itil.require.entity.HRSAccountApply;
import com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis;
import com.digitalchina.itil.service.entity.ServiceItemProcess;
/**
 * �˺Ź��������
 * @Class Name AccountService
 * @Author lee
 * @Create In Jun 1, 2009
 */
public interface AccountService {
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
    public PersonFormalAccount findPersonAccount(String accountType,UserInfo applyUser);
    
    /**
	 * ��ȡ����������Ϣͨ�� ��������
	 * @Methods Name findTelephoneAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    public PersonFormalAccount findTelephoneAccount(String accountType,String telephoneNumber);
    
    /**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return AccountApplys
	 */
    public List findAllPersonAccountByAccountType(String accountType);
    /**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    public List findSpecailAccount(String accountType,UserInfo applyUser);
    
    /**
	 * ��ȡ�����ʺ���Ϣ ��ҳ
	 * @Methods Name findSpecailAccountByUser
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    public Page findSpecailAccountByUser(String accountType,UserInfo applyUser,int pageNo, int pageSize);
    /**
     * 
     * @Methods Name findSpecailAccountByUser
     * @Create In Jun 21, 2010 By admin
     * @param accountId
     * @param applyUser
     * @param pageNo
     * @param pageSize
     * @return Page
     */
    public Page findWin7AccountByUser(Long accountId,UserInfo applyUser,int pageNo, int pageSize);
    /**
	 * ��ȡ�����ʺ���Ϣ�����ʺ�����
	 * @Methods Name findSpecailAccountByType
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    public List findSpecailAccountByType(String accountType);
    
    
    
    /**
	 * ��ȡ�����ʺ���Ϣͨ��������
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    public List findSpecailAccountByApplyId(String applyId);
    
    /**
	 * ��ȡԱ�����и����ʺ���Ϣ 
	 * @Methods Name findAllPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    public List findAllPersonAccount(UserInfo userInfo);
    
    
    /**
	 * ��ȡԱ�����������ʺ���Ϣ 
	 * @Methods Name findAllSpecailAccount
	 * @Create In Aug 5, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    public List findAllSpecailAccount(UserInfo userInfo);
    
    
    /**
	 * ��ȡ�ʼ�ת���ʺ���Ϣ 
	 * @Methods Name findAllPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return List
	 */
    public List findMailForwardAccount(UserInfo userInfo);
    
    
  
    
    /**
	 * ��ȡHRS�ʺ���Ϣ ͨ���ʺ���
	 * @Methods Name findHRSAccountByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param UserInfo
	 * 
	 * @return HRSAccountApply
	 */
    public HRSAccountApply findHRSAccountByName(String accountName);
    
    
    /**
	 * ��ȡ�ʼ�Ⱥ���ʺ���Ϣ ͨ���ʺ���
	 * @Methods Name findMailGroupByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param accountName
	 * 
	 * @return MailGroup
	 */
    public MailGroup findMailGroupByName(String accountName);
    
    /**
	 * ��ȡ�ʼ�ת���ʺ���Ϣ ͨ���ʺ���
	 * @Methods Name findMailForwardByName
	 * @Create In Jun 25, 2009 By gaowen
	 * @param accountName
	 * 
	 * @return MailForwardApply
	 */
    public MailForwardApply findMailForwardByName(String accountName);
    	
    	
    /**
	 * ��ȡԱ���ֻ���Ϣ
	 * @Methods Name findMobileTelephone
	 * @Create In Aug 20, 2009 By gaowen
	 * @param accountType
	 * @param applyUser
	 * @return MobileTelephoneApply
	 */
    	
    public MobileTelephoneApply findMobileTelephone(String accountType,UserInfo applyUser);
    
    /**
     * ��excle�����������������ݿ�
     * @author tongjp
     * @param realPath
     */
    public String importAccountDateExcel(String realPath,String accountType,String isnew);
    public String importAccountDateExcel(String realPath);
    
    public AccountModifyRecord findModifyRecord(String itCode,Integer accountFlag);
    
    /**
	 * ��ȡ�����ʺ���Ϣ
	 * @Methods Name findDomainAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @param serviceItemId
	 * @param applyUser
	 * @return List 
	 */
    public SpecialAccount findSpecailAccountByAccountName(String accountType,String accountName);
    
    public AccountApplyMainTable findUserApply(ServiceItemProcess serviceItemProcess,UserInfo userInfo,String processType);
    
    /**
     * ��ȡ���û��йص�Ӧ�ù���Ա�������ȫ�����ݣ�
     * @Methods Name findAllAppAdministratorByUser
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return List<AppAdministrator>
     */
    List<SystemAppAdmin> findAllAppAdministratorByUser(String itCode);
    
    Page findSystemAppAdminByPage(String itCode,int pageNo, int pageSize);
    
    public Page findByPageQuerySameMailDept(String sameMailDeptName,int pageNo) ;
    
    public Page findByPageQueryHRSAccount(UserInfo userInfo,int pageNo,int pageSize) ;
    
    /**
     * ��ȡ���ʼ�Ⱥ�����ƣ�ȫ�����ݣ�
     * @Methods Name findByPageQueryMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    public Page findByPageQueryMailGroup(String name,int pageNo,int pageSize) ;
    /**
     * ��ȡwin7ƽ̨���ƣ�ȫ�����ݣ�
     * @Methods Name findByPageQueryWin7PlatForm
     * @Create In December 10, 2009 By liuying
     * @param itCode
     * @return page
     */
    public Page findByPageQueryWin7PlatForm(String name,int pageNo,int pageSize) ;
    /**
     * ��ȡ�豸��ţ�ȫ�����ݣ�
     * @Methods Name findByPageQueryWin7PlatForm
     * @Create In December 10, 2009 By liuying
     * @param itCode
     * @return page
     */
    public Page findByPageQueryDeviceType(String name,int pageNo,int pageSize) ;
    /**
     * ͨ��Itcode��ȡ�û���Ϣ
     * @Methods Name saveOrGetContacts
     * @Create In Jan 19, 2010 By lee
     * @param itcode
     * @return DCContacts
     */
    DCContacts saveOrGetContacts(String itcode);

    /**
     * ͨ���绰����õ������ʺ�
     * @Methods Name getAllTelephone
     * @Create In Mar 25, 2010 By liuying
     * @param userName
     * @param pageNo
     * @param pageSize
     * @return Page
     */
	Page getAllTelephone(String number, int pageNo, int pageSize);
	/**
     * ͨ���绰����õ����������ʺ�
     * @Methods Name getAllDeptTelephone
     * @Create In Mar 25, 2010 By liuying
     * @param userName
     * @param pageNo
     * @param pageSize
     * @return Page
     */
	Page getAllDeptTelephone(String number, int pageNo, int pageSize);
	/**
	 * �����˺����õ�ת�����ʺ�
	 * @Methods Name getAllMailForward
	 * @Create In Aug 19, 2010 By liuying
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page getAllMailForward(String name, int pageNo, int pageSize);
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
	/**
	 * ������������õ����п��õ������ʺ�
	 * @Methods Name getAllTelephoneAccount
	 * @Create In Apr 7, 2010 By liuying
	 * @param serviceItemProcess
	 * @param processType
	 * @return List<AccountApplyMainTable>
	 */
	public List<PersonFormalAccount> getAllTelephoneAccount(String accountType,String telephoneNumber);
	/**
	 * ������������õ����п��õĲ��������ʺ�
	 * @Methods Name getAllDeptTelephoneAccount
	 * @Create In Apr 7, 2010 By liuying
	 * @param serviceItemProcess
	 * @param processType
	 * @return List<AccountApplyMainTable>
	 */
	public List<PersonFormalAccount> getAllDeptTelephoneAccount(String accountType,String telephoneNumber);


	List<SpecialAccount> findAllSpecailAccountByAccountName(String accountType,
			String accountName);


	Page findSpecailAccountByUsername(String string, String ownerName,
			String confirmUserName, int pageNo, int pageSize);


	List<AccountModifyDesc> findModifyDescByAccountId(String accountId,
			String string);


	Page findByPageQueryAllSameMailDept(int pageNo);


	Page findByPageQuerySameMailDeptByName(String sameMailDeptName, int pageNo);


	List<ServiceItemApplyAuditHis> findServiceItemApplyAuditHis(String dataId,
			String serviceItemId);
	

	Page findAccountApply(String applyUser, String delegateApplyUser,
			String serviceItemProcess,String applyname, int pageNo, int pageSize);
	
			
	public Page findWWWDayDetail(String calendar,int start,int size);
	public long findWWWMonth();
	/**
	 * ��ȡ�û�www�����Ϣ
	 * @Methods Name findWwwLimit
	 * @Create In Sep 13, 2010 By lee
	 */
	Integer findWwwLimit(String itcode);
	public List<Object[]> findWWWMonthDetail(String yearAndMonth);
}

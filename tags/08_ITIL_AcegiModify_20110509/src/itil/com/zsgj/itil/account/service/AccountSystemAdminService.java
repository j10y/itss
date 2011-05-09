package com.zsgj.itil.account.service;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.SameMailDept;
import com.zsgj.itil.account.entity.SystemAppAdmin;

public interface AccountSystemAdminService {
	 /**
     * ��ȡһ�����ţ�ȫ�����ݣ�
     * @Methods Name findAllSameMailDept
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return Page
     */
	 Page findSameMailDeptByPage(String name,int pageNo, int pageSize);
	 
	 /**
	     * ��ȡ�ʼ�Ⱥ�飨ȫ�����ݣ�
	     * @Methods Name findMailGroupByPage
	     * @Create In December 10, 2009 By gaowen
	     * @param itCode
	     * @return Page
	     */
     Page findMailGroupByPage(String name,int pageNo, int pageSize);
     
     /**
	     * ��ȡ�����ص�-�ʼ���������ȫ�����ݣ�
	     * @Methods Name findWorkSpaceByPage
	     * @Create In December 10, 2009 By gaowen
	     * @param itCode
	     * @return Page
	     */
    Page findWorkSpaceByPage(String name,int pageNo, int pageSize);
    
    
    /**
     * ��ȡƽ̨HR��ǩ��ȫ�����ݣ�
     * @Methods Name findPlatFormHrSignByPage
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return Page
     */
    Page findPlatFormHrSignByPage(String name,int pageNo, int pageSize);
    /**
     * ��ȡ�����ֻ���ǩ����Ϣ
     * @Methods Name findTelephoneCountSignByDeptName
     * @Create In Jul 1, 2010 By liuying
     * @param deptname
     * @param start
     * @param pageSize
     * @return Page
     */
	Page findTelephoneCountSignByDeptName(String deptname, int start,
			int pageSize);

	Page findAccountSBUOfficerByName(String deptname, int start, int pageSize);

	Page findMobileTelAllowance(String deptname, int start, int pageSize);

	Page findRAndBUserListByNameAndType(String deptname, String type,
			int start, int pageSize);

}

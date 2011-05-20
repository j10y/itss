package com.zsgj.itil.account.dao;
import com.zsgj.info.framework.dao.support.Page;

public interface AccountSystemAdminDao {
	/**
     * ��ȡһ�����ţ�ȫ�����ݣ�
     * @Methods Name findAllSameMailDept
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return List<SameMailDept>
     */
    Page findAllSameMailDept(String name, int pageNo, int pageSize) ;
    
    /**
     * ��ȡ�ʼ�Ⱥ�飨ȫ�����ݣ�
     * @Methods Name findMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    Page findMailGroup(String name, int pageNo, int pageSize) ;
    
    /**
     * ��ȡ�����ص�-�ʼ���������ȫ�����ݣ�
     * @Methods Name findMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    Page findWorkSapce(String name, int pageNo, int pageSize) ;
    
    /**
     * ��ȡƽ̨HR��ǩ��ȫ�����ݣ�
     * @Methods Name findMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param department ����/ƽ̨
     * @return page
     * 
     */
    Page findPlatFormHrSign(String department, int pageNo, int pageSize) ;
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

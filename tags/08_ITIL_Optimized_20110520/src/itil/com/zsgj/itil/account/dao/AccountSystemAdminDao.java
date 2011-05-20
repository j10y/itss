package com.zsgj.itil.account.dao;
import com.zsgj.info.framework.dao.support.Page;

public interface AccountSystemAdminDao {
	/**
     * 获取一级部门（全部数据）
     * @Methods Name findAllSameMailDept
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return List<SameMailDept>
     */
    Page findAllSameMailDept(String name, int pageNo, int pageSize) ;
    
    /**
     * 获取邮件群组（全部数据）
     * @Methods Name findMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    Page findMailGroup(String name, int pageNo, int pageSize) ;
    
    /**
     * 获取工作地点-邮件服务器（全部数据）
     * @Methods Name findMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return page
     */
    Page findWorkSapce(String name, int pageNo, int pageSize) ;
    
    /**
     * 获取平台HR加签（全部数据）
     * @Methods Name findMailGroup
     * @Create In December 10, 2009 By gaowen
     * @param department 部门/平台
     * @return page
     * 
     */
    Page findPlatFormHrSign(String department, int pageNo, int pageSize) ;
    /**
     * 获取座机手机加签人信息
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

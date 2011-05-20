package com.zsgj.itil.account.service;

import java.util.List;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.SameMailDept;
import com.zsgj.itil.account.entity.SystemAppAdmin;

public interface AccountSystemAdminService {
	 /**
     * 获取一级部门（全部数据）
     * @Methods Name findAllSameMailDept
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return Page
     */
	 Page findSameMailDeptByPage(String name,int pageNo, int pageSize);
	 
	 /**
	     * 获取邮件群组（全部数据）
	     * @Methods Name findMailGroupByPage
	     * @Create In December 10, 2009 By gaowen
	     * @param itCode
	     * @return Page
	     */
     Page findMailGroupByPage(String name,int pageNo, int pageSize);
     
     /**
	     * 获取工作地点-邮件服务器（全部数据）
	     * @Methods Name findWorkSpaceByPage
	     * @Create In December 10, 2009 By gaowen
	     * @param itCode
	     * @return Page
	     */
    Page findWorkSpaceByPage(String name,int pageNo, int pageSize);
    
    
    /**
     * 获取平台HR加签（全部数据）
     * @Methods Name findPlatFormHrSignByPage
     * @Create In December 10, 2009 By gaowen
     * @param itCode
     * @return Page
     */
    Page findPlatFormHrSignByPage(String name,int pageNo, int pageSize);
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

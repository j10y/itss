package com.digitalchina.itil.account.webservice;

import java.util.List;
import java.util.Map;

/**
 * 用招聘单对接提供接口
 * @Class Name ITILToHRJobData
 * @Author lee
 * @Create In Jan 19, 2010
 */
public interface ITILToHRJobData {
	
	/**
	 * 获取工作地点
	 * @Methods Name getWorkSpace
	 * @Create In Jan 19, 2010 By lee
	 * @return String
	 */
	public String getWorkSpace();
	/**
	 * 获取邮件等价名部门
	 * @Methods Name getSameMailDept
	 * @Create In Jan 19, 2010 By lee
	 * @param sameMailDeptName
	 * @param pageNo
	 * @return String
	 */
	public String getSameMailDept(String sameMailDeptName,int pageNo);
	/**
	 * 招聘单调用接口提交新员工IT帐号申请
	 * @Methods Name hRJobStartItilAccount
	 * @Create In Jan 19, 2010 By lee
	 * @param josnData
	 * @return String
	 */
	public String hRJobStartItilAccount(String josnData);
}

package com.digitalchina.itil.event.webservice.impl;
import com.digitalchina.itil.event.webservice.ConnectPmcWS;


public class ConnectPmcWSImpl implements ConnectPmcWS {


	public String connectPmcService(String userCode, String roleId,
			String managerCode, String departmentCode, String superiorDeptCode,
			String attachPlatCode, String customerName, 
            String customerShortName, String projectName, String projectCode,
            String taxRate, String signCompanyName, String planSignMoney, String finalUserName, 
            String planRealRate){  	
		return "ok";

	}
	
	/**
	 * 判断用户是否在PMC中存在
	 * 返回：存在或不存在的提示信息
	 */
	public String checkUserLogin(String userCode) {
		return "success";	
	}
	
	/**
	 * 校验前台用户是否有创建项目权限
	 * 返回：
	 */
	public String validateUserRight(String userCode) {
		return "success";
   }
	
	public String sayHello() {
		return "HELLO WORLD!"; 
	} 
	
}

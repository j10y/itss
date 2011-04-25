package com.digitalchina.itil.event.webservice;


public interface ConnectPmcWS {

	public String connectPmcService(String userCode, String roleId,
			String managerCode, String departmentCode, String superiorDeptCode,
			String attachPlatCode, String customerName,
			String customerShortName, String projectName, String projectCode,
			String taxRate,String signCompanyName, String planSignMoney, 
			String finalUserName,String planRealRate);

	public String checkUserLogin(String userCode);
	
	String sayHello();

	public String validateUserRight(String userCode);
}

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
	 * �ж��û��Ƿ���PMC�д���
	 * ���أ����ڻ򲻴��ڵ���ʾ��Ϣ
	 */
	public String checkUserLogin(String userCode) {
		return "success";	
	}
	
	/**
	 * У��ǰ̨�û��Ƿ��д�����ĿȨ��
	 * ���أ�
	 */
	public String validateUserRight(String userCode) {
		return "success";
   }
	
	public String sayHello() {
		return "HELLO WORLD!"; 
	} 
	
}

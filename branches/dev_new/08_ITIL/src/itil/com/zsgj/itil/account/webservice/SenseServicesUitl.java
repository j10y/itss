package com.zsgj.itil.account.webservice;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.UserInfoService;

/**
 * ��Tivoli�Խӽӿڹ�����
 * @Class Name SenseServicesUitl
 * @Author lee
 * @Create In Aug 20, 2010
 */
public class SenseServicesUitl {
	
	/**
	 * ��ȡ�û���Ϣ
	 * @Methods Name getUserInfo
	 * @Create In Aug 20, 2010 By lee
	 * @param itcode
	 * @return Map
	 */
	public Map getUserInfo(String itcode){
		Map userMap = new HashMap();
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String result = service.employeeInfo(itcode);
        try {
//			Document document = DocumentHelper.parseText(result);
//			Element e = document.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				Element userElement = e.element("employee");
//				userMap.put("userName", userElement.elementText("ITCode"));
//				userMap.put("realName", userElement.elementText("cn"));
//				userMap.put("itcode", userElement.elementText("ITCode").toUpperCase());
//				userMap.put("department", Long.valueOf(userElement.elementText("departmentNumber")));
//				userMap.put("costCenterCode", userElement.elementText("dcCostCenterCode"));
//				userMap.put("employeeCode", userElement.elementText("employeeNumber"));
//			}
			
			UserInfoService uis = (UserInfoService)ContextHolder.getBean("userInfoService");
			UserInfo ui = uis.findUserInfoByUserName(itcode);
			if(ui != null){
				userMap.put("userName", ui.getUserName());
				userMap.put("realName", ui.getRealName());
				userMap.put("itcode",ui.getUserName());
				userMap.put("department", ui.getDepartCode());
				userMap.put("costCenterCode", ui.getCostCenterCode());
				userMap.put("employeeCode",ui.getEmployeeCode());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userMap;
	}
	
	/**
	 * ��ʼ����ʱԱ���˺���Ϣ
	 * @Methods Name initTempUser
	 * @Create In Aug 20, 2010 By lee
	 * @param userCnName	������
	 * @param userDemoName	������ƴ��
	 * @param managerItcode	�ʺ�������ITCODE
	 * @return String
	 */
	public String initTempUser(String userCnName,String userDemoName,String managerItcode){
//		String initItcode = "";
//		Document document = DocumentHelper.createDocument();
//		Element catalogElement = document.addElement("tempPerson");
//		Element nameElement = catalogElement.addElement("name");
//		nameElement.setText(userCnName);
//		Element managerElement = catalogElement.addElement("ITCodeManager");
//		managerElement.setText(managerItcode);
//		Element itcodeElement = catalogElement.addElement("ITCodeBase");
//		itcodeElement.setText(userDemoName);
//		String paramStr = document.asXML();
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.addTempPerson(paramStr);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				initItcode = e.elementText("ITCode");
//			}else{
//				System.out.println("������ʱ��Ա��Ϣʧ�ܣ�"+message);
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		return initItcode;
		return "success";
	}
	/**
	 * �޸���ʱ�ʺ�������
	 * @Methods Name modifyTempUserManager
	 * @Create In Aug 20, 2010 By lee
	 * @param tempUserItcode
	 * @param managerItcode
	 * @return String
	 */
	public String modifyTempUserManager(String tempUserItcode,String managerItcode){
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.modifyTempPersonITCodeManager(tempUserItcode,managerItcode);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				return "success";
//			}else{
//				System.out.println("�޸���ʱ��Ա��Ϣʧ�ܣ�"+message);
//				return message;
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
		return "success";
	}
	/**
	 * ɾ����ʱ��Ա�˺�
	 * @Methods Name removeTempUser
	 * @Create In Aug 20, 2010 By lee
	 * @param tempUserItcode
	 * @return String
	 */
	public String removeTempUser(String tempUserItcode){
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.deleteTempPerson(tempUserItcode);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				return "success";
//			}else{
//				System.out.println("ɾ����ʱ��Ա��Ϣʧ�ܣ�"+message);
//				return message;
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
		return "success";
	}
	/**
	 * ����WWW�ʺ�
	 * @Methods Name addWWWAccount
	 * @Create In Aug 20, 2010 By lee
	 * @param itcode	�ʺ�ʹ����ITCODE
	 * @return String
	 */
	public String addWWWAccount(String itcode){
//		Document document = DocumentHelper.createDocument();
//		Element catalogElement = document.addElement("request");
//		Element itcodeElement = catalogElement.addElement("ITCode");
//		itcodeElement.setText(itcode);
//		Element groupElement = catalogElement.addElement("group");
//		groupElement.setText("www");
//		String paramStr = document.asXML();
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.addWWWAccount(paramStr);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				return "success";
//			}else{
//				System.out.println("�½�WWW�ʺ���Ϣʧ�ܣ�"+message);
//				return message;
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
		System.out.println("SenseServicesUitl-addWWWAccount ����WWW�ʺ�----------�˷�����ͬ���������⣬����Ŀǰûʹ��");
		return "success";
	}
	/**
	 * ɾ��WWW�ʺ�
	 * @Methods Name deleteWWWAccount
	 * @Create In Aug 20, 2010 By lee
	 * @param itcode
	 * @return String
	 */
	public String deleteWWWAccount(String itcode){
//		Document document = DocumentHelper.createDocument();
//		Element catalogElement = document.addElement("request");
//		Element itcodeElement = catalogElement.addElement("ITCode");
//		itcodeElement.setText(itcode);
//		Element groupElement = catalogElement.addElement("group");
//		groupElement.setText("www");
//		String paramStr = document.asXML();
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.deleteWWWAccount(paramStr);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				return "success";
//			}else{
//				System.out.println("ɾ��WWW�ʺ���Ϣʧ�ܣ�"+message);
//				return message;
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
		System.out.println("SenseServicesUitl-addWWWAccount ɾ��WWW�ʺ�----------�˷�����ͬ���������⣬����Ŀǰûʹ��");
		return "success";
	}
	/**
	 * ����MSN�ʺ�
	 * @Methods Name addMSNAccount
	 * @Create In Sep 1, 2010 By lee
	 * @param itcode
	 * @return String
	 */
	public String addMSNAccount(String itcode){
//		Document document = DocumentHelper.createDocument();
//		Element catalogElement = document.addElement("request");
//		Element itcodeElement = catalogElement.addElement("ITCode");
//		itcodeElement.setText(itcode);
//		Element groupElement = catalogElement.addElement("group");
//		groupElement.setText("msn");
//		String paramStr = document.asXML();
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.addWWWAccount(paramStr);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				return "success";
//			}else{
//				System.out.println("�½�MSN�ʺ���Ϣʧ�ܣ�"+message);
//				return message;
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
		return "success";
	}

	/**
	 * ɾ��MSN�ʺ�
	 * @Methods Name deleteMSNAccount
	 * @Create In Sep 1, 2010 By lee
	 * @param itcode
	 * @return String
	 */
	public String deleteMSNAccount(String itcode){
//		Document document = DocumentHelper.createDocument();
//		Element catalogElement = document.addElement("request");
//		Element itcodeElement = catalogElement.addElement("ITCode");
//		itcodeElement.setText(itcode);
//		Element groupElement = catalogElement.addElement("group");
//		groupElement.setText("msn");
//		String paramStr = document.asXML();
//		SenseServicesClient client = new SenseServicesClient();
//        SenseServicesPortType service = client.getService();
//        String resultStr = service.deleteWWWAccount(paramStr);
//        try {
//			Document resultDocument = DocumentHelper.parseText(resultStr);
//			Element e = resultDocument.getRootElement();
//			String returnCode = e.elementText("returnCode");
//			String message = e.elementText("message");
//			if("success".equals(returnCode)){
//				return "success";
//			}else{
//				System.out.println("ɾ��MSN�ʺ���Ϣʧ�ܣ�"+message);
//				return message;
//			}
//		} catch (DocumentException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		return null;
		return "success";
	}
	public static void main(String[] args) {
		SenseServicesUitl tt = new SenseServicesUitl();
		System.out.println(tt.getUserInfo("lushan"));
        		System.exit(0);
    }
}

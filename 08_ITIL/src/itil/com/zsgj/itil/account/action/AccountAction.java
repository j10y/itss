package com.zsgj.itil.account.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableRelationService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelTableService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.MailServer;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.SameMailDept;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.entity.UserType;
import com.zsgj.info.framework.security.entity.WorkSpace;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.itil.account.entity.AccountModifyDesc;
import com.zsgj.itil.account.entity.AccountModifyRecord;
import com.zsgj.itil.account.entity.AccountType;
import com.zsgj.itil.account.entity.DCContacts;
import com.zsgj.itil.account.entity.DeviceType;
import com.zsgj.itil.account.entity.ITPassword;
import com.zsgj.itil.account.entity.MobileTelephoneApply;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.entity.SystemAppAdmin;
import com.zsgj.itil.account.entity.Win7PlatForm;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.account.webservice.HrInfoService;
import com.zsgj.itil.account.webservice.HrInfoServiceLocator;
import com.zsgj.itil.account.webservice.HrInfoServiceSoap_PortType;
import com.zsgj.itil.account.webservice.SenseServicesUitl;
import com.zsgj.itil.config.extlist.entity.AR_DrawSpace;
import com.zsgj.itil.config.extlist.entity.HRSOperationScope;
import com.zsgj.itil.config.extlist.entity.HRSUserRight;
import com.zsgj.itil.config.extlist.entity.MailForwardApply;
import com.zsgj.itil.config.extlist.entity.MailGroup;
import com.zsgj.itil.config.extlist.entity.MailVolume;
import com.zsgj.itil.config.extlist.entity.MobileTelAllowance;
import com.zsgj.itil.config.extlist.entity.NotesIDFile;
import com.zsgj.itil.config.extlist.entity.TelephoneType;
import com.zsgj.itil.config.extlist.entity.WWWScanType;
import com.zsgj.itil.require.entity.AccountApplyMainTable;
import com.zsgj.itil.require.entity.HRSAccountApply;
import com.zsgj.itil.require.service.RequireSIService;
import com.zsgj.itil.service.entity.Constants;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.service.ServiceItemProcessService;
import com.zsgj.itil.service.service.ServiceItemService;

/**
 * �˺Ź���Aciton
 * 
 * @Class Name AccountAction
 * @Author gaowen
 * @Create In May 20, 2009
 */
public class AccountAction extends BaseAction {
	private PageManager pageManager = (PageManager) ContextHolder.getBean("pageManager");
	private MetaDataManager mdm = (MetaDataManager) super.getBean("metaDataManager");
	private PagePanelService pagePanelService = (PagePanelService) getBean("pagePanelService");
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private PagePanelTableService ppts = (PagePanelTableService) getBean("pagePanelTableService");
	private PagePanelTableRelationService pptrs = (PagePanelTableRelationService) getBean("pagePanelTableRelationService");
	private SystemColumnService systemColumnService = (SystemColumnService) getBean("systemColumnService");
	private AccountService accountService = (AccountService) getBean("accountService");
	private ServiceItemProcessService serviceItemProcessService = (ServiceItemProcessService) getBean("serviceItemProcessService");
	private SystemMainTableService systemMainTableService = (SystemMainTableService) getBean("systemMainTableService");
	private ServiceItemService serviceItemService = (ServiceItemService) getBean("serviceItemService");
	private RequireSIService requireSIService = (RequireSIService) getBean("requireSIService");
	private static Service baseBervice = (Service) ContextHolder.getBean("baseService");
	private DefinitionService ds = (DefinitionService) ContextHolder.getBean("definitionService");

	// ///////////////////////////////////////////�����ʺ�����START//////////////////////////////////
	/**
	 * ��ʼ���˺Ź��������˼��������
	 * 
	 * @Methods Name initNewApplyDate
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String initNewApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String itCode = curUser.getItcode();
		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
        try {
        	//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (StringUtils.isNotBlank(mobileTelephone)&&StringUtils.isBlank(telephone)) {
				bWrapper.setPropertyValue("delegateApplyTel", mobileTelephone);
			}  else if (StringUtils.isNotBlank(telephone)&&StringUtils.isBlank(mobileTelephone)) {
				bWrapper.setPropertyValue("delegateApplyTel", telephone);
			}  else if (StringUtils.isNotBlank(telephone)&&StringUtils.isNotBlank(mobileTelephone)) {
				bWrapper.setPropertyValue("delegateApplyTel", telephone + "/"
						+ mobileTelephone);
			} else {
				bWrapper.setPropertyValue("delegateApplyTel", "");
			}

		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}

		// bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("delegateApplyUser", curUser);
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);

		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ���˺Ź��������˼��������
	 * 
	 * @Methods Name initNewApplyDate
	 * @Create In AUG 20, 2009 By gaowen
	 * @return String
	 */
	public String initUserInfoData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);

		try {
			String costCenter = user.getCostCenterCode();
			String employeeCode = user.getEmployeeCode();
			String itCode = user.getItcode();
			String titleCode="";
			Double allowance = 0.00;
			if(user.getTitleCode()!=null){
				titleCode = user.getTitleCode().toString();
				MobileTelAllowance mobileTelAllowance = (MobileTelAllowance) getService()
				.findUnique(MobileTelAllowance.class, "postCode", titleCode);
				if (mobileTelAllowance != null) {
					allowance = mobileTelAllowance.getAllowance();
				}
			}
			String postName = user.getPostName();
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			StringBuilder result=new StringBuilder();
		    if(telephone!=null&&telephone.trim().length()!=0&&mobileTelephone!=null&&mobileTelephone.trim().length()!=0){
		      result.append(telephone);
		      result.append("/");
		      result.append(mobileTelephone);
		     }else if((telephone==null||telephone.trim().length()==0)&&mobileTelephone!=null&&mobileTelephone.trim().length()!=0)
		     {   
		    	 result.append(mobileTelephone);
		      }
		     else if (telephone!=null&&telephone.trim().length()!=0&&(mobileTelephone==null||mobileTelephone.trim().length()==0))
		      {result.append(telephone);
		      
		      }
		    Long userType=null;
		    try{
			   userType = user.getUserType().getId();
		    }catch(Exception e){
		    	System.out.println("��ʼ��Ա�����ʧ��");
		    }
			Long PersonScope=null;
			try{
			PersonScope = user.getPersonnelScope().getId();
			}catch (Exception e) {
				System.out.println("��ʼ���û������ӷ�Χʧ��");
			}
			String departMent = user.getDepartment().getDepartName();
			String mailServer = "";
			try {
				mailServer = user.getMailServer().getId().toString();
			} catch (Exception e) {
				System.out.println("��ʼ���û��ʼ�������ʧ��");
			}
			String json = "{success:" + true + ",userType:" + userType
					+ ",PersonScope:" + PersonScope + ",costCenter:'"
					+ costCenter + "',employeeCode:'" + employeeCode
					+ "',departMent:'" + departMent + "',telephone:'" + result.toString()
					+ "',titleCode:'" + titleCode + "',postName:'" + postName
					+ "',mailServer:'" + mailServer + "',allowance:'"
					+ allowance + "'}";

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;

			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * ��ʼ���˺Ź��������˼��������
	 * 
	 * @Methods Name initNewApplyDate
	 * @Create In AUG 20, 2009 By gaowen
	 * @return String
	 */
	public String initUserInfoNewITApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);

		try {
			String costCenter = user.getCostCenterCode();
			String employeeCode = user.getEmployeeCode();
			String itCode = user.getItcode();
			Long titleCode = user.getTitleCode();
			String postName = user.getPostName();
		    String telephone = user.getTelephone();
			String mobileTelephone = user.getMobilePhone();
			StringBuilder result=new StringBuilder();
		    if(telephone!=null&&telephone.trim().length()!=0&&mobileTelephone!=null&&mobileTelephone.trim().length()!=0){
		      result.append(telephone);
		      result.append("/");
		      result.append(mobileTelephone);
		     }else if((telephone==null||telephone.trim().length()==0)&&mobileTelephone!=null&&mobileTelephone.trim().length()!=0)
		     {   
		    	 result.append(mobileTelephone);
		      }
		     else if (telephone!=null&&telephone.trim().length()!=0&&(mobileTelephone==null||mobileTelephone.trim().length()==0))
		      {
		    	 result.append(telephone);
		      }
		    Long userType=null;
		    try{
			   userType = user.getUserType().getId();
		    }catch(Exception e){
		    	System.out.println("��ʼ��Ա�����ʧ��");
		    }
			Long PersonScope=null;
			try{
			PersonScope = user.getPersonnelScope().getId();
			}catch (Exception e) {
				System.out.println("��ʼ���û������ӷ�Χʧ��");
			}
			String departMent = user.getDepartment().getDepartName();
			String mailServer = "";
			try {
				mailServer = user.getMailServer().getId().toString();
			} catch (Exception e) {
				System.out.println("��ʼ���û��ʼ�������ʧ��");
			}
			String json = "{success:" + true + ",userType:" + userType
					+ ",PersonScope:" + PersonScope + ",costCenter:'"
					+ costCenter + "',employeeCode:'" + employeeCode
					+ "',departMent:'" + departMent + "',telephone:'" + result.toString()
					+ "',titleCode:'" + titleCode + "',postName:'" + postName
					+ "',mailServer:'" + mailServer  + "'}";

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;

			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��Ա�����ű�������˼��������
	 * 
	 * @Methods Name initDeptChangeUser
	 * @Create In AUG 20, 2009 By gaowen
	 * @return String
	 */
	public String initDeptChangeUser() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		String itCode = curUser.getItcode();
		DCContacts employee = null;
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (telephone.equals("")) {
				bWrapper.setPropertyValue("applyUserTel", mobileTelephone);
			} else if (mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("applyUserTel", telephone);
			} else if (telephone.equals("") && mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("applyUserTel", "");
			} else {
				bWrapper.setPropertyValue("applyUserTel", telephone + "/"
						+ mobileTelephone);
			}
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}
		Department dept = employee.getDepartment();

		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		Map<String, Object> dcMap = metaDataManager.getFormDataForEdit(
				employee, "itil_ac_DCContacts");
		dataMap.putAll(userMap);
		dataMap.putAll(dcMap);
		dataMap.put("itil_ac_DCContacts$department", dept.getDepartName());

		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���沿�ű����ʼ���ݸ�
	 * 
	 * @Methods Name saveDeptChangeDraff
	 * @Create In AUG 2, 2009 By gaowen
	 * @return String
	 */
	public String saveDeptChangeDraff() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String acountId = request.getParameter("id");// �õ����������
		String ifHold = request.getParameter("ifHold");
		String remark = request.getParameter("remark");
		String curId = request.getParameter("curId");
		String accountType = request.getParameter("accountType");
		if (accountType.equals("�ʼ��ʺ�") || accountType.equals("���ʺ�")) {
			ifHold = "1";
		}
		String wwwValue = request.getParameter("wwwValue");
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, curId);
		String id = aa.getId().toString();// �õ���ʵ��ID
		String name = aa.getName();// �õ�������
		PersonFormalAccount account = (PersonFormalAccount) getService().find(
				PersonFormalAccount.class, acountId);
		if (account.getAccountState().equals("1")) {
			PersonFormalAccount newAccount = new PersonFormalAccount();
			String[] ignoreProperties = { "id" };
			BeanUtils.copyProperties(account, newAccount, ignoreProperties);
			if (accountType.equals("WWW�ʺ�") && wwwValue != null
					&& !wwwValue.equals("")) {
				WWWScanType type = (WWWScanType) getService().find(
						WWWScanType.class, wwwValue);
				newAccount.setWwwAccountValue(type);
				newAccount.setIfSysn(1);
			}
			newAccount.setOlodApplyAccount(account);
			newAccount.setApplyId(aa);
			newAccount.setIfHold(Integer.valueOf(ifHold));
			newAccount.setRemarkDesc(remark);
			newAccount.setAccountState("0");
			PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
					.save(newAccount);
		} else {
			account.setIfHold(Integer.valueOf(ifHold));
			account.setRemarkDesc(remark);
		}
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡԱ�����ű������ ҳ����������� new
	 * 
	 * @Methods getDeptChangeDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getDeptChangeDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��
		List<PersonFormalAccount> accounts = getService().find(
				PersonFormalAccount.class, "applyId", mainObj);
		UserInfo curUser = mainObj.getApplyUser();
		//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
		//DCContacts employee = (DCContacts) getService().findUnique(
		//		DCContacts.class, "itcode", itCode);
		DCContacts employee = accountService.saveOrGetContacts(curUser.getItcode());
		//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);

		Map<String, Object> paMap = metaDataManager.getFormDataForEdit(
				employee, "itil_ac_DCContacts");

		Department dept = employee.getDepartment();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		for (PersonFormalAccount ac : accounts) {
			if (ac.getAccountType().getAccountType().equals("WWWAccount")
					&& ac.getIfSysn() != null) {
				dataMap.put("itil_ac_PersonFormalAccount$wwwAccountValue", ac
						.getWwwAccountValue().getId());
			}
		}
		dataMap.putAll(userMap);
		dataMap.putAll(paMap);
		dataMap.put("itil_ac_DCContacts$department", dept.getDepartName());
		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��������ʽ�ʺ������˼�������� new
	 * 
	 * @Methods Name initPersonAccountApplyData
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String initPersonAccountApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("delegateApplyUser", curUser);
		bWrapper.setPropertyValue("mail",curUser.getEmail());
		String itCode = curUser.getItcode();
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (StringUtils.isBlank(telephone)) {
				bWrapper.setPropertyValue("delegateApplyTel", mobileTelephone);
				bWrapper.setPropertyValue("applyUserTel", mobileTelephone);
			} else if (StringUtils.isBlank(mobileTelephone)) {
				bWrapper.setPropertyValue("delegateApplyTel", telephone);
				bWrapper.setPropertyValue("applyUserTel", telephone);
			} else if (StringUtils.isBlank(telephone) && StringUtils.isBlank(mobileTelephone)) {
				bWrapper.setPropertyValue("delegateApplyTel", "");
				bWrapper.setPropertyValue("applyUserTel", "");
			} else {
				bWrapper.setPropertyValue("delegateApplyTel", telephone + "/"
						+ mobileTelephone);
				bWrapper.setPropertyValue("applyUserTel", telephone + "/"
						+ mobileTelephone);
			}

		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		//modify by liuying for ��������û�titleCodeʱΪ��ʱ���쳣 at 20100422 start
		String postCode = "";
		MobileTelAllowance allowance =null;
		if(curUser.getTitleCode()!=null){
			postCode=curUser.getTitleCode().toString();
			allowance = (MobileTelAllowance) getService()
			.findUnique(MobileTelAllowance.class, "postCode", postCode);
		}
		//modify by liuying for ��������û�titleCodeʱΪ��ʱ���쳣 at 20100422 end
		if (allowance != null) {
			dataMap.put("itil_ac_MobileTelephoneApply$allowance", allowance
					.getAllowance());
		}
		UserType ut = curUser.getUserType();
		dataMap.putAll(userMap);

		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��������ʽ�ʺ�ɾ���������˼�������� new
	 * 
	 * @Methods Name initPersonAccountDeleteData
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String initPersonAccountDeleteData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		// bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("delegateApplyUser", curUser);
		bWrapper.setPropertyValue("mail",curUser.getEmail());
		String itCode = curUser.getItcode();
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (telephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", mobileTelephone);
			} else if (mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", telephone);
			} else if (telephone.equals("") && mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", "");
			} else {
				bWrapper.setPropertyValue("delegateApplyTel", telephone + "/"
						+ mobileTelephone);
			}
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		// Map<String, Object> userMap =
		// metaDataManager.getFormDataForEdit(curUser,"sUserInfos");
		// dataMap.putAll(userMap);
		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��HRS��ʽ�ʺ�ɾ���������˼�������� new
	 * 
	 * @Methods Name initHRSAccountApplyData
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String initHRSAccountApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();

		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("userOwner", curUser.getItcode());
		String itCode = curUser.getItcode();
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (telephone.equals("")) {
				bWrapper.setPropertyValue("applyTel", mobileTelephone);
			} else if (mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("applyTel", telephone);
			} else if (telephone.equals("") && mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("applyTel", "");
			} else {
				bWrapper.setPropertyValue("applyTel", telephone + "/"
						+ mobileTelephone);
			}
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}

		// bWrapper.setPropertyValue("delegateApplyUser", curUser);
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������ʽ�˺������ʼ�ݸ�
	 * 
	 * @Methods Name saveApplyDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		List<PagePanelTableRelation> relations = pptrs
				.findRelationsByPanel(panel);
		List<SystemMainTable> ftables = new ArrayList();
		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(PersonFormalAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
			}
		}
		temp.put("accountState", "0");
		temp.put("accountType", at);
		temp.put("cardState", 0);
		temp.put("applyId", mainObject);
		temp.put("accountowner", acUser);

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ������ʽ�˺����� ҳ����������� new
	 * 
	 * @Methods getPersonApplyDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getPersonApplyDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("VPNAccount")) {
				String rights = acc.getRemarkDesc();
				if(rights!=null&&rights.trim().length()!=0){
				String rightsDesc = rights.substring(1, rights.length() - 1);
				String[] right = rightsDesc.split(",");
				for (String r : right) {
					String s = r.substring(1, r.length() - 1);
					if (s.equals("����SAPϵͳ")) {
						dataMap.put("sap", 1);

					} else if (s.equals("ʹ��IPsoftphone")) {
						dataMap.put("iPsoftphone", 1);

					} else if (s.equals("���ð칫")) {
						dataMap.put("office", 1);
					} else {
						dataMap.put("office", 1);
					}
				  }
				}
			}
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");
			//add by liuying at 20100528 for �޸�VPN��ʾȨ��json��������� start
			if(acc.getAccountType().getAccountType().equals("VPNAccount")){
				tempMap.put("itil_ac_PersonFormalAccount$remarkDesc", "");//
			}
			//add by liuying at 20100528 for �޸�VPN��ʾȨ��json��������� end

			dataMap.putAll(tempMap);

		}
		Map<String, Object> userMap = new HashMap();
		if (applyUser != null) {
			userMap = metaDataManager.getFormDataForEdit(applyUser,
					"sUserInfos");
		}
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ������ʽ�˺����� ҳ����������� new
	 * 
	 * @Methods getEmployeeQuitDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getEmployeeQuitDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("ERPAccount")) {
				Map<String, Object> tempMap = metaDataManager
						.getFormDataForEdit(acc, "itil_ac_PersonFormalAccount");
				dataMap.putAll(tempMap);

			}
		}
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������˺�������Ϣ
	 * 
	 * @Methods Name saveAccountInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveAccountInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		String pw = request.getParameter("password");
		Date currentDate = DateUtil.getCurrentDate();
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aam);
		for (PersonFormalAccount personAccount : account) {
			personAccount.setAccountName(user.getItcode().toLowerCase());
			if(pw!=null&&!"".equals(pw)){
				personAccount.setPassword(pw);
			}else{
				personAccount.setPassword("123");
			}
			personAccount.setCreateDate(currentDate);
			personAccount.setRightsDesc(rightDesc);
			// personAccount.setAccountState("1");
			personAccount.setIfHold(1);
			personAccount.setCardState(1);
			PersonFormalAccount pa = (PersonFormalAccount) getService().save(
					personAccount);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������˺�������Ϣ
	 * 
	 * @Methods Name saveRemoteAccessAccInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveRemoteAccessAccInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String vpnType = request.getParameter("vpnType");
		String dataId = request.getParameter("dataId");
		String userInfo = request.getParameter("userInfo");
		String cardNumber = request.getParameter("cardNumber");
		String rightDesc = request.getParameter("rightDesc");
		Date endDate=null;
		String pingCode="";
		Date currentDate = DateUtil.getCurrentDate();
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aam);
		if(vpnType.equals("1")){
			String attachmentFlag = request.getParameter("attachmentFlag");
			aam.setAttachment(attachmentFlag);
			getService().save(aam);
			pingCode = request.getParameter("pingCode");
			endDate=DateUtil.convertStringToDate("9999-12-31");
		}else{
			String end = request.getParameter("endDate");
			endDate = DateUtil.convertStringToDate(end);
		}
		for (PersonFormalAccount personAccount : account) {
			personAccount.setCardNumber(cardNumber);
			personAccount.setAccountName(user.getItcode().toLowerCase());
			personAccount.setPassword("123");
			personAccount.setCreateDate(currentDate);
			personAccount.setRightsDesc(rightDesc);
			personAccount.setEndDate(endDate);
			personAccount.setIfHold(1);
			personAccount.setCardState(1);
			personAccount.setVpnType(vpnType);
			if(vpnType.equals("1")){
				personAccount.setPingCode(pingCode);
			}
			 getService().save(personAccount);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
//		HttpServletRequest request = super.getRequest();
//		HttpServletResponse response = super.getResponse();
//		
//		String userInfo = request.getParameter("userInfo");
//		String dataId = request.getParameter("dataId");
//		String end = request.getParameter("endDate");
//		Date endDate = DateUtil.convertStringToDate(end);
//		String cardNumber = request.getParameter("cardNumber");
//		String rightDesc = request.getParameter("rightDesc");
//		Date currentDate = DateUtil.getCurrentDate();
//		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);
//		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
//				AccountApplyMainTable.class, dataId);
//		List<PersonFormalAccount> account = getService().find(
//				PersonFormalAccount.class, "applyId", aam);
//		for (PersonFormalAccount personAccount : account) {
//			personAccount.setCardNumber(cardNumber);
//			personAccount.setAccountName(user.getItcode().toLowerCase());
//			personAccount.setPassword("123");
//			personAccount.setCreateDate(currentDate);
//			personAccount.setRightsDesc(rightDesc);
//			personAccount.setEndDate(endDate);
//			// personAccount.setAccountState("1");
//			personAccount.setIfHold(1);
//			personAccount.setCardState(1);
//			PersonFormalAccount pa = (PersonFormalAccount) getService().save(
//					personAccount);
//		}
//		String json = "{success:true}";
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//		PrintWriter out;
//		try {
//			out = response.getWriter();
//			out.println(json);
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
	}

	/**
	 * �����������������Ϣ
	 * 
	 * @Methods Name saveTelephoneInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveTelephoneInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		String number = request.getParameter("number");
		String rightDesc = request.getParameter("rightDesc");
		String voip = request.getParameter("voip");
		Date currentDate = DateUtil.getCurrentDate();
		if (userInfo != null && StringUtils.isNotBlank(userInfo)) {
			UserInfo user = (UserInfo) getService().find(UserInfo.class,
					userInfo);
			String itCode = user.getItcode();
			String employeeCode=user.getEmployeeCode();
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			if(employee!=null){
			employee.setTelephone(number);
			Object dcUser = getService().save(employee);
			}
//			DCAddressList dc=(DCAddressList) getService().findUnique(
//					DCAddressList.class, "employeeCode", employeeCode);
//			if(dc!=null){
//				dc.setTelephone(number);
//				dc.setVoip(voip);
//				getService().save(dc);
//			}
//			else{
//				DCAddressList dcAddressList=new DCAddressList();
//				dcAddressList.setEmployeeCode(employeeCode);
//				dcAddressList.setTelephone(number);
//				dcAddressList.setVoip(voip);
//				dcAddressList.setUserName(user.getUserName());
//				dcAddressList.setDepartment(user.getDepartment());
//				dcAddressList.setItcode(user.getItcode());
//				dcAddressList.setRealName(user.getRealName());
//				getService().save(dcAddressList);
//			}
		}
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aam);
		for (PersonFormalAccount personAccount : account) {
			personAccount.setTelephoneNumber(number);
			personAccount.setCreateDate(currentDate);
			personAccount.setVoip(voip);
			personAccount.setRightsDesc(rightDesc);
			// personAccount.setAccountState("1");
			personAccount.setIfHold(1);
			personAccount.setCardState(1);
            if (userInfo != null && StringUtils.isNotBlank(userInfo)) {
            	personAccount.setDepartTelephone("0");
			}else{
				personAccount.setAccountowner(aam.getDelegateApplyUser());
				personAccount.setDepartTelephone("1");
			}
			PersonFormalAccount pa = (PersonFormalAccount) getService().save(
					personAccount);
			

		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////
	// //////////////////////////////////////////////�����ʺ�����END//////////////////////////////////////////////////////

	// ///////////////////////////////////////////�����ʺ�Ȩ�ޱ��start//////////////////////////////////
	/**
	 * ��ʼ��������ʽ�ʺ������˼�������� new
	 * 
	 * @Methods Name initPersonRightChangeData
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String initPersonRightChangeData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String itCode = curUser.getItcode();
		Date curDate = DateUtil.getCurrentDate();
		String accountType = request.getParameter("accountType");
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (telephone.equals("")) {

				bWrapper.setPropertyValue("applyUserTel", mobileTelephone);
			} else if (mobileTelephone.equals("")) {

				bWrapper.setPropertyValue("applyUserTel", telephone);
			} else if (telephone.equals("") && mobileTelephone.equals("")) {

				bWrapper.setPropertyValue("applyUserTel", "");
			} else {

				bWrapper.setPropertyValue("applyUserTel", telephone + "/"
						+ mobileTelephone);
			}
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}

		PersonFormalAccount account = accountService.findPersonAccount(
				accountType, curUser);

		// bWrapper.setPropertyValue("delegateApplyUser", curUser);
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);

		Map<String, Object> accountMap = metaDataManager.getFormDataForEdit(
				account, "itil_ac_PersonFormalAccount");

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");

		dataMap.putAll(accountMap);
		dataMap.putAll(userMap);
		dataMap.put("itil_ac_PersonFormalAccount$remarkDesc", "");//add by liuying at 20100528 for �޸�VPN��ʾȨ��json���������
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����˺������ʼ�ݸ�
	 * 
	 * @Methods Name savePersonRightChangeDraft new
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String savePersonRightChangeDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		//ServiceItem serviceItem = (ServiceItem) getService().find(
				//ServiceItem.class, serviceItemId);
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String applyReason = request.getParameter("applyReason");
		String wwwValue = request.getParameter("wwwValue");
		WWWScanType wwwst = null;
		if (wwwValue != null) {
			wwwst = (WWWScanType) getService()
					.find(WWWScanType.class, wwwValue);
		}

		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItem", serviceItemId);

		mainMap.put("delegateApplyUser", curUser);
		mainMap.put("serviceItemProcess", serviceItemProcess);

		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		/** *******************************�����˺�ʵ��START************************************************* */
		PersonFormalAccount accounts = accountService.findPersonAccount(
				accountType, curUser);
		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(PersonFormalAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
			}
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		temp.put("accountState", "0");
		temp.put("accountType", at);
		temp.put("createDate", currentDate);
		temp.put("applyId", mainObject);
		temp.put("ifHold", 1);
		temp.put("cardState", 1);
		temp.put("accountowner", acUser);
		temp.put("olodApplyAccount", accounts);

		//add by liuying at 20100222 for ���������ʺ���Ϣ start
		if(at.getAccountType().equals("Telephone")){
			temp.put("departTelephone", accounts.getDepartTelephone());
			temp.put("telephoneNumber", accounts.getTelephoneNumber());
			temp.put("voip", accounts.getVoip());
		}
		//add by liuying at 20100222 for ���������ʺ���Ϣ end 
		//add by liuying at 20100226 for ����VPN�ʺ���Ϣ start
		if(at.getAccountType().equals("VPNAccount")){
			String s=(String) temp.get("remarkDesc");
			if("".equals(s)){
				temp.put("remarkDesc", "[\"\",\"\"]");
			}
			temp.put("drawSpace", accounts.getDrawSpace());
			temp.put("endDate", accounts.getEndDate());
			temp.put("cardNumber", accounts.getCardNumber());
			temp.put("pingCode", accounts.getPingCode());
			temp.put("vpnType", accounts.getVpnType());
			
		}
		//add by liuying at 20100226 for ����VPN�ʺ���Ϣ end 
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * �����˺������ʼ�ݸ�
	 * �����ƻ�����
	 * @Methods Name saveTelephoneTransferDraft
	 * @Create In 11 9, 2009 By gaowen
	 * @return String
	 */
	public String saveTelephoneTransferDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
//		ServiceItem serviceItem = (ServiceItem) getService().find(
//				ServiceItem.class, serviceItemId);
//		UserInfo curUser = UserContext.getUserInfo();
//		String userInfo = request.getParameter("userInfo");
//		String telephoneNumber = request.getParameter("telephoneNumber");
//		if (userInfo != null && StringUtils.isNotBlank(userInfo)) {
//			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
//		}
		String accountType = request.getParameter("accountType");
		/*PersonFormalAccount accounts = accountService.findTelephoneAccount(
				accountType, telephoneNumber);
		if (accounts == null) {
			throw new RuntimeException();
		}*/
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItem", serviceItemId);

		mainMap.put("serviceItemProcess", serviceItemProcess);

		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}

		/** *******************************�����˺�ʵ��START************************************************* */

		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(PersonFormalAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
			}
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		temp.put("accountState", "0");
		temp.put("accountType", at);
		temp.put("createDate", currentDate);
		temp.put("applyId", mainObject);
		temp.put("ifHold", 1);
		temp.put("cardState", 1);
		temp.put("accountowner", acUser);
		/*temp.put("yearMoney", accounts.getYearMoney());
		temp.put("departTelephone", accounts.getDepartTelephone());//add by liuying at 20100202
		temp.put("olodApplyAccount", accounts);*/

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ������ʽ�˺�Ȩ�ޱ��ҳ����������� new
	 * 
	 * @Methods Name getPARightChangeDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getPARightChangeDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (PersonFormalAccount acc : account) {
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");
			dataMap.putAll(tempMap);

		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");

		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡԶ�̽����˺�Ȩ�ޱ��ҳ����������� new
	 * 
	 * @Methods Name getVPNRightChangeDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getVPNRightChangeDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (PersonFormalAccount acc : account) {
			String rights = acc.getRemarkDesc();
			if(rights!=null&&!"".equals(rights)){
				String rightsDesc = rights.substring(1, rights.length() - 1);
				String[] right = rightsDesc.split(",");
				for (String r : right) {
					String s = r.substring(1, r.length() - 1);
					if (s.equals("����SAPϵͳ")) {
						dataMap.put("sap", 1);
						
					} else if (s.equals("ʹ��IPsoftphone")) {
						dataMap.put("iPsoftphone", 1);
						
					} else if (s.equals("����Ȩ��")) {
						dataMap.put("wuliu", 1);
						
					} else if (s.equals("����/������Ȩ��")) {
						dataMap.put("netWork", 1);
						
					} else {
						dataMap.put("other", s);
					}
				}
			}

			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");
			dataMap.putAll(tempMap);
			dataMap.put("itil_ac_PersonFormalAccount$remarkDesc", "");//add by liuying at 20100528 for �޸�vpnȨ�ޱ���ַ���������
		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");

		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������ʺ�Ȩ�ޱ���˺�������Ϣ
	 * 
	 * @Methods Name savePARightChangeInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String savePARightChangeInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		// String userInfo=request.getParameter("userInfo");
//		String erpUserName = request.getParameter("erpUserName");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		String yearMoney = request.getParameter("yearMoney");
		String beyondMoney = request.getParameter("beyondMoney");
		Date currentDate = DateUtil.getCurrentDate();
		// UserInfo user=(UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aam);
		for (PersonFormalAccount acc : account) {
			// PersonFormalAccount oldApplyAccount = acc.getOlodApplyAccount();
			// oldApplyAccount.setAccountState("0");
			// PersonFormalAccount ac = (PersonFormalAccount) getService().save(
			// oldApplyAccount);
			acc.setRightsDesc(rightDesc);
//			acc.setErpUserName(erpUserName);
			acc.setIfHold(1);
			acc.setCardState(1);
			acc.setCreateDate(DateUtil.getCurrentDate());
			// acc.setAccountState("1");
			if (beyondMoney != null && StringUtils.isNotBlank(beyondMoney)) {
				acc.setBeyondMoney(Double.valueOf(beyondMoney));
			}
			if (yearMoney != null && StringUtils.isNotBlank(yearMoney)) {
				acc.setYearMoney(Double.valueOf(yearMoney));
			}
			PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
					.save(acc);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ///////////////////////////////////////////�����ʺ�Ȩ�ޱ��END//////////////////////////////////

	// ///////////////////////////////////////////�����ʺ�Ȩ�ޱ��Start//////////////////////////////////
	/**
	 * ���������ʺ�Ȩ�ޱ�������ʼ�ݸ�
	 * 
	 * @Methods Name saveSPARightChangeApplyDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveSPARightChangeApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String accountName = request.getParameter("accountName");
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		UserInfo curUser = UserContext.getUserInfo();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItemProcess", serviceItemProcess);
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("delegateApplyUser", curUser);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);
		/** *******************************�����˺�ʵ��START************************************************* */

		SpecialAccount specailAccounts = accountService
				.findSpecailAccountByAccountName(at.getAccountType(),
						accountName);

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(SpecialAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map specailAccount = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				specailAccount.put(propertyName, columnValue);

			}
		}

		specailAccount.put("accountState", "0");
		specailAccount.put("accountType", at);
		specailAccount.put("applyId", mainObject);
		specailAccount.put("accountOldUser", acUser);
		specailAccount.put("accountName", specailAccounts.getAccountName());
		specailAccount.put("accountNowUser", acUser);
		specailAccount.put("olodApplyAccount", specailAccounts);
		specailAccount.put("vpnType", specailAccounts.getVpnType());
		specailAccount.put("pingCode", specailAccounts.getPingCode());
		if(at.getAccountType().equals("TempVPNAccount")){
			String s=(String) specailAccount.get("remarkDesc");
			if("���ð칫".equals(s)){
				specailAccount.put("remarkDesc", "[\"���ð칫\",\"\",\"\"]");
			}
			
			
		}
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, specailAccount);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������ʺ�Ȩ�ޱ���˺�������Ϣ
	 * 
	 * @Methods Name saveSpecailRightChangeInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveSpecailRightChangeInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		// String userInfo=request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		// UserInfo user=(UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aam);
		for (SpecialAccount acc : account) {
			SpecialAccount ac = acc.getOlodApplyAccount();
			// SpecialAccount oldApplyAccount = acc.getOlodApplyAccount();
			// oldApplyAccount.setAccountState("0");
			// SpecialAccount ac = (SpecialAccount) getService().save(
			// oldApplyAccount);
			if (acc.getAccountType().getAccountType().equals("TempVPNAccount")) {
				acc.setRightsDesc(rightDesc);
				// acc.setAccountState("1");
				acc.setIfHold(1);
				acc.setCardNumber(ac.getCardNumber());
				acc.setEndDate(ac.getEndDate());
				acc.setDrawSpace(ac.getDrawSpace());
				acc.setCardState(1);
				acc.setCreateDate(currentDate);
			} else {
				acc.setRightsDesc(rightDesc);
				// acc.setAccountState("1");
				acc.setIfHold(1);
				acc.setCardState(1);
				acc.setCreateDate(currentDate);
			}
			SpecialAccount newAccountApply = (SpecialAccount) getService()
					.save(acc);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ///////////////////////////////////////////�����ʺ�Ȩ�ޱ��End//////////////////////////////////
	/**
	 * ��ʼ���˺�ɾ���������
	 * 
	 * @Methods Name initDeleteApplyData
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String initDeleteApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String itCode = curUser.getItcode();
		String accountType = request.getParameter("accountType");
		PersonFormalAccount account = accountService.findPersonAccount(
				accountType, curUser);

		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("delegateApplyUser", curUser);
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (telephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", mobileTelephone);
				bWrapper.setPropertyValue("applyUserTel", mobileTelephone);
			} else if (mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", telephone);
				bWrapper.setPropertyValue("applyUserTel", telephone);
			} else if (telephone.equals("") && mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", "");
				bWrapper.setPropertyValue("applyUserTel", "");
			} else {
				bWrapper.setPropertyValue("delegateApplyTel", telephone + "/"
						+ mobileTelephone);
				bWrapper.setPropertyValue("applyUserTel", telephone + "/"
						+ mobileTelephone);
			}
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		Map<String, Object> accountMap = metaDataManager.getFormDataForEdit(
				account, "itil_ac_PersonFormalAccount");
		accountMap.put("itil_ac_PersonFormalAccount$applyReason", "");
		dataMap.putAll(accountMap);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ�������˺�ɾ���������
	 * 
	 * @Methods Name initSpecailDeleteApplyData
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String initSpecailDeleteApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String accountType = request.getParameter("accountType");
		List account = accountService.findSpecailAccount(accountType, curUser);

		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("delegateApplyUser", curUser);
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);

		// Map<String, Object> accountMap =
		// metaDataManager.getFormDataForEdit(account,"itil_ac_PersonFormalAccount");
		// accountMap.put("itil_ac_PersonFormalAccount$applyReason", "");
		// dataMap.putAll(accountMap);

		//JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "";
		if (!account.isEmpty()) {
			json = "{success:" + true + ",form:" + mapToJson(dataMap) + "}";
		} else {
			throw new RuntimeException();
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ͨ��������ȡ��
	 * 
	 * @Methods Name toClass
	 * @Create In Mar 4, 2009 By gaowen
	 * @param className
	 * @return Class
	 */
	private Class toClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clazz;
	}

	// //////////////////////////////////////////////�����ʺ�����start/////////////////////////////////////////////////////////

	/**
	 * ��ʼ�������˺Ź��������˼��������
	 * 
	 * @Methods Name initSpecailAccountApplyData
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String initSpecailAccountApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String itCode = curUser.getItcode();
		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		bWrapper.setPropertyValue("delegateApplyUser", curUser);
		bWrapper.setPropertyValue("mail",curUser.getEmail());
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			if (telephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", mobileTelephone);
				bWrapper.setPropertyValue("applyUserTel", mobileTelephone);
			} else if (mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", telephone);
				bWrapper.setPropertyValue("applyUserTel", telephone);
			} else if (telephone.equals("") && mobileTelephone.equals("")) {
				bWrapper.setPropertyValue("delegateApplyTel", "");
				bWrapper.setPropertyValue("applyUserTel", "");
			} else {
				bWrapper.setPropertyValue("delegateApplyTel", telephone + "/"
						+ mobileTelephone);
				bWrapper.setPropertyValue("applyUserTel", telephone + "/"
						+ mobileTelephone);
			}
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");

		userMap.put("itil_ac_SpecialAccount$mailValue", "50M");

		dataMap.putAll(userMap);

		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������ʺ������ʼ�ݸ�
	 * 
	 * @Methods Name saveSpecailAccountApplyDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveSpecailAccountApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		UserInfo curUser = UserContext.getUserInfo();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItemProcess", serviceItemProcess);
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("delegateApplyUser", curUser);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */

		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(SpecialAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map specailAccount = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				specailAccount.put(propertyName, columnValue);

			}
		}

		specailAccount.put("accountState", "0");
		specailAccount.put("accountType", at);
		specailAccount.put("applyId", mainObject);
		specailAccount.put("accountOldUser", acUser);
		specailAccount.put("accountNowUser", acUser);
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, specailAccount);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�����˺�����ҳ�����������
	 * 
	 * @Methods Name getApplyDraft
	 * @Create In Jun 1, 2009 By lee
	 * @return String
	 */
	public String getSpecailDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<SpecialAccount> fObj = getService().find(SpecialAccount.class,
				"applyId", mainObj); // ��ȡ����ʵ��
		for (SpecialAccount acc : fObj) {
			if (acc.getAccountType().getAccountType().equals("TempVPNAccount")) {
				try {
					String rights = acc.getRemarkDesc();
					String rightsDesc = rights
							.substring(1, rights.length() - 1);
					String[] right = rightsDesc.split(",");
					for (String r : right) {
						String s = r.substring(1, r.length() - 1);
						if (s.equals("����SAPϵͳ")) {
							dataMap.put("sap", 1);

						} else if (s.equals("ʹ��IPsoftphone")) {
							dataMap.put("iPsoftphone", 1);

						} else if (s.equals("���ð칫")) {
							dataMap.put("office", 1);
						} else {
							dataMap.put("office", 1);
						}
					}
				} catch (Exception e) {
					System.out.println("��ȡȨ��ʧ�ܣ�");

				}
			}
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_SpecialAccount");
			 //add by liuying at 20100528 for �޸���ʱVPN��ʾȨ��json��������� start
			if(acc.getAccountType().getAccountType().equals("TempVPNAccount")){
				tempMap.put("itil_ac_SpecialAccount$remarkDesc", "");
			}
			if(acc.getAccountType().getAccountType().equals("Win7")){
				tempMap.put("itil_ac_SpecialAccount$remarkDesc", "");
			}
			dataMap.putAll(tempMap);
			//add by liuying at 20100528 for �޸���ʱVPN��ʾȨ��json��������� end

		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");

		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);

		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ��ʱ��ԱԶ�̽����˺�����ҳ�����������
	 * 
	 * @Methods Name getTempVPNDraftData
	 * @Create In Jun 1, 2009 By lee
	 * @return String
	 */
	public String getTempVPNDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<SpecialAccount> fObj = getService().find(SpecialAccount.class,
				"applyId", mainObj); // ��ȡ����ʵ��
		for (SpecialAccount acc : fObj) {
			String rights = acc.getRemarkDesc();
				String rightsDesc = rights.substring(1, rights.length() - 1);
				String[] right = rightsDesc.split(",");
				
				for (String r : right) {
					String s = r.substring(1, r.length() - 1);
					if (s.equals("����SAPϵͳ")) {
						dataMap.put("sap", 1);
						
					} else if (s.equals("ʹ��IPsoftphone")) {
						dataMap.put("iPsoftphone", 1);
						
					} else {
						dataMap.put("other", s);
					}
				}

			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_SpecialAccount");
			  //add by liuyng at 20100528 for �޸���ʱVPN��ʾȨ��json��������� start
			if(acc.getAccountType().getAccountType().equals("TempVPNAccount")){
				tempMap.put("itil_ac_SpecialAccount$remarkDesc", "");
			}
			//add by liuying at 20100528 for �޸���ʱVPN��ʾȨ��json��������� end
			dataMap.putAll(tempMap);
		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");

		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);

		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������˺�����������Ϣ
	 * 
	 * @Methods Name saveSpecaiAccountInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveSpecaiAccountInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();

		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		String endDate = request.getParameter("endDate");
		String accountName = request.getParameter("accountName");
		String attachment = request.getParameter("attachment");
		String mailServer = request.getParameter("mailServer");
		String password = request.getParameter("password");
		Date endDates = null;
		if (endDate != null) {
			endDates = DateUtil.convertStringToDate(endDate);
		}
		String cardNumber = request.getParameter("cardNumber");
		Date currentDate = DateUtil.getCurrentDate();

		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aam);
		for (SpecialAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("DeptMail")) {
				acc.setPassword(password);
				acc.setMailServer(mailServer);
				acc.setCreateDate(currentDate);
				acc.setIfHold(1);
				acc.setAttachment(attachment);
			} else {
				if(password!=null&&!"".equals(password)){
					acc.setPassword(password);
				}else{
					acc.setPassword("123");
				}
				acc.setCreateDate(currentDate);
				acc.setEndDate(endDates);
				acc.setCardNumber(cardNumber);
				acc.setIfHold(1);
				acc.setCardState(1);
				// acc.setAttachment(attachment);
				//add by liuying at 20100531 for ���ӱ�����ʱVPN�ʺŵ����� start
				if(acc.getAccountType().getAccountType().equals("TempVPNAccount")){
					acc.setVpnType("0");
				}
				//add by liuying at 20100531 for ���ӱ�����ʱVPN�ʺŵ����� end
			}
			if (accountName != null) {
				acc.setAccountName(accountName);
			}else{
				if(acc.getAccountType().getAccountType().equals("Win7")){
					acc.setAccountName(acc.getId().toString());
					acc.setConfirmUser(acc.getAccountNowUser());
				}
			}
			acc.setRightsDesc(rightDesc);
			// acc.setAccountState("1");
			SpecialAccount pa = (SpecialAccount) getService().save(acc);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////////////////////////////////////////////�����ʺ�����END/////////////////////////////////////////////////////////

	/**
	 * ��������˺�ɾ��������Ϣ
	 * 
	 * @Methods Name savePersonAccountDeleteInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String savePersonAccountDeleteInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		// String userInfo=request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		// String rightDesc=request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		// UserInfo user=(UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aam);
		for (PersonFormalAccount acc : account) {
			PersonFormalAccount oldApplyAccount = acc.getOlodApplyAccount();
			// oldApplyAccount.setAccountState("0");
			// if(acc.getAccountType().getAccountType().equals("WWWAccount")){
			// UserInfo accountOwner=oldApplyAccount.getAccountowner();
			// PersonFormalAccount
			// msnAccount=accountService.findPersonAccount("MSN�ʺ�",
			// accountOwner);
			// if(msnAccount!=null){
			// msnAccount.setAccountState("0");
			// getService().save(msnAccount);
			// }
			// }
			oldApplyAccount.setIfHold(0);
			PersonFormalAccount ac = (PersonFormalAccount) getService().save(
					oldApplyAccount);
			PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
					.save(acc);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����Ա����ְ�˺�ɾ��������Ϣ
	 * 
	 * @Methods Name savePersonAccountAllDeleteInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String savePersonAccountAllDeleteInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String erpIfHold = request.getParameter("erpIfHold");
//		String accountTypes = request.getParameter("accountType");
        String gParam1 = request.getParameter("gParam1");
		JSONArray jsonArray = JSONArray.fromObject(gParam1);
		Date currentDate = DateUtil.getCurrentDate();
		UserInfo curUser = UserContext.getUserInfo();
		Object[] panelDataArrays = jsonArray.toArray();
		PersonFormalAccount account=null;
		String rightsDesc=null;
		for (int i = 0; i < panelDataArrays.length; i++) {
			JSONObject record = (JSONObject) panelDataArrays[i];
			Iterator columnIter = record.keys();
			while (columnIter.hasNext()) {
				String columnName = (String) columnIter.next();
				String columnValue = record.getString(columnName);
				if (columnName.equals("id")) {
					    account = (PersonFormalAccount) getService()
								.find(PersonFormalAccount.class, columnValue);
						String accountType = account.getAccountType()
								.getAccountType();
						Role role = account.getAccountType().getRole();
						Set<UserInfo> userinfos = role.getUserInfos();
						if (userinfos.contains(curUser)) {
							if (accountType.equals("ERPAccount")&& erpIfHold.equals("1")) {
								PersonFormalAccount oldApplyAccount = account.getOlodApplyAccount();
								oldApplyAccount.setAccountowner(account.getAccountowner());
								oldApplyAccount.setIfHold(1);
								getService().save(oldApplyAccount);
							} else {
								PersonFormalAccount oldApplyAccount = account.getOlodApplyAccount();
								oldApplyAccount.setAccountState("0");
								oldApplyAccount.setIfHold(0);
								getService().save(oldApplyAccount);
							}
						}
				}
				if (columnName.equals("rightsDesc")) {
					 rightsDesc=columnValue;
				}
				
			}
			account.setRightsDesc(rightsDesc);
			getService().save(account);

		}

		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////////////////////////////////////////////�����ʺ�ɾ��start/////////////////////////////////////////////////////////
	/**
	 * �����˺�������Ϣ
	 * 
	 * @Methods Name saveAccountApplyInfo
	 * @Create In Jun 26, 2009 By gaowen
	 * @return String
	 */
	public String saveAccountApplyInfo() {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		String panelName = request.getParameter("panelName");// �õ����������
		String mailServer = request.getParameter("mailServer");
		String workSpace = request.getParameter("workSpace");
		String sameMailDept=request.getParameter("sameMailDept");
		
		if (mailServer != null && workSpace != null&&StringUtils.isNotBlank(mailServer)&&StringUtils.isNotBlank(workSpace)) {//modify by liuying at 20100607 for ���ű�����벻���ƽ̨ʱ�ύʧ�ܵ�BUG
			WorkSpace depart = (WorkSpace) getService().find(WorkSpace.class,
					workSpace);
			MailServer mail = (MailServer) getService().findUnique(
					MailServer.class, "name", mailServer);
			curUser.setWorkSpace(depart);
			curUser.setMailServer(mail);
		}
		if(sameMailDept!=null&&StringUtils.isNotBlank(sameMailDept)){
			SameMailDept mailDept=(SameMailDept) getService().find(SameMailDept.class, sameMailDept);
			curUser.setSameMailDept(mailDept);
			getService().save(curUser);
		}

		String serviceItemId = request.getParameter("serviceItemId");
		// String accountType = request.getParameter("accountType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}

		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", UserContext.getUserInfo());
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ�����ID
		List<SpecialAccount> accounts = getService().find(SpecialAccount.class,
				"applyId", mainObject);
		if (accounts.size() >= 1) {
			for (SpecialAccount account : accounts) {
				getService().remove(account);
			}
		}
		String name = mainObject.getName();// �õ�������
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������ʺ�ɾ���˺���Ϣ
	 * 
	 * @Methods Name saveDeleteSpecialAccountInfo
	 * @Create In Jun 26, 2009 By gaowen
	 * @return String
	 */
	public String saveDeleteSpecialAccountInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String acountId = request.getParameter("id");// �õ����������
		String applyReason = request.getParameter("applyReason");
		String applyId = request.getParameter("curId");
		String ds = request.getParameter("drawSpace");
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		AR_DrawSpace drawSpace = null;
		String processInfoId = request.getParameter("processInfoId");
		AccountApplyMainTable aa = null;
		if (applyId != null && !applyId.equals("")) {
			aa = (AccountApplyMainTable) getService().find(
					AccountApplyMainTable.class, applyId);
		}
		String id = aa.getId().toString();// �õ���ʵ��ID
		String name = aa.getName();// �õ�������
		SpecialAccount account = null;
		if (acountId != null && !acountId.equals("")) {
			account = (SpecialAccount) accountService
					.findSpecailAccountByAccountName(accountType, accountName);
		}
		SpecialAccount newAccount = new SpecialAccount();
		String[] ignoreProperties = { "id" };
		BeanUtils.copyProperties(account, newAccount, ignoreProperties);
		newAccount.setOlodApplyAccount(account);
		newAccount.setApplyId(aa);
		newAccount.setApplyReason(applyReason);
		newAccount.setIfHold(1);
		newAccount.setAccountState("0");
		if (ds != null && !ds.equals("")) {
			drawSpace = (AR_DrawSpace) getService()
					.find(AR_DrawSpace.class, ds);
			newAccount.setDrawSpace(drawSpace);
		}
		SpecialAccount newAccountApply = (SpecialAccount) getService().save(
				newAccount);

		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�����ʺ��б�
	 * 
	 * @Methods Name listSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSpecailAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		String accountType = request.getParameter("accountType");
		//modify by liuying at 20100121 for ҳ�����start
		List<SpecialAccount> account=new ArrayList();
		if(accountType.equals("��ʱ��Ա�ʼ�/���ʺ�")){
			account = accountService.findSpecailAccount(
					accountType, curUser);
			List<SpecialAccount> wwwaccount=accountService.findSpecailAccount("��ʱ��ԱWWW�ʺ�", curUser);
			List<SpecialAccount> vpnaccount=accountService.findSpecailAccount("��ʱ��ԱԶ�̽����ʺ�", curUser);
			List<SpecialAccount> msnaccount=accountService.findSpecailAccount("��ʱ��ԱMSN�ʺ�", curUser);
			List<SpecialAccount> temp=new ArrayList();
			for(SpecialAccount sa:account){
				String saName=sa.getAccountName();
				if(wwwaccount!=null){
					for(SpecialAccount sawww:wwwaccount){
						if(saName.equals(sawww.getAccountName())){
							temp.add(sawww);
						}
					}
				}
				if(vpnaccount!=null){
					for(SpecialAccount savpn:vpnaccount){
						if(saName.equals(savpn.getAccountName())){
							temp.add(savpn);
						}
					}
				}
				if(msnaccount!=null){
					for(SpecialAccount samsn:msnaccount){
						if(saName.equals(samsn.getAccountName())){
							temp.add(samsn);
						}
					}
				}
			}
			account.addAll(temp);
			
			
		}else{
		 account = accountService.findSpecailAccount(
				accountType, curUser);
		 }
		//modify by liuying at 20100121 for ҳ�����end
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		StringBuilder json = new StringBuilder();
		String jsons="";
		for (SpecialAccount acc : account) {
			Long id = acc.getId();
			String accountName = acc.getAccountName();
			String applyReason = "";
			UserInfo accountOwner = acc.getAccountNowUser();
			UserInfo accountManger = acc.getConfirmUser();
			String cadreBizAuditStr = "--";
			String accType=acc.getAccountType().getName();//add by liuying at 20100121 for ҳ�����
			String accTypeEN=acc.getAccountType().getAccountType();//add by liuying at 20100121 for ҳ�����
			String vpnType="";
			if (accountOwner != null) {
				cadreBizAuditStr = accountOwner.getItcode() + "/"
						+ accountOwner.getRealName();
			}
			String cadreFinanceAuditStr = "--";
			if (accountManger != null) {
				cadreFinanceAuditStr = accountManger.getItcode() + "/"
						+ accountManger.getRealName();
			}
			if(acc.getApplyReason()!=null){
				applyReason=acc.getApplyReason();
			}

			if(acc.getVpnType()!=null){
				vpnType=acc.getVpnType();
			}
			json.append( "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"applyReason\":\"" + applyReason
					+ "\",\"vpnType\":\"" + vpnType
					+ "\",\"accType\":\"" + accType//add by liuying at 20100121 for ҳ�����
					+ "\",\"accTypeEN\":\"" + accTypeEN//add by liuying at 20100121 for ҳ�����
					+ "\",\"accountNowUser\":\"" + cadreBizAuditStr
					+ "\",\"accountManger\":\"" + cadreFinanceAuditStr + "\"},");
		}
		if (json.length() == 0) {
			jsons = "{success:true,rowCount:" + "1" + ",data:["+ json.substring(0, json.length()) + "]}";
		} else {
			jsons = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(jsons);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�û�Ҫɾ���������ʺ��б�
	 * 
	 * @Methods Name listSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSAUseerChoose() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aa);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		StringBuilder jsons=new StringBuilder();
		for (SpecialAccount acc : account) {
			Long id = acc.getId();
			String accountName = acc.getAccountName();
			String applyReason = acc.getApplyReason();
			UserInfo accountOwner = acc.getAccountNowUser();
			UserInfo accountManger = acc.getConfirmUser();
			String cadreBizAuditStr = "--";
			String vpnType="";
			String accType=acc.getAccountType().getName();//add by liuying at 20100121 for ҳ�����
			String accTypeEN=acc.getAccountType().getAccountType();//add by liuying at 20100121 for ҳ�����
			if (accountOwner != null) {
				cadreBizAuditStr = accountOwner.getItcode() + "/"
						+ accountOwner.getRealName();
			}
			String cadreFinanceAuditStr = "--";
			if (accountManger != null) {
				cadreFinanceAuditStr = accountManger.getItcode() + "/"
						+ accountManger.getRealName();
			}
			if(acc.getApplyReason()!=null){
				applyReason=acc.getApplyReason();
			}

			if(acc.getVpnType()!=null){
				vpnType=acc.getVpnType();
			}
			jsons.append("{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"applyReason\":\"" + applyReason
					+ "\",\"vpnType\":\"" + vpnType
					+ "\",\"accType\":\"" + accType//add by liuying at 20100121 for ҳ�����
					+ "\",\"accTypeEN\":\"" + accTypeEN//add by liuying at 20100121 for ҳ�����
					+ "\",\"accountNowUser\":\"" + cadreBizAuditStr
					+ "\",\"accountManger\":\"" + cadreFinanceAuditStr + "\"},");
		}
		if (jsons.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ jsons.substring(0, jsons.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ jsons.substring(0, jsons.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������˺�������Ϣ
	 * 
	 * @Methods Name saveAccountSpecailDeleteInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveAccountSpecailDeleteInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		// String userInfo=request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		// UserInfo user=(UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aam);
		for (SpecialAccount acc : account) {
			 SpecialAccount oldApplyAccount = acc.getOlodApplyAccount();
			 String accountType=oldApplyAccount.getAccountType().getAccountType();
			 if(accountType.equals("DeptMail")||accountType.equals("TempMailAccount")){
				 String accountName=acc.getAccountName();
					DCContacts employee = (DCContacts)getService().findUnique(
							DCContacts.class, "userNames", accountName);
					if (employee != null) {
						getService().remove(employee);
					} 
			 }
			acc.setRightsDesc(rightDesc);
			SpecialAccount newAccountApply = (SpecialAccount) getService()
					.save(acc);

		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// //////////////////////////////////////////////�����ʺ�ɾ��end/////////////////////////////////////////////////////////
	/**
	 * ������ʱ��Ա�ʼ�/���˺������ʼ�ݸ�
	 * 
	 * @Methods Name saveTempMailOrDomainApplyDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveTempMailOrDomainApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		UserInfo curUser = UserContext.getUserInfo();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItemProcess", serviceItemProcess);
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("delegateApplyUser", curUser);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(SpecialAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		// Map domain = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
				// domain.put(propertyName, columnValue);
			}
		}
		//modify by liuying at 20100113 for ��ʱ�ʼ��˺ų�ʼ����Ϊ50M start
		temp.put("mailValue", 1);
		//temp.put("mailValue", 2);
		//modify by liuying at 20100113 for ��ʱ�ʼ��˺ų�ʼ����Ϊ50M end
		temp.put("accountState", "0");
		temp.put("accountType", at);
		temp.put("applyId", mainObject);
		temp.put("accountOldUser", acUser);
		temp.put("accountNowUser", acUser);

		// domain.put("accountState", "0");
		// domain.put("accountType", "1");
		// domain.put("applyId", mainObject);
		// domain.put("accountOldUser", acUser);
		// domain.put("accountNowUser", acUser);
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��
		// BaseObject domainAccount = (BaseObject)
		// metaDataManager.saveEntityData(
		// account, domain);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������Mail and Domain�˺�����������Ϣ
	 * 
	 * @Methods Name saveTempMailAndDomainAccountInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveTempMailAndDomainAccountInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String accountName = request.getParameter("accountName");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		String attachment = request.getParameter("attachment");

		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aam);
		for (SpecialAccount acc : account) {
			acc.setPassword("password123");
			acc.setCreateDate(currentDate);
			acc.setRightsDesc(rightDesc);
			acc.setAttachment(attachment);
			acc.setAccountName(accountName);
			SpecialAccount pa = (SpecialAccount) getService().save(acc);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����˺�ɾ����ʼ�ݸ�
	 * 
	 * @Methods Name saveChangeOwnerDraft
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveAccountDeleteDraft() {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);

		String applyUserId = request.getParameter("applyUser");
		String applyReason = request.getParameter("applyReason");
		String ds = request.getParameter("drawSpace");
		AR_DrawSpace drawSpace = null;
		if (ds != null && !ds.equals("")) {
			drawSpace = (AR_DrawSpace) getService()
					.find(AR_DrawSpace.class, ds);
		}
		UserInfo applyUser = null;
		if (applyUserId != null && !applyUserId.equals("")) {
			applyUser = (UserInfo) getService().find(UserInfo.class,
					applyUserId);
		}
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("detagateApplyUser", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		String stationNumber = request.getParameter("stationNumber");
		String telephoneNumber = request.getParameter("telephoneNumber");
		PersonFormalAccount account = accountService.findPersonAccount(
				accountType, applyUser);
		PersonFormalAccount newAccount = new PersonFormalAccount();
		String[] ignoreProperties = { "id" };
		BeanUtils.copyProperties(account, newAccount, ignoreProperties);
		newAccount.setOlodApplyAccount(account);
		newAccount.setApplyId((AccountApplyMainTable) mainObject);
		newAccount.setApplyReason(applyReason);
		newAccount.setStationNumber(stationNumber);
		newAccount.setTelephoneNumber(telephoneNumber);
		newAccount.setAccountState("0");
		newAccount.setDrawSpace(drawSpace);
		PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
				.save(newAccount);
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�˺�����ҳ�����������
	 * 
	 * @Methods Name getApplyDraftData
	 * @Create In Jun 1, 2009 By gaowen
	 * @return String
	 */
	public String getApplyDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��

		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);

		List<PagePanelTableRelation> relations = pptrs
				.findRelationsByPanel(panel);
		for (PagePanelTableRelation relation : relations) {
			SystemMainTable msmt = relation.getSystemMainTable(); // ��ȡ��������
			String msmtName = msmt.getTableName(); // ��ȡ��������Ӣ�ı���
			Class msmts = this.toClass(msmt.getClassName()); // ��ȡ������
			// SystemMainTableColumn fColumn=
			// relation.getForeignTableColumn();//�õ������ֶ�
			// String fColumnName = fColumn.getPropertyName(); //�õ������ֶ���
			List fObj = getService().find(msmts, "applyId", mainObj); // ��ȡ����ʵ��
			for (int i = 0; i < fObj.size(); i++) {
				Map<String, Object> tempMap = metaDataManager
						.getFormDataForEdit(fObj.get(i), msmtName);
				dataMap.putAll(tempMap);
			}
		}

		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",id:" + dataId + ",form:"
				+ mapToJson(dataMap) + "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�˺�����ҳ�����������
	 * 
	 * @Methods Name getApplyDraft
	 * @Create In Jun 1, 2009 By lee
	 * @return String
	 */
	public String getTempMailDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List fObj = getService().find(SpecialAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (int i = 0; i < fObj.size(); i++) {
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					fObj.get(i), "itil_ac_SpecialAccount");
			dataMap.putAll(tempMap);
		}

		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�˺�����ҳ�����������
	 * 
	 * @Methods Name getApplyDraftNew
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String getApplyDraftDataNew() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��

		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);

		List<PagePanelTableRelation> relations = pptrs
				.findRelationsByPanel(panel);
		for (PagePanelTableRelation relation : relations) {
			SystemMainTable msmt = relation.getSystemMainTable(); // ��ȡ��������
			String msmtName = msmt.getTableName(); // ��ȡ��������Ӣ�ı���
			SystemMainTable fsmt = relation.getForeignTable(); // ��ȡ������
			String fsmtName = fsmt.getTableName(); // ��ȡ������Ӣ����
			Class fClass = this.toClass(fsmt.getClassName()); // ��ȡ������
			SystemMainTableColumn fColumn = relation.getForeignTableColumn();// �õ������ֶ�
			String fColumnName = fColumn.getPropertyName(); // �õ������ֶ���
			String fColumnValue = dataMap.get(msmtName + "$" + fColumnName)
					.toString();
			Object fObj = getService().find(fClass, fColumnValue); // ��ȡ����ʵ��
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					fObj, fsmtName);
			dataMap.putAll(tempMap);
		}

		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�����ʺ��б�
	 * 
	 * @Methods Name listPersonAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listPersonAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		List<PersonFormalAccount> account = accountService
				.findAllPersonAccount(curUser);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (PersonFormalAccount acc : account) {
			Long id = acc.getId();
			Integer ifHold = acc.getIfHold();
			String accountName = acc.getAccountName();
			AccountType at = acc.getAccountType();
			String accountType = at.getName();
			String remarkDesc = acc.getRemarkDesc();
			String rightsDesc = "--";
			String vpnType=acc.getVpnType();
			//if (rightsDesc == null) {
			//	rightsDesc = "--";
			//}
			if(vpnType==null){
				vpnType="";
			}

			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"accountType\":\"" + accountType
					+ "\",\"vpnType\":\"" + vpnType
					+ "\",\"ifHold\":\"" + ifHold + "\",\"remarkDesc\":\"" + ""
					+ "\",\"userRight\":\"" + "" + "\",\"rightsDesc\":\""
					+ rightsDesc + "\"},";
		}
		// String accountType="�ֻ�";
		// MobileTelephoneApply
		// mobile=accountService.findMobileTelephone(accountType, curUser);
		// if(mobile!=null){
		// json += "{\"id\":\"" + mobile.getId() + "\",\"accountName\":\"" +
		// "--"
		// + "\",\"accountType\":\"" + accountType
		// + "\",\"ifHold\":\"" + "--"+ "\",\"remarkDesc\":\""
		// + "" + "\",\"userRight\":\"" + ""
		// + "\",\"rightsDesc\":\"" + "--" + "\"},";
		// }

		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�������������ʺ��б�
	 * 
	 * @Methods Name listSpecailAllAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSpecailAllAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();

		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		List<SpecialAccount> account = null;
		String json = "";
		String applyReason = "";
		if (userInfo != null) {
			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
		}

		if (dataId != null && !dataId.equals("0")) {
			AccountApplyMainTable aat = (AccountApplyMainTable) getService()
					.find(AccountApplyMainTable.class, dataId);
			account = getService().find(SpecialAccount.class, "applyId", aat);
			for (SpecialAccount acc : account) {
				String accountNewOwner = "";
				String userInfoId = "";
				Long id = acc.getId();
				Integer ifHold = acc.getIfHold();
				String accountName = acc.getAccountName();
				AccountType type = acc.getAccountType();
				applyReason = acc.getApplyReason();
				if (acc.getAccountNowUser() != null) {
					accountNewOwner = acc.getAccountNowUser().getRealName()
							+ "/"
							+ acc.getAccountNowUser().getItcode()
							+ "/"
							+ acc.getAccountNowUser().getDepartment()
									.getDepartName();
					userInfoId = acc.getAccountNowUser().getId().toString();
				}

				String accountType = type.getName();
				json += "{\"id\":\"" + id + "\",\"accountName\":\""
						+ accountName + "\",\"accountType\":\"" + accountType
						+ "\",\"applyReason\":\"" + applyReason
						+ "\",\"ifHold\":\"" + ifHold
						+ "\",\"accountNewOwner\":\"" + accountNewOwner
						+ "\",\"userInfoId\":\"" + userInfoId + "\"},";
			}
		} else {
			account = accountService.findAllSpecailAccount(curUser);
			for (SpecialAccount acc : account) {
				Long id = acc.getId();
				Integer ifHold = acc.getIfHold();
				String accountName = acc.getAccountName();
				AccountType type = acc.getAccountType();
				String accountType = type.getName();
				json += "{\"id\":\"" + id + "\",\"accountName\":\""
						+ accountName + "\",\"accountType\":\"" + accountType
						+ "\",\"applyReason\":\"" + applyReason
						+ "\",\"ifHold\":\"" + ifHold
						+ "\",\"accountNewOwner\":\"" + "" + "\"},";
			}
		}

		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼

		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ������������ERP�ʺ��б�
	 * 
	 * @Methods Name listSpecailERPAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSpecailERPAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		List<SpecialAccount> account = null;
		String json = "";
		String applyReason = "";
		AccountApplyMainTable aat = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		account = getService().find(SpecialAccount.class, "applyId", aat);
		for (SpecialAccount acc : account) {
			AccountType type = acc.getAccountType();
			if (type.getAccountType().equals("SpecailERPAccount")) {
				String accountNewOwner = "";
				String userInfoId = "";
				Long id = acc.getId();
				Integer ifHold = acc.getIfHold();
				String accountName = acc.getAccountName();

				applyReason = acc.getApplyReason();
				if (acc.getAccountNowUser() != null) {
					accountNewOwner = acc.getAccountNowUser().getRealName()
							+ "/"
							+ acc.getAccountNowUser().getItcode()
							+ "/"
							+ acc.getAccountNowUser().getDepartment()
									.getDepartName();
					userInfoId = acc.getAccountNowUser().getId().toString();
				}

				String accountType = type.getName();
				json += "{\"id\":\"" + id + "\",\"accountName\":\""
						+ accountName + "\",\"accountType\":\"" + accountType
						+ "\",\"applyReason\":\"" + applyReason
						+ "\",\"ifHold\":\"" + ifHold
						+ "\",\"accountNewOwner\":\"" + accountNewOwner
						+ "\",\"userInfoId\":\"" + userInfoId + "\"},";
			}
		}

		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼

		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ������������EB�ʺ��б�
	 * 
	 * @Methods Name listSpecailEBAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSpecailEBAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();

		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		List<SpecialAccount> account = null;
		String json = "";
		String applyReason = "";
		AccountApplyMainTable aat = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		account = getService().find(SpecialAccount.class, "applyId", aat);
		for (SpecialAccount acc : account) {
			AccountType type = acc.getAccountType();
			if (type.getAccountType().equals("TempEBAccount")) {
				String accountNewOwner = "";
				String userInfoId = "";
				Long id = acc.getId();
				Integer ifHold = acc.getIfHold();
				String accountName = acc.getAccountName();

				applyReason = acc.getApplyReason();
				if (acc.getAccountNowUser() != null) {
					accountNewOwner = acc.getAccountNowUser().getRealName()
							+ "/"
							+ acc.getAccountNowUser().getItcode()
							+ "/"
							+ acc.getAccountNowUser().getDepartment()
									.getDepartName();
					userInfoId = acc.getAccountNowUser().getId().toString();
				}

				String accountType = type.getName();
				json += "{\"id\":\"" + id + "\",\"accountName\":\""
						+ accountName + "\",\"accountType\":\"" + accountType
						+ "\",\"applyReason\":\"" + applyReason
						+ "\",\"ifHold\":\"" + ifHold
						+ "\",\"accountNewOwner\":\"" + accountNewOwner
						+ "\",\"userInfoId\":\"" + userInfoId + "\"},";
			}
		}

		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼

		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�����ʺ��б�
	 * 
	 * @Methods Name listPersonAccountDeptChange
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listPersonAccountDeptChange() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		List<PersonFormalAccount> accounts = accountService
				.findAllPersonAccount(curUser);
		List<PersonFormalAccount> account = new ArrayList<PersonFormalAccount>();
		// for (PersonFormalAccount ac : accounts) {
		// if(!(ac.getAccountType().getAccountType().equals("DomainAccount")||ac.getAccountType().getAccountType().equals("MailAccount"))){
		// account.add(ac);
		// }
		// }

		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (PersonFormalAccount acc : accounts) {
			Long id = acc.getId();
			Integer ifHold = acc.getIfHold();
			if (ifHold == null) {
				ifHold = 1;
			}
			String accountName = acc.getAccountName();
			AccountType at = acc.getAccountType();
			String accountType = at.getName();
			String remarkDesc = acc.getRemarkDesc();
			String cadreBizAuditStr = "--";
			if (remarkDesc != null) {
				cadreBizAuditStr = remarkDesc;
			}
			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"accountType\":\"" + accountType
					+ "\",\"ifHold\":\"" + ifHold + "\",\"userRight\":\"" + ""
					+ "\",\"rightsDesc\":\"" + "" + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡԱ����Ҫ���б�
	 * 
	 * @Methods Name listPersonAccountSummary
	 * @Create In AUG 20, 2009 By gaowen
	 * @return String
	 */
	public String listPersonAccountSummary() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();

		List<PersonFormalAccount> account = accountService
				.findAllPersonAccount(curUser);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (PersonFormalAccount acc : account) {
			Long id = acc.getId();
			String accountState = acc.getAccountState();

			String accountName = acc.getAccountName();
			AccountType at = acc.getAccountType();
			String accountType = at.getName();
			String type = at.getAccountType();

			String remarkDesc = acc.getRemarkDesc();
			String cadreBizAuditStr = "--";
			json += "{\"id\":\"" + id + "\",\"accountType\":\"" + accountType
					+ "\",\"accountState\":\"" + accountState
					+ "\",\"remarkDesc\":\"" + cadreBizAuditStr
					+ "\",\"userRight\":\"" + "" + "\",\"rightsDesc\":\"" + ""
					+ "\"},";
		}

		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ����ĸ����ʺ��б�
	 * 
	 * @Methods Name listUserAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listUserAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aa);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		StringBuilder json=new StringBuilder();
		String jsons = "";
		for (PersonFormalAccount acc : account) {
			Long id = acc.getId();
			Integer ifHold = acc.getIfHold();
			String accountName = acc.getAccountName();
			AccountType at = acc.getAccountType();
			String accountType = at.getName();
			String remarkDesc = "--";
			//add by liuying at 20100204 for ����ҳ��BUG start
			String rightsDesc=acc.getRightsDesc();
			String vpnType=acc.getVpnType();
			//add by liuying at 20100727 for ����ҳ����ʾԶ�̽����ʺ����Ϳ��� start
			
			if(accountType.equals("Զ�̽����ʺ�")){
				if(vpnType.equals("1")){
					remarkDesc="������/"+acc.getCardNumber();
				}else{
					remarkDesc="Ӳ����/"+acc.getCardNumber();
				}
			}
			//add by liuying at 20100727 for ����ҳ����ʾԶ�̽����ʺ����Ϳ��� end
//			if(remarkDesc==null){
//				remarkDesc="";
//			}else{
//				if(remarkDesc.startsWith("[")){
//					remarkDesc=remarkDesc.substring(1,remarkDesc.length()-1);
//					String[] s=remarkDesc.split(",");
//					String temp="";
//					for(int i=0;i<s.length;i++){
//						s[i]=s[i].substring(1, s[i].length()-1);
//						temp=temp+"+"+s[i];
//					}
//					if(temp.startsWith("+")){
//						remarkDesc=temp.substring(1);
//					}
//					
//				}
//			}
			if(rightsDesc==null){
				rightsDesc="";
			}
			if(vpnType==null){
				vpnType="";
			}
			json.append("{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"accountType\":\"" + accountType
					+ "\",\"vpnType\":\"" + vpnType
					+ "\",\"ifHold\":\"" + ifHold + "\",\"remarkDesc\":\"" + remarkDesc
					+ "\",\"userRight\":\"" + "" + "\",\"rightsDesc\":\"" + rightsDesc
					+ "\"},");
		}
		//add by liuying at 20100204 for ����ҳ��BUG end
		// MobileTelephoneApply mobile=(MobileTelephoneApply)
		// getService().findUnique(
		// MobileTelephoneApply.class, "applyId", aa);
		// if(mobile!=null){
		// json += "{\"id\":\"" + mobile.getId() + "\",\"accountName\":\"" +
		// "--"
		// + "\",\"accountType\":\"" + "�ֻ�"
		// + "\",\"ifHold\":\"" + "--"+ "\",\"remarkDesc\":\""
		// + "" + "\",\"userRight\":\"" + ""
		// + "\",\"rightsDesc\":\"" + "" + "\"},";
		// }

		if (json.length() == 0) {
			jsons = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			jsons = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(jsons);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ж��û��Ƿ������ʽ�ʺ�
	 * 
	 * @Methods Name getUserPersonAccount
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getUserPersonAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");

		if (userInfo != null&&!userInfo.equals("")) {//modify by liuying at 20100510 for �����ж�
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		UserType type = user.getUserType();
		String userType = null;
		if (type != null) {
			userType = type.getUserTypeCode();
		}
		String accountType = request.getParameter("accountType");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		String telephoneType = null;
		try {

			PersonFormalAccount account = accountService.findPersonAccount(
					accountType, user);
			if (account != null) {
				Long id = account.getId();
				TelephoneType teType = account.getTelephoneType();
				if (teType != null) {
					telephoneType = teType.getId().toString();
				}
				Double yearValue = account.getYearMoney();
				Integer card = account.getCardState();
				String telnum=account.getTelephoneNumber();
				json = "{success:true" + ",id:" + id + ",card:" + card
						+ ",userType:'" + userType + "',yearValue:" + yearValue+ ",telnum:'" + telnum
						+ "',telephoneType:'" + telephoneType + "'}";
			} else {
				json = "{success:false" + ",userType:'" + userType
						+ "',telephoneType:'" + telephoneType + "'}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * �ж��û��Ƿ�������ֻ�����
	 * 
	 * @Methods Name getUserMobileTelephone
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getUserMobileTelephone() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		String accountType = request.getParameter("accountType");
		if (userInfo != null) {
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		UserType type = user.getUserType();
		String userType = "";
		if (type != null) {
			userType = type.getUserTypeCode();
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		String telephoneType = "";
		try {

			MobileTelephoneApply account = (MobileTelephoneApply) accountService
					.findMobileTelephone(accountType, user);
			if (account != null) {
				Long id = account.getId();
				json = "{success:true" + ",id:" + id + ",userType:" + userType
						+ "}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ����û�������Ϣ
	 * 
	 * @Methods Name getUserTelephoneInfo
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getUserTelephoneInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		String accountType = request.getParameter("accountType");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		try {

			PersonFormalAccount account = accountService.findPersonAccount(
					accountType, user);
			if (account != null) {
				long type = 1L;
				Long id = account.getId();
				Integer card = account.getCardState();
				TelephoneType telephoneType = account.getTelephoneType();
				if (telephoneType != null) {
					type = account.getTelephoneType().getId();
				}
				String number = account.getTelephoneNumber();
				Double yearValue = account.getYearMoney();
				json = "{success:true" + ",id:" + id + ",card:" + card
						+ ",yearValue:" + yearValue + ",type:" + type + "}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ����û��ֻ���Ϣ
	 * 
	 * @Methods Name getUserMobileTelephoneInfo
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getUserMobileTelephoneInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		String accountType = request.getParameter("accountType");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		try {

			MobileTelephoneApply account = accountService.findMobileTelephone(
					accountType, user);
			if (account != null) {
				Long id = account.getId();

				json = "{success:true" + ",id:" + id + "}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * �ж��û��Ƿ������ʱ�ʼ��ʺ�
	 * 
	 * @Methods Name getUserSpecailAccount
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getUserSpecailAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		String accountType = request.getParameter("accountType");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		try {

			List account = accountService.findSpecailAccount(accountType, user);
			if (!account.isEmpty()) {
				json = "{success:true}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * �ж��û��Ƿ���������ʺ�
	 * 
	 * @Methods Name getUserAllSpecailAccount
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getUserAllSpecailAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		try {

			List<SpecialAccount> account = accountService.findAllSpecailAccount(user);
			String sb=new String();
			if (!account.isEmpty()) {
				for(SpecialAccount sp:account){
					sb+=sp.getAccountName();
					
				}
				json = "{success:true}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * �����˺�ɾ����ʼ�ݸ�
	 * 
	 * @Methods Name saveChangeOwnerDraft
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveEmployeeDimissionDraft() {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String userInfo = request.getParameter("userInfo");
//		String workSpace = request.getParameter("workSpace");
		UserInfo applyUser = (UserInfo) getService().find(UserInfo.class,
				userInfo);
//		WorkSpace space = (WorkSpace) getService().find(WorkSpace.class,
//				workSpace);
//		
//		applyUser.setWorkSpace(space);
//		getService().save(applyUser);
		String erpIfHold = request.getParameter("erpIfHold");
		String accountownUser = request.getParameter("accountownUser");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);

		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		// String accountTypes="�ֻ�";
		// MobileTelephoneApply
		// mobile=accountService.findMobileTelephone(accountTypes, applyUser);
		// if(mobile!=null){
		// MobileTelephoneApply applyMobile=new MobileTelephoneApply();
		// String[] ignoreProperties = { "id" };
		// BeanUtils.copyProperties(mobile,applyMobile, ignoreProperties);
		// applyMobile.setOldApply(mobile);
		// applyMobile.setApplyId((AccountApplyMainTable) mainObject);
		// applyMobile.setAccountState("0");
		// MobileTelephoneApply newAccountApply = (MobileTelephoneApply)
		// getService()
		// .save(applyMobile);
		// }

		List<PersonFormalAccount> account = accountService
				.findAllPersonAccount(applyUser);
		List<PersonFormalAccount> personAccount = getService().find(
				PersonFormalAccount.class, "applyId", mainObject);
		if (personAccount.isEmpty()) {
			for (PersonFormalAccount acc : account) {
				String accountType = acc.getAccountType().getAccountType();
				PersonFormalAccount newAccounts = (PersonFormalAccount) getService()
						.findUnique(PersonFormalAccount.class,
								"olodApplyAccount", acc);

				if ((accountType.equals("ERPAccount")) && erpIfHold.equals("1")) {
					UserInfo accountOwner = (UserInfo) getService().find(
							UserInfo.class, accountownUser);
					PersonFormalAccount newAccount = new PersonFormalAccount();
					String[] ignoreProperties = { "id" };
					BeanUtils.copyProperties(acc, newAccount, ignoreProperties);
					newAccount.setOlodApplyAccount(acc);
					newAccount.setApplyId((AccountApplyMainTable) mainObject);
					newAccount.setAccountState("0");
					newAccount.setAccountowner(accountOwner);
					newAccount.setIfHold(1);
					PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
							.save(newAccount);

				} else {

					PersonFormalAccount newAccount = new PersonFormalAccount();
					String[] ignoreProperties = { "id" };
					BeanUtils.copyProperties(acc, newAccount, ignoreProperties);
					newAccount.setOlodApplyAccount(acc);
					newAccount.setIfHold(0);
					newAccount.setApplyId((AccountApplyMainTable) mainObject);
					newAccount.setAccountState("0");
					PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
							.save(newAccount);
				}
			}

		}

		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ////////////////////////////////////////////////////�����ʺ������߱��Start/////////////////////////////
	/**
	 * ��ȡ�����ʺ��б�
	 * 
	 * @Methods Name listSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSAOwnerChangeAccount() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null) {
			curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		String accountType = request.getParameter("accountType");
		List<SpecialAccount> account = accountService.findSpecailAccount(
				accountType, curUser);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (SpecialAccount acc : account) {
			Long id = acc.getId();
			String accountName = acc.getAccountName();
			String applyReason = "";
			UserInfo accountOwner = acc.getAccountNowUser();
			UserInfo accountManger = acc.getConfirmUser();
			String cadreBizAuditStr = "--";
			//add by liuying at 20100310 for �����޸���Ϣ start
//			String res=acc.getApplyReason();
//			if(res!=null){
//				applyReason=acc.getApplyReason();
//			}
			//add by liuying at 20100310 for �����޸���Ϣ end
			if (accountOwner != null) {
				cadreBizAuditStr = accountOwner.getItcode() + "/"
						+ accountOwner.getRealName();
			}
			String cadreFinanceAuditStr = "--";
			if (accountManger != null) {
				cadreFinanceAuditStr = accountManger.getItcode() + "/"
						+ accountManger.getRealName();
			}

			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"applyReason\":\"" + applyReason
					+ "\",\"accountNowUser\":\"" + cadreBizAuditStr
					+ "\",\"accountManger\":\"" + cadreFinanceAuditStr + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����˺������߱����Ϣ
	 * 
	 * @Methods Name saveOwnerChangeAccountInfo
	 * @Create In Jun 26, 2009 By gaowen
	 * @return String
	 */
	public String saveOwnerChangeAccountInfo() {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String acountId = request.getParameter("id");// �õ����������
		String applyReason = request.getParameter("applyReason");
		String applyId = request.getParameter("curId");
		String gParam1 = request.getParameter("gParam1");
		String accountNewOwner = request.getParameter("accountNewOwner");
		String telephone = request.getParameter("telephone");
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		UserInfo newOwner = (UserInfo) getService().find(UserInfo.class,
				accountNewOwner);
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, applyId);
		String id = aa.getId().toString();// �õ���ʵ��ID
		String name = aa.getName();// �õ�������
		SpecialAccount account = accountService
				.findSpecailAccountByAccountName(accountType, accountName);
		if (account != null) {
			SpecialAccount newAccount = new SpecialAccount();
			String[] ignoreProperties = { "id" };
			BeanUtils.copyProperties(account, newAccount, ignoreProperties);
			newAccount.setOlodApplyAccount(account);
			newAccount.setApplyId(aa);
			newAccount.setAccountNowUser(newOwner);
			newAccount.setAccountOldUser(account.getAccountNowUser());
			newAccount.setTelephone(telephone);
			newAccount.setApplyReason(applyReason);
			newAccount.setAccountState("0");
			SpecialAccount newAccountApply = (SpecialAccount) getService()
					.save(newAccount);
		}else{
			//add by liuying at 20100621 for ����Win7�����߱���ݸ� start
			account =(SpecialAccount) getService().find(SpecialAccount.class, acountId);
			if (account != null) {
				SpecialAccount newAccount = new SpecialAccount();
				String[] ignoreProperties = { "id" };
				BeanUtils.copyProperties(account, newAccount, ignoreProperties);
				newAccount.setOlodApplyAccount(account);
				newAccount.setApplyId(aa);
				newAccount.setAccountNowUser(newOwner);
				newAccount.setAccountOldUser(account.getAccountNowUser());
				newAccount.setTelephone(telephone);
				newAccount.setApplyReason(applyReason);
				newAccount.setAccountState("0");
				SpecialAccount newAccountApply = (SpecialAccount) getService()
						.save(newAccount);
			}
			//add by liuying at 20100621 for ����Win7�����߱���ݸ� end
		}
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����Ա�����ű��������Ϣ
	 * 
	 * @Methods Name saveDeptChangeResult
	 * @Create In OCT 15, 2009 By gaowen
	 * @return String
	 */
	public String saveDeptChangeResult() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		String right = request.getParameter("rights");
		String wwwValue = request.getParameter("wwwValue");
		Date currentDate = DateUtil.getCurrentDate();
		UserInfo curUser = UserContext.getUserInfo();
		PersonFormalAccount acc = (PersonFormalAccount) getService().find(
				PersonFormalAccount.class, id);
		if (acc.getIfHold() == 0) {
			PersonFormalAccount pa = acc.getOlodApplyAccount();
			pa.setAccountState("0");
			Object ac = getService().save(pa);
		} else {
			AccountType accountType=acc.getAccountType();
            Role role=accountType.getRole();
            Set userInfo=role.getUserInfos();
			if (accountType.getAccountType().equals("WWWAccount")
					&& wwwValue != null&&userInfo.contains(curUser)) {
				WWWScanType type = (WWWScanType) getService().find(
						WWWScanType.class, wwwValue);
				PersonFormalAccount pa = acc.getOlodApplyAccount();
				acc.setRightsDesc(right);
				pa.setWwwAccountValue(type);
				Object ac = getService().save(pa);
				Object account = getService().save(acc);
			} else {
				PersonFormalAccount pa = acc.getOlodApplyAccount();
				acc.setRightsDesc(right);
				Object ac = getService().save(pa);
				Object account = getService().save(acc);
			}
		}

		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡԱ�����ű���ʺ��б�
	 * 
	 * @Methods Name listDeptChangeAccountList
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listDeptChangeAccountList() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aa);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (PersonFormalAccount acc : account) {
			Long id = acc.getId();
			Integer ifHold = acc.getIfHold();
			String accountName = acc.getAccountName();
			if (accountName == null) {
				accountName = "";
			}
			AccountType at = acc.getAccountType();
			String accountType = at.getName();
			String remarkDesc = acc.getRemarkDesc();
			String rightsDescs = acc.getRightsDesc();
			//modify by liuying at 20100406 for ���ű��ʱ�ڽ���ҳ����ʾȨ��������Ϣ ����Ա����ʱ����ʾ start
			if(acc.getAccountState().equals("0")){
				if (rightsDescs == null) {
					rightsDescs = "";
				}
			}
			//modify by liuying at 20100406 for ���ű��ʱ�ڽ���ҳ����ʾȨ��������Ϣ ����Ա����ʱ����ʾ end
			String cadreBizAuditStr = "--";
			if (remarkDesc != null) {
				cadreBizAuditStr = remarkDesc;
			}
			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"accountType\":\"" + accountType
					+ "\",\"ifHold\":\"" + ifHold + "\",\"remarkDesc\":\""
					+ cadreBizAuditStr + "\",\"userRight\":\"" + ""
					+ "\",\"rightsDescs\":\"" + rightsDescs + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�û�Ҫ�����߱���������ʺ��б�
	 * 
	 * @Methods Name listSpecailAccount
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String listSAOwnerUseerChoose() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aa);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		StringBuilder json=new StringBuilder();
		String jsons = "";
		for (SpecialAccount acc : account) {
			Long id = acc.getId();
			String accountName = acc.getAccountName();
			String applyReason = acc.getApplyReason();
			SpecialAccount sa = acc.getOlodApplyAccount();
			UserInfo accountOwner = sa.getAccountNowUser();
			UserInfo accountManger = acc.getConfirmUser();
			String cadreBizAuditStr = "--";
			if (accountOwner != null) {
				cadreBizAuditStr = accountOwner.getItcode() + "/"
						+ accountOwner.getRealName();
			}
			String cadreFinanceAuditStr = "--";
			if (accountManger != null) {
				cadreFinanceAuditStr = accountManger.getItcode() + "/"
						+ accountManger.getRealName();
			}

			json.append("{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\",\"applyReason\":\"" + applyReason
					+ "\",\"accountNowUser\":\"" + cadreBizAuditStr
					+ "\",\"accountManger\":\"" + cadreFinanceAuditStr + "\"},");
		}
		if (json.length() == 0) {
			jsons = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			jsons = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(jsons);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������˺������߱��������Ϣ
	 * 
	 * @Methods Name saveSAOwnerChangeInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveSAOwnerChangeInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<SpecialAccount> account = getService().find(SpecialAccount.class,
				"applyId", aam);
		for (SpecialAccount acc : account) {
			acc.setCreateDate(currentDate);
			acc.setRightsDesc(rightDesc);
			getService().save(acc);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���������˺������߱��������Ϣ
	 * 
	 * @Methods Name listSpecailAccountName
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String listSpecailAccountName() {
		    HttpServletRequest request = super.getRequest();
		    HttpServletResponse response = super.getResponse();
		    String accountType = request.getParameter("accountType");
		    UserInfo user = UserContext.getUserInfo();
		    String userInfo = request.getParameter("userInfo");
			if (userInfo != null&&!userInfo.equals("")) {
				user = (UserInfo) getService().find(UserInfo.class, userInfo);
			}
		    int pageSize = 10;//ÿҳ����
		    int start = HttpUtil.getInt(request, "start", 0);
		    int pageNo = start / pageSize + 1;
		    Page page = accountService.findSpecailAccountByUser(accountType, user, pageNo, pageSize);
		    List<SpecialAccount> account = page.list();
	        Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼

		
		String json = "";
		if(accountType!=null&&!"".equals(accountType)&&accountType.equals("Win7")){
			for (SpecialAccount acc : account) {
				Long id = acc.getId();
				String accountName = acc.getAccountName();
				String deviceId = "--";
				if(acc.getDeviceType()!=null){
					deviceId=acc.getDeviceType().getDeviceName();
				}
				String hardwareId = acc.getHardwareId();
				json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName+"/"+deviceId+"/"+hardwareId
						+ "\" },";
			}
		}else{
			for (SpecialAccount acc : account) {
				Long id = acc.getId();
				String accountName = acc.getAccountName();
				json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
				+ "\" },";
			}
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * ���ʺ��嵥�л�ȡ�����ʺ���������Ϣ
	 * 
	 * @Methods Name listPersonAccountOwner
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String listPersonAccountOwner() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		List<DCContacts> user = getService().findAll(DCContacts.class);
		Integer total = user.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (DCContacts accountUser : user) {
			Long id = accountUser.getId();
			String userName = accountUser.getUserNames();
			json += "{\"id\":\"" + id + "\",\"userName\":\"" + userName
					+ "\" },";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ���ʼ�ת��������Ϣ
	 * 
	 * @Methods Name initMailForwardApplyData
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String initMailForwardApplyData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object object = BeanUtils.instantiateClass(clazz);
		UserInfo curUser = UserContext.getUserInfo();
		Date curDate = DateUtil.getCurrentDate();
		BeanWrapper bWrapper = new BeanWrapperImpl(object);
		bWrapper.setPropertyValue("applyDate", curDate);
		bWrapper.setPropertyValue("applyUser", curUser);
		String itCode = curUser.getItcode();
		try {
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
			//DCContacts employee = (DCContacts) getService().findUnique(
			//		DCContacts.class, "itcode", itCode);
			DCContacts employee = accountService.saveOrGetContacts(itCode);
			//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
			String telephone = employee.getTelephone();
			String mobileTelephone = employee.getMobilePhone();
			bWrapper.setPropertyValue("applyUserTel", telephone + "/"
					+ mobileTelephone);
		} catch (Exception e) {
			System.out.println("��ȡ�û�ͨѶ¼���ݴ���");
		}

		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������ʼ�ת�������ʼ�ݸ�
	 * 
	 * @Methods Name saveMailForwardDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveMailForwardDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MailForwardApply.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);

			}
		}
		temp.put("accountState", "0");
		temp.put("applyId", mainObject);
		temp.put("accountOwner", acUser);
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����û�Ҫֹͣ���ʼ�ת���ʺ�
	 * 
	 * @Methods Name listMailForwardAccountName
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String listMailForwardAccountName() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json = "";
		
		int pageSize = 10;//ÿҳ����
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		String name = request.getParameter("accountName");
		String curId = request.getParameter("id");
		if(curId!=null&&curId!=""){
			MailForwardApply mfa=(MailForwardApply) getService().find(MailForwardApply.class, curId);
			json+="{id:'"+mfa.getId()+"',accountName:'"+mfa.getAccountName()+"'}";
			json = "{success: true, rowCount:'1',data:["+json+"]}";
		}else{
			Page page = accountService.getAllMailForward(name, pageNo, pageSize);
			Long total = page.getTotalCount();
			List<MailForwardApply> queryList = page.list();
			for(MailForwardApply pfa : queryList){
				json+="{id:'"+pfa.getId()+"',accountName:'"+pfa.getAccountName()+"'},";
			}
			if (json.length() == 0) {
				json = "{success:true,rowCount:" + "1" + ",data:["
						+ json.substring(0, json.length()) + "]}";
			} else {
				json = "{success:true,rowCount:" + total + ",data:["
						+ json.substring(0, json.length() - 1) + "]}";
			}
		}
	
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * �����ʼ�ת��ֹͣ�����ʼ�ݸ�
	 * 
	 * @Methods Name saveMailForwardStopDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveMailForwardStopDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String accountName = request.getParameter("accountName");
		MailForwardApply stop = accountService
				.findMailForwardByName(accountName);
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MailForwardApply.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);

			}
		}
		temp.put("accountState", "0");
		temp.put("applyId", mainObject);
		temp.put("oldApply", stop);
		temp.put("accountName", stop.getAccountName());
		temp.put("accountOwner", acUser);
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ������ʽ�˺����� ҳ����������� new
	 * 
	 * @Methods getMailForwardDraft
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getMailForwardDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��

		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<MailForwardApply> account = getService().find(
				MailForwardApply.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (MailForwardApply acc : account) {
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "MailForwardApply");
			dataMap.putAll(tempMap);
		}
		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ͨ���ʼ�Ⱥ������ȡ�ʼ�Ⱥ�����Ա
	 * 
	 * @Methods Name getMailGroupMangerByName
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String getMailGroupMangerByName() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String groupNewName = request.getParameter("groupNewName");
		Date currentDate = DateUtil.getCurrentDate();
		MailGroup accounts = accountService.findMailGroupByName(groupNewName);
		String groupManger = accounts.getChangeGroupManger();
		String json = "{success:true,groupManger:'" + groupManger + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �������Ա��IT�ʺ������ʼ�ݸ�
	 * 
	 * @Methods Name saveNewITAccountDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveNewITAccountDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");

		String mialValue = request.getParameter("mialValue");
		String remarkDesc = request.getParameter("remarkDesc");
		String wwwValue = request.getParameter("wwwValue");
		String applyReason = request.getParameter("applyReason");
		String yearMoney = request.getParameter("yearMoney");
		String telephoneType = request.getParameter("telephoneType");
		String stationNumber = request.getParameter("stationNumber");

		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);

		String user = request.getParameter("user");
		String mailServer = request.getParameter("mailServer");
		String workSpace = request.getParameter("workSpace");
		String sameDeptMail = request.getParameter("sameDeptMail");

		UserInfo userInfo = (UserInfo) getService().find(UserInfo.class, user);
		Department dept = userInfo.getDepartment();
		WorkSpace ws = (WorkSpace) getService()
				.find(WorkSpace.class, workSpace);
		userInfo.setWorkSpace(ws);	//�����û������ص�
		SameMailDept sd = (SameMailDept) getService().find(SameMailDept.class,
				sameDeptMail);

		userInfo.setSameMailDept(sd);	//�����û��ʼ��ȼ�������

		MailServer ms = (MailServer) getService().findUnique(MailServer.class,
				"name", mailServer);
		userInfo.setMailServer(ms);		//�����û��ʼ�������

		Object us = getService().save(userInfo);

		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */

		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(PersonFormalAccount.class); // ��ȡ��������
		String msmtName = msmt.getTableName(); // ��ȡ��������Ӣ�ı���
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		Map domain = new HashMap();
		Map www = new HashMap();
		Map telephone = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
				domain.put(propertyName, columnValue);
				www.put(propertyName, columnValue);
				telephone.put(propertyName, columnValue);
			}
		}
		temp.put("accountState", "0");
		temp.put("applyId", mainObject);
		temp.put("accountType", "1");
		temp.put("mailValue", mialValue);
		temp.put("department", userInfo.getDepartment());

		temp.put("remarkDesc", remarkDesc);
		temp.put("accountowner", acUser);

		domain.put("accountState", "0");
		domain.put("applyId", mainObject);
		domain.put("department", userInfo.getDepartment());
		domain.put("accountType", "2");
		domain.put("accountowner", acUser);

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��
		BaseObject domianObject = (BaseObject) metaDataManager.saveEntityData(
				account, domain);// �������ʵ��

		if (wwwValue != null && wwwValue != "") {
			www.put("accountState", "0");
			www.put("applyId", mainObject);
			www.put("accountType", "3");
			www.put("department", userInfo.getDepartment());
			www.put("accountowner", acUser);
			www.put("wwwAccountValue", wwwValue);
			www.put("applyReason", applyReason);
			BaseObject wwwObject = (BaseObject) metaDataManager.saveEntityData(
					account, www);// �������ʵ��

		}

		if (yearMoney != null && yearMoney != "") {
			telephone.put("accountState", "0");
			telephone.put("applyId", mainObject);
			telephone.put("accountType", "15");
			telephone.put("accountowner", acUser);
			temp.put("department", userInfo.getDepartment());
			telephone.put("yearMoney", yearMoney);
			telephone.put("telephoneType", telephoneType);
			telephone.put("stationNumber", stationNumber);
			BaseObject telephoneObject = (BaseObject) metaDataManager
					.saveEntityData(account, telephone);// �������ʵ��
		}

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * ��ȡ����Ա���˺����� ҳ����������� new
	 * 
	 * @Methods getNewITAccountDraftDataToApply
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getNewITAccountDraftDataToApply() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		UserInfo user = UserContext.getUserInfo();

		String www = "0";
		String telephone = "0";
		String te = "0";
		String mail = "0";
		Map<String, Object> valueMap = new HashMap<String, Object>();
		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("WWWAccount")) {
				www = "1";
				Long wwwValue = acc.getWwwAccountValue().getId();
				valueMap.put("itil_ac_PersonFormalAccount$wwwAccountValue",
						wwwValue);
				valueMap.put("wwwRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$applyReason", acc
						.getApplyReason());
			} else if (acc.getAccountType().getAccountType()
					.equals("Telephone")) {
				telephone = "1";
				Role role = acc.getAccountType().getRole();
				String name = role.getName();
				Set<UserInfo> userinfos = role.getUserInfos();
				if (userinfos.contains(user)) {
					te = "1";
				}
				valueMap.put("itil_ac_PersonFormalAccount$stationNumber", acc
						.getStationNumber());
				valueMap.put("itil_ac_PersonFormalAccount$yearMoney", acc
						.getYearMoney());
				valueMap.put("itil_ac_PersonFormalAccount$telephoneType", acc
						.getTelephoneType().getId());
				valueMap.put("telephoneRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$telephoneNumber", acc
						.getTelephoneNumber());
			} else if (acc.getAccountType().getAccountType().equals(
					"MailAccount")) {
				Role role = acc.getAccountType().getRole();
				String name = role.getName();
				Set<UserInfo> userinfos = role.getUserInfos();
				Long mailValue = acc.getMailValue().getId();
				String mailServer = acc.getMailServer();
				valueMap
						.put("itil_ac_PersonFormalAccount$mailValue", mailValue);
				valueMap.put("mailServer", mailServer);
				valueMap.put("mailRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$remarkDesc", acc
						.getRemarkDesc());
				if (userinfos.contains(user)) {
					mail = "1";
				}
			} else {
				valueMap.put("domainRightsDesc", acc.getRightsDesc());
			}
			

			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");

			dataMap.putAll(tempMap);
		}
		dataMap.putAll(valueMap);
		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		//modify by liuying for �޸���Ա����ְ����ʱ�ݸ��ύʧ�� at 20100426 start
		//dataMap.put("AccountApplyMainTable$applyUser", curUser.getRealName()+"/"+curUser.getUserName()+"/"+curUser.getDepartment().getDepartName());
		//modify by liuying for �޸���Ա����ְ����ʱ�ݸ��ύʧ�� at 20100426 end
		dataMap.put("www", www);
		dataMap.put("te", te);
		dataMap.put("mail", mail);
		dataMap.put("telephone", telephone);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ����Ա���˺����� ҳ����������� new
	 * 
	 * @Methods getNewITAccountDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getNewITAccountDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		UserInfo user = UserContext.getUserInfo();

		String www = "0";
		String telephone = "0";
		String te = "0";
		String mail = "0";
		Map<String, Object> valueMap = new HashMap<String, Object>();
		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("WWWAccount")) {
				www = "1";
				Long wwwValue = acc.getWwwAccountValue().getId();
				valueMap.put("itil_ac_PersonFormalAccount$wwwAccountValue",
						wwwValue);
				valueMap.put("wwwRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$applyReason", acc
						.getApplyReason());
			} else if (acc.getAccountType().getAccountType()
					.equals("Telephone")) {
				telephone = "1";
				Role role = acc.getAccountType().getRole();
				String name = role.getName();
				Set<UserInfo> userinfos = role.getUserInfos();
				if (userinfos.contains(user)) {
					te = "1";
				}
				valueMap.put("itil_ac_PersonFormalAccount$stationNumber", acc
						.getStationNumber());
				valueMap.put("itil_ac_PersonFormalAccount$yearMoney", acc
						.getYearMoney());
				valueMap.put("itil_ac_PersonFormalAccount$telephoneType", acc
						.getTelephoneType().getId());
				valueMap.put("telephoneRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$telephoneNumber", acc
						.getTelephoneNumber());
			} else if (acc.getAccountType().getAccountType().equals(
					"MailAccount")) {
				Role role = acc.getAccountType().getRole();
				String name = role.getName();
				Set<UserInfo> userinfos = role.getUserInfos();
				Long mailValue = acc.getMailValue().getId();
				String mailServer = acc.getMailServer();
				valueMap
						.put("itil_ac_PersonFormalAccount$mailValue", mailValue);
				valueMap.put("mailServer", mailServer);
				valueMap.put("mailRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$remarkDesc", acc
						.getRemarkDesc());
				if (userinfos.contains(user)) {
					mail = "1";
				}
			} else {
				valueMap.put("domainRightsDesc", acc.getRightsDesc());
			}
			

			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");

			dataMap.putAll(tempMap);
		}
		dataMap.putAll(valueMap);
		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		dataMap.put("www", www);
		dataMap.put("te", te);
		dataMap.put("mail", mail);
		dataMap.put("telephone", telephone);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ����Ա���˺����� ���ս��ҳ����������� new
	 * 
	 * @Methods getNewITAccountEnd
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getNewITAccountEnd() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", mainObj); // ��ȡ����ʵ��
		String www = "0";
		String telephone = "0";
		String number = "";
		Map<String, Object> valueMap = new HashMap<String, Object>();
		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("WWWAccount")) {
				www = "1";
				String wwwValue = acc.getWwwAccountValue().getType();
				valueMap.put("itil_ac_PersonFormalAccount$wwwAccountValue",
						wwwValue);
				valueMap.put("wwwRightsDesc", acc.getRightsDesc());
			}
			if (acc.getAccountType().getAccountType().equals("Telephone")) {
				telephone = "1";
				number = acc.getTelephoneNumber();
				valueMap.put("itil_ac_PersonFormalAccount$stationNumber", acc
						.getStationNumber());
				valueMap.put("itil_ac_PersonFormalAccount$yearMoney", acc
						.getYearMoney());
				valueMap.put("itil_ac_PersonFormalAccount$telephoneType", acc
						.getTelephoneType().getType());
				valueMap.put("telephoneRightsDesc", acc.getRightsDesc());
				valueMap.put("itil_ac_PersonFormalAccount$telephoneNumber", acc
						.getTelephoneNumber());
				if(acc.getVoip()!=null){
					valueMap.put("itil_ac_PersonFormalAccount$voip", acc.getVoip());
				}
			}

			if (acc.getAccountType().getAccountType().equals("DomainAccount")) {
				dataMap.put("domainRightsDesc", acc.getRightsDesc());

			}
			if (acc.getAccountType().getAccountType().equals("MailAccount")) {
				String mailValue = acc.getMailValue().getVolume();
				String mailServer = acc.getMailServer();
				valueMap
						.put("itil_ac_PersonFormalAccount$mailValue", mailValue);
				valueMap.put("mailServer", mailServer);
				dataMap.put("mailRightsDesc", acc.getRightsDesc());
			}

			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");
			dataMap.putAll(tempMap);

		}
		dataMap.putAll(valueMap);

		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		dataMap.put("www", www);
		dataMap.put("telephone", telephone);
		if (number != null || number != "") {
			dataMap.put("itil_ac_PersonFormalAccount$telephoneNumber", number);
		}
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ������Ա��IT�ʺ�����������Ϣ
	 * 
	 * @Methods Name saveNewITAccountInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveNewITAccountInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String userInfo = request.getParameter("userInfo");
		UserInfo applyUser = (UserInfo) getService().find(UserInfo.class,
				userInfo);
		String itCode = applyUser.getItcode();
		//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 begin
		//DCContacts employee = (DCContacts) getService().findUnique(
		//		DCContacts.class, "itcode", itCode);
		DCContacts employee = accountService.saveOrGetContacts(itCode);
		//modify by lee for ���ͬ������δ����ͨѶ¼���BUG in 20100119 end
		Date currentDate = DateUtil.getCurrentDate();
		String domainRightsDesc = request.getParameter("domainRightsDesc");
		String mailRightsDesc = request.getParameter("mailRightsDesc");
		String wwwRightsDesc = request.getParameter("wwwRightsDesc");
		String telephoneRightsDesc = request
				.getParameter("telephoneRightsDesc");
		String voip = request.getParameter("voip");
		String attachment = request.getParameter("attachment");
		String telephoneNumber = request.getParameter("telephoneNumber");
		UserInfo curUser = UserContext.getUserInfo();
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> account = getService().find(
				PersonFormalAccount.class, "applyId", aam);
		for (PersonFormalAccount acc : account) {
			Role role = acc.getAccountType().getRole();
			Set<UserInfo> userinfos = role.getUserInfos();
			String accountType = acc.getAccountType().getAccountType();
			// add by liuying on 20100729 for �����˶������ϴ�id�ļ� start
			if ((accountType.equals("MailAccount"))
					&& (!attachment.equals(""))) {
				acc.setAttachment(attachment);
				PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
						.save(acc);
			}
			// add by liuying on 20100729 for �����˶������ϴ�id�ļ� end
			if (userinfos.contains(curUser)) {
				if ((accountType.equals("DomainAccount"))) {
					acc.setRightsDesc(domainRightsDesc);
					acc.setAccountName(applyUser.getItcode().toLowerCase());
					acc.setCreateDate(currentDate);
					acc.setIfHold(1);
					PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
							.save(acc);
				}
				if ((accountType.equals("MailAccount"))
						&& (!attachment.equals(""))) {
					
					acc.setRightsDesc(mailRightsDesc);
					acc.setAccountName(applyUser.getItcode().toLowerCase());
					acc.setAttachment(attachment);
					acc.setIfHold(1);
					acc.setCreateDate(currentDate);
					PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
							.save(acc);

				}
				if ((accountType.equals("WWWAccount"))) {
					acc.setRightsDesc(wwwRightsDesc);
					acc.setAccountName(applyUser.getItcode().toLowerCase());
					acc.setIfHold(1);
					acc.setCreateDate(currentDate);
					PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
							.save(acc);

				}
				if (accountType.equals("Telephone")
						&& (!telephoneNumber.equals(""))) {
					acc.setRightsDesc(telephoneRightsDesc);
					acc.setAccountName(applyUser.getItcode().toLowerCase());
					acc.setTelephoneNumber(telephoneNumber);
					acc.setVoip(voip);
					acc.setIfHold(1);
					acc.setDepartTelephone("0");
					acc.setCreateDate(currentDate);
					PersonFormalAccount newAccountApply = (PersonFormalAccount) getService()
							.save(acc);
				
				}
			}

		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ʼ�Ⱥ�������ʼ�ݸ�
	 * 
	 * @Methods Name saveMailGroupDraft
	 * @Create In Aug 2, 2009 By gaowen
	 * @return String
	 */
	public String saveMailGroupDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String groupName = request.getParameter("groupName");
		String groupManger=request.getParameter("groupManger");
		String[] group = groupName.split(";");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();
		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MailGroup.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (int i = 0; i < group.length; i++) {
			for (Column column : tempColumn) {
				String propertyName = column.getPropertyName();
				String tableColumnName = msmtName + "$" + propertyName;
				if (dataMap.containsKey(tableColumnName)) {
					Object columnValue = dataMap.get(tableColumnName);
					temp.put(propertyName, columnValue);

				}
			}
			temp.put("accountState", "0");
			temp.put("groupName", group[i]);
			temp.put("groupNewName", group[i]);
			temp.put("changeGroupManger", groupManger);
			temp.put("applyId", mainObject);
			temp.put("accountowner", acUser);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					account, temp);// �������ʵ��
		}

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ʼ�Ⱥ��ɾ�������ʼ�ݸ�
	 * 
	 * @Methods Name saveMailGroupDeleteDraft
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String saveMailGroupDeleteDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String groupName = request.getParameter("groupName");
		String[] group = groupName.split(";");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();
		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MailGroup.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (int i = 0; i < group.length; i++) {
			MailGroup mg = accountService.findMailGroupByName(group[i]);
			for (Column column : tempColumn) {
				String propertyName = column.getPropertyName();
				String tableColumnName = msmtName + "$" + propertyName;
				if (dataMap.containsKey(tableColumnName)) {
					Object columnValue = dataMap.get(tableColumnName);
					temp.put(propertyName, columnValue);

				}
			}
			temp.put("accountState", "0");
			temp.put("groupName", group[i]);
			temp.put("oldApply", mg.getId());
			temp.put("groupManger", mg.getGroupManger());

			temp.put("applyId", mainObject);
			temp.put("accountowner", acUser);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					account, temp);// �������ʵ��
		}

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	/**
	 * �����ʼ�Ⱥ�����Ʊ�������ʼ�ݸ�
	 * @Methods Name saveMailGroupNameChangeDraft
	 * @Create In Jan 29, 2010 By liuying
	 * @return String
	 */
	public String saveMailGroupNameChangeDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String groupName = request.getParameter("groupName");
		String groupNewName=request.getParameter("groupNewName");
		String[] groupNewNames=groupNewName.split(";");
		String[] group = groupName.split(";");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();
		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MailGroup.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (int i = 0; i < group.length; i++) {
			MailGroup mg = accountService.findMailGroupByName(group[i]);
			for (Column column : tempColumn) {
				String propertyName = column.getPropertyName();
				String tableColumnName = msmtName + "$" + propertyName;
				if (dataMap.containsKey(tableColumnName)) {
					Object columnValue = dataMap.get(tableColumnName);
					temp.put(propertyName, columnValue);

				}
			}
			temp.put("accountState", "0");
			temp.put("groupName", group[i]);
			temp.put("oldApply", mg.getId());
			temp.put("groupManger", mg.getGroupManger());
			temp.put("groupNewName", groupNewNames[i]);
			temp.put("applyId", mainObject);
			temp.put("accountowner", acUser);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					account, temp);// �������ʵ��
		}

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ʼ�Ⱥ�����Ա��������ʼ�ݸ�
	 * 
	 * @Methods Name saveMailGroupMangerChangeDraft
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String saveMailGroupMangerChangeDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String groupName = request.getParameter("groupName");
		String[] group = groupName.split(";");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();
		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MailGroup.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (int i = 0; i < group.length; i++) {
			MailGroup mg = accountService.findMailGroupByName(group[i]);
			for (Column column : tempColumn) {
				String propertyName = column.getPropertyName();
				String tableColumnName = msmtName + "$" + propertyName;
				if (dataMap.containsKey(tableColumnName)) {
					Object columnValue = dataMap.get(tableColumnName);
					temp.put(propertyName, columnValue);

				}
			}
			temp.put("accountState", "0");
			temp.put("groupName", group[i]);
			temp.put("groupNewName", mg.getGroupNewName());
			temp.put("oldApply", mg.getId());
			temp.put("groupManger", mg.getGroupManger());
			temp.put("applyId", mainObject);
			temp.put("accountowner", acUser);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					account, temp);// �������ʵ��
		}

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�õ��ʼ�Ⱥ��ҳ������.
	 * 
	 * @Methods getMailGroupDraft
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getMailGroupDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<MailGroup> account = getService().find(MailGroup.class, "applyId",
				mainObj); // ��ȡ����ʵ��
		StringBuffer sb = new StringBuffer();
		//modify by liuying at 20100129 for ҳ����� start
		StringBuffer sb2=new StringBuffer();
		for (MailGroup acc : account) {
			sb.append(acc.getGroupName());
			sb.append(";");
			sb2.append(acc.getGroupNewName());
			sb2.append(";");
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "MailGroup");
			dataMap.putAll(tempMap);
		}
		dataMap.put("MailGroup$groupName", sb.substring(0, sb.lastIndexOf(";")));
		dataMap.put("MailGroup$groupNewName", sb2.substring(0, sb2.lastIndexOf(";")));
		//modify by liuying at 20100129 for ҳ����� end
		UserInfo curUser = ((AccountApplyMainTable) mainObj).getApplyUser();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ʼ�Ⱥ��������Ϣ
	 * 
	 * @Methods Name saveGroupInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveGroupInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		Date currentDate = DateUtil.getCurrentDate();
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List account = getService().find(MailGroup.class, "applyId", aam);
		for (int i = 0; i < account.size(); i++) {
			MailGroup personAccount = (MailGroup) account.get(i);
			// personAccount.setCreateDate(currentDate);
			personAccount.setAccountState("1");
			MailGroup pa = (MailGroup) getService().save(personAccount);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����û��ʼ��б�
	 * 
	 * @Methods Name listUserEmail
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String listUserEmail() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		List<UserInfo> user = getService().findAll(UserInfo.class);
		Integer total = user.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (UserInfo acc : user) {
			Long id = acc.getId();
			String accountName = acc.getEmail();
			json += "{\"id\":\"" + id + "\",\"email\":\"" + accountName
					+ "\" },";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����ID�ļ�����ݸ�
	 * 
	 * @Methods Name saveIDFileDraft
	 * @Create In Aug 2, 2009 By gaowen
	 * @return String
	 */
	public String saveIDFileDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, id);
		/** *******************************�����˺�ʵ��START************************************************* */
		String fileId = request.getParameter("fileId");
		String dcMail = request.getParameter("dcMail");
		String webMail = request.getParameter("webMail");
		String noPassword = request.getParameter("noPassword");
		NotesIDFile noteId = (NotesIDFile) getService().findUnique(
				NotesIDFile.class, "applyId", aam);
		if (noteId == null) {
			noteId = new NotesIDFile();
		}
		noteId.setFileName(fileId);
		noteId.setDcMail(dcMail);
		noteId.setWebMail(webMail);
		noteId.setApplyId(aam);
		noteId.setNoPassword(noPassword);
		Object file = getService().save(noteId);
		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����HRS�˺������ʼ�ݸ�
	 * 
	 * @Methods Name saveHRSApplyDraft
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("accountState", "0");
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����HRS�˺ű�������ʼ�ݸ�
	 * 
	 * @Methods Name saveHRSChangeDraft
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSChangeDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String accountName = request.getParameter("accountName");
		HRSAccountApply hrs = (HRSAccountApply) accountService
				.findHRSAccountByName(accountName);
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("accountState", "0");
		mainMap.put("accountName", accountName);
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		mainMap.put("oldApply", hrs);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡHRS�˺����� ҳ����������� new
	 * 
	 * @Methods getHRSApplyDraftData
	 * @Create In Aug 2, 2009 By gaowen
	 * @return String
	 */
	public String getHRSApplyDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		HRSAccountApply mainObj = (HRSAccountApply) getService().find(clazz,
				dataId, true); // �õ������ʵ��
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����HRS�˺�������Ϣ
	 * 
	 * @Methods Name saveHRSAccountInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSAccountInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String accountName = request.getParameter("accountName");
		String userInfo = request.getParameter("userInfo");
		Date currentDate = DateUtil.getCurrentDate();
		HRSAccountApply aam = (HRSAccountApply) getService().find(
				HRSAccountApply.class, dataId);
		if (userInfo != null) {
			UserInfo user = (UserInfo) getService().find(UserInfo.class,
					userInfo);
			aam.setAccountName(accountName);
			aam.setConfirmUser(user);
		} else {
			aam.setAccountState("1");
			aam.setCreateDate(currentDate);
			aam.setModifyDate(currentDate);
		}
		HRSAccountApply hrs = (HRSAccountApply) getService().save(aam);
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����û�Ҫɾ����HRS�ʺ�
	 * 
	 * @Methods Name listHRSAccountName
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String listHRSAccountName() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		int pageSize = 10;//ÿҳ����
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		
		Page page = accountService.findByPageQueryHRSAccount(curUser,
				pageNo, pageSize);
		List<HRSAccountApply> account = page.list();

		Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
		String json = "";

		
		for (HRSAccountApply acc : account) {
			Long id = acc.getId();
			String accountName = acc.getAccountName();
			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\" },";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����û�Ҫɾ�����ʼ�Ⱥ����
	 * 
	 * @Methods Name listMialGroupName
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String listMialGroupName() {
	
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String name=request.getParameter("accountName");
		int pageSize = 10;//ÿҳ����
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		Page page = accountService.findByPageQueryMailGroup(name, pageNo, pageSize);
		List<MailGroup> account = page.list();
        Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
	    String json = "";
		
		for (MailGroup acc : account) {
			Long id = acc.getId();
			String accountName = acc.getGroupNewName();
			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\" },";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����HRS�˺�ɾ����ʼ�ݸ�
	 * 
	 * @Methods Name saveHRSDeleteDraft
	 * @Create In Aug 5, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSDeleteDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String accountName = request.getParameter("accountName");
		HRSAccountApply account = (HRSAccountApply) accountService
				.findHRSAccountByName(accountName);
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("accountState", "0");
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("oldApply", account);
		mainMap.put("accountName", accountName);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������
		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����HRS�˺�������Ϣ
	 * 
	 * @Methods Name saveHRSAccountDelete
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSAccountDelete() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		Date currentDate = DateUtil.getCurrentDate();
		HRSAccountApply aam = (HRSAccountApply) getService().find(
				HRSAccountApply.class, dataId);
		HRSAccountApply oldApply = aam.getOldApply();
		oldApply.setAccountState("0");
		HRSAccountApply hrs = (HRSAccountApply) getService().save(oldApply);
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����HRSȨ�ޱ���˺�������Ϣ
	 * 
	 * @Methods Name saveHRSAccountRightChange
	 * @Create In Aug 6, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSAccountRightChange() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		Date currentDate = DateUtil.getCurrentDate();
		HRSAccountApply aam = (HRSAccountApply) getService().find(
				HRSAccountApply.class, dataId);
		aam.setAccountState("1");
		HRSAccountApply oldApply = aam.getOldApply();
		oldApply.setAccountState("0");
		HRSAccountApply hrs = (HRSAccountApply) getService().save(oldApply);
		HRSAccountApply newApply = (HRSAccountApply) getService().save(aam);
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��HRSȨ����Ϣ
	 * 
	 * @Methods Name initHRSRightData
	 * @Create In Aug 6, 2009 By gaowen
	 * @return String
	 */
	public String initHRSRightData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String accountName = request.getParameter("accountName");
		HRSAccountApply aam = (HRSAccountApply) accountService
				.findHRSAccountByName(accountName);

		HRSOperationScope os = aam.getOperationScope();
		String osId = "";
		if (os != null) {
			osId = os.getId().toString();
		}
		Integer referMoney = aam.getIsReferMoney();
		HRSUserRight hr = aam.getUserRight();
		String hrId = "";
		if (hr != null) {
			hrId = hr.getId().toString();
		}
		String json = "{success:" + true + ",os:" + osId + ",referMoney:"
				+ referMoney + ",hr:" + hrId + "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡIDFile���� ҳ����������� new
	 * 
	 * @Methods getIDFileDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getIDFileDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��
		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<NotesIDFile> account = getService().find(NotesIDFile.class,
				"applyId", mainObj); // ��ȡ����ʵ��
		for (NotesIDFile acc : account) {
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "NotesIDFile");
			dataMap.put("NotesIDFile$password", acc.getPassword());
			dataMap.putAll(tempMap);

		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ȡIT�������� ҳ����������� new
	 * 
	 * @Methods getITPasswordDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getITPasswordDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService()
				.find(clazz, dataId, true); // �õ������ʵ��
		UserInfo curUser = mainObj.getApplyUser();
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		ITPassword account = (ITPassword) getService().findUnique(ITPassword.class,
				"applyId", mainObj); // ��ȡ����ʵ��
	    Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
	    		account, "itil_ac_ITPassword");
			dataMap.putAll(tempMap);

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * ����IT��������ݸ�
	 * 
	 * @Methods Name saveITPassword
	 * @Create In Aug 2, 2009 By gaowen
	 * @return String
	 */
	public String saveITPassword() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String dc = request.getParameter("dc");
		String webMail = request.getParameter("webMail");
		String value = request.getParameter("value");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(ITPassword.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
			}
		}
		temp.put("applyId", mainObject);
		temp.put("mailType", value);
		temp.put("webMail", webMail);
		temp.put("dcMail", dc);
		

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ݹ����ص��ȡ�ʼ�������
	 * 
	 * @Methods Name getMailServer
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getMailServer() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo user = UserContext.getUserInfo();
		String workSpace = request.getParameter("workSpace");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;

		try {
			WorkSpace space = (WorkSpace) getService().find(
					WorkSpace.class, workSpace);
			String mailServer = space.getMailServer();
			String json = "{success:true,mailServer:'" + mailServer + "'}";
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ΪTreeGrid�ṩ����
	 * 
	 * @Methods Name createTreeGridData
	 * @Create In AUG 18, 2009 By gaowen
	 * @return String
	 * @throws IOException
	 */

	@SuppressWarnings("unchecked")
	public String createTreeGridData() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		// String className= request.getParameter("clazz");
		String panelName = request.getParameter("panelName");
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		String className = pagePanel.getSystemMainTable().getClassName();
		ServiceItem serviceItem = serviceItemService.findServiceItemById(id);
		// List clazzauto=requireSIService.findAutoClazz(className);

		// modify by lee for ���˲鿴�ɲ鿴�û�����Ȩ�޷�Χ���Լ����ǲ��������� begin
		UserInfo userInfo = UserContext.getUserInfo();
		List<UserInfo> custIds = requireSIService.findDataScopeByUser(userInfo);
		List mainList = requireSIService.findEntities(className, id, custIds);
		// List mainList = requireSIService.findEntities(className, id);
		// modify by lee for ���˲鿴�ɲ鿴�û�����Ȩ�޷�Χ���Լ����ǲ��������� end

		List<Map<String, Object>> listData = mdm.getEntityMapDataForList(
				toClass(className), mainList);
		// ��������֮ǰ���ݲ�ѯ���ݣ����˳�listData��������
		// #######################################################
		// String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns = pageManager
				.getUserPagePanelColumn(panelName);
		// ######################################################
		// //modify by tongjp getcloumn from SystemTableColumn
		SystemMainTable smt = systemMainTableService
				.findSystemMainTableByClazz(toClass(className));
		List<SystemMainTableColumn> utss = systemColumnService
				.findSystemTableColumns(smt);
		// List<UserTableSetting> utss =
		// mdm.getUserColumnForList(toClass(className));

		String json = "";

		int lft = 1;
		int rgt = 1;
		int ids = 1;
		List<ServiceItemProcess> list = serviceItemProcessService
				.findProcessesByServiceItem(serviceItem);
		for (ServiceItemProcess process : list) {
			int parent = 0;
			json += "{";
			json += "'rootGrid':'"
					+ process.getProcessInfo().getVirtualDefinitionDesc()
					+ "',";
			for (SystemMainTableColumn column : utss) {
				// Column column = uts.getColumn();
				String tableName = column.getSystemMainTable().getTableName();
				String propertyName = column.getPropertyName();
				json += "\"" + tableName + "$" + propertyName + "\":" + "\"\""
						+ ",";
			}
			int k = 0;
			for (Map<String, Object> item : listData) {
				// if
				// (process.getSidProcessType().equals(Integer.parseInt(item.get("processType").toString())))
				// {
				// k++;
				// }
				// add by lee for �������̹������� in 20090825
				if (item.get("serviceItemProcess") != null
						&& process.getId().toString().equals(
								item.get("serviceItemProcess").toString())) {
					k++;
				}
				// add by lee for �������̹������� in 20090825
			}
			json += "'_id':" + ids + ",";
			json += "'_parent':" + 0 + ",";
			json += "'_level':" + 1 + ",";
			json += "'_lft':" + lft + ",";
			rgt = lft + (k + 3) * 2 + 1;
			json += "'_rgt':" + rgt + ",";
			json += "'_is_leaf':" + false + "},";
			// ��ȡ����
			int Second = lft + 1;
			lft = rgt + 1;
			parent = ids;
			ids++;
			for (int i = 0; i < 1; i++) {
				String onlyName = "";
				if (i == 0) {
					onlyName = "�ݸ�";
				} else if (i == 1) {
					onlyName = "������";
				} else if (i == 2) {
					onlyName = "��������";
				}
				json += "{'rootGrid':'" + onlyName + "',";
				for (SystemMainTableColumn column : utss) {
					// Column column = uts.getColumn();
					String tableName = column.getSystemMainTable()
							.getTableName();
					String propertyName = column.getPropertyName();

					json += "\"" + tableName + "$" + propertyName + "\":"
							+ "\"\"" + ",";
				}
				json += "'_id':" + ids + ",";
				json += "'_parent':" + parent + ",";
				json += "'_level':" + 2 + ",";
				json += "'_lft':" + Second + ",";
				int y = 0;
				for (Map<String, Object> item : listData) {
					Object numObject = i;
					// if
					// (process.getSidProcessType().equals(Integer.parseInt(item.get("processType").toString()))
					// add by lee for �������̹������� in 20090825
					if (item.get("serviceItemProcess") != null
							&& process.getId().toString().equals(
									item.get("serviceItemProcess").toString())
							&& item.get("status").equals(
									String.valueOf(numObject))) {
						y++;
					}
				}
				int rgtc = Second + y * 2 + 1;
				json += "'_rgt':" + rgtc + ",";
				if (y == 0) {
					json += "'_is_leaf':" + true + "},";
				} else {
					json += "'_is_leaf':" + false + "},";
				}
				// ��ȡ���ǵ�����
				Object numObject = i;
				int z = 1;
				int third = Second + 1;
				Second = rgtc + 1;
				int chidparent = ids;
				ids++;
				for (Map<String, Object> item : listData) {
					// if
					// (process.getSidProcessType().equals(Integer.parseInt(item.get("processType").toString()))
					// add by lee for �������̹������� in 20090825
					if (item.get("serviceItemProcess") != null
							&& process.getId().toString().equals(
									item.get("serviceItemProcess").toString())
							&& item.get("status").equals(
									String.valueOf(numObject))) {
						json += "{";
						json += "'rootGrid':'" + item.get("name") + "',";
						for (SystemMainTableColumn column : utss) {
							// Column column = uts.getColumn();
							String tableName = column.getSystemMainTable()
									.getTableName();
							String propertyName = column.getPropertyName();
							Object value = item.get(propertyName);
							if (value == null)
								value = "";
							json += "\"" + tableName + "$" + propertyName
									+ "\":'" + value + "',";
						}
						// json+="'_id':"+ids+",";
						json += "'_parent':" + chidparent + ",";
						json += "'_level':" + 3 + ",";
						json += "'_lft':" + third + ",";
						rgt = third + 1;
						json += "'_rgt':" + rgt + ",";
						json += "'_is_leaf':" + true + "},";
						third = rgt + 1;
						ids++;
					}
					z++;
				}

			}

		}
		if (json.length() > 0) {
			json = json.substring(0, json.length() - 1);
		}

		// modify by lee for ΪtreeGridStore��Ϊֱ��Զ�������޸ķ��ط�ʽ in 20091110 begin
		// json = "[" + json + "]";
		json = "{success:true,data:[" + json + "]}";
		// modify by lee for ΪtreeGridStore��Ϊֱ��Զ�������޸ķ��ط�ʽ in 20091110 end
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * �����ֻ������ʼ�ݸ�
	 * 
	 * @Methods Name saveMobileTelephoneApplyDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveMobileTelephoneApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		List<PagePanelTableRelation> relations = pptrs
				.findRelationsByPanel(panel);
		List<SystemMainTable> ftables = new ArrayList();
		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MobileTelephoneApply.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
			}
		}
		temp.put("accountState", "0");
		temp.put("accountType", at);
		temp.put("applyId", mainObject);
		temp.put("accountowner", acUser);

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�ֻ����� new
	 * 
	 * @Methods getMobileTelephoneDraftData
	 * @Create In Jun 27, 2009 By gaowen
	 * @return String
	 */
	public String getMobileTelephoneDraftData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<MobileTelephoneApply> account = getService().find(
				MobileTelephoneApply.class, "applyId", mainObj); // ��ȡ����ʵ��
		for (MobileTelephoneApply acc : account) {
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_MobileTelephoneApply");
			dataMap.putAll(tempMap);

		}
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");
		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ֻ�����������Ϣ
	 * 
	 * @Methods Name saveMobileTelephoneInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveMobileTelephoneInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<MobileTelephoneApply> account = getService().find(MobileTelephoneApply.class, "applyId",
				aam);
		for (MobileTelephoneApply mobile:account) {
			
			mobile.setCreateDate(currentDate);
			mobile.setRightsDesc(rightDesc);
			mobile.setAccountState("1");
			MobileTelephoneApply pa = (MobileTelephoneApply) getService().save(
					mobile);
			//add by lee for ����ͨѶ¼�ֻ���Ϣ in 20100127 begin
			UserInfo accountOwner = (UserInfo) this.getService().find(UserInfo.class, mobile.getAccountowner().getId().toString());
			DCContacts employee = (DCContacts)this.getService().findUnique(
					DCContacts.class, "itcode", accountOwner.getItcode());
			employee.setMobilePhone(mobile.getTelephone());
			employee = (DCContacts) this.getService().save(employee);
			this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
			//add by lee for ����ͨѶ¼�ֻ���Ϣ in 20100127 end
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ֻ���������ʼ�ݸ�
	 * 
	 * @Methods Name saveMobileTelephoneChangeDraft
	 * @Create In May 20, 2009 By gaowen
	 * @return String
	 */
	public String saveMobileTelephoneChangeDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");// �õ����������
		String serviceItemId = request.getParameter("serviceItemId");
		String accountType = request.getParameter("accountType");
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		String applyUser = request.getParameter("applyUser");
		UserInfo user = (UserInfo) getService().find(UserInfo.class, applyUser);
		MobileTelephoneApply mobileTelephone = accountService
				.findMobileTelephone(accountType, user);

		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}
		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}

		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		Object acUser = null;
		if (mainMap.containsKey("useUser")) {
			acUser = mainMap.get("useUser");
		} else {
			acUser = mainMap.get("applyUser");
		}
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", acUser);
		}
		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ���ʵ��ID
		String name = mainObject.getName();// �õ�������

		/** *******************************�����˺�ʵ��START************************************************* */
		List<PagePanelTableRelation> relations = pptrs
				.findRelationsByPanel(panel);
		List<SystemMainTable> ftables = new ArrayList();
		AccountType at = (AccountType) getService().findUnique(
				AccountType.class, "name", accountType);

		SystemMainTable msmt = systemMainTableService
				.findSystemMainTableByClazz(MobileTelephoneApply.class); // ��ȡ��������
		String msmtName = msmt.getTableName();
		Class account = this.toClass(msmt.getClassName());
		Map temp = new HashMap();
		List<Column> tempColumn = systemColumnService
				.findSystemTableColumns(msmt);
		for (Column column : tempColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = msmtName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				temp.put(propertyName, columnValue);
			}
		}
		temp.put("accountState", "0");
		temp.put("accountType", at);
		temp.put("applyId", mainObject);
		temp.put("oldApply", mobileTelephone);
		temp.put("accountowner", acUser);

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(
				account, temp);// �������ʵ��

		/** *******************************�����˺�ʵ��END************************************************* */
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ֻ��������������Ϣ
	 * 
	 * @Methods Name saveMobileTelephoneChangeInfo
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveMobileTelephoneChangeInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		String dataId = request.getParameter("dataId");
		String rightDesc = request.getParameter("rightDesc");
		Date currentDate = DateUtil.getCurrentDate();
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userInfo);
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<MobileTelephoneApply> account = getService().find(MobileTelephoneApply.class, "applyId",
				aam);
		for (MobileTelephoneApply personAccount:account) {
            MobileTelephoneApply telephone = personAccount.getOldApply();
			if (personAccount.getDeptAllowance() != null) {
				telephone.setDeptAllowance(personAccount.getDeptAllowance());
			}
			if (personAccount.getTelephone() != null) {
				telephone.setTelephone(personAccount.getTelephone());
				//add by lee for ����ͨѶ¼�ֻ���Ϣ in 20100127 begin
				UserInfo accountOwner = (UserInfo) this.getService().find(UserInfo.class, personAccount.getAccountowner().getId().toString());
				DCContacts employee = (DCContacts)this.getService().findUnique(
						DCContacts.class, "itcode", accountOwner.getItcode());
				employee.setMobilePhone(personAccount.getTelephone());
				employee = (DCContacts) this.getService().save(employee);
				this.updateDCContacts(accountOwner.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
				//add by lee for ����ͨѶ¼�ֻ���Ϣ in 20100127 end
				
			}
			if (personAccount.getOldTelephone() != null) {
				telephone.setOldTelephone(personAccount.getOldTelephone());
			}

			if (personAccount.getStartDate() != null) {
				telephone.setStartDate(personAccount.getStartDate());
			}
			if (personAccount.getStartMonth() != null) {
				telephone.setStartMonth(personAccount.getStartMonth());
			}
			if (personAccount.getAllowance() != null) {
				telephone.setAllowance(personAccount.getAllowance());
			}
			if (personAccount.getPayType() != null) {
				telephone.setPayType(personAccount.getPayType());
			}

			telephone.setCreateDate(currentDate);
			telephone.setRightsDesc(rightDesc);
			//add by liuying for ���ӽ���ҳ����ʾ����Ա������� at 20100414 start 
			personAccount.setRightsDesc(rightDesc);
			getService().save(personAccount);
			//add by liuying for ���ӽ���ҳ����ʾ����Ա������� at 20100414 end
			MobileTelephoneApply pa = (MobileTelephoneApply) getService().save(
					telephone);
			
		}

		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��Ա����Ҫ������Ϣ
	 * 
	 * @Methods Name initPersonSummaryData
	 * @Create In Aug 20, 2009 By gaowen
	 * @return String
	 */
	public String initPersonSummaryData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		UserInfo curUser = (UserInfo) getService().find(UserInfo.class,
				userInfo);
		String itCode = curUser.getItcode();
		String comment = "";
		AccountModifyRecord record = accountService.findModifyRecord(itCode,
				AccountModifyRecord.PERSONACCOUNT);
		if (record != null) {
			comment = record.getComment() + " &nbsp&nbsp &nbsp   "
					+ record.getAccountManger() + "  &nbsp &nbsp &nbsp   "
					+ record.getModifyDate().toString();
		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");

		List<PersonFormalAccount> account = accountService
				.findAllPersonAccount(curUser);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		Map<String, Object> accountMap = new HashMap();
		for (PersonFormalAccount acc : account) {
			if (acc.getAccountType().getAccountType().equals("DomainAccount")) {
				accountMap.put("domain$id", acc.getId());
				accountMap.put("domain$accountState", acc.getAccountState());
				accountMap.put("domain$rightsDesc", acc.getRightsDesc());
				accountMap.put("domain$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("MailAccount")) {
				accountMap.put("mail$id", acc.getId());
				accountMap.put("mail$accountState", acc.getAccountState());
				accountMap
						.put("mail$mailValue", acc.getMailValue().getVolume());
				accountMap.put("mail$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("WWWAccount")) {
				accountMap.put("www$id", acc.getId());
				accountMap.put("www$accountState", acc.getAccountState());
				accountMap.put("www$wwwAccountValue", acc.getWwwAccountValue()
						.getType());
				accountMap.put("www$remarkDesc", acc.getRemarkDesc());
			}
			if (acc.getAccountType().getAccountType().equals("MSNAccount")) {
				accountMap.put("msn$id", acc.getId());
				accountMap.put("msn$accountState", acc.getAccountState());
				accountMap.put("msn$rightsDesc", acc.getRightsDesc());
				accountMap.put("msn$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("ERPAccount")) {
				accountMap.put("erp$id", acc.getId());
				accountMap.put("erp$accountState", acc.getAccountState());
				accountMap.put("erp$rightsDesc", acc.getRightsDesc());
				accountMap.put("erp$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("BIAccount")) {
				accountMap.put("bi$id", acc.getId());
				accountMap.put("bi$accountState", acc.getAccountState());
				accountMap.put("bi$referSalary", acc.getReferSalary());
				accountMap.put("bi$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("VPNAccount")) {
				accountMap.put("vpn$id", acc.getId());
				accountMap.put("vpn$accountState", acc.getAccountState());
				if(acc.getVpnType().equals("1")){
					String desc="�������ͣ��� �������룺"+acc.getPingCode();
					accountMap.put("vpn$rightsDesc", desc);
				}else{
					accountMap.put("vpn$rightsDesc", "�������ͣ�Ӳ");
				}
				accountMap.put("vpn$endDate", DateUtil.convertDateToString(acc.getEndDate()));
				accountMap.put("vpn$cardNumber", acc.getCardNumber());

			}
			if (acc.getAccountType().getAccountType().equals("E-BridgeAccount")) {
				accountMap.put("eb$id", acc.getId());
				accountMap.put("eb$telephone", acc.getTelephone());
				accountMap.put("eb$rightsDesc", acc.getRightsDesc());
				accountMap.put("eb$accountState", acc.getAccountState());
				accountMap.put("eb$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals(
					"E-LogisticsAccount")) {
				accountMap.put("el$id", acc.getId());
				accountMap.put("el$accountState", acc.getAccountState());
				accountMap.put("el$rightsDesc", acc.getRightsDesc());
				accountMap.put("el$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("SCMAccount")) {
				accountMap.put("scm$id", acc.getId());
				accountMap.put("scm$accountState", acc.getAccountState());
				accountMap.put("scm$rightsDesc", acc.getRightsDesc());
				accountMap.put("scm$remarkDesc", acc.getRemarkDesc());

			}
			/*if (acc.getAccountType().getAccountType().equals("PushMailAccount")) {
				accountMap.put("pushMail$id", acc.getId());
				accountMap.put("pushMail$accountState", acc.getAccountState());
				accountMap.put("pushMail$rightsDesc", acc.getRightsDesc());
				accountMap.put("pushMail$remarkDesc", acc.getRemarkDesc());

			}*/
			if (acc.getAccountType().getAccountType().equals("TravelerAccount")) {
				accountMap.put("pushMail$id", acc.getId());
				accountMap.put("pushMail$accountState", acc.getAccountState());
				accountMap.put("pushMail$rightsDesc", acc.getRightsDesc());
				accountMap.put("pushMail$remarkDesc", acc.getRemarkDesc());

			}
			if (acc.getAccountType().getAccountType().equals("Telephone")&&acc.getDepartTelephone().equals("0")) {
				accountMap.put("telephone$id", acc.getId());
				accountMap.put("telephone$accountState", acc.getAccountState());
				accountMap.put("telephone$yearMoney", acc.getYearMoney());
				//modify by liuying for ���Ӻ���Ϊ��ʱ����֤ at 20100504 start
				if(acc.getTelephoneNumber()!=null){
					accountMap.put("telephone$telephoneNumber", acc
							.getTelephoneNumber().toString());
				}
				if(acc.getVoip()!=null){
					accountMap.put("telephone$voip", acc.getVoip().toString());
				}

			}
			if (acc.getAccountType().getAccountType().equals("MoblieTelephone")) {
				accountMap.put("mobileTelephone$id", acc.getId());
				accountMap.put("mobileTelephone$accountState", acc
						.getAccountState());
				accountMap.put("mobileTelephone$rightsDesc", acc
						.getRightsDesc());
				if(acc.getTelephone()!=null){
					accountMap.put("mobileTelephone$mobileTelephone", acc
							.getTelephone().toString());
				}
				//modify by liuying for ���Ӻ���Ϊ��ʱ����֤ at 20100504 end
			}
		}
		//add by liuying at 20100312 for ��Ҫ����ʾ�ֻ���Ϣ start
		MobileTelephoneApply mobileTelephone=accountService.findMobileTelephone("�ֻ�", curUser);
		if(mobileTelephone!=null){
			accountMap.put("mobileTelephone$id", mobileTelephone.getId());
			accountMap.put("mobileTelephone$accountState", mobileTelephone
					.getAccountState());
			accountMap.put("mobileTelephone$rightsDesc", mobileTelephone
					.getRightsDesc());
			accountMap.put("mobileTelephone$mobileTelephone", mobileTelephone
					.getTelephone().toString());
		}
		//add by liuying at 20100312 for ��Ҫ����ʾ�ֻ���Ϣ end
		userMap.putAll(accountMap);
		userMap.put("account$comment", comment);
		JSONArray jsonObject = JSONArray.fromObject(userMap);
		json = "{success:" + true + ",form:" + mapToJson(userMap) + "}";

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����Ա����Ҫ����Ϣ
	 * 
	 * @Methods Name savePersonSummaryData
	 * @Create In Aug 20, 2009 By gaowen
	 * @return String
	 */
	public String savePersonSummaryData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String info = request.getParameter("info");			//��ȡ��Ҫ����Ϣ
		
		//��¼��Ҫ���޸���Ϣ
		Date currentDate = DateUtil.getCurrentDate();		//��ȡ��ǰʱ��
		UserInfo curUser = UserContext.getUserInfo();		//��ǰ��¼�ˣ��û���¼�޸ĵĹ���Ա
		String userid = request.getParameter("userid");		//�ʺ�������ID
		String comment = request.getParameter("comment"); 	//��Ҫ���޸�ԭ������
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userid);
		String itCode = user.getItcode();
		String accountName = user.getItcode().toLowerCase();
		AccountModifyRecord record = (AccountModifyRecord) accountService
				.findModifyRecord(itCode, AccountModifyRecord.PERSONACCOUNT);
		if (record != null) {
			record.setComment(comment);
			record.setModifyDate(currentDate);
			record.setAccountManger(curUser.getItcode());
			getService().save(record);
		} else {
			record = new AccountModifyRecord();
			record.setAccountFlag(record.PERSONACCOUNT);
			record.setItCode(itCode);
			record.setComment(comment);
			record.setModifyDate(currentDate);
			record.setAccountManger(curUser.getItcode());
			getService().save(record);
		}
		
		//��ȡ��Ҫ����Ϣ
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> domainMap = new HashMap<String, Object>();
		Map<String, Object> msnMap = new HashMap<String, Object>();
		Map<String, Object> wwwMap = new HashMap<String, Object>();
		Map<String, Object> elMap = new HashMap<String, Object>();
		Map<String, Object> ebMap = new HashMap<String, Object>();
		Map<String, Object> erpMap = new HashMap<String, Object>();
		Map<String, Object> biMap = new HashMap<String, Object>();
		Map<String, Object> scmMap = new HashMap<String, Object>();
		Map<String, Object> pushMailMap = new HashMap<String, Object>();
		Map<String, Object> mailMap = new HashMap<String, Object>();
		Map<String, Object> vpnMap = new HashMap<String, Object>();
		Map<String, Object> telephoneMap = new HashMap<String, Object>();
		Map<String, Object> mobileTelephoneMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			int temp = columnName.indexOf("$");
			String property = columnName.substring(temp + 1);
			String accountType = columnName.substring(0, temp);
			if (accountType.equals("domain")) {
				String columnValue = panelJO.getString(columnName);
				domainMap.put(property, columnValue);
				domainMap.put("accountType", "1");
				domainMap.put("createDate", currentDate);

			}
			if (accountType.equals("msn")) {
				String columnValue = panelJO.getString(columnName);
				msnMap.put(property, columnValue);
				msnMap.put("accountType", "4");
				msnMap.put("createDate", currentDate);

			}
			if (accountType.equals("www")) {
				String columnValue = panelJO.getString(columnName);
				wwwMap.put(property, columnValue);
				wwwMap.put("accountType", "3");
				wwwMap.put("createDate", currentDate);

			}
			if (accountType.equals("el")) {
				String columnValue = panelJO.getString(columnName);
				elMap.put(property, columnValue);
				elMap.put("accountType", "9");
				elMap.put("createDate", currentDate);

			}
			if (accountType.equals("eb")) {
				String columnValue = panelJO.getString(columnName);
				ebMap.put(property, columnValue);
				ebMap.put("accountType", "8");
				ebMap.put("createDate", currentDate);

			}
			if (accountType.equals("bi")) {
				String columnValue = panelJO.getString(columnName);
				biMap.put(property, columnValue);
				biMap.put("accountType", "13");
				biMap.put("createDate", currentDate);

			}
			if (accountType.equals("scm")) {
				String columnValue = panelJO.getString(columnName);
				scmMap.put(property, columnValue);
				scmMap.put("accountType", "10");
				scmMap.put("createDate", currentDate);

			}
			if (accountType.equals("pushMail")) {
				String columnValue = panelJO.getString(columnName);
				pushMailMap.put(property, columnValue);
				pushMailMap.put("accountType", "23");
				pushMailMap.put("createDate", currentDate);

			}
			if (accountType.equals("erp")) {
				String columnValue = panelJO.getString(columnName);
				erpMap.put(property, columnValue);
				erpMap.put("accountType", "7");
				erpMap.put("createDate", currentDate);

			}
			if (accountType.equals("vpn")) {
				String columnValue = panelJO.getString(columnName);
				vpnMap.put(property, columnValue);
				vpnMap.put("accountType", "5");
				vpnMap.put("createDate", currentDate);

			}
			if (accountType.equals("mail")) {
				String columnValue = panelJO.getString(columnName);
				mailMap.put(property, columnValue);
				mailMap.put("accountType", "2");
				mailMap.put("createDate", currentDate);

			}
			if (accountType.equals("telephone")) {
				String columnValue = panelJO.getString(columnName);
				telephoneMap.put(property, columnValue);
				telephoneMap.put("accountType", "15");
				telephoneMap.put("createDate", currentDate);

			}
			if (accountType.equals("mobileTelephone")) {
				String columnValue = panelJO.getString(columnName);
				mobileTelephoneMap.put(property, columnValue);
				mobileTelephoneMap.put("accountType", "16");
				mobileTelephoneMap.put("createDate", currentDate);

			}
		}
		//�������˺���Ϣ
		String domainId = (String) domainMap.get("id");
		String domainstate = (String) domainMap.get("accountState");
		if (domainId.equals("") && domainstate.equals("1")) {
			domainMap.put("accountowner", userid);
			domainMap.put("accountName", accountName);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, domainMap);// �������ʵ��
		} else if (!domainId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, domainId);
			ac.setAccountState(domainstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����MSN�ʺ���Ϣ
		String msnId = (String) msnMap.get("id");
		String msnstate = (String) msnMap.get("accountState");
		if (msnId.equals("") && msnstate.equals("1")) {
			msnMap.put("accountowner", userid);
			msnMap.put("accountName", accountName);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, msnMap);// �������ʵ��
		} else if (!msnId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, msnId);
			ac.setAccountState(msnstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����ERP�ʺ���Ϣ
		String erpId = (String) erpMap.get("id");
		String erpstate = (String) erpMap.get("accountState");
		if (erpId.equals("") && erpstate.equals("1")) {
			erpMap.put("accountowner", userid);
			erpMap.put("accountName", accountName);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, erpMap);// �������ʵ��
		} else if (!erpId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, erpId);
			ac.setAccountState(erpstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����EL�ʺ���Ϣ
		String elId = (String) elMap.get("id");
		String elstate = (String) elMap.get("accountState");
		if (elId.equals("") && elstate.equals("1")) {
			elMap.put("accountowner", userid);
			elMap.put("accountName", accountName);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, elMap);// �������ʵ��
		} else if (!elId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, elId);
			ac.setAccountState(elstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����EB�ʺ���Ϣ
		String ebId = (String) ebMap.get("id");
		String ebstate = (String) ebMap.get("accountState");
		String telephone = (String) ebMap.get("telephone");
		if (ebId.equals("") && ebstate.equals("1")) {
			ebMap.put("accountowner", userid);
			ebMap.put("accountName", accountName);
			ebMap.put("telephone", telephone);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, ebMap);// �������ʵ��
		} else if (!ebId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, ebId);
			ac.setAccountState(ebstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����SCM�ʺ���Ϣ
		String scmId = (String) scmMap.get("id");
		String scmstate = (String) scmMap.get("accountState");
		if (scmId.equals("") && scmstate.equals("1")) {
			scmMap.put("accountowner", userid);
			scmMap.put("accountName", accountName);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, scmMap);// �������ʵ��
		} else if (!scmId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, scmId);
			ac.setAccountState(scmstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����pushmail�ʺ���Ϣ
		String pushMailId = (String) pushMailMap.get("id");
		String pushMailstate = (String) pushMailMap.get("accountState");
		if (pushMailId.equals("") && pushMailstate.equals("1")) {
			pushMailMap.put("accountowner", userid);
			pushMailMap.put("accountName", accountName);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, pushMailMap);// �������ʵ��
		} else if (!pushMailId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, pushMailId);
			ac.setAccountState(pushMailstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//���������ʺ���Ϣ
		String telephoneId = (String) telephoneMap.get("id");
		String telephonestate = (String) telephoneMap.get("accountState");
		String yearMoney = (String) telephoneMap.get("yearMoney");
		String telephoneNumber = (String) telephoneMap.get("telephoneNumber");
		String voip = (String) telephoneMap.get("voip");
		if (telephoneId.equals("") && telephonestate.equals("1")) {
//			telephoneMap.put("accountowner", userid);
//			telephoneMap.put("accountName", accountName);
//			telephoneMap.put("yearMoney", yearMoney);
//			telephoneMap.put("telephoneNumber", telephoneNumber);
//			telephoneMap.put("voip", voip);
//
//			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
//					PersonFormalAccount.class, telephoneMap);// �������ʵ��
		} else if (!telephoneId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, telephoneId);
			//ac.setAccountState(telephonestate);
			ac.setVoip(voip);
			ac.setTelephoneNumber(telephoneNumber);
			if(yearMoney!=null&&!yearMoney.equals("")){
			ac.setYearMoney(Double.parseDouble(yearMoney));
			}
			BaseObject object = (BaseObject) getService().save(ac);
			user.setTelephone(telephoneNumber);
			UserInfo userinfo=(UserInfo)getService().save(user);
			DCContacts employee = (DCContacts)this.getService().findUnique(
					DCContacts.class, "itcode", userinfo.getItcode());
			employee.setTelephone(telephoneNumber);
			employee.setVoipPhone(voip);
			employee = (DCContacts) this.getService().save(employee);
			this.updateDCContacts(userinfo.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
			
		}
		//�����ֻ��ʺ���Ϣ
		String mobileTelephoneId = (String) mobileTelephoneMap.get("id");
		String mobileTelephonestate = (String) mobileTelephoneMap.get("accountState");
		String mobileTelephonerightDesc = (String) mobileTelephoneMap.get("rightsDesc");
		String mobileTelephoneNumber=(String)mobileTelephoneMap.get("mobileTelephone");
		if (mobileTelephoneId.equals("") && mobileTelephonestate.equals("1")) {
//			mobileTelephoneMap.put("accountowner", userid);
//			mobileTelephoneMap.put("telephone", mobileTelephoneNumber);
//			mobileTelephoneMap.put("rightsDesc", mobileTelephonerightDesc);
//
//			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
//					MobileTelephoneApply.class, mobileTelephoneMap);// �������ʵ��
		} else if (!mobileTelephoneId.equals("")) {
			MobileTelephoneApply ac = (MobileTelephoneApply) getService().find(
					MobileTelephoneApply.class, mobileTelephoneId);
			//ac.setAccountState(mobileTelephonestate);
			ac.setRightsDesc(mobileTelephonerightDesc);
			ac.setTelephone(mobileTelephoneNumber);
			BaseObject object = (BaseObject) getService().save(ac);
			user.setMobilePhone(mobileTelephoneNumber);
			UserInfo userinfo=(UserInfo)getService().save(user);
			DCContacts employee = accountService.saveOrGetContacts(userinfo.getItcode());
			employee.setMobilePhone(mobileTelephoneNumber);
			employee = (DCContacts) this.getService().save(employee);
			this.updateDCContacts(userinfo.getEmployeeCode(),employee.getTelephone(),employee.getMobilePhone(),employee.getVoipPhone(),false);
			
			
		}
		//����WWW�ʺ���Ϣ
		String wwwId = (String) wwwMap.get("id");
		String wwwstate = (String) wwwMap.get("accountState");
		String wwwAccountValue = (String) wwwMap.get("wwwAccountValue");

		if (wwwId.equals("") && wwwstate.equals("1")) {
			wwwMap.put("accountowner", userid);
			wwwMap.put("accountName", accountName);
			wwwMap.put("wwwAccountValue", wwwAccountValue);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, wwwMap);// �������ʵ��
		} else if (!wwwId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, wwwId);
			ac.setAccountState(wwwstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}
		//����Զ�̽����˺���Ϣ
		String vpnId = (String) vpnMap.get("id");
		String vpnstate = (String) vpnMap.get("accountState");
		String cardNumber = (String) vpnMap.get("cardNumber");
		String vpnEndDateStr = (String) vpnMap.get("endDate"); 
		Date vpnEndDate = null;
		if(StringUtils.isNotBlank(vpnEndDateStr)){
			vpnEndDate = DateUtil.convertStringToDate(vpnEndDateStr);
		}
		if (vpnId.equals("") && vpnstate.equals("1")) {
			vpnMap.put("accountowner", userid);
			vpnMap.put("accountName", accountName);
			vpnMap.put("cardNumber", cardNumber);
			vpnMap.put("endDate", vpnEndDate);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, vpnMap);// �������ʵ��
		} else if (!vpnId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, vpnId);
			ac.setAccountState(vpnstate);
			//add by lee for ������©��Ϣ in 20100115begin
			ac.setCardNumber(cardNumber);
			ac.setAccountName(accountName);
			ac.setEndDate(vpnEndDate);
			//add by lee for ������©��Ϣ in 20100115 end
			BaseObject object = (BaseObject) getService().save(ac);
		}

		String biId = (String) biMap.get("id");
		String bistate = (String) biMap.get("accountState");
		String referSalary = (String) biMap.get("referSalary");
		if (biId.equals("") && bistate.equals("1")) {
			biMap.put("accountowner", userid);
			biMap.put("accountName", accountName);
			biMap.put("referSalary", referSalary);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, biMap);// �������ʵ��
		} else if (!biId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, biId);
			ac.setAccountState(bistate);
			BaseObject object = (BaseObject) getService().save(ac);
		}

		String mailId = (String) mailMap.get("id");
		String mailstate = (String) mailMap.get("accountState");
		String mailValue = (String) mailMap.get("mailValue");

		if (mailId.equals("") && mailstate.equals("1")) {
			mailMap.put("accountowner", userid);
			mailMap.put("accountName", accountName);
			mailMap.put("mailValue", mailValue);

			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					PersonFormalAccount.class, mailMap);// �������ʵ��
		} else if (!mailId.equals("")) {
			PersonFormalAccount ac = (PersonFormalAccount) getService().find(
					PersonFormalAccount.class, mailId);
			ac.setAccountState(mailstate);
			BaseObject object = (BaseObject) getService().save(ac);
		}

		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ��ʼ��Ա��HR�˺ż�Ҫ����Ϣ
	 * 
	 * @Methods Name initHRSSummaryData
	 * @Create In Aug 20, 2009 By gaowen
	 * @return String
	 */
	public String initHRSSummaryData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		String accountName = request.getParameter("accountName");
		UserInfo curUser = (UserInfo) getService().find(UserInfo.class,
				userInfo);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		try {
			HRSAccountApply account = accountService
					.findHRSAccountByName(accountName);
			String userRight = account.getUserRight().getName();
			String operation = account.getOperationScope().getName();
			Integer salary = account.getIsReferMoney();
			String accountState = account.getAccountState();
			userMap.put("HRSAccountApply$id", account.getId());
			userMap.put("HRSAccountApply$accountState", accountState);
			userMap.put("HRSAccountApply$accountName", accountName);
			userMap.put("HRSAccountApply$userRight", userRight);
			userMap.put("HRSAccountApply$operationScope", operation);
			userMap.put("HRSAccountApply$isReferMoney", salary);
		} catch (Exception e) {
			System.out.println("�û�HR�ʺ���Ϣ����򲻴���");

		}
		JSONArray jsonObject = JSONArray.fromObject(userMap);

		String json = "{success:" + true + ",form:" + mapToJson(userMap)
				+ "}";

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����Ա��HR�˺ż�Ҫ����Ϣ
	 * 
	 * @Methods Name saveHRSSummaryData
	 * @Create In Aug 20, 2009 By gaowen
	 * @return String
	 */
	public String saveHRSSummaryData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String info = request.getParameter("info");
		Date currentDate = DateUtil.getCurrentDate();
		String userid = request.getParameter("userid");
		UserInfo user = (UserInfo) getService().find(UserInfo.class, userid);
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> accountMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			int temp = columnName.indexOf("$");
			String head = columnName.substring(0, temp);
			if (head.endsWith("HRSAccountApply")) {
				String property = columnName.substring(temp + 1);
				String columnValue = panelJO.getString(columnName);
				accountMap.put(property, columnValue);
			}
		}
		String id = (String) accountMap.get("id");
		String accountState = (String) accountMap.get("accountState");
		if (id.equals("")) {
			accountMap.put("userOwner", userid);
			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					HRSAccountApply.class, accountMap);// �������ʵ��
		} else if (!id.equals("")) {
			HRSAccountApply ac = (HRSAccountApply) getService().find(
					HRSAccountApply.class, id);
			ac.setAccountState(accountState);
			BaseObject object = (BaseObject) getService().save(ac);
		}

		String json = "{success:true}";

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��Ա��HR�˺ż�Ҫ����Ϣ
	 * 
	 * @Methods Name initSpecailSummaryData
	 * @Create In Aug 20, 2009 By gaowen
	 * @return String
	 */
	public String initSpecailSummaryData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		UserInfo curUser = (UserInfo) getService().find(UserInfo.class,
				userInfo);
		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				curUser, "sUserInfos");
		JSONArray jsonObject = JSONArray.fromObject(userMap);
		String json = "{success:" + true + ",form:" + mapToJson(userMap)
				+ "}";

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ�û������ʺż�Ҫ��
	 * 
	 * @Methods Name listSpecailAccountSummary
	 * @Create In Aug 25, 2009 By gaowen
	 * @return String
	 */
	public String listSpecailAccountSummary() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String user = request.getParameter("userInfo");
		UserInfo userInfo = (UserInfo) getService().find(UserInfo.class, user);
		List<SpecialAccount> account = accountService
				.findAllSpecailAccount(userInfo);
		Integer total = account.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (SpecialAccount acc : account) {
			String cardNumber = "--";
			String mailValue = "--";
			String wwwAccountValue = "--";
			String erpUserName = "--";
			String endDate = "--";
			String telephone = "--";
			String rightsDesc = "--";
			String remarkDesc = "--";
			//add by liuying at 20100126 for ҳ����� start
			String accountName=acc.getAccountName();
			//add by liuying at 20100126 for ҳ����� end
			Long id = acc.getId();
			String type = acc.getAccountType().getAccountType();
			String accountType = acc.getAccountType().getName();
			if (type.equals("DomainAccount")) {
				accountType = "��ʱ��Ա���ʺ�";
			}
			if (type.equals("TempVPNAccount")) {
				cardNumber = acc.getCardNumber();
				Date end = acc.getEndDate();
				endDate = DateUtil.convertDateToString(end);
			}
			String accountState = acc.getAccountState();
			if (type.equals("TempMailAccount")) {
				mailValue = acc.getMailValue().getVolume();
			} else if (type.equals("TempWWWAccount")) {
				wwwAccountValue = acc.getWwwAccountValue().getValue()
						.toString();
			} else if (type.equals("SpecailERPAccount")) {
				erpUserName = acc.getErpUserName();
			} else if (type.equals("TempEBAccount")) {
				telephone = acc.getTelephone();
			} else if (type.equals("DeptMail")) {
				mailValue = acc.getMailValue().getVolume();
			}
			rightsDesc = acc.getRightsDesc();
			if (rightsDesc == null) {
				rightsDesc = "--";
			}

			json += "{\"id\":\"" + id + "\",\"accountType\":\"" + accountType
					+ "\",\"accountState\":\"" + accountState
					+ "\",\"cardNumber\":\"" + cardNumber
					+ "\",\"accountName\":\"" + accountName//add by liuying at 20100126 for ҳ����� 
					+ "\",\"mailValue\":\"" + mailValue
					+ "\",\"wwwAccountValue\":\"" + wwwAccountValue
					+ "\",\"telephone\":\"" + telephone + "\",\"endDate\":\""
					+ endDate + "\",\"erpUserName\":\"" + erpUserName
					+ "\",\"endDate\":\"" + endDate + "\",\"remarkDesc\":\""
					+ remarkDesc + "\",\"rightsDesc\":\"" + rightsDesc + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����Ա��HR�˺ż�Ҫ����Ϣ
	 * 
	 * @Methods Name saveSpecailAccountSummary
	 * @Create In Aug 20, 2009 By gaowen
	 * @return String
	 */
	public String saveSpecailAccountSummary() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String accountType = request.getParameter("accountType");
		String id = request.getParameter("id");
		SpecialAccount sa = (SpecialAccount) getService().find(
				SpecialAccount.class, id);
		String rightsDesc = request.getParameter("rightsDesc");
		String accountState = request.getParameter("accountState");
		String mail = request.getParameter("mailValue");
		if (mail != null) {
			MailVolume mailValue = (MailVolume) getService().findUnique(
					MailVolume.class, "volume", mail);
			sa.setMailValue(mailValue);
		}
		String wwwValue = request.getParameter("wwwAccountValue");
		if (wwwValue != null) {
			WWWScanType wwwAccountValue = (WWWScanType) getService()
					.findUnique(WWWScanType.class, "type", wwwValue);
			sa.setWwwAccountValue(wwwAccountValue);
		}
		String erpUserName = request.getParameter("erpUserName");
		String telephone = request.getParameter("telephone");
		String cardNumber = request.getParameter("cardNumber");
		String endDate = request.getParameter("endDate");
		//modify by liuying at 20100126 start
		String accountName = request.getParameter("accountName");
		if (accountName != null) {
			sa.setAccountName(accountName);
		}
		if(cardNumber!=null){
			sa.setCardNumber(cardNumber);
		}
		if(!endDate.equals("--")){
			Date end = DateUtil.convertStringToDate(endDate);
			sa.setEndDate(end);
		}
		//modify by liuying at 20100126 end
		String remarkDesc = request.getParameter("remarkDesc");

		sa.setRightsDesc(rightsDesc);
		sa.setRemarkDesc(remarkDesc);

		sa.setTelephone(telephone);
		sa.setErpUserName(erpUserName);
		sa.setCardNumber(cardNumber);
		sa.setAccountState(accountState);
		Object account = getService().save(sa);

		String json = "(success:true)";

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡID�ļ���
	 * 
	 * @Methods Name listIDFileName
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String listIDFileName() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		List<DCContacts> contacts = getService().findAll(DCContacts.class);
		Integer total = contacts.size();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (DCContacts acc : contacts) {
			Long id = acc.getId();
			String accountName = acc.getUserNames();
			json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName
					+ "\" },";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ID�ļ������ϴ�
	 * 
	 * @Methods Name saveIDFileAttachment
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String saveIDFileAttachment() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String attachmentFlag = request.getParameter("attachmentFlag");
		String password = request.getParameter("password");
		Date currentDate = DateUtil.getCurrentDate();
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		
		List idFiles = getService().find(NotesIDFile.class, "applyId", aam);
		for (int i = 0; i < idFiles.size(); i++) {
			NotesIDFile idFile = (NotesIDFile) idFiles.get(i);
			idFile.setAttachment(attachmentFlag);
			idFile.setPassword(password);
			NotesIDFile pa = (NotesIDFile) getService().save(idFile);
		}
		String json = "{success:true}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ID�ļ�������ȡ
	 * 
	 * @Methods Name getFileList
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String getFileList() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String clazz = request.getParameter("clazzName");
		String dataId = request.getParameter("dataId");
		String columnName = request.getParameter("columnName");
		String hiddenId = request.getParameter("hiddenId");
		String columnid = request.getParameter("columnid");
		Object ob1 = baseBervice.find(AccountApplyMainTable.class, dataId);
		List oblist = baseBervice.find(this.toClass(clazz), "applyId", ob1);

		Object ob = oblist.get(0);
		BeanWrapper bw = new BeanWrapperImpl(ob);
		Object value = bw.getPropertyValue(columnName);
		String json = "";
		List<SystemFile> systemfileList = baseBervice.find(SystemFile.class,
				"nowtime", value);
		String filesPathString = "";
		int k = 1;
		for (SystemFile filesTemp : systemfileList) {
			filesPathString += "<span id =su_" + filesTemp.getId()
					+ "><img src='+webContext+'/images/other/file.gif >"
					+ "&nbsp;<a href='+webContext+'"
					+ "/fileDown.do?methodCall=downloadFile&id="
					+ filesTemp.getId() + "&columnId=" + columnid + ">"
					+ filesTemp.getFileName() + "</a>&nbsp;&nbsp;";
			// +"<img src='+webContext+'/images/other/suremove.gif
			// onClick=getRemoveFile(\""
			// +
			// filesTemp.getId()+"\",\""+hiddenId+"\",\"com.zsgj.info.appframework.metadata.entity.SystemFile\")"
			// +" alt=\"ɾ������\" style=\"cursor:hand;margin:-1 0\">"
			if (k % 4 == 0) {
				filesPathString = filesPathString + "<br></span>";
			} else {
				filesPathString = filesPathString + "</span>";
			}
			k++;
		}
		json = "{file:'" + filesPathString + "'}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��Ա��ID�ļ�������ȡ
	 * 
	 * @Methods Name getNewITApplyFileList
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String getNewITApplyFileList() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String clazz = request.getParameter("clazzName");
		String dataId = request.getParameter("dataId");
		String columnName = request.getParameter("columnName");
		String hiddenId = request.getParameter("hiddenId");
		String columnid = request.getParameter("columnid");
		Object ob1 = baseBervice.find(AccountApplyMainTable.class, dataId);
		PersonFormalAccount ob = null;
		List<PersonFormalAccount> oblist = baseBervice.find(
				this.toClass(clazz), "applyId", ob1);
		for (PersonFormalAccount acc : oblist) {
			if (acc.getAccountType().getAccountType().equals("MailAccount")) {
				ob = acc;
			}
		}

		BeanWrapper bw = new BeanWrapperImpl(ob);
		Object value = bw.getPropertyValue(columnName);
		String json = "";
		List<SystemFile> systemfileList = baseBervice.find(SystemFile.class,
				"nowtime", value);
		String filesPathString = "";
		int k = 1;
		for (SystemFile filesTemp : systemfileList) {
			filesPathString += "<span id =su_" + filesTemp.getId()
					+ "><img src='+webContext+'/images/other/file.gif >"
					+ "&nbsp;<a href='+webContext+'"
					+ "/fileDown.do?methodCall=downloadFile&id="
					+ filesTemp.getId() + "&columnId=" + columnid + ">"
					+ filesTemp.getFileName() + "</a>&nbsp;&nbsp;";
			// +"<img src='+webContext+'/images/other/suremove.gif
			// onClick=getRemoveFile(\""
			// +
			// filesTemp.getId()+"\",\""+hiddenId+"\",\"com.zsgj.info.appframework.metadata.entity.SystemFile\")"
			// +" alt=\"ɾ������\" style=\"cursor:hand;margin:-1 0\">";
			if (k % 4 == 0) {
				filesPathString = filesPathString + "<br></span>";
			} else {
				filesPathString = filesPathString + "</span>";
			}
			k++;
		}
		json = "{file:'" + filesPathString + "'}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �õ��û��Ƿ��Ѿ��ύ����δ���������ĸ�����
	 * 
	 * @Methods Name getUserApply
	 * @Create In Jun 20, 2009 By gaowen
	 * @return String
	 */
	public String getUserApply() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItem = request.getParameter("serviceItemProcess");
		ServiceItemProcess serviceItemProcess = (ServiceItemProcess) getService()
				.find(ServiceItemProcess.class, serviceItem);
		String processType = request.getParameter("processType");
		UserInfo user = UserContext.getUserInfo();
		String userInfo = request.getParameter("userInfo");
		if (userInfo != null&&!userInfo.equals("")) {//modify by liuying at 20100510 for ������֤����
			user = (UserInfo) getService().find(UserInfo.class, userInfo);
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		String telephoneType = null;
		try {

			AccountApplyMainTable account = accountService.findUserApply(
					serviceItemProcess, user, processType);
			if (account != null) {
				Long id = account.getId();
				json = "{success:true}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����Ա����ְ�ʺ�ע�����������ʺ���Ϣ
	 * 
	 * @Methods Name saveEmployeeQuitSpecailAccount
	 * @Create In Sep 17, 2009 By gaowen
	 * @return String
	 */
	public String saveEmployeeQuitSpecailAccount() {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String acountId = request.getParameter("id");// �õ����������
		String applyReason = request.getParameter("applyReason");
		String applyId = request.getParameter("curId");
		String accountNewOwner = request.getParameter("accountNowUser");
		String userInfoId = request.getParameter("userInfoId");
		if (userInfoId != null && !userInfoId.equals("")) {
			accountNewOwner = userInfoId;
		}
		String ifHold = request.getParameter("ifHold");
		if (ifHold == null || ifHold.equals("null")) {
			ifHold = "0";
		}
		UserInfo newOwner = null;
		if (accountNewOwner != null && !accountNewOwner.equals("")) {
			newOwner = (UserInfo) getService().find(UserInfo.class,
					accountNewOwner);
		}
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, applyId);
		String id = aa.getId().toString();// �õ���ʵ��ID
		String name = aa.getName();// �õ�������
		SpecialAccount account = (SpecialAccount) getService().find(
				SpecialAccount.class, acountId);
		if (account.getAccountState().equals("1")) {
			SpecialAccount newAccount = new SpecialAccount();
			String[] ignoreProperties = { "id" };
			BeanUtils.copyProperties(account, newAccount, ignoreProperties);
			newAccount.setOlodApplyAccount(account);
			newAccount.setApplyId(aa);
			newAccount.setAccountNowUser(newOwner);
			newAccount.setApplyReason(applyReason);
			newAccount.setIfHold(Integer.parseInt(ifHold));
			newAccount.setAccountState("0");
			SpecialAccount newAccountApply = (SpecialAccount) getService()
					.save(newAccount);
		} else {
			account.setAccountNowUser(newOwner);
			account.setApplyReason(applyReason);
			account.setIfHold(Integer.parseInt(ifHold));
			account.setAccountState("0");
			SpecialAccount newAccountApply = (SpecialAccount) getService()
					.save(account);
		}
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ж������ʺ��Ƿ����
	 * 
	 * @Methods Name getSpecailAccountByName
	 * @Create In Jun 24, 2009 By gaowen
	 * @param
	 * @return String
	 */
	public String getSpecailAccountByName() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String accountName = request.getParameter("accountName");
		String accountType = request.getParameter("accountType");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "";
		String telephoneType = null;
		try {

			SpecialAccount account = accountService
					.findSpecailAccountByAccountName(accountType, accountName);
			if (account != null) {
				Long id = account.getId();
				json = "{success:true}";
			} else {
				json = "{success:false}";
			}
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	
	/**
	 * �ж��������Ƿ���ϵͳ��Ӧ�ù���Ա
	 * 
	 * @Methods Name isAppAdministrator
	 * @Create In 12 10, 2009 By gaowen
	 * @return String
	 */
	public String isAppAdministrator() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String user = request.getParameter("userInfo");
		UserInfo userInfo = (UserInfo) getService().find(UserInfo.class, user);
		String itCode=userInfo.getItcode().toLowerCase();
		List<SystemAppAdmin> administrators = getService().find(
				SystemAppAdmin.class, "itCode", itCode);
		String json = "{success:false}";
		Integer total = administrators.size();// ���ǲ�ѯ�����еļ�¼
		if (total > 0) {
			json = "{success:true}";
		}
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * ��ȡϵͳ��Ӧ�ù���Ա
	 * 
	 * @Methods Name listSystemAppAdmin
	 * @Create In Sep 27, 2009 By gaowen
	 * @return String
	 */
	public String listSystemAppAdmin() {
		int pageSize = 10;//ÿҳ����
		HttpServletRequest request = super.getRequest();//�õ�request
		String itcode = request.getParameter("itCode");//�õ�itCode
		int start = HttpUtil.getInt(request, "start", 1);
		int pageNo = start/pageSize+1;
		
		Page page = accountService.findSystemAppAdminByPage(itcode,
				pageNo, pageSize);
		List<SystemAppAdmin> list = page.list();

		Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (SystemAppAdmin admin : list) {
			Long id = admin.getId();
			String itCode=admin.getItCode();
			String realName=admin.getRealName();
			json += "{\"id\":\"" + id + "\",\"itCode\":\""
					+ itCode + "\",\"realName\":\""
					+ realName +  "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	


	

	/**
	 * �����������ͼ��ڵ������жϵ�ǰ�û���ɫ����
	 * 
	 * @Methods Name getUserRole
	 * @Create In OCT 15, 2009 By gaowen
	 * @return String
	 */
	public String getUserRole() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String applyId = request.getParameter("applyId");
		String applyType = request.getParameter("applyType");
		String taskName = request.getParameter("taskName");
		String taskId = request.getParameter("taskId");
		String defdesc = request.getParameter("defdesc");
		String json = "{success:true}";
		UserInfo curUser = UserContext.getUserInfo();
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, applyId);
		ServiceItemProcess sip = aa.getServiceItemProcess();
		VirtualDefinitionInfo vd = sip.getProcessInfo();
		Long id = vd.getId();
		int i = 0;
		List<ConfigUnitRole> configUtilRoles = getService().find(
				ConfigUnitRole.class, "processId", id);
		ConfigUnitRole configUnitRole = null;
		for (ConfigUnitRole cur : configUtilRoles) {
			if (cur.getNodeName().equals(taskName)) {
				configUnitRole = cur;
			}
		}
		if (configUnitRole != null) {
			Set roles = configUnitRole.getRoles();
			Iterator<Role> iter = roles.iterator();
			while (iter.hasNext()) {
				Role role = iter.next();
				Set userInfo = role.getUserInfos();
				if (userInfo.contains(curUser)) {
					i++;
				}
			}
		}
		if (i > 1) {
			json = "{success:false}";
		}

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ����û�������ɫ�б�
	 * 
	 * @Methods Name getUserAuditRole
	 * @Create In OCT 15, 2009 By gaowen
	 * @return String
	 */
	public String getUserAuditRole() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String applyId = request.getParameter("applyId");
		String taskName = request.getParameter("taskName");
		UserInfo curUser = UserContext.getUserInfo();
		AccountApplyMainTable aa = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, applyId);
		ServiceItemProcess sip = aa.getServiceItemProcess();
		VirtualDefinitionInfo vd = sip.getProcessInfo();
		Long id = vd.getId();
		List<ConfigUnitRole> configUtilRoles = getService().find(
				ConfigUnitRole.class, "processId", id);
		ConfigUnitRole configUnitRole = null;
		for (ConfigUnitRole cur : configUtilRoles) {
			if (cur.getNodeName().equals(taskName)) {
				configUnitRole = cur;
			}
		}
		String json = "";
		Integer total = 0;
		if (configUnitRole != null) {
			Set roles = configUnitRole.getRoles();
			total = roles.size();// ���ǲ�ѯ�����еļ�¼

			Iterator<Role> iter = roles.iterator();
			while (iter.hasNext()) {
				Role role = iter.next();
				Long ids = role.getId();
				String accountName = role.getName();
				json += "{\"id\":\"" + ids + "\",\"name\":\"" + accountName
						+ "\" },";
			}
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ������������Ϣ���������δ�ҵ���ȥR3���ݿ��ң����ҵ����沢���ء�
	 * @Methods Name findApplyUser
	 * @Create In Dec 21, 2009 By duxh
	 * @return String
	 */
	public String findApplyUser(){
		try {
			String json="";
			HttpServletRequest request = super.getRequest();
			String itcode = request.getParameter("itcode").toUpperCase();
			UserInfo userInfo=(UserInfo) getService().findUnique(UserInfo.class, "itcode", itcode);
			if(userInfo==null){
//				SAPExecute sap=new SAPExecute();
//				Map userMap=sap.getUserInfo(null, itcode);
				SenseServicesUitl ssUtil = new SenseServicesUitl();
				Map userMap = ssUtil.getUserInfo(itcode);
				if(userMap.isEmpty())
					json="{success:false}";
				else{
					userMap.put("password", "000000");
					userMap.put("enabled", 1);
					userMap.put("isLock", 0);
					userMap.put("specialUser", 0);
					userInfo=(UserInfo) metaDataManager.saveEntityData(UserInfo.class, userMap);
				}
			}
			if(userInfo!=null){
				Department dept=(Department) getService().findUnique(Department.class, "departCode", userInfo.getDepartment().getId());
				json="{success:true,userInfo:{" +
						"id:'"+userInfo.getId()+"'," +
						"info:'"+userInfo.getRealName()+"/"+
								 userInfo.getItcode().toLowerCase()+"/"+
								 dept.getDepartName()+
					  "'}}";
			}
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw=response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * ���ø���DCͨѶ¼webservice
	 * @Methods Name updateDCContacts
	 * @Create In Jan 27, 2010 By lee
	 * @param employeeCode
	 * @param telephone
	 * @param mobilePhone
	 * @param isdelete
	 * @param voipPhone void
	 */
	private void updateDCContacts(String employeeCode,String telephone,String mobilePhone,String voipPhone,boolean isDelete){
		UserInfo user=(UserInfo) getService().findUnique(UserInfo.class, "employeeCode",employeeCode);
		if(user!=null){
			user.setTelephone(telephone);
			user.setMobilePhone(mobilePhone);
			getService().save(user);
		}
		HrInfoService hs = new HrInfoServiceLocator();
		try {
			HrInfoServiceSoap_PortType hp = hs.getHrInfoServiceSoap();
			int reslut = hp.updatePhone(employeeCode,telephone,mobilePhone,voipPhone,isDelete);
			if(reslut==1){
				System.out.println("����HR����ͨѶ¼[Ա����ţ�"+employeeCode+" ][�ֻ�:"+mobilePhone+"][ ����:"+telephone+" ][voip:"+voipPhone+"]��Ϣ�ɹ���");
			}else{
				System.out.println("����HR����ͨѶ¼"+employeeCode+"��Ϣʧ�ܣ�");
				System.out.println("ERROR:"+(reslut==-1?"����ʧ��":"û��Ȩ��"));
			}
		} catch (ServiceException e) {
			System.out.println("����HR����ͨѶ¼webservice����ʧ�ܣ�");
			e.printStackTrace();
		} catch (RemoteException e) {
			System.out.println("����HR����ͨѶ¼webservice����ʧ�ܣ�");
			e.printStackTrace();
		}
	}
	/**
	 * ͨ���û����õ�Ա�����
	 * @Methods Name getEmployeeCode
	 * @Create In Mar 25, 2010 By liuying
	 * @return String
	 */
	public String getEmployeeCode(){
		try {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
			String username = request.getParameter("userName");
			UserInfo userInfo=null;
			if(username!=null&&!username.equals("")){
				 userInfo=(UserInfo) getService().findUnique(UserInfo.class, "itcode", username.toUpperCase());
			}
			String json="";
			if(userInfo!=null){
				String code=userInfo.getEmployeeCode();
				json="{success:true,code:'"+code+"'}";
			}else{
				json="{success:false}";
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter	pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * �г����������ʺ�
	 * @Methods Name listAllTelephoneAccount
	 * @Create In Mar 25, 2010 By liuying
	 * @return String
	 */
	
	public String listAllTelephoneAccount(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String json = "";
		int pageSize = 10;
		int start = Integer.parseInt(request.getParameter("start"));
		int pageNo = start / pageSize + 1;
		//String orderBy = "id";
		//boolean isAsc = true;
		String number = request.getParameter("number");
		String curId = request.getParameter("id");
		if(curId!=null&&curId!=""){
			PersonFormalAccount pfa = (PersonFormalAccount) accountService.getAllTelephoneAccount("����", number).get(0);
			json+="{id:'"+pfa.getTelephoneNumber()+"',number:'"+pfa.getTelephoneNumber()+"'}";
			json = "{success: true, rowCount:'1',data:["+json+"]}";
		}else{
			Page page = accountService.getAllTelephone(number, pageNo, pageSize);
			Long total = page.getTotalCount();
			List<PersonFormalAccount> queryList = page.list();
			for(PersonFormalAccount pfa : queryList){
				json+="{id:'"+pfa.getTelephoneNumber()+"',number:'"+pfa.getTelephoneNumber()+"'},";
			}
			if (json.length() == 0) {
				json = "{success:true,rowCount:" + "1" + ",data:["
						+ json.substring(0, json.length()) + "]}";
			} else {
				json = "{success:true,rowCount:" + total + ",data:["
						+ json.substring(0, json.length() - 1) + "]}";
			}
		}
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	/**
	 * �õ��ʺ���Ϣ
	 * @Methods Name getAccountInfo
	 * @Create In Mar 25, 2010 By liuying
	 * @return String
	 */
	public String getAccountInfo(){
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String number = request.getParameter("number");
			List<PersonFormalAccount> accounts = accountService.getAllTelephoneAccount(
					"����", number);

			String json="";
			if(accounts!=null){
				String draw="";
				String userName="";
				if(accounts.get(0).getWorkSpace()!=null){
					draw=accounts.get(0).getWorkSpace().getId().toString();
				}
				UserInfo user=accounts.get(0).getAccountowner();
				if(user!=null){
					userName=user.getRealName()+"/"+user.getUserName()+"/"+user.getDepartment().getDepartName();
				}
				
				json="{success:true,drawspace:'"+draw+"',name:'"+userName+"',num:'"+accounts.size()+"'}";
				
			}else{
				json="{success:false}";
			}
			response.setCharacterEncoding("utf-8");
			PrintWriter	pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	}
	/**
	 * �������������߱���ݸ�
	 * @Methods Name saveTelephoneOwnerChangeDraft
	 * @Create In Mar 26, 2010 By liuying
	 * @return String
	 */
	public String saveTelephoneOwnerChangeDraft(){

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		UserInfo curUser = UserContext.getUserInfo();
		String panelName = request.getParameter("panelName");// �õ����������
		
		String serviceItemId = request.getParameter("serviceItemId");
		// String accountType = request.getParameter("accountType");
		String processInfoId = request.getParameter("processInfoId");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findServiceItemProcessById(processInfoId);
		String info = request.getParameter("info");
		String processType = request.getParameter("processType");
		Date currentDate = DateUtil.getCurrentDate();
		JSONObject panelJO = JSONObject.fromObject(info);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator columnIter = panelJO.keys();
		while (columnIter.hasNext()) {
			String columnName = (String) columnIter.next();
			String columnValue = panelJO.getString(columnName);
			dataMap.put(columnName, columnValue);
		}
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		/** *******************************��ȡ������ʵ������************************************************* */
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
		String mainTableName = mainTable.getTableName(); // �õ�������
		Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
		Map mainMap = new HashMap();

		List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
				.getSystemMainTable());
		for (Column column : mColumn) {
			String propertyName = column.getPropertyName();
			String tableColumnName = mainTableName + "$" + propertyName;
			if (dataMap.containsKey(tableColumnName)) {
				Object columnValue = dataMap.get(tableColumnName);
				mainMap.put(propertyName, columnValue);
			}
		}

		if ("".equals(mainMap.get("id"))) {
			mainMap.put("createDate", currentDate);
			mainMap.put("createUser", UserContext.getUserInfo());
			mainMap.put("processType", processType);
			mainMap.put("status", Constants.STATUS_DRAFT);
		}
		mainMap.put("serviceItem", serviceItemId);
		mainMap.put("serviceItemProcess", serviceItemProcess);
		if (!mainMap.containsKey("delegateApplyUser")) {
			mainMap.put("delegateApplyUser", UserContext.getUserInfo());
		}

		BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
				mainClass, mainMap);// ���汻������ʵ��
		String id = mainObject.getId().toString();// �õ�����ID
		List<PersonFormalAccount> accounts = getService().find(PersonFormalAccount.class,
				"applyId", mainObject);
		if (accounts.size() >= 1) {
			for (PersonFormalAccount account : accounts) {
				getService().remove(account);
			}
		}
		String name = mainObject.getName();// �õ�������
		
		String telNumber=(String) dataMap.get("itil_ac_PersonFormalAccount$telephoneNumber");
		String reason=(String) dataMap.get("itil_ac_PersonFormalAccount$remarkDesc");
		String applyuser=(String) dataMap.get("AccountApplyMainTable$applyUser");
		
		List<PersonFormalAccount> pfas = accountService.getAllTelephoneAccount(
				"����", telNumber);
		UserInfo newOwner = (UserInfo) getService().find(UserInfo.class,
				applyuser);
		if(pfas!=null){
			for(PersonFormalAccount pfa:pfas){
				PersonFormalAccount newpfa=new PersonFormalAccount();
				String[] ignoreProperties = { "id" };
				BeanUtils.copyProperties(pfa, newpfa, ignoreProperties);
				newpfa.setOlodApplyAccount(pfa);
				newpfa.setApplyId((AccountApplyMainTable) mainObject);
				newpfa.setAccountowner(newOwner);
				newpfa.setRemarkDesc(reason);
				newpfa.setAccountState("0");
				newpfa.setAccountName(newOwner.getUserName());
				PersonFormalAccount account = accountService.findPersonAccount(
						"����", newOwner);
				if(account!=null){
					newpfa.setDepartTelephone("1");
				}else{
					newpfa.setDepartTelephone("0");
				}
				getService().save(newpfa);
			}
		}
		
		String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	public String getTelOwnerChangeDraftData(){
	     
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		String dataId = request.getParameter("dataId");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable mainTable = panel.getSystemMainTable(); // �õ��������
		String tableName = mainTable.getTableName(); // �õ����������
		Class clazz = this.toClass(mainTable.getClassName()); // �õ������ʵ����
		Object mainObj = getService().find(clazz, dataId, true); // �õ������ʵ��
		BeanWrapper bWrapper = new BeanWrapperImpl(mainObj);
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				mainObj, tableName);
		List<PersonFormalAccount> fObj = getService().find(PersonFormalAccount.class,
				"applyId", mainObj); // ��ȡ����ʵ��
		for (PersonFormalAccount acc : fObj) {
			Map<String, Object> tempMap = metaDataManager.getFormDataForEdit(
					acc, "itil_ac_PersonFormalAccount");

			dataMap.putAll(tempMap);

		}

		Map<String, Object> userMap = metaDataManager.getFormDataForEdit(
				applyUser, "sUserInfos");

		dataMap.putAll(userMap);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);

		String json = "{success:" + true + ",form:" + mapToJson(dataMap)
				+ "}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	/**
	 * ͨ��dataId��÷�����Ϣ
	 * @Methods Name getApplyInfo
	 * @Create In Apr 6, 2010 By liuying
	 * @return String
	 */
	public String getApplyInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		
		AccountApplyMainTable aamt = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);
		
		Integer processType=aamt.getProcessType();
		String serviceItemId=aamt.getServiceItem().toString();
		ServiceItemProcess serviceItemProcess=aamt.getServiceItemProcess();
		String serviceItempro=serviceItemProcess.getId().toString();
		
		Integer status = aamt.getStatus();
		
		VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
		String vname = virtualDefinitionInfo.getVirtualDefinitionName();
		String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "{serviceItemId:'"+serviceItemId+"',"+
						"processType:'"+processType+"',"+
						"serviceItemProcessId:'"+serviceItempro+"',"+
						"processName:'"+vname+"',"+
						"description:'"+vdescription+"',"+
						"status:'"+status+"',"+
						"dataId:'"+dataId+"'}";
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �������������ж���������Ƿ��������е�����
	 * @Methods Name getNumberApply
	 * @Create In Jun 22, 2010 By liuying
	 * @return String
	 */
	public String getNumberApply(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String number = request.getParameter("number");
		String serviceItemProcessId = request.getParameter("serviceItemProcess");
		
		List<PersonFormalAccount> accounts = accountService.findAllTelephoneAccount(
				"����", number);
		
		String json="{success:true}";

		if(accounts!=null){
			for(PersonFormalAccount amt:accounts){
				AccountApplyMainTable aamt=amt.getApplyId();
				if(aamt!=null){
					String sip=aamt.getServiceItemProcess().getId().toString();
					String status=aamt.getStatus().toString();
					if(sip.equals(serviceItemProcessId)&&status.equals("1")){
						json="{success:false}";
					}
				}
			}
		}
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter	pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ���ERP����
	 * @Methods Name getErpFiles
	 * @Create In Jun 22, 2010 By liuying
	 * @return String
	 */
	public String getErpFiles(){

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String columnName = request.getParameter("columnName");
		String columnid = request.getParameter("columnid");
		AccountApplyMainTable ob1 = (AccountApplyMainTable)baseBervice.find(AccountApplyMainTable.class, dataId);
		Object value = ob1.getAttachment();
		
		String json = "";
		List<SystemFile> systemfileList = baseBervice.find(SystemFile.class,
				"nowtime", value);
		String filesPathString = "";
		int k = 1;
		for (SystemFile filesTemp : systemfileList) {
			filesPathString += "<span id =su_" + filesTemp.getId()
					+ "><img src='+webContext+'/images/other/file.gif >"
					+ "&nbsp;<a href='+webContext+'"
					+ "/fileDown.do?methodCall=downloadFile&id="
					+ filesTemp.getId() + "&columnId=" + columnid + ">"
					+ filesTemp.getFileName() + "</a>&nbsp;&nbsp;";
			if (k % 4 == 0) {
				filesPathString = filesPathString + "<br></span>";
			} else {
				filesPathString = filesPathString + "</span>";
			}
			k++;
		}
		json = "{file:'" + filesPathString + "'}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	/**
	 * �жϼ�Ҫ���޸������ʺ�ʱ�Ƿ�ϵͳ���Ѵ����������
	 * @Methods Name getTelAccount
	 * @Create In May 17, 2010 By liuying
	 * @return String
	 */
	public String getTelAccount(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String telnum = request.getParameter("num");
		List<PersonFormalAccount> accounts = accountService.getAllTelephoneAccount(
				"����", telnum);
		String json="{success:true}";
		if(accounts.size()!=0){
			json="{success:false}";
		}
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �ж��û��ύ�����»�ȡID�ļ������е�id�ļ��Ƿ��������еĻ�ȡ����
	 * @Methods Name getIDfileApply
	 * @Create In May 24, 2010 By liuying
	 * @return String
	 */
	public String getIDfileApply(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String idname = request.getParameter("idname");
		List<NotesIDFile> ids = getService().find(NotesIDFile.class,
				"fileName", idname);
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		String json = "{success:false}";
		if(ids.size()!=0){
			for(NotesIDFile id:ids){
				if(id.getApplyId().getStatus()==1){
					json = "{success:true}";
				}
			}
		}else{
			json = "{success:false}";
		}
		
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ����Զ�̽����ʺŵ��ʺ�����
	 * @Methods Name saveRemoteAccountType
	 * @Create In May 26, 2010 By liuying
	 * @return String
	 */
	public String saveRemoteAccountType(){

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String vpnType = request.getParameter("vpnType");
		String drawSpace = request.getParameter("drawSpace");
		String isSpAccount = request.getParameter("isSpAccount");//0Ϊ��ʽ�ʺ� 1Ϊ��ʱ�ʺ�
		AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
				AccountApplyMainTable.class, dataId);
		List<PersonFormalAccount> accounts=new ArrayList();
		List<SpecialAccount> spaccounts=new ArrayList();
		if(isSpAccount.equals("0")){
			 accounts = getService().find(
					PersonFormalAccount.class, "applyId", aam);
		}else{
			spaccounts = getService().find(
					SpecialAccount.class, "applyId", aam);
		}
		AR_DrawSpace space=(AR_DrawSpace) getService().find(AR_DrawSpace.class, drawSpace);
		String confirmUser=space.getConfirmUser();
		String[] usernames=confirmUser.split(",");
		String signuser="";
		for(int i=0;i<usernames.length;i++){
			UserInfo s=(UserInfo) getService().findUnique(UserInfo.class, "userName",usernames[i]);
			signuser=signuser+s.getId().toString()+",";
		}
		String signuserid=signuser.substring(0,signuser.length()-1 );
		if(isSpAccount.equals("0")){
			for (PersonFormalAccount personAccount : accounts) {
				personAccount.setVpnType(vpnType);
				personAccount.setDrawSpace(space);
				PersonFormalAccount pa = (PersonFormalAccount) getService().save(
						personAccount);
			}
		}else{
			for (SpecialAccount personAccount : spaccounts) {
				personAccount.setVpnType(vpnType);
				personAccount.setDrawSpace(space);
				SpecialAccount pa = (SpecialAccount) getService().save(
						personAccount);
			}
		}
		String json = "{success:true,user:'"+signuserid+"'}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	/**
	 * �ж�Զ�̽����ʺŵ�����
	 * @Methods Name getVpnAccountType
	 * @Create In May 27, 2010 By liuying
	 * @return String
	 */
	public String getVpnAccountType(){

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userInfo = request.getParameter("userInfo");
		
		UserInfo user = (UserInfo) getService().find(
				UserInfo.class, userInfo);
		PersonFormalAccount account = accountService.findPersonAccount(
				"Զ�̽����ʺ�", user);
		String type=account.getVpnType();
		String json = "{success:true}";
		if(type.equals("1")){
			json = "{success:false}";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	
	}
	
		private String mapToJson(Map map){
		String json = "";
		if(map!=null){
			Set keys = map.keySet();
			for(Object key : keys){
				json += "\""+key.toString() + "\":\"" + (map.get(key)==null?"":map.get(key).toString()) +"\",";
			}
			if(json.length()>0){
				json = json.substring(0, json.length() - 1);
			}
			json = "[{" + json +"}]";
		}
		return json;
	}
		/**
		 * ������ʱԶ�̽����ʺ�����������Ϣ
		 * @Methods Name saveTempVpnAccountInfo
		 * @Create In May 31, 2010 By liuying
		 * @return String
		 */
		public String saveTempVpnAccountInfo(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String vpnType = request.getParameter("vpnType");
			String dataId = request.getParameter("dataId");
			String userInfo = request.getParameter("userInfo");
			String cardNumber = request.getParameter("cardNumber");
			String rightDesc = request.getParameter("rightDesc");
			Date endDate=null;
			String pingCode="";
			Date currentDate = DateUtil.getCurrentDate();
			AccountApplyMainTable aam = (AccountApplyMainTable) getService().find(
					AccountApplyMainTable.class, dataId);
			List<SpecialAccount> account = getService().find(
					SpecialAccount.class, "applyId", aam);
			if(vpnType.equals("1")){
				String attachmentFlag = request.getParameter("attachmentFlag");
				aam.setAttachment(attachmentFlag);
				getService().save(aam);
				pingCode = request.getParameter("pingCode");
				endDate=DateUtil.convertStringToDate("9999-12-31");
			}else{
				String end = request.getParameter("endDate");
				endDate = DateUtil.convertStringToDate(end);
			}
			for (SpecialAccount personAccount : account) {
				personAccount.setCardNumber(cardNumber);
				personAccount.setPassword("123");
				personAccount.setCreateDate(currentDate);
				personAccount.setRightsDesc(rightDesc);
				personAccount.setEndDate(endDate);
				personAccount.setIfHold(1);
				personAccount.setCardState(1);
				personAccount.setVpnType(vpnType);
				if(vpnType.equals("1")){
					personAccount.setPingCode(pingCode);
				}
				 getService().save(personAccount);
			}
			String json = "{success:true}";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * �ж���ʱ�ʺ��Ƿ���ں��Ƿ��������е�����
		 * @Methods Name getTempAccountApply
		 * @Create In Jun 22, 2010 By liuying
		 * @return String
		 */
		public String getTempAccountApply(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String accountName = request.getParameter("accountName");
			String accountType = request.getParameter("accountType");
			String serviceItemProcessId = request.getParameter("serviceItemProcess");
		
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			String json = "";
			String telephoneType = null;
			try {

				SpecialAccount account = accountService
						.findSpecailAccountByAccountName(accountType, accountName);
				if (account != null) {
					json = "{success:true,type:'hasaccount'}";
				} else {
					json = "{success:false}";
					List<SpecialAccount> accounts=accountService.findAllSpecailAccountByAccountName(accountType, accountName);
					if(accounts.size()!=0){
						for(SpecialAccount sa:accounts){
							AccountApplyMainTable aamt=sa.getApplyId();
							if(aamt!=null){
								String sip=aamt.getServiceItemProcess().getId().toString();
								String status=aamt.getStatus().toString();
								if(sip.equals(serviceItemProcessId)&&status.equals("1")){
									json="{success:true,type:'hasapply'}";
								}
							}
						}
					}
				}
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		
		}
		/**
		 * �г�����win7�ʺ�
		 * @Methods Name listAllWin7Account
		 * @Create In Jun 22, 2010 By admin
		 * @return String
		 */
		public String listAllWin7Account(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String ownerName=request.getParameter("ownerName");
			String confirmUserName=request.getParameter("managerName");
			int start = HttpUtil.getInt(request, "start", 0);
			int pageSize = HttpUtil.getInt(request, "pageSize", 10);
			int pageNo = start/pageSize +1;

			Page page=accountService.findSpecailAccountByUsername("Win7",ownerName,confirmUserName,pageNo,pageSize);
			String jsons="";
			StringBuilder json=new StringBuilder();
			Long total = page.getTotalCount();
			List<SpecialAccount> sas = page.list();
			for(SpecialAccount sa:sas){
				UserInfo owner=sa.getAccountNowUser();
				UserInfo manager=sa.getConfirmUser();
				Win7PlatForm pf=sa.getWin7PaltForm();
				String platformname="";
				String pfId="";
				String ownername="";
				String ownerId="";
				String managername="";
				String managerId="";
				
				if(owner!=null){
					ownername=owner.getUserName()+"/"+owner.getRealName();
					ownerId=owner.getId().toString();
				}
				if(manager!=null){
					managername=manager.getUserName()+"/"+manager.getRealName();
					managerId=manager.getId().toString();
				}
				if(pf!=null){
					platformname=pf.getName();
					pfId=pf.getId().toString();
				}
				String remarkDesc="";
				if(sa.getRemarkDesc()!=null){
					remarkDesc=sa.getRemarkDesc();
				}
				String hardwareId="";
				if(sa.getHardwareId()!=null){
					hardwareId=sa.getHardwareId();
				}
				String deviceName="";
				Long deviceId=null;
				if(sa.getDeviceType()!=null){
					deviceName=sa.getDeviceType().getDeviceName();
					deviceId=sa.getDeviceType().getId();
				}
				json.append("{\"id\":\"" + sa.getId() + "\",\"accountNowUser\":\"" + ownername
				 + "\",\"accountNowUserId\":\"" + ownerId + "\",\"hardwareId\":\"" + hardwareId
				+ "\",\"accountManager\":\"" + managername+ "\",\"accountManagerId\":\"" + managerId
				+ "\",\"deviceId\":\"" + deviceId+ "\",\"deviceName\":\"" + deviceName+ "\",\"platFormId\":\"" + pfId
				+ "\",\"platForm\":\"" + platformname + "\",\"remarkDesc\":\"" + remarkDesc
				+ "\",\"createDate\":\"" + sa.getCreateDate()+ "\",\"accountName\":\"" + sa.getAccountName()
				+ "\"},");
			}
			
			if (json.length() == 0) {
				jsons = "{success:true,rowCount:" + "1" + ",data:["
						+ json.substring(0, json.length()) + "]}";
			} else {
				jsons = "{success:true,rowCount:" + total + ",data:["
						+ json.substring(0, json.length() - 1) + "]}";
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(jsons);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		/**
		 * �޸�win7�ʺ�
		 * @Methods Name modifyWin7Account
		 * @Create In Jun 22, 2010 By liuying
		 * @return String
		 */
		public String modifyWin7Account(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String owner=request.getParameter("owner");
			String confirmUser=request.getParameter("confirmUser");
			String Win7pf=request.getParameter("platform");
			String deviceId=request.getParameter("deviceId");
			String rightsDesc=request.getParameter("rightsDesc");
			String accountid=request.getParameter("accountid");
			String hardwardId=request.getParameter("hardwardId");
			SpecialAccount sa=(SpecialAccount) getService().find(SpecialAccount.class, accountid);
			String json = "{success:true}";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
		
			if(sa!=null){
				UserInfo accountOwner=(UserInfo) getService().find(UserInfo.class, owner);
				if(accountOwner!=null){
					sa.setAccountNowUser(accountOwner);
				}
				UserInfo accountConfirmUser=(UserInfo) getService().find(UserInfo.class, confirmUser);
				if(accountConfirmUser!=null){
					sa.setConfirmUser(accountConfirmUser);
				}
				Win7PlatForm pf=(Win7PlatForm) getService().find(Win7PlatForm.class, Win7pf);
				if(pf!=null){
					sa.setWin7PaltForm(pf);
				}
				sa.setRemarkDesc(rightsDesc);
				DeviceType deviceType=(DeviceType) getService().find(DeviceType.class, deviceId);
				if(deviceType!=null){
					sa.setDeviceType(deviceType);
				}
				sa.setHardwareId(hardwardId);
				getService().save(sa);
			}
			
			AccountModifyDesc amd=new AccountModifyDesc();
			amd.setAccountFlag(AccountModifyDesc.SPECAILACCOUNT);
			amd.setAccountId(accountid);
			UserInfo curUser = UserContext.getUserInfo();
			amd.setAccountManger(curUser.getItcode());
			Date currentDate = DateUtil.getCurrentDateTime();
			amd.setModifyDate(currentDate);
			getService().save(amd);
		
			
			
			try {
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;	
		}
		/**
		 * ����win7�ʺ�
		 * @Methods Name addWin7Account
		 * @Create In Jun 22, 2010 By liuying
		 * @return String
		 */
		public String addWin7Account(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String owner=request.getParameter("owner");
			String confirmUser=request.getParameter("confirmUser");
			String win7pf=request.getParameter("platform");
			String deviceId=request.getParameter("deviceId");
			String rightsDesc=request.getParameter("rightsDesc");
			String hardWareId=request.getParameter("hardwareId");
			SpecialAccount sa=new SpecialAccount();
			String json = "{success:true}";
			
			if("".equals(owner)||owner==null){
				owner=confirmUser;
			}
				UserInfo accountOwner=(UserInfo) getService().find(UserInfo.class, owner);
				if(accountOwner!=null){
					sa.setAccountNowUser(accountOwner);
				}
				UserInfo accountConfirmUser=(UserInfo) getService().find(UserInfo.class, confirmUser);
				if(accountConfirmUser!=null){
					sa.setConfirmUser(accountConfirmUser);
				}
				Win7PlatForm pf=(Win7PlatForm) getService().find(Win7PlatForm.class, win7pf);
				if(pf!=null){
					sa.setWin7PaltForm(pf);
				}
				sa.setRemarkDesc(rightsDesc);
				DeviceType deviceType=(DeviceType) getService().find(DeviceType.class, deviceId);
				if(deviceType!=null){
					sa.setDeviceType(deviceType);
				}
				sa.setAccountState("1");
				AccountType at = (AccountType) getService().find(AccountType.class, "24");
				sa.setAccountType(at);
				Date currentDate = DateUtil.getCurrentDate();
				sa.setCreateDate(currentDate);
				sa.setHardwareId(hardWareId);
				UserInfo curUser = UserContext.getUserInfo();
				if(curUser!=null){
					sa.setWriterItcode(curUser.getItcode());
				}
				sa=(SpecialAccount) getService().save(sa);
				sa.setAccountName(sa.getId().toString());
				getService().save(sa);
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;	
		}
		/**
		 * �����ʺ����жϸ��ʺ��Ƿ��������е�����
		 * @Methods Name getAccountNameApply
		 * @Create In Jun 22, 2010 By liuying
		 * @return String
		 */
		public String getAccountNameApply(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String accountId = request.getParameter("accountId");
			SpecialAccount spa=(SpecialAccount) getService().find(SpecialAccount.class, accountId);
			String accountName = spa.getAccountName();
			String accountType = request.getParameter("accountType");
			String serviceItemProcessId = request.getParameter("serviceItemProcess");
		
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			String json = "";
			String telephoneType = null;
			try {
				
				json = "{success:false}";
				List<SpecialAccount> accounts=accountService.findAllSpecailAccountByAccountName(accountType, accountName);
				if(accounts.size()!=0){
					for(SpecialAccount sa:accounts){
						AccountApplyMainTable aamt=sa.getApplyId();
						if(aamt!=null){
							String sip=aamt.getServiceItemProcess().getId().toString();
							String status=aamt.getStatus().toString();
							if(sip.equals(serviceItemProcessId)&&status.equals("1")){
								json="{success:true}";
							}
						}
					}
				}
				
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		
		}
		/**
		 * �г��û����µ�win7�ʺ�
		 * @Methods Name listWin7AccountName
		 * @Create In Jun 22, 2010 By liuying
		 * @return String
		 */
		public String listWin7AccountName() {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			UserInfo user = UserContext.getUserInfo();
			String accountIdStr = request.getParameter("id");
			String userInfo = request.getParameter("userInfo");
			if (userInfo != null&&!userInfo.equals("")) {
				user = (UserInfo) getService().find(UserInfo.class, userInfo);
			}
			int pageSize = 10;//ÿҳ����
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			Long accountId=null;
			if(accountIdStr!=null&&accountIdStr.trim().length()!=0){
				accountId=Long.valueOf(accountIdStr);
			}
			Page page = accountService.findWin7AccountByUser(accountId,user, pageNo, pageSize);
			List<SpecialAccount> account = page.list();
			Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
			
			
			String json = "";
			for (SpecialAccount acc : account) {
				Long id = acc.getId();
				String accountName = acc.getAccountName();
				String deviceId ="--";
				if(acc.getDeviceType()!=null){
					deviceId=acc.getDeviceType().getDeviceName();
				}
				String hardwareId = acc.getHardwareId();
				json += "{\"id\":\"" + id + "\",\"accountName\":\"" + accountName+"/"+deviceId+"/"+hardwareId
				+ "\" },";
			}
			if (json.length() == 0) {
				json = "{success:true,rowCount:" + "1" + ",data:["
				+ json.substring(0, json.length()) + "]}";
			} else {
				json = "{success:true,rowCount:" + total + ",data:["
				+ json.substring(0, json.length() - 1) + "]}";
			}
			
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * �г����в��������ʺ�
		 * @Methods Name listAllDeptTelephoneAccount
		 * @Create In Mar 25, 2010 By liuying
		 * @return String
		 */
		
		public String listAllDeptTelephoneAccount(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			
			String json = "";
			int pageSize = 10;
			int start = Integer.parseInt(request.getParameter("start"));
			int pageNo = start / pageSize + 1;
			String number = request.getParameter("number");
			String curId = request.getParameter("id");
			if(curId!=null&&curId!=""){
				PersonFormalAccount pfa = (PersonFormalAccount) accountService.getAllDeptTelephoneAccount("����", number).get(0);
				json+="{id:'"+pfa.getTelephoneNumber()+"',number:'"+pfa.getTelephoneNumber()+"'}";
				json = "{success: true, rowCount:'1',data:["+json+"]}";
			}else{
				Page page = accountService.getAllDeptTelephone(number, pageNo, pageSize);
				Long total = page.getTotalCount();
				List<PersonFormalAccount> queryList = page.list();
				for(PersonFormalAccount pfa : queryList){
					json+="{id:'"+pfa.getTelephoneNumber()+"',number:'"+pfa.getTelephoneNumber()+"'},";
				}
				if (json.length() == 0) {
					json = "{success:true,rowCount:" + "1" + ",data:["
							+ json.substring(0, json.length()) + "]}";
				} else {
					json = "{success:true,rowCount:" + total + ",data:["
							+ json.substring(0, json.length() - 1) + "]}";
				}
			}
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		
		}
		
		/**
		 * ��������ɾ���ݸ���Ϣ
		 * @Methods Name saveTelephoneDeleteDraft
		 * @Create In Jun 23, 2010 By liuying
		 * @return String
		 */
		public String saveTelephoneDeleteDraft() {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String panelName = request.getParameter("panelName");// �õ����������
			String serviceItemId = request.getParameter("serviceItemId");
			String isDeptTel = request.getParameter("isdepttel");
			
			ServiceItem serviceItem = (ServiceItem) getService().find(
					ServiceItem.class, serviceItemId);
			String telephoneNumber = request.getParameter("telephoneNumber");
			String accountType = request.getParameter("accountType");
			PersonFormalAccount accounts = null;
			if(isDeptTel.equals("0")){
				UserInfo curUser = null;
				String userInfo = request.getParameter("userInfo");
				if (userInfo != null && StringUtils.isNotBlank(userInfo)) {
					curUser = (UserInfo) getService().find(UserInfo.class, userInfo);
				}
				accounts = accountService.findPersonAccount(accountType, curUser);
			}else{
				accounts = accountService.getAllDeptTelephoneAccount(
						accountType, telephoneNumber).get(0);
			}
			
			if (accounts == null) {
				throw new RuntimeException();
			}
			String info = request.getParameter("info");
			String processType = request.getParameter("processType");
			String processInfoId = request.getParameter("processInfoId");
			String applyReason = request.getParameter("applyReason");
			ServiceItemProcess serviceItemProcess = serviceItemProcessService
					.findServiceItemProcessById(processInfoId);
			Date currentDate = DateUtil.getCurrentDate();
			JSONObject panelJO = JSONObject.fromObject(info);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Iterator columnIter = panelJO.keys();
			while (columnIter.hasNext()) {
				String columnName = (String) columnIter.next();
				String columnValue = panelJO.getString(columnName);
				dataMap.put(columnName, columnValue);
			}
			PagePanel panel = pagePanelService.findPagePanel(panelName);
			/** *******************************��ȡ������ʵ������************************************************* */
			SystemMainTable mainTable = panel.getSystemMainTable(); // �õ���������
			String mainTableName = mainTable.getTableName(); // �õ�������
			Class mainClass = this.toClass(mainTable.getClassName());// �õ�������ʵ����
			Map mainMap = new HashMap();

			List<Column> mColumn = systemColumnService.findSystemTableColumns(panel
					.getSystemMainTable());
			for (Column column : mColumn) {
				String propertyName = column.getPropertyName();
				String tableColumnName = mainTableName + "$" + propertyName;
				if (dataMap.containsKey(tableColumnName)) {
					Object columnValue = dataMap.get(tableColumnName);
					mainMap.put(propertyName, columnValue);
				}
			}
			if ("".equals(mainMap.get("id"))) {
				mainMap.put("createDate", currentDate);
				mainMap.put("createUser", UserContext.getUserInfo());
				mainMap.put("processType", processType);
				mainMap.put("status", Constants.STATUS_DRAFT);
			}
			mainMap.put("serviceItem", serviceItemId);

			mainMap.put("serviceItemProcess", serviceItemProcess);

			Object acUser = null;
			if (mainMap.containsKey("useUser")) {
				acUser = mainMap.get("useUser");
			} else {
				acUser = mainMap.get("applyUser");
			}
			if (!mainMap.containsKey("delegateApplyUser")) {
				mainMap.put("delegateApplyUser", acUser);
			}

			/** *******************************�����˺�ʵ��START************************************************* */

			AccountType at = (AccountType) getService().findUnique(
					AccountType.class, "name", accountType);

			SystemMainTable msmt = systemMainTableService
					.findSystemMainTableByClazz(PersonFormalAccount.class); // ��ȡ��������
			String msmtName = msmt.getTableName();
			Class account = this.toClass(msmt.getClassName());
			Map temp = new HashMap();
			List<Column> tempColumn = systemColumnService
					.findSystemTableColumns(msmt);
			for (Column column : tempColumn) {
				String propertyName = column.getPropertyName();
				String tableColumnName = msmtName + "$" + propertyName;
				if (dataMap.containsKey(tableColumnName)) {
					Object columnValue = dataMap.get(tableColumnName);
					temp.put(propertyName, columnValue);
				}
			}
			BaseObject mainObject = (BaseObject) metaDataManager.saveEntityData(
					mainClass, mainMap);// ���汻������ʵ��
			String id = mainObject.getId().toString();// �õ���ʵ��ID
			String name = mainObject.getName();// �õ�������
			temp.put("accountState", "0");
			temp.put("accountType", at);
			temp.put("createDate", currentDate);
			temp.put("applyId", mainObject);
			temp.put("ifHold", 1);
			temp.put("cardState", 1);
			temp.put("accountowner", acUser);
			temp.put("yearMoney", accounts.getYearMoney());
			temp.put("departTelephone", accounts.getDepartTelephone());//add by liuying at 20100202
			temp.put("olodApplyAccount", accounts);

			BaseObject object = (BaseObject) metaDataManager.saveEntityData(
					account, temp);// �������ʵ��

			/** *******************************�����˺�ʵ��END************************************************* */
			String json = "{success:true,id:" + id + ",applyId:'" + name + "'}";
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public String listAllActiveProcessInstance(){
//			HttpServletRequest request = super.getRequest();
//			HttpServletResponse response = super.getResponse();
//			String ownerName=request.getParameter("ownerName");
//			String confirmUserName=request.getParameter("managerName");
//			
//			int start = HttpUtil.getInt(request, "start", 1);
//			int pageSize = HttpUtil.getInt(request, "pageSize", 10);
//			String json="";
//			System.out.println(json+new Date().getTime());
//			List<ProcessInfo> list= ds.getAllActiveProcessInstance();
//			System.out.println(json+new Date().getTime());
//			for(int i =0; i<1;i++){
//				ProcessInfo pi = list.get(i);
//				json += "{\"id\":\"" + pi.getId() + "\",\"auditper\":\"" + pi.getAuditper()
//				// + "\",\"definitionName\":\"" + pi.getDefinitionName() + "\",\"name\":\"" + pi.getName()
//				//+ "\",\"nodeDesc\":\"" + pi.getNodeDesc()
//				+ "\",\"nodeName\":\"" + pi.getNodeName()
//				+ "\",\"processCreator\":\"" + pi.getProcessCreator()+ "\",\"taskId\":\"" + pi.getTaskid()
//				+ "\",\"virtualDefinitionDesc\":\"" + pi.getVirtualDefinitionDesc() + "\",\"dataId\":\"" + pi.getDataId()
//				+ "\",\"start\":\"" + pi.getStart()
//				//+ "\",\"defVersion\":\"" + pi.getDefVersion()
//				+ "\"},";
//			}
//			int total = list.size();
//			
//			
//			if (json.length() == 0) {
//				json = "{success:true,rowCount:" + "1" + ",data:["
//						+ json.substring(0, json.length()) + "]}";
//			} else {
//				json = "{success:true,rowCount:" + total + ",data:["
//						+ json.substring(0, json.length() - 1) + "]}";
//			}
//			response.setContentType("text/plain");
//			response.setCharacterEncoding("UTF-8");
//			PrintWriter out;
//			System.out.println(json+new Date().getTime());
//			try {
//				out = response.getWriter();
//				out.println(json);
//				System.out.println(json+new Date().getTime());
//				out.flush();
//				out.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			
			return null;
		}
		
		
		/**
		 * ���win7ƽ̨��
		 * 
		 * @Methods Name listWin7PlatFormName
		 * @Create In Aug 5, 2009 By liuying
		 * @return String
		 */
		public String listWin7PlatFormName() {
		
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String name=request.getParameter("name");
			String pfid = request.getParameter("id");
			int pageSize = 10;//ÿҳ����
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			
			String json = "";
			if(pfid!=null&&pfid!=""){
				Win7PlatForm pfa = (Win7PlatForm)  getService().find(Win7PlatForm.class, pfid);
				json+="{id:'"+pfa.getId()+"',name:'"+pfa.getName()+"'}";
				json = "{success: true, rowCount:'1',data:["+json+"]}";
			}else{
				Page page = accountService.findByPageQueryWin7PlatForm(name, pageNo, pageSize);
				List<Win7PlatForm> account = page.list();
		        Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
				
				for (Win7PlatForm acc : account) {
					Long id = acc.getId();
					String accountName = acc.getName();
					json += "{\"id\":\"" + id + "\",\"name\":\"" + accountName
							+ "\" },";
				}
				if (json.length() == 0) {
					json = "{success:true,rowCount:" + "1" + ",data:["
							+ json.substring(0, json.length()) + "]}";
				} else {
					json = "{success:true,rowCount:" + total + ",data:["
							+ json.substring(0, json.length() - 1) + "]}";
				}
			}
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		/**
		 * ���win7ƽ̨��
		 * 
		 * @Methods Name listDeviceTypeName
		 * @Create In Aug 5, 2009 By liuying
		 * @return String
		 */
		public String listDeviceTypeName() {
		
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String name=request.getParameter("name");
			String pfid = request.getParameter("id");
			int pageSize = 10;//ÿҳ����
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo = start / pageSize + 1;
			
			String json = "";
			if(pfid!=null&&pfid!=""){
				DeviceType pfa = (DeviceType)  getService().find(DeviceType.class, pfid);
				json+="{id:'"+pfa.getId()+"',name:'"+pfa.getDeviceName()+"'}";
				json = "{success: true, rowCount:'1',data:["+json+"]}";
			}else{
				Page page = accountService.findByPageQueryDeviceType(name, pageNo, pageSize);
				List<DeviceType> account = page.list();
		        Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
				
				for (DeviceType acc : account) {
					Long id = acc.getId();
					String accountName = acc.getDeviceName();
					json += "{\"id\":\"" + id + "\",\"name\":\"" + accountName
							+ "\" },";
				}
				if (json.length() == 0) {
					json = "{success:true,rowCount:" + "1" + ",data:["
							+ json.substring(0, json.length()) + "]}";
				} else {
					json = "{success:true,rowCount:" + total + ",data:["
							+ json.substring(0, json.length() - 1) + "]}";
				}
			}
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * �õ��ʺ��޸ĵ���ʷ��¼
		 * @Methods Name getModifyDesc
		 * @Create In Jun 30, 2010 By liuying
		 * @return String
		 */
		public String getModifyDesc(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String accountId=request.getParameter("accountId");
			List<AccountModifyDesc> list=accountService.findModifyDescByAccountId(accountId,"2");
			String json="";
			if(list!=null&&list.size()!=0){
				String his="";
				for(AccountModifyDesc amd:list){
					his+=amd.getAccountManger()+" �� "+amd.getModifyDate()+" �޸Ĺ����ʺš�\\r\\n";
				}
				json="{success:true,his:'"+his+"'}";
			}else{
				json="{success:true,his:'��'}";
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * �Զ������ʱ�ʼ��ʺŵ��ʺ���
		 * @Methods Name getTempAccountName
		 * @Create In Aug 20, 2010 By liuying
		 * @return String
		 */
		public String getTempAccountName(){
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String cnName=request.getParameter("cnName");
			String tempName=request.getParameter("tempName");
			String managerName=request.getParameter("managerName");
			String accid=request.getParameter("accid");
			
			SenseServicesUitl ssUtil = new SenseServicesUitl();
			String itcode=ssUtil.initTempUser(cnName,tempName,managerName.toUpperCase());
			String json="";
			if(itcode!=null&&!"".equals(itcode)){
				if(accid!=null&&!"".equals(accid)){
					SpecialAccount sa=(SpecialAccount) getService().find(SpecialAccount.class, accid);
					if(sa!=null){
						sa.setAccountName(itcode);
						getService().save(sa);
					}
				}
				json="{success:true,itcode:'"+itcode+"'}";
			}else{
				json="{success:false}";
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		public String findWWWDayDetail(){
			String calendar=getRequest().getParameter("calendar");
			int pageNo=HttpUtil.getInt(getRequest(), "pageNo",1);
			int pageSize=HttpUtil.getInt(getRequest(),"pageSize",20);
			Page page=accountService.findWWWDayDetail(calendar,pageNo, pageSize);
			getRequest().setAttribute("list", page.getResult());
			getRequest().setAttribute("totalCount",page.getTotalCount() );
			getRequest().setAttribute("pageCount", page.getTotalPageCount());
			getRequest().setAttribute("pageNo", page.getCurrentPageNo());
			getRequest().setAttribute("calendar", calendar);
			return	"toWWWDayDetail";
		}
		public String findWWWMonth(){
			long bytes=accountService.findWWWMonth();
			Integer limit=accountService.findWwwLimit(UserContext.getUserInfo().getUserName());
			if(limit==null){
				getRequest().setAttribute("errorMessage", "����û��WWW�ʺţ�");
				return "toNoWWWAccount";
			}
			double flux=bytes/1024/1024;
			double fee=flux*0.25;
			getRequest().setAttribute("limit", limit);
			getRequest().setAttribute("flux", flux);
			getRequest().setAttribute("balance", limit-fee);
			getRequest().setAttribute("fee", fee);
			DateFormat df=new SimpleDateFormat("yyyyMM");
			getRequest().setAttribute("yearAndMonth", df.format(new Date()));
			return "toWWWMonth";
			
		}
		public  String findWWWMonthDetail(){
			String yearAndMonth=getRequest().getParameter("yearAndMonth");
			List<Object[]> list=accountService.findWWWMonthDetail(yearAndMonth);
			getRequest().setAttribute("list", list);
			getRequest().setAttribute("yearAndMonth", yearAndMonth);
			return "toWWWMonthDetail";
		}
}
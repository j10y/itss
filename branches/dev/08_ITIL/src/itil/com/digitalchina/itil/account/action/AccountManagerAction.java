package com.zsgj.itil.account.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.security.entity.PersonnelScope;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.web.adapter.struts2.BaseAction;
import com.digitalchina.info.framework.workflow.ContextService;
import com.digitalchina.info.framework.workflow.ParameterService;
import com.digitalchina.info.framework.workflow.ProcessService;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.account.entity.AccountSBUOfficer;
import com.zsgj.itil.account.entity.AccountType;
import com.zsgj.itil.account.entity.PersonFormalAccount;
import com.zsgj.itil.account.entity.SpecialAccount;
import com.zsgj.itil.account.entity.Win7PlatForm;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.config.extlist.entity.AR_DrawSpace;
import com.zsgj.itil.config.extlist.entity.HRSAccountManger;
import com.zsgj.itil.config.extlist.entity.PlatFormHRCountSign;
import com.zsgj.itil.config.extlist.entity.TelephoneAudit;
import com.zsgj.itil.config.extlist.entity.TelephoneCountSign;
import com.zsgj.itil.require.entity.AccountApplyMainTable;
import com.zsgj.itil.require.entity.HRSAccountApply;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
/**
 * �˺Ź���������̴���
 * @Class Name AccountManagerAction
 * @Author lee
 * @Create In May 25, 2009
 */
public class AccountManagerAction extends BaseAction{
	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService)ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private ContextService vm = (ContextService)ContextHolder.getBean("contextService");
	private TaskService tm = (TaskService)ContextHolder.getBean("taskService");
	private ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
	private AccountService as = (AccountService) ContextHolder.getBean("accountService");
	
	/**
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵����
	 * ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * @Methods Name apply
	 * @Author lee
	 * @Create In May 25, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		
		
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		
		mapBizz.put("userList", userListStr);//�������̲�����
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}	
	
	
	/**
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵����
	 * ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * @Methods Name applyHRS
	 * @Author gaowen
	 * @Create In May 25, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String applyHrs() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		HRSAccountManger cofirmUser = (HRSAccountManger) bWrapper.getPropertyValue("accountManger");
		
		String userListStr = "confirmByAM:"+cofirmUser.getItcode();//ָ�����ž��������˽ڵ�������
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		mapBizz.put("userList", userListStr);//�������̲�����
		
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}
	
	/**
	 * ������루������������Զ�̽����ʺ�����,
	 * 
	 * @Methods Name applyRemoteAccessAccount
	 * @Author gaowen
	 * @Create In Aug 10, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String applyRemoteAccessAccount() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//�ж�SBU������
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String drawSpace=super.getRequest().getParameter("drawSpace");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************���������ӷ�Χ��ʼ��SBU�ڵ�������************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		
		
		
		/************�����쿨�ص�ѡ���ʺŹ���Ա�ڵ�������************************/
		AR_DrawSpace space=(AR_DrawSpace) service.find(AR_DrawSpace.class, drawSpace);
		String confirmUser=space.getConfirmUser();
		userListStr+="$confirmByAM:"+confirmUser;
		mapBizz.put("userList", userListStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	
	/**
	 * ������루�����������������ʺ�����,
	 * 
	 * @Methods Name applyTelephoneAccount
	 * @Author gaowen
	 * @Create In Aug 10, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String applyTelephoneAccount() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String workSpace=super.getRequest().getParameter("workSpace");
		String department=super.getRequest().getParameter("department");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************���ݹ����ص�ѡ���ʺŹ���Ա�ڵ�������************************/
		
		//modify by liuying at 20100329 for �޸��������빤���ص��Ӧ�����˵Ĵ��� start
		//AR_DrawSpace space=(AR_DrawSpace) service.find(AR_DrawSpace.class, workSpace);
		//String confirmUsers=space.getTelephoneConfirmUser();
		//userListStr+="$confirmByAM:"+confirmUsers;
			TelephoneAudit ta=(TelephoneAudit)service.find(TelephoneAudit.class, workSpace);
			String confirmUsers=ta.getAuditManger();
			userListStr+="$confirmByAM:"+confirmUsers;
		
		//modify by liuying at 20100329 for �޸��������빤���ص��Ӧ�����˵Ĵ��� end
		if(department!=null&&!department.equals("")){
		TelephoneCountSign addSign=(TelephoneCountSign) service.find(TelephoneCountSign.class, department);
		String addSignUser=addSign.getCountSignItcode();
		userListStr+="$confirmMore:"+addSignUser;
		}
		mapBizz.put("userList", userListStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	/**
	 * ������루�������������ֻ��ʺ�����,
	 * 
	 * @Methods Name applyMobileTelephoneApply
	 * @Author gaowen
	 * @Create In Aug 10, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String applyMobileTelephoneApply() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String department=super.getRequest().getParameter("department");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************������������ѡ���ʺŹ���Ա�ڵ�������************************/
		TelephoneCountSign addSign=(TelephoneCountSign) service.find(TelephoneCountSign.class, department);
		String addSignUser=addSign.getCountSignItcode();
		userListStr+="$confirmMore:"+addSignUser;
		userListStr+="$confirmBySign:"+addSignUser;
		mapBizz.put("userList", userListStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	/**
	 * ������루������������WWW��ʱ��ȵ�������,
	 * 
	 * @Methods Name applyWWWTempValue
	 * @Author gaowen
	 * @Create In Aug 10, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String applyWWWTempValue() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String department=super.getRequest().getParameter("department");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************������������ѡ���ʺŹ���Ա�ڵ�������************************/
		userListStr+="$confirmMore:"+"wangxq";
		userListStr+="$confirmByAM:"+"liuqz";
		mapBizz.put("userList", userListStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	/**
	 * ������루������������Ա�����ű������,
	 * 
	 * @Methods Name applyDeptChange
	 * @Author gaowen
	 * @Create In Aug 10, 2009
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String applyDeptChange() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String wwwValue=super.getRequest().getParameter("wwwValue");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        UserInfo signAuditUser=(UserInfo) bWrapper.getPropertyValue("signAuditUser");//�²��ž�������
		String userListStr = "confirmByDMold:"+cofirmUser.getUserName()+"$confirmByDMnew:"+signAuditUser.getUserName();//ָ�����ž��������˽ڵ�������
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		mapBizz.put("userList", userListStr);//�������̲�����
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//�õ������ʵ��
		List<PersonFormalAccount> accounts=  getService().find(PersonFormalAccount.class, "applyId", mainObj);
		List<PersonFormalAccount> account=new ArrayList<PersonFormalAccount>();
		for (PersonFormalAccount ac : accounts) {
			if(ac.getAccountType().getAccountType().equals("MailAccount")){
				account.add(ac);
			}
			else if(wwwValue!=null&&!wwwValue.equals("")&&ac.getAccountType().getAccountType().equals("WWWAccount")){
				account.add(ac);
			}else if(ac.getIfHold()==0){
				account.add(ac);
			}
			
		}
		for (PersonFormalAccount acc : account) {
			//add by liuying at 20100903 for �޸Ĳ��ű�������������벻����ʱ ���ݹ����ص�ѡ����������Ա start
			AccountType at=acc.getAccountType();
			if(at.getAccountType().equals("Telephone")){
				TelephoneAudit telephoneAudit=(TelephoneAudit) getService().find(TelephoneAudit.class, "workSpace", acc.getWorkSpace().getName()).get(0);
				dynCounterSignStr+="1"+"+"+telephoneAudit.getAuditManger();
				dynCounterSignStr+=";";
			}else{
			//add by liuying at 20100903 for �޸Ĳ��ű�������������벻����ʱ ���ݹ����ص�ѡ����������Ա end
				Role role = at.getRole();
				Set<UserInfo> userinfos=role.getUserInfos();
				if(userinfos.size()>1){
					dynCounterSignStr+="1"+"+";
					for(UserInfo userinfo:userinfos){
						dynCounterSignStr+=userinfo.getUserName()+",";
					}
					dynCounterSignStr=dynCounterSignStr.substring(0, dynCounterSignStr.length()-1);
					dynCounterSignStr+=";";
					
				}else{
					for(UserInfo userinfo:userinfos){
						dynCounterSignStr+="0"+"+"+userinfo.getUserName();
					}
					dynCounterSignStr+=";";
					
				}
			}
		}
	
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		
		
		mapBizz.put("dynCounterSign", dynCounterSignStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * ���ERP���˺����루������������
	 * @Methods Name applyERP
	 * @Create In Jun 1, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	@SuppressWarnings("unchecked")
	public String apply2() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//�ж�SBU������
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String userInfo = super.getRequest().getParameter("userInfo");//������
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("processName", definitionName);
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************���������ӷ�Χ��ʼ��SBU�ڵ�������************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		
		mapBizz.put("userList", userListStr);//�������̲�����
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}	
	
	/**
	 * ��Ա��IT�ʺ����루������������
	 * @Methods Name newITAccountApply
	 * @Create In Aug 8, 2009 By CEO awen
	 * @return String
	 * @throws 
	 */
	
//	public String newITAccountApply() throws Exception{
//		String json = ""; 
//		//��Ҫ�Ĳ���
//		String definitionName = super.getRequest().getParameter("defname");
//		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
//		String dataId=super.getRequest().getParameter("dataId");//������id
//		String userInfo =super.getRequest().getParameter("userInfo");//������
//		String workSpace=super.getRequest().getParameter("workSpace");//������
//		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
//        List<TelephoneAudit> audit= getService().find(TelephoneAudit.class, "workSpace", workSpace);
//        String accountManger="";
//        if(audit!=null){
//        for(TelephoneAudit te:audit){
//		 accountManger=te.getAuditManger();
//         }
//        }
//	    
//		//��Ҫ���������ĵ�ҵ�����
//		Map<String,String> mapBizz = new HashMap<String,String>();
//		if(buzzParameters!=null&&!buzzParameters.equals("")) {
//			JSONObject jo = JSONObject.fromObject(buzzParameters);
//			Iterator it = jo.keys();
//			while(it.hasNext()) {
//				String key = (String)it.next();
//				String value = (String)jo.get(key);					
//				mapBizz.put(key, value);
//			}
//		}
//		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
//		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
//		String serviceItemId = mapBizz.get("serviceItemId");
//		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
//		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
//		String className = siut.getClassName();
//		Object obj = service.find(this.toClass(className), dataId);
//		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
//		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
//		
//		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
//		mapBizz.put("userList", userListStr);//�������̲�����
//		String name = (String) bWrapper.getPropertyValue("name");
//		mapBizz.put("applyNum",name);
//		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
//		String dynCounterSignStr="confirmByAM:";
//		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//�õ������ʵ��
//		List<PersonFormalAccount> account=  getService().find(PersonFormalAccount.class, "applyId", mainObj);
//		Set user1= new HashSet();
//		Set user2=new HashSet();
//		for (PersonFormalAccount acc : account) {
//		if(acc.getAccountType().getAccountType().equals("Telephone")){
//			dynCounterSignStr+="0"+"+";
//			dynCounterSignStr+=accountManger+",";
//		}else{
//		Role role = acc.getAccountType().getRole();
//		Set<UserInfo> userinfos=role.getUserInfos();
//		if(userinfos.size()>1){
//		for(UserInfo userinfo:userinfos){
//			user1.add(userinfo.getUserName());
//		}
//		
//		}else{
//			for(UserInfo userinfo:userinfos){
//				user2.add(userinfo.getUserName());
//			}	
//			
//		}
//		}
//		}
//		
//		if(user2.size()>0){
//		Iterator ite2 = user2.iterator();
//		if(dynCounterSignStr.indexOf("0")==-1){
//			dynCounterSignStr+="0"+"+";
//		}
//		while(ite2.hasNext()){
//			dynCounterSignStr+=ite2.next()+",";
//		}
//		
//		}
//		if(dynCounterSignStr.endsWith(",")) {
//			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
//		}
//		dynCounterSignStr+=";";
//		
//		if(user1.size()>0){
//		dynCounterSignStr+="1"+"+";
//		Iterator ite1 = user1.iterator();
//		while(ite1.hasNext()){
//			dynCounterSignStr+=ite1.next()+",";
//		}
//		}
//		if(dynCounterSignStr.endsWith(",")) {
//			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
//		}
//		if(dynCounterSignStr.endsWith(";")) {
//			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
//		}
//		
//		
//		mapBizz.put("dynCounterSign", dynCounterSignStr);//�������̲�����
//		String creator = UserContext.getUserInfo().getUserName();
//		Long instanceId = null;
//		String meg = "";
//		try{
//			instanceId = ps.createProcess(definitionName,creator,mapBizz);
//			json = "{success:true,id:'"+instanceId+"'}";	
//		}catch(Exception e){
//			meg = e.getMessage();
//			json = "{success:true,Exception:'"+meg+"'}";
//		}			
//		try {			
//			super.getResponse().setCharacterEncoding("utf-8");
//			PrintWriter pw = super.getResponse().getWriter();	
//			pw.write(json);		
//			} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;				
//	}	
	
	
	/**
	 * ��Ա��IT�ʺ����루������������
	 * @Methods Name newITAccountApply
	 * @Create In Aug 8, 2009 By CEO awen
	 * @return String
	 * @throws 
	 */
	
	public String newITAccountApply() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId=super.getRequest().getParameter("dataId");//������id
		String userInfo =super.getRequest().getParameter("userInfo");//������
		String workSpace=super.getRequest().getParameter("workSpace");//������
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
        List<TelephoneAudit> audit= getService().find(TelephoneAudit.class, "workSpace", workSpace);
        String accountManger="liuqz";
        if(audit!=null){
        for(TelephoneAudit te:audit){
		 accountManger=te.getAuditManger();
         }
        }
	    
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		mapBizz.put("userList", userListStr);//�������̲�����
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//�õ������ʵ��
		List<PersonFormalAccount> account=  getService().find(PersonFormalAccount.class, "applyId", mainObj);
		Set user2=new HashSet();
		for (PersonFormalAccount acc : account) {
		if(acc.getAccountType().getAccountType().equals("Telephone")){
			dynCounterSignStr+="1"+"+";
			dynCounterSignStr+=accountManger;
			String type="&"+acc.getAccountType().getName()+"����Ա����";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
		}else{
		Role role = acc.getAccountType().getRole();
		Set<UserInfo> userinfos=role.getUserInfos();
		if(userinfos.size()>1){
		dynCounterSignStr+="1"+"+";
		for(UserInfo userinfo:userinfos){
			dynCounterSignStr+=userinfo.getUserName()+",";
		}
		dynCounterSignStr=dynCounterSignStr.substring(0, dynCounterSignStr.length()-1);
		String type="&"+acc.getAccountType().getName()+"����Ա����";
		dynCounterSignStr+=type;
		dynCounterSignStr+=";";
		}else{
			for(UserInfo userinfo:userinfos){
				dynCounterSignStr+="1"+"+"+userinfo.getUserName();
			}
			String type="&"+acc.getAccountType().getName()+"����Ա����";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
			
		}
		}
		}
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		mapBizz.put("dynCounterSign", dynCounterSignStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}	
	
	
	
	/**
	 * �����ʱ��Ա�ʼ�/���ʺ�
	 * @Methods Name applyTempMail
	 * @Create In Jun 30, 2009 By CEO awen
	 * @return
	 * @throws Exception String
	 */
	
	public String applyTempMail() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//�ж�SBU������
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String userInfo = super.getRequest().getParameter("userInfo");//������
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		PlatFormHRCountSign hrCountSign=(PlatFormHRCountSign) bWrapper.getPropertyValue("platFormHRCountSign");
		String userListStr = "confirmByDM:"+cofirmUser.getUserName()+"$confirmMore:"+hrCountSign.getItcode();//ָ�����ž��������˽ڵ�������
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		mapBizz.put("userList", userListStr);//�������̲�����
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//�õ������ʵ��
		List<SpecialAccount> account=  getService().find(SpecialAccount.class, "applyId", mainObj);
		for (SpecialAccount acc : account) {
	    Role role = acc.getAccountType().getRole();
		Set<UserInfo> userinfos=role.getUserInfos();
		dynCounterSignStr+="0"+"+";
		for(UserInfo userinfo:userinfos){
			dynCounterSignStr+=userinfo.getUserName()+",";
		}
		}
		
		if(dynCounterSignStr.endsWith(",")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		
		
		mapBizz.put("dynCounterSign", dynCounterSignStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}	
	
	
	/**
	 * ������������ʼ��ʺ�
	 * @Methods Name applyDeptMail
	 * @Create In 11 6, 2009 By CEO awen
	 * @return
	 * @throws Exception String
	 */
	
	public String applyDeptMail() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//�ж�SBU������
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String userInfo = super.getRequest().getParameter("userInfo");//������
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		PlatFormHRCountSign hrCountSign=(PlatFormHRCountSign) bWrapper.getPropertyValue("platFormHRCountSign");
		String userListStr = "confirmByDM:"+cofirmUser.getUserName()+"$confirmMore:"+hrCountSign.getItcode();//ָ�����ž��������˽ڵ�������
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		mapBizz.put("userList", userListStr);//�������̲�����
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}
	/**
	 * ��ʱ�ʼ�ɾ������
	 * @Methods Name applyTempMailDelete
	 * @Create In Jan 27, 2010 By liuying
	 * @return String
	 */
	public String applyTempMailDelete() throws Exception{
		
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		
		
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		
		mapBizz.put("userList", userListStr);//�������̲�����
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//�õ������ʵ��
		List<SpecialAccount> account=  getService().find(SpecialAccount.class, "applyId", mainObj);
		boolean mailtype=true;
		for (SpecialAccount acc : account) {
			//modify by liuying at 20100223 for ��ʱ�ʼ����ʺ�ɾ��ʱ�ʺŹ���Ա�����Ϊ��ǩ start
		if(acc.getAccountType().getAccountType().equals("TempMailAccount")){
			if(mailtype){
				Role role = acc.getAccountType().getRole();
				Set<UserInfo> userinfos=role.getUserInfos();
				for(UserInfo userinfo:userinfos){
					dynCounterSignStr+="0"+"+";
					dynCounterSignStr+=userinfo.getUserName()+";";
				}
				dynCounterSignStr=dynCounterSignStr.substring(0, dynCounterSignStr.length()-1);
				dynCounterSignStr+=";";
				mailtype=false;
			}
		}else{
				Role role = acc.getAccountType().getRole();
				Set<UserInfo> userinfos=role.getUserInfos();
				if(userinfos.size()>1){
					dynCounterSignStr+="1"+"+";
					for(UserInfo userinfo:userinfos){
						dynCounterSignStr+=userinfo.getUserName()+",";
					}
					dynCounterSignStr=dynCounterSignStr.substring(0, dynCounterSignStr.length()-1);
					dynCounterSignStr+=";";
				}else{
					for(UserInfo userinfo:userinfos){
						dynCounterSignStr+="0"+"+"+userinfo.getUserName();
					}
					dynCounterSignStr+=";";
					
				}
		}
		//modify by liuying at 20100223 for ��ʱ�ʼ����ʺ�ɾ��ʱ�ʺŹ���Ա�����Ϊ��ǩ end
		}
		
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		mapBizz.put("dynCounterSign", dynCounterSignStr);//�������̲�����
	
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				

	}
	
	
	/**
	 * ID�ļ���������
	 * @return String 
	 * @throws Exception
	 */
	public String applyIDFile() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}

	/**
	 * ���Ա����ְ���루������������
	 * @Methods Name applyEmployQuit
	 * @Create In Jun 30, 2009 By CEO awen
	 * @return
	 * @throws Exception String
	 */
	
	public String applyEmployQuit() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String processNameDescription = super.getRequest().getParameter("description");
		String dataId = super.getRequest().getParameter("dataId");//������id
		String userInfo = super.getRequest().getParameter("userInfo");//������
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String  drawSpace=super.getRequest().getParameter("workSpace");
		AR_DrawSpace space=null;
		if(drawSpace!=null&&StringUtils.isNotBlank(drawSpace)){
			space=(AR_DrawSpace) service.find(AR_DrawSpace.class, drawSpace);
		}
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");
		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		mapBizz.put("userList", userListStr);//�������̲�����
		
//		MobileTelephoneApply mobile=(MobileTelephoneApply) getService().findUnique(
//				MobileTelephoneApply.class, "applyId", obj);
		
		String dynCounterSignStr="confirmByAM:";
//		if(mobile!=null){
//			dynCounterSignStr+="0"+"+"+"maran;";
//		}
		List<PersonFormalAccount> account= as.findAllPersonAccount(applyUser);
		for (PersonFormalAccount acc : account) {
//		if(acc.getAccountType().getAccountType().equals("VPNAccount")){
//		AR_DrawSpace workSpace=acc.getDrawSpace();
//		String confirmUsers=workSpace.getConfirmUser();	
//		dynCounterSignStr+="1"+"+"+confirmUsers;
//		}else{
		String accountType=acc.getAccountType().getAccountType();
		String vpnType=acc.getVpnType();
		if(accountType.equals("VPNAccount")&&vpnType.equals("0")){
			dynCounterSignStr+="1"+"+"+space.getConfirmUser();
			String type="&"+acc.getAccountType().getName()+"����Ա����";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
			
		}else if(accountType.equals("Telephone")){
			dynCounterSignStr+="1"+"+"+space.getTelephoneConfirmUser();
			String type="&"+acc.getAccountType().getName()+"����Ա����";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
		}else{
		Role role = acc.getAccountType().getRole();
		Set<UserInfo> userinfos=role.getUserInfos();
		if(userinfos.size()>1){
			dynCounterSignStr+="1"+"+";
			for(UserInfo userinfo:userinfos){
				dynCounterSignStr+=userinfo.getUserName()+",";
			}
			dynCounterSignStr=dynCounterSignStr.substring(0, dynCounterSignStr.length()-1);
			String type="&"+acc.getAccountType().getName()+"����Ա����";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
		}else{
			for(UserInfo userinfo:userinfos){
				dynCounterSignStr+="1"+"+"+userinfo.getUserName();
			}
			String type="&"+acc.getAccountType().getName()+"����Ա����";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";	
		  }
		 }
		}
		
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		
		
		
		mapBizz.put("dynCounterSign", dynCounterSignStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}	
	
	
	/**
	 * ��ʾ�����б�
	 * @Methods Name tasks
	 * @Create In May 25, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
		HttpServletRequest request = super.getRequest();
		//��Ҫ�Ĳ���
		String actor = request.getParameter("actorId");
		String json = "";
		
		int rowCount = 0;
	  	List<TaskInfo> list = ts.listTasks(actor);
	  	List<TaskInfo> list1 =new ArrayList();
	  	for(TaskInfo taskInfo:list) {
	  		Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
	  		String type = (String)bizParams.get("applyType");
	  		if("acproject".equals(type)){
	  			list1.add(taskInfo);
			}
	  	}
		for(TaskInfo taskInfo:list1) {
			String str = "";
			str += "defname:'"+taskInfo.getDefinitionName()+"',";
			str += "defdesc:'"+taskInfo.getDefinitionDesc()+"',";
			str += "nodeName:'"+taskInfo.getNodeName()+"',";
			str += "taskId:'"+taskInfo.getId()+"',";
			str += "taskName:'"+taskInfo.getName()+"',";
			//��ʵ�����ƴ����û�ϵͳ��
			str += "startDate:'"+toBlank(taskInfo.getStart())+"',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String accountName = (String)bizParams.get("accountName");
			if(accountName == null || "null".equalsIgnoreCase(accountName)){
				bizParams.put("accountName", "δ����");
			}
			String type = (String)bizParams.get("applyType");
			String dataId=(String)bizParams.get("dataId");
			String serviceItemId = (String)bizParams.get("serviceItemId");
//			
			
			String applyUser =null;
			String applydate=null;
            if(serviceItemId.equals("295")){
            	HRSAccountApply ac=(HRSAccountApply) getService().find(HRSAccountApply.class, dataId);
    			
				if(ac.getApplyUser()!=null){
					applyUser=ac.getApplyUser().getRealName();
				}
				if(ac.getApplyDate()!=null){
					applydate=ac.getApplyDate().toString();
				}
			}
			else{	
			AccountApplyMainTable ac=(AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId);
			
				if(ac.getApplyUser()!=null){
					applyUser=ac.getApplyUser().getRealName();
				}
				if(ac.getApplyDate()!=null){
					applydate=ac.getApplyDate().toString();
				}
			}
			bizParams.put("applyUser", applyUser);
			bizParams.put("applyTime",applydate );
			
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
			if(strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if(strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,strBizParams.length()-1);
			}
			
			str += strBizParams+",";
			str += "comments:'"+toBlank(taskInfo.getComments().getValue("comment"))+"'";
			str = "{"+str+"},";
			String defname = taskInfo.getDefinitionName();
//			if("acproject".equals(type)){
				json += str;
				rowCount++;
//			}
		}		
		json = deleteComma(json);
		json =  "{success: true, rowCount:'"+rowCount+"',data:["+json+"]}";
		
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		return null;
	}
	
	private String toBlank(Object o){
		return o==null?"":(String)o;		
	}
	
	private String deleteComma(String json){
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return json;
	}
	
	private Class toClass(String className){
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println(className+"�಻���ڣ�");
			e.printStackTrace();
		}
		return clazz;
	}
	
	/**
	 * ������루������������Զ�̽����ʺ�ɾ�����루��������������Ӳ����ʱ��
	 * @Methods Name applyVPNAccountDelete
	 * @Create In May 31, 2010 By liuying
	 * @return
	 * @throws Exception String
	 */
	@SuppressWarnings("unchecked")
	public String applyVPNAccountDelete() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//�ж�SBU������
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String drawSpace=super.getRequest().getParameter("drawSpace");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************���������ӷ�Χ��ʼ��SBU�ڵ�������************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		mapBizz.put("userList", userListStr);//�������̲�����
		
		
		
		/************�����쿨�ص�ѡ���ʺŹ���Ա�ڵ�������************************/
		AR_DrawSpace space=(AR_DrawSpace) service.find(AR_DrawSpace.class, drawSpace);
		String confirmUser=space.getConfirmUser();
		AR_DrawSpace spaceBeijing=(AR_DrawSpace) service.find(AR_DrawSpace.class, "1");
		String confirmUserBeijing=spaceBeijing.getConfirmUser();
		String dynCounterSignUser="";
		if(confirmUser.contains(",")){
			dynCounterSignUser+="confirmByAM:"+"1"+"+"+confirmUser+";";
		}else{
			dynCounterSignUser+="confirmByAM:"+"0"+"+"+confirmUser+";";
		}
		if(confirmUserBeijing.contains(",")){
			dynCounterSignUser+="1"+"+"+confirmUserBeijing;
		}else{
			dynCounterSignUser+="0"+"+"+confirmUserBeijing;
		}
		
		
		mapBizz.put("dynCounterSign", dynCounterSignUser);//�������̲�����
		
		
		
		
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * ���������� win7�ʺ�����
	 * @Methods Name applyWin7AccessAccount
	 * @Create In Jul 6, 2010 By liuying
	 * @return
	 * @throws Exception String
	 */
	public String applyWin7AccessAccount() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//�ж�SBU������
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String platForm=super.getRequest().getParameter("platForm");
		//��Ҫ���������ĵ�ҵ�����
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		mapBizz.put("workflowHistory", "com.digitalchina.itil.service.entity.ServiceItemApplyAuditHis");

		/*************************����serviceItem��dataId��ȡ������ʵ��*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//ԭ���ž�������
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for ���û�Ҫ���޸�Ϊ�ʺţ���������� in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//ָ�����ž��������˽ڵ�������
		/************���������ӷ�Χ��ʼ��SBU�ڵ�������************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		
		
		
		/************�����쿨�ص�ѡ���ʺŹ���Ա�ڵ�������************************/
		Win7PlatForm space=(Win7PlatForm) service.find(Win7PlatForm.class, platForm);
		String confirmUser=space.getManager();
		userListStr+="$confirmByAM:"+confirmUser;
		mapBizz.put("userList", userListStr);//�������̲�����
		String creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			instanceId = ps.createProcess(definitionName,creator,mapBizz);
			json = "{success:true,id:'"+instanceId+"'}";	
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
}

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
 * 账号管理相关流程处理
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
	 * 提出申请（启动工作流）,需要考虑到一个节点有可能指派给多人的情况
	 * 支持单节点单行数据指派，形式为a|b|c,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		
		
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		
		mapBizz.put("userList", userListStr);//放入流程参数中
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		
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
	 * 提出申请（启动工作流）,需要考虑到一个节点有可能指派给多人的情况
	 * 支持单节点单行数据指派，形式为a|b|c,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		HRSAccountManger cofirmUser = (HRSAccountManger) bWrapper.getPropertyValue("accountManger");
		
		String userListStr = "confirmByAM:"+cofirmUser.getItcode();//指定部门经理审批人节点审批人
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		mapBizz.put("userList", userListStr);//放入流程参数中
		
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
	 * 提出申请（启动工作流）远程接入帐号申请,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//判断SBU审批人
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String drawSpace=super.getRequest().getParameter("drawSpace");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据人事子范围初始化SBU节点审批人************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		
		
		
		/************根据领卡地点选择帐号管理员节点审批人************************/
		AR_DrawSpace space=(AR_DrawSpace) service.find(AR_DrawSpace.class, drawSpace);
		String confirmUser=space.getConfirmUser();
		userListStr+="$confirmByAM:"+confirmUser;
		mapBizz.put("userList", userListStr);//放入流程参数中
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
	 * 提出申请（启动工作流）座机帐号申请,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String workSpace=super.getRequest().getParameter("workSpace");
		String department=super.getRequest().getParameter("department");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据工作地点选择帐号管理员节点审批人************************/
		
		//modify by liuying at 20100329 for 修改座机申请工作地点对应审批人的错误 start
		//AR_DrawSpace space=(AR_DrawSpace) service.find(AR_DrawSpace.class, workSpace);
		//String confirmUsers=space.getTelephoneConfirmUser();
		//userListStr+="$confirmByAM:"+confirmUsers;
			TelephoneAudit ta=(TelephoneAudit)service.find(TelephoneAudit.class, workSpace);
			String confirmUsers=ta.getAuditManger();
			userListStr+="$confirmByAM:"+confirmUsers;
		
		//modify by liuying at 20100329 for 修改座机申请工作地点对应审批人的错误 end
		if(department!=null&&!department.equals("")){
		TelephoneCountSign addSign=(TelephoneCountSign) service.find(TelephoneCountSign.class, department);
		String addSignUser=addSign.getCountSignItcode();
		userListStr+="$confirmMore:"+addSignUser;
		}
		mapBizz.put("userList", userListStr);//放入流程参数中
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
	 * 提出申请（启动工作流）手机帐号申请,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String department=super.getRequest().getParameter("department");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据隶属部门选择帐号管理员节点审批人************************/
		TelephoneCountSign addSign=(TelephoneCountSign) service.find(TelephoneCountSign.class, department);
		String addSignUser=addSign.getCountSignItcode();
		userListStr+="$confirmMore:"+addSignUser;
		userListStr+="$confirmBySign:"+addSignUser;
		mapBizz.put("userList", userListStr);//放入流程参数中
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
	 * 提出申请（启动工作流）WWW临时额度调整申请,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String department=super.getRequest().getParameter("department");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据隶属部门选择帐号管理员节点审批人************************/
		userListStr+="$confirmMore:"+"wangxq";
		userListStr+="$confirmByAM:"+"liuqz";
		mapBizz.put("userList", userListStr);//放入流程参数中
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
	 * 提出申请（启动工作流）员工部门变更申请,
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
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String wwwValue=super.getRequest().getParameter("wwwValue");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        UserInfo signAuditUser=(UserInfo) bWrapper.getPropertyValue("signAuditUser");//新部门经理审批
		String userListStr = "confirmByDMold:"+cofirmUser.getUserName()+"$confirmByDMnew:"+signAuditUser.getUserName();//指定部门经理审批人节点审批人
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		mapBizz.put("userList", userListStr);//放入流程参数中
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//得到面板主实体
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
			//add by liuying at 20100903 for 修改部门变更申请座机号码不保留时 根据工作地点选择座机管理员 start
			AccountType at=acc.getAccountType();
			if(at.getAccountType().equals("Telephone")){
				TelephoneAudit telephoneAudit=(TelephoneAudit) getService().find(TelephoneAudit.class, "workSpace", acc.getWorkSpace().getName()).get(0);
				dynCounterSignStr+="1"+"+"+telephoneAudit.getAuditManger();
				dynCounterSignStr+=";";
			}else{
			//add by liuying at 20100903 for 修改部门变更申请座机号码不保留时 根据工作地点选择座机管理员 end
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
		
		
		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
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
	 * 提出ERP类账号申请（启动工作流）
	 * @Methods Name applyERP
	 * @Create In Jun 1, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	@SuppressWarnings("unchecked")
	public String apply2() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//判断SBU审批人
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String userInfo = super.getRequest().getParameter("userInfo");//申请人
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据人事子范围初始化SBU节点审批人************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		
		mapBizz.put("userList", userListStr);//放入流程参数中
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		
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
	 * 新员工IT帐号申请（启动工作流）
	 * @Methods Name newITAccountApply
	 * @Create In Aug 8, 2009 By CEO awen
	 * @return String
	 * @throws 
	 */
	
//	public String newITAccountApply() throws Exception{
//		String json = ""; 
//		//需要的参数
//		String definitionName = super.getRequest().getParameter("defname");
//		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
//		String dataId=super.getRequest().getParameter("dataId");//主数据id
//		String userInfo =super.getRequest().getParameter("userInfo");//申请人
//		String workSpace=super.getRequest().getParameter("workSpace");//申请人
//		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
//        List<TelephoneAudit> audit= getService().find(TelephoneAudit.class, "workSpace", workSpace);
//        String accountManger="";
//        if(audit!=null){
//        for(TelephoneAudit te:audit){
//		 accountManger=te.getAuditManger();
//         }
//        }
//	    
//		//需要进入上下文的业务参数
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
//		/*************************根据serviceItem和dataId获取申请主实体*****************/
//		String serviceItemId = mapBizz.get("serviceItemId");
//		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
//		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
//		String className = siut.getClassName();
//		Object obj = service.find(this.toClass(className), dataId);
//		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
//		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
//		
//		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
//		mapBizz.put("userList", userListStr);//放入流程参数中
//		String name = (String) bWrapper.getPropertyValue("name");
//		mapBizz.put("applyNum",name);
//		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
//		String dynCounterSignStr="confirmByAM:";
//		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//得到面板主实体
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
//		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
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
	 * 新员工IT帐号申请（启动工作流）
	 * @Methods Name newITAccountApply
	 * @Create In Aug 8, 2009 By CEO awen
	 * @return String
	 * @throws 
	 */
	
	public String newITAccountApply() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId=super.getRequest().getParameter("dataId");//主数据id
		String userInfo =super.getRequest().getParameter("userInfo");//申请人
		String workSpace=super.getRequest().getParameter("workSpace");//申请人
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
        List<TelephoneAudit> audit= getService().find(TelephoneAudit.class, "workSpace", workSpace);
        String accountManger="liuqz";
        if(audit!=null){
        for(TelephoneAudit te:audit){
		 accountManger=te.getAuditManger();
         }
        }
	    
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		mapBizz.put("userList", userListStr);//放入流程参数中
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//得到面板主实体
		List<PersonFormalAccount> account=  getService().find(PersonFormalAccount.class, "applyId", mainObj);
		Set user2=new HashSet();
		for (PersonFormalAccount acc : account) {
		if(acc.getAccountType().getAccountType().equals("Telephone")){
			dynCounterSignStr+="1"+"+";
			dynCounterSignStr+=accountManger;
			String type="&"+acc.getAccountType().getName()+"管理员处理";
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
		String type="&"+acc.getAccountType().getName()+"管理员处理";
		dynCounterSignStr+=type;
		dynCounterSignStr+=";";
		}else{
			for(UserInfo userinfo:userinfos){
				dynCounterSignStr+="1"+"+"+userinfo.getUserName();
			}
			String type="&"+acc.getAccountType().getName()+"管理员处理";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
			
		}
		}
		}
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
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
	 * 提出临时人员邮件/域帐号
	 * @Methods Name applyTempMail
	 * @Create In Jun 30, 2009 By CEO awen
	 * @return
	 * @throws Exception String
	 */
	
	public String applyTempMail() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//判断SBU审批人
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String userInfo = super.getRequest().getParameter("userInfo");//申请人
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		PlatFormHRCountSign hrCountSign=(PlatFormHRCountSign) bWrapper.getPropertyValue("platFormHRCountSign");
		String userListStr = "confirmByDM:"+cofirmUser.getUserName()+"$confirmMore:"+hrCountSign.getItcode();//指定部门经理审批人节点审批人
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		mapBizz.put("userList", userListStr);//放入流程参数中
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//得到面板主实体
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
		
		
		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
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
	 * 提出部门特殊邮件帐号
	 * @Methods Name applyDeptMail
	 * @Create In 11 6, 2009 By CEO awen
	 * @return
	 * @throws Exception String
	 */
	
	public String applyDeptMail() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//判断SBU审批人
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String userInfo = super.getRequest().getParameter("userInfo");//申请人
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		PlatFormHRCountSign hrCountSign=(PlatFormHRCountSign) bWrapper.getPropertyValue("platFormHRCountSign");
		String userListStr = "confirmByDM:"+cofirmUser.getUserName()+"$confirmMore:"+hrCountSign.getItcode();//指定部门经理审批人节点审批人
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		mapBizz.put("userList", userListStr);//放入流程参数中
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
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
	 * 临时邮件删除流程
	 * @Methods Name applyTempMailDelete
	 * @Create In Jan 27, 2010 By liuying
	 * @return String
	 */
	public String applyTempMailDelete() throws Exception{
		
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		
		
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		
		mapBizz.put("userList", userListStr);//放入流程参数中
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		
		String dynCounterSignStr="confirmByAM:";
		AccountApplyMainTable mainObj = (AccountApplyMainTable) getService().find(AccountApplyMainTable.class, dataId, true);			//得到面板主实体
		List<SpecialAccount> account=  getService().find(SpecialAccount.class, "applyId", mainObj);
		boolean mailtype=true;
		for (SpecialAccount acc : account) {
			//modify by liuying at 20100223 for 临时邮件域帐号删除时帐号管理员处理改为会签 start
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
		//modify by liuying at 20100223 for 临时邮件域帐号删除时帐号管理员处理改为会签 end
		}
		
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
	
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
	 * ID文件申请流程
	 * @return String 
	 * @throws Exception
	 */
	public String applyIDFile() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
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
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
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
	 * 提出员工离职申请（启动工作流）
	 * @Methods Name applyEmployQuit
	 * @Create In Jun 30, 2009 By CEO awen
	 * @return
	 * @throws Exception String
	 */
	
	public String applyEmployQuit() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String processNameDescription = super.getRequest().getParameter("description");
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String userInfo = super.getRequest().getParameter("userInfo");//申请人
		UserInfo applyUser=(UserInfo) getService().find(UserInfo.class, userInfo);
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String  drawSpace=super.getRequest().getParameter("workSpace");
		AR_DrawSpace space=null;
		if(drawSpace!=null&&StringUtils.isNotBlank(drawSpace)){
			space=(AR_DrawSpace) service.find(AR_DrawSpace.class, drawSpace);
		}
		//需要进入上下文的业务参数
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
		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		mapBizz.put("userList", userListStr);//放入流程参数中
		
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
			String type="&"+acc.getAccountType().getName()+"管理员处理";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
			
		}else if(accountType.equals("Telephone")){
			dynCounterSignStr+="1"+"+"+space.getTelephoneConfirmUser();
			String type="&"+acc.getAccountType().getName()+"管理员处理";
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
			String type="&"+acc.getAccountType().getName()+"管理员处理";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";
		}else{
			for(UserInfo userinfo:userinfos){
				dynCounterSignStr+="1"+"+"+userinfo.getUserName();
			}
			String type="&"+acc.getAccountType().getName()+"管理员处理";
			dynCounterSignStr+=type;
			dynCounterSignStr+=";";	
		  }
		 }
		}
		
		if(dynCounterSignStr.endsWith(";")) {
			dynCounterSignStr = dynCounterSignStr.substring(0,dynCounterSignStr.length()-1);
		}
		
		
		
		mapBizz.put("dynCounterSign", dynCounterSignStr);//放入流程参数中
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
	 * 显示任务列表
	 * @Methods Name tasks
	 * @Create In May 25, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
		HttpServletRequest request = super.getRequest();
		//需要的参数
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
			//用实际名称代替用户系统名
			str += "startDate:'"+toBlank(taskInfo.getStart())+"',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String accountName = (String)bizParams.get("accountName");
			if(accountName == null || "null".equalsIgnoreCase(accountName)){
				bizParams.put("accountName", "未命名");
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
			System.out.println(className+"类不存在！");
			e.printStackTrace();
		}
		return clazz;
	}
	
	/**
	 * 提出申请（启动工作流）远程接入帐号删除申请（既有软令牌又有硬令牌时）
	 * @Methods Name applyVPNAccountDelete
	 * @Create In May 31, 2010 By liuying
	 * @return
	 * @throws Exception String
	 */
	@SuppressWarnings("unchecked")
	public String applyVPNAccountDelete() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//判断SBU审批人
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String drawSpace=super.getRequest().getParameter("drawSpace");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据人事子范围初始化SBU节点审批人************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		mapBizz.put("userList", userListStr);//放入流程参数中
		
		
		
		/************根据领卡地点选择帐号管理员节点审批人************************/
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
		
		
		mapBizz.put("dynCounterSign", dynCounterSignUser);//放入流程参数中
		
		
		
		
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
	 * 启动工作流 win7帐号申请
	 * @Methods Name applyWin7AccessAccount
	 * @Create In Jul 6, 2010 By liuying
	 * @return
	 * @throws Exception String
	 */
	public String applyWin7AccessAccount() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		String processNameDescription = super.getRequest().getParameter("description");//判断SBU审批人
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		String platForm=super.getRequest().getParameter("platForm");
		//需要进入上下文的业务参数
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

		/*************************根据serviceItem和dataId获取申请主实体*****************/
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem servcieItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", servcieItem);
		String className = siut.getClassName();
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
        UserInfo cofirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");//原部门经理审批
        String name = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("applyNum",name);
		mapBizz.put("applyName",servcieItem.getName());//modify by lee for 按用户要求修改为帐号（服务项）名称 in 20091210
		String userListStr = "confirmByDM:"+cofirmUser.getUserName();//指定部门经理审批人节点审批人
		/************根据人事子范围初始化SBU节点审批人************************/
		UserInfo applyUser = (UserInfo) bWrapper.getPropertyValue("applyUser");
		PersonnelScope personnelScope=applyUser.getPersonnelScope();
		if(personnelScope!=null){
		String personScope=personnelScope.getPersonnelScopeCode();
		List<AccountSBUOfficer> confirmUsers = as.findOfficer(processNameDescription, personScope);
		for(AccountSBUOfficer officer:confirmUsers){
			userListStr+="$"+officer.getNodeName()+":"+officer.getConfirmUser();
		}
		}
		
		
		
		/************根据领卡地点选择帐号管理员节点审批人************************/
		Win7PlatForm space=(Win7PlatForm) service.find(Win7PlatForm.class, platForm);
		String confirmUser=space.getManager();
		userListStr+="$confirmByAM:"+confirmUser;
		mapBizz.put("userList", userListStr);//放入流程参数中
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

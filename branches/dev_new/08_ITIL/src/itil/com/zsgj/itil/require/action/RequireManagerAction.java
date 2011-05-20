package com.zsgj.itil.require.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ContextService;
import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.config.extlist.entity.RequirementLevel;
import com.zsgj.itil.require.entity.RequireApplyDefaultAudit;
import com.zsgj.itil.require.service.RequireService;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceItemUserTable;

public class RequireManagerAction extends BaseAction{
	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService)ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ContextService vm = (ContextService)ContextHolder.getBean("contextService");
//	private TaskService tm = (TaskService)ContextHolder.getBean("taskService");
	private ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
	private RequireService rs = (RequireService)ContextHolder.getBean("requireService");
	
	/**
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵����
	 * ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		
		//definitionName=StringUtils.substringAfter(definitionName, "$");
		//definitionName = definitionName.substring(8);
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
//		String dataId = super.getRequest().getParameter("dataId");//������id

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
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		mapBizz.put("reqClass", className);
		
		VirtualDefinitionInfo virtualDefinitionInfo=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "virtualDefinitionName", definitionName);
		String description = virtualDefinitionInfo.getVirtualDefinitionDesc();
		mapBizz.put("applyTypeName",description);
		
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
	 * �ύ���루������㲿�֣�
	 * @Methods Name applyForBusiness
	 * @Create In Aug 23, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	@SuppressWarnings("unchecked")
	public String applyForBusiness() throws Exception{
		String json = ""; 
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id

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
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();		
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
//		RequireApplyDefaultAudit flat = null;
		Integer status = (Integer) bWrapper.getPropertyValue("status");
		String reqName = (String) bWrapper.getPropertyValue("name");
		mapBizz.put("reqName", reqName);
		if(Integer.valueOf(1).equals(status)){
			json = "{success:true,Exception:'��������Ѿ����ύ��'}";
			try {			
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter pw = super.getResponse().getWriter();	
				pw.write(json);		
				} catch (IOException e) {
				e.printStackTrace();
			}
			return null;	
		}
		VirtualDefinitionInfo virtualDefinitionInfo=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "virtualDefinitionName", definitionName);
		String description = virtualDefinitionInfo.getVirtualDefinitionDesc();
		mapBizz.put("applyTypeName",description);
		
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
	 * �ύ��ָ�ɽڵ������û�
	 * @Methods Name applyAndAssign
	 * @Create In Aug 19, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String applyAndAssign() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		
		//definitionName=StringUtils.substringAfter(definitionName, "$");
		//definitionName = definitionName.substring(8);
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id

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
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		mapBizz.put("reqClass", className);
		
		VirtualDefinitionInfo virtualDefinitionInfo=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "virtualDefinitionName", definitionName);
		String description = virtualDefinitionInfo.getVirtualDefinitionDesc();
		mapBizz.put("applyTypeName",description);
		
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		RequireApplyDefaultAudit flat = null;
		Integer status = (Integer) bWrapper.getPropertyValue("status");
		if(Integer.valueOf(1).equals(status)){
			json = "{success:true,Exception:'��������Ѿ����ύ��'}";
			try {			
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter pw = super.getResponse().getWriter();	
				pw.write(json);		
				} catch (IOException e) {
				e.printStackTrace();
			}
			return null;	
		}
		//add by lee for add property to userSelfProtal data in 20090819 begin
		String applyNum = "";//�����š�
		String applyName = "";//��������
		//add by lee for add require level in 20090827 begin
		String hurryFlag = "";//�Ƿ�Ӽ�
		if(bWrapper.isReadableProperty("requireLevel")){
			RequirementLevel level = (RequirementLevel)bWrapper.getPropertyValue("requireLevel");
			if(level!=null){
				hurryFlag = level.getId().toString();
			}
		}
		mapBizz.put("hurryFlag", hurryFlag);
		//add by lee for add require level in 20090827 end
		if(bWrapper.isReadableProperty("applyNum")){//������������
			applyNum = (String) bWrapper.getPropertyValue("applyNum");
		}
		if(bWrapper.isReadableProperty("requireId")){//������Ի�������
			applyNum = (String) bWrapper.getPropertyValue("requireId");
		}
		if(bWrapper.isReadableProperty("name")){ 	//������������
			applyName = (String) bWrapper.getPropertyValue("name");
		}
		mapBizz.put("applyNum",applyNum);
		mapBizz.put("applyName",applyName);
		//add by lee for add property to userSelfProtal data in 20090819 end
		
		if(bWrapper.isReadableProperty("flat")){
			flat = (RequireApplyDefaultAudit) bWrapper.getPropertyValue("flat");
		}
		UserInfo confirmUser = null;
		if(bWrapper.isReadableProperty("confirmUser")){
			confirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		}
		String userListStr = "";
		
		String erpxzFlag = mapBizz.get("erpxzFlag");
		ServiceItemType erpxzsiType = null;
		boolean isErpXZ = false;	//�Ƿ���ERP������
		if(erpxzFlag!=null&&!erpxzFlag.equals("")){
			erpxzsiType = (ServiceItemType) service.findUnique(ServiceItemType.class, "id", Long.parseLong(erpxzFlag));
			ServiceItemType tempFlage = serviceItem.getServiceItemType();
			Long erpxzsiTypeId = erpxzsiType.getId();
			Long tempFlageId = tempFlage.getId();
			if(erpxzsiType!=null&&(tempFlageId==erpxzsiTypeId)){
				isErpXZ = true;	//�Ƿ���ERP������
			}
		}
	//add by zhangzy for ��ӱ������������� in 2009 11 20 start 
		if(flat!=null){
			String financialManagerName = "admin";//��������������
			String groupFinanceAuditName = "admin";//���Ų���������
			String cadreBizAuditName = "admin";//����������
//			String itAuditName = "admin";//�ͻ�IT����
			UserInfo businessAudit = flat.getCadreBusinessAudit();
			UserInfo financeAudit = flat.getCadreFinanceAudit();
			UserInfo groupFinanceAudit = flat.getGroupFinanceAudit();
			UserInfo cadreBizAudit = flat.getCadreBizAudit();
//			UserInfo itAudit = flat.getClientItManager();
			if(className.equals("com.zsgj.itil.require.entity.ERP_NormalNeed")&&isErpXZ){			
				if(businessAudit != null){
					financialManagerName = (String)businessAudit.getUserName();
				}else if(financeAudit !=null){
					financialManagerName = (String)financeAudit.getUserName();
				}
			}else{
				if(financeAudit !=null){
					financialManagerName = (String)financeAudit.getUserName();
				}
			}
			if(groupFinanceAudit!=null){
				groupFinanceAuditName = groupFinanceAudit.getUserName();
			}
			if(cadreBizAudit!=null){
				cadreBizAuditName = cadreBizAudit.getUserName();
			}
//			if(itAudit!=null){
//				itAuditName = itAudit.getUserName();
//			}
			userListStr += "confirmByFinancialManager:" + financialManagerName+
							"$confirmByGroupFinance:" + groupFinanceAuditName+
							"$confirmByCadre:" + cadreBizAuditName+
							"$confirmByHome:" + cadreBizAuditName;
							//remove by lee for �ͻ�IT������ָ�� in 20091222 begin
							//"$confirmByIT:" + itAuditName+
							//"$makeShareInfo:" + itAuditName;
							//remove by lee for �ͻ�IT������ָ�� in 20091222 end
		}
	//add by zhangzy for ��ӱ������������� in 2009 11 20 end 	
		if(confirmUser!=null){
			userListStr += "$confirmByDivision:" + confirmUser.getUserName()+
							"$confirmByClientManager:" + confirmUser.getUserName();//add by lee for ��������û����������׶�Ҫ����ʱ�Ĳ��������������� in 20091104
		}
		if(userListStr.startsWith("$")){
			userListStr = userListStr.substring(1);
		}
		mapBizz.put("userList",userListStr);
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
	 * ��������פ�������������ύ����
	 * @Methods Name applyForServerManage
	 * @Create In Jul 30, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String applyForServerManage() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id

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
		String serviceItemId = mapBizz.get("serviceItemId");
		ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
		ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
		String className = siut.getClassName();
		mapBizz.put("reqClass", className);
		
		VirtualDefinitionInfo virtualDefinitionInfo=(VirtualDefinitionInfo)service.findUnique(VirtualDefinitionInfo.class, "virtualDefinitionName", definitionName);
		String description = virtualDefinitionInfo.getVirtualDefinitionDesc();
		mapBizz.put("applyTypeName",description);
		
		Object obj = service.find(this.toClass(className), dataId);
		BeanWrapper bWrapper = new BeanWrapperImpl(obj);
		Integer status = (Integer) bWrapper.getPropertyValue("status");
		if(Integer.valueOf(1).equals(status)){
			json = "{success:true,Exception:'��������Ѿ����ύ��'}";
			try {			
				super.getResponse().setCharacterEncoding("utf-8");
				PrintWriter pw = super.getResponse().getWriter();	
				pw.write(json);		
				} catch (IOException e) {
				e.printStackTrace();
			}
			return null;	
		}		

		//add by lee for add property to userSelfProtal data in 20090819 begin
		String applyNum = "";//�����š�
		String applyName = "";//��������
		//add by lee for add require level in 20090827 begin
		String hurryFlag = "";//�Ƿ�Ӽ�
		if(bWrapper.isReadableProperty("requireLevel")){
			RequirementLevel level = (RequirementLevel)bWrapper.getPropertyValue("requireLevel");
			hurryFlag = level.getId().toString();
		}
		mapBizz.put("hurryFlag", hurryFlag);
		//add by lee for add require level in 20090827 end
		if(bWrapper.isReadableProperty("applyNum")){//������������
			applyNum = (String) bWrapper.getPropertyValue("applyNum");
		}
		if(bWrapper.isReadableProperty("requireId")){//������Ի�������
			applyNum = (String) bWrapper.getPropertyValue("requireId");
		}
		if(bWrapper.isReadableProperty("name")){ 	//������������
			applyName = (String) bWrapper.getPropertyValue("name");
		}
		mapBizz.put("applyNum",applyNum);
		mapBizz.put("applyName",applyName);
		//add by lee for add property to userSelfProtal data in 20090819 end
		
		UserInfo confirmUser = null;
		if(bWrapper.isReadableProperty("confirmUser")){
			confirmUser = (UserInfo) bWrapper.getPropertyValue("confirmUser");
		}
		String userListStr = "";
		if(confirmUser!=null){
			userListStr += "confirmTogether:" + confirmUser.getUserName();
		}
		mapBizz.put("addDynCounterSign",userListStr);
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
	 * ���ݲ�ͬ����������ʾ��ͬ�������б�
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
		HttpServletRequest request = super.getRequest();
		//��Ҫ�Ĳ���
		String actor = request.getParameter("actorId");
		//String actor = UserContext.getUserInfo().getUserName();
		String json = "";
		int rowCount = 0;
	  	List<TaskInfo> list = ts.listTasks(actor);
		for(TaskInfo taskInfo:list) {
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String type = (String)bizParams.get("applyType");
			if("rproject".equals(type)||"baproject".equals(type)||"acproject".equals(type)){
				String str = "";
				
				str += "taskId:'"+taskInfo.getId()+"',";
				str += "taskName:'"+taskInfo.getName()+"',";
				
				//add by lee for ���������˺�����������Ϣ in 20091229 begin
				String applyDate = "";			//��������
				String applyUser = "";			//������
				String delegateApplyUser = "";	//��������
				String dataId=(String)bizParams.get("dataId");
				str += "applyId:'"+dataId+"',";
				str += "applyNum:'"+(String)bizParams.get("applyNum")+"',";
				str += "applyName:'"+(String)bizParams.get("applyName")+"',";
				str += "applyType:'"+type+"',";
				str += "applyTypeName:'"+(String)bizParams.get("applyTypeName")+"',";
				String className = null;
				if("rproject".equals(type)){
					String serviceItemId = (String)bizParams.get("serviceItemId");
					ServiceItem serviceItem = (ServiceItem) service.find(ServiceItem.class, serviceItemId);
					ServiceItemUserTable siut = (ServiceItemUserTable) service.findUnique(ServiceItemUserTable.class, "serviceItem", serviceItem);
					className = siut.getClassName();
				}else if("acproject".equals(type)){
					String serviceItemId = (String)bizParams.get("serviceItemId");
					if("295".equals(serviceItemId)){
						className = "com.zsgj.itil.require.entity.HRSAccountApply";
					}else{
						className = "com.zsgj.itil.require.entity.AccountApplyMainTable";
					}
				}else if("baproject".equals(type)){
					className = "com.zsgj.itil.require.entity.BusinessAccount";
				}
				Object obj = service.find(this.toClass(className), dataId);
				BeanWrapper bWrapper = new BeanWrapperImpl(obj);
				if(bWrapper.isReadableProperty("applyDate")&&bWrapper.getPropertyValue("applyDate")!=null){
					applyDate = bWrapper.getPropertyValue("applyDate").toString();
				}
				if(bWrapper.isReadableProperty("applyUser")){
					UserInfo user1 = (UserInfo) bWrapper.getPropertyValue("applyUser");
					if(user1!=null){
						applyUser = user1.getRealName()+"/"+user1.getUserName();
					}
				}
				if(bWrapper.isReadableProperty("delegateApplyUser")){
					UserInfo user2 = (UserInfo) bWrapper.getPropertyValue("delegateApplyUser");
					if(user2!=null){
						delegateApplyUser = user2.getRealName()+"/"+user2.getUserName();
					}
				}
				str += "applyDate:'"+applyDate+"',";
				str += "applyUser:'"+applyUser+"',";
				str += "delegateApplyUser:'"+delegateApplyUser+"'";	
				//add by lee for ���������˺�����������Ϣ in 20091229 end
				/*JSONObject jo = JSONObject.fromObject(bizParams);
				String strBizParams = jo.toString();
				strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
				if(strBizParams.startsWith("{")) {
					strBizParams = strBizParams.substring(1);
				}
				if(strBizParams.endsWith("}")) {
					strBizParams = strBizParams.substring(0,strBizParams.length()-1);
				}*/
//add by zhangzy for ����������û������ܾ��󵽵�ǰ�������������� �����������⴦�� in 2010 01 26 start				
//				WorkflowRecordTaskInfo wrti = (WorkflowRecordTaskInfo) service.findUnique(WorkflowRecordTaskInfo.class, "taskId", taskInfo.getId());
//				String strIsRefuseFlag = "false";
//				if(wrti!=null){
//					strIsRefuseFlag = isRefuseFlag(dataId, taskInfo.getProcessId()+"", wrti.getNodeId()+"");
//				}				
//				str += strBizParams+"";
//				str += "isRefuseFlag:'"+strIsRefuseFlag+"',";
//				str += "comments:'"+toBlank(taskInfo.getComments().getValue("comment"))+"'";
				str = "{"+str+"},";
				//String defname = taskInfo.getDefinitionName();
				json += str;
				rowCount++;
//add by zhangzy for ����������û������ܾ��󵽵�ǰ�������������� �����������⴦�� in 2010 01 26 end				
			}
		}		
		json = deleteComma(json);
		json =  "{success: true, rowCount:'"+rowCount+"',data:["+json+"]}";
		
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		return null;
	}
	/**
	 * ����������û������ܾ��󵽵�ǰ�������������죬�򷵻ء�true��
	 * @Methods Name isRefuseFlag
	 * @Create In 26 01 2010 By zhangzy
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	public String isRefuseFlag(String dataId,String processId,String nodeId){
		String isRefuseFlag = rs.isRefuseFlag(dataId, processId, nodeId);
		return isRefuseFlag;
	}
	/**
	 * ���ĳ���̵�������ʷ��Ϣ
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	public String next() throws Exception{
		//��Ҫ�Ĳ���
		String taskId = super.getRequest().getParameter("taskid");	 
		String procId = super.getRequest().getParameter("procid");		
		long instId = 0;
		if(procId!=null&&procId.trim().length()!=0) {
			instId = Long.parseLong(procId);
			List tasks = ps.getActiveTasks(instId);
			if(!tasks.isEmpty()) {
				taskId = ((TaskInfo)tasks.get(0)).getId()+"";
			}
		}
		else if(taskId!=null&&taskId.trim().length()!=0){
			instId = ts.getProcessInfo(Long.parseLong(taskId)).getId();
		}
		else {
			System.out.println("ListHistoryAction��������");
		}
		
//	 	List<HistoryInfo> list = ps.getHistory(instId);
	 	List<HistoryInfo> list = new ArrayList();
	 	List tasks = ps.getActiveTasks(instId);
	 	for(int i=0;i<tasks.size();i++) {
	 		TaskInfo ti = (TaskInfo)tasks.get(i);
	 		HistoryInfo hi = new HistoryInfo(ti);
	 		hi.setTaskName(ti.getName());
	 		list.add(hi);
	 	}
	 	String json = "";
	 	for(HistoryInfo historyInfo:list) {
	 		String str = "";
	 		//historyInfo.getComments()
	 		//��ʵ�����ƴ����û�ϵͳ��
//	 		String realName = getUserRealNameByName(historyInfo.getActorId());
//	 		str += "actorId:'"+realName+"',";
	 		str += "date:'"+historyInfo.getDate()+"',";	
	 		str += "definitionName:'"+historyInfo.getDefinitionName()+"',";
	 		str += "processId:'"+historyInfo.getProcessId()+"',";
	 		str += "nodeName:'"+historyInfo.getNodeName()+"',";
	 		str += "taskName:'"+historyInfo.getTaskName()+"',";
	 		str += "name:'"+historyInfo.getName()+"',";
	 		str += "taskId:'"+historyInfo.getTaskId()+"'";
	 		str = "{"+str+"},";
	 		json += str;
	 	}
	 	if(json.endsWith(",")) {
	 		json = json.substring(0,json.length()-1);
	 	}
	 	json = "["+json+"]";
	 	//json = "{data:["+json+"]}";
	 	//json = "{success:true,data:"+json+"}";
		return json;		
	}
	
	@SuppressWarnings("unused")
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
}

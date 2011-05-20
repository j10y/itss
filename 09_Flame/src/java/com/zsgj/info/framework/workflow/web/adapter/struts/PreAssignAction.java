package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.extjs.servlet.CoderForSave;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.TaskAssignService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.DefinitionPreAssign;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;
import com.zsgj.info.framework.workflow.entity.WorkflowRoleMaping;
import com.zsgj.info.framework.workflow.info.NodeInfo;

public class PreAssignAction extends BaseDispatchAction {
	private Service bs = getService();
	//private MetaDataService ms = (MetaDataService) getBean("metaDataService");
//	private boolean showListSearchTitle = true;
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private ConfigUnitService configUnitService = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
//	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private TaskAssignService tas = (TaskAssignService) super.getBean("taskAssignService");
	private DepartmentService deptserv = (DepartmentService) super.getBean("deptService");

/**
 * ����ҳ��
 * @Methods Name forAdd
 * @Create In Dec 10, 2008 By yang
 * @param mapping
 * @param actionForm
 * @param request
 * @param response
 * @return
 * @throws Exception 
 * @ReturnType ActionForward
 */
	@SuppressWarnings("unchecked")
	public ActionForward forAdd(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserInfo userInfo = UserContext.getUserInfo();
		//List<WorkflowRole> findWorkflowRoleByUserInfo(UserInfo);
		//Department findDepartmentByUserInfo(UserInfo)
		//List<Department> findDepartmentParents(Department)
//		List<Department> depts = deptserv.findDeptAll();
//		List<WorkflowRole> workRoles = getService().findAll(WorkflowRole.class);
//		List<Map<String,Object>> definitions = new ArrayList<Map<String,Object>>();
		
		List<Department> depts = deptserv.findDeptAll();
		List<WorkflowRole> workRoles = tas.findWorkflowRoleByUserInfo(userInfo);
		//List<Map<String,Object>> definitions = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> definitionMap = new HashMap<String,Object>();
		List<TaskPreAssign> tpas = getService().find(TaskPreAssign.class,"actorId",UserContext.getUserInfo().getUserName());
		
		for(WorkflowRole workflowRole: workRoles) {
			//�����ж���ڵ�
			List<DefinitionPreAssign> dpas = getService().find(DefinitionPreAssign.class, "workflowRole", workflowRole);
			for(DefinitionPreAssign dpa: dpas) {
				//���˵��Ѿ��������Ľڵ�
				boolean hasProxy = false;
				for(TaskPreAssign tpa: tpas) {
					String tpaDefName = tpa.getDefinitionName();
					String tpaNodeName = tpa.getTaskName();
					String dpaDefName = dpa.getDefinitionName();
					String dpaNodeName = dpa.getNodeName();
					if(dpaDefName.equalsIgnoreCase(tpaDefName)&&dpaNodeName.equalsIgnoreCase(tpaNodeName)) {
						hasProxy = true;
						break;
					}
				}
				if(hasProxy) {
					continue;
				}
				
				String defDeptCode = dpa.getDepartmentCode();
				boolean isParentDept = false;
				for(Department dept: depts) {
					if(String.valueOf(dept.getDepartCode()).equals(defDeptCode)) {
						isParentDept = true;
						break;
					}
				}
				if(isParentDept) {			
					List<DefinitionPreAssign> nodes = (List<DefinitionPreAssign>)definitionMap.get(dpa.getDefinitionName());
					if(nodes==null||nodes.isEmpty()) {
						nodes = new ArrayList<DefinitionPreAssign>();						
					}
					nodes.add(dpa);
					definitionMap.put(dpa.getDefinitionName(), nodes);
				}
			}
		}
			
		String json = "";
		List<UserTableSetting> columns = metaDataManager.getUserColumnForEdit(TaskPreAssign.class);
		Map<String, Object> dataMap = null;
		dataMap = metaDataManager.getEntityDataForAdd(TaskPreAssign.class);
		json = CoderForSave.encode(dataMap, columns, false);
		Map jsonMap = new HashMap();
		
		jsonMap.put("json", json);
		jsonMap.put("defs", definitionMap);
		
		json = JSONObject.fromObject(jsonMap).toString();	
		
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}
	
	public ActionForward forSave(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String json = "";
		String id = request.getParameter("id");
		Class clazz = TaskPreAssign.class;
		List<UserTableSetting> columns = metaDataManager.getUserColumnForEdit(clazz);
		Map<String, Object> dataMap = null;

		if (StringUtils.isNotBlank(id)) {
			@SuppressWarnings("unused")
			Object detail = (Object) bs.find(clazz, id, true);// ���б�����ɾ������Ч
			dataMap = metaDataManager.getEntityDataForEdit(clazz, id);
			json = CoderForSave.encode(dataMap, columns, true);
		} else {
			dataMap = metaDataManager.getEntityDataForAdd(clazz);
			json = CoderForSave.encode(dataMap, columns, false);
		}
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}
/**
 * ɾ��
 * @Methods Name delete
 * @Create In Dec 10, 2008 By yang
 * @param mapping
 * @param actionForm
 * @param request
 * @param response
 * @return
 * @throws Exception 
 * @ReturnType ActionForward
 */
	public ActionForward remove(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String idsp = request.getParameter("ids");
		String[] ids = idsp==null?null:idsp.split(",");
		if(ids!=null) {
			for(String id:ids) {
				getService().remove(TaskPreAssign.class, id);
			}
		}
		
		String json = "{success:true}";
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	} 
	
	/**
	 * ������е����̽�ɫ
	 * @Methods Name getAllWorkflowRole
	 * @Create In Dec 23, 2008 By yang
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @ReturnType ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getAllWorkflowRole(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String json = "";
		List<WorkflowRole> workFlowRoles = getService().find(WorkflowRole.class,"deleteFlag",new Integer(0));
		if(workFlowRoles!=null&&!workFlowRoles.isEmpty()) {
			for(WorkflowRole wr: workFlowRoles) {
				String str = "";
				str += "['"+wr.getId()+"','"+wr.getName()+"']"; 
				json += str +",";
			}
			if(json.endsWith(",")) {
				json = "["+json.substring(0,json.length()-1)+"]";
			}
		}
		else {
			json = "{success:false}";
		}	
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward getWorkflowRoleByRole(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String json = "";
		String id  = request.getParameter("id");//roleId
		Role role = (Role)getService().find(Role.class, id);
		List<WorkflowRoleMaping> selectedWorkFlowRoles = getService().find(WorkflowRoleMaping.class, "role", role);
		List<WorkflowRole> allWorkFlowRoles = getService().find(WorkflowRole.class,"deleteFlag",new Integer(0));
		if(selectedWorkFlowRoles!=null&&!selectedWorkFlowRoles.isEmpty()) {
			for(WorkflowRoleMaping wfrm: selectedWorkFlowRoles) {
				String swfrId = wfrm.getWorkflowRole().getId()+"";
				for(WorkflowRole wfr:allWorkFlowRoles) {
					String wfrId = wfr.getId()+"";
					if(swfrId.equalsIgnoreCase(wfrId)) {//�Ѿ���ѡ��
						allWorkFlowRoles.remove(wfr);
						break;
					}
				}
			}
		}
		String selected = "";
		String restwfr = "";
		for(WorkflowRoleMaping wfrm: selectedWorkFlowRoles) {
			selected += "['"+wfrm.getWorkflowRole().getId()+"','"+wfrm.getWorkflowRole().getName()+"'],";
		}
		if(selected.endsWith(",")) {
			selected = selected.substring(0,selected.length()-1);
		}
		for(WorkflowRole wfr:allWorkFlowRoles) {
			restwfr += "['"+wfr.getId()+"','"+wfr.getName()+"'],";
		}
		if(restwfr.endsWith(",")) {
			restwfr = restwfr.substring(0,restwfr.length()-1);
		}
		
		json = "{'rest':["+restwfr+"],'selected':["+selected+"]}";
				
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}
	
	
	@SuppressWarnings(value = { "unchecked" })
	public ActionForward saveProxy(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserInfo userInfo = UserContext.getUserInfo();
		Department department = deptserv.findDepartmentByUserInfo(userInfo);
		try {
			String json = "{success:true}";
			String info = request.getParameter("info");
//			String preAssignId = request.getParameter("defPreAssignId");
			info = HttpUtil.ConverUnicode(info);
			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(info);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String value = jo.getString(key);
				infoMap.put(key, value);
			}
			String proxySelect = (String)infoMap.get("proxySelect");
			String[] proxyIds = proxySelect.split(",");
			String proxyRealNames = "";
			for(String proxyId: proxyIds) {
				List users = getService().find(UserInfo.class, "userName", proxyId);
				if(users!=null&&!users.isEmpty()) {
					String proxyRealName = ((UserInfo)users.get(0)).getRealName();
					proxyRealNames += proxyRealName+",";
				}
			}
			if(proxyRealNames.endsWith(",")) {
				proxyRealNames = proxyRealNames.substring(0,proxyRealNames.length()-1);
			}
			proxySelect = proxySelect.replaceAll(",", "\\|");
			String proxyId = proxySelect;
						
			//���������ֶΣ���proxySelect����
			infoMap.put("proxyId", proxyId);
			infoMap.put("proxyName", proxyRealNames);
			infoMap.put("actorId", userInfo.getUserName());
			infoMap.put("actorName", userInfo.getRealName());
			infoMap.put("departmentCode", department.getDepartCode());
			infoMap.put("departmentName", department.getDepartName());	
//			

//			TaskPreAssign taskPreAssign = null;
			metaDataManager.saveEntityData(TaskPreAssign.class, infoMap);

			response.setCharacterEncoding("gbk");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding("gbk");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}
	
	/**
	 * ���Ĭ��ִ���ˣ���ǰ�û����Ŀ��ܴ������б���ͬ������Ա
	 * @Methods Name proxies
	 * @Create In Nov 14, 2008 By yang
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @ReturnType ActionForward
	 */
	@SuppressWarnings(value = { "unchecked" })
	public ActionForward workmates(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserInfo usrInfo = UserContext.getUserInfo();
		UserInfo uni = UserContext.getUserInfo();
		Long departCode = uni.getDepartCode();
		List<UserInfo> proxies = getService().find(UserInfo.class, "departCode", departCode);
		try {
			String json = "";
			for(UserInfo user: proxies) {
				if(!usrInfo.equals(user)) {
					json += "[";
					json += "'"+user.getUserName()+"',";
					json += "'"+user.getRealName()+"'";
					json += "],";
				}
			}
			if(json.endsWith(",")) {
				json = json.substring(0,json.length()-1);
			}
			json = "["+json+"]";
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}
	/**
	 * �õ���ҳ��
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public int getNumber(HttpServletRequest request, String param,
			int defaultValue) {
		String strValue = request.getParameter(param);
		if (strValue == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(strValue);
		}
	}
	
	/**
	 * ��ô���������
	 * @Methods Name proxies
	 * @Create In Nov 24, 2008 By yang
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @ReturnType ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward proxies(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String preAssignId = request.getParameter("id");
		String proxyId = "";
		TaskPreAssign tpa = (TaskPreAssign)getService().find(TaskPreAssign.class, preAssignId);
		if(tpa!=null) {
			proxyId = tpa.getProxyId();
		}
		String json = "";
		if(proxyId!=null) {
			String[] proxyIds = proxyId.split("\\|");			
			for(String proxyIdItem: proxyIds) {
				List<UserInfo> users = getService().find(UserInfo.class, "userName", proxyIdItem);
				if(users!=null&&!users.isEmpty()) {
					UserInfo user = users.get(0);
					json += "[";
					json += "'"+user.getUserName()+"',";
					json += "'"+user.getRealName()+"'";
					json += "],";				
				}
			}
			
			if(json.endsWith(",")) {
				json = json.substring(0,json.length()-1);
			}
		}
		json = "["+json+"]";
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
		return null;
	}


	@SuppressWarnings("unused")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}
	
	public ActionForward preassign(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String pdn = request.getParameter("pdn");
		request.setAttribute("pdn",pdn); 
	    
		List<NodeInfo> list = ds.getTaskNodes(pdn);
		request.getSession().setAttribute("taskNodes",list);
		
		
		return mapping.findForward("success");
	}
	
	/**
	 * �������ж��ڴ�����
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getUserInfoWorkmates(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int total = 0;
		int pageSize = 10;
		int start = this.getNumber(request, "start", 0);	
		int pageNo = start / pageSize + 1;
		String userName = request.getParameter("realName");
		userName = HttpUtil.ConverUnicode(userName);
		List<UserInfo> searchUserName = new ArrayList<UserInfo>();
		UserInfo userInfo = UserContext.getUserInfo();
		UserInfo uni = UserContext.getUserInfo();
		Long departCode = uni.getDepartCode();
		List<UserInfo> userNameList = getService().find(UserInfo.class, "departCode", departCode);//���ҵ�����
		try {
			String json = "";
			if(!"".equals(userName)&&userName!=null){//�û���������Ӧ��ֵ
				for(int i=0;i<userNameList.size();i++){//����ͬ��
					if(!userInfo.equals(userNameList.get(i))&&userInfo!=userNameList.get(i)){//ȥ���������Լ��Լ�
						if(userNameList.get(i).getRealName().contains(userName)){//��ʼ�г�ƥ���ͬ������
							searchUserName.add(userNameList.get(i));
						}
					}
				}
				int length = 0;
				if(pageNo*pageSize>searchUserName.size()){
					length = searchUserName.size();
				}else{
					length = pageNo*pageSize;
				}
				for(int i=pageNo*pageSize-10;i<length;i++){
					String UserName = searchUserName.get(i).getUserName();
					String realName = searchUserName.get(i).getRealName();
					String name = realName+"("+UserName+")";
					json += "{id:" + i +",realName:'"+name+"'},";
				}
				total = searchUserName.size();
			}else{
				
				int length = 0;
				if(pageNo*pageSize>userNameList.size()){
					length = userNameList.size();
				}else{
					length = pageNo*pageSize;
				}
				for(int i=pageNo*pageSize-10;i<length;i++){
					String UserName = userNameList.get(i).getUserName();
					String realName = userNameList.get(i).getRealName();
					String name = realName+"("+UserName+")";
					json += "{id:" + i +",realName:'"+name+"'},";
				}
				total = userNameList.size();
			}
			if(json.endsWith(",")) {
				json = json.substring(0,json.length()-1);
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
		}
		return null;
	}
	/**
	 * �������ж��ڴ�����֮���������������ʾ������м�¼
	 * ���ͬһ���û��ύ���α��뱣֤ʱ�䲻���ص�������ص����������䱣�档Ҫ���Ѻ���ʾ
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveUserInfoWorkmates(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String realName = "";
		String userName = "";
		boolean flag = false;
		UserInfo userInfo = UserContext.getUserInfo();
		String proxyMate = HttpUtil.ConverUnicode(request.getParameter("proxyAuditPerson"));
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate = sdfs.parse(start);
		Long newStartDate = startDate.getTime();
		SimpleDateFormat sdfe = new SimpleDateFormat("yyyy-MM-dd"); 
		Date endDate = sdfe.parse(end);
		Long newEndDate = endDate.getTime();
		String[] userInfoNames = proxyMate.split(",");
		List<TaskPreAssign> userProxyList = super.getService().find(TaskPreAssign.class, "actorId", userInfo.getUserName());
		for(TaskPreAssign userProxy : userProxyList){
			Date dbBeginDate = userProxy.getProxyBegin();
			Date dbEndDate = userProxy.getProxyEnd();
			Long existBeginDate = dbBeginDate.getTime();
			Long existEndDate = dbEndDate.getTime();
			if(newStartDate<=existEndDate&&newEndDate>existBeginDate){//˵��ʱ�����ص�
				flag = true;
			}
		}
		if(flag){
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("{success:" +true+ ",flag:"+flag+"}");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				
			}finally{
				if(out != null)
					out.close();
			}
		}else{
			for(int i=0;i<userInfoNames.length;i++){
				realName += userInfoNames[i].substring(0,userInfoNames[i].indexOf("("));
				realName += ",";
				userName += userInfoNames[i].substring(userInfoNames[i].indexOf("(")+1,userInfoNames[i].indexOf(")"));
				userName += ",";
			}
			if(realName.endsWith(",")){
				realName = realName.substring(0,realName.length()-1);
			}
			if(userName.endsWith(",")){
				userName = userName.substring(0,userName.length()-1);
			}
			TaskPreAssign taskPreAssign = new TaskPreAssign();
			taskPreAssign.setActorName(userInfo.getRealName());
			taskPreAssign.setProxyName(realName);
			taskPreAssign.setProxyId(userName);
			taskPreAssign.setActorId(userInfo.getUserName());
			taskPreAssign.setProxyBegin(startDate);
			taskPreAssign.setProxyEnd(endDate);
			super.getService().save(taskPreAssign);
			
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("{success:" +true+ "}");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				
			}finally{
				if(out != null)
					out.close();
			}
		}
		return null;
	}
	/**
	 * �������ж��ڴ�����֮��ʾ������м�¼
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward showAllUserInfoWorkmates(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		String json = "";
		List<TaskPreAssign> taskPreAssign = super.getService().findAll(TaskPreAssign.class);
		for(TaskPreAssign tpa : taskPreAssign){
			json += "{'id':"+tpa.getId()+",'userName':'"+tpa.getActorName()+"','proxyName':'"+tpa.getProxyName()+"','proxyStartDate':'"+tpa.getProxyBegin()+"','proxyEndDate':'"+tpa.getProxyEnd()+"'},";
		}
		if(json.endsWith(",")){
			json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		}else{
			json = "{data:[" + json + "]}";
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
	 * �������ж��ڴ�����֮ɾ������������
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeUserInfoWorkmates(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{
		String[] ids = request.getParameterValues("ids");
		for(int i=0;i<ids.length;i++){
			TaskPreAssign timer = (TaskPreAssign)super.getService().find(TaskPreAssign.class, ids[i]);
			super.getService().remove(timer);
		}	
		try {
			response.getWriter().write("{success:" + true+ "}");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ����id��ȡTaskPreAssign�Ļ�����Ϣ�����޸Ĺ���
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskPreAssignById(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{
		String id = request.getParameter("id");
		TaskPreAssign taskPreAssign = (TaskPreAssign) super.getService().findUnique(TaskPreAssign.class, "id", Long.parseLong(id));
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb.append("{success:true,data:[");
		String[] names = taskPreAssign.getProxyName().split(",");
		String[] ids = taskPreAssign.getProxyId().split(",");
		for(int i=0 ; i<names.length ; i++) {
			String proxyAuditPerson = names[i] + "(" + ids[i] +")";
			sb.append("{proxyAuditPerson:'").append(proxyAuditPerson).append("',");
			sb.append("start:'").append(taskPreAssign.getProxyBegin()==null?"":simpleDateFormat.format(taskPreAssign.getProxyBegin())).append("',");
			sb.append("end:'").append(taskPreAssign.getProxyEnd()==null?"":simpleDateFormat.format(taskPreAssign.getProxyEnd())).append("'},");
		}
		sb.append("]}");
		sb.deleteCharAt(sb.lastIndexOf(","));
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(sb.toString());
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * ����id��ȡTaskPreAssign�Ļ�����Ϣ�����޸Ĺ���(gridPanel����form���е�����)
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTaskPreAssignFormById(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{
		String id = request.getParameter("id");
		TaskPreAssign taskPreAssign = (TaskPreAssign) super.getService().findUnique(TaskPreAssign.class, "id", Long.parseLong(id));
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb.append("{success:true,");
		sb.append("proxyAuditPerson:'").append(taskPreAssign.getProxyName()==null?"":taskPreAssign.getProxyName()).append("',");
		sb.append("start:'").append(taskPreAssign.getProxyBegin()==null?"":simpleDateFormat.format(taskPreAssign.getProxyBegin())).append("',");
		sb.append("end:'").append(taskPreAssign.getProxyEnd()==null?"":simpleDateFormat.format(taskPreAssign.getProxyEnd())).append("'");
		sb.append("}");
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(sb.toString());
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * �޸�����
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward modifyUserInfoWorkmates(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String realName = "";
		String userName = "";
		boolean flag = false;
		UserInfo userInfo = UserContext.getUserInfo();
		String proxyMate = HttpUtil.ConverUnicode(request.getParameter("proxyAuditPerson"));
		String modifyPreAssignId = request.getParameter("modifyPreAssignId");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate = sdfs.parse(start);
		Long newStartDate = startDate.getTime();
		SimpleDateFormat sdfe = new SimpleDateFormat("yyyy-MM-dd"); 
		Date endDate = sdfe.parse(end);
		Long newEndDate = endDate.getTime();
		String[] userInfoNames = proxyMate.split(",");
		TaskPreAssign userProxy = (TaskPreAssign)super.getService().find(TaskPreAssign.class, modifyPreAssignId);
		
		List<TaskPreAssign> taskPreAssign = super.getService().find(TaskPreAssign.class, "actorId", userInfo.getUserName());
		for(int i=0;i<taskPreAssign.size();i++){
			if(taskPreAssign.get(i).getId()!=Long.valueOf(modifyPreAssignId)&&!taskPreAssign.get(i).getId().equals(Long.valueOf(modifyPreAssignId))){
				Date dbBeginDate = taskPreAssign.get(i).getProxyBegin();
				Date dbEndDate = taskPreAssign.get(i).getProxyEnd();
				Long existBeginDate = dbBeginDate.getTime();
				Long existEndDate = dbEndDate.getTime();
				if(newStartDate<=existEndDate&&newEndDate>=existBeginDate){//˵��ʱ�����ص�
					flag = true;
					break;
				}
			}
		}
		if(flag){
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("{success:" +true+ ",flag:"+flag+"}");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				
			}finally{
				if(out != null)
					out.close();
			}
		}else{
			for(int i=0;i<userInfoNames.length;i++){
				realName += userInfoNames[i].substring(0,userInfoNames[i].indexOf("("));
				realName += ",";
				userName += userInfoNames[i].substring(userInfoNames[i].indexOf("(")+1,userInfoNames[i].indexOf(")"));
				userName += ",";
			}
			if(realName.endsWith(",")){
				realName = realName.substring(0,realName.length()-1);
			}
			if(userName.endsWith(",")){
				userName = userName.substring(0,userName.length()-1);
			}
			userProxy.setActorName(userInfo.getRealName());
			userProxy.setProxyName(realName);
			userProxy.setProxyId(userName);
			userProxy.setActorId(userInfo.getUserName());
			userProxy.setProxyBegin(startDate);
			userProxy.setProxyEnd(endDate);
			super.getService().save(userProxy);
			
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.write("{success:" +true+ "}");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				
			}finally{
				if(out != null)
					out.close();
			}
		}
		return null;
	}
	/**
	 * �������ж��ڴ�����֮�õ���Ҫ�û�����������
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getUserInfoProcessDefinition(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		UserInfo userInfo = UserContext.getUserInfo();
		Set<VirtualDefinitionInfo> userNameSet = new HashSet();
		Set<Role> userRole = userInfo.getRoles();//�õ��û����еĽ�ɫ����
		//��ʼ���Һ��û���ɫһֱ����������
		List<VirtualDefinitionInfo> vdf = super.getService().findAll(VirtualDefinitionInfo.class);//���ҵ����е���������
		for(VirtualDefinitionInfo virtualDefinitionInfo : vdf){//��ʼ�������е���������
			Long virtualDefId = virtualDefinitionInfo.getId();//����ĳ����������ID
			//ͨ���������̵õ���Ӧ�Ľڵ���Ϣ
			List<VirtualNodeInfo> vni = super.getService().find(VirtualNodeInfo.class, "virtualDefinitionInfo", virtualDefinitionInfo);
			for(VirtualNodeInfo virtualNodeInfo : vni){
				Long nodeId = virtualNodeInfo.getId();//ĳ�����������о���ڵ��Id
				//ͨ�����������ID�ͽڵ�ID�ҵ���Ӧ�Ľ�ɫ�ڵ�
				ConfigUnitRole configUnitRole = configUnitService.findConfigUnitRole(String.valueOf(virtualDefId), String.valueOf(nodeId));
				//�ҵ���Ӧ�Ľ�ɫ�ڵ�֮��Ҫ��ʼ�õ���ɫ�ڵ������еĽ�ɫ
				Set<Role> nodeRoles = new HashSet<Role>();
				if(configUnitRole!=null){
					nodeRoles = configUnitRole.getRoles();
					//��ʱ��ʼ���û���ɫ�ͽڵ��ɫ��û���ظ��ģ�����ظ���˵����������Ҫ�û�����
					boolean flag = this.isHaveRepeateProcessDefinition(userRole, nodeRoles);
					if(flag){//˵���˽ڵ㺬�������ɫ
						userNameSet.add(virtualDefinitionInfo);
						break;
					}
				}
				
			}
		}
		//�ҵ����к��д��û���ɫ����������,Ȼ��ʼƴװ����
		int total = 0;
		int pageSize = 10;
		int start = this.getNumber(request, "start", 0);	
		int pageNo = start / pageSize + 1;
		String processName = request.getParameter("processName");
		processName = HttpUtil.ConverUnicode(processName);
		Set<VirtualDefinitionInfo> searchUserName = new HashSet<VirtualDefinitionInfo>();
		try {
			String json = "";
			if(!"".equals(processName)&&processName!=null){//�û���������Ӧ��ֵ
				for(VirtualDefinitionInfo virtualDef : userNameSet){
					if(virtualDef.getVirtualDefinitionDesc().contains(processName)){
						searchUserName.add(virtualDef);
					}
				}
				int length = 0;
				if(pageNo*pageSize>searchUserName.size()){
					length = searchUserName.size();
				}else{
					length = pageNo*pageSize;
				}
				VirtualDefinitionInfo[] searchValue = searchUserName.toArray(new VirtualDefinitionInfo[0]);
				for(int i=pageNo*pageSize-10;i<length;i++){
//					Long id = searchValue[i].getId();
					String virtualName = searchValue[i].getVirtualDefinitionDesc();
					
					json += "{id:" + i +",processName:'"+virtualName+"'},";
				}
				total = searchUserName.size();
			}else{
				int length = 0;
				if(pageNo*pageSize>userNameSet.size()){
					length = userNameSet.size();
				}else{
					length = pageNo*pageSize;
				}
				VirtualDefinitionInfo[] userAllValue = userNameSet.toArray(new VirtualDefinitionInfo[0]);
				for(int i=pageNo*pageSize-10;i<length;i++){
//					Long id = userAllValue[i].getId();
					String virtualName = userAllValue[i].getVirtualDefinitionDesc();
					
					json += "{id:" + i +",processName:'"+virtualName+"'},";
				}
				total = userNameSet.size();
			}
			if(json.endsWith(",")) {
				json = json.substring(0,json.length()-1);
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
		}
		return null;
	}
	/**
	 *  ������̽�ɫ�����û���ɫ��������true��
	 * @param userRole
	 * @param nodeRoles
	 * @return
	 */
	public boolean isHaveRepeateProcessDefinition(Set<Role> userRole,Set<Role> nodeRoles){
		Role[] nodeRolesMessage = nodeRoles.toArray(new Role[0]);
		Role[] userRoleMessage = userRole.toArray(new Role[0]);
		for(int i=0;i<userRoleMessage.length;i++){
			for(int j=0;j<nodeRolesMessage.length;j++){
				if(userRoleMessage[i].equals(nodeRolesMessage[j])){
					return true;
				}
			}
		}
		return false;
	}
}

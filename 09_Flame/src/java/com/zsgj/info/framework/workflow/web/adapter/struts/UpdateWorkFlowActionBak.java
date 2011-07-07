package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.appframework.extjs.servlet.CoderForList;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.UpdateWorkflowService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.PageModelConfigUnit;
import com.zsgj.info.framework.workflow.entity.RuleConfigUnit;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;
import com.zsgj.info.framework.workflow.info.NodeInfo;

/**
 * @author ���� E-mail: yangtao@info.com
 * @version ����ʱ�䣺Mar 10, 2009 4:03:38 PM ��˵��
 */

public class UpdateWorkFlowActionBak extends BaseDispatchAction {

	private Service service = (Service) ContextHolder.getBean("baseService");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder
			.getBean("metaDataManager");
	private DefinitionService ds = (DefinitionService) ContextHolder
			.getBean("definitionService");
	private UpdateWorkflowService updateWorkflowService = (UpdateWorkflowService) ContextHolder
			.getBean("updateWorkflowService");

	/**
	 * �õ����е���������
	 * (��ʱδ��)
	 * @Methods Name getAllVirtualProcess
	 * @Create In Mar 10, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getAllVirtualProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "";
		Class clazz = getClass(request.getParameter("clazz"));
		/*
		 * ��ȡ�������
		 */
		int pageNo = HttpUtil.getInt(request, "start", 1);
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String realProcessDesc = request.getParameter("realProcessDesc");
		// �������
		realProcessDesc = HttpUtil.ConverUnicode(realProcessDesc);

		Map requestParams = HttpUtil.requestParam2Map(request);
		requestParams.remove("realProcessDesc");
		requestParams.put("realDefinitionDesc", realProcessDesc);

		Page page = metaDataManager.query(clazz, requestParams, pageNo,
				pageSize, orderBy, isAsc);

		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager
				.getEntityMapDataForList(clazz, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager
				.getUserColumnForList(clazz);
		json = CoderForList.encode(userVisibleColumns, listData, page
				.getTotalCount());
		json = json.replaceAll("[\\n|\\r]", "");// ɾ���ַ����еĻ����ַ�

		response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;
	}
	/**
	 * �޸ĵ�ʱ������ԭ����ҳ��
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSearchVirtualDefinitionInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String json = "{data:[";
		String realProcessDesc = request.getParameter("realProcessDesc");
		// �������
		realProcessDesc = HttpUtil.ConverUnicode(realProcessDesc);
		List<VirtualDefinitionInfo> vProcessDefinition = super.getService().find(VirtualDefinitionInfo.class, "realDefinitionDesc", realProcessDesc);
		for(VirtualDefinitionInfo vpd : vProcessDefinition){
			ProcessDefinition pd = ds.getDefinitionById(vpd.getProcessDefinitionId());
			json += "{id:" + vpd.getId() + ",realDefinitionDesc:'"
					+ vpd.getRealDefinitionDesc() + "',virtualDefinitionDesc:'"
					+ vpd.getVirtualDefinitionDesc() + "',ruleFileName:'"
					+ vpd.getRuleFileName() + "',deptName:'"
					+ vpd.getDept().getDepartName() +
					"',virtualDefinitionName:'"+vpd.getVirtualDefinitionName()+
					"',version:'["+pd.getVersion()+"]'}";
			json += ",";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json += "],rowCount:"+vProcessDefinition.size()+"}";

		response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();  
		out.close();

		return null;
	}

	/**
	 * �õ����е��������̣�û���ÿ�ܣ�
	 * 
	 * @Methods Name getAllVirtualDefinitionInfo
	 * @Create In Mar 17, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	
	public ActionForward getAllVirtualDefinitionInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = "{data:[";

		/*
		 * ��ȡ�������
		 */
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/10+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String realProcessDesc = request.getParameter("realProcessDesc");
		String virtualDefinitionDesc = request.getParameter("virtualDefinitionDesc");
		// �������
		realProcessDesc = HttpUtil.ConverUnicode(realProcessDesc);
		virtualDefinitionDesc = HttpUtil.ConverUnicode(virtualDefinitionDesc);
		Map requestParams = HttpUtil.requestParam2Map(request);
		requestParams.remove("realProcessDesc");
		requestParams.remove("virtualDefinitionDesc");
		requestParams.put("realDefinitionDesc", realProcessDesc);
		requestParams.put("virtualDefinitionDesc", virtualDefinitionDesc);
		Page page = updateWorkflowService.getVirtualDefinitionInfo(
				requestParams, pageNo, 10, orderBy, isAsc);
		Long total = page.getTotalCount();
		List queryList = page.list();
		for (Object o : queryList) {
			VirtualDefinitionInfo d = (VirtualDefinitionInfo) o;
			ProcessDefinition pd = ds.getDefinitionById(d.getProcessDefinitionId());
			json += "{id:" + d.getId() + ",realDefinitionDesc:'"
					+ d.getRealDefinitionDesc() + "',virtualDefinitionDesc:'"
					+ d.getVirtualDefinitionDesc() + "',ruleFileName:'"
					+ d.getRuleFileName() + "',deptName:'"
					+ d.getDept().getDepartName() +
					"',virtualDefinitionName:'"+d.getVirtualDefinitionName()+
					"',version:'["+pd.getVersion()+"]'}";
			json += ",";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json += "],rowCount:"+total+"}";

		response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();  
		out.close();

		return null;

	}

	/**
	 * �õ�ĳ���������̵Ľڵ���Ϣ
	 * 
	 * @Methods Name getAllVirtualNodeInfo
	 * @Create In Mar 17, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getAllVirtualNodeInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "{data:[";
		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		VirtualDefinitionInfo vd = (VirtualDefinitionInfo) service.findUnique(
				VirtualDefinitionInfo.class, "id", Long
						.valueOf(virtualDefinitionInfoId));
		/*
		 * ��ȡ�������
		 */
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/10+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		Map requestParams = HttpUtil.requestParam2Map(request);
		requestParams.put("virtualDefinitionInfo", vd);
		Page page = updateWorkflowService.getVirtualNodeInfo(
				requestParams, pageNo, 10, orderBy, isAsc);
		Long total = page.getTotalCount();
		List queryList = page.list();
		for (Object o : queryList) {
			VirtualNodeInfo d = (VirtualNodeInfo) o;
			json += "{id:" + d.getId() +",nodeId:"+d.getNodeId()+ ",virtualNodeName:'"
					+ d.getVirtualNodeName() + "',virtualDefinitionInfo:'"
					+ d.getVirtualDefinitionInfo()+"',virtualDefinitionName:'"+d.getVirtualDefinitionInfo().getVirtualDefinitionDesc()
					+ "',virtualNodeDesc:'"+d.getVirtualNodeDesc()+"'}";
			json += ",";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json += "],rowCount:"+total+"}";

		response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;

	}

	public ActionForward getVirtualProcessDefinitionStore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		int start = this.getInt(request, "start", 0);		
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String realName = request.getParameter("description");
		realName = HttpUtil.ConverUnicode(realName);
		List<VirtualDefinitionInfo> searchProcessDefinition = new ArrayList<VirtualDefinitionInfo>();
		String json = "";
		int total = 0;
		try {
			List<VirtualDefinitionInfo> listDefination =service.findAll(VirtualDefinitionInfo.class);
			if("".equals(realName)){
				int length = 0;
				if(pageNo*pageSize>listDefination.size()){
					length = listDefination.size();
				}else{
					length = pageNo*pageSize;
				}
				
				for(int i=pageNo*pageSize-10;i<length;i++){
					json += "{id:" + listDefination.get(i).getId() + ",name:'" + listDefination.get(i).getRealDefinitionDesc()
					+ "',description:'" + listDefination.get(i).getVirtualDefinitionDesc() + "'}";
					json += ",";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				total = listDefination.size();
			}else{
				for(VirtualDefinitionInfo listProcessDefinition : listDefination){
					String description = listProcessDefinition.getVirtualDefinitionDesc();
					if(description.contains(realName)&&!"".equals(realName)){
						searchProcessDefinition.add(listProcessDefinition);
					}
				}
				int length = 0;
				if(pageNo*pageSize>searchProcessDefinition.size()){
					length = searchProcessDefinition.size();
				}else{
					length = pageNo*pageSize;
				}
				for(int i=pageNo*pageSize-10;i<length;i++){
					json += "{id:" + searchProcessDefinition.get(i).getId() + ",name:'" + searchProcessDefinition.get(i).getRealDefinitionDesc()
					+ "',description:'" + searchProcessDefinition.get(i).getVirtualDefinitionDesc() + "'}";
					json += ",";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				total = searchProcessDefinition.size();
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}
	
	/**
	 * �õ����е����̶���
	 * 
	 * @Methods Name getProcessStore
	 * @Create In Mar 10, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 *             
	 */
	public ActionForward getProcessDefinitionStore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int start = this.getInt(request, "start", 0);		
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String realName = request.getParameter("description");
		realName = HttpUtil.ConverUnicode(realName);
		List<ProcessDefinition> searchProcessDefinition = new ArrayList<ProcessDefinition>();
		String json = "";
		int total = 0;
		try {
			List<ProcessDefinition> listDefination =ds.getAllLatestProcess();
			if("".equals(realName)){
				int length = 0;
				if(pageNo*pageSize>listDefination.size()){
					length = listDefination.size();
				}else{
					length = pageNo*pageSize;
				}
				
				for(int i=pageNo*pageSize-10;i<length;i++){
					json += "{id:" + listDefination.get(i).getId() + ",name:'" + listDefination.get(i).getName()
					+ "',description:'" + listDefination.get(i).getDescription() + "'}";
					json += ",";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				total = listDefination.size();
			}else{
				for(ProcessDefinition listProcessDefinition : listDefination){
					String description = listProcessDefinition.getDescription();
					if(description.contains(realName)&&!"".equals(realName)){
						searchProcessDefinition.add(listProcessDefinition);
					}
				}
				int length = 0;
				if(pageNo*pageSize>searchProcessDefinition.size()){
					length = searchProcessDefinition.size();
				}else{
					length = pageNo*pageSize;
				}
				for(int i=pageNo*pageSize-10;i<length;i++){
					json += "{id:" + searchProcessDefinition.get(i).getId() + ",name:'" + searchProcessDefinition.get(i).getName()
					+ "',description:'" + searchProcessDefinition.get(i).getDescription() + "'}";
					json += ",";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
				total = searchProcessDefinition.size();
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}
	public int getInt(HttpServletRequest request, String param,
			int defaultValue) {
		String strValue = request.getParameter(param);
		if (strValue == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(strValue);
		}
	}

	/**
	 * �õ����е�DefinitionType
	 * 
	 * @Methods Name getAllDefinitionType
	 * @Create In Mar 5, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getAllDefinitionType(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "{data:[";

		try {
			List<Module> list = service.findAll(Module.class);
			for (Module a : list) {
				json += "{id:" + a.getId() + ",name:'" + a.getName() + "'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]}";

			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * ����������VirtualProcess��Ϣ
	 * (��ʱδ��)
	 */
	public ActionForward saveVirtualProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String virtualProcessDesc = request
					.getParameter("virtualProcessDesc");
			// �����������
			virtualProcessDesc = HttpUtil.ConverUnicode(virtualProcessDesc);
			String id = request.getParameter("id");

			// realProcessName = HttpUtil.ConverUnicode(realProcessName);
			ProcessDefinition pd = ds.getDefinitionById(Long.valueOf(id));
			String dept = request.getParameter("department");
			String type = request.getParameter("processType");
			Long departcode = Long.valueOf(dept
					.substring(dept.indexOf("=") + 1));
			Department de = (Department) service.findUnique(Department.class,
					"departCode", departcode);
			Module dt = (Module) service.findUnique(
					Module.class, "id", Long.valueOf(type));
			VirtualDefinitionInfo vd = new VirtualDefinitionInfo();
			vd.setProcessDefinitionId(pd.getId());
			vd.setDept(de);
			vd.setType(dt);
			vd.setRealDefinitionName(pd.getName());
			vd.setRealDefinitionDesc(pd.getDescription());
			vd.setVirtualDefinitionDesc(virtualProcessDesc);
			vd.setVirtualDefinitionName(pd.getName() + de.getDepartCode());
			service.save(vd);
			List<NodeInfo> list = ds.getTaskNodes(pd.getName());
			// ��ͳһ��ͬһ����ɫ
//			WorkflowRole wr = (WorkflowRole) service.findUnique(
//					WorkflowRole.class, "id", Long.valueOf("50"));
			for (NodeInfo tn : list) {
				VirtualNodeInfo vn = new VirtualNodeInfo();
				vn.setNodeId(tn.getId());
				vn.setVirtualNodeName(tn.getNodeName());
				vn.setVirtualDefinitionInfo(vd);
				service.save(vn);
			}
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:true}");
			writer.flush();
			writer.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			writer.close();
			return null;
		}

	}

	/**
	 * �����޸ĺ��VirtualDefinitionInfo��Ϣ
	 * 
	 * @Methods Name saveUpdateVirtualProcess
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward saveUpdateVirtualProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String virtualProcessDesc = request
					.getParameter("virtualProcessDesc");
			// �����������
			virtualProcessDesc = HttpUtil.ConverUnicode(virtualProcessDesc);
			String virtualDefinitionInfoId = request
					.getParameter("virtualDefinitionInfoId");

			VirtualDefinitionInfo vd = (VirtualDefinitionInfo) service
					.findUnique(VirtualDefinitionInfo.class, "id", Long
							.valueOf(virtualDefinitionInfoId));
			String dept = request.getParameter("department");
			String type = request.getParameter("processType");
			//Added by Kanglei email tpl  begin
			String content = request.getParameter("content");
			content = new String((content==null?"":content).getBytes("gbk"),"utf-8");
			vd.setEmailTemplate(Hibernate.createClob(content));
			//Added by Kanglei email tpl  end
			if (dept.matches("^[0-9]+$")) {// �����޸���
				Long departcode = Long.valueOf(dept
						.substring(dept.indexOf("=") + 1));
				Department de = (Department) service.findUnique(
						Department.class, "departCode", departcode);
				vd.setDept(de);
			}
			if (type.matches("^[0-9]+$")) {// �����޸��ˣ���֤���������Ƿ�����
				Module dt = (Module) service.findUnique(
						Module.class, "id", Long.valueOf(type));
				vd.setType(dt);
			}
			vd.setVirtualDefinitionDesc(virtualProcessDesc);
			//�������޸�ConfigUnitRole�е��������̵�����
			List<ConfigUnitRole> list=service.find(ConfigUnitRole.class, "processId", Long.valueOf(virtualDefinitionInfoId));
			for(ConfigUnitRole cu : list){
				cu.setDefinitionName(virtualProcessDesc);
				service.save(cu);
			}
			//add by awen for changge vd's processDefinition on 2010-06-09 begin
			vd.setProcessDefinitionId(ds.getLatestProcessDefinitionByName(vd.getRealDefinitionName()).getId());
			//add by awen for changge vd's processDefinition on 2010-06-09 end
			service.save(vd);

			//add by awen for merge nodeinofos on 2011-06-10 begin			
			List<NodeInfo> nodeList = ds.getAllNodes(vd.getRealDefinitionName());	//���°汾��ʵ���̵�����node�ڵ�		
			List<VirtualNodeInfo> vNodeList = updateWorkflowService.getVirtualNodeInfo(vd);//�޸�֮ǰ�������������̽ڵ�
			String have = "0";//0 update��1���ӣ�-1ɾ��
			for (NodeInfo ni : nodeList) {//ʵ�ʵ���
				//for each real PD's nodes,merge vNodes
				for(VirtualNodeInfo vni : vNodeList){
					if(vni.getVirtualNodeName().equals(ni.getNodeName())){//update
						vni.setNodeId(ni.getId());
						vni.setVirtualDefinitionInfo(vd);
						vni.setVirtualNodeDesc(ni.getDesc());
						have= "0" ;
						service.save(vni);
						break;
					}
					have= "1" ;//����
				}
				if(have.equals("1")){
					VirtualNodeInfo vn = new VirtualNodeInfo();
					vn.setNodeId(ni.getId());
					vn.setVirtualNodeName(ni.getNodeName());
					vn.setVirtualDefinitionInfo(vd);
					vn.setVirtualNodeDesc(ni.getDesc());
					service.save(vn);
				}
				
			}
			for(VirtualNodeInfo vni : vNodeList){				
				for (NodeInfo ni : nodeList){
					if(vni.getVirtualNodeName().equals(ni.getNodeName())){
						have= "0" ;
						break;
					}
					have = "-1";//ɾ��
				}
				if(have.equals("-1")){
					service.remove(vni);
				}
			}
			//�������������Ϣ,�������޴�		
			//add by awen for merge nodeinofos on 2011-06-10 end
			
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:true}");
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}

	/**
	 * �õ����̽�ɫ
	 * (��ʱδ��)
	 * @Methods Name getWorkFlowRole
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getWorkFlowRole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "{data:[";

		try {
			List<WorkflowRole> list = service.findAll(WorkflowRole.class);
			Iterator it = list.iterator();
			while (it.hasNext()) {
				WorkflowRole wf = (WorkflowRole) it.next();
				if (wf.getDeleteFlag() == 1)
					it.remove();
			}
			for (WorkflowRole a : list) {
				// json += "{id:1,name:' ϵͳ����Ա '},{id:2,name:'�ͻ�����'}";
				// json += ",";
				json += "{id:" + a.getId() + ",name:'" + a.getName() + "'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]}";

			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * �õ��������̵�����ڵ�
	 * (��ʱδ��)
	 * @Methods Name getVirtualNodeInfo
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getVirtualNodeInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		VirtualDefinitionInfo vd = (VirtualDefinitionInfo) service.findUnique(
				VirtualDefinitionInfo.class, "id", Long
						.valueOf(virtualDefinitionInfoId));

		try {
			String json = "";
			Class clazz = getClass(request.getParameter("clazz"));
			/*
			 * ��ȡ�������
			 */
			int pageNo = HttpUtil.getInt(request, "start", 1);
			String orderBy = HttpUtil.getString(request, "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
			Map requestParams = HttpUtil.requestParam2Map(request);
			requestParams.put("virtualDefinitionInfo", vd);
			// Map<Object, Object> queryParamValues =
			// metaDataManager.genQueryParams(
			// clazz, requestParams);
			Page page = metaDataManager.query(clazz, requestParams, pageNo,
					pageSize, orderBy, isAsc);
			List queryList = page.list();
			List<Map<String, Object>> listData = metaDataManager
					.getEntityMapDataForList(clazz, queryList);
			List<UserTableSetting> userVisibleColumns = metaDataManager
					.getUserColumnForList(clazz);
			json = CoderForList.encode(userVisibleColumns, listData, page
					.getTotalCount());
			json = json.replaceAll("[\\n|\\r]", "");// ɾ���ַ����еĻ����ַ�

			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * ����VirtualNodeInfo����Ϣ
	 * (��ʱδ��)
	 * @Methods Name saveVirtualNodeInfo
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward saveVirtualNodeInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String info = request.getParameter("info");
			// �����������
			info = HttpUtil.ConverUnicode(info);

			if (info != null && info.endsWith(",")) {
				info = info.substring(0, info.length() - 1);
			}

			JSONArray ja = JSONArray.fromObject("[" + info + "]");
			// ��ÿһ����¼���в���<<<<<<<<<<<<<<<<<<<<<<<<<<<<

			for (int i = 0; i < ja.size(); i++) {
				HashMap infoMap = new HashMap();
				JSONObject opl = (JSONObject) ja.get(i);
				Iterator set = opl.keys();
				while (set.hasNext()) {
					String key = (String) set.next();
					String value = opl.getString(key);
					infoMap.put(key, value);
					if (key.equals("role")) {
						// new String(value.getBytes("GBK"),"ISO8859-1");
						List<WorkflowRole> list = service
								.findAll(WorkflowRole.class);
						Iterator it = list.iterator();
						while (it.hasNext()) {
							WorkflowRole r = (WorkflowRole) it.next();
							if (value.equals(r.getName())) {
								infoMap.put("role", r);
							}
						}
					}

				}
				Object result = BeanUtil.getObject(infoMap, VirtualNodeInfo.class);
				service.save(result);
				//metaDataManager.saveEntityData(VirtualNodeInfo.class, infoMap);
			}
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:true}");
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}

	/**
	 * �õ����̶��������
	 * 
	 * @Methods Name getRealProcessDesc
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 
	 */
	public ActionForward getRealProcessDesc(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String processId = request.getParameter("processId");

		String json = null;

		try {
			if (processId != null && !"".equals(processId)) {
				ProcessDefinition p = ds.getDefinitionById(Long
						.valueOf(processId));
				String desc = p.getDescription();
				json = "{success:true,realProcessDesc:'" + desc +"',version:"+p.getVersion()+ "}";
			}
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * �õ�ĳ��VirtualDefinitionInfo��Ϣ�������޸�
	 * 
	 * @Methods Name getVirtualDefinitionInfo
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getVirtualDefinitionInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request.getParameter("id");

		String json = null;

		try {

			if (virtualDefinitionInfoId != null
					&& !"".equals(virtualDefinitionInfoId)) {
				VirtualDefinitionInfo vd = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class, "id", Long
								.valueOf(virtualDefinitionInfoId));
				if(vd!=null&&!"".equals(vd)){
					ProcessDefinition p = ds.getDefinitionById(vd.getProcessDefinitionId());
					if(p!=null&&!"".equals(p)){
						String virtualDefinitionDesc = vd.getVirtualDefinitionDesc();
						String realDefinitionDesc = vd.getRealDefinitionDesc();
						java.sql.Clob emailTpl = vd.getEmailTemplate();
						String emailTplStr = emailTpl==null?"":(emailTpl.getSubString(1, (int)emailTpl.length()));
						Module type = vd.getType();
						Department dept = vd.getDept();
						String ruleFileName = vd.getRuleFileName();
						json = "{success:true,virtualDefinitionDesc:'"
								+ virtualDefinitionDesc + "',realDefinitionDesc:'"
								+ realDefinitionDesc + "',typeName:'" + type.getName()
								+ "',deptId: '" + dept.getId()
								+ "',ruleFileName:'" + ruleFileName +"',version:"+p.getVersion()+",emailTplStr:'"+emailTplStr+"'}";
					}
				}
			}
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * ɾ��VirtualDefinitionInfo������ڵ�
	 * 
	 * @Methods Name deleteVirtualDefinitionInfo
	 * @Create In Mar 11, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward deleteVirtualDefinitionInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String removeIds = request.getParameter("removeIds");
		String json = "{success:true}";

		if ((!"".equals(removeIds)) && (removeIds != null)) {
			String[] ids = removeIds.split(",");
			for (String id : ids) {
				if (!id.equals("") && !id.equals("undefined")) {
					VirtualDefinitionInfo vd = (VirtualDefinitionInfo) service
							.findUnique(VirtualDefinitionInfo.class, "id", Long
									.valueOf(id));
					//ɾ���������̵���������ڵ�
					List<VirtualNodeInfo> list = service.find(
							VirtualNodeInfo.class, "virtualDefinitionInfo", vd);
					for (VirtualNodeInfo vn : list) {
						service.remove(VirtualNodeInfo.class, String.valueOf(vn
								.getId()));
					}
					//ɾ������ڵ��Ӧ�Ĺ������������Ϣ
					List<RuleConfigUnit> li=service.find(RuleConfigUnit.class, "processId", vd.getId());
					for(RuleConfigUnit rc : li){
						service.remove(RuleConfigUnit.class, String.valueOf(rc.getId()));
					}
					//ɾ������ڵ��Ӧ��ҳ��ģ�����������Ϣ
					List<PageModelConfigUnit> lii=service.find(PageModelConfigUnit.class, "processId", vd.getId());
					for(PageModelConfigUnit rc : lii){
						service.remove(PageModelConfigUnit.class, String.valueOf(rc.getId()));
					}
					//ɾ������ڵ��Ӧ�Ľ�ɫ�������������Ϣ
					List<ConfigUnitRole> li3=service.find(ConfigUnitRole.class, "processId", vd.getId());
					for(ConfigUnitRole rc : li3){
						List<ConfigUnitRoleTable> li4=service.find(ConfigUnitRoleTable.class, "configUnitRole", rc);
						for(ConfigUnitRoleTable cfr :li4){
							service.remove(ConfigUnitRoleTable.class, String.valueOf(cfr.getId()));	
						}
						service.remove(ConfigUnitRole.class, String.valueOf(rc.getId()));
					}
					service.remove(VirtualDefinitionInfo.class, id);
				}
			}
		}

		json = json.replaceAll("[\\n|\\r]", "");// ɾ���ַ����еĻ����ַ�

		response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}
}

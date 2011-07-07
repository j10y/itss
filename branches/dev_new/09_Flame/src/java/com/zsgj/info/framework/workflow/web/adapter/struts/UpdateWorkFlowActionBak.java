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
 * @author 杨涛 E-mail: yangtao@info.com
 * @version 创建时间：Mar 10, 2009 4:03:38 PM 类说明
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
	 * 得到所有的虚拟流程
	 * (暂时未用)
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
		 * 获取请求参数
		 */
		int pageNo = HttpUtil.getInt(request, "start", 1);
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String realProcessDesc = request.getParameter("realProcessDesc");
		// 解决乱码
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
		json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

		response.setCharacterEncoding(this.getProperties("system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;
	}
	/**
	 * 修改的时候留在原来的页面
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
		// 解决乱码
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
	 * 得到所有的虚拟流程（没有用框架）
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
		 * 获取请求参数
		 */
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/10+1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String realProcessDesc = request.getParameter("realProcessDesc");
		String virtualDefinitionDesc = request.getParameter("virtualDefinitionDesc");
		// 解决乱码
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
	 * 得到某个虚拟流程的节点信息
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
		 * 获取请求参数
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
	 * 得到所有的流程定义
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
	 * 得到所有的DefinitionType
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
	 * 保存新增的VirtualProcess信息
	 * (暂时未用)
	 */
	public ActionForward saveVirtualProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String virtualProcessDesc = request
					.getParameter("virtualProcessDesc");
			// 解决中文乱码
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
			// 先统一配同一个角色
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
	 * 保存修改后的VirtualDefinitionInfo信息
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
			// 解决中文乱码
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
			if (dept.matches("^[0-9]+$")) {// 部门修改了
				Long departcode = Long.valueOf(dept
						.substring(dept.indexOf("=") + 1));
				Department de = (Department) service.findUnique(
						Department.class, "departCode", departcode);
				vd.setDept(de);
			}
			if (type.matches("^[0-9]+$")) {// 类型修改了，验证传过来的是否数字
				Module dt = (Module) service.findUnique(
						Module.class, "id", Long.valueOf(type));
				vd.setType(dt);
			}
			vd.setVirtualDefinitionDesc(virtualProcessDesc);
			//级联的修改ConfigUnitRole中的虚拟流程的名称
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
			List<NodeInfo> nodeList = ds.getAllNodes(vd.getRealDefinitionName());	//最新版本真实流程的所有node节点		
			List<VirtualNodeInfo> vNodeList = updateWorkflowService.getVirtualNodeInfo(vd);//修改之前的所有虚拟流程节点
			String have = "0";//0 update，1增加，-1删除
			for (NodeInfo ni : nodeList) {//实际的来
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
					have= "1" ;//新增
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
					have = "-1";//删除
				}
				if(have.equals("-1")){
					service.remove(vni);
				}
			}
			//如果拷贝配置信息,工作量巨大		
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
	 * 得到流程角色
	 * (暂时未用)
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
				// json += "{id:1,name:' 系统管理员 '},{id:2,name:'客户经理'}";
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
	 * 得到虚拟流程的任务节点
	 * (暂时未用)
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
			 * 获取请求参数
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
			json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

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
	 * 保存VirtualNodeInfo的信息
	 * (暂时未用)
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
			// 解决中文乱码
			info = HttpUtil.ConverUnicode(info);

			if (info != null && info.endsWith(",")) {
				info = info.substring(0, info.length() - 1);
			}

			JSONArray ja = JSONArray.fromObject("[" + info + "]");
			// 对每一条记录进行操作<<<<<<<<<<<<<<<<<<<<<<<<<<<<

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
	 * 得到流程定义的描述
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
	 * 得到某个VirtualDefinitionInfo信息，进行修改
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
	 * 删除VirtualDefinitionInfo及任务节点
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
					//删除虚拟流程的所有虚拟节点
					List<VirtualNodeInfo> list = service.find(
							VirtualNodeInfo.class, "virtualDefinitionInfo", vd);
					for (VirtualNodeInfo vn : list) {
						service.remove(VirtualNodeInfo.class, String.valueOf(vn
								.getId()));
					}
					//删除虚拟节点对应的规则流程组件信息
					List<RuleConfigUnit> li=service.find(RuleConfigUnit.class, "processId", vd.getId());
					for(RuleConfigUnit rc : li){
						service.remove(RuleConfigUnit.class, String.valueOf(rc.getId()));
					}
					//删除虚拟节点对应的页面模型流程组件信息
					List<PageModelConfigUnit> lii=service.find(PageModelConfigUnit.class, "processId", vd.getId());
					for(PageModelConfigUnit rc : lii){
						service.remove(PageModelConfigUnit.class, String.valueOf(rc.getId()));
					}
					//删除虚拟节点对应的角色配置流程组件信息
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

		json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

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
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}
}

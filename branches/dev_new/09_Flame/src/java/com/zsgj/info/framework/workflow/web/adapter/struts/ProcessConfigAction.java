package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.File;
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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.jbpm.graph.def.ProcessDefinition;

import com.zsgj.info.appframework.extjs.servlet.CoderForList;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.UpdateWorkflowService;
import com.zsgj.info.framework.workflow.entity.ActionConfigUnit;
import com.zsgj.info.framework.workflow.entity.ConfigModel;
import com.zsgj.info.framework.workflow.entity.ConfigUnit;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.DefinitionInfo;
import com.zsgj.info.framework.workflow.entity.DefinitionType;
import com.zsgj.info.framework.workflow.entity.PageModelConfigUnit;
import com.zsgj.info.framework.workflow.entity.RuleConfigUnit;
import com.zsgj.info.framework.workflow.entity.SubProcessConfigUnit;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;
import com.zsgj.info.framework.workflow.info.NodeInfo;

/**
 * 流程配置里面涉及到的action
 * 
 * @Class Name ProcessConfigAction
 * @author Yang Tao
 * @Create In Feb 11, 2009 TODO
 */
public class ProcessConfigAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService) ContextHolder
			.getBean("definitionService");

	private Service service = (Service) ContextHolder.getBean("baseService");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder
			.getBean("metaDataManager");
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private UpdateWorkflowService updateWorkflowService = (UpdateWorkflowService) ContextHolder
			.getBean("updateWorkflowService");
	private ConfigUnitService cs = (ConfigUnitService) ContextHolder
			.getBean("configUnitService");

	/**
	 * 得到所有最新版本的流程 TODO Feb 16, 2009 By Administrator (暂时未用)
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward getProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String processName = request.getParameter("processName");
		String description = request.getParameter("description");

		java.util.List<DefinitionInfo> list = null;
		String json = "{data:[";
		if (processName == null || processName.equals("")
				&& description == null || description.equals("")) {
			list = (List<DefinitionInfo>) ds.getLatestDefinitions();

		} else {
			list = ds.searchDefinition(processName, description);
		}

		if (list != null) {

			for (DefinitionInfo d : list) {
				if (d.getDept() == null) {
					Department n = d.getDept();
					json += "{id:" + d.getProcessDefinitionId() + ",name:'"
							+ d.getName() + "',description:'"
							+ d.getDescription() + "',version:"
							+ d.getVersion() + ",deptName:" + n + "}";
					json += ",";
				} else {
					Department n = d.getDept();
					json += "{id:" + d.getProcessDefinitionId() + ",name:'"
							+ d.getName() + "',description:'"
							+ d.getDescription() + "',version:"
							+ d.getVersion() + ",deptName:'"
							+ n.getDepartName() + "'}";
					json += ",";
				}

			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
		}
		json += "]}";
		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 得到所有最新版本的流程(从DefinitionInfo中取得)
	 * 
	 * @Methods Name getDefinitionInfos
	 * @Create In Mar 5, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getDefinitionInfos(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "";
		Class clazz = getClass(request.getParameter("clazz"));
		/*
		 * 获取请求参数
		 */
		int pageNo = HttpUtil.getInt(request, "start", 1);
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		Map<String, String> requestParams = HttpUtil.requestParam2Map(request);
		Map<Object, Object> queryParamValues = metaDataManager.genQueryParams(
				clazz, requestParams);
		Page page = metaDataManager.query(clazz, queryParamValues, null,
				pageNo, pageSize, orderBy, isAsc);
		List queryList = page.list();
		List<Map<String, Object>> listData = metaDataManager
				.getEntityMapDataForList(clazz, queryList);
		List<UserTableSetting> userVisibleColumns = metaDataManager
				.getUserColumnForList(clazz);
		json = CoderForList.encode(userVisibleColumns, listData, page
				.getTotalCount());
		json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;

	}

	/**
	 * 列出一个流程的所有节点 TODO Feb 16, 2009 By Administrator
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward listAllNode(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String processName = request.getParameter("processName");
		java.util.List<NodeInfo> list = ds.getAllNodes(processName);
		String json = "{data:[";
		if (list != null) {
			for (NodeInfo d : list) {
				json += "{id:" + d.getId() + ",nodeName:'" + d.getNodeName()
						+ "',definitionName:'" + d.getDefinitionName()
						+ "',desc:'" + d.getDesc() + "'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
		}
		json += "]}";
		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 发布流程时，为某个流程填写一个部门信息 TODO Feb 16, 2009 By Administrator
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward writeDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String processName = request.getParameter("processName");
		String dept = request.getParameter("dept");
		String json = "{success:true}";
		java.util.List<DefinitionInfo> list = ds.getLatestDefinitions();

		Long departcode = Long.valueOf(dept.substring(dept.indexOf("=") + 1));
		Department de = (Department) service.findUnique(Department.class,
				"departCode", departcode);
		// //根据id找到流程
		// DefinitionInfo d=new
		// DefinitionInfo(ds.getDefinitionById(Long.valueOf(id)));
		// d.setDept(de);
		// service.save(d);

		for (DefinitionInfo d : list) {
			if (d.getName().equals(processName)) {
				d.setDept(de);
				service.save(d);
			}
		}
		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();

		return null;
	}

	/**
	 * 根据节点类型得到所有的配置单元 TODO Feb 17, 2009 By Administrator
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward getConfigUnit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		String nodeId = request.getParameter("nodeId");
		String nodeType = null;
		String json = "[";
		// 通过节点名字nodeName判断节点类型NodeType，根据节点类型NodeType找到对应的配置单元ConfigUnit，根据配置单元ConfigUnit找到对应的url与name
		try {
//			ProcessDefinition p = null;
			if (virtualDefinitionInfoId != null
					&& !"".equals(virtualDefinitionInfoId)) {
				VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class, "id", Long
								.valueOf(virtualDefinitionInfoId));
				// p =
				// ds.getDefinitionById(Long.valueOf(v.getProcessDefinitionId()));
				// List<NodeInfo> li = ds.getAllNodes(p.getName());
				// for (NodeInfo n : li) {
				// if (Long.valueOf(nodeId).equals(n.getId())) {
				// nodeType = n.getType();
				// }
				// }

				nodeType = ds.getNodeByNodeId(nodeId, v
						.getProcessDefinitionId());
				List<ConfigModel> list2 = service.findAll(ConfigModel.class);
				List<ConfigUnit> list3 = new ArrayList<ConfigUnit>();
				for (ConfigModel c : list2) {
					if (nodeType.indexOf(c.getNodeType()) == 0) {
						list3.add(c.getConfigUnit());
					}
				}

				for (ConfigUnit co : list3) {
					json += "{id:" + co.getId() + ",name:'" + co.getName()
							+ "',url:'" + co.getUrl() + "'}";
					json += ",";
				}
			}

			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]";
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 根据节点类型得到action配置单元的信息 TODO Feb 17, 2009 By Administrator
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward getActionConfigUnit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		String nodeId = request.getParameter("nodeId");

		String json = "{data:[";

		try {
			if (virtualDefinitionInfoId != null
					&& !"".equals(virtualDefinitionInfoId)) {
				VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class, "id", Long
								.valueOf(virtualDefinitionInfoId));
				ProcessDefinition p = ds.getDefinitionById(Long.valueOf(v
						.getProcessDefinitionId()));
				String processName = p.getName();
				List<NodeInfo> list = ds.getAllNodes(processName);
				String nodeName = null;
				for (NodeInfo n : list) {
					if (Long.valueOf(nodeId).equals(n.getId())) {
						nodeName = n.getNodeName();
					}
				}

				List<ActionConfigUnit> queryList = service
						.findAll(ActionConfigUnit.class);
				Iterator it = queryList.iterator();
				while (it.hasNext()) {
					ActionConfigUnit a = (ActionConfigUnit) it.next();
					if (!processName.equals(a.getProcessName())
							|| !nodeName.equals(a.getNodeName())) {
						it.remove();
					}
				}
				for (ActionConfigUnit a : queryList) {
					json += "{id:" + a.getId() + ",eventName:'"
							+ a.getEventName() + "',actionName:'"
							+ a.getActionName() + "'}";
					json += ",";
				}
				if (json.endsWith(",")) {
					json = json.substring(0, json.length() - 1);
				}
			}
			json += "]}";

			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 删除那些在数据库中有的，在界面中删除的ActionConfigUnit对象
	 * 
	 */
	public ActionForward removeActionConfigUnitRecord(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String removeIds = request.getParameter("removeIds");
		String json = "{success:true}";

		if ((!"".equals(removeIds)) && (removeIds != null)) {
			String[] ids = removeIds.split(",");
			for (String id : ids) {
				if (!id.equals("") && !id.equals("undefined")) {
					service.remove(ActionConfigUnit.class, id);
				}
			}
		}

		json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 保存新增的ActionConfigUnit信息
	 * 
	 */
	public ActionForward saveActionConfigUnitRecord(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String info = request.getParameter("info");
			// 解决中文乱码
			info = HttpUtil.ConverUnicode(info);
			String virtualDefinitionInfoId = request.getParameter("processId");
			String nodeId = request.getParameter("nodeId");
			VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
					.findUnique(VirtualDefinitionInfo.class, "id", Long
							.valueOf(virtualDefinitionInfoId));
			ProcessDefinition p = ds.getDefinitionById(Long.valueOf(v
					.getProcessDefinitionId()));
			String processName = p.getName();
			String nodeName = null;
			String nodeDesc = null;
			List<NodeInfo> list = ds.getAllNodes(processName);
			for (NodeInfo n : list) {
				if (Long.valueOf(nodeId).equals(n.getId())) {
					nodeName = n.getNodeName();
					nodeDesc = n.getDesc();
				}
			}

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
					infoMap.put("processName", processName);
					infoMap.put("nodeDesc", nodeDesc);
					infoMap.put("nodeName", nodeName);
				}
				metaDataManager.saveEntityData(ActionConfigUnit.class, infoMap);
			}
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:true}");
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}

	/**
	 * 根据节点类型得到pageModel配置单元的信息 TODO Feb 17, 2009 By Administrator
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward getPageModelConfigUnit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		String processId = request.getParameter("processId");
//		String nodeId = request.getParameter("nodeId");
		try {
			String json = "{data:[";
			/*
			 * 获取请求参数
			 */
			int start = HttpUtil.getInt(request, "start", 0);
			int pageNo = start/10+1;
			String orderBy = HttpUtil.getString(request, "orderBy", "id");
			boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
			Map requestParams = HttpUtil.requestParam2Map(request);
			Page page = updateWorkflowService.getPageModelConfigUnit(
					requestParams, pageNo, 10, orderBy, isAsc);
			Long total = page.getTotalCount();
			List queryList = page.list();
			for (Object o : queryList) {
				PageModelConfigUnit d = (PageModelConfigUnit) o;
				VirtualDefinitionInfo vd = (VirtualDefinitionInfo) service
						.find(VirtualDefinitionInfo.class, d.getProcessId()
								+ "");
				json += "{id:" + d.getId() + ",nodeId:" + d.getNodeId()
						+ ",virtualDesc:'" + vd.getVirtualDefinitionDesc()
						+ "',nodeName:'" + d.getNodeName() + "',roleName:'"
						+ d.getRoleName() + "',pageModelName:'"
						+ d.getPageModelName() + "'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "],rowCount:" + total + "}";

			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 删除那些在数据库中有的，在界面中删除的PageModelConfigUnit对象
	 * 
	 */
	
	public ActionForward removePageModelConfigUnitRecord(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String removeIds = request.getParameter("removeIds");
		String json = "{success:true}";

		if ((!"".equals(removeIds)) && (removeIds != null)) {
			String[] ids = removeIds.split(",");
			for (String id : ids) {
				if (!id.equals("") && !id.equals("undefined")) {
					service.remove(PageModelConfigUnit.class, id);
				}
			}
		}

		json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 保存新增的PageModelConfigUnit信息
	 * 
	 */
	public ActionForward savePageModelConfigUnitRecord(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String info = request.getParameter("info");
			// 解决中文乱码
			info = HttpUtil.ConverUnicode(info);
			String virtualDefinitionInfoId = request.getParameter("processId");
			String nodeId = request.getParameter("nodeId");
			VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
					.findUnique(VirtualDefinitionInfo.class, "id", Long
							.valueOf(virtualDefinitionInfoId));
			ProcessDefinition p = ds.getDefinitionById(v
					.getProcessDefinitionId());
			String processName = p.getName();
			String nodeName = null;
			String nodeDesc = null;
			List<VirtualNodeInfo> li = service.find(VirtualNodeInfo.class,
					"virtualDefinitionInfo", v);
			for (VirtualNodeInfo vn : li) {
				if (Long.valueOf(nodeId).equals(vn.getNodeId())) {
					nodeName = vn.getVirtualNodeName();
					nodeDesc = vn.getVirtualNodeDesc();
				}
			}
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
					if (key.equals("roleName")) {
						// new String(value.getBytes("GBK"),"ISO8859-1");
						List<Role> list = service.findAll(Role.class);
						Iterator it = list.iterator();
						while (it.hasNext()) {
							Role r = (Role) it.next();
							if (value.equals(r.getName())) {
								infoMap.put("roleId", r.getId());
							}
						}

					}
					if (key.equals("pageModelName")) {
						List<PageModel> list = service.findAll(PageModel.class);
						Iterator it = list.iterator();
						while (it.hasNext()) {
							PageModel r = (PageModel) it.next();
							String value1=value.substring(value.indexOf("/")+1, value.length());
							if (value1.equals(r.getName())) {
								infoMap.put("pageModelId", r.getId());
							}
						}

					}
					infoMap.put(key, value);
					infoMap.put("processName", processName);
					infoMap.put("nodeDesc", nodeDesc);
					infoMap.put("nodeName", nodeName);
					infoMap.put("nodeId", Long.valueOf(nodeId));
					infoMap.put("processId", Long
							.valueOf(virtualDefinitionInfoId));
				}
				if (infoMap.containsKey("id")) {
					PageModelConfigUnit rmc = (PageModelConfigUnit) service
							.find(PageModelConfigUnit.class, (String) infoMap
									.get("id"));
					rmc.setRoleId((Long) infoMap.get("roleId"));
					rmc.setRoleName((String) infoMap.get("roleName"));
					rmc.setPageModelId((Long) infoMap.get("pageModelId"));
					rmc.setPageModelName((String) infoMap.get("pageModelName"));
					service.save(rmc);
				} else {
					PageModelConfigUnit rmc = new PageModelConfigUnit();
					rmc.setProcessName((String) infoMap.get("processName"));
					rmc.setProcessId((Long) infoMap.get("processId"));
					rmc.setNodeId((Long) infoMap.get("nodeId"));
					rmc.setNodeName((String) infoMap.get("nodeName"));
					rmc.setNodeDesc((String) infoMap.get("nodeDesc"));
					rmc.setRoleId((Long) infoMap.get("roleId"));
					rmc.setRoleName((String) infoMap.get("roleName"));
					rmc.setPageModelId((Long) infoMap.get("pageModelId"));
					rmc.setPageModelName((String) infoMap.get("pageModelName"));
					service.save(rmc);
				}
				// metaDataManager.saveEntityData(PageModelConfigUnit.class,
				// infoMap);
			}
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:true}");
			writer.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}

	}

	/**
	 * 根据节点类型得到pageModel配置单元中的角色的信息 TODO Feb 17, 2009 By Administrator
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             TODO
	 */
	public ActionForward getSystemRole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request.getParameter("processId");
		String nodeId = request.getParameter("nodeId");
		String json = "{data:[";
		try {
//			VirtualDefinitionInfo v = (VirtualDefinitionInfo) service.findUnique(VirtualDefinitionInfo.class, "id", Long
//							.valueOf(virtualDefinitionInfoId));
//			ProcessDefinition p = ds.getDefinitionById(v.getProcessDefinitionId());
//			String processName = p.getName();
//			String nodeName = null;
//			String nodeDesc = null;
//			List<VirtualNodeInfo> li = service.find(VirtualNodeInfo.class,"virtualDefinitionInfo", v);
//			for (VirtualNodeInfo vn : li) {
//				if (Long.valueOf(nodeId).equals(vn.getNodeId())) {
//					nodeName = vn.getVirtualNodeName();
//					nodeDesc = vn.getVirtualNodeDesc();
//				}
//			}
			//List<DefinitionInfo> list = ds.getLatestDefinitions();
			ConfigUnitRole configUnitRole = cs.findConfigUnitRole(virtualDefinitionInfoId, nodeId);
			if(configUnitRole!=null&&!"".equals(configUnitRole)){
				List<ConfigUnitRoleTable> list3 = service.find(ConfigUnitRoleTable.class, "configUnitRole", configUnitRole);
				if(list3.size()!=0){
					for (ConfigUnitRoleTable a : list3) {
						json += "{id:" + a.getRole().getId() + ",name:'"
								+ a.getRole().getName() + "'}";
						json += ",";
					}
					if (json.endsWith(",")) {
						json = json.substring(0, json.length() - 1);
					}
					json += "]}";
				}
			}
			response.setCharacterEncoding(this.getProperties("system.characterEncoding", "utf-8"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "utf-8"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 得到所有的pageModel
	 * 
	 * @Methods Name getAllPageModel
	 * @Create In Feb 26, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getAllPageModel(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		String processId = request.getParameter("processId");
//		String nodeId = request.getParameter("nodeId");

		String 	json = "";	
		int start = this.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String pageModelName = request.getParameter("name");
		// 把查询结果做分页
		Map requestParams = new HashMap();
		requestParams.put("pageModelName", pageModelName);
		Page page = cs.findPageModelByParams(requestParams, pageNo, pageSize);
		Long total = page.getTotalCount();
		List<PageModel> pageModel = page.list();

		if (pageModel.size() == 0) {

			json = "{success: true, rowCount:" + total + ",data:[" + json
					+ "]}";
			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				response.getWriter().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			for (PageModel model : pageModel) {

//				String dataStr = "";
				json += "{id:" + model.getId() + ",name:'" + model.getTitle() + "/" + model.getName()
						+ "'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";

			try {
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(json);
				response.getWriter().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
		/** **************************************************************************************** */
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
			List<DefinitionType> list = service.findAll(DefinitionType.class);
			for (DefinitionType a : list) {
				json += "{id:" + a.getId() + ",name:'" + a.getName() + "'}";
				json += ",";
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]}";

			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 把流程的部门名称，分类类型和一个规则文件保存起来 (暂时未用)
	 * 
	 * @Methods Name saveRuleFile
	 * @Create In Mar 4, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward saveProcessInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String processId = request.getParameter("processId");
		String ruleName = request.getParameter("ruleName");
//		String processType = request.getParameter("processType");
		String dept = request.getParameter("department");
		Long departcode = Long.valueOf(dept.substring(dept.indexOf("=") + 1));
		Department de = (Department) service.findUnique(Department.class,
				"departCode", departcode);

		String json = null;

		try {
			DefinitionInfo definitionInfo = null;
			if (processId != null && !processId.equals("")) {
				definitionInfo = (DefinitionInfo) service.findUnique(
						DefinitionInfo.class, "processDefinitionId", Long
								.valueOf(processId));
			}

			if (definitionInfo != null) {
				if (ruleName != null) {
					definitionInfo.setRuleName(ruleName);
				}
				if (de != null) {
					definitionInfo.setDept(de);
				}

				// definitionInfo.setType(processType);
				service.save(definitionInfo);
				json = "{success :true}";
			} else {
				json = "{success:false}";
			}
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 根据流程id和节点id得到节点名称(中文）
	 * 
	 * @Methods Name getNodeName
	 * @Create In Mar 6, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getNodeName(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		String nodeId = request.getParameter("nodeId");

		String json = null;
		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		try {
			if (virtualDefinitionInfoId != null
					&& !"".equals(virtualDefinitionInfoId)) {
				VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class, "id", Long
								.valueOf(virtualDefinitionInfoId));
//				ProcessDefinition p = ds.getDefinitionById(v
//						.getProcessDefinitionId());
//				String processName = p.getName();
				String nodeName = null;
//				String nodeDesc = null;
				List<VirtualNodeInfo> li = service.find(VirtualNodeInfo.class,
						"virtualDefinitionInfo", v);
				for (VirtualNodeInfo vn : li) {
					if (Long.valueOf(nodeId).equals(vn.getNodeId())) {
						nodeName = vn.getVirtualNodeName();
//						nodeDesc = vn.getVirtualNodeDesc();
						break;
					}
				}
				json = "{success:true,nodeName:'" + nodeName + "'}";
			}
			
			out.println(json);
			out.flush();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			
			out.write("{success:false}");
			out.flush();
			return null;
		}finally{
			if (out != null)
				out.close();
		}
	}

	/**
	 * 得到虚拟流程的名称
	 * 
	 * @Methods Name getVitualDefintionName
	 * @Create In Mar 16, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getVitualDefintionName(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
//		String nodeId = request.getParameter("nodeId");

		String json = null;

		try {
			if (virtualDefinitionInfoId != null
					&& !"".equals(virtualDefinitionInfoId)) {
				VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class, "id", Long
								.valueOf(virtualDefinitionInfoId));
				String virtualDefinitionDesc = v.getVirtualDefinitionDesc();
				json = "{success:true,virtualDefinitionDesc:'"
						+ virtualDefinitionDesc + "'}";
			}
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 保存增加的虚拟流程信息 上传规则文件 把流程的部门名称，分类类型,规则文件路径保存起来了
	 * 
	 * @Methods Name uploadFile
	 * @Create In Mar 5, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * ActionForward
	 */
	@SuppressWarnings("deprecation")
	public ActionForward uploadRuleFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String result = null;
		String id = request.getParameter("id");
		ProcessDefinition pd = ds.getDefinitionById(Long.valueOf(id));
		String dept = request.getParameter("department");
		String type = request.getParameter("processType");
		String content = request.getParameter("content");
		Long departcode = Long.valueOf(dept.substring(dept.indexOf("=") + 1));
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
		// vd.setVirtualDefinitionDesc(virtualProcessDesc);
		vd.setVirtualDefinitionName(pd.getName() + System.currentTimeMillis());
		String virtualProcessDesc = null;
		String rulePath = null;

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		request.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		String systemRulePath = PropertiesUtil.getProperties("workflow.rule.package","\\WEB-INF\\classes\\com\\zsgj\\itil\\rules");
		try {
			while (iter.hasNext()) {
				// 把实体换成map，然后保存
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField() && item.getName() != null
						&& !"".equals(item.getName())) {
					String fileName = item.getName();
//					String appendix = "";

					// 考虑到out文件可能不以.out为后缀
//					int indexOfDot = fileName.lastIndexOf(".");
//					if (indexOfDot >= 0) {
//						appendix = fileName.substring(indexOfDot);
//					}
					String ruleName = fileName.substring(fileName
							.lastIndexOf("\\") + 1);
					// String systemFileName = "upload-"
					// + System.currentTimeMillis() + appendix;
//					request.getRealPath("");
					String filePath = FSP + systemRulePath + FSP + ruleName;//"\\WEB-INF\\classes\\com\\digitalchina\\itil\\rules"
					String realPath = request.getRealPath(FSP) + filePath;

					File uploadedFile = new File(realPath);
					item.write(uploadedFile);
					ruleName = PropertiesUtil.getProperties("workflow.rult.filePackage", "/com/zsgj/itil/rules/") + ruleName;
					rulePath = ruleName;
					int p1 = fileName.lastIndexOf("\\");
					int p2 = fileName.lastIndexOf("/");
					if (p1 >= p2 && p1 >= 0) {
						fileName = fileName.substring(p1 + 1);
					}
					if (p2 >= p1 && p2 >= 0) {
						fileName = fileName.substring(p2 + 1);
					}

				} else if (item.isFormField()) {
					String name = item.getFieldName();
					if ("virtualProcessDesc".equals(name)) {
						String value = item.getString();
						virtualProcessDesc = HttpUtil.ConverUnicode(value);
					}
					if("content".equals(name)){
						content = item.getString();
						vd.setEmailTemplate(Hibernate.createClob(content));
					}
				}
			}
			vd.setRuleFileName(rulePath);
			vd.setVirtualDefinitionDesc(virtualProcessDesc);
			service.save(vd);
			List<NodeInfo> list = ds.getAllNodes(pd.getName());

			for (NodeInfo tn : list) {
				VirtualNodeInfo vn = new VirtualNodeInfo();
				vn.setNodeId(tn.getId());
				vn.setVirtualNodeName(tn.getNodeName());
				vn.setVirtualDefinitionInfo(vd);
				vn.setVirtualNodeDesc(tn.getDesc());
				service.save(vn);
			}

			List<NodeInfo> li = ds.getAllNodes(pd.getName());

			Iterator it = li.iterator();
			while (it.hasNext()) {
				NodeInfo node = (NodeInfo) it.next();
				RuleConfigUnit rc = new RuleConfigUnit();
				rc.setNodeId(node.getId());
				rc.setNodeName(node.getNodeName());
				rc.setProcessId(vd.getId());
				rc.setProcessName(pd.getName());
				rc.setVersion(pd.getVersion());
				if (rulePath != null) {
					rc.setRuleFileName(rulePath.substring(rulePath
							.lastIndexOf("/") + 1));
				}
				service.save(rc);
			}
			result = "{success:true,message:'上传成功'}";
		} catch (RuntimeException e) {
			result = "{success:flase,message:'上传失败'}";
			throw e;
		}
		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}

	/**
	 * 得到RuleConfigUnit的信息 (暂时未用)
	 * 
	 * @Methods Name getRuleConfigUnit
	 * @Create In Mar 9, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getRuleConfigUnit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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

			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 保存ruleConfigUnit
	 * 
	 * @Methods Name saveRuleConfigUnit
	 * @Create In Mar 9, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward saveRuleConfigUnit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String formParam = request.getParameter("info");
			// 解决中文乱码
			formParam = HttpUtil.ConverUnicode(formParam);

			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(formParam);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String value = jo.getString(key);
				infoMap.put(key, value);
			}
			RuleConfigUnit rc = (RuleConfigUnit) service.find(
					RuleConfigUnit.class, (String) infoMap.get("id"));
			rc.setRuleName((String) infoMap.get("ruleName"));
			service.save(rc);
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "utf-8"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println("{success:true}");
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 删除那些在数据库中有的，在界面中删除的RuleConfigUnit对象 (暂时未用)
	 */
	public ActionForward removeRuleConfigUnitRecord(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String removeIds = request.getParameter("removeIds");
		String json = "{success:true}";

		if ((!"".equals(removeIds)) && (removeIds != null)) {
			String[] ids = removeIds.split(",");
			for (String id : ids) {
				if (!id.equals("") && !id.equals("undefined")) {
					service.remove(RuleConfigUnit.class, id);
				}
			}
		}

		json = json.replaceAll("[\\n|\\r]", "");// 删除字符串中的回行字符

		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 得到ruleConfigUnit的信息
	 * 
	 * @Methods Name getRuleInfo
	 * @Create In Mar 10, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getRuleInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String json = "";
		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		String nodeId = request.getParameter("nodeId");

		String id = null;
		if (virtualDefinitionInfoId != null
				&& !"".equals(virtualDefinitionInfoId)) {
//			VirtualDefinitionInfo v = (VirtualDefinitionInfo) service
//					.findUnique(VirtualDefinitionInfo.class, "id", Long
//							.valueOf(virtualDefinitionInfoId));
//			ProcessDefinition p = ds.getDefinitionById(v
//					.getProcessDefinitionId());
//			int version = p.getVersion();
			List<RuleConfigUnit> list = service.findAll(RuleConfigUnit.class);
			for (RuleConfigUnit rc : list) {
				if (rc.getNodeId() != null
						&& rc.getProcessId() != null
						&& rc.getNodeId().equals(Long.valueOf(nodeId))
						&& rc.getProcessId().equals(
								Long.valueOf(virtualDefinitionInfoId))) {
					id = String.valueOf(rc.getId());
				}
			}
		}
		RuleConfigUnit r = (RuleConfigUnit) service.findUnique(
				RuleConfigUnit.class, "id", Long.valueOf(id));
		json += "{success:true,id:" + r.getId() + ",ruleName:'"
				+ r.getRuleName() + "',ruleFileName:'" + r.getRuleFileName()
				+ "'}";

		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 得到SubProcessConfigUnit的信息
	 * 
	 * @Methods Name getSubProcessInfo
	 * @Create In Apr 13, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getSubProcessInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String json = "";
		// 主流的虚拟流程的id，及节点的id
		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		String nodeId = request.getParameter("nodeId");

		String id = null;
		if (virtualDefinitionInfoId != null
				&& !"".equals(virtualDefinitionInfoId)) {
			List<SubProcessConfigUnit> list = service.find(
					SubProcessConfigUnit.class, "superProcessId", Long
							.valueOf(virtualDefinitionInfoId));
			for (SubProcessConfigUnit sub : list) {
				if (sub.getNodeId() != null
						&& sub.getNodeId().equals(Long.valueOf(nodeId))) {
					id = String.valueOf(sub.getId());
				}
			}
		}
		if (id != null && !"".equals(id)) {
			SubProcessConfigUnit r = (SubProcessConfigUnit) service.findUnique(
					SubProcessConfigUnit.class, "id", Long.valueOf(id));
			VirtualDefinitionInfo subProcess = (VirtualDefinitionInfo) service
					.findUnique(VirtualDefinitionInfo.class, "id", r
							.getSubProcessId());
			json += "{success:true,id:" + r.getId() + ",subProcessName:'"
					+ subProcess.getVirtualDefinitionDesc() + "',applyType:'"
					+ r.getApplyType() + "',applyTypeName:'"
					+ r.getApplyTypeName() + "',param:'" + r.getParam() + "'}";
		} else {
			json += "{success:true,id:'',subProcessName:''}";
		}

		response.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 保存子流程的配置信息
	 * 
	 * @Methods Name saveSubProcessConfigUnit
	 * @Create In Apr 13, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward saveSubProcessConfigUnit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			// 主流的虚拟流程的id，及节点的id
			String virtualDefinitionInfoId = request.getParameter("processId");
			String nodeId = request.getParameter("nodeId");
			// 子流程的名字
			String formParam = request.getParameter("info");
			// 解决中文乱码
			formParam = HttpUtil.ConverUnicode(formParam);

			HashMap infoMap = new HashMap();
			JSONObject jo = JSONObject.fromObject(formParam);
			Iterator itInfo = jo.keys();
			while (itInfo.hasNext()) {
				String key = (String) itInfo.next();
				String value = jo.getString(key);
				infoMap.put(key, value);
			}
			if ((String) infoMap.get("id") != null
					&& !"".equals((String) infoMap.get("id"))) {
				SubProcessConfigUnit sub = (SubProcessConfigUnit) service.find(
						SubProcessConfigUnit.class, (String) infoMap.get("id"));
				VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class,
								"virtualDefinitionDesc", (String) infoMap
										.get("subProcessName"));
				if (virtualDefinitionInfo != null) {
					sub.setSubProcessId(virtualDefinitionInfo.getId());
				} else {// 子流程名称错误，没有找到子流程
					response.setCharacterEncoding(this.getProperties(
							"system.characterEncoding", "gbk"));
					PrintWriter writer = response.getWriter();
					writer.write("{success:false}");
					writer.flush();
					return null;
				}
				sub.setApplyTypeName((String) infoMap.get("applyTypeName"));
				String applyType = "";
				if ("配置项审批类型".equals((String) infoMap.get("applyTypeName"))) {
					applyType = "cproject";
				} else {
					applyType = "nproject";
				}
				sub.setApplyType(applyType);
				String param = (String) infoMap.get("subProcessParam");
				if (param.contains("(")) {
					param = "";
				}
				sub.setParam(param);
				service.save(sub);
			} else {
				SubProcessConfigUnit sub = new SubProcessConfigUnit();
				sub.setSuperProcessId(Long.valueOf(virtualDefinitionInfoId));
				sub.setNodeId(Long.valueOf(nodeId));

				sub.setApplyTypeName((String) infoMap.get("applyTypeName"));
				String applyType = "";
				if ("配置项审批类型".equals((String) infoMap.get("applyTypeName"))) {
					applyType = "cproject";
				} else {
					applyType = "nproject";
				}
				sub.setApplyType(applyType);
				String param = (String) infoMap.get("subProcessParam");
				if (param.contains("(")) {
					param = "";
				}
				sub.setParam(param);
				VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service
						.findUnique(VirtualDefinitionInfo.class,
								"virtualDefinitionDesc", (String) infoMap
										.get("subProcessName"));
				if (virtualDefinitionInfo != null) {
					sub.setSubProcessId(virtualDefinitionInfo.getId());
				} else {// 子流程名称错误，没有找到子流程
					response.setCharacterEncoding(this.getProperties(
							"system.characterEncoding", "utf-8"));
					PrintWriter writer = response.getWriter();
					writer.write("{success:false}");
					writer.flush();
					return null;
				}
				ProcessDefinition pd = ds
						.getDefinitionById(virtualDefinitionInfo
								.getProcessDefinitionId());
				sub.setVersion(pd.getVersion());// 默认取最新版本的
				service.save(sub);
			}

			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println("{success:true}");
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException e) {
			e.printStackTrace();
			response.setCharacterEncoding(this.getProperties(
					"system.characterEncoding", "gbk"));
			PrintWriter writer = response.getWriter();
			writer.write("{success:false}");
			writer.flush();
			return null;
		}
	}

	/**
	 * 得到所有的虚拟流程(可查询）
	 * 
	 * @Methods Name getSubProcessStore
	 * @Create In Apr 13, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getSubProcessStore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "";
		String virtualDefinitionInfoId = request
				.getParameter("virtualDefinitionInfoId");
		int start = this.getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		int pageSize = 10;
		String subProcessName = request.getParameter("subProcessName");
		// 把查询结果做分页
		subProcessName = HttpUtil.ConverUnicode(subProcessName);
		Map requestParams = new HashMap();
		requestParams.put("virtualDefinitionInfoId", virtualDefinitionInfoId);
		requestParams.put("subProcessName", subProcessName);
		Page page = cs.findVirtualDefinitionInfos(requestParams, pageNo,
				pageSize);
		Long total = page.getTotalCount();
		List<VirtualDefinitionInfo> userInfo = page.list();
		for (VirtualDefinitionInfo vd : userInfo) {
			json += "{id:" + vd.getId() + ",subProcessName:'"
					+ vd.getVirtualDefinitionDesc() + "'}";
			json += ",";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";

		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到applyType
	 * 
	 * @Methods Name getApplyTypeStore
	 * @Create In Apr 20, 2009 By Administrator
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward getApplyTypeStore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String json = "{data:[";
		json += "{applyType:'cproject',applyTypeName:'配置项审批类型'}, {applyType:'nproject',applyTypeName:'公告管理审批类型'},{applyType:'rproject',applyTypeName:'服务目录审批'}";
		json += "]}";
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用于分页计算
	 * 
	 * @Methods Name getInt
	 * @Create In Feb 24, 2009 By guangsa
	 * @param request
	 * @param param
	 * @param defaultValue
	 * @return int
	 */
	public int getInt(HttpServletRequest request, String param, int defaultValue) {
		String strValue = request.getParameter(param);
		if (strValue == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(strValue);
		}
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

package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;


@SuppressWarnings("serial")
public class ConfigUnitRoleAction extends BaseAction {
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder
			.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private ProcessService processService = (ProcessService) ContextHolder
			.getBean("processService");

	private ConfigUnitService configUnitService = (ConfigUnitService) ContextHolder
			.getBean("configUnitService");
	DefinitionService ds = (DefinitionService) ContextHolder
			.getBean("definitionService");

	/**
	 * 
	 * @Methods Name queryCobom
	 * @Create In Feb 24, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String queryCobom() throws Exception {
		String json = "";
		// /////////////////////////////////////////////////////////////////////
		HttpServletRequest request = super.getRequest();
		// 注意以后从设置中取出
		int pageSize = 10;
		// 注意以后从设置中取出
		int start = getInt(request, "start", 0);
		int pageNo = start / pageSize + 1;
		String orderBy = HttpUtil.getString(request, "orderBy", "id");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String pClazz = request.getParameter("clazz");
		String propertyName = request.getParameter("displayField");
		Class clazz = getClass(pClazz);
		Long total = 0L;
		List queryList = new ArrayList();
		String id = request.getParameter("id");
		if (id != null && !"".equals(id)) {
			Object obj = service.find(clazz, id);
			total = 1L;
			queryList.add(obj);
		} else {
			Map<String, String> requestParams = HttpUtil
					.requestParam2Map(request);
			Integer deleteFlag = new Integer(0);
			requestParams.put("deleteFlag", deleteFlag.toString());
			Map<Object, Object> queryParamValues = metaDataManager
					.genQueryParams(clazz, requestParams);
			Page page = metaDataManager.query(clazz, queryParamValues, null,
					pageNo, pageSize, orderBy, isAsc);
			total = page.getTotalCount();
			queryList = page.list();
		}
		List<Map<String, Object>> listData = metaDataManager
				.getEntityMapDataForList(clazz, queryList);
		json = this.encodeForCombo(propertyName, listData, total);
		HttpServletResponse response = super.getResponse();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		return null;
	}

	/**
	 * 
	 * @Methods Name encodeForCombo
	 * @Create In Feb 24, 2009 By guangsa
	 * @param propertyName
	 * @param listData
	 * @param total
	 * @return String
	 */
	private String encodeForCombo(String propertyName,
			List<Map<String, Object>> listData, Long total) {
		String json = "";
		for (Map<String, Object> item : listData) {
			String dataItem = "";
			// ---valueField
			Object value = item.get("id");
			if (value == null)
				value = "";
			dataItem += "id:'" + value + "',";

			// ---displayField
			value = item.get(propertyName);
			if (value == null)
				value = "";
			dataItem += "" + propertyName + ":'" + value + "',";

			if (dataItem.endsWith(",")) {
				dataItem = dataItem.substring(0, dataItem.length() - 1);
			}
			dataItem = "{" + dataItem + "},";
			json += dataItem;
		}

		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
		return json;
	}

	/**
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

	/**
	 * 根据部门得到相应的角色
	 * 
	 * @Methods Name findRoleByDept
	 * @Create In Feb 24, 2009 By guangsa
	 * @return String
	 */
	public String findRoleByDept() {
		String json = "";
		String deptId = super.getRequest().getParameter("deptCode");
		Department department = (Department) super.getService().find(
				Department.class, deptId);
		List list = super.getService().find(Role.class, "department",
				department);

		for (int i = 0; i < list.size(); i++) {
			Role role = (Role) list.get(i);
			Long id = role.getId();
			String name = role.getName();
			String descn = role.getDescn();
			json += "{\"id\":" + id + ",\"descn\":\"" + descn
					+ "\",\"name\":\"" + name + "\"},";
		}
		if (json.endsWith(",")) {
			json = "{data:[" + json.substring(0, json.length() - 1) + "]}";
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
	 * 配置角色的保存方法
	 * 
	 * @Methods Name configUnitRoleSave
	 * @Create In Mar 24, 2009 By guangsa
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String configUnitRoleSave() {

		String configUnitRoleTableId = super.getRequest().getParameter("id");
		String virtualDefinitionId = super.getRequest().getParameter(
				"virtualDefinitionId");
		String nodeId = super.getRequest().getParameter("nodeId");
		String definitonName = HttpUtil.ConverUnicode(super.getRequest()
				.getParameter("definitonName"));
		String nodeName = super.getRequest().getParameter("nodeName");
		String nodeDesc = super.getRequest().getParameter("nodeDesc");
		String roleType = super.getRequest().getParameter("roleType");
		String nodeType = super.getRequest().getParameter("nodeType");
		String checkCreate = super.getRequest().getParameter("checkCreate");
		String sendMail = super.getRequest().getParameter("sendMail");
		String auditPerson = HttpUtil.ConverUnicode(super.getRequest()
				.getParameter("auditPer"));
		if ("".equals(checkCreate) || checkCreate == null
				|| "false".equals(checkCreate)) {
			checkCreate = "0";
		} else {
			checkCreate = "1";
		}
		if ("".equals(sendMail) || sendMail == null || "false".equals(sendMail)) {
			sendMail = "0";
		} else {
			sendMail = "1";
		}
		if ("需多人审批".equals(roleType)) {
			roleType = "0";
		} else if ("仅一人审批".equals(roleType)) {
			roleType = "1";
		} else if ("自定义审批".equals(roleType)) {
			roleType = "2";
		}
		if ("需多人审批".equals(nodeType)) {
			nodeType = "0";
		} else if ("仅一人审批".equals(nodeType)) {
			nodeType = "1";
		}
		String[] roleIds = super.getRequest().getParameterValues("roleId");
		// 第一次保存的时候
		if (configUnitRoleTableId == null || "".equals(configUnitRoleTableId)) {
			// 在往库中查找，如果数据库有这样role记录了。我就不在插入了
			ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
					virtualDefinitionId, nodeId);
			if (unitRole == null || "".equals(unitRole)) {// 这时里面还没有相应配置单元角色
				ConfigUnitRole user = new ConfigUnitRole();
				user.setDefinitionName(definitonName);
				user.setNodeName(nodeName);
				user.setNodeDesc(nodeDesc);
				user.setProcessId(Long.valueOf(virtualDefinitionId));
				user.setNodeId(Long.valueOf(nodeId));
				user.setNodeType(Integer.parseInt(nodeType));
				user.setIsGiveCreate(Integer.parseInt(checkCreate));
				user.setIsNotSendMail(Integer.parseInt(sendMail));
				super.getService().save(user);

				if (roleIds != null && roleIds.length > 0) {
					for (int i = 0; i < roleIds.length; i++) {
						String roleId = roleIds[i];
						if (!"".equals(roleId)) {
							Role role = (Role) super.getService().find(
									Role.class, roleId);
							// **********************************************************
							ConfigUnitRoleTable table = new ConfigUnitRoleTable();
							table.setConfigUnitRole(user);
							table.setRole(role);
							table.setFlag(Integer.parseInt(roleType));
							table.setWorkflowBrowsePerson(auditPerson);
							super.getService().save(table);
							// **********************************************************
						}
					}
				}
				PrintWriter out = null;
				try {
					out = super.getResponse().getWriter();
					out.write("{success:" + true + "}");
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();

				} finally {
					if (out != null) {
						out.close();
					}
				}
			} else {// 如果库中已经存在了这样一条role记录，只需要保存相应的角色
				List<ConfigUnitRoleTable> list = super.getService().find(
						ConfigUnitRoleTable.class, "configUnitRole", unitRole);
				List roleList = new ArrayList();
				for (ConfigUnitRoleTable table : list) {
					roleList.add(table.getRole());
				}

				if (roleIds != null && roleIds.length > 0) {
					for (int i = 0; i < roleIds.length; i++) {
						String roleId = roleIds[i];
						if (!"".equals(roleId)) {
							Role role = (Role) super.getService().find(
									Role.class, roleId);
							if (roleList.contains(role)) {// 如果当前角色又重复值
								PrintWriter out = null;
								try {
									out = super.getResponse().getWriter();
									out.write("{success:" + false + "}");
									out.flush();
									return null;
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if (out != null) {
										out.close();
									}
								}
								/***************************************************/
							} else {
								ConfigUnitRoleTable table = new ConfigUnitRoleTable();
								table.setConfigUnitRole(unitRole);
								table.setRole(role);
								table.setFlag(Integer.parseInt(roleType));
								table.setWorkflowBrowsePerson(auditPerson);
								super.getService().save(table);
								PrintWriter out = null;
								try {
									out = super.getResponse().getWriter();
									out.write("{success:" + true + "}");
									out.flush();
								} catch (IOException e) {
									e.printStackTrace();
								} finally {
									if (out != null)
										out.close();
								}
							}
						}
					}
				}
			}
		} else {// 如果是修改记录的话
//			boolean deMark = false;
			/*********************************************** 保存修改的配置单元（角色）信息 ************************/
			// 思路就是让现在的角色一个一个去和数据库中比较，如果没有就保存；
			if (roleIds != null && roleIds.length > 0) {
				List roleList = new ArrayList();
				for (int i = 0; i < roleIds.length; i++) {
					String roleId = roleIds[i];// 这个就是前台传过来的所有角色
					Role role = (Role) super.getService().find(Role.class,
							roleId);
					if (!"".equals(roleId)) {// LIST就是当前修改角色信息
						ConfigUnitRoleTable configUnitRoleTable = (ConfigUnitRoleTable) super
								.getService().find(ConfigUnitRoleTable.class,
										configUnitRoleTableId);
						List<ConfigUnitRoleTable> list = super
								.getService()
								.find(ConfigUnitRoleTable.class,
										"configUnitRole",
										configUnitRoleTable.getConfigUnitRole());
						for (ConfigUnitRoleTable table : list) {
							roleList.add(table.getRole());
						}
						roleList.remove(configUnitRoleTable.getRole());
						/***************************************************/
						if (roleList.contains(role)) {// 如果当前角色有重复值
							PrintWriter out = null;
							try {
								out = super.getResponse().getWriter();
								out.write("{success:" + false + "}");
								out.flush();
								return null;
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								if (out != null)
									out.close();
							}
							/***************************************************/
						} else {
							configUnitRoleTable
									.setConfigUnitRole(configUnitRoleTable
											.getConfigUnitRole());
							configUnitRoleTable.setRole(role);
							configUnitRoleTable.setFlag(Integer
									.parseInt(roleType));
							configUnitRoleTable
									.setWorkflowBrowsePerson(auditPerson);
							super.getService().save(configUnitRoleTable);
						}
					}
				}
			}
			PrintWriter out = null;
			try {
				out = super.getResponse().getWriter();
				out.write("{success:" + true + "}");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				if (out != null)
					out.close();
			}
		}
		return null;
	}

	/**
	 * 修改表单数据
	 * 
	 * @Methods Name modifyRoleMessage
	 * @Create In Feb 24, 2009 By guangsa
	 * @return String
	 */
	public String modifyRoleMessage() {

		String json = "";
		String configUnitRoleTableId = super.getRequest().getParameter("id");
		ConfigUnitRoleTable rolesTable = (ConfigUnitRoleTable) super
				.getService().find(ConfigUnitRoleTable.class,
						configUnitRoleTableId);
		// List<ConfigUnitRoleTable> rolesTable =
		// super.getService().find(ConfigUnitRoleTable.class, "configUnitRole",
		// configUnitRole);
		// Set roles = configUnitRole.getRoles();
		// Iterator ite = roles.iterator();
		// while(ite.hasNext()){
		// for(int i=0;i<rolesTable.size();i++){
		Role role = rolesTable.getRole();// (Role)ite.next();
		json += "{\"id\":" + role.getId() + ",\"descn\":\"" + role.getDescn()
				+ "\",\"name\":\"" + role.getName() + "\"},";
		// }
		// if(rolesTable.size()!=0){
		json = "{data:[" + json.substring(0, json.length() - 1) + "]}";
		// }
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String configUnitRoleDelete() {

		String[] configUnitRoleTableId = super.getRequest().getParameterValues(
				"removeIds");
		String virtualId = super.getRequest().getParameter(
				"virtualDefinitionId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitRole configUnitRole = configUnitService.findConfigUnitRole(
				virtualId, nodeId);
		if (configUnitRole != null && !"".equals(configUnitRole)) {

			Integer creatorFlag = configUnitRole.getIsGiveCreate();
			if (creatorFlag == 0) {// 当说明没有加入申请人时候，全部删除即可
				for (int i = 0; i < configUnitRoleTableId.length; i++) {
					ConfigUnitRoleTable configUnitTable = (ConfigUnitRoleTable) super
							.getService().find(ConfigUnitRoleTable.class,
									configUnitRoleTableId[i]);
					super.getService().remove(configUnitTable);
				}
				List<ConfigUnitRoleTable> table = super.getService().find(
						ConfigUnitRoleTable.class, "configUnitRole",
						configUnitRole);
				if (table.size() == 0) {
					super.getService().remove(configUnitRole);
				}
			} else {// 当有申请人时候，时删除角色即可
				for (int i = 0; i < configUnitRoleTableId.length; i++) {
					ConfigUnitRoleTable configUnitTable = (ConfigUnitRoleTable) super
							.getService().find(ConfigUnitRoleTable.class,
									configUnitRoleTableId[i]);
					super.getService().remove(configUnitTable);
				}
			}
		}

		try {
			super.getResponse().getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 角色面板修改功能中得到相应的面板值
	 * 
	 * @Methods Name queryProNameAndnodeName
	 * @Create In Feb 26, 2009 By guangsa
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String queryProNameAndnodeName() {
//		String json = "";
//		String vProcessName = "";
		String vProcessDesc = "";
		String nodeName = "";
		String nodeDesc = "";
		String typeName = "";

		String virtualDefinitionInfoId = super.getRequest().getParameter(
				"virtualDefinitionInfoId");
		String nodeId = super.getRequest().getParameter("nodeId");
		String tableId = super.getRequest().getParameter("tableId");
//		ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
//				virtualDefinitionInfoId, nodeId);
		VirtualDefinitionInfo vitualDef = (VirtualDefinitionInfo) service
				.findUnique(VirtualDefinitionInfo.class, "id", Long
						.valueOf(virtualDefinitionInfoId));
		List<VirtualNodeInfo> virturalNodes = service.find(
				VirtualNodeInfo.class, "virtualDefinitionInfo", vitualDef);
		List listProperty = new ArrayList();
		HashMap<String, Object> relation = new HashMap<String, Object>();
		if (tableId != null && !"".equals(tableId)) {
			ConfigUnitRoleTable unitTable = (ConfigUnitRoleTable) super
					.getService().find(ConfigUnitRoleTable.class, tableId);
			if (unitTable != null && !"".equals(unitTable)) {
				Integer flag = unitTable.getFlag();
				if (flag != null) {
					if (flag.equals(1)) {
						typeName = "仅一人审批";
					} else if (flag.equals(0)) {
						typeName = "需多人审批";
					} else {
						typeName = "自定义审批";
					}
				}
			}
		}
		for (VirtualNodeInfo node : virturalNodes) {
			if (node.getNodeId() == Long.valueOf(nodeId)
					|| nodeId.equals(node.getNodeId() + "")) {
//				vProcessName = vitualDef.getVirtualDefinitionName();
				vProcessDesc = vitualDef.getVirtualDefinitionDesc();
				nodeName = node.getVirtualNodeName();
				nodeDesc = node.getVirtualNodeDesc();
				relation.put("definitonName", vProcessDesc);
				relation.put("nodeName", nodeName);
				relation.put("nodeDesc", nodeDesc);
				relation.put("typeName", typeName);
				listProperty.add(relation);

			}
		}
		JSONArray jsonObject = JSONArray.fromObject(listProperty);
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter()
					.write(
							"{success:" + true + ",list:"
									+ jsonObject.toString() + "}");
			super.getResponse().getWriter().flush();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到相应的配置单元角色
	 * 
	 * @Methods Name getConfigUnitRole
	 * @Create In Mar 24, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String getConfigUnitRole() throws Exception {

		String json = "";
		String roleType = "";
		String nodeType = "";
		String processId = super.getRequest().getParameter(
				"virtualDefinitionInfoId");
		String nodeId = super.getRequest().getParameter("nodeId");
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service
				.findUnique(VirtualDefinitionInfo.class, "id", Long
						.valueOf(processId));
		// VirtualNodeInfo virtualNodeInfo =
		// (VirtualNodeInfo)service.findUnique(VirtualNodeInfo.class, "nodeId",
		// Long.valueOf(nodeId));//?
		VirtualNodeInfo virtualNodeInfo = configUnitService
				.findVirtualNodeInfo(virtualDefinitionInfo, nodeId);
		String desc = virtualDefinitionInfo.getVirtualDefinitionDesc();
		String nodeName = virtualNodeInfo.getVirtualNodeName();
		String nodeDesc = virtualNodeInfo.getVirtualNodeDesc();
		ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
				processId, nodeId);
		if (unitRole != null && !"".equals(unitRole)) {
			Integer flag = unitRole.getNodeType();
			if (flag != null) {
				if (flag.equals(1)) {
					nodeType = "仅一人审批";
				} else if (flag.equals(0)) {
					nodeType = "需多人审批";
				}
			}
		}
		List<ConfigUnitRoleTable> unitTable = super.getService().find(
				ConfigUnitRoleTable.class, "configUnitRole", unitRole);

		if (unitRole != null) {

//			String roleName = "";
			List<Role> listRole = new ArrayList<Role>();
			List listFlag = new ArrayList();

			Map roles = configUnitService.showRole(processId, nodeId);// 得到了所有的节点角色
			Set forkParam = roles.keySet();
			Iterator ite = forkParam.iterator();
			while (ite.hasNext()) {
				Role role = (Role) ite.next();
				listRole.add(role);
				Integer flag = (Integer) roles.get(role);
				listFlag.add(flag);
			}

			for (int i = 0; i < unitTable.size(); i++) {// 保存table表的id
				int flags = unitTable.get(i).getFlag();
				if (flags == 1) {
					roleType = "仅一人审批";
				} else if (flags == 0) {
					roleType = "需多人审批";
				} else if (flags == 2) {
					roleType = "自定义审批";
				}
				json += "{id:" + unitTable.get(i).getId() + ",nodeType:'"
						+ nodeType + "',virtualDesc:'" + desc + "',nodeName:'"
						+ nodeName + "',roleType:'" + roleType + "',roleName:'"
						+ unitTable.get(i).getRole().getName() + "',nodeDesc:'"
						+ nodeDesc + "'},";
			}
			if (roles.size() != 0) {
				json = "{data:[" + json.substring(0, json.length() - 1) + "]}";
			}

		}
		try {
			super.getResponse().setCharacterEncoding(
					this.getProperties("system.characterEncoding", "utf-8"));
			super.getResponse().setContentType("text/html");
			PrintWriter out = super.getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();

		} catch (RuntimeException e) {
			e.printStackTrace();
			super.getResponse().setCharacterEncoding(
					this.getProperties("system.characterEncoding", "utf-8"));
			PrintWriter writer = super.getResponse().getWriter();
			writer.write("{success:false}");
			writer.flush();

		}
		return null;
	}

	/**
	 * 根据流程部门找到相应的部门角色
	 * 
	 * @Methods Name findRolesByDept
	 * @Create In Mar 24, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String findRolesByDept() throws Exception {
		String json = "";
		String virtualId = super.getRequest()
				.getParameter("virtualDefinitonId");
		VirtualDefinitionInfo virtualDefinitonInfo = (VirtualDefinitionInfo) super
				.getService().find(VirtualDefinitionInfo.class, virtualId);
		Department dept = virtualDefinitonInfo.getDept();
		List<Role> roles = super.getService().find(Role.class, "department",
				dept);
		if (!"".equals(roles) && !roles.isEmpty()) {
			for (Role role : roles) {
				json += "{\"id\":" + role.getId() + ",\"descn\":\""
						+ role.getDescn() + "\",\"name\":\"" + role.getName()
						+ "\"},";
			}
		}
		if (roles.size() != 0) {
			json = "{data:[" + json.substring(0, json.length() - 1) + "]}";
		}
		PrintWriter out = null;
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			out = super.getResponse().getWriter();
			out.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null)
				out.close();
		}
		return null;
	}

	/**
	 * 得到配置单元的标识
	 * 
	 * @Methods Name getConfigUnitFlag
	 * @Create In May 13, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String getConfigUnitFlag() throws Exception {

		int flag = 1;
		String processId = super.getRequest().getParameter(
				"virtualDefinitionInfoId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
				processId, nodeId);
		if (unitRole != null && !"".equals(unitRole)) {
			flag = unitRole.getNodeType();
		}
		try {
			super.getRequest().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write("{success:" + true + ",flag:" + flag + "}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到是否有加入创建者
	 * 
	 * @Methods Name getConfigUnitCreateFlag
	 * @Create In May 13, 2009 By guangsa
	 * @return String
	 */
	public String getConfigUnitCreateFlag() throws Exception {

		int flag = 0;
		int sendMailFlag = 0;
		String virProcessId = super.getRequest().getParameter(
				"virtualDefinitionInfoId");
		String nodeId = super.getRequest().getParameter("nodeId");
		ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
				virProcessId, nodeId);
		if (unitRole != null && !"".equals(unitRole)) {
			flag = unitRole.getIsGiveCreate();
			sendMailFlag = unitRole.getIsNotSendMail();// 默需要发送认
		}
		try {
			super.getRequest().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write("{success:" + true + ",sendMailFlag:" + sendMailFlag
					+ ",flag:" + flag + "}");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到分页器
	 * 
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
	 * 后台角色面板中自定义类型审批人 功能是可查询和分页
	 * 
	 * @Methods Name configUnitRoleCreateCheckSave
	 * @Create In May 13, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	@SuppressWarnings("unchecked")
	public String getAuditPersonByNode() throws Exception {

		String json = "";
		int total = 0;
		int pageSize = 10;
		int start = this.getNumber(super.getRequest(), "start", 0);
		int pageNo = start / pageSize + 1;
		List searchUserName = new ArrayList<UserInfo>();
		String userName = super.getRequest().getParameter("realName");
		userName = HttpUtil.ConverUnicode(userName);
		String roleId = super.getRequest().getParameter("roleId");
		Role role = (Role) service.find(Role.class, roleId);
		List<String> userNameList = configUnitService.getUserNameByRoleId(role);// 得到本角色中的用户名

		if (!"".equals(userName) && userName != null) {// 用户输入了相应的值
			for (int i = 0; i < userNameList.size(); i++) {
				if (userNameList.get(i).contains(userName)) {
					searchUserName.add(userNameList.get(i));
				}
			}
			int length = 0;
			if (pageNo * pageSize > searchUserName.size()) {
				length = searchUserName.size();
			} else {
				length = pageNo * pageSize;
			}
			for (int i = pageNo * pageSize - 10; i < length; i++) {
				json += "{id:" + i + 1 + ",realName:'" + searchUserName.get(i)
						+ "'},";
			}
			total = searchUserName.size();
		} else {// 用户没有输入相应的值
			int length = 0;
			if (pageNo * pageSize > userNameList.size()) {
				length = userNameList.size();
			} else {
				length = pageNo * pageSize;
			}
			for (int i = pageNo * pageSize - 10; i < length; i++) {
				json += "{id:" + i + 1 + ",realName:'" + userNameList.get(i)
						+ "'},";
			}
			total = userNameList.size();
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true, rowCount:" + total + ",data:[" + json + "]}";
		System.out.println(json);
		try {
			super.getResponse().setCharacterEncoding("utf-8");
			super.getResponse().getWriter().write(json);
			super.getResponse().getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存是否不需要发送邮件 首先判断如果configUnitRole已经存在，则只需添加上相应的属性值； 如果没有存在，就创建一条unitRole记录
	 * 
	 * @Methods Name configUnitRoleSendMailCheckSave
	 * @Create In May 13, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String configUnitRoleSendMailCheckSave() throws Exception {

		String checkValue = super.getRequest().getParameter("checkValue");
		String processName = super.getRequest().getParameter("processName");
		String nodeName = super.getRequest().getParameter("nodeName");
		String virtualDefinitionInfoId = super.getRequest().getParameter(
				"virtualDefinitionId");
		String nodeId = super.getRequest().getParameter("nodeId");
		String nodeType = super.getRequest().getParameter("nodeType");
		String giveValue = super.getRequest().getParameter("giveValue");
		if ("true".equals(checkValue)) {
			checkValue = "1";// 不需要发送邮件
		} else {
			checkValue = "0";// 需要发送邮件
		}
		if ("true".equals(giveValue)) {
			giveValue = "1";// 增加申请人
		} else {
			giveValue = "0";// 不用加入申请人
		}
		if ("需多人审批".equals(nodeType)) {
			nodeType = "0";
		} else if ("仅一人审批".equals(nodeType)) {
			nodeType = "1";
		}
		ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
				virtualDefinitionInfoId, nodeId);
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service
				.find(VirtualDefinitionInfo.class, virtualDefinitionInfoId);
		VirtualNodeInfo nodeInfo = configUnitService.findVirtualNodeInfo(
				virtualDefinitionInfo, nodeId);

		if (unitRole == null || "".equals(unitRole)) {
			ConfigUnitRole configUnitRole = new ConfigUnitRole();
			configUnitRole.setNodeName(nodeName);
			configUnitRole.setDefinitionName(processName);
			configUnitRole.setNodeId(Long.valueOf(nodeId));
			configUnitRole.setProcessId(Long.valueOf(virtualDefinitionInfoId));
			configUnitRole.setIsNotSendMail(Integer.parseInt(checkValue));
			configUnitRole.setIsGiveCreate(Integer.parseInt(giveValue));

			configUnitRole.setNodeType(Integer.parseInt(nodeType));
			if (nodeInfo != null && !"".equals(nodeInfo)) {
				configUnitRole.setNodeDesc(nodeInfo.getVirtualNodeDesc());
			}
			service.save(configUnitRole);
		} else {
			unitRole.setIsNotSendMail(Integer.parseInt(checkValue));
			service.save(unitRole);
		}
		return null;

	}

	/**
	 * 保存是否有创建者来审批内容 首先判断如果configUnitRole已经存在，则只需添加上相应的属性值；
	 * 如果没有存在，就创建一条unitRole记录
	 * 
	 * @Methods Name configUnitRoleCreateCheckSave
	 * @Create In May 13, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String configUnitRoleCreateCheckSave() throws Exception {

		String checkValue = super.getRequest().getParameter("checkValue");
//		String virtualDesc = super.getRequest().getParameter("virtualDesc");
		String processName = super.getRequest().getParameter("processName");
		String nodeName = super.getRequest().getParameter("nodeName");
		String virtualDefinitionInfoId = super.getRequest().getParameter(
				"virtualDefinitionId");
		String nodeId = super.getRequest().getParameter("nodeId");
		String nodeType = super.getRequest().getParameter("nodeType");
		String sendValue = super.getRequest().getParameter("sendValue");

		if ("true".equals(checkValue)) {
			checkValue = "1";// 增加申请人
		} else {
			checkValue = "0";// 不用加入申请人
		}
		if ("true".equals(sendValue)) {
			sendValue = "1";// 增加申请人
		} else {
			sendValue = "0";// 不用加入申请人
		}

		if ("需多人审批".equals(nodeType)) {
			nodeType = "0";
		} else if ("仅一人审批".equals(nodeType)) {
			nodeType = "1";
		}
		ConfigUnitRole unitRole = configUnitService.findConfigUnitRole(
				virtualDefinitionInfoId, nodeId);
		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service
				.find(VirtualDefinitionInfo.class, virtualDefinitionInfoId);
		VirtualNodeInfo nodeInfo = configUnitService.findVirtualNodeInfo(
				virtualDefinitionInfo, nodeId);

		if (unitRole == null || "".equals(unitRole)) {
			ConfigUnitRole configUnitRole = new ConfigUnitRole();
			configUnitRole.setNodeName(nodeName);
			configUnitRole.setDefinitionName(processName);
			configUnitRole.setNodeId(Long.valueOf(nodeId));
			configUnitRole.setProcessId(Long.valueOf(virtualDefinitionInfoId));
			configUnitRole.setIsGiveCreate(Integer.parseInt(checkValue));
			configUnitRole.setNodeType(Integer.parseInt(nodeType));
			configUnitRole.setIsNotSendMail(Integer.parseInt(sendValue));
			if (nodeInfo != null && !"".equals(nodeInfo)) {
				configUnitRole.setNodeDesc(nodeInfo.getVirtualNodeDesc());
			}
			service.save(configUnitRole);
		} else {
			unitRole.setIsGiveCreate(Integer.parseInt(checkValue));
			service.save(unitRole);
		}
		return null;
	}

	public Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	@SuppressWarnings("unchecked")
	public String test() {
		// DefinitionServiceImpl dsi = new DefinitionServiceImpl();
		Map mapBizz = new HashMap();
		mapBizz.put("applyTypeName", "申请类型");
		mapBizz.put("workflowHistory",
				"com.digitalchina.itil.config.entity.ConfigItemAuditHis");
		mapBizz.put("applyType", "cproject");
		mapBizz.put("addDynCounterSign", "repeatTaskNodeTwo:gaowen,guoxl");
		// mapBizz.put("dynCounterSign",
		// "repeatTaskNodeTwo:1+guangsa,haowc$repeatTaskNodeThree:0+guoxl,sujs");
		// mapBizz.put("userList", "repeatTaskNodeThree:sujs,haowc");
		// mapBizz.put("addDynAssignPer", "repeatTaskNodeTwo:gaowen,guoxl");
		mapBizz.put("dataId", "1286");
		Long processId = new Long(0);
		try {
			processId = processService.createProcess(
					"TestMulitpleFlow9876541250501630687", "guangsa", mapBizz);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(processId);
		// long tt = 153;
		// dsi.deleteDefinition(tt);
		return null;
	}
}
// 子流程1----公告管理审批流程的业务参数
// mapBizz.put("noticeManager1237967838187",
// "dataId=85,applyId=85,applyType=nproject,applyTypeName=公告审批,customer=");
// 子流程2----配置项审批流程的业务参数 TestExceptionTaskNode1247054291640
// mapBizz.put("addDynAssignPer", "taskTwo:guangsa");
// mapBizz.put("userList","taskThree:sujs,gaowen");
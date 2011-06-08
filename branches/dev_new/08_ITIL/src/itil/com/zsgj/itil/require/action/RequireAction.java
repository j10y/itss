package com.zsgj.itil.require.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.appframework.pagemodel.servlet.CoderForSave;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.zsgj.itil.account.service.AccountService;
import com.zsgj.itil.config.entity.ConfigItem;
import com.zsgj.itil.config.extci.entity.DeliveryTeam;
import com.zsgj.itil.config.extci.entity.ServiceEngineer;
import com.zsgj.itil.config.extlist.entity.RequirementLevel;
import com.zsgj.itil.config.extlist.entity.SRProjectPlan;
import com.zsgj.itil.project.service.SRProjectPlanService;
import com.zsgj.itil.require.entity.ERP_NormalNeed;
import com.zsgj.itil.require.entity.ProcessLockInfo;
import com.zsgj.itil.require.entity.RequireAppSystem;
import com.zsgj.itil.require.entity.RequireApplyDefaultAudit;
import com.zsgj.itil.require.entity.RequireFactoryInfo;
import com.zsgj.itil.require.entity.SpecialRequirement;
import com.zsgj.itil.require.service.RequireSIService;
import com.zsgj.itil.require.service.RequireService;
import com.zsgj.itil.requireErp.synchronize.SAPExecute;
import com.zsgj.itil.service.entity.Constants;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.service.ServiceItemProcessService;
import com.zsgj.itil.service.service.ServiceItemService;
import com.zsgj.itil.service.service.ServiceItemUserTableService;

public class RequireAction extends BaseAction {
	private Service service = (Service) ContextHolder.getBean("baseService");
	private PageManager pageManager = (PageManager) ContextHolder.getBean("pageManager");
	private RequireService requireService = (RequireService) getBean("requireService");
	private RequireSIService requireSIService = (RequireSIService) getBean("requireSIService");
	private ServiceItemService serviceItemService = (ServiceItemService) getBean("serviceItemService");
	private ServiceItemProcessService serviceItemProcessService = (ServiceItemProcessService) getBean("serviceItemProcessService");
	private ServiceItemUserTableService serviceItemUserTableService = (ServiceItemUserTableService) getBean("serviceItemUserTableService");
	private PageModelService pageModelService = (PageModelService) getBean("pageModelService");
	private PagePanelService pagePanelService = (PagePanelService) getBean("pagePanelService");
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
//	private DefinitionService ds = (DefinitionService) ContextHolder.getBean("definitionService");
//	private ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	private SRProjectPlanService projectPlanService = (SRProjectPlanService) getBean("SRprojectPlanService");
//	private AccountService accountService=(AccountService)getBean("accountService");
	private SAPExecute SAPExecute = (SAPExecute)getBean("SAPExecute");
	private String forwardUrl;

	/**
	 * Ϊ����������ҳ���ṩ��Ϣ
	 * 
	 * @Methods Name toRequireInfo
	 * @Create In Feb 25, 2009 By lee
	 * @return String
	 */
	public String toRequireInfo() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(id);
//		String serviceItemName = serviceItem.getName();
		ServiceItemUserTable siut = serviceItemUserTableService
				.findServiceItemUserTableByServiceItem(serviceItem);
		if (siut.getPageListPanel() == null) {
			throw new ApplicationException("�˷���û������������Ϣ");
		}
		List<ServiceItemProcess> serviceItemProcesses = serviceItemProcessService
				.findProcessesByServiceItem(serviceItem);
		int processTypeSum = 0;
		for (ServiceItemProcess serviceItemProcess : serviceItemProcesses) {
			processTypeSum += serviceItemProcess.getSidProcessType();
		}
		String listPanelName = siut.getPageListPanel().getName();
		String tableName = siut.getSystemMainTable().getTableName();
//		String clazz = siut.getClassName();
		request.setAttribute("serviceItemId", serviceItem.getId());
		request.setAttribute("serviceItemName", serviceItem.getName());
		request.setAttribute("processTypeSum", processTypeSum);
		request.setAttribute("gridName", listPanelName);
		request.setAttribute("tableName", tableName);// "com.zsgj.itil.service.entity.ServiceItem");//
		return "requireInfo";
	}

	/**
	 * ��ȡ�����̬ҳ��ģ��
	 * 
	 * @Methods Name getPageModelForRequire
	 * @Create In Feb 27, 2009 By lee
	 * @return String
	 */
	public String getPageModelForRequire() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String keyName = request.getParameter("modelName");
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = (ServiceItem) getService().find(
				ServiceItem.class, serviceItemId);
		// ����model�Ĺؼ���ȡmodel���˷����ǿ����µķ������˼�����Ƶģ��ײ�������ȡ�������������
		PageModel model = pageModelService.findPageModel$$$$$(keyName);
		String json = "";
		json = "{\"pageModel\":[{";
		json += "\"name\":\"" + model.getName() + "\",";
		json += "\"title\":\"" + model.getTitle() + "\",";
		json += "\"modelTableName\":\""
				+ model.getSystemMainTable().getTableName() + "\",";
		json += "\"pagePathType\":\"" + model.getPagePanelType().getName()
				+ "\",";
		json += "\"pagePath\":\"" + model.getPagePath() + "\"";
		json += "}],";
		// end
		json += requireService.getRequirePanelJson(keyName, serviceItem);
		json = json.substring(0, json.length() - 1);
		json += "]}";
		// *******************************************************************
		//System.out.println(json);
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
	 * ����������ǩ����������
	 * @Methods Name saveWM
	 * @Create In Jun 10, 2009 By lee
	 * @return String
	 */
	public String saveWM(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String isWM = request.getParameter("isWM");
		String wmInfo = request.getParameter("wmInfo");
		ERP_NormalNeed normalNeed = (ERP_NormalNeed) service.find(ERP_NormalNeed.class, dataId);
		normalNeed.setIsWM(Integer.valueOf(isWM));
		normalNeed.setIncludeAndExpend(wmInfo);
		service.save(normalNeed);
		String json = "{success:ture}";
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
	 * ��ȡָ������ָ��״̬��������������
	 * 
	 * @Methods Name forRequirePanel
	 * @Create In Feb 27, 2009 By lee
	 * @return
	 * @throws IOException
	 *             String
	 */
	public String forRequirePanel() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService
				.findServiceItemById(serviceItemId);
		String json = "";
//		String modelName = request.getParameter("modelName");
//		String configItemTypeId = request.getParameter("configItemTypeId");
		// ȡ������panel��Ҫ��id
		// PageModel model = pageModelService.findPageModel(keyName);
		List<PageModelPanel> pmps = (List<PageModelPanel>) requireService
				.findPanelsByServiceItem("serviceItemRequire_page", serviceItem);
		for (PageModelPanel item : pmps) {
			String divFloat = item.getDivFloat();
			if (divFloat == null)
				divFloat = "";
			PagePanel panel = item.getPagePanel();
			json += "{";
			json += "\"panelname\":\"" + panel.getName() + "\",";
			json += "\"title\":\"" + panel.getTitle() + "\",";
			json += "\"panelTableName\":\""
					+ panel.getSystemMainTable().getTableCnName() + "\",";
			json += "\"xtype\":\"" + panel.getXtype().getName() + "\",";
			json += "\"divFloat\":\"" + divFloat + "\",";
			json += "\"order\":\"" + item.getOrder() + "\",";
			json += "\"clazz\":\"" + panel.getSystemMainTable().getClassName()
					+ "\"";
			json += "},";
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ��ȡ�������������columnTree��������
	 * 
	 * @Methods Name getRequireTreeData
	 * @Create In Mar 2, 2009 By lee
	 * @return String
	 * @throws IOException
	 */
	public String getRequireTreeData() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json = "";
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService
				.findServiceItemById(serviceItemId);
		List<ServiceItemProcess> processes = serviceItemProcessService
				.findProcessesByServiceItem(serviceItem);
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService
				.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
		for (ServiceItemProcess process : processes) {
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
			//json += "{process:'" + process.getDefinitionName() + "',";
			json += "{process:'" + process.getProcessInfo().getRealDefinitionDesc()+"',";
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
			json += "uiProvider:'col',";
			json += "cls:'master-task',";
			json += "iconCls:'task-folder',";
			json += "children:[";
			json += "{process:\"�ݸ�\",";
			json += "uiProvider:'col',";
			json += "cls:'master-task',";
			json += "iconCls:'task-folder',";
			json += "children:["
					+ requireService.forQuerry(className, process
							.getSidProcessType(), 0) + "]},";
			json += "{process:\"������\",";
			json += "uiProvider:'col',";
			json += "cls:'master-task',";
			json += "iconCls:'task-folder',";
			json += "children:["
					+ requireService.forQuerry(className, process
							.getSidProcessType(), 1) + "]},";
			json += "{process:\"��������\",";
			json += "uiProvider:'col',";
			json += "cls:'master-task',";
			json += "iconCls:'task-folder',";
			json += "children:["
					+ requireService.forQuerry(className, process
							.getSidProcessType(), 2) + "]}";
			json += "]},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ��ת�����������λҳ�����(���ڲݸ壬�����У�������״̬˫����ͬ��Ӧ)
	 * 
	 * @Methods Name toOperatePanel
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	public String toOperatePanel() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
		Class clazz = this.toClass(className);
		Integer processType = Constants.RROCESS_TYPE_APPLY;
		Integer status = Constants.STATUS_DRAFT;
		if (StringUtils.isNotBlank(id)) {
			Object object = getService().find(clazz, id, true);
			BeanWrapper bw = new BeanWrapperImpl(object);
			processType = (Integer) bw.getPropertyValue("processType");
			status = (Integer) bw.getPropertyValue("status");
		}
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findProcessesByServiceItemAndType(serviceItem, processType);
		//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
//		String virtualDesc = serviceItemProcess.getDefinitionName();
//		/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
//				.findUnique(VirtualDefinitionInfo.class,
//						"virtualDefinitionDesc", virtualDesc);
		
		VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
		//modify by lee for change serviceItemProcess to ORM in 200090707 end
		String vname = virtualDefinitionInfo.getVirtualDefinitionName();
		String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
		String pagePanelName = serviceItemProcess.getPagePanel().getName();
		request.setAttribute("serviceItemId", serviceItemId);
		request.setAttribute("processType", processType);
		request.setAttribute("pagePanel", pagePanelName);
		request.setAttribute("reqClass", className);
		request.setAttribute("processName", vname);
		request.setAttribute("description", vdescription);
		request.setAttribute("status", status);
		request.setAttribute("dataId", id);
		return "operate";
	}

	/**
	 * ��ת������������λҳ�����(���ڲݸ壬�����У�������״̬˫����ͬ��Ӧ)
	 * 
	 * @Methods Name toModifyOperatePanel
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	public String toModifyOperatePanel() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService
				.findServiceItemById(serviceItemId);
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService
				.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
		Class clazz = this.toClass(className);
		Integer processType = Constants.RROCESS_TYPE_MODIFY;
		Integer status = Constants.STATUS_DRAFT;
		if (StringUtils.isNotBlank(id)) {
			Object object = getService().find(clazz, id, true);
			BeanWrapper bw = new BeanWrapperImpl(object);
			processType = (Integer) bw.getPropertyValue("processType");
			status = (Integer) bw.getPropertyValue("status");
		}
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findProcessesByServiceItemAndType(serviceItem, processType);
		//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
//		String virtualDesc = serviceItemProcess.getDefinitionName();
//		/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
//				.findUnique(VirtualDefinitionInfo.class,
//						"virtualDefinitionDesc", virtualDesc);
		
		VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
		//modify by lee for change serviceItemProcess to ORM in 200090707 end
		String vname = virtualDefinitionInfo.getVirtualDefinitionName();
		String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
		String pagePanelName = serviceItemProcess.getPagePanel().getName();
		request.setAttribute("serviceItemId", serviceItemId);
		request.setAttribute("pagePanel", pagePanelName);
		request.setAttribute("processType", processType);
		request.setAttribute("reqClass", className);
		request.setAttribute("processName", vname);
		request.setAttribute("description", vdescription);
		request.setAttribute("status", status);
		request.setAttribute("dataId", id);
		return "operate";
	}

	/**
	 * ��ת������������λҳ�����(���ڲݸ壬�����У�������״̬˫����ͬ��Ӧ)
	 * 
	 * @Methods Name toCancelOperatePanel
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	public String toCancelOperatePanel() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService
				.findServiceItemById(serviceItemId);
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService
				.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
//		Class clazz = this.toClass(className);
		Integer processType = Constants.RROCESS_TYPE_CANCLE;
		Integer status = Constants.STATUS_DRAFT;
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findProcessesByServiceItemAndType(serviceItem, processType);
		//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
//		String virtualDesc = serviceItemProcess.getDefinitionName();
//		/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
//				.findUnique(VirtualDefinitionInfo.class,
//						"virtualDefinitionDesc", virtualDesc);
		
		VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
		//modify by lee for change serviceItemProcess to ORM in 200090707 end
		String vname = virtualDefinitionInfo.getVirtualDefinitionName();
		String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
		String pagePanelName = serviceItemProcess.getPagePanel().getName();
		request.setAttribute("serviceItemId", serviceItemId);
		request.setAttribute("pagePanel", pagePanelName);
		request.setAttribute("processType", processType);
		request.setAttribute("reqClass", className);
		request.setAttribute("processName", vname);
		request.setAttribute("description", vdescription);
		request.setAttribute("status", status);
		request.setAttribute("dataId", id);
		return "operate";
	}

	/**
	 * ��ȡ��ͨ���������Ԫ����
	 * 
	 * @Methods Name getApplyRequireFormPanel
	 * @Create In Mar 24, 2009 By lee
	 * @return String
	 */
	public String getApplyRequireFormPanel() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json = "";
		String id = request.getParameter("id");
		String panelName = request.getParameter("panelname");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = panel.getSystemMainTable();
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		List<PagePanelColumn> pagePanelColumns = pageManager
				.getUserPagePanelColumn(panelName);
		List<PagePanelColumn> curColumns = new ArrayList<PagePanelColumn>();
		for (PagePanelColumn pagePanelColumn : pagePanelColumns) {
			String propertyName = pagePanelColumn.getColumn().getPropertyName();
			if (!propertyName.endsWith("oldApply")) { // !propertyName.endsWith("name")&&
				curColumns.add(pagePanelColumn);
			}
		}
		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			if (panel.getXtype().getName().equals("form")) {
				Object obj = service.find(clazz, id, true);
				dataMap = metaDataManager.getEntityDataForEdit(obj, tableName);
				json = CoderForSave.encode(curColumns, dataMap, true);

			} else {
				json = null;
			}

		} else {
			Object object = this.initDraftObject(clazz);
			// dataMap = pageManager.getPagePanelDataForAdd(panelName);
			dataMap = metaDataManager.getEntityDataForEdit(object, tableName);
			json = CoderForSave.encode(curColumns, dataMap, true);
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
	 * ��ȡ������������Ԫ����
	 * 
	 * @Methods Name getModifyRequireFormPanel
	 * @Create In Mar 24, 2009 By lee
	 * @return String
	 */
	public String getModifyRequireFormPanel() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String json = "";
		String id = request.getParameter("id");
		String oldId = request.getParameter("oldId");
		String panelName = request.getParameter("panelname");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = panel.getSystemMainTable();
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		List<PagePanelColumn> pagePanelColumns = pageManager
				.getUserPagePanelColumn(panelName);
		List<PagePanelColumn> curColumns = new ArrayList<PagePanelColumn>();
		for (PagePanelColumn pagePanelColumn : pagePanelColumns) {
			String propertyName = pagePanelColumn.getColumn().getPropertyName();
			if (!propertyName.endsWith("$name")) {
				curColumns.add(pagePanelColumn);
			}
		}
		Map<String, Object> dataMap = null;
		if (StringUtils.isNotBlank(id)) {
			if (panel.getXtype().getName().equals("form")) {
				Object obj = service.find(clazz, id, true);
				dataMap = metaDataManager.getEntityDataForEdit(obj, tableName);
				json = CoderForSave.encode(curColumns, dataMap, true);

			} else {
				json = null;
			}

		} else {
			//add by lee for init userInfo draft and modifyEntityInfo information in 20090804 begin
			Object object = null;
			if(StringUtils.isNotBlank(oldId)){
				object = initModifyDraftObject(clazz,oldId);
			}else{
				object = this.initDraftObject(clazz);
			}
			dataMap = metaDataManager.getEntityDataForEdit(object, tableName);
			//add by lee for init userInfo draft and modifyEntityInfo information in 20090804 end
			//dataMap = pageManager.getPagePanelDataForAdd(panelName);//remove by lee for init draft userInfo in 20090804
			json = CoderForSave.encode(curColumns, dataMap, false);
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
	 * Ϊ��������������ز����ṩ����ݸ幦��
	 * 
	 * @Methods Name saveApplyDraft
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveApplyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String param = request.getParameter("info");
		String serviceItemId = request.getParameter("serviceItemId");
		String pagePanelName = request.getParameter("pagePanel");
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);

		Map map = getMapFormPanelJson(param);

		Date currentDate = DateUtil.getCurrentDate();
//		Map map = new HashMap();
//		Enumeration en = request.getParameterNames();// HttpUtil.requestParam2Map(request);
//		while (en.hasMoreElements()) {
//			String key = (String) en.nextElement();
//			if (key.equalsIgnoreCase("pagePanel")
//					|| key.equalsIgnoreCase("serviceItemId")) {
//				continue;
//			}
//			String value = request.getParameter(key);
//			key = StringUtils.substringAfter(key, "$");
//			value = value.trim();
//			map.put(key, value);
//		}
		if ("".equals(map.get("id"))) {
			map.put("serviceItem", Long.valueOf(serviceItemId));
			map.put("processType", ServiceItemProcess.PROCESS_TYPE_CREATE);
			map.put("status", Constants.STATUS_DRAFT);
			map.put("createDate", currentDate);
			map.put("createUser", UserContext.getUserInfo());
		}
		map.put("modifyDate", currentDate);
		map.put("modifyUser", UserContext.getUserInfo());
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,map);
		String id = object.getId().toString();

//		String name = (String) map.get("name");
		//add by lee for ����û�Ҫ�󷵻���Ϣ in 20091107 begin
		String applyNum = "";
		BeanWrapper bw = new BeanWrapperImpl(object);
		if(bw.isReadableProperty("applyNum")){
			applyNum = (String) bw.getPropertyValue("applyNum");
		}
		String applyDate = (String) map.get("applyDate");
		//add by lee for ����û�Ҫ�󷵻���Ϣ in 20091107 end
		
		String json = "{success:true,id:" + id +",applyNum:'"+applyNum+"',applyDate:'"+applyDate+"'}";
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
	 * ��ʼ����Ŀ�ƻ�
	 * @Methods Name initProjectPlan
	 * @Create In Aug 4, 2009 By lee
	 * @param name
	 * @param id void
	 */
	private void initProjectPlan(String name,String id){
		SRProjectPlan projectPlan = projectPlanService.findRootProjectPlanByReq(id);
		SpecialRequirement sr = (SpecialRequirement) getService().find(SpecialRequirement.class, id);
		if (projectPlan == null) {
			projectPlan = new SRProjectPlan();
		
			projectPlan.setPlanName(name);
			projectPlan.setSpecialRequire(sr);
			SRProjectPlan curPlan = (SRProjectPlan) getService().save(
					projectPlan);
			// �ٴ���3����
			SRProjectPlan projectPlan2 = new SRProjectPlan();
			projectPlan2.setPlanName("ʵʩ");
			projectPlan2.setSpecialRequire(sr);
			projectPlan2.setParentPlan(curPlan);
			getService().save(projectPlan2);
		
			SRProjectPlan projectPlan3 = new SRProjectPlan();
			projectPlan3.setPlanName("����");
			projectPlan3.setSpecialRequire(sr);
			projectPlan3.setParentPlan(curPlan);
			getService().save(projectPlan3);
		
			SRProjectPlan projectPlan4 = new SRProjectPlan();
			projectPlan4.setPlanName("����");
			projectPlan4.setSpecialRequire(sr);
			projectPlan4.setParentPlan(curPlan);
			getService().save(projectPlan4);
		} else {
			projectPlan.setPlanName(name);
			projectPlan.setSpecialRequire(sr);
			getService().save(projectPlan);
		}
	}
	/**
	 * Ϊ������������ز����ṩ����ݸ幦��
	 * 
	 * @Methods Name saveModifyDraft
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	public String saveModifyDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
//		String param = request.getParameter("info");
		String pagePanelName = request.getParameter("pagePanel");
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		// ServiceItem serviceItem =
		// serviceItemService.findServiceItemById(serviceItemId);
		// ServiceItemUserTable serviceItemUserTable =
		// serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
		// String className= serviceItemUserTable.getClassName();
		Class clazz = this.toClass(className);

		Date currentDate = DateUtil.getCurrentDate();
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();// HttpUtil.requestParam2Map(request);
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		if ("".equals(map.get("id"))) {
			map.put("processType", ServiceItemProcess.PROCESS_TYPE_MODIFY);
			map.put("status", Constants.STATUS_DRAFT);
			map.put("createDate", currentDate);
			map.put("createUser", UserContext.getUserInfo());
		}
		map.put("modifyDate", currentDate);
		map.put("modifyUser", UserContext.getUserInfo());
		// Integer processType = (Integer) map.get("processType");
		// ServiceItemProcess serviceItemProcess =
		// serviceItemProcessService.findProcessesByServiceItemAndType(serviceItem,
		// processType);
		// String pagePanelName = serviceItemProcess.getPagePanel().getName();
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,
				map);
		//modify by lee for ����û�Ҫ�󷵻���Ϣ in 20091112 begin
		String id = object.getId().toString();
		String name = (String) map.get("name");
		String applyNum = "";
		BeanWrapper bw = new BeanWrapperImpl(object);
		if(bw.isReadableProperty("requireId")){
			applyNum = (String) bw.getPropertyValue("requireId");
		}
		if(bw.isReadableProperty("applyNum")){
			applyNum = (String) bw.getPropertyValue("applyNum");
		}
		String applyDate = (String) map.get("applyDate");

		String json = "{success:true,id:" + id + ",reqName:'" + name + "',applyNum:'"+applyNum+"',applyDate:'"+applyDate+"'}";
		//modify by lee for ����û�Ҫ�󷵻���Ϣ in 20091112 end
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
	 * Ϊ������������ز����ṩ����ݸ幦��
	 * 
	 * @Methods Name saveConcelDraft
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	public String saveCancelDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
//		String param = request.getParameter("info");
		String pagePanelName = request.getParameter("pagePanel");
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);

		Date currentDate = DateUtil.getCurrentDate();
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();// HttpUtil.requestParam2Map(request);
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		String dataId = (String) map.get("id");
		if ("".equals(dataId)) {
			map.put("processType", Constants.RROCESS_TYPE_CANCLE);
			map.put("status", Constants.STATUS_DRAFT);
			map.put("createDate", currentDate);
			map.put("createUser", UserContext.getUserInfo());
		} else {
			String curProcessTypeStr = (String) map.get("processType");
			Integer curProcessType = Integer.valueOf(curProcessTypeStr);
			if (curProcessType != Constants.RROCESS_TYPE_CANCLE) {
				Object object = getService().find(clazz, dataId);
				map.put("oldApply", object);
				map.put("processType", Constants.RROCESS_TYPE_CANCLE);
				map.put("status", Constants.STATUS_DRAFT);
				map.put("createDate", currentDate);
				map.put("createUser", UserContext.getUserInfo());
				map.put("id", null);
			}
		}
		map.put("modifyDate", currentDate);
		map.put("modifyUser", UserContext.getUserInfo());
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,
				map);
		Long id = object.getId();
		String name = (String) map.get("name");
		String json = "{success:true,id:" + id + ",reqName:'" + name + "'}";
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
	 * ����ݸ�
	 * @Methods Name saveDraft
	 * @Create In Aug 4, 2009 By lee
	 * @return String
	 */
	public String saveDraft(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
//		String param = request.getParameter("info");	//��ȡ�������Ϣ
		String serviceItemId = request.getParameter("serviceItemId");//��ȡ������������Ϣ
		String pagePanelName = request.getParameter("pagePanel");	//��ȡ�������
		String processType = request.getParameter("processType");	//��ȡ�������ͣ�����0/���1/ɾ��2��
		String status = request.getParameter("status");				//��ȡ����״̬���ݸ�0/����1/����2��
//		String serviceItemProcessId = request.getParameter("serviceItemProcessId");	//��ȡ��Ӧ��������������ID
		
		//�������������ʵ��
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);
		Date currentDate = new Date();
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();// HttpUtil.requestParam2Map(request);
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")
				|| key.equalsIgnoreCase("serviceItemId")
				|| key.equalsIgnoreCase("processType")
				|| key.equalsIgnoreCase("serviceItemProcessId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		if ("".equals(map.get("id"))) {
			map.put("createDate", currentDate);
			map.put("createUser", UserContext.getUserInfo());
		}
		map.put("serviceItem", Long.valueOf(serviceItemId));
		map.put("processType", Integer.valueOf(processType));
		map.put("status", Integer.valueOf(status));
		map.put("modifyDate", currentDate);
		map.put("modifyUser", UserContext.getUserInfo());
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,map);
		String id = object.getId().toString();
		// *******���ɷ�����ʱͬʱ��������Ŀ�ƻ�
		String name = (String) map.get("name");
		if ("com.zsgj.itil.require.entity.SpecialRequirement".equals(className)){
			this.initProjectPlan(name,id);
		}
		String json = "{success:true,id:" + id + ",reqName:'" + name + "'}";
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
	 * Ϊ����������ز����ṩɾ���ݸ幦��
	 * 
	 * @Methods Name removeDraft
	 * @Create In Mar 4, 2009 By lee
	 * @return String
	 */
	public String removeDraft() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String objectId = request.getParameter("id");
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService
				.findServiceItemById(serviceItemId);
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService
				.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
		Class clazz = this.toClass(className);
		if (StringUtils.isNotBlank(objectId))
			requireSIService.removeHisByApply(className, objectId);//����ɾ��������ʷ
			super.getService().remove(clazz, objectId);
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
	 * ����Ϊ����ʵ�����ɴ��������ݲݸ�
	 * 
	 * @Methods Name initDraftObject
	 * @Create In Apr 3, 2009 By lee
	 * @param clazz
	 * @return Object
	 */
	private Object initDraftObject(Class clazz) {
		Object object = BeanUtils.instantiateClass(clazz);
		try {
			UserInfo curUser = UserContext.getUserInfo();
			Date curDate = DateUtil.getCurrentDate();
			Department dept = curUser.getDepartment();
			//modify by lee for add costCenter in 20090820 begin
			//String costCode = curUser.getPostCode();
			String costCode = curUser.getCostCenterCode();
			String tel = curUser.getTelephone();
			//modify by lee for add costCenter in 20090820 end
			RequirementLevel level = (RequirementLevel) service.findUnique(
					RequirementLevel.class, "name", "��ͨ");
			BeanWrapper bWrapper = new BeanWrapperImpl(object);
			bWrapper.setPropertyValue("applyUser", curUser);
			bWrapper.setPropertyValue("applyDate", curDate);
			bWrapper.setPropertyValue("applyDept", dept);
			
			//add by lee for ��Ӹ��Ի��ύʱ��д������Ϣģ�� in 20091112 begin
			if(bWrapper.isReadableProperty("descn")){
				bWrapper.setPropertyValue("descn", "<P>1����Ҫ�����ҵ�����⣺<BR><BR>2��ϣ�����մ�ɵ�Ŀ�꣺</P>");
			}
			//add by lee for ��Ӹ��Ի��ύʱ��д������Ϣģ�� in 20091112 end
			bWrapper.setPropertyValue("costConter", costCode);
			bWrapper.setPropertyValue("requireLevel", level);
			bWrapper.setPropertyValue("tel",tel);//add by lee for ��Ӵ����û��绰 in 20090820
		} catch (Exception e) {
			System.out.print(clazz + "���������������Զ������������ã�����Ϊ��ʵ������Ĭ������");
		}
		return object;
	}
	/**
	 * ����Ϊ����ʵ�����ɴ����ԭʵ�����ݲݸ�
	 * @Methods Name initModifyDraftObject
	 * @Create In Aug 4, 2009 By lee
	 * @param clazz
	 * @param oldId
	 * @return Object
	 */
	private Object initModifyDraftObject(Class clazz,String oldId) {
		Object oldObj = super.getService().find(clazz, oldId);
		
		Object object = BeanUtils.instantiateClass(clazz);
		try {
			BeanUtils.copyProperties(oldObj, object);
			UserInfo curUser = UserContext.getUserInfo();
			Date curDate = DateUtil.getCurrentDate();
			Department dept = curUser.getDepartment();
			
			//modify by lee for add costCenter in 20090820 begin
			//String costCode = curUser.getPostCode();
			String costCode = curUser.getCostCenterCode();
			String tel = curUser.getTelephone();
			//modify by lee for add costCenter in 20090820 end
			BeanWrapper bWrapper = new BeanWrapperImpl(object);
			bWrapper.setPropertyValue("id",null);
			bWrapper.setPropertyValue("processType",null);
			bWrapper.setPropertyValue("deleteFlag",null);
			bWrapper.setPropertyValue("oldApply",oldObj);
			bWrapper.setPropertyValue("status",null);
			bWrapper.setPropertyValue("costConter", costCode);
			bWrapper.setPropertyValue("tel",tel);//add by lee for ��Ӵ����û��绰 in 20090820
			bWrapper.setPropertyValue("applyNum",null);
			bWrapper.setPropertyValue("applyUser", curUser);
			bWrapper.setPropertyValue("applyDate", curDate);
			bWrapper.setPropertyValue("applyDept", dept);
			
		} catch (Exception e) {
			System.out.print(clazz + "���������������Զ������������ã�����Ϊ��ʵ������Ĭ������");
		}
		return object;
	}
	/**
	 * ��ʼ�����Ի����뼰�������
	 * 
	 * @Methods Name initNewApplyDate
	 * @Create In May 31, 2009 By gaowen
	 * @return String
	 */
	public String initspecialRequireData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String panelName = request.getParameter("panelName");
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);
		Object object = BeanUtils.instantiateClass(clazz);
		try {
			UserInfo curUser = UserContext.getUserInfo();
			Date curDate = DateUtil.getCurrentDate();
			String dept = curUser.getDepartment().getDepartName();
			//modify by lee for add costCenter in 20090820 begin
			//String costCode = curUser.getPostCode();
			String costCode = curUser.getCostCenterCode();
			String tel = curUser.getTelephone();
			//modify by lee for add costCenter in 20090820 end
			BeanWrapper bWrapper = new BeanWrapperImpl(object);

			bWrapper.setPropertyValue("applyUser", curUser);
			bWrapper.setPropertyValue("applyDate", curDate);
			bWrapper.setPropertyValue("applyDept", dept);
			bWrapper.setPropertyValue("costConter", costCode);
			bWrapper.setPropertyValue("tel",tel);//add by lee for ��Ӵ����û��绰 in 20090820
		} catch (Exception e) {
			System.out.print(clazz + "���˺��������������Զ������������ã�����Ϊ��ʵ������Ĭ������");
		}
		Map<String, Object> dataMap = metaDataManager.getFormDataForEdit(
				object, tableName);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:" + jsonObject.toString()
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
	 * ��ת�����̳�ʼЭ��ҳ��
	 * 
	 * @Methods Name toAgreementPage
	 * @Create In May 7, 2009 By lee
	 * @return String
	 */
	public String toAgreementPage() {
		
		HttpServletRequest request = super.getRequest();
		String processTypeStr = request.getParameter("processType");
		String serviceItemId = request.getParameter("serviceItemId");
		String dataId = request.getParameter("dataId");
		
//		UserInfo curUser = UserContext.getUserInfo();
//		if(processTypeStr.equals("1")){
//            if(!accountService.findAccount(serviceItemId, curUser)){
//        	return "noAccountPage";
//        }
//		}
		ServiceItem serviceItem = serviceItemService
				.findServiceItemById(serviceItemId);
		ServiceItemProcess serviceItemProcess = serviceItemProcessService
				.findProcessesByServiceItemAndType(serviceItem, Integer
						.valueOf(processTypeStr));
		
		if (serviceItemProcess.getAgreement() == null
				|| "".equals(serviceItemProcess.getAgreement())) {
			ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService
					.findServiceItemUserTableByServiceItem(serviceItem);
			String className = serviceItemUserTable.getClassName();
			Class clazz = this.toClass(className);
			Integer processType = serviceItemProcess.getSidProcessType();
			Integer status = Constants.STATUS_DRAFT;
			if (StringUtils.isNotBlank(dataId)) {
				Object object = getService().find(clazz, dataId, true);
				BeanWrapper bw = new BeanWrapperImpl(object);
				processType = (Integer) bw.getPropertyValue("processType");
				status = (Integer) bw.getPropertyValue("status");
			}
			forwardUrl = serviceItemProcess.getPageModel().getPagePath();
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
//			String virtualDesc = serviceItemProcess.getDefinitionName();
//			/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
//			VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
//					.findUnique(VirtualDefinitionInfo.class,
//							"virtualDefinitionDesc", virtualDesc);
			
			VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
			//modify by lee for change serviceItemProcess to ORM in 200090707 end
			String vname = virtualDefinitionInfo.getVirtualDefinitionName();
			String vdescription = virtualDefinitionInfo
					.getVirtualDefinitionDesc();
			// String pagePanelName =
			// serviceItemProcess.getPagePanel().getName();
			request.setAttribute("serviceItemId", serviceItemId);
			request.setAttribute("processType", processType);
			// request.setAttribute("pagePanel", pagePanelName);
			request.setAttribute("reqClass", className);
			request.setAttribute("processName", vname);
			request.setAttribute("description", vdescription);
			request.setAttribute("status", status);
			request.setAttribute("dataId", dataId);
			
			return "forward";
		} else {
			request.setAttribute("process", serviceItemProcess);
			return "agreement";
		}
	}

	/**
	 * ��ת���������ҳ��
	 * 
	 * @Methods Name toApplyPage
	 * @Create In Jun 10, 2009 By lee
	 * @return String
	 */
	public String toOperatePage() {
		HttpServletRequest request = super.getRequest();
		String processTypeStr = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		String serviceItemId = request.getParameter("serviceItemId");
		String id = request.getParameter("dataId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		ServiceItemProcess serviceItemProcess = null;
		if(StringUtils.isNotBlank(processInfoId)){
			serviceItemProcess = serviceItemProcessService.findServiceItemProcessById(processInfoId);
		}else if(StringUtils.isBlank(processInfoId)&&StringUtils.isNotBlank(processTypeStr)){
			serviceItemProcess = serviceItemProcessService.findProcessesByServiceItemAndType(serviceItem, Integer.valueOf(processTypeStr));
		}
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
		Class clazz = this.toClass(className);
		Integer processType = serviceItemProcess.getSidProcessType();
		Integer status = Constants.STATUS_DRAFT;
		if (StringUtils.isNotBlank(id)) {
			Object object = getService().find(clazz, id, true);
			BeanWrapper bw = new BeanWrapperImpl(object);
			processType = (Integer) bw.getPropertyValue("processType");
			status = (Integer) bw.getPropertyValue("status");
		}
		//add by lee for �������������� in 20100302 begin
		PageModel enterPage = serviceItemProcess.getPageModel();
		PagePanel enterPanel = serviceItemProcess.getPagePanel();
		if(enterPage!=null){
			forwardUrl = serviceItemProcess.getPageModel().getPagePath();
		}
		//add by lee for �������������� in 20100302 end
		//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
//		String virtualDesc = serviceItemProcess.getDefinitionName();
//		/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
//				.findUnique(VirtualDefinitionInfo.class,
//						"virtualDefinitionDesc", virtualDesc);
		
		VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
		//modify by lee for change serviceItemProcess to ORM in 200090707 end
		
		String vname = virtualDefinitionInfo.getVirtualDefinitionName();
		String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
		// String pagePanelName = serviceItemProcess.getPagePanel().getName();
		request.setAttribute("serviceItemId", serviceItemId);
		request.setAttribute("processType", processType);
		// request.setAttribute("pagePanel", pagePanelName);
		request.setAttribute("reqClass", className);
		request.setAttribute("processName", vname);
		request.setAttribute("description", vdescription);
		request.setAttribute("status", status);
		request.setAttribute("dataId", id);
		if(enterPage!=null){
			return "forward";
		}else if(enterPanel!=null){
			request.setAttribute("pagePanel", enterPanel.getName());
			return "operate";
		}else{
			request.setAttribute("errorMessage", "δ�ҵ�������ڣ�����ϵ����Ա��");
			return "error";
		}

	}
	
	
	/**
	 * ��ת���������ҳ�� �˺�����
	 * 
	 * @Methods Name toOperatePageAccount
	 * @Create In Aug 10, 2009 By gaowen
	 * @return String
	 */
	public String toOperatePageAccount() {
		HttpServletRequest request = super.getRequest();
//		String processTypeStr = request.getParameter("processType");
//		String processInfoId = request.getParameter("processInfoId");
		String serviceItemId = request.getParameter("serviceItemId");
		String id = request.getParameter("dataId");
		//String serviceItempro=request.getParameter("serviceItemProcess");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		ServiceItemProcess serviceItemProcess = null;//(ServiceItemProcess) getService().find(ServiceItemProcess.class, serviceItempro);
		ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
		String className = serviceItemUserTable.getClassName();
		Class clazz = this.toClass(className);
		Integer processType = null;//serviceItemProcess.getSidProcessType();
		Integer status = Constants.STATUS_DRAFT;
		if (StringUtils.isNotBlank(id)) {
			Object object = getService().find(clazz, id, true);
			BeanWrapper bw = new BeanWrapperImpl(object);
			processType = (Integer) bw.getPropertyValue("processType");
			status = (Integer) bw.getPropertyValue("status");
			serviceItemProcess = (ServiceItemProcess) bw.getPropertyValue("serviceItemProcess");
		}
		serviceItemProcess = (ServiceItemProcess) getService().find(ServiceItemProcess.class, serviceItemProcess.getId().toString());
		forwardUrl = serviceItemProcess.getPageModel().getPagePath();
		
		//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
//		String virtualDesc = serviceItemProcess.getDefinitionName();
//		/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
//				.findUnique(VirtualDefinitionInfo.class,
//						"virtualDefinitionDesc", virtualDesc);
		
		VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
		//modify by lee for change serviceItemProcess to ORM in 200090707 end
		
		String vname = virtualDefinitionInfo.getVirtualDefinitionName();
		String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
		// String pagePanelName = serviceItemProcess.getPagePanel().getName();
		request.setAttribute("serviceItemId", serviceItemId);
		request.setAttribute("processType", processType);
		request.setAttribute("serviceItemProcessId", serviceItemProcess.getId());
		// request.setAttribute("pagePanel", pagePanelName);
		request.setAttribute("reqClass", className);
		request.setAttribute("processName", vname);
		request.setAttribute("description", vdescription);
		request.setAttribute("status", status);
		request.setAttribute("dataId", id);
		return "forward";

	}
	
	/**
	 * ��ת���������ҳ��
	 * @Methods Name toProcessEnterPage
	 * @Create In Jun 30, 2009 By lee
	 * @return String
	 */
	public String toProcessEnterPage() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("dataId");
		String oldId = request.getParameter("oldId");//add by lee for modifyPage in 20090804
		String serviceItemProcessId = request.getParameter("serviceItemProcessId");
		String toPage = request.getParameter("toPage");
		ServiceItemProcess serviceItemProcess = serviceItemProcessService.findServiceItemProcessById(serviceItemProcessId);
		//add by lee for ����û�Э�� in 20091203 begin
		if (serviceItemProcess.getAgreement() == null
				|| "".equals(serviceItemProcess.getAgreement())
				|| "yes".equals(toPage)) {
		//add by lee for ����û�Э�� in 20091203 end
			ServiceItem serviceItem = serviceItemProcess.getServiceItem();
			String serviceItemId = serviceItem.getId().toString();
//			String processTypeStr = serviceItemProcess.getSidProcessType().toString();
	
			ServiceItemUserTable serviceItemUserTable = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
			String className = serviceItemUserTable.getClassName();
			Class clazz = this.toClass(className);
			Integer processType = serviceItemProcess.getSidProcessType();
			Integer status = Constants.STATUS_DRAFT;
			if (StringUtils.isNotBlank(id)) {
				Object object = getService().find(clazz, id, true);
				BeanWrapper bw = new BeanWrapperImpl(object);
				status = (Integer) bw.getPropertyValue("status");
			}
			//modify by lee for �������������� in 200090714 begin 
			PageModel enterPage = serviceItemProcess.getPageModel();
			PagePanel enterPanel = serviceItemProcess.getPagePanel();
			if(enterPage!=null){
				forwardUrl = serviceItemProcess.getPageModel().getPagePath();
			}
			//modify by lee for �������������� in 200090714 end 
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
	//		String virtualDesc = serviceItemProcess.getDefinitionName();
	//		/** *************�����������������������ظ�����ǰ̨����У�飩�õ���Ӧ����������******************** */
	//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) getService()
	//				.findUnique(VirtualDefinitionInfo.class,
	//						"virtualDefinitionDesc", virtualDesc);
			
			VirtualDefinitionInfo virtualDefinitionInfo = serviceItemProcess.getProcessInfo();
			//modify by lee for change serviceItemProcess to ORM in 200090707 end
			String vname = virtualDefinitionInfo.getVirtualDefinitionName();
			String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
			request.setAttribute("serviceItemId", serviceItemId);
			request.setAttribute("serviceItemProcessId",serviceItemProcessId);//add by lee for ͬ���Ͷ����̴��� in 20090728
			request.setAttribute("processType", processType);
			request.setAttribute("reqClass", className);
			request.setAttribute("processName", vname);
			request.setAttribute("description", vdescription);
			request.setAttribute("status", status);
			request.setAttribute("dataId", id);
			request.setAttribute("serviceItemName", serviceItem.getName());
			//add by lee for modifyPage in 20090804 begin
			if(StringUtils.isNotBlank(oldId)){
				request.setAttribute("oldId", oldId);
			}
			//add by lee for modifyPage in 20090804 end
			//modify by lee for �������������� in 200090714 begin 
			if(enterPage!=null){
				return "forward";
			}else if(enterPanel!=null){
				request.setAttribute("pagePanel", enterPanel.getName());
				return "operate";
			}else{
				request.setAttribute("errorMessage", "δ�ҵ�������ڣ�����ϵ����Ա��");
				return "error";
			}
		//add by lee for ����û�Э�� in 20091203 begin
		}else {
			request.setAttribute("process", serviceItemProcess);
			return "agreement";
		}
		//add by lee for ����û�Э�� in 20091203 end
		//modify by lee for �������������� in 200090714 end 
	}
	/**
	 * ��ת�����̽�����鿴ҳ��
	 * @Methods Name toProcessEndPage
	 * @Create In Jun 30, 2009 By lee
	 * @return String
	 */
	public String toProcessEndPage() {
		HttpServletRequest request = super.getRequest();
//		String id = request.getParameter("dataId");
		String serviceItemId = request.getParameter("serviceItemId");
		String processType = request.getParameter("processType");
		String processInfoId = request.getParameter("processInfoId");
		
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		ServiceItemProcess serviceItemProcess = null;
		if(StringUtils.isNotBlank(processInfoId)){
			serviceItemProcess = serviceItemProcessService.findServiceItemProcessById(processInfoId);
		}else if(StringUtils.isBlank(processInfoId)&&StringUtils.isNotBlank(processType)){
			serviceItemProcess = serviceItemProcessService.findProcessesByServiceItemAndType(serviceItem, Integer.valueOf(processType));
		}
		if(serviceItemProcess!=null){
			PageModel endPageModel = serviceItemProcess.getEndPageModel();
			PagePanel panel = serviceItemProcess.getPagePanel();
			if(endPageModel!=null){
				forwardUrl = endPageModel.getPagePath();
				return "forward";
			}else if(panel!=null){
				request.setAttribute("pagePanel", panel.getName());
				return "operate";
			}else{
				request.setAttribute("errorMessage", "δ�ҵ�����ҳ�棬����ϵ����Ա��");
				return "error";
			}
		}else{
			request.setAttribute("errorMessage", "δ�ҵ�����ҳ�棬����ϵ����Ա��");
			return "error";
		}
	}
	
	
	/**
	 * ��ת�����̽�����鿴ҳ��
	 * @Methods Name toProcessEndPageAccount
	 * @Create In Sep 9, 2009 By gaowen
	 * @return String
	 */
	public String toProcessEndPageAccount() {
		HttpServletRequest request = super.getRequest();
//		String processTypeStr = request.getParameter("processType");
//		String serviceItemId = request.getParameter("serviceItemId");
//		String id = request.getParameter("dataId");
		String serviceItempro=request.getParameter("serviceItemProcess");
//		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		ServiceItemProcess serviceItemProcess = (ServiceItemProcess) getService().find(ServiceItemProcess.class, serviceItempro);
		if(serviceItemProcess!=null){
			PageModel endPageModel = serviceItemProcess.getEndPageModel();
			PagePanel panel = serviceItemProcess.getPagePanel();
			if(endPageModel!=null){
				forwardUrl = endPageModel.getPagePath();
//				request.setAttribute("forwardUrl", forwardUrl);
				return "forward";
			}else if(panel!=null){
				request.setAttribute("pagePanel", panel.getName());
				return "operate";
			}else{
				request.setAttribute("errorMessage", "δ�ҵ�����ҳ�棬����ϵ����Ա��");
				return "error";
			}
		}else{
			request.setAttribute("errorMessage", "δ�ҵ�����ҳ�棬����ϵ����Ա��");
			return "error";
		}
	}	
	
	
	/**
	 * ��ȡERP����Ĭ�Ͻڵ��������б�
	 * 
	 * @Methods Name listRequireAudit
	 * @Create In Jun 2, 2009 By lee
	 * @return String
	 */
	public String listRequireAudit() {
		int pageSize = 10;//ÿҳ����
		HttpServletRequest request = super.getRequest();//�õ�request
		String paramName = request.getParameter("start");//�õ�start���������Ǹ�ʲô�õģ��ӵڼ�ҳ��ʼ��
		String departmentName = request.getParameter("departmentName");//�õ���������
		String auditUser = request.getParameter("auditUser");//�õ���������Ϣ
		int start = this.confirmPageNo(paramName, 1);//���õ�ǰҳ��
		int pageNo=start/pageSize+1;
		UserInfo audit = null;//����һ��UserInfo
		if (StringUtils.isNotBlank(auditUser)) {
			audit = (UserInfo) service.find(UserInfo.class, auditUser);//���auditUser isNotBlank ���õ���UserInfo
		}

		Page page = requireService.findAuditsByPage(departmentName, audit,
				pageNo, pageSize);
		List<RequireApplyDefaultAudit> list = page.list();

		Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
		String json = "";
		for (RequireApplyDefaultAudit tempAudit : list) {
			Long tempId = tempAudit.getId();
			String tempDepartmentName = tempAudit.getDepartmentName();
			UserInfo cadreBizAudit = tempAudit.getCadreBizAudit();
			UserInfo cadreFinanceAudit = tempAudit.getCadreFinanceAudit();
			UserInfo groupFinanceAudit = tempAudit.getGroupFinanceAudit();
			UserInfo cadreBusinessAudit = tempAudit.getCadreBusinessAudit(); // add by zhangzy for ���ӱ�������������  in 2009 11 20 
			UserInfo clientItManager = tempAudit.getClientItManager(); // add by zhangzy for �ͻ�IT����  in 2009 11 25
			Integer deleteFlagStr = tempAudit.getDeleteFlag();
			Integer sortNumStr = tempAudit.getSortNum();
			Integer enableStr = tempAudit.getEnable();
			
			String cadreBizAuditStr = "--";
			if (cadreBizAudit != null) {
				cadreBizAuditStr = cadreBizAudit.getRealName();
			}
			String cadreFinanceAuditStr = "--";
			if (cadreFinanceAudit != null) {
				cadreFinanceAuditStr = cadreFinanceAudit.getRealName();
			}
			String groupFinanceAuditStr = "--";
			if (groupFinanceAudit != null) {
				groupFinanceAuditStr = groupFinanceAudit.getRealName();
			}
			String cadreBusinessAuditStr = "--";
			if (cadreBusinessAudit != null) {
				cadreBusinessAuditStr = cadreBusinessAudit.getRealName();
			}
			String clientItManagerStr = "--";
			if (clientItManager != null) {
				clientItManagerStr = clientItManager.getRealName();
			}
			json += "{\"id\":\"" + tempId + "\",\"departmentName\":\""
					+ tempDepartmentName + "\",\"cadreBizAudit\":\""
					+ cadreBizAuditStr + "\",\"cadreFinanceAudit\":\""
					+ cadreFinanceAuditStr + "\",\"groupFinanceAudit\":\""
					+ groupFinanceAuditStr + "\",\"cadreBusinessAudit\":\""
					+ cadreBusinessAuditStr+ "\",\"clientItManager\":\""
					+ clientItManagerStr+ "\",\"deleteFlag\":\""
					+ deleteFlagStr+ "\",\"sortNum\":\""
					+ sortNumStr+ "\",\"enable\":\""
					+ enableStr + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		//System.out.println("�����û�ʱ,����ǰ̨�Ĳ������ݣ� " + json);
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
	 * ��ȡ������ʷҳ��
	 * @Methods Name toHisPage
	 * @Create In Aug 30, 2009 By lee
	 * @return String
	 */
	public String toHisPage(){

		HttpServletRequest request = super.getRequest();
//		HttpServletResponse response = super.getResponse();
		String reqId = request.getParameter("dataId");	//��ȡ������ʵ��ID
		String siId = request.getParameter("serviceItemId");		//��ȡ��������������ID
		UserInfo curUser = UserContext.getUserInfo();	//��ȡ��ǰ�û�
		ServiceItem si = (ServiceItem) this.getService().find(ServiceItem.class, siId);
		ServiceItemUserTable userTable = (ServiceItemUserTable) this.getService().findUnique(ServiceItemUserTable.class, "serviceItem", si);
		String reqClass = userTable.getClassName();
		Object obj = this.getService().find(this.toClass(reqClass), reqId);
		BeanWrapper bw = new BeanWrapperImpl(obj);
		Integer processType = (Integer) bw.getPropertyValue("processType");
		ServiceItemProcess process = serviceItemProcessService.findProcessesByServiceItemAndType(si, processType);
		String processId = process.getProcessInfo().getId().toString();//�������̺ţ�Ҳ���ǵ�ǰ�����е�����
		ServiceItemApplyAuditHis his = requireSIService.findLastHis(reqId, siId, curUser);
		
		String realStatus = bw.getPropertyValue("status").toString();	
		if(realStatus.equals("2")){//������ݿ���statusֵΪ��2�����������̽���״̬��������ת��endҳ��	
			
		//add by zhangzy for ����������������롱��������� in 2009 11 25 start				
			ServiceItemProcess serviceItemProcess = (ServiceItemProcess) getService().find(ServiceItemProcess.class, process.getId().toString());
			if(serviceItemProcess!=null){
				PageModel endPageModel = serviceItemProcess.getEndPageModel();
				PagePanel panel = serviceItemProcess.getPagePanel();
				if(endPageModel!=null){
					forwardUrl = endPageModel.getPagePath();
					return "forward";
				}else if(panel!=null){
					request.setAttribute("pagePanel", panel.getName());
					return "operate";
				}else{
					request.setAttribute("errorMessage", "δ�ҵ�����ҳ�棬����ϵ����Ա��");
					return "error";
				}
			}else{
				request.setAttribute("errorMessage", "δ�ҵ�����ҳ�棬����ϵ����Ա��");
				return "error";
			}
		//add by zhangzy for ����������������롱��������� in 2009 11 25 end	
			
		}else{//������ݿ���statusֵ��Ϊ��2�������������̽���״̬��
			
			if(his!=null){
				String nodeId = his.getNodeId();	//��ȡ�ڵ�ID
				String pageModelName = requireSIService.getPageModelNameByNode(processId.toString(), nodeId);
				PageModel pm = (PageModel) this.getService().findUnique(PageModel.class, "name", pageModelName);
				if(pm==null){
					PageModel enterPage = process.getPageModel();
					PagePanel enterPanel = process.getPagePanel();
					if(enterPage!=null){
						forwardUrl = process.getPageModel().getPagePath();
					}
					VirtualDefinitionInfo virtualDefinitionInfo = process.getProcessInfo();
					String vname = virtualDefinitionInfo.getVirtualDefinitionName();
					String vdescription = virtualDefinitionInfo.getVirtualDefinitionDesc();
					request.setAttribute("serviceItemId", siId);
					request.setAttribute("serviceItemProcessId",process.getId());
					request.setAttribute("processType", processType);
					request.setAttribute("reqClass", reqClass);
					request.setAttribute("processName", vname);
					request.setAttribute("description", vdescription);
					request.setAttribute("status", 1);//״̬����Ϊ������״̬
					request.setAttribute("dataId", reqId);
					if(enterPage!=null){
						return "forward";
					}else if(enterPanel!=null){
						request.setAttribute("pagePanel", enterPanel.getName());
						return "operate";
					}
				}else{
					forwardUrl = pm.getPagePath()+"?dataId="+reqId+"&readOnly=1";
					return "forward";
				}
			}else{
				return "error";
			}
			
		}
		
		return "error";
	}
    /**
     * ģ����ѯ��������
	 * @Methods Name findRequire
	 * @Create In Nov 9, 2009 By duxh
     */
	public String findRequireByName(){
		try {
			HttpServletRequest request = super.getRequest();
//			String requireId = request.getParameter("require");
			String name = request.getParameter("name");
			int pageSize = HttpUtil.getInt(super.getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(super.getRequest(), "start", 0);
			int pageNo = start/pageSize +1;
			Page page=requireService.findRequireByName(name, pageNo, pageSize);
			List<SpecialRequirement> specialRequirement=page.list();
			StringBuilder json = new StringBuilder("{success:true,rowCount:" + page.getTotalCount() + ",data:[");
			for (int i = 0; i < specialRequirement.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + specialRequirement.get(i).getId() + "',");
				json.append("name:'" + specialRequirement.get(i).getName()+"'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("UTF-8");
			PrintWriter out =getResponse().getWriter();
			out.println(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException();
		}
	}
	/**
	 * ͨ��"����SBU/���� "��ȡIT��������Ϣ
	 * 
	 * @Methods Name selectConfirmUserByFlat
	 * @Create In 12 1, 2009 By zhangzy
	 * @param void
	 * @return void
	 */
	public void selectConfirmUserByFlat(){
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String flatId = request.getParameter("flatId").trim();
			if(flatId == null || flatId == "" || flatId.length() == 0){ 
				
			}else{
			RequireApplyDefaultAudit rada = (RequireApplyDefaultAudit) service.find(RequireApplyDefaultAudit.class, flatId);
			UserInfo auditUser = rada.getCadreBizAudit();
			UserInfo cadreFinanceAudit = rada.getCadreFinanceAudit();
			UserInfo groupFinanceAudit = rada.getGroupFinanceAudit();
			UserInfo cadreBusinessAudit = rada.getCadreBusinessAudit();
			UserInfo clientItManager = rada.getClientItManager();
			
			Long auditUserId = 0L;
			Long cadreFinanceAuditId = 0L;
			Long groupFinanceAuditId = 0L;
			Long cadreBusinessAuditId = 0L;
			Long clientItManagerId = 0L;
			
			if(auditUser != null){
				auditUserId = auditUser.getId(); 
			}
			if(cadreFinanceAudit != null){
				cadreFinanceAuditId = cadreFinanceAudit.getId(); 
			}
			if(groupFinanceAudit != null){
				groupFinanceAuditId = groupFinanceAudit.getId(); 
			}
			if(cadreBusinessAudit != null){
				cadreBusinessAuditId = cadreBusinessAudit.getId(); 
			}
			if(clientItManager != null){
				clientItManagerId = clientItManager.getId(); 
			}			
			String json="{success:true,auditUserId:"+auditUserId+
						",cadreFinanceAuditId:"+cadreFinanceAuditId+
						",groupFinanceAuditId:"+groupFinanceAuditId+
						",cadreBusinessAuditId:"+cadreBusinessAuditId+
						",clientItManagerId:"+clientItManagerId+
						"}";
			  try {
					response.setCharacterEncoding("utf-8");
					PrintWriter pw = response.getWriter();
					pw.write(json);
					pw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	}
	/**
	 * ����˽ڵ������
	 * 
	 * @Methods Name saveLockUser
	 * @Create In 01 21, 2010 By zhangzy
	 * @param void
	 * @return void
	 */
	public void saveLockUser(){
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			UserInfo curUserInfo = UserContext.getUserInfo();	//��ȡ��ǰ�û�
			String taskId = request.getParameter("taskId").trim();
			if(taskId != null && taskId != "" && taskId.length() != 0){ 
				ProcessLockInfo pli = (ProcessLockInfo) service.findUnique(ProcessLockInfo.class, "taskId", Long.parseLong(taskId));
				if(pli==null){
					WorkflowRecordTaskInfo wrti = (WorkflowRecordTaskInfo) service.findUnique(WorkflowRecordTaskInfo.class, "taskId", Long.parseLong(taskId));
					Long processId = wrti.getProcessInstanceId();
					Long nodeId = wrti.getNodeId();
//					String strAuditUserInfos = wrti.getAuditUserInfos();//��ȡWorkflowRecordTaskInfo���д������һ������������(���ڱȽϵ�ǰ�û��Ƿ����������е�һ������ʱδʵ��)
					ProcessLockInfo plif = new ProcessLockInfo();
					plif.setProcessId(processId);
					plif.setNodeId(nodeId);
					plif.setTaskId(Long.parseLong(taskId));
					plif.setLockUser(curUserInfo);
					
					service.save(plif);			
					
					String json="{success:true,curUserInfoId:"+curUserInfo.getId()+
								",curUserName:"+curUserInfo.getUserName()+
								"}";
					  try {
							response.setCharacterEncoding("utf-8");
							PrintWriter pw = response.getWriter();
							pw.write(json);
							pw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}				
				}				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	}		
	/**
	 * ��֤�˽ڵ�������ǲ��ǵ�ǰ�û�
	 * 
	 * @Methods Name confirmLockUser
	 * @Create In 01 21, 2010 By zhangzy
	 * @param void
	 * @return void
	 */
	public void confirmLockUser(){
		try {
			boolean isLockUser=false;
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			UserInfo curUserInfo = UserContext.getUserInfo();	//��ȡ��ǰ�û�
			String taskId = request.getParameter("taskId").trim();
			if(taskId != null && taskId != "" && taskId.length() != 0){ 

				ProcessLockInfo pli = (ProcessLockInfo) service.findUnique(ProcessLockInfo.class, "taskId", Long.parseLong(taskId));
				if(pli==null){					
					return ;
				}
				Long tempId = pli.getId();
//				Long tempTaskId = pli.getTaskId();
				if(tempId==null){					
					return ;
				}
				UserInfo lockUser = pli.getLockUser();				
				Long lockUserId = 0L;
				Long curUserInfoId = 0L;
				String lockUserName = lockUser.getUserName();
				
				if(lockUser != null){
					lockUserId = lockUser.getId(); 
				}
				if(curUserInfo != null){
					curUserInfoId = curUserInfo.getId(); 
				}
				if(lockUserId.equals(curUserInfoId)){
					isLockUser=true;
				}
				String json="{success:true,lockUserId:"+lockUserId+
							",curUserInfoId:"+curUserInfoId+
							",isLockUser:"+isLockUser+
							",lockUserName:'"+lockUserName+
							"'}";
				  try {
						response.setCharacterEncoding("utf-8");
						PrintWriter pw = response.getWriter();
						pw.write(json);
						pw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	}	
	/**
	 * �鿴�����̵Ĳ��������Ϣ
	 * 
	 * @Methods Name selectFinanceType
	 * @Create In 01 28, 2010 By zhangzy
	 * @param void
	 * @return void
	 */
	public void selectFinanceType(){
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String dataId = request.getParameter("dataId").trim();
			String strFinanceType =null;
			String strBatchType =null;
			if(dataId != null && dataId != "" && dataId.length() != 0){ 

				SpecialRequirement sr = (SpecialRequirement) service.findUnique(SpecialRequirement.class, "id", Long.parseLong(dataId));
				if(sr==null){					
					return ;
				}
				if(sr.getFinanceType()!=null){
					strFinanceType = sr.getFinanceType().getId()+"";
				} 
				if(sr.getBatchType()!=null){
					strBatchType = sr.getBatchType().getId()+"";
				}

				String json="{success:true,financeType:'"+strFinanceType+
							"',batchType:'"+strBatchType+
							"'}";
				  try {
						response.setCharacterEncoding("utf-8");
						PrintWriter pw = response.getWriter();
						pw.write(json);
						pw.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	}	
	
	/**
	 * ͨ��"Ӧ��ϵͳ"��ȡӦ�ù���Ա��Ϣ
	 * 
	 * @Methods Name selectAppManagerByAppConfigItem
	 * @Create In 12 1, 2009 By zhangzy
	 * @param void
	 * @return void
	 */
	public void selectAppManagerByAppConfigItem(){
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			String appConfigItemId = request.getParameter("appConfigItemId").trim();
			if(appConfigItemId == null || appConfigItemId == "" || appConfigItemId.length() == 0){  
				
			}else{
			UserInfo appManager = null;	
			ConfigItem ci = (ConfigItem) service.find(ConfigItem.class, appConfigItemId);
			RequireAppSystem ras = (RequireAppSystem) service.findUnique(RequireAppSystem.class, "appConfigItem", ci);
				if(ras != null){
					appManager = ras.getAppManager();
				}			
			Long appManagerId = 0L;			
			if(appManager != null){
				appManagerId = appManager.getId(); 
			}		
			String json="{success:true,appManagerId:"+appManagerId+
						"}";
			  try {
					response.setCharacterEncoding("utf-8");
					PrintWriter pw = response.getWriter();
					pw.write(json);
					pw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			throw new ApplicationException();
		}
		
	} 
	/**
	 * ���������Ӧ��ϵͳ���ƺ͹���Ա��������Ӧ��ϵͳ
	 * @return null
	 */
	public String listRequireApp() {
		int pageSize = 10;//ÿҳ����
		HttpServletRequest request = super.getRequest();//�õ�request
		String paramName = request.getParameter("start");//�õ�start���������Ǹ�ʲô�õģ��ӵڼ�ҳ��ʼ��
		String appId = request.getParameter("appConfigItem");//�õ���������
		String managerUser = request.getParameter("appManager");//�õ���������Ϣ
		int start = this.confirmPageNo(paramName, 0);//���õ�ǰҳ��
		int pageNo=start/pageSize+1;
		UserInfo manager = null;//����һ��UserInfo
		if (StringUtils.isNotBlank(managerUser)) {
			manager = (UserInfo) service.find(UserInfo.class, managerUser);//���auditUser isNotBlank ���õ���UserInfo
		}
		ConfigItem appConfigItem = null;
		if (StringUtils.isNotBlank(appId)){
			appConfigItem = (ConfigItem)service.find(ConfigItem.class, appId);
		}

		Page page = requireService.findAppByPage(appConfigItem, manager, pageNo, pageSize);
		
		List<RequireAppSystem> list = page.list();
		
		Long total = page.getTotalCount();// ���ǲ�ѯ�����еļ�¼
		
		String json = "";
		//System.out.println("list.size:"+list.size());
		for (RequireAppSystem tempApp : list) {
			Long tempId = tempApp.getId();
			
//			String appTemp = tempApp.getAppConfigItem().getName();
			ConfigItem appTemp = tempApp.getAppConfigItem();
			//System.out.println(appTemp);
			UserInfo appManagerTemp = tempApp.getAppManager();
			DeliveryTeam deliveryTeam = tempApp.getDeliveryTeam();
			ServiceEngineer serviceEngineer = tempApp.getEngineer();
			String appConfigItemTempStr = "--";
			if(appTemp!=null){
				appConfigItemTempStr = appTemp.getName();
			}
			String appManagerTempStr = "--";
			if (appManagerTemp != null) {
				appManagerTempStr = appManagerTemp.getRealName();
			}
			String serviceProviderInTempStr = "--";
			if (deliveryTeam != null) {
				serviceProviderInTempStr = deliveryTeam.getName();
			}
			String engineerTempStr = "--";
			if (serviceEngineer != null) {
				engineerTempStr = serviceEngineer.getUserInfo().getRealName();
			}
			
			json += "{\"id\":\"" + tempId + "\",\"appConfigItem\":\""
					+ appConfigItemTempStr + "\",\"appManager\":\""
					+ appManagerTempStr + "\",\"serviceProviderIn\":\""
					+ serviceProviderInTempStr + "\",\"engineer\":\""
					+ engineerTempStr + "\"},";
		}
		if (json.length() == 0) {
			json = "{success:true,rowCount:" + "1" + ",data:["
					+ json.substring(0, json.length()) + "]}";
		} else {
			json = "{success:true,rowCount:" + total + ",data:["
					+ json.substring(0, json.length() - 1) + "]}";
		}

		//System.out.println("�����û�ʱ,����ǰ̨�Ĳ������ݣ� " + json);
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
	 * ͨ��������ȡ��
	 * 
	 * @Methods Name toClass
	 * @Create In Mar 4, 2009 By lee
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

	public int confirmPageNo(String paramName, int size) {

		if (paramName == null || paramName.equals("")) {
			return size;
		}
		return Integer.parseInt(paramName);
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String removeRequireAudit(){
		try{
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			
			String id = request.getParameter("id");
			String flag = requireService.removeRequireAudit(id);
			StringBuilder json = new StringBuilder("{success:true,flag:'" + flag );
			json.append("'}");
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter pw = response.getWriter();
				pw.write(json.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;	
}
	   /**
	 * ��ȡERP���ڱ��
	 * @Methods Name getErpAccountId
	 * @Create In 03 23, 2010 By zhangzy
	 * @param 
	 * @param 
	 * @return Map
	 */
	public void getErpAccountId(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
//		String weightAccount=  request.getParameter("weightAccount");
		String otherInfo=  request.getParameter("otherInfo");
		String transferRequestNumber=  request.getParameter("transferRequestNumber");
		String subOtherInfo="";
		if(!("".equals(otherInfo))){
			if(otherInfo.length()>30)
				subOtherInfo = otherInfo.substring(0, 30);
			else
				subOtherInfo = otherInfo;
		}
		List resultList = SAPExecute.getErpAccountId(dataId, subOtherInfo, otherInfo, transferRequestNumber);
		
		
		StringBuilder json = new StringBuilder("[");

		for(int i = 0;i<resultList.size();i++){
			Map map = (Map) resultList.get(i);
			if(i==0){
				json.append("{");
				json.append("SUBRC:'"+map.get("SUBRC")+"',");
				json.append("erpAccountId:'"+map.get("erpAccountId")+"'");
				json.append("}");
			}else{
				json.append(",");			
				json.append("{");
				json.append("ARBGB:'"+map.get("ARBGB")+"',");
				json.append("MSGNR:'"+map.get("MSGNR")+"',");
				json.append("TEXT:'"+map.get("TEXT")+"'");
				json.append("}");
			}
		}
		json.append("]");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * �����������ʵ�����ݺ͹�����ϸ����
	 * 
	 * @Methods Name saveApplyDraftAndGrid
	 * @Create In 03 25, 2010 By zhangzy
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveApplyDraftAndGrid() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("serviceItemId");
		String pagePanelName = request.getParameter("pagePanel");
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);

		Date currentDate = DateUtil.getCurrentDate();
		Map map = new HashMap();
		Enumeration en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			if (key.equalsIgnoreCase("pagePanel")
					|| key.equalsIgnoreCase("serviceItemId")) {
				continue;
			}
			String value = request.getParameter(key);
			key = StringUtils.substringAfter(key, "$");
			value = value.trim();
			map.put(key, value);
		}
		if ("".equals(map.get("id"))) {
			map.put("serviceItem", Long.valueOf(serviceItemId));
			map.put("processType", ServiceItemProcess.PROCESS_TYPE_CREATE);
			map.put("status", Constants.STATUS_DRAFT);
			map.put("createDate", currentDate);
			map.put("createUser", UserContext.getUserInfo());
		}
		map.put("modifyDate", currentDate);
		map.put("modifyUser", UserContext.getUserInfo());
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,map);
		String id = object.getId().toString();

		String name = (String) map.get("name");
		String applyNum = "";
		BeanWrapper bw = new BeanWrapperImpl(object);
		if(bw.isReadableProperty("applyNum")){
			applyNum = (String) bw.getPropertyValue("applyNum");
		}
		String applyDate = (String) map.get("applyDate");
		String product = request.getParameter("product");
		String tempProduct = "";
		if (!"".equals(product) ) {
			byte[] bt1;
			try {
				bt1 = product.getBytes("ISO8859_1");
				tempProduct = new String(bt1,"GBK");//��GBK���н��룬����1���µ��ַ���  
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//��ISO8859_1���б���			
		}
		ERP_NormalNeed erp = (ERP_NormalNeed) getService().find(ERP_NormalNeed.class, id);
		
		JSONArray ja = JSONArray.fromObject("[" + tempProduct + "]");
		
//		RequireFactoryInfo rfi = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);

//				if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
//					rfi = (RequireFactoryInfo) super.getService().find(RequireFactoryInfo.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				productMap.put(key, value);
			}
			productMap.put("requireData",erp);
			metaDataManager.saveEntityData(RequireFactoryInfo.class, productMap);
		}		
		String json = "{success:true,id:" + id + ",reqName:'" + name + "',applyNum:'"+applyNum+"',applyDate:'"+applyDate+"'}";
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
	 * �������빤����ϸ����
	 * 
	 * @Methods Name saveGridInfo
	 * @Create In 03 25, 2010 By zhangzy
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveGridInfo() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String product = request.getParameter("product");
//		String tempProduct = "";
//		if (!"".equals(product) ) {
//			byte[] bt1;
//			try {
//				bt1 = product.getBytes("ISO8859_1");
//				tempProduct = new String(bt1,"GBK");//��GBK���н��룬����1���µ��ַ���  
//			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}//��ISO8859_1���б���			
//		}		
		String id = request.getParameter("dataId");
		ERP_NormalNeed erp = (ERP_NormalNeed) getService().find(ERP_NormalNeed.class, id);
//		product = HttpUtil.ConverUnicode(product);
		JSONArray ja = JSONArray.fromObject("[" + product + "]");
//		RequireFactoryInfo rfi = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);

//				if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
//					rfi = (RequireFactoryInfo) super.getService().find(RequireFactoryInfo.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				productMap.put(key, value);

			}

			productMap.put("requireData",erp);
			metaDataManager.saveEntityData(RequireFactoryInfo.class, productMap);
		}		
		
		
		String json = "{success:true,id:" + id +"'}";
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
	 * �������빤����ϸ��Ϣ
	 * @Methods Name saveRequireFactoryInfo
	 * @Create In 03 23, 2010 By zhangzy
	 * @param 
	 * @param 
	 * @return Map
	 */
	public void saveRequireFactoryInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String dataId = request.getParameter("dataId");
		String transferRequestNumber=  request.getParameter("transferRequestNumber");		
		String strProductId = request.getParameter("productIds");
		String[] productIds = StringUtils.split(strProductId,",");
	
		List resultList = SAPExecute.saveRequireFactoryInfo(dataId, productIds, transferRequestNumber);
		
		StringBuilder json = new StringBuilder("[");

		for(int i = 0;i<resultList.size();i++){
			Map map = (Map) resultList.get(i);
			if(i==0){
				json.append("{");
				json.append("SUBRC:'"+map.get("SUBRC")+"'");
				json.append("}");
			}else{
				json.append(",");			
				json.append("{");
				json.append("ARBGB:'"+map.get("ARBGB")+"',");
				json.append("MSGNR:'"+map.get("MSGNR")+"',");
				json.append("TEXT:'"+map.get("TEXT")+"'");
				json.append("}");
			}
		}
		json.append("]");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	/**
	 * �����������ʵ�����ݺʹ�����������ϸ����
	 * 
	 * @Methods Name saveApplyDraftAndGrid
	 * @Create In 03 25, 2010 By zhangzy
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String saveApplyDraftAndBigGrid() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("serviceItemId");
		String pagePanelName = request.getParameter("pagePanel");
		String formParams = request.getParameter("formParams");
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);
		Date currentDate = DateUtil.getCurrentDate();
		Map map = getMapFormPanelJson(formParams);
		
		if ("".equals(map.get("id"))) {
			map.put("serviceItem", Long.valueOf(serviceItemId));
			map.put("processType", ServiceItemProcess.PROCESS_TYPE_CREATE);
			map.put("status", Constants.STATUS_DRAFT);
			map.put("createDate", currentDate);
			map.put("createUser", UserContext.getUserInfo());
		}
		map.put("modifyDate", currentDate);
		map.put("modifyUser", UserContext.getUserInfo());
		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,map);
		String id = object.getId().toString();

//		String name = (String) map.get("name");
		String applyNum = "";
		BeanWrapper bw = new BeanWrapperImpl(object);
		if(bw.isReadableProperty("applyNum")){
			applyNum = (String) bw.getPropertyValue("applyNum");
		}
		String applyDate = (String) map.get("applyDate");
		String product = request.getParameter("product");
//		String tempProduct = "";
//		if (!"".equals(product) ) {
//			byte[] bt1;
//			try {
//				bt1 = product.getBytes("ISO8859_1");
//				tempProduct = new String(bt1,"GBK");//��GBK���н��룬����1���µ��ַ���  
//			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}//��ISO8859_1���б���			
//		}
		ERP_NormalNeed erp = (ERP_NormalNeed) getService().find(ERP_NormalNeed.class, id);
		
		JSONArray ja = JSONArray.fromObject("[" + product + "]");
//		RequireFactoryInfo rfi = null;
		for (int i = 0; i < ja.size(); i++) {
			HashMap productMap = new HashMap();
			JSONObject opl = (JSONObject) ja.get(i);
			Iterator itProduct = opl.keys();
			while (itProduct.hasNext()) {
				String key = (String) itProduct.next();
				String value = opl.getString(key);

//				if(("flm_ProjectTestReport$id").equals(key)&&value!="null"){
//					rfi = (RequireFactoryInfo) super.getService().find(RequireFactoryInfo.class, value);
//				}
				key = StringUtils.substringAfter(key, "$");
				value = value.trim();
				productMap.put(key, value);
			}
			productMap.put("requireData",erp);
			metaDataManager.saveEntityData(RequireFactoryInfo.class, productMap);
		}		
		String json = "{success:true,id:" + id + ",applyNum:'"+applyNum+"',applyDate:'"+applyDate+"'}";
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
	 * ͨ��dataId��ȡ������ع������
	 * 
	 * @Methods Name getAllProductIds
	 * @Create In 04 27, 2010 By zhangzy
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public void getAllProductIds(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String dataId = request.getParameter("dataId");		
//		Long requireId =0L;
		ERP_NormalNeed requireData = null;
		if(!"".equals(dataId)){
			 requireData = (ERP_NormalNeed) service.find(ERP_NormalNeed.class, dataId);
		}
		List dataList = service.find(RequireFactoryInfo.class,"requireData",requireData);
		String allProductIds = "";
		for(int i = 0 ; i<dataList.size();i++){
			RequireFactoryInfo rfi = (RequireFactoryInfo) dataList.get(i);
			if(rfi != null ){
				allProductIds += rfi.getId();
				allProductIds +=",";
			}	
		}
		StringBuilder json = new StringBuilder("{success:true,allProductIds:'");
		json.append(allProductIds);
		json.append("'}");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	private Map getMapFormPanelJson(String param){
		JSONObject basicJson=JSONObject.fromObject(param);
		Set<String> basicSet=basicJson.keySet();
		Map map = new HashMap();
		for (String key:basicSet) {
			String keyString = StringUtils.substringAfter(key, "$");
			String value = basicJson.getString(key);
			map.put(keyString, value);
		}
		return map;
	}
	
	
	
	/**
	 * ����panel�е���Ϣ
	 * 
	 * @Methods Name savePanelData
	 * @Create In 7, 7, 2010 By zhangzy
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String savePanelData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String param = request.getParameter("info");
		String pagePanelName = request.getParameter("pagePanel");
		PagePanel pagePanel = pagePanelService.findPagePanel(pagePanelName);
		SystemMainTable smt = pagePanel.getSystemMainTable();
		String className = smt.getClassName();
		Class clazz = this.toClass(className);

		Map map = getMapFormPanelJson(param);

		BaseObject object = (BaseObject) metaDataManager.saveEntityData(clazz,map);
		String id = object.getId().toString();
		
		String json = "{success:true,id:" + id +"'}";
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

}

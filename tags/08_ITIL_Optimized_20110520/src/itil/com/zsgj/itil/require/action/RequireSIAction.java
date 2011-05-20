package com.zsgj.itil.require.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.Column;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainTableService;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanelRelation;
import com.zsgj.info.appframework.pagemodel.service.PageGroupPanelService;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.appframework.pagemodel.service.PagePanelService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.itil.require.service.RequireSIService;
import com.zsgj.itil.service.entity.SCIRelationShip;
import com.zsgj.itil.service.entity.SCIRelationShipType;
import com.zsgj.itil.service.entity.ServiceCatalogue;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemApplyAuditHis;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.entity.ServiceType;
import com.zsgj.itil.service.service.SCIRelationShipService;
import com.zsgj.itil.service.service.ServiceItemProcessService;
import com.zsgj.itil.service.service.ServiceItemService;
import com.zsgj.itil.service.service.ServiceItemUserTableService;

public class RequireSIAction extends BaseAction {
	private PageManager pageManager = (PageManager) ContextHolder.getBean("pageManager");
	private MetaDataManager mdm = (MetaDataManager) super.getBean("metaDataManager");
	private RequireSIService requireSIService = (RequireSIService) getBean("requireSIService");
	private ServiceItemService serviceItemService = (ServiceItemService) getBean("serviceItemService");
	private ServiceItemUserTableService serviceItemUserTableService = (ServiceItemUserTableService) getBean("serviceItemUserTableService");
	private ServiceItemProcessService serviceItemProcessService = (ServiceItemProcessService) getBean("serviceItemProcessService");
	private SCIRelationShipService sciRelationShipService = (SCIRelationShipService) getBean("sciRelationShipService");
	private PageModelService pageModelService = (PageModelService) getBean("pageModelService");
	private PageGroupPanelService pgps = (PageGroupPanelService) getBean("pageGroupPanelService");
	private PagePanelService pagePanelService = (PagePanelService) ContextHolder.getBean("pagePanelService");
	private SystemColumnService systemColumnService = (SystemColumnService) getBean("systemColumnService");
	private SystemMainTableService systemMainTableService = (SystemMainTableService) getBean("systemMainTableService");
	/**
	 * 服务目录merge
	 * 根据不同的serviceType类型显示不同的服务目录结构，例如，常规，个性，账号等
	 * @Methods Name creatTreeDataForGeneral
	 * @Create In May 7, 2009 By sujs
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String creatTreeData() {
		HttpServletRequest request = super.getRequest();
		String sCIRelationShipId = request.getParameter("id");
		// 新增的从前台传过来的serviceType的标识
		String serviceTypeKeyWord = request.getParameter("serviceTypeKeyWord");
		if (sCIRelationShipId.equals("-1")) {
			UserInfo userInfo = UserContext.getUserInfo();
			List custIds = requireSIService.findCustIdByUser(userInfo);
			@SuppressWarnings("unused")
			List<ServiceCatalogue> servicecataLogue = requireSIService.findServiceCatalogueByCust(custIds);
			List<SCIRelationShip> list = requireSIService.findSCIRelationShipByService(servicecataLogue);
			List<SCIRelationShip> storeServiceCatalogue = new ArrayList<SCIRelationShip>();
			Map map = new HashMap();
			// ++++++++++++++++++++++++++++++++从根的下面开始比较的++++++++++++++++++
			long time = new Date().getTime(); // 从第2层开始merge
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getId(), time);
				if (i == 0) {
					// list.get(i).getServiceCatalogue().setName("根目录");
					storeServiceCatalogue.add(list.get(i));
				}
			}
			// ++++++++++++++++++++++++++++++++从根的下面开始比较的++++++++++++++++++
			super.getSession().setAttribute("storeData", map);

			request.setAttribute("scirelation", storeServiceCatalogue);
		} else {
			Map storeData = (Map) super.getSession().getAttribute("storeData");
			List<SCIRelationShip> list = requireSIService.findSCIRelationShipById(sCIRelationShipId, storeData,serviceTypeKeyWord);
			Map map = new HashMap();
			List<SCIRelationShip> storeServiceCatalogue = new ArrayList<SCIRelationShip>();
			if (list.isEmpty()) {
				request.setAttribute("scirelation", storeServiceCatalogue);
				return "serviceCatalogueShow";
			}
			for (int i = 0; i < list.size(); i++) {
				//为了区分是否特别的1代表正常的，0代表特例
				int flagSpecial = 1;
				long time = new Date().getTime() + i;
				if (list.get(i).getTypeFlag().equals(SCIRelationShip.SCI_TYPE_CATALOGUE)) {
					if (list.get(i).getTempName() == null|| !list.get(i).getTempName().equals("重复目录")) {
						int z = 0;
						if (list.get(i).getDispFlag() != null&& list.get(i).getDispFlag() == 0) {
							flagSpecial = 0;
						}
						// long time=new Date().getTime();
						for (int j = i + 1; j < list.size(); j++) {
							if (!list.get(j).getTypeFlag().equals(SCIRelationShip.SCI_TYPE_ITEM)) {
								if (list.get(i).getServiceCatalogue().getName().equals(list.get(j).getServiceCatalogue().getName())) {
									if (z == 0) {
										//加入的判断特别的方法
										if (flagSpecial == 1) {
											map.put(list.get(i).getId(), time);
										}
										z = 1;
									}
									if (flagSpecial == 1) {
										map.put(list.get(j).getId(), time);
									}
									list.get(j).setTempName("重复目录");

								}
							}
						}
						if (flagSpecial == 1) {
							storeServiceCatalogue.add(list.get(i));
						}

					}
				} else {
					if (list.get(i).getTempName() == null|| !list.get(i).getTempName().equals("重复服务项")) {
						int m = 0;
						// long time=new Date().getTime();
						if (list.get(i).getDispFlag() != null&& list.get(i).getDispFlag() == 0) {
							flagSpecial = 0;
						}
						for (int j = i + 1; j < list.size(); j++) {
							if (!list.get(j).getTypeFlag().equals(SCIRelationShip.SCI_TYPE_CATALOGUE)) {
								if (list.get(i).getServiceItem().getName().equals(list.get(j).getServiceItem().getName())) {
									if (m == 0) {
										//加入的判断特别的方法
										if (flagSpecial == 1) {
											map.put(list.get(i).getId(), time);
										}
										m = 1;
									}
									if (flagSpecial == 1) {
										map.put(list.get(j).getId(), time);
									}
									list.get(j).setTempName("重复服务项");
								}
							}
						}
						if (flagSpecial == 1) {
							storeServiceCatalogue.add(list.get(i));
						}
					}
				}
			}
			Long compareID = Long.parseLong(sCIRelationShipId);
			Object key = null;
			Object value = null;
			Iterator ittt = storeData.keySet().iterator();
			if (storeData.containsKey(compareID)) {
				while (ittt.hasNext()) {
					key = ittt.next();
					value = storeData.get(key);
					if (!storeData.get(compareID).equals(value)) {
						map.put(key, value);
					}
				}
			} else {
				while (ittt.hasNext()) {
					key = ittt.next();
					value = storeData.get(key);
					map.put(key, value);

				}
			}
			super.getSession().removeAttribute("storeData");
			super.getSession().setAttribute("storeData", map);
			request.setAttribute("scirelation", storeServiceCatalogue);

		}
		return "serviceCatalogueShow";

	}

	/*
	 * 通过id获取描述信息
	 * By sujs
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public String selectDeseData() {
		String treeDeseID = super.getRequest().getParameter("treeDeseID");
		@SuppressWarnings("unused")
		SCIRelationShip sCIRelationShip = requireSIService.findServiceCatalogueByRelationId(treeDeseID);

		String json = "{\"servicemessage\":[{";
		if (sCIRelationShip.getTypeFlag().equals(SCIRelationShip.SCI_TYPE_ITEM)) {
			ServiceItem serviceItem = sCIRelationShip.getServiceItem();
			List list = serviceItemProcessService.findProcessesByServiceItem(serviceItem);
			if (list.size() > 0) {
				json += "\"process\":\"" + "true" + "\",";
			} else {
				json += "\"process\":\"" + "false" + "\",";
			}
			json = json + "\"serviceTage\":\"" + "服务项" + "\",";
			json = json + "\"serviceName\":\"" + sCIRelationShip.getServiceItem().getName() + "\",";
			Double siFee = sCIRelationShip.getServiceItemFee();
			if (siFee == null) {
				siFee = 0.0D;
			}
			json = json + "\"servicePrice\":\"" + siFee + "\",";
			String descn = sCIRelationShip.getServiceItem().getDescn();
			if (descn == null) {
				descn = "";
			}
			json = json + "\"descn\":\"" + descn + "\"";
		} else {
			json = json + "\"serviceTage\":\"" + "服务目录" + "\"";
		}
		json += "}]}";
		try {
			HttpServletResponse response = super.getResponse();
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 点击节点获取节点包含服务项或服务目录数据
	 * @Methods Name getNodeInfo
	 * @Create In Jun 29, 2009 By lee
	 * @return String
	 */
	public String getNodeInfo(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String nodeId = request.getParameter("nodeId");
		SCIRelationShip sCIRelationShip = requireSIService.findServiceCatalogueByRelationId(nodeId);

		String type = sCIRelationShip.getTypeFlag();
		String json = "";
		if(type.equals(SCIRelationShip.SCI_TYPE_ITEM)){
			ServiceItem serviceItem = sCIRelationShip.getServiceItem();
			String sciName = serviceItem.getName();
			String descn = serviceItem.getDescn();
			Double siFee = sCIRelationShip.getServiceItemFee();
			List list = serviceItemProcessService.findProcessesByServiceItem(serviceItem);
			
			json+="{success:true,type:\"item\",";
			json+="name:\""+sciName+"\",";
			if (siFee == null) {
				siFee = 0.0D;
			}
			json+="price:\""+siFee+"\",";
			if (descn == null) {
				descn = "";
			}
			json+="descn:\"" + descn + "\",";
			if (list.size() > 0) {
				json += "process:\"" + "true" + "\"";
			} else {
				json += "process:\"" + "false" + "\"";
			}
			json += "}";
		}else{
			json+="{success:true,type:\"cata\"}";
		}
		
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 为服务项需求页面提供信息
	 * @Methods Name toRequireInfo
	 * @Create In Feb 25, 2009 By lee
	 * @return String
	 */
	public String toRequireInfo() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		SCIRelationShip sciRelationShip = sciRelationShipService.findSCIRelationShipById(id);
		ServiceItem serviceItem = null;
		if (SCIRelationShip.SCI_TYPE_ITEM.equals(sciRelationShip.getTypeFlag())) {
			serviceItem = sciRelationShip.getServiceItem();
			String serviceItemName = serviceItem.getName();
			ServiceItemUserTable siut = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
			//modify by lee for process entry in 20090522 begin
			if (siut.getPageListPanel() == null) {
				throw new ApplicationException("此服务没有配置需求入口面板");
			}
			//modify by lee for process entry in 20090522 begin
			List<ServiceItemProcess> serviceItemProcesses = serviceItemProcessService.findProcessesByServiceItem(serviceItem);
			int processTypeSum = 0;
			for (ServiceItemProcess serviceItemProcess : serviceItemProcesses) {
				processTypeSum += serviceItemProcess.getSidProcessType();
			}
			String listPanelName = siut.getPageListPanel().getName();
			String tableName = siut.getSystemMainTable().getTableName();
			String clazz = siut.getClassName();
			request.setAttribute("serviceItemId", serviceItem.getId());
			request.setAttribute("serviceItemName", serviceItem.getName());
			request.setAttribute("processTypeSum", processTypeSum);
			request.setAttribute("gridName", listPanelName);
			request.setAttribute("tableName", tableName);//"com.zsgj.itil.service.entity.ServiceItem");//
			return "requireInfo";
		} else {
			return "error";
		}
	}

	public String toRequireInfo2() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		SCIRelationShip sciRelationShip = sciRelationShipService.findSCIRelationShipById(id);
		ServiceItem serviceItem = null;
		if (SCIRelationShip.SCI_TYPE_ITEM.equals(sciRelationShip.getTypeFlag())) {
			serviceItem = sciRelationShip.getServiceItem();
			String serviceItemName = serviceItem.getName();
			ServiceItemUserTable siut = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
			//modify by lee for process entry in 20090522 begin
			if (siut.getPageListPanel() == null) {
				throw new ApplicationException("此服务没有配置需求入口面板");
			}
			//modify by lee for process entry in 20090522 begin
			List<ServiceItemProcess> serviceItemProcesses = serviceItemProcessService.findProcessesByServiceItem(serviceItem);
			int processTypeSum = 0;
			for (ServiceItemProcess serviceItemProcess : serviceItemProcesses) {
				processTypeSum += serviceItemProcess.getSidProcessType();
			}
			String listPanelName = siut.getPageListPanel().getName();
			String tableName = siut.getSystemMainTable().getTableName();
			String clazz = siut.getClassName();
			request.setAttribute("serviceItemId", serviceItem.getId());
			request.setAttribute("serviceItemName", serviceItem.getName());
			request.setAttribute("processTypeSum", processTypeSum);
			request.setAttribute("gridName", listPanelName);
			request.setAttribute("tableName", tableName);//"com.zsgj.itil.service.entity.ServiceItem");//
			return "requireInfo2";
		} else {
			return "error";
		}
	}

	/**
	 * 为服务项需求页面提供信息帐号
	 * @Methods Name toRequireInfoByServiceItemId2
	 * @Create In Jun 25, 2009 By gaowen
	 * @return String
	 */
	public String toRequireInfoByServiceItemId2() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		if (id != null && !"".equals(id)) {
			ServiceItem serviceItem = serviceItemService.findServiceItemById(id);
			String serviceItemName = serviceItem.getName();
			ServiceItemUserTable siut = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
			String listPanelName = siut.getPageListPanel().getName();
			String tableName = siut.getSystemMainTable().getTableName();
			String clazz = siut.getClassName();
			request.setAttribute("serviceItemId", serviceItem.getId());
			request.setAttribute("serviceItemName", serviceItem.getName());
			request.setAttribute("gridName", listPanelName);
			request.setAttribute("tableName", tableName);//"com.zsgj.itil.service.entity.ServiceItem");//
			return "requireInfo2";
		} else {
			return "error";
		}
	}
	
	/**
	 * 为服务项需求页面提供信息
	 * @Methods Name toRequireInfoByServiceItemId
	 * @Create In Feb 25, 2009 By lee
	 * @return String
	 */
	public String toRequireInfoByServiceItemId() {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		if (id != null && !"".equals(id)) {
			ServiceItem serviceItem = serviceItemService.findServiceItemById(id);
			String serviceItemName = serviceItem.getName();
			ServiceItemUserTable siut = serviceItemUserTableService.findServiceItemUserTableByServiceItem(serviceItem);
			String listPanelName = siut.getPageListPanel().getName();
			String tableName = siut.getSystemMainTable().getTableName();
			String clazz = siut.getClassName();
			request.setAttribute("serviceItemId", serviceItem.getId());
			request.setAttribute("serviceItemName", serviceItem.getName());
			request.setAttribute("gridName", listPanelName);
			request.setAttribute("tableName", tableName);//"com.zsgj.itil.service.entity.ServiceItem");//
			return "requireInfo";
		} else {
			return "error";
		}
	}

	/**
	 * 为服务项下流程树提供数据
	 * @Methods Name getProcessTreeData
	 * @Create In Feb 25, 2009 By lee
	 * @return String
	 */
	public String getProcessTreeData() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(id);
		String json = "";
		List<ServiceItemProcess> list = serviceItemProcessService.findProcessesByServiceItem(serviceItem);
		for (ServiceItemProcess process : list) {
			json += "{id:" + process.getId() + ",";
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
			//json += "text:\"" + process.getDefinitionName() + "\",";
			json += "text:\"" + process.getProcessInfo().getRealDefinitionDesc() + "\",";
			//modify by lee for change serviceItemProcess to ORM in 200090707 end
			json += "icon : webContext+'/images/other/class.gif',";
			json += "cls : 'x-btn-text-icon',";
			json += "children:[";
			json += "{id:1,text:\"草稿\",";
			json += "icon : webContext+'/images/cls/gears.gif',";
			json += "cls : 'x-btn-text-icon',";
			json += "leaf:true},";
			json += "{id:2,text:\"处理中\",";
			json += "icon : webContext+'/images/cls/gears.gif',";
			json += "cls : 'x-btn-text-icon',";
			json += "leaf:true},";
			json += "{id:3,text:\"处理结束\",";
			json += "icon : webContext+'/images/cls/gears.gif',";
			json += "cls : 'x-btn-text-icon',";
			json += "leaf:true}";
			json += "]},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
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

	/**
	 * 为TreeGrid提供数据
	 * @Methods Name createTreeGridData
	 * @Create In Mar 4, 2009 By sujs
	 * @return String
	 * @throws IOException 
	 */

	@SuppressWarnings("unchecked")
	public String createTreeGridData() throws IOException {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");			//服务项ID
		//String className= request.getParameter("clazz");
		String panelName = request.getParameter("panelName");
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		String className = pagePanel.getSystemMainTable().getClassName();
		ServiceItem serviceItem = serviceItemService.findServiceItemById(id);
		//List clazzauto=requireSIService.findAutoClazz(className);
		
		//modify by lee for 过滤查看可查看用户数据权限范围：自己还是部门所有人 in 20090818 begin
		UserInfo userInfo = UserContext.getUserInfo();
		List<UserInfo> custIds = requireSIService.findDataScopeByUser(userInfo);
		List mainList = requireSIService.findEntities(className, id,custIds);
		
		//add by lee for 添加用户审批过的数据 in 20090829 bgein
		List auditList = requireSIService.findAuditHisEntities(className, id, userInfo);
		List mAuditList = new ArrayList();
		for(Object bo : auditList){
			Object newObj = BeanUtils.instantiateClass(getClass(className));
			BeanUtils.copyProperties(bo, newObj);
			BeanWrapper bw = new BeanWrapperImpl(newObj);
			bw.setPropertyValue("status", Integer.valueOf(3));
			mAuditList.add(newObj);
		}
		mainList.addAll(mAuditList);
		//add by lee for 添加用户审批过的数据 in 20090829 end
		
		//List mainList = requireSIService.findEntities(className, id);
		//modify by lee for 过滤查看可查看用户数据权限范围：自己还是部门所有人 in 20090818 end
		
		List<Map<String, Object>> listData = mdm.getEntityMapDataForList(getClass(className), mainList);
		//可以在这之前操纵查询数据，过滤出listData数据来，
		//#######################################################
		//String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns = pageManager.getUserPagePanelColumn(panelName);
		//######################################################
		SystemMainTable smt=systemMainTableService.findSystemMainTableByClazz(toClass(className));
		List<SystemMainTableColumn> utss = systemColumnService.findSystemTableColumns(smt);
		//List<UserTableSetting> utss = mdm.getUserColumnForList(getClass(className));
		
		String json = "";

		int lft = 1;
		int rgt = 1;
		int ids = 1;
		List<ServiceItemProcess> list = serviceItemProcessService.findProcessesByServiceItem(serviceItem);
		for (ServiceItemProcess process : list) {
			int parent = 0;
			json += "{";
			//modify by lee for change serviceItemProcess to ORM in 200090707 begin 
			//json += "'rootGrid':'" + process.getDefinitionName() + "',";
			json += "'rootGrid':'" + process.getProcessInfo().getVirtualDefinitionDesc() + "',";
			//modify by lee for change serviceItemProcess to ORM in 200090707 end 
			//modify by lee for 避开1期按用户过滤方法 in 20090829 begin
			//for (UserTableSetting uts : utss) {
			for (SystemMainTableColumn column : utss) {
				//Column column = uts.getColumn();
			//modify by lee for 避开1期按用户过滤方法 in 20090829 end
				String tableName = column.getSystemMainTable().getTableName();
				String propertyName = column.getPropertyName();

				json += "\"" + tableName + "$" + propertyName + "\":" + "\"\""
						+ ",";
			}
			int k = 0;
			for (Map<String, Object> item : listData) {
				if (process.getSidProcessType().equals(Integer.parseInt(item.get("processType").toString()))) {
					k++;
				}
			}
			json += "'_id':" + ids + ",";
			json += "'_parent':" + 0 + ",";
			json += "'_level':" + 1 + ",";
			json += "'_lft':" + lft + ",";
			rgt = lft + (k + 4) * 2 + 1;
			json += "'_rgt':" + rgt + ",";
			json += "'_is_leaf':" + false + "},";
			//获取数据
			int Second = lft + 1;
			lft = rgt + 1;
			parent = ids;
			ids++;
			for (int i = 0; i < 4; i++) {
				String onlyName = "";
				if (i == 0) {
					onlyName = "草稿";
				} else if (i == 1) {
					onlyName = "审批中";
				} else if (i == 2) {
					onlyName = "审批结束";
				} 
				//add by lee for 添加用户审批过的数据 in 20090829 begin
				else if (i == 3){
					onlyName = "我审批的";
				}
				//add by lee for 添加用户审批过的数据 in 20090829 end
				json += "{'rootGrid':'" + onlyName + "',";
				//modify by lee for 避开1期按用户过滤方法 in 20090829 begin
				//for (UserTableSetting uts : utss) {
				for (SystemMainTableColumn column : utss) {
					//Column column = uts.getColumn();
				//modify by lee for 避开1期按用户过滤方法 in 20090829 end
					String tableName = column.getSystemMainTable().getTableName();
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
					//System.out.print(item);
					Object numObject = i;
					if (process.getSidProcessType().equals(Integer.parseInt(item.get("processType").toString()))
							&& item.get("status").equals(String.valueOf(numObject))) {
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
				//获取真是的数据
				Object numObject = i;
				int z = 1;
				int third = Second + 1;
				Second = rgtc + 1;
				int chidparent = ids;
				ids++;
				for (Map<String, Object> item : listData) {
					if (process.getSidProcessType().equals(Integer.parseInt(item.get("processType").toString()))
							&& item.get("status").equals(String.valueOf(numObject))) {
						json += "{";
						json += "'rootGrid':'" + item.get("name") + "',";
						//modify by lee for 避开1期按用户过滤方法 in 20090829 begin
						//for (UserTableSetting uts : utss) {
						for (SystemMainTableColumn column : utss) {
							//Column column = uts.getColumn();
						//modify by lee for 避开1期按用户过滤方法 in 20090829 end
							String tableName = column.getSystemMainTable().getTableName();
							String propertyName = column.getPropertyName();
							Object value = item.get(propertyName);
							if (value == null)
								value = "";
							json += "\"" + tableName + "$" + propertyName
									+ "\":'" + value + "',";
						}
						//json+="'_id':"+ids+",";
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

//modify by lee for 为treeGridStore改为直接远程请求修改返回方式 in 20091110 begin
		//json = "[" + json + "]";
		json = "{success:true,data:[" + json + "]}";
//modify by lee for 为treeGridStore改为直接远程请求修改返回方式 in 20091110 end
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 按不同流程不同状态获取需求主表信息
	 * @Methods Name getQuerry
	 * @Create In Feb 27, 2009 By lee
	 * @return String
	 */
	public String getQuerry() {
		HttpServletRequest request = super.getRequest();
		String clazz = request.getParameter("clazz");
		String procesId = request.getParameter("processId");
		String state = request.getParameter("state");

		return null;
	}

	public String getHeadforTree() throws IOException {

		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		//String className= request.getParameter("clazz");
		String panelName = request.getParameter("panelName");
		PagePanel pagePanel = pagePanelService.findPagePanel(panelName);
		String className = pagePanel.getSystemMainTable().getClassName();
		List<UserTableSetting> utss = mdm.getUserColumnForList(getClass(className));
		//    	######################################################
		//String panelName = request.getParameter("panelName");
		PagePanel panel = pagePanelService.findPagePanel(panelName);
		List<PagePanelColumn> pagePanelColumns = pageManager.getUserPagePanelColumn(panelName);
		//    	######################################################
		String json = "{name:'rootGrid'},";
		//        	for(UserTableSetting uts : utss){  //改成遍历
		//        		Column column = uts.getColumn();
		//        		String propertyName = column.getPropertyName();	
		//        		json+="{name:"+"'"+propertyName+"'"+"},";
		//        	}
		for (PagePanelColumn ppcolumn : pagePanelColumns) { //改成遍历
			Column column = ppcolumn.getColumn();
			String tableName = ppcolumn.getSystemMainTable().getTableName();
			String propertyName = column.getPropertyName();
			json += "{name:" + "'" + tableName + "$" + propertyName + "'"+ "},";
		}
		json += "{name:'_id'},";
		json += "{name:'_parent'},";
		json += "{name:'_level'},";
		json += "{name:'_lft'},";
		json += "{name:'_rgt'},";
		json += "{name:'_is_leaf'}";
		//json = json.substring(0, json.length()-1);
		json = "[" + json + "]";
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
		return null;

	}

	public String getPageModelForRequire() {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String keyName = request.getParameter("modelName");
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = (ServiceItem) getService().find(
				ServiceItem.class, serviceItemId);
		//根据model的关键字取model，此方法是考虑新的分组面板思想而设计的，底层主动获取分组面板的子面板
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
		//end

		List<PageModelPanel> pmps = requireSIService
				.getPanelsByServiceItem(serviceItem);
		if (pmps.isEmpty()) {
			json = ""; //告诉页面model没有选择panel
			return json;
		}
		//生成panel
		json += "\"panels\":[";
		for (PageModelPanel item : pmps) { //遍历model下的根面板
			//生成pageModel下面板json
			json += this.genPagePanelJson(item); //进入递归方法，传入分组面板
			json += ",";

		}
		json = json.substring(0, json.length() - 1);
		json += "]}";
		//*******************************************************************
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
	 * 获取面板信息
	 * @Methods Name genPagePanelJson
	 * @Create In Feb 27, 2009 By Administrator
	 * @param pmp
	 * @return String
	 */
	private String genPagePanelJson(PageModelPanel pmp) {
		String json = "";
		PagePanel panel = pmp.getPagePanel();
		String divFloat = pmp.getDivFloat();
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		json += "{";
		json += "\"panelname\":\"" + panel.getName() + "\",";
		json += "\"title\":\"" + panel.getTitle() + "\",";
		if (panel.getGroupFlag() != null&& panel.getGroupFlag().intValue() == 0) {
			json += "\"panelTableName\":\"" + panel.getSystemMainTable().getTableCnName() + "\",";
			json += "\"clazz\":\"" + panel.getSystemMainTable().getClassName() + "\",";
			//如果model根面板属于可编辑面板，如财务面板改成editorGrid，它就引用配置项
			if (panel.getXtype().getName().equalsIgnoreCase("editorgrid")
					|| panel.getXtype().getName().equalsIgnoreCase("grid")) {
				SystemMainTable subTable = panel.getSystemMainTable();
				List<PageModelPanelTable> pmpts = this.pageModelService.findPageModelPanelTableBySub(pmp.getPageModel(),panel, subTable);
				for (PageModelPanelTable pmpt : pmpts) {
					//配置项面板
					PagePanel parentPanel = pmpt.getParentPagePanel();
					//配置项父表
					SystemMainTable parentTable = parentPanel.getSystemMainTable();
					SystemMainTableColumn parentPanelTablePColumn = pmpt.getParentPanelTablePColumn();
					//ConfigItem$id
					pcolumnPropName = parentTable.getTableName() + "$" + parentPanelTablePColumn.getPropertyName();
					SystemMainTableColumn subFc = pmpt.getSubPanelTableFColumn();
					fcolumnPropName = subFc.getPropertyName();
				}

			}
		}

		json += "\"fcolumnName\":\"" + fcolumnPropName + "\",";
		json += "\"pcolumnName\":\"" + pcolumnPropName + "\",";
		json += "\"xtype\":\"" + panel.getXtype().getName() + "\",";
		json += "\"groupFlag\":\"" + panel.getGroupFlag() + "\",";

		Integer readonlyFlag = pmp.getReadonly();
		if (readonlyFlag == null)
			readonlyFlag = 0;
		json += "\"readonlyFlag\":\"" + readonlyFlag + "\",";

		json += "\"queryFlag\":\"" + panel.getQueryFlag() + "\",";
		json += "\"divFloat\":\"" + divFloat + "\",";
		json += "\"order\":\"" + pmp.getOrder() + "\"";
		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if (!childpprs.isEmpty()) { //遍历分组面板下面的面板
			json += ",\"childPagePanels\":[";
			for (PagePanelRelation ppr : childpprs) {
				json += this.genPagePanelJson(ppr);
				json += ",";
			}
			json = json.substring(0, json.length() - 1);
			json += "]";
		}
		json += "}";
		return json;
	}

	private String genPagePanelJson(PagePanelRelation pmp) {
		String json = "";
		PagePanel panel = pmp.getPagePanel();
		String fcolumnPropName = "";
		String pcolumnPropName = "";
		json += "{";
		json += "\"panelname\":\"" + panel.getName() + "\",";
		json += "\"title\":\"" + panel.getTitle() + "\",";
		if (panel.getGroupFlag() != null&& panel.getGroupFlag().intValue() == 0) {
			json += "\"panelTableName\":\"" + panel.getSystemMainTable().getTableCnName() + "\",";
			json += "\"clazz\":\"" + panel.getSystemMainTable().getClassName() + "\",";
			List<PageGroupPanelTable> list = this.pgps.findGroupPanelTableBySub(pmp.getParentPagePanel(), panel);
			PageGroupPanelTable pgpt = null;
			if (list != null && !list.isEmpty()) {
				pgpt = list.iterator().next();
			}
			if (pgpt != null) {
				SystemMainTableColumn fcolumn = pgpt.getSubPanelTableFColumn();
				fcolumnPropName = fcolumn.getPropertyName();

				SystemMainTable parentPanelTable = pgpt.getParentPanelTable();
				String pptableName = parentPanelTable.getTableName();
				SystemMainTableColumn pc = pgpt.getParentPanelTablePColumn();
				pcolumnPropName = pc.getPropertyName();
				pcolumnPropName = pptableName + "$" + pcolumnPropName;

			}

		}
		json += "\"fcolumnName\":\"" + fcolumnPropName + "\",";
		json += "\"pcolumnName\":\"" + pcolumnPropName + "\",";
		json += "\"xtype\":\"" + panel.getXtype().getName() + "\",";

		Integer readonlyFlag = pmp.getReadonly();
		if (readonlyFlag == null)
			readonlyFlag = 0;
		json += "\"readonlyFlag\":\"" + readonlyFlag + "\",";

		json += "\"groupFlag\":\"" + panel.getGroupFlag() + "\",";
		json += "\"queryFlag\":\"" + panel.getQueryFlag() + "\",";
		json += "\"order\":\"" + pmp.getOrder() + "\"";

		Set<PagePanelRelation> childpprs = panel.getChildPagePanels();
		if (!childpprs.isEmpty()) {
			json += ",\"childPagePanels\":[";
			for (PagePanelRelation ppr : childpprs) {
				json += this.genPagePanelJson(ppr);
				json += ",";
			}
			json = json.substring(0, json.length() - 1);
			json += "]";
		}
		json += "}";
		return json;
	}
	
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
	
	/**
	 * 为需求部分服务目录显示提供数据
	 * @Methods Name listSiForReq
	 * @Create In Nov 20, 2009 By lee
	 * @return String
	 */
	public String listSiForReq(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceTypeStr = request.getParameter("serviceType");
		int start = HttpUtil.getInt(request, "start",1);
		int pageSize = HttpUtil.getInt(request, "pageSize",20);
		
		ServiceType serviceType = null;
		if(StringUtils.isNotBlank(serviceTypeStr)){
			serviceType = (ServiceType) getService().find(ServiceType.class, serviceTypeStr);
		}
		UserInfo userInfo = UserContext.getUserInfo();
		List<SCIRelationShip> list = sciRelationShipService.findServiceItemsByServiceType(serviceType,userInfo);
		String json = "";
		for(SCIRelationShip ship : list){
			String tempStr = "";
			tempStr += "id:" + ship.getId();
			tempStr += ",serviceItemCode:'" + ship.getServiceItem().getServiceItemCode();
			tempStr += "',name:'" + ship.getServiceItem().getName();
			String servePrice = "";
			if(ship.getServiceItemFee()!=null){
				servePrice = ship.getServiceItemFee().toString();
			}else if(ship.getServiceItem().getServePrice()!=null){
				servePrice = ship.getServiceItem().getServePrice().toString();
			}
			tempStr += "',servePrice:'" + servePrice;
			ServiceItemType serviceItemType = ship.getServiceItem().getServiceItemType();
			if(serviceItemType!=null){
				tempStr += "',serviceItemType:'" + serviceItemType.getId();
			}
			String descn = "";
			if(ship.getServiceItem().getDescn()!=null){
				descn = ship.getServiceItem().getDescn();
			}
			tempStr += "',descn:'" + descn;
			String process = "false";
			List<ServiceItemProcess> processes = serviceItemProcessService.findProcessesByServiceItem(ship.getServiceItem());
			if(!processes.isEmpty()){
				process = "true";
			}
			tempStr += "',process:'" + process +"'";
			json += "{" + tempStr + "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		
		json = "{success: true, data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 得到快速通道服务项
	 * @Methods Name listFastSiForReq
	 * @Create In Nov 23, 2009 By lee
	 * @return String
	 */
	public String listFastSiForReq(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItems = request.getParameter("fastSiIds");
		int start = HttpUtil.getInt(request, "start",1);
		int pageSize = HttpUtil.getInt(request, "pageSize",20);
		String[] ids = serviceItems.split(",");
		List<Long> idlist = new ArrayList<Long>();
		for(int i=0;i<ids.length;i++){
			idlist.add(Long.valueOf(ids[i]));
		}
		ServiceType serviceType = null;
		UserInfo userInfo = UserContext.getUserInfo();
		List<SCIRelationShip> list = sciRelationShipService.findServiceItemsByServiceType(serviceType,userInfo);
		String json = "";
		for(SCIRelationShip ship : list){
			Long siId = ship.getServiceItem().getId();
			if(!idlist.contains(siId)){
				continue;
			}
			String tempStr = "";
			tempStr += "id:" + ship.getId();
			tempStr += ",serviceItemCode:'" + ship.getServiceItem().getServiceItemCode();
			tempStr += "',name:'" + ship.getServiceItem().getName();
			String servePrice = "";
			if(ship.getServiceItemFee()!=null){
				servePrice = ship.getServiceItemFee().toString();
			}else if(ship.getServiceItem().getServePrice()!=null){
				servePrice = ship.getServiceItem().getServePrice().toString();
			}
			tempStr += "',servePrice:'" + servePrice;
			ServiceItemType serviceItemType = ship.getServiceItem().getServiceItemType();
			if(serviceItemType!=null){
				tempStr += "',serviceItemType:'" + serviceItemType.getId();
			}
			String descn = "";
			if(ship.getServiceItem().getDescn()!=null){
				descn = ship.getServiceItem().getDescn();
			}
			tempStr += "',descn:'" + descn;
			String process = "false";
			List<ServiceItemProcess> processes = serviceItemProcessService.findProcessesByServiceItem(ship.getServiceItem());
			if(!processes.isEmpty()){
				process = "true";
			}
			tempStr += "',process:'" + process +"'";
			json += "{" + tempStr + "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		
		json = "{success: true, data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	/**
	 * 根据用户信息获取服务项
	 * @Methods Name listServiceItemByUserAction
	 * @Create In 15 7, 2010 By zhangzy
	 * @return String
	 */
	public String listServiceItemByUserAction(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String strServiceItemType = request.getParameter("serviceItemType");
		String strServiceType = request.getParameter("serviceType");
		String strServiceItemName = request.getParameter("serviceItemName");
		int pageNo = HttpUtil.getInt(request, "start",1);
		int pageSize = HttpUtil.getInt(request, "pageSize",20);
		
		ServiceItemType serviceItemType = null;
		ServiceType serviceType = null;
		if(StringUtils.isNotBlank(strServiceItemType)){
			serviceItemType = (ServiceItemType) getService().find(ServiceItemType.class, strServiceItemType);
		}
		if(StringUtils.isNotBlank(strServiceType)){
			serviceType = (ServiceType) getService().find(ServiceType.class, strServiceType);
		}
		UserInfo userInfo = UserContext.getUserInfo();
		List<SCIRelationShip> list = sciRelationShipService.listServiceItemByUserService(strServiceItemName, serviceItemType, serviceType , userInfo );
		String json = "";
		for(SCIRelationShip ship : list){
			String tempStr = "";
			tempStr += "id:" + ship.getId();
			tempStr += ",serviceItemCode:'" + ship.getServiceItem().getServiceItemCode();
			tempStr += "',name:'" + ship.getServiceItem().getName();
			String servePrice = "";
			if(ship.getServiceItemFee()!=null){
				servePrice = ship.getServiceItemFee().toString();
			}else if(ship.getServiceItem().getServePrice()!=null){
				servePrice = ship.getServiceItem().getServePrice().toString();
			}
			tempStr += "',servePrice:'" + servePrice;
			if(strServiceItemType!=null){
				tempStr += "',serviceItemType:'" + strServiceItemType;
			}
			String descn = "";
			if(ship.getServiceItem().getDescn()!=null){
				descn = ship.getServiceItem().getDescn();
			}
			tempStr += "',descn:'" + descn+"'";
			json += "{" + tempStr + "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "{success: true,data:[" + json + "]}";
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

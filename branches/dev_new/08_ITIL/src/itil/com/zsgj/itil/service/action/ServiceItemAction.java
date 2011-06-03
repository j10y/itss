package com.zsgj.itil.service.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.PropertyType;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.zsgj.info.appframework.metadata.entity.ValidateType;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.itil.service.entity.SCIColumn;
import com.zsgj.itil.service.entity.SCIDColumn;
import com.zsgj.itil.service.entity.ServiceCatalogueContract;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemProcess;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceStatus;
import com.zsgj.itil.service.service.SCIColumnService;
import com.zsgj.itil.service.service.SCIDColumnService;
import com.zsgj.itil.service.service.ServiceItemProcessService;
import com.zsgj.itil.service.service.ServiceItemService;

public class ServiceItemAction  extends BaseAction{
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private ServiceItemService serviceItemService = (ServiceItemService) getBean("serviceItemService");
	private ServiceItemProcessService sips = (ServiceItemProcessService) getBean("serviceItemProcessService");
	private SCIColumnService sCIColumnService = (SCIColumnService) getBean("sCIColumnService");
	private SCIDColumnService sCIDColumnService = (SCIDColumnService) getBean("sCIDColumnService");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	/**
	 * ��ȡ�����������б�
	 * @Methods Name list
	 * @Create In Feb 12, 2009 By lee
	 * @return
	 * @ReturnType String
	 */
	public String list() throws Exception {
		HttpServletRequest request = super.getRequest();
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceStates = getService().findAll(ServiceStatus.class);
		request.setAttribute("serviceStates", serviceStates);
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String serviceItemTypeId = request.getParameter("serviceItemType");
		String serviceStateId = request.getParameter("serviceItemState");
		String serviceItemName = request.getParameter("serviceItemName");
		ServiceItemType servcieItemType = null;
		if(StringUtils.isNotBlank(serviceItemTypeId)){
			servcieItemType = (ServiceItemType) getService().find(ServiceItemType.class, serviceItemTypeId);
			request.setAttribute("serviceItemType", servcieItemType);
		}
		
		ServiceStatus serviceState = null;
		if(StringUtils.isNotBlank(serviceStateId)){
			serviceState = (ServiceStatus) getService().find(ServiceStatus.class, serviceStateId);
			request.setAttribute("serviceState", serviceState);
		}
		if(StringUtils.isNotBlank(serviceItemName)){
		request.setAttribute("serviceItemName", serviceItemName);
		}
		Page page = serviceItemService.findServiceItems(servcieItemType,serviceState,serviceItemName,pageNo,pageSize);
		request.setAttribute("page", page);
		
		return "list";
	}
	/**
	 * ��ȡ�������б�Ϊ��������ṩ
	 * @Methods Name listForIssue
	 * @Create In Apr 2, 2009 By lee
	 * @return
	 * @throws Exception String
	 */
	public String listForIssue() throws Exception {
		HttpServletRequest request = super.getRequest();
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceStates = getService().findAll(ServiceStatus.class);
		request.setAttribute("serviceStates", serviceStates);
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String serviceItemTypeId = request.getParameter("serviceItemType");
		String serviceStateId = request.getParameter("serviceItemState");
		String serviceItemName = request.getParameter("serviceItemName");
		ServiceItemType servcieItemType = null;
		if(StringUtils.isNotBlank(serviceItemTypeId)){
			servcieItemType = (ServiceItemType) getService().find(ServiceItemType.class, serviceItemTypeId);
			request.setAttribute("serviceItemType", servcieItemType);
		}
		
		ServiceStatus serviceState = null;
		if(StringUtils.isNotBlank(serviceStateId)){
			serviceState = (ServiceStatus) getService().find(ServiceStatus.class, serviceStateId);
			request.setAttribute("serviceState", serviceState);
		}
		if(StringUtils.isNotBlank(serviceItemName)){
		request.setAttribute("serviceItemName", serviceItemName);
		}
		Page page = serviceItemService.findServiceItems(servcieItemType,serviceState,serviceItemName,pageNo,pageSize);
		request.setAttribute("page", page);
		
		return "listForIssue";
	}
	/**
	 * ��������������Ϣ��ͬʱ���÷��������������ֶ������������ɫ�ֶα�
	 * @Methods Name saveBaseInfo
	 * @Create In Feb 12, 2009 By lee
	 * @return
	 * @ReturnType String
	 */
	public String saveBaseInfo() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		ServiceItem serviceItem =(ServiceItem) BeanUtil.getObject(request, ServiceItem.class);
		Map recordMap = HttpUtil.requestParam2Map(request);
		String id=request.getParameter("id");
		
		if(id!=null&&!id.equals("")){
			ServiceItem serviceItemOld=serviceItemService.findServiceItemById(id);
			if(serviceItemOld.getServiceItemType()!=null&&
					!serviceItem.getServiceItemType().getId().equals(serviceItemOld.getServiceItemType().getId())){
				//ServiceItem curItem = (ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, recordMap);
				
				ServiceItem curItem = serviceItemService.save(recordMap);
				
//				List <SCIDColumn> sCIDColumns=sCIDColumnService.findSCIDColumnByServiceItem(curItem);
//				for(SCIDColumn sCIDColumn:sCIDColumns){
//					sCIDColumnService.removeSCIDColumn(sCIDColumn);
//				}
				ServiceItemType sit = curItem.getServiceItemType();
				List columns = sCIColumnService.findSCIColumnByServiceItemType(sit);
				List<SCIColumn> sciColumns = columns;
				sCIDColumnService.saveSCIDColumn(sciColumns,curItem);
			}else if(serviceItemOld.getServiceItemType()==null){
				
				//ServiceItem curItem = serviceItemService.save(serviceItem);
				
				//ServiceItem curItem = (ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, recordMap);
				ServiceItem curItem = serviceItemService.save(recordMap);
				
				ServiceItemType sit = curItem.getServiceItemType();
				List columns = sCIColumnService.findSCIColumnByServiceItemType(sit);
				List<SCIColumn> sciColumns = columns;
				sCIDColumnService.save(sciColumns,curItem);
			}else{
				//ServiceItem curItem = serviceItemService.save(serviceItem);
				//ServiceItem curItem = (ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, recordMap);
				serviceItemService.save(recordMap);
			}
		}else{
			//ServiceItem curItem = serviceItemService.save(serviceItem);
			
			ServiceItem curItem = (ServiceItem) metaDataManager.saveEntityData(ServiceItem.class, recordMap);
			
			id = curItem.getId().toString();
			ServiceItemType sit = curItem.getServiceItemType();
			List columns = sCIColumnService.findSCIColumnByServiceItemType(sit);
			List<SCIColumn> sciColumns = columns;
			sCIDColumnService.save(sciColumns,serviceItem);
		}
		String json = "{dataId :"+id+"}";
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
	 * ����������������Ի���Ϣ��ͬʱ���÷��������������ֶ������������ɫ�ֶα�
	 * @Methods Name saveSpecialInfo
	 * @Create In Feb 16, 2009 By lee
	 * @return
	 * @ReturnType String
	 */
	public String saveSpecialInfo() throws Exception {
		HttpServletRequest request = super.getRequest();
		String curdataId = request.getParameter("dataId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(curdataId);
		List<SCIDColumn> columns = sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		for(SCIDColumn column : columns){
			String columnName = column.getColumnName();
			String value = request.getParameter(columnName);
			sCIDColumnService.saveColumnValue(serviceItem, columnName, value);
		}
		return null;
	}
	/**
	 * ɾ��������
	 * @Methods Name remove
	 * @Create In Feb 16, 2009 By lee
	 * @return
	 * @ReturnType String
	 */
	public String remove() throws Exception {
		HttpServletRequest request = super.getRequest();
		String[] dataIds = request.getParameterValues("id");
		serviceItemService.removeByIds(dataIds);
		
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceStates = getService().findAll(ServiceStatus.class);
		request.setAttribute("serviceStates", serviceStates);
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String serviceItemTypeId = request.getParameter("serviceItemType");
		String serviceStateId = request.getParameter("serviceItemState");
		String serviceItemName = request.getParameter("serviceItemName");
		ServiceItemType servcieItemType = null;
		if(StringUtils.isNotBlank(serviceItemTypeId)){
			servcieItemType = (ServiceItemType) getService().find(ServiceItemType.class, serviceItemTypeId);
			request.setAttribute("serviceItemType", servcieItemType);
		}
		
		ServiceStatus serviceState = null;
		if(StringUtils.isNotBlank(serviceStateId)){
			serviceState = (ServiceStatus) getService().find(ServiceStatus.class, serviceStateId);
			request.setAttribute("serviceState", serviceState);
		}
		if(StringUtils.isNotBlank(serviceItemName)){
		request.setAttribute("serviceItemName", serviceItemName);
		}
		Page page = serviceItemService.findServiceItems(servcieItemType,serviceState,serviceItemName,pageNo,pageSize);
		request.setAttribute("page", page);
		
		return "list";
	}
	/**
	 * ��ȡ��������Ի��ֶΣ���ת����������Ի��ֶ�ҳ��
	 * @Methods Name columnInfo
	 * @Create In Feb 16, 2009 By lee
	 * @return
	 * @ReturnType String
	 */
	public String columnInfo() throws Exception {
		HttpServletRequest request = super.getRequest();
		String curdataId = request.getParameter("dataId");
		ServiceItem serviceItem=serviceItemService.findServiceItemById(curdataId);
		List<SCIDColumn> sCIDColumns =sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		request.setAttribute("serviceItem", serviceItem);
		request.setAttribute("sCIDColumns", sCIDColumns);
		return "columnInfo";
	}
	/**
	 * ��ȡ��������Ի��ֶε���ϸ��Ϣ
	 * @author tongjp
	 * @return
	 * @throws Exception
	 */
	public String scidColumnDetail() throws Exception {
		HttpServletRequest request = super.getRequest();
		String id = request.getParameter("id");
		if(id!=null&&id!=""){
			SCIDColumn sCIDColumn=sCIDColumnService.findSCIDColumnById(id);
			SystemMainTable ftable=sCIDColumn.getForeignTable();
			List fcolumns = scs.findSystemTableColumns(ftable);
			request.setAttribute("fcolumns",fcolumns);
			request.setAttribute("detail",sCIDColumn);
		}
		String serviceItemId=request.getParameter("serviceItem");
		ServiceItem serviceItem=serviceItemService.findServiceItemById(serviceItemId);
		request.setAttribute("serviceItem", serviceItem);
		
		List systemMainTableColumnTypes = getService().findAll(SystemMainTableColumnType.class);
		request.setAttribute("systemMainTableColumnTypes", systemMainTableColumnTypes);
		
		List validateTypes = getService().findAll(ValidateType.class);
		request.setAttribute("validateTypes", validateTypes);
		
		List sysMainTables = getService().findAllBy(SystemMainTable.class, "tableCnName", true);
		request.setAttribute("sysMainTables", sysMainTables);
		
		return "columnDetail";
	}
	/**
	 * ͨ��idɾ����������Ի��ֶ�
	 * @author tongjp
	 * @return
	 * @throws Exception
	 */
	public String removeScidColumn() throws Exception {
		HttpServletRequest request = super.getRequest();
		String[] ids = request.getParameterValues("id");
		sCIDColumnService.removeSCIDColumnByIds(ids);
		String serviceItemId = request.getParameter("serviceItem");
		ServiceItem serviceItem=serviceItemService.findServiceItemById(serviceItemId);
		List<SCIDColumn> sCIDColumns =sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		request.setAttribute("serviceItem", serviceItem);
		request.setAttribute("sCIDColumns", sCIDColumns);
		return "columnInfo";
	}
	/**
	 * ��Ӹ��Ի��ֶΣ������ֶεĸ�������
	 * @author tongjp
	 * @return
	 * @throws Exception
	 */
	public String saveScidColumn()throws Exception {
		HttpServletRequest request = super.getRequest();
		SCIDColumn sCIDColumn=(SCIDColumn) BeanUtil.getObject(request, SCIDColumn.class);
		sCIDColumnService.saveSCIDColumn(sCIDColumn);
		String serviceItemId = request.getParameter("serviceItem");
		ServiceItem serviceItem=serviceItemService.findServiceItemById(serviceItemId);
		List<SCIDColumn> sCIDColumns =sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		request.setAttribute("serviceItem", serviceItem);
		request.setAttribute("sCIDColumns", sCIDColumns);
		return "columnInfo";
	}
	/**
	 * ��ȡ������ǻ��������ֶ�����
	 * @Methods Name getItems
	 * @Create In Feb 12, 2009 By lee
	 * @return
	 * @ReturnType String
	 */
	public String getItems(){
		String json = "";
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("dataId");
		if(serviceItemId.equals("")||serviceItemId==null){
			json = "[]";
		}else{
			ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
			List<SCIDColumn> list = sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
			json = sCIDColumnService.encode(list);
		}
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
	 * ��ȡ����״̬ҳ������������ť
	 * @Methods Name getSCIProcessButton
	 * @Create In Jun 22, 2009 By lee
	 * @return String
	 */
	public String getSCIProcessButton(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = null;
		if(StringUtils.isNotBlank(serviceItemId)){
			serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		}
		String json="[";
		List<ServiceItemProcess> processes = sips.findProcessesByServiceItem (serviceItem);
		if(processes.isEmpty()){
			json+="";
		}else{
			for(ServiceItemProcess process : processes){
				if(!Integer.valueOf(1).equals(process.getStatus())){
					json+="{";
					json += "id:\""+process.getId()+"\",";
					json += "\"btnName\":\""+process.getButtonName()+"\",";
					json += "\"processType\":\""+process.getSidProcessType()+"\"";
					//json += "\"link\":\""+process.getPageModel().getPagePath()+"\"";//remove by lee for unused property in 20090715
					json+="},";
				}
			}
			if(json.length()>1)
				json = json.substring(0, json.length()-1);
		}
		json += "]";
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
	 * ��ȡ���������б�����
	 * @Methods Name getReqTables
	 * @Create In Jun 17, 2009 By lee
	 * @return String
	 */
	public String getReqTables(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String tableName = request.getParameter("tableName");
		int start = HttpUtil.getInt(request, "start",1);
		int pageSize = HttpUtil.getInt(request, "pageSize",20);
		Page page = serviceItemService.getReqTables(tableName, start, pageSize);
		String json = "";
		List<SystemMainTable> list = page.list();
		for(SystemMainTable smt : list){
			String tempStr = "";
			tempStr += "id:" + smt.getId();
			tempStr += ",tableName:'" + smt.getTableName();
			tempStr += "',tableCnName:'" + smt.getTableCnName();
			tempStr += "',classNameInfo:'" + smt.getClassName() + "'";
			json += "{" + tempStr + "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		
		json = "{success: true, rowCount:"+page.getTotalCount()
				+",data:[" + json + "]}";
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
	 * ��ȡ�������б�����
	 * @Methods Name listServiceItem
	 * @Create In Jun 17, 2009 By lee
	 * @return String
	 */
	public String listServiceItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sciType = request.getParameter("sciType");
		String sciStatus = request.getParameter("sciStatus");
		String sciName = request.getParameter("sciName");
		int start = HttpUtil.getInt(request, "start",0);
		int pageSize = HttpUtil.getInt(request, "pageSize",15);
		int pageNo=start/pageSize+1;
		ServiceItemType serviceItemType = null;
		ServiceStatus serviceStatus = null;
		if(StringUtils.isNotBlank(sciType)){
			serviceItemType = (ServiceItemType) getService().find(ServiceItemType.class, sciType);
		}
		if(StringUtils.isNotBlank(sciStatus)){
			serviceStatus = (ServiceStatus) getService().find(ServiceStatus.class, sciStatus);
		}
		Page page = serviceItemService.findServiceItems(serviceItemType, serviceStatus, sciName, pageNo, pageSize);
		String json = "";
		List<ServiceItem> list = page.list();
		for(ServiceItem sci : list){
			String tempStr = "";
			tempStr += "id:" + sci.getId();
			tempStr += ",serviceItemCode:'" + sci.getServiceItemCode();
			tempStr += "',name:'" + sci.getName();
			String serviceItemTypeStr = "";
			if(sci.getServiceItemType()!=null){
				serviceItemTypeStr = sci.getServiceItemType().getName();
			}
			tempStr += "',serviceItemType:'" + serviceItemTypeStr;
			String serviceStatusStr = "";
			if(sci.getServiceStatus()!=null){
				serviceStatusStr = sci.getServiceStatus().getName();
			}
			tempStr += "',serviceStatus:'" + serviceStatusStr;
//			tempStr += "',beginDate:'" + sci.getBeginDate().toString();
//			tempStr += "',endDate:'" + sci.getEndDate().toString();
//			tempStr += "',servePrice:'" + sci.getServePrice();
//			tempStr += "',serveCost:'" + sci.getServeCost();
			String serviceTypeStr = "";
			if(sci.getServiceType()!=null){
				serviceTypeStr = sci.getServiceType().getName();
			}
			tempStr += "',serviceType:'" + serviceTypeStr + "'";
			json += "{" + tempStr + "},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		
		json = "{success: true, rowCount:"+page.getTotalCount()
				+",data:[" + json + "]}";
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
	 * ɾ�����������ɾ��������Ϣ���������������
	 * @Methods Name removeSci
	 * @Create In Jul 2, 2009 By lee
	 * @return String
	 */
	public String removeSci(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sciId = request.getParameter("serviceItemId");
		serviceItemService.removeById(sciId);
		String json = "{success: true}";
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
	 * ��ȡ���������ݣ�Ϊ��������ṩ���ݣ�
	 * @Methods Name getSiDataById
	 * @Create In Nov 21, 2009 By lee
	 * @return String
	 */
	public String getSiDataById(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String sciId = request.getParameter("dataId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(sciId);
		Map<String, Object> dataMap = this.metaDataManager.getFormDataForEdit(serviceItem);
		JSONArray jsonObject = JSONArray.fromObject(dataMap);
		String json = "{success:" + true + ",form:"+ jsonObject.toString() + "}";
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

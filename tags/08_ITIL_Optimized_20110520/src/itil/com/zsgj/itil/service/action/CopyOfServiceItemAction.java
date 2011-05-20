package com.zsgj.itil.service.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceStatus;
import com.zsgj.itil.service.service.SCIColumnService;
import com.zsgj.itil.service.service.SCIDColumnService;
import com.zsgj.itil.service.service.ServiceItemService;

public class CopyOfServiceItemAction  extends BaseAction{
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private ServiceItemService serviceItemService = (ServiceItemService) getBean("serviceItemService");
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
		String id=request.getParameter("id");
		if(id!=null&&!id.equals("")){
			ServiceItem serviceItemOld=serviceItemService.findServiceItemById(id);
			ServiceItem curItem = serviceItemService.save(serviceItem);
			if(serviceItemOld.getServiceItemType()!=null&&!curItem.getServiceItemType().getId().equals(serviceItemOld.getServiceItemType().getId())){
				List <SCIDColumn> sCIDColumns=sCIDColumnService.findSCIDColumnByServiceItem(curItem);
				for(SCIDColumn sCIDColumn:sCIDColumns){
					sCIDColumnService.removeSCIDColumn(sCIDColumn);
				}
				ServiceItemType sit = curItem.getServiceItemType();
				List columns = sCIColumnService.findSCIColumnByServiceItemType(sit);
				List<SCIColumn> sciColumns = columns;
				sCIDColumnService.save(sciColumns,curItem);
			}else if(serviceItemOld.getServiceItemType()==null){
				ServiceItemType sit = curItem.getServiceItemType();
				List columns = sCIColumnService.findSCIColumnByServiceItemType(sit);
				List<SCIColumn> sciColumns = columns;
				sCIDColumnService.save(sciColumns,curItem);
			}
		}else{
			ServiceItem curItem = serviceItemService.save(serviceItem);
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
		List<SCIDColumn> sCIColumns =sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		request.setAttribute("serviceItem", serviceItem);
		request.setAttribute("sCIColumns", sCIColumns);
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
		List<SCIDColumn> sCIColumns =sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		request.setAttribute("serviceItem", serviceItem);
		request.setAttribute("sCIColumns", sCIColumns);
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
		List<SCIDColumn> sCIColumns =sCIDColumnService.findSCIDColumnByServiceItem(serviceItem);
		request.setAttribute("serviceItem", serviceItem);
		request.setAttribute("sCIColumns", sCIColumns);
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

}

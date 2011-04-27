package com.zsgj.itil.service.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.entity.ServiceStatus;
import com.zsgj.itil.service.service.ServiceItemService;
import com.zsgj.itil.service.service.ServiceItemTypeService;
import com.zsgj.itil.service.service.ServiceItemUserTableService;

public class SerivceItemUserTableAction extends BaseDispatchAction{
	private ServiceItemUserTableService siuts = (ServiceItemUserTableService) getBean("serviceItemUserTableService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");
	private Service service =getService();
	private ServiceItemTypeService siTypeService=(ServiceItemTypeService) getBean("serviceItemTypeService");
	private ServiceItemService serviceItemService = (ServiceItemService) getBean("serviceItemService");
	
	/**
	 * 为服务项发布列表页面提供数据
	 * @Methods Name list
	 * @Create In 2009-2-23 By lee
	 */
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String serviceItemName=request.getParameter("serviceItemName");
		String tableName = request.getParameter("tableName");
		
		Page page = siuts.findServiceItemUserTables(serviceItemName,tableName, pageNo, pageSize);
		request.setAttribute("page", page);
		
		return mapping.findForward("list");
	}
	
	/**
	 * 显示所有的服务项数据
	 * @Methods Name listAllServiceItem
	 * @Create In 2009-3-21 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward listServiceItem(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
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
		
		return mapping.findForward("listScid");
	}
	
	
	/**
	 * 添加新的服务项发布或修改原有的发布
	 * @Methods Name add
	 * @Create In 2009-2-23 By lee
	 */
	public ActionForward add(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String id = request.getParameter("id");
		String serviceItemId = request.getParameter("serviceItem");
		if(StringUtils.isNotBlank(id)&&!"".endsWith(id)){
			ServiceItemUserTable siut =siuts.findServiceItemUserTableById(id);
			request.setAttribute("siut", siut);
			SystemMainTable smt = siut.getSystemMainTable();
			List mainColumns = smcs.findSystemMainTableColumns(smt);
			request.setAttribute("mainColumns", mainColumns);
		}
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceItems = getService().findAll(ServiceItem.class);
		request.setAttribute("serviceItems", serviceItems);
		List pagePanels = getService().find(PagePanel.class, "groupFlag", Integer.valueOf(0));
		request.setAttribute("pagePanels", pagePanels);
		List groupPanels = getService().find(PagePanel.class, "groupFlag", Integer.valueOf(1));
		request.setAttribute("groupPanels", groupPanels);
		
		return mapping.findForward("info");
	}
	/**
	 * 添加新的服务项发布或修改原有的发布
	 * @Methods Name addTable
	 * @Create In 2009-2-23 By lee
	 */
	public ActionForward addTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		request.setAttribute("serviceItem", serviceItem);
		ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
		if(siut!=null){
			request.setAttribute("siut", siut);
			SystemMainTable smt = siut.getSystemMainTable();
			List mainColumns = smcs.findSystemMainTableColumns(smt);
			request.setAttribute("mainColumns", mainColumns);
		}
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceItems = getService().findAll(ServiceItem.class);
		request.setAttribute("serviceItems", serviceItems);
		List pagePanels = getService().find(PagePanel.class, "groupFlag", Integer.valueOf(0));
		request.setAttribute("pagePanels", pagePanels);
		List groupPanels = getService().find(PagePanel.class, "groupFlag", Integer.valueOf(1));
		request.setAttribute("groupPanels", groupPanels);
		
		return mapping.findForward("info2");
	}
	/**
	 * 选择已有需求表到服务项
	 * @Methods Name selectTable
	 * @Create In Apr 2, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward selectTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String serviceItemId = request.getParameter("serviceItemId");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		request.setAttribute("targetServiceItem", serviceItem);
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceItems = getService().findAll(ServiceItem.class);
		request.setAttribute("serviceItems", serviceItems);
		List serviceItemUserTables = getService().findAll(ServiceItemUserTable.class);
		request.setAttribute("serviceItemUserTables", serviceItemUserTables);
		
		return mapping.findForward("selectTable");
	}
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String siutId = request.getParameter("id");

		ServiceItemUserTable siut =siuts.findServiceItemUserTableById(siutId);
		request.setAttribute("siut", siut);
		SystemMainTable smt = siut.getSystemMainTable();
		List mainColumns = siuts.findRequireTableColumns(smt);
		request.setAttribute("mainColumns", mainColumns);
	
		List serviceItemTypes = getService().findAll(ServiceItemType.class);
		request.setAttribute("serviceItemTypes", serviceItemTypes);
		List serviceItems = getService().findAll(ServiceItem.class);
		request.setAttribute("serviceItems", serviceItems);
		List pagePanels = getService().find(PagePanel.class, "groupFlag", Integer.valueOf(0));
		request.setAttribute("pagePanels", pagePanels);
		List groupPanels = getService().find(PagePanel.class, "groupFlag", Integer.valueOf(1));
		request.setAttribute("groupPanels", groupPanels);
		
		return mapping.findForward("info");
				
		
	}
	
	/**
	 * 保存服务项对应需求主表
	 * @Methods Name saveTable
	 * @Create In 2009-2-23 By lee
	 */
	public ActionForward saveTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String id = request.getParameter("id"); 
        String systemMainTableId = request.getParameter("systemMainTable");
        String serviceItemId= request.getParameter("serviceItem");
        
 
		//新建需求主表
        SystemMainTable smt = (SystemMainTable) BeanUtil.getObject(request, SystemMainTable.class);
        if(StringUtils.isNotBlank(systemMainTableId)){
        	smt.setId(Long.valueOf(systemMainTableId));
        }
		//保存需求主表
		siuts.saveSystemMainTable(smt);
		
		//接收服务项需求表
		ServiceItemUserTable siut = (ServiceItemUserTable) BeanUtil.getObject(request, ServiceItemUserTable.class);
		siut.setSystemMainTable(smt);
		siut.setClassName(smt.getClassName());
		siut.setTableName(smt.getTableName());
		
		ServiceItem serviceItem = siut.getServiceItem();
		if(serviceItem!=null){
			Long sidId = serviceItem.getId();
			serviceItem = (ServiceItem)super.getService().find(ServiceItem.class, String.valueOf(sidId));
			siut.setServiceItem(serviceItem);
			siut.setServiceItemType(serviceItem.getServiceItemType());
		}

		super.getService().save(siut);
		
		return HttpUtil.redirect("serviceItemUserTableAction.do?methodCall=addTable&serviceItemId="+serviceItemId); 
	}
	/**
	 * 保存已有需求主表到指定服务项需求表关系实体
	 * @Methods Name saveExistentTable
	 * @Create In Apr 2, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveExistentTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String serviceItemId = request.getParameter("serviceItemId");
		String sourceServiceItemId=request.getParameter("sourceServiceItem");
        String tableId = request.getParameter("tableId");
        SystemMainTable smt = (SystemMainTable) service.find(SystemMainTable.class, tableId,true);
        ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
        ServiceItem sourceServiceItem = serviceItemService.findServiceItemById(sourceServiceItemId);
        ServiceItemUserTable sourceSiut= siuts.findServiceItemUserTableByServiceItem(sourceServiceItem);
		ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
		if(siut==null){
			siut = new ServiceItemUserTable();
		}
		siut.setSystemMainTable(smt);
		siut.setServiceItem(serviceItem);
		siut.setSystemMainTable(smt);
		siut.setPagePanel(sourceSiut.getPagePanel());
		siut.setPageListPanel(sourceSiut.getPageListPanel());
		siut.setGroupPanel(sourceSiut.getGroupPanel());
		siut.setServiceItemType(serviceItem.getServiceItemType());
		siut.setClassName(smt.getClassName());
		siut.setTableName(smt.getTableName());
		super.getService().save(siut);
		
		return HttpUtil.redirect("serviceItemUserTableAction.do?methodCall=addTable&serviceItemId="+serviceItemId); 
	}
	/**
	 * 发布服务项需求主表
	 * @Methods Name deployTable
	 * @Create In 2009-2-25 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward deployTable(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ServiceItem scid = null;
		String serviceItemId = request.getParameter("serviceItem");
		if(StringUtils.isNotBlank(serviceItemId)){
			scid = (ServiceItem) super.getService().find(ServiceItem.class, serviceItemId, true);
		}
		
		ServiceItemUserTable siut = null;
		String siutId = request.getParameter("id");
		if(StringUtils.isNotBlank(siutId)){
			siut = (ServiceItemUserTable) super.getService().find(ServiceItemUserTable.class, siutId, true);
		}
		
		SystemMainTable smt = siut.getSystemMainTable();
		//smt = (SystemMainTable) super.getService().find(SystemMainTable.class, String.valueOf(smt.getId()));
		String tableName = smt.getTableName();
		String className = smt.getClassName();
		int lastDot = className.lastIndexOf(".");
		String sourcePkg = className.substring(0, lastDot);
		String sourceClass = className.substring(lastDot+1);
		
		this.siuts.saveSystemMainTableDeploy(scid, smt);
		
//		SystemMainTable smtEvent = this.siuts.findUserTableEvent(smt);
//		if(smtEvent!=null){
//			siuts.saveEventTableByMainTable(smt);
//			this.siuts.saveSystemMainTableDeploy(scid, smtEvent);
//		}
		
		SystemMainTable smtEvent = siuts.findUserTableEvent(smt);
		if(smtEvent!=null){
			this.siuts.saveSystemMainTableDeploy(scid, smtEvent);
		}else{
			smtEvent = siuts.saveEventTableByMainTable(smt);
			this.siuts.saveSystemMainTableDeploy(scid, smtEvent);
		}
		
		return HttpUtil.redirect("serviceItemUserTableAction.do?methodCall=toForm&id="+siut.getId()); 
	}
	
	public ActionForward findServiceItemBySCIT(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String serviceItemTypeId=request.getParameter("serviceItemType");
		ServiceItemType serviceItemType=siTypeService.findServiceItemTypeById(serviceItemTypeId);
		List list=service.find(ServiceItem.class, "serviceItemType", serviceItemType);
		String json = "";
		for(int i=0; i< list.size(); i++){
		ServiceItem serviceItem = (ServiceItem)list.get(i);			
		Long id = serviceItem.getId();
		String name = serviceItem.getName();
		json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
		}
		json = "{success:true,data:[" + json.substring(0, json.length()-1) + "]}";
		response.setCharacterEncoding("gbk");
		response.getWriter().write(json);
		//response.getWriter().flush();
		return null;
	}
	public ActionForward findTableBySCID(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String serviceItemId=request.getParameter("serviceItem");
		ServiceItem serviceItem = serviceItemService.findServiceItemById(serviceItemId);
		ServiceItemUserTable siut = siuts.findServiceItemUserTableByServiceItem(serviceItem);
		SystemMainTable smt = siut.getSystemMainTable();
		Long tableId = smt.getId();
		String tableName=smt.getTableName();
		String tableCnName = smt.getTableCnName();
		String json = "{success:true,tableName:'"+tableName+"',tableCnName:'"+tableCnName+"',tableId:"+tableId+"}";
		response.setCharacterEncoding("gbk");
		response.getWriter().write(json);
		return null;
	}
}

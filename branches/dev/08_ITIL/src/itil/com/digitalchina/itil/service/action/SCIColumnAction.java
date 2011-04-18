package com.digitalchina.itil.service.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.metadata.entity.PropertyType;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumnType;
import com.digitalchina.info.appframework.metadata.entity.ValidateType;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.itil.service.entity.SCIColumn;
import com.digitalchina.itil.service.entity.ServiceItemType;
import com.digitalchina.itil.service.service.SCIColumnService;
import com.digitalchina.itil.service.service.ServiceItemTypeService;

public class SCIColumnAction extends BaseDispatchAction{
	private SystemColumnService scs = (SystemColumnService) getBean("systemColumnService");
	private ServiceItemTypeService serviceItemTypeService=(ServiceItemTypeService)getBean("serviceItemTypeService");
	private SCIColumnService sCIColumnService=(SCIColumnService)getBean("sCIColumnService");
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		
		
		return mapping.findForward("list");
	}
	
	public ActionForward add(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		String serviceItemTypeId=request.getParameter("serviceItemType");
		ServiceItemType serviceItemType=serviceItemTypeService.findServiceItemTypeById(serviceItemTypeId);
		request.setAttribute("serviceItemType", serviceItemType);
		String id=request.getParameter("id");
		if(id!=null&&id.length()>0){
			SCIColumn sCIColumn=sCIColumnService.findSCIColumnById(id);
			request.setAttribute("detail", sCIColumn);
			SystemMainTable ftable=sCIColumn.getForeignTable();
			List fcolumns = scs.findSystemTableColumns(ftable);
			request.setAttribute("fcolumns",fcolumns);
		}
		
		List propertyTypes = getService().findAll(PropertyType.class);
		request.setAttribute("propertyTypes", propertyTypes);
		
		List systemMainTableColumnTypes = getService().findAll(SystemMainTableColumnType.class);
		request.setAttribute("systemMainTableColumnTypes", systemMainTableColumnTypes);
		
		List validateTypes = getService().findAll(ValidateType.class);
		request.setAttribute("validateTypes", validateTypes);
		
		List sysMainTables = getService().findAllBy(SystemMainTable.class, "tableCnName", true);
		request.setAttribute("sysMainTables", sysMainTables);
		
		return mapping.findForward("add");
	}
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SCIColumn sCIColumn=(SCIColumn) BeanUtil.getObject(request, SCIColumn.class);
		sCIColumnService.saveSCIColumn(sCIColumn);
		String serviceItemTypeId=request.getParameter("serviceItemType");
		return HttpUtil.redirect("ServiceItemTypeAction.do?methodCall=add&id="+serviceItemTypeId);
	}
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String serviceItemTypeId=request.getParameter("serviceItemType");
		String[] ids = request.getParameterValues("id");
		sCIColumnService.removeSCIColumnByIds(ids);
		return HttpUtil.redirect("ServiceItemTypeAction.do?methodCall=add&id="+serviceItemTypeId);
	}
}

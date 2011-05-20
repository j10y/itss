package com.zsgj.itil.service.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.service.entity.SCIColumn;
import com.zsgj.itil.service.entity.ServiceItemType;
import com.zsgj.itil.service.service.SCIColumnService;
import com.zsgj.itil.service.service.ServiceItemTypeService;

public class ServiceItemTypeAction extends BaseDispatchAction{
	
	private ServiceItemTypeService serviceItemTypeService=(ServiceItemTypeService)getBean("serviceItemTypeService");
	private SCIColumnService sCIColumnService=(SCIColumnService)getBean("sCIColumnService");
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String name=request.getParameter("name");
		Page page = serviceItemTypeService.findServiceItemTypeByName(name, pageNo, pageSize);
		request.setAttribute("page", page);
		
		return mapping.findForward("list");
	}
	
	public ActionForward add(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		String id=request.getParameter("id");
		if(id!=null&&id.length()>0){
			ServiceItemType scit=serviceItemTypeService.findServiceItemTypeById(id);
			List <SCIColumn> sCIColumns= sCIColumnService.findSCIColumnByServiceItemType(scit);
			request.setAttribute("sCIColumns", sCIColumns);
			request.setAttribute("serviceItemType", scit);
		}
		return mapping.findForward("add");
	}
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		ServiceItemType scit=(ServiceItemType) BeanUtil.getObject(request, ServiceItemType.class);
		ServiceItemType serviceItemType=serviceItemTypeService.saveServiceItemType(scit);
		request.setAttribute("serviceItemType", serviceItemType);
		return HttpUtil.redirect("ServiceItemTypeAction.do?methodCall=add&id="+serviceItemType.getId());
	}
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String[] ids = request.getParameterValues("id");
		serviceItemTypeService.removeServiceItemTypeByIds(ids);
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		return HttpUtil.redirect("ServiceItemTypeAction.do?methodCall=list&pageNo="+pageNo);
	}
}

package com.zsgj.info.appframework.template.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.template.entity.SystemMenuTemplate;
import com.zsgj.info.appframework.template.service.SystemTemplateMenuService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 系统菜单模板管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class SystemMenuTemplateAction extends BaseDispatchAction{
	
	
	private SystemTemplateMenuService stms = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	
	public ActionForward listTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List sysTemplateMenus = stms.findSystemMenuTemplates();
		request.setAttribute("sysTemplateMenus", sysTemplateMenus);
		
		return mapping.findForward("listTemplate");
	}
	
	public ActionForward templateDetail(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List sysTemplateMenus = stms.findSystemMenuTemplates();
		request.setAttribute("sysTemplateMenus", sysTemplateMenus);
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			SystemMenuTemplate smt = (SystemMenuTemplate) getService().find(SystemMenuTemplate.class, id);
			request.setAttribute("detail", smt);
		}
		return mapping.findForward("detail");
	}

	public ActionForward saveTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		SystemMenuTemplate result = null;
		
		SystemMenuTemplate smt = (SystemMenuTemplate) BeanUtil.getObject(request, SystemMenuTemplate.class);
		
		String sysTemplateMenuCopy = request.getParameter("sysTemplateMenuCopy");
		if(StringUtils.isNotBlank(sysTemplateMenuCopy)){
			result = stms.saveSystemTemplate4Copy(smt, sysTemplateMenuCopy);
		}else{
			result = stms.saveSystemMenuTemplate(smt);
		}
		
		
		return HttpUtil.redirect("sysTemplateManage.do?methodCall=templateDetail&id="+result.getId());
	}

	
	public ActionForward removeTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String[] ids = request.getParameterValues("id");
		stms.removeSystemMenuTemplate(ids);
		
		return HttpUtil.redirect("sysTemplateManage.do?methodCall=listTemplate");
	}

	
	
}

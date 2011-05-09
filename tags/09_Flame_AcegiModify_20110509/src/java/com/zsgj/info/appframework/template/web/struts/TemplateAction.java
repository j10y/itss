package com.zsgj.info.appframework.template.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.template.entity.Template;
import com.zsgj.info.appframework.template.service.TemplateService;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.service.DepartmentService;
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
public class TemplateAction extends BaseDispatchAction{
	
	
	private TemplateService templateService = (TemplateService)getBean("templateService");
	private DepartmentService ds = (DepartmentService) getBean("deptService");

	
	public ActionForward listTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List templateList = templateService.findAllTemplates();
		request.setAttribute("templates", templateList);
		
		return mapping.findForward("listTemplate");
	}
	
	public ActionForward templateDetail(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List depts = ds.findDeptAll();
		request.setAttribute("depts", depts);
		
		List users =  templateService.findAllUsers();
		request.setAttribute("users", users);
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Template smt = (Template) getService().find(Template.class, id);
			request.setAttribute("detail", smt);
		}
		return mapping.findForward("detail");
	}

	public ActionForward saveTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Template template = (Template) BeanUtil.getObject(request, Template.class);
		template.setCreateUser(UserContext.getUserInfo());
		template.setCreateDate(new java.util.Date());
		templateService.saveTemplate(template);
		
		return HttpUtil.redirect("templateManage.do?methodCall=templateDetail&id="+template.getId());
	}

	
	public ActionForward removeTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String[] ids = request.getParameterValues("id");
		templateService.removeTemplate(ids);
		
		return HttpUtil.redirect("deptTemplateManage.do?methodCall=listTemplate");
	}

	
	
}

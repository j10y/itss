package com.digitalchina.info.appframework.template.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.template.entity.DeptMenuTemplate;
import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.service.DeptTemplateMenuService;
import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.service.DepartmentService;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

/**
 * 系统菜单模板管理
 * @Class Name SystemMainTableAction
 * @Author peixf
 * @version 3.0
 * @Create In 2008-7-25
 */
public class DeptMenuTemplateAction extends BaseDispatchAction{
	
	private DepartmentService ds = (DepartmentService) getBean("deptService");
	private SystemTemplateMenuService stms = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	private DeptTemplateMenuService dtms = (DeptTemplateMenuService) getBean("deptTemplateMenuService");
	
	public ActionForward listTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		List deptTemplateMenus = dtms.findDeptMenuTemplates();
		request.setAttribute("deptTemplateMenus", deptTemplateMenus);
		
		return mapping.findForward("listTemplate");
	}
	
	public ActionForward templateDetail(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	//通过根找其部门及下面的子部门.
		List depts = ds.findDeptAll();
		//String rootDeptCode = PropertiesUtil.getProperties("system.dept.rootdeptcode", "50000075");
		//List dept = ds.findRootDept(rootDeptCode);
		request.setAttribute("depts", depts);
		
		List systemMenuTemplates = stms.findSystemMenuTemplates();
		request.setAttribute("systemMenuTemplates", systemMenuTemplates);
		
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			DeptMenuTemplate smt = (DeptMenuTemplate) getService().find(DeptMenuTemplate.class, id);
			request.setAttribute("detail", smt);
		}
		
		
		
		return mapping.findForward("detail");
	}
	
	public ActionForward removeTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		String[] ids = request.getParameterValues("id");
		dtms.removeDeptMenuTemplate(ids);
		
		return HttpUtil.redirect("deptTemplateManage.do?methodCall=listTemplate");
	}

	public ActionForward saveTemplate(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		DeptMenuTemplate dmt = (DeptMenuTemplate) BeanUtil.getObject(request, DeptMenuTemplate.class);
		SystemMenuTemplate smtNew = dmt.getSystemMenuTemplate();
		if(smtNew==null){
			throw new ApplicationException("请选择系统菜单模板");
		}
		if(dmt.getId()!=null){
			DeptMenuTemplate dmtOld = (DeptMenuTemplate) getService().find(DeptMenuTemplate.class, String.valueOf(dmt.getId()));
			SystemMenuTemplate smtOld = dmtOld.getSystemMenuTemplate();
			if(smtNew.getId().intValue()!= smtOld.getId().intValue()){
				dtms.saveDeptSystemTemplateChange(dmt, smtNew);
			}
		}
		dtms.saveDeptMenuTemplate(dmt);
		
		return HttpUtil.redirect("deptTemplateManage.do?methodCall=listTemplate");
	}

	
	
	
	
}

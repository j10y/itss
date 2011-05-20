package com.zsgj.info.framework.security.web.adapter.struts.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
 
/**
 * 资源管理action
 * @Class Name ResourceAction
 * @Author peixf
 * @Create In 2008-3-14
 */
public class ModuleAction extends BaseDispatchAction{
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	
	/**
	 * 显示所有资源
	 * @Methods Name list
	 * @Create In 2008-3-14 By peixf
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception ActionForward
	 */
	public ActionForward listModules(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		
		return mapping.findForward("listModules");
	}
	
	/**
	 * 修改指定资源
	 * @Methods Name toEdit
	 * @Create In 2008-3-14 By peixf
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toEditModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		
		List parentModules = sms.findModuleTopLevel();
		request.setAttribute("parentModules", parentModules);

		String moduleId = request.getParameter("id");
		Module module = sms.findModuleById(moduleId);
		request.setAttribute("module", module);
		
		return mapping.findForward("listModules"); //本页快速修改
	}
	
	/**
	 * 去添加一个资源
	 * @Methods Name toAddResource
	 * @Create In 2008-3-14 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toAddModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		
		List parentModules = sms.findModuleTopLevel();
		request.setAttribute("parentModules", parentModules);
		
		return mapping.findForward("listModules"); //本页快速修改
	}
	
	/**
	 * 保存资源
	 * @Methods Name saveResource
	 * @Create In 2008-3-14 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward saveModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		Module module = (Module) BeanUtil.getObject(request, Module.class);
		sms.saveModule(module);
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
	
		return HttpUtil.redirect("moduleManage.do?methodCall=listModules");
	
	}
	
	public ActionForward removeModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String moduleId = request.getParameter("id");
		sms.removeModule(moduleId);
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		
		return HttpUtil.redirect("moduleManage.do?methodCall=listModules");
	}

}

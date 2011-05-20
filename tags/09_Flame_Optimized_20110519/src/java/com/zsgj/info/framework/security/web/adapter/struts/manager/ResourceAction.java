package com.zsgj.info.framework.security.web.adapter.struts.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
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
public class ResourceAction extends BaseDispatchAction{
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
	public ActionForward listResources(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
		
		return mapping.findForward("listRes");
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
	public ActionForward toEditResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
		
		List modules = sms.findModuleTopLevel();
		request.setAttribute("modules", modules);
		
		String resourceId = request.getParameter("id");
		Resource res = sms.findResourceById(resourceId);
		request.setAttribute("resource", res);
		
		return mapping.findForward("listRes"); //本页快速修改
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
	public ActionForward toAddResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
		
		List modules = sms.findModuleTopLevel();
		request.setAttribute("modules", modules);
		
		
		
		return mapping.findForward("listRes"); //本页快速修改
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
	public ActionForward saveResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		Resource res = (Resource) BeanUtil.getObject(request, Resource.class);
		String moduleId = request.getParameter("moduleId");
		if(moduleId!=null&& !moduleId.equals("")){
			Module module = sms.findModuleById(moduleId); 
			res.setModule(module);
		}

		sms.saveResource(res);
		
		List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
	
		return HttpUtil.redirect("resourceManage.do?methodCall=listResources");
	
	}
	
	public ActionForward removeResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String resourceId = request.getParameter("id");
		sms.removeResource(resourceId);
		
		List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
		
		return HttpUtil.redirect("resourceManage.do?methodCall=listResources");
	}

}

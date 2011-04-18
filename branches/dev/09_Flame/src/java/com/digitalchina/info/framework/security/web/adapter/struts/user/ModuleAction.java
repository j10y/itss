package com.digitalchina.info.framework.security.web.adapter.struts.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

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
	
		/*List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		return mapping.findForward("listModules");*/
		
		List modules = sms.findModulesAllForPage();		
		JSONArray jsonObject = JSONArray.fromObject(modules);
		//System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",modules:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
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
		
		/*Module module = (Module) BeanUtil.getObject(request, Module.class);
		sms.saveModule(module);
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
	
		return HttpUtil.redirect("moduleManage.do?methodCall=listModules");*/
		
		Module module = new Module();
		String moduleId = request.getParameter("id");
		String name = request.getParameter("name");
		String serviceKeyName = request.getParameter("serviceKeyName");
		String url = request.getParameter("url");
		String descn = request.getParameter("descn");
		String pModuleId = request.getParameter("pModuleId");
		
		module.setName(name);
		module.setServiceKeyName(serviceKeyName);
		module.setUrl(url);
		module.setDescn(descn);
		if(!"".equals(pModuleId)){
			Module parentModule = sms.findModuleById(pModuleId);
			module.setParentModule(parentModule);
		}

		Module module2 = sms.saveModule(module);
		
		System.out.println("new module Id :"+module2.getId());
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",id:"+ module2.getId() +"}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
	
		return null;
	}
	
	public ActionForward modifyModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		
		String moduleId = request.getParameter("id");
		String name = request.getParameter("name");
		String serviceKeyName = request.getParameter("serviceKeyName");
		String url = request.getParameter("url");
		String descn = request.getParameter("descn");
		String pModuleId = request.getParameter("pModuleId");
		
		Module module = sms.findModuleById(moduleId);
		module.setName(name);
		module.setServiceKeyName(serviceKeyName);
		module.setUrl(url);
		module.setDescn(descn);
		if(!"".equals(pModuleId)){
			Module parentModule = sms.findModuleById(pModuleId);
			module.setParentModule(parentModule);
		}

		sms.saveModule(module);		
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	public ActionForward removeModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		/*String moduleId = request.getParameter("id");
		sms.removeModule(moduleId);
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		
		return HttpUtil.redirect("moduleManage.do?methodCall=listModules");*/
		
		String moduleId = request.getParameter("id");
		sms.removeModule(moduleId);
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	/**
	 * 查找父模块
	 * @Methods Name findParentModules
	 * @Create In Apr 9, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findParentModules(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List parentModules = sms.findModuleTopLevelForPage();
		JSONArray jsonObject = JSONArray.fromObject(parentModules);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",parentModules:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return null;
	}
	
	/**
	 * 查找模块
	 * @Methods Name findModule
	 * @Create In Apr 9, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findModule(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String moduleId = request.getParameter("id");
		
		List module = sms.findModuleByIdForPage(moduleId);
		JSONArray jsonObject = JSONArray.fromObject(module);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",module:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return null;
	}
	/**
	 * 通过父模块Id来获得父模块,供modules_listN.jsp同步请求后台时调用
	 * @Methods Name getModule
	 * @Create In Apr 10, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward getModuleName(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String moduleId = request.getParameter("id");
		
		Module module = sms.findModuleById(moduleId);
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",name:"+"\""+ module.getName()+"\""+ "}");
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		return null;
	}
}

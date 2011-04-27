package com.zsgj.info.framework.security.web.adapter.struts.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.service.SecurityManageService;
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
	//private String jsonString;
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
	
		List resources = sms.findResourcesAllForPage();
		//List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
		
		JSONArray jsonObject = JSONArray.fromObject(resources);
		//System.out.println(jsonObject.toString());
		
		//this.setJsonString("{success:" + true + ",jsonString:"+ jsonObject.toString() + "}");
		try {
			//httpServletResponse.getWriter().write(jsonString);
			httpServletResponse.getWriter().write("{success:" + true + ",jsonString:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		//return mapping.findForward("listRes"); 
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
		
		List modules = sms.findModulesAll();
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
		
		List modules = sms.findModulesAll();
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
		
		/*Resource res = (Resource) BeanUtil.getObject(request, Resource.class);
		String moduleId = request.getParameter("moduleId");
		if(moduleId!=null&& !moduleId.equals("")){
			Module module = sms.findModuleById(moduleId); 
			res.setModule(module);
		}

		Resource resource = sms.saveResource(res);
		
		List resources = sms.findResourcesAllForPage();
		request.setAttribute("resources", resources);
		JSONArray jsonObject = JSONArray.fromObject(resource);
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ ",id:"+resource.getId()+"}");
			out.flush();
			//httpServletResponse.getWriter().write("{success:" +true+ ",resource:"+jsonObject.toString() +"}");
		} catch (IOException e) {
			e.printStackTrace();
			out.close();
		}
		return null;
		//return HttpUtil.redirect("resourceManage.do?methodCall=listResources");
		*/
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String className = request.getParameter("className");
		String methodName = request.getParameter("methodName");
		String moduleId = request.getParameter("moduleId");
		
		Resource res = new Resource();
		res.setName(name);
		res.setType(type);
		res.setClassName(className);
		res.setMethodName(methodName);
		if(moduleId!=null&& !moduleId.equals("")){
			Module module = sms.findModuleById(moduleId); 
			res.setModule(module);
		}
		
		Resource resource = sms.saveResource(res);
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ ",id:"+resource.getId()+"}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			out.close();
		}
		return null;
		//return HttpUtil.redirect("resourceManage.do?methodCall=listResources");
	}
	
	public ActionForward getMoudle(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List moduleList = sms.findModulesAllForPage();
		
		JSONArray jsonObject = JSONArray.fromObject(moduleList);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",moudleList:"+ jsonObject.toString() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public ActionForward modifyResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		
		String resouceId = request.getParameter("id");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String className = request.getParameter("className");
		String methodName = request.getParameter("methodName");
		
		String moduleId = request.getParameter("moduleId");	
		Resource res = sms.findResourceById(resouceId);
		res.setName(name);
		res.setType(type);
		res.setClassName(className);
		res.setMethodName(methodName);

		if(!"".equals(moduleId)){
			Module module = sms.findModuleById(moduleId);
			res.setModule(module);
		}

		Resource resource = sms.saveResource(res);
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	public ActionForward removeResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String resourceId = request.getParameter("id");
		sms.removeResource(resourceId);
		
		List resources = sms.findResourcesAll();
		request.setAttribute("resources", resources);
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return HttpUtil.redirect("resourceManage.do?methodCall=listResources");
	}
	
	public ActionForward findResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String resourceId = request.getParameter("id");
		List resource = sms.findResourceByIdForPage(resourceId);
		
		JSONArray jsonObject = JSONArray.fromObject(resource);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",resource:"+ jsonObject.toString() + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ActionForward findResourceName(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String resourceId = request.getParameter("id");
		Resource resource = sms.findResourceById(resourceId);
		
		try {
			httpServletResponse.getWriter().write("{success:" + true + ",name:"+"\""+ resource.getName()+"\""+ "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ActionForward getResource(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		List resourceList = sms.findResourcesAllForPage();
		JSONArray jsonObject = JSONArray.fromObject(resourceList);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.getWriter().write("{\"success\":true,\"resourceList\":"+ jsonObject.toString() + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
}

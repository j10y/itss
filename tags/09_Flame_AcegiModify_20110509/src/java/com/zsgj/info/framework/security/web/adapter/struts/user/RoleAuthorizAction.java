package com.zsgj.info.framework.security.web.adapter.struts.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

public class RoleAuthorizAction extends BaseDispatchAction{
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
	public ActionForward listRoleAuthorizations(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		return mapping.findForward("listRoleAuthorizations");
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
	public ActionForward toEditRoleAuthorizations(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		return mapping.findForward("listRoleAuthorizations"); //本页快速修改
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
	public ActionForward toAddRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		List modules = sms.findModulesAll();
		request.setAttribute("modules", modules);
		
		
		
		return mapping.findForward("listRoleAuthorizations"); //本页快速修改
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
	public ActionForward saveRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		Role role = (Role) BeanUtil.getObject(request, Role.class);
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String[] au_checks = request.getParameterValues("au_check");
		String[] au_check_name = request.getParameterValues("au_check_name");
		Set auSet = new HashSet();
		for(int i=0; i<au_check_name.length; i++){
			String item = au_check_name[i];
			StringTokenizer token = new StringTokenizer(item, "|");
			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			//String[] splits = item.split("|");
			if(trueOrFalse.equals("true")){
				Authorization auth = sms.findAuthorizationById(itemId);
				auSet.add(auth);
			}
		}
		role.setAuthorizations(auSet);
		sms.saveRole(role);
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
	
		return HttpUtil.redirect("roleManage.do?methodCall=showRoleDetail&id="+id);
	
	}
	
	public ActionForward removeRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String roleId = request.getParameter("id");
		sms.removeRole(roleId);
		
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		return HttpUtil.redirect("roleManage.do?methodCall=listRoleAuthorizations");
	}

}
package com.zsgj.info.framework.security.web.adapter.struts.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.JsonUtil;
 
public class RoleAction extends BaseDispatchAction{
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private DepartmentService ds = (DepartmentService) getBean("deptService");
	
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
	public ActionForward listRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		return mapping.findForward("listRoles");
	}
	
	public ActionForward toEditRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List depts = ds.findDeptAll();
		request.setAttribute("depts", depts);
		
		List authorizationsAll = sms.findAuthorizationsOrderByModule();
		
		String roleId = request.getParameter("id");
		Role role = sms.findRoleById(roleId);
		request.setAttribute("role", role);
		
//		Set deptTmps = new HashSet();
//		Department dept = role.getDepartment();
		/*if(dept!=null){
			deptTmps = dtms.findDeptMenuTemplate(dept);
			Department deptDc = ds.findDepartmentById(Long.valueOf(1101L));
			List listDc = dtms.findDeptMenuTemplate(deptDc);
			deptTmps.addAll(listDc);
			
		}else{
			Department deptDc = ds.findDepartmentById(Long.valueOf(1101));
			deptTmps = dtms.findDeptMenuTemplate(deptDc);
		}
		if(deptTmps.isEmpty()){
			Department deptDc = ds.findDepartmentById(Long.valueOf(1101));
			deptTmps = dtms.findDeptMenuTemplate(deptDc);
		}*/
		
		Set authorizSet = role.getAuthorizations();
		Iterator iter = authorizSet.iterator();
		while(iter.hasNext()){
			Authorization item = (Authorization) iter.next();
			item.setChecked(true);
		}
		//request.setAttribute("rolesAuthorizations", authorizSet);
		for(int i=0; i<authorizationsAll.size(); i++){
			Authorization item = (Authorization) authorizationsAll.get(i);
			if(authorizSet.contains(item)){
				item.setChecked(true);
			}
		}
		request.setAttribute("authorizations", authorizationsAll);
		
		List modules = sms.findModuleWithAuthorizations();
		request.setAttribute("modules", modules);
		
		return mapping.findForward("roleDetail");
	}
	
	/**
	 * 保存资源
	 * @Methods Name saveRoles
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
//		String[] au_checks = request.getParameterValues("au_check");
		String[] au_check_name = request.getParameterValues("au_check_name");
		Set auSet = new HashSet();
		for(int i=0; i<au_check_name.length; i++){
			String item = au_check_name[i];
			StringTokenizer token = new StringTokenizer(item, "|");
			String itemId = token.nextToken();
			String trueOrFalse = token.nextToken();
			if(trueOrFalse.equals("true")){
				Authorization auth = sms.findAuthorizationById(itemId);
				auSet.add(auth);
			}
		}
		role.setAuthorizations(auSet);
		sms.saveRole(role);
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
	
		return HttpUtil.redirect("roleManage.do?methodCall=toEditRoles&id="+role.getId());
	
	}
	
	public ActionForward removeRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String roleId = request.getParameter("id");
		sms.removeRole(roleId);
		
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		return HttpUtil.redirect("roleManage.do?methodCall=listRoles");
	}

}
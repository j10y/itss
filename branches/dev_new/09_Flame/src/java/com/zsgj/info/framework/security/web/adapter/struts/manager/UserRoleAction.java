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

import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.util.BeanUtil;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.web.json.JsonUtil;
 
public class UserRoleAction extends BaseDispatchAction{
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
	public ActionForward listUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		List users = sms.findUserByQueryParams(userName, realName);
		request.setAttribute("users", users);
		
		return mapping.findForward("listUsers");
	}
	
	/**
	 * 修改用户及其角色信息
	 * @Methods Name toEditUsers
	 * @Create In 2008-3-18 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return ActionForward
	 * @throws Exception ActionForward
	 */
	public ActionForward toEditUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		List depts = ds.findDeptAll();
		request.setAttribute("depts", depts);
		
		List rolesAll = sms.findRolesAll();
		
		String userId = request.getParameter("id");
		UserInfo user = sms.findUserById(userId);
		request.setAttribute("user", user);
		
		Set rolesSet = user.getRoles();
		Iterator iter = rolesSet.iterator();
		while(iter.hasNext()){
			Role item = (Role ) iter.next();
			item.setChecked(true);
		}
		for(int i=0; i<rolesAll.size(); i++){
			Role item = (Role) rolesAll.get(i);
			if(rolesSet.contains(item)){
				item.setChecked(true);
			}
		}
		
		//List userCorporations = cs.findUserSelectCorporations(user);
		//request.setAttribute("userSelectCorporations", userCorporations);
		
		//List corporations = getService().findAllBy(Corporation.class, "corporationName", true);
		//request.setAttribute("corporations", corporations);
		
		//corporations.removeAll(userCorporations);
		
		
		
		request.setAttribute("roles", rolesAll);

		return mapping.findForward("userDetail");
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
	public ActionForward toAddUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List rolesAll = sms.findRolesAll();
		request.setAttribute("roles", rolesAll);
		
		List depts = ds.findDeptAll();
		request.setAttribute("depts", depts);
		
		return mapping.findForward("userDetail"); 
	}
	
	
	public ActionForward findRoleByDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List depts = ds.findDeptAll();
		request.setAttribute("depts", depts);
		
		UserInfo user = (UserInfo) BeanUtil.getObject(request, UserInfo.class);
		request.setAttribute("user", user);
				
		String departCode = request.getParameter("department");
		Department dept = ds.findDepartmentById(Long.valueOf(departCode));
		request.setAttribute("dept", dept);
		
		List roles = sms.findRoleByDept(dept);
		request.setAttribute("roles", roles);
		
		/*String result = JsonUtil.toJSONString(res);

		response.setCharacterEncoding("gbk");
		response.getWriter().write("{success:true,data:"+result+"}");
		response.getWriter().flush();	*/	
		//return null;
		return mapping.findForward("userDetail"); 
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
	public ActionForward saveUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		UserInfo user = (UserInfo) BeanUtil.getObject(request, UserInfo.class);
		String[] role_check_name = request.getParameterValues("role_check_name");
//		Set changedRoles = new HashSet();
		if(role_check_name!=null&& role_check_name.length>0){
			Set rolesSet = new HashSet();
			for(int i=0; i<role_check_name.length; i++){
				String item = role_check_name[i];
				StringTokenizer token = new StringTokenizer(item, "|");
				String itemId = token.nextToken();
				String trueOrFalse = token.nextToken();
				if(trueOrFalse.equals("true")){
					Role role = sms.findRoleById(itemId);
					rolesSet.add(role);
				}
			}
			user.setRoles(rolesSet);
		}
		
		sms.saveUserInfoWithRoles(user);

		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
	
		return HttpUtil.redirect("userRoleManage.do?methodCall=toEditUsers&id="+user.getId());
	
	}
	
	public ActionForward removeUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String userId = request.getParameter("id");
		sms.removeUser(userId);
		
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		return HttpUtil.redirect("userRoleManage.do?methodCall=listUsers");
	}

}

/*String userName = request.getParameter("userName");
String password = request.getParameter("password");
String itcode = request.getParameter("itcode");
String realName = request.getParameter("realName");
String email = request.getParameter("email");

String departCode = request.getParameter("department");
Department dept = ds.findDepartmentById(Long.valueOf(departCode));

String enabled = request.getParameter("enabled");
String specialUser = request.getParameter("specialUser");*/
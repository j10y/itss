package com.zsgj.itil.actor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.itil.actor.entity.Customer;
import com.zsgj.itil.actor.entity.CustomerOutUserInfo;
import com.zsgj.itil.actor.service.CustomerOutUserService;


public class CustomerOutUserAction extends BaseDispatchAction{
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private Service service = (Service) getBean("baseService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private DepartmentService deptService = (DepartmentService) getBean("deptService");
	private CustomerOutUserService cous = (CustomerOutUserService) getBean("customerOutUserService");
	
	/**
	 * 显示所有资源
	 * @Methods Name list
	 * @Create In 2008-3-14 By Vincent
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
		
		int pageNo = HttpUtil.getInt(request, "start", 1);
		String userName = HttpUtil.ConverUnicode(request.getParameter("userName"));
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("realName", realName);
		
		Page page = cous.findCustomerOutUser(map, pageNo, pageSize, "userName", true);
		
		List queryList = page.list();
		request.setAttribute("users", queryList);
		
		List listData = metaDataManager.getEntityMapDataForList(UserInfo.class, queryList);
		String json = convertListData2JsonString(listData, page);
		
		//System.out.println("用户管理，用户列表："+json);
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write(json);
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 修改用户及其角色信息
	 * @Methods Name toEditUsers
	 * @Create In 2008-3-18 By Vincent
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
		request.setAttribute("roles", rolesAll);

		return mapping.findForward("userDetail");
	}
	
	
	/**
	 * 去添加一个资源
	 * @Methods Name toAddResource
	 * @Create In 2008-3-14 By Vincent
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
		
		
		return mapping.findForward("userDetail"); 
	}
	
	
	public ActionForward findRoleByDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String deptCode = request.getParameter("deptCode");
		Department dept = deptService.findDepartmentById(new Long(deptCode));
		List<Role> roles = sms.findRoleByDept(dept);
		//JSONArray jsonObject = JSONArray.fromObject(roles);
		String json = "";
		for(Role role : roles){
			json += "{'name':'"+role.getName()+"',";
			json += "'descn':'"+role.getDescn()+"',";
			json += "'id':'"+role.getId()+"'},";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",rowCount:"+roles.size()+",roles:"+ json + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 保存资源
	 * @Methods Name saveRoles
	 * @Create In 2008-3-14 By Vincent
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
				
		UserInfo user = new UserInfo();
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String mobilePhone = request.getParameter("mobilePhone");
		String enabled = request.getParameter("enabled");
		String specialUserStr = request.getParameter("specialUser");
		String deptCode = request.getParameter("department");
		
		Department dept = deptService.findDepartmentById(Long.valueOf(deptCode));
		user.setDepartCode(Long.valueOf(deptCode));
		user.setDepartment(dept);
		user.setRealName(realName);
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setTelephone(telephone);
		user.setMobilePhone(mobilePhone);
		user.setEnabled(new Integer(enabled));
		user.setIsLock(0);
		user.setSpecialUser(Integer.valueOf(specialUserStr));
		user.setUserViewStyle("ext-all");  //默认页面样式
		String[] roleIds = request.getParameterValues("roleId");
		Set rolesSet = new HashSet();
		if(roleIds!=null && roleIds.length>0){
			for(int i=0; i<roleIds.length; i++){
				String roleId = roleIds[i];
				if(!"".equals(roleId)){
					Role role = sms.findRoleById(roleId);
					rolesSet.add(role);
				}
		    }
			user.setRoles(rolesSet);
		}
		sms.saveUserInfoWithRoles(user);	

		//获取外部用户的信息
		String customerOutId = request.getParameter("customerOut");
		Customer custOut = (Customer) super.getService().find(Customer.class, customerOutId);
		List<CustomerOutUserInfo> list = cous.findCustomerOutUser(custOut, user);
		if(list.size()==0){ //说明是新增用户或者修改用户的所属外部客户
			cous.saveCustomerOutUserInfo(custOut, user);
		}
		
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ "}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out != null)
				out.close();
		}
		return null;
	}
	
	public ActionForward addRolesToUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String userId = request.getParameter("id");
		
		try {				
			UserInfo user = sms.findUserById(userId);
	
			String data = request.getParameter("data");
			JSONArray jsonArray = JSONArray.fromObject(data);
			Set<Role> roleSet = user.getRoles();
			Set<String> idSet = new HashSet();
			for(Role role:roleSet){
				idSet.add(role.getId()+"");
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
				//判断是不是选中
				String checked = jsonObject.getString("checked");
				String id = jsonObject.getString("id");			
				if (!"".equals(checked) && checked.equals("true")&&!idSet.contains(id)) {	
					Role role = sms.findRoleById(id);
					roleSet.add(role);
				}
				else if(!"".equals(checked) && checked.equals("false") && idSet.contains(id)){
					Role role = sms.findRoleById(id);
					roleSet.remove(role);				
				}
			}
			
			user.setRoles(roleSet);
			sms.saveUserInfoWithRoles(user);	
		
			
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	public ActionForward removeRolesFromUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		String userId = request.getParameter("id");
		
		try {				
			UserInfo user = sms.findUserById(userId);
			
			String data = request.getParameter("data");
			JSONArray jsonArray = JSONArray.fromObject(data);
			Set<Role> roleSet = user.getRoles();
			Set<String> idSet = new HashSet();
			for(Role role:roleSet){
				idSet.add(role.getId()+"");
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
				//判断是不是选中
				String checked = jsonObject.getString("checked");
				String id = jsonObject.getString("id");			
				if (!"".equals(checked) && checked.equals("true")&&!idSet.contains(id)) {	
					Role role = sms.findRoleById(id);
					roleSet.remove(role);//选中的删除
				}
				else if(!"".equals(checked) && checked.equals("false") && idSet.contains(id)){
					Role role = sms.findRoleById(id);
					roleSet.add(role);	//为选的保留			
				}
			}
			
			user.setRoles(roleSet);
			sms.saveUserInfoWithRoles(user);	
			
			
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	public ActionForward modifyUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		//List rolesAll = sms.findRolesAll();
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String mobilePhone = request.getParameter("mobilePhone");
		String enabled = request.getParameter("enabled");
		String specialUser = request.getParameter("specialUser");
		String departCode = request.getParameter("department");
		String userId = request.getParameter("id");
		
		try {
			
				
			UserInfo user = sms.findUserById(userId);
			user.setRealName(realName);
			user.setUserName(userName);
			user.setPassword(password);
			user.setEmail(email);
			user.setEnabled(new Integer(enabled));
			user.setSpecialUser(new Integer(specialUser));
			
			if(StringUtils.isNotBlank(departCode)){//防止部门code为null发生异常
				Department department = deptService.findDepartmentById(Long.valueOf(departCode));
				user.setDepartment(department);
				user.setDepartCode(Long.valueOf(departCode));
			}
			user.setMobilePhone(mobilePhone);
			user.setTelephone(telephone);
			
			String data = request.getParameter("data");
			JSONArray jsonArray = JSONArray.fromObject(data);
			Set<Role> roleSet = user.getRoles();
			//要删除的角色
			String editRemovedIds = request.getParameter("editRemovedIds");
			if(!editRemovedIds.equals("")){
				String[] rolArg = editRemovedIds.split(",");
				for (int i = 0; i < rolArg.length; i++) {
					Role role = sms.findRoleById(rolArg[i]);
					roleSet.remove(role);
				}
			}
			
			Set<String> idSet = new HashSet();
			for(Role role:roleSet){
				idSet.add(role.getId()+"");
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
				//判断是不是选中
//					String checked = jsonObject.getString("checked");
				String id = jsonObject.getString("id");			
				if (!idSet.contains(id)) {	
					Role role = sms.findRoleById(id);
					roleSet.add(role);
				}
//					else if(!"".equals(checked) && checked.equals("false") && idSet.contains(id)){
//						Role role = sms.findRoleById(id);
//						roleSet.remove(role);				
//					}
			}
			
			user.setRoles(roleSet);
			sms.saveUserInfoWithRoles(user);	
			
			
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	
	public ActionForward removeUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String[] userIds = request.getParameterValues("id");
		for(int i=0;i<userIds.length;i++){
			String userId = userIds[i];
			UserInfo user = sms.findUserById(userId);
			user.setIsLock(1);
			service.save(user);
//			Set roles = user.getRoles();
//			user.setEnabled(0);
//			user.setRoles(roles);
//			sms.removeUser(userId);
		}
			
//		List roles = sms.findRolesAll();
//		request.setAttribute("roles", roles);
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
//		return HttpUtil.redirect("userRoleManage.do?methodCall=listUsers");
	}
	
	public ActionForward findUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String userId = request.getParameter("id");
		List user = sms.findUserByIdForPage(userId);
		
		JSONArray jsonObject = JSONArray.fromObject(user);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",user:"+ jsonObject.toString() + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ActionForward findRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List roles = sms.findRolesAllForPage();
		
		JSONArray jsonObject = JSONArray.fromObject(roles);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",roles:"+ jsonObject.toString() + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 根据用户Id获得用户所具有的角色Id
	 * @Methods Name findUserRoleIds
	 * @Create In Jun 24, 2008 By itnova
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findRolesByUserId(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		//查找当前用户所有的角色，遍历角色集合，置checked:true
		String userId = request.getParameter("id");
		List roles = sms.findRoleIdsByUserIdForPage(userId);
		
		JSONArray jsonObject = JSONArray.fromObject(roles);
		System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",roles:"+ jsonObject.toString() + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ActionForward findAllDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List<Department> deptList = deptService.findDeptAll();
		String json = "";
		for(int i=0; i< deptList.size(); i++){
			Department dept = (Department)deptList.get(i);			
			Long id = dept.getId();
			String name = dept.getDepartName();
			json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
		}
		//json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		json = "{data:[" + (json.length()>1 ? json.substring(0, json.length()-1) : "") + "]}";
		//modified by awen for add json length judgement on 2011-05-18 end
		System.out.println("创建用户时,发往前台的部门数据： "+json);
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write(json);
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String convertListData2JsonString(List listData,Page page){
		String js = "";
		if(listData.size()==0){
			js = "{success: false, rowCount:0,users:["+js+"]}";
		}
		for(int i=0;i<listData.size();i++) {
			Map data =  (Map)listData.get(i);
			Set keySet = data.keySet();
			Iterator it = keySet.iterator();
			String jss = "";
			while(it.hasNext()) {
				String key = (String)it.next();
			    if(key.equals("departCode")){
			    	Object value = data.get(key);
			    	if(value != null){
			    		Department deptment = deptService.findDepartmentById(new Long(data.get(key).toString()));
				    	if(deptment != null){
				    		String department = deptment.getDepartName();
					    	jss += "'department':'"+(department ==null ? "" : department)+"',";
				    	}
			    	}
			    	
			    }
			    
			    if(key.equals("enabled") || key.equals("specialUser")){
			    	Object value = data.get(key);
			    	if(value != null){
			    		if(value.equals("1")){
			    			jss += "'"+key+"':'"+"是"+"',";
			    		}
			    		if(value.equals("0")){
			    			jss += "'"+key+"':'"+"否"+"',";
			    		}
			    	}
			    	continue;
			    }
			    
				jss += "'"+key+"':'"+(data.get(key) ==null ? "" : data.get(key))+"',";
				
			}
			jss += "'id':'"+(data.get("id")==null?"":data.get("id"))+"'";
			if(jss.length()>0&&jss.endsWith(",")) {
				jss = jss.substring(0,jss.length()-1);
			}
			js += "{"+jss+"},";
		}
		if(js.length()>0&&js.endsWith(",")) {
			js = "{success: true, rowCount:"+page.getTotalCount()+",users:["+js.substring(0,js.length()-1)+"]}";
		}
		return js;
	}
	
}
package com.zsgj.itil.actor.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.zsgj.itil.actor.service.CustomerOutUserService;


public class UserOutManageAction extends BaseDispatchAction{
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private Service service = (Service) getBean("baseService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private DepartmentService deptService = (DepartmentService) getBean("deptService");
	private CustomerOutUserService customerOutUserService = (CustomerOutUserService) getBean("customerOutUserService");
	
	/**
	 * 通过指定参数获得用户信息
	 * @Methods Name queryAllUserByParameter
	 * @Create In Apr 23, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryAllUserByParameter(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo = start/10+1;
		Map params=new HashMap<String, String>();
		String userName = HttpUtil.ConverUnicode(request.getParameter("userName"));
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
//		String userName = request.getParameter("userName");
//		String realName = request.getParameter("realName");
		params.put("userName", userName);
		params.put("realName", realName);
		Page page =	this.customerOutUserService.findCustomerOutUser(params, pageNo, 10, "userName", true );
			//sms.findUser(params, pageNo, 10, "userName", true );
		List queryList = page.list(); 
		List listData = metaDataManager.getEntityMapDataForList(UserInfo.class, queryList);
		//List listData =sms.findUserByQueryParamsForPage(userName,realName);
		String json = convertListData2JsonString(listData, page);
		try {
			response.setCharacterEncoding("gbk");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取外部客户列表Combo
	 * @Methods Name findCustOutForCombo
	 * @Create In Apr 29, 2009 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findCustOutForCombo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String json = "";
		// /////////////////////////////////////////////////////////////////////
		// 注意以后从设置中取出
		int pageSize = 10;
		// 注意以后从设置中取出
		String startStr = request.getParameter("start");
		int start = 0;
		if(startStr!=null&&!"".equals(startStr)){
			start = Integer.parseInt(startStr);
		} 
		int pageNo = start / pageSize + 1;
		String orderProp = HttpUtil.getString(request, "orderBy", "departName");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String pClazz = request.getParameter("clazz");
		Class clazz = Department.class;
		Long total = 0L;
		List queryList = new ArrayList();
		String departName = request.getParameter("departName");
		//System.out.println(departName);
		departName = HttpUtil.ConverUnicode(departName);
		String isLoad = request.getParameter("isLoad");
		if(isLoad!=null&&!"".equals("true")){
			Object obj = service.findUnique(Customer.class, "customerName", departName);
			total = 1L;
			queryList.add(obj);
			json = this.encodeForCustOutCombo(queryList, total);
		}else{
			//按部门名称查
			Page page = customerOutUserService.findCustOutByCustName(departName, orderProp, isAsc, pageNo, pageSize);
			total = page.getTotalCount();
			queryList = page.list();
			json = this.encodeForCustOutCombo(queryList, total);
		}
		httpServletResponse.setCharacterEncoding("gbk");
		httpServletResponse.getWriter().write(json);
		httpServletResponse.getWriter().flush();
		return null;
	}
	
 /**
  * 通过部门code查找角色
  * @Methods Name findRoleByDept
  * @Create In Apr 23, 2009 By sujs
  * @param mapping
  * @param actionForm
  * @param request
  * @param httpServletResponse
  * @return
  * @throws Exception ActionForward
  */
	
	@SuppressWarnings("unchecked")
	public ActionForward findRoleByDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String deptCode = request.getParameter("deptCode");
		Department dept = deptService.findDepartmentById(new Long(deptCode));
		List<Role> roles = sms.findRoleByDept(dept);
		String json = "";
		String roleName="";
		String deptName = "";
		String descn="";
		for(Role role : roles){
			if(role.getName()!=null){
				roleName=role.getName();
			}
			if(role.getDescn()!=null){
				descn=role.getDescn();
			}
			if(role.getDepartment()!=null){
				deptName = role.getDepartment().getDepartName();
			}
			json += "{'name':'"+roleName+"',";
			json += "'department':'"+deptName+"',";
			json += "'descn':'"+descn+"',";
			json += "'id':'"+role.getId()+"'},";
			descn="";
			roleName="";
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
	 * 通过用户id查找角色
	 * @Methods Name findRolesByUserId
	 * @Create In Apr 23, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findRolesByUserId(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		//查找当前用户所有的角色，遍历角色集合，置checked:true
		String userId = request.getParameter("userId");
		if(userId==null||userId.equals("")){
			return null;
		}
		String json = "";
		String roleName="";
		String deptName = "";
		String descn="";
		List<Map> roles = sms.findRoleIdsByUserIdForPage(userId);
		for(Map role : roles){
			if(role.get("name")!=null){
				roleName=(String) role.get("name");
			}
			if(role.get("descn")!=null){
				descn=(String) role.get("descn");
			}
			if(role.get("department")!=null){
				deptName=(String) role.get("department");
			}

			json += "{'name':'"+roleName+"',";
			json += "'department':'"+deptName+"',";
			json += "'descn':'"+descn+"',";
			json += "'id':'"+role.get("id")+"'},";
			descn="";
			roleName="";
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		//JSONArray jsonObject = JSONArray.fromObject(roles);
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",roles:"+ json + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 保存用户信息
	 * @Methods Name saveUsers
	 * @Create In Apr 23, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {	
		PrintWriter out = null;
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
		String isLock=request.getParameter("isLock");
		String externalFlag = request.getParameter("externalFlag");
		//判断是否存在当前新建的用户
		List<UserInfo> userInfoList=sms.findUserByQueryParams(userName, realName);
		if (userInfoList.size() > 0) {
			if (userInfoList.get(0).getIsLock() == 1) {
				httpServletResponse.setCharacterEncoding("gbk");
				out.write("{success:" + true + ",flagSign':exitIsLock',"
						+ "userNameTring:'" + "真实姓名为：<font color=red>"
						+ realName + "</font>用户名为:<font color=red>" + userName
						+ "</font>'}");
				return null;
			} else {
				httpServletResponse.setCharacterEncoding("gbk");
				out.write("{success:" + true + ",flagSign':exit',"
						+ "userNameTring:'" + "真实姓名为：<font color=red>"
						+ realName + "</font>用户名为:<font color=red>" + userName
						+ "</font>'}");
				return null;
			}
		}
		
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
		user.setIsLock((int)Long.parseLong(isLock));
		user.setSpecialUser(Integer.valueOf(specialUserStr));
		user.setUserViewStyle("ext-all");  //默认页面样式
		user.setExternalFlag(Integer.parseInt(externalFlag));
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
		
		Customer customerOut = (Customer) getService().find(Customer.class, deptCode);
		customerOutUserService.saveCustomerOutUserInfo(customerOut, user);
				
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ "}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			out.close();
		}
		return null;
	}
	/**
	 * 通过用户id查找所有用户信息
	 * @Methods Name findUserForm
	 * @Create In Apr 24, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findUserForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String json = "";
		String userId = request.getParameter("id");
		List<UserInfo> user = sms.findUserByIdForPage(userId);
		//********************************************************
		
//		for(Map role : roles){
//			if(role.get("name")!=null){
//				roleName=(String) role.get("name");
//			}
//			if(role.get("descn")!=null){
//				descn=(String) role.get("descn");
//			}
//			if(role.get("department")!=null){
//				deptName=(String) role.get("department");
//			}
//
//			json += "{'name':'"+roleName+"',";
//			json += "'department':'"+deptName+"',";
//			json += "'descn':'"+descn+"',";
//			json += "'id':'"+role.get("id")+"'},";
//			descn="";
//			roleName="";
//		}
//		if (json.endsWith(",")) {
//			json = json.substring(0, json.length() - 1);
//		}
		json = "[" + json + "]";
		//********************************************************
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
	/**
	 * 修改用户信息
	 * @Methods Name modifyUser
	 * @Create In Apr 24, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward modifyUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String mobilePhone = request.getParameter("mobilePhone");
		String enabled = request.getParameter("enabled");
		String specialUser = request.getParameter("specialUser");
		String departCode = request.getParameter("department");
		String userId = request.getParameter("userId");
		String isLock=request.getParameter("isLock");
		try {
			UserInfo user = sms.findUserById(userId);
			user.setRealName(realName);
			user.setUserName(userName);
			user.setPassword(password);
			user.setEmail(email);
			user.setEnabled(new Integer(enabled));
			user.setSpecialUser(new Integer(specialUser));
			user.setIsLock(new Integer(isLock));
			if(StringUtils.isNotBlank(departCode)){//防止部门code为null发生异常
				if(departCode.equals("callback")){
					Department department = deptService.findDepartmentById(user.getDepartCode());
					user.setDepartment(department);
					user.setDepartCode(user.getDepartCode());
				}else{
					Customer customerOut = (Customer) getService().find(Customer.class, departCode);
					customerOutUserService.saveCustomerOutUserInfo(customerOut, user);
					
					Department department = deptService.findDepartmentById(Long.valueOf(departCode));
					user.setDepartment(department);
					user.setDepartCode(Long.valueOf(departCode));
				}
			}
			user.setMobilePhone(mobilePhone);
			user.setTelephone(telephone);
			
			Set<Role> roleSet = user.getRoles();
			Iterator it=roleSet.iterator();
//				while(it.hasNext()){
//					sms.removeRoleFromUser(user, (String)it.next());
//				}
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
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
			httpServletResponse.getWriter().write("{success:" + false + "}");
		}
		return null;
	}
	/**
	 * 删除用户(删除一个外部客户时要将其关联表CustomerOutUserInfo所关联的记录也删除)
	 * @Methods Name removeUsers sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	
	public ActionForward removeUsers(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String[] userIds = request.getParameterValues("id");
		for(int i=0;i<userIds.length;i++){
			String userId = userIds[i];
			UserInfo user = sms.findUserById(userId);
			user.setIsLock(1);
			sms.saveUserInfoWithRoles(user);
		}
		try {
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	}
	/**
	 * 查找所有的部门
	 * @Methods Name findDeptForCombo
	 * @Create In Apr 26, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findDeptForCombo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String json = "";
		// /////////////////////////////////////////////////////////////////////
		// 注意以后从设置中取出
		int pageSize = 10;
		// 注意以后从设置中取出
		String startStr = request.getParameter("start");
		int start = 0;
		if(startStr!=null&&!"".equals(startStr)){
			start = Integer.parseInt(startStr);
		}
		int pageNo = start / pageSize + 1;
		String orderBy = HttpUtil.getString(request, "orderBy", "departName");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String pClazz = request.getParameter("clazz");
		Class clazz = Department.class;
		Long total = 0L;
		List queryList = new ArrayList();
		String departName = request.getParameter("departName");
		System.out.println(departName);
		departName = HttpUtil.ConverUnicode(departName);
		String isLoad = request.getParameter("isLoad");
		if(isLoad!=null&&!"".equals("true")){
			Object obj = service.findUnique(Department.class, "departName", departName);
			total = 1L;
			queryList.add(obj);
			json = this.encodeForDepCombo(queryList, total);
		}else{
			//按部门名称查
			Map resultMap = deptService.findDepartmentByDepName(departName, orderBy, isAsc, pageNo, pageSize);
			
			total = (Long) resultMap.get("total");
			queryList = (List) resultMap.get("queryList");
			json = this.encodeForDepCombo(queryList, total);
		}
		httpServletResponse.setCharacterEncoding("gbk");
		httpServletResponse.getWriter().write(json);
		httpServletResponse.getWriter().flush();
		return null;
	}
	
	
	
	//查询所有的角色
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

	public String convertListData2JsonString(List listData,Page page){
		String js = "";
		if(listData.size()==0){
			js = "{success: false, rowCount:0,users:["+js+"]}";
		}
		for(int i=0;i<listData.size();i++) {
			Map data =  (Map)listData.get(i);
			String depCode = (String) data.get("departCode");
			if(depCode!=null&&!"".equals(depCode)){
				Department deptment = deptService.findDepartmentById(Long.valueOf(depCode));
		    	if(deptment != null){
		    		String depName = deptment.getDepartName();
		    		data.put("department", depName);
		    	}
			}
			
			Set keySet = data.keySet();
			Iterator it = keySet.iterator();
			String jss = "";
			while(it.hasNext()) {
				String key = (String)it.next();
				Object value = data.get(key);
				String strVal = value==null?"":value.toString();
			    if(key.equals("enabled") || key.equals("specialUser")){
			    	if(Integer.valueOf(1).equals(value)){
			    		strVal = "是";
			    	}else if(Integer.valueOf(0).equals(value)){
			    		strVal = "否";
			    	}
			    }
			    if(key.equals("userName")){
			    	strVal = strVal.split("/")[1];
			    	
			    }
			    
				jss += "'"+key+"':'"+strVal+"',";
				
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
	
	/**
	 * 部门COMBO中查询方法，用户模糊查询和初始化COMBO的查询
	 * @Methods Name findDeptForCombo
	 * @Create In Mar 30, 2009 By chuanyu ou
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	
	
	private String encodeForCustOutCombo(List<Customer> queryList,Long total){
		String json = "";
		if(queryList==null){
			json = "{success:false}";
		}else{
			for(Customer dep:queryList){
				String dataItem = "";
				dataItem += "id:'"+dep.getId()+"',";
				dataItem += "departName:'"+dep.getCustomerName()+"'";
				dataItem = "{"+dataItem+"},";	
				json += dataItem;
			}
			if(json.endsWith(",")) {
				json = json.substring(0,json.length()-1);
			}
			
		}
		json = "{success: true, rowCount:"+(total==null?0:total)+",data:["+json+"]}";
		return json;
	}
	
	
	private String encodeForDepCombo(List<Department> queryList,Long total){
		String json = "";
		if(queryList==null){
			json = "{success:false}";
		}else{
			for(Department dep:queryList){
				String dataItem = "";
				dataItem += "id:'"+dep.getId()+"',";
				dataItem += "departName:'"+dep.getDepartName()+"'";
				dataItem = "{"+dataItem+"},";	
				json += dataItem;
			}
			if(json.endsWith(",")) {
				json = json.substring(0,json.length()-1);
			}
			
		}
		json = "{success: true, rowCount:"+(total==null?0:total)+",data:["+json+"]}";
		return json;
	}
	
}
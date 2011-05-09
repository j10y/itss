package com.zsgj.info.framework.security.web.adapter.struts.user;

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
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;

import com.zsgj.info.appframework.extjs.servlet.CoderForList;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.UserTableSetting;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Platform;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.security.service.PlatformService;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;


public class UserManageAction extends BaseDispatchAction{
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private Service service = (Service) getBean("baseService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private DepartmentService deptService = (DepartmentService) getBean("deptService");
	private PlatformService platService = (PlatformService) getBean("platService");
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
		int pageNo =start/10+1;
		Map params=new HashMap<String, String>();
		String userName = HttpUtil.ConverUnicode(request.getParameter("userName"));
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
//		String userName = request.getParameter("userName");
//		String realName = request.getParameter("realName");
		params.put("userName", userName);
		params.put("realName", realName);
		Page page =	sms.findUser(params, pageNo, 10, "userName", true );
		List<UserInfo> queryList = page.list(); 
		String json = "";
		for(UserInfo userInfo : queryList){
			json += "{'id':'"+userInfo.getId()+"',";
			json += "'userName':'"+ userInfo.getUserName() +"',";
			json += "'realName':'"+ userInfo.getRealName() +"',";
			json += "'password':'"+ userInfo.getPassword() +"',";
			json += "'email':'"+ userInfo.getEmail() +"',";
			json += "'department':'"+ userInfo.getDepartment().getDepartName() +"',";
			
			/////////////////////////////add by lee for add platform in 2009.6.13 start ////////////////
			String platName = "--";
			if(userInfo.getPlatform()!=null){
				platName = userInfo.getPlatform().getName();
			}
			json += "'platform':'"+ platName +"',";
			/////////////////////////////add by lee for add platform in 2009.6.13 end ////////////////
			
			json += "'enabled':'"+ getBooleanValue(userInfo.getEnabled()) +"',";
			json += "'specialUser':'"+ getBooleanValue(userInfo.getSpecialUser()) +"',";
			json += "'isLock':'"+ getBooleanValue(userInfo.getIsLock()) + "'},";
		}
		//json = convertListData2JsonString(listData, page);
		
		if(json.length()>0&&json.endsWith(",")) {
			json = "{success: true, rowCount:"+page.getTotalCount()+",users:["+json.substring(0,json.length()-1)+"]}";
		}
		
		try {
			response.setCharacterEncoding("gbk");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward queryAllLockUserByParameter(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo =start/10+1;
		Map params=new HashMap<String, String>();
		String userName = HttpUtil.ConverUnicode(request.getParameter("userName"));
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
//		String userName = request.getParameter("userName");
//		String realName = request.getParameter("realName");
		params.put("userName", userName);
		params.put("realName", realName);
		Page page =	sms.findLockUser(params, pageNo, 10, "userName", true );
		List<UserInfo> queryList = page.list(); 
		String json = "";
		for(UserInfo userInfo : queryList){
			json += "{'id':'"+userInfo.getId()+"',";
			json += "'userName':'"+ userInfo.getUserName() +"',";
			json += "'realName':'"+ userInfo.getRealName() +"',";
			json += "'password':'"+ userInfo.getPassword() +"',";
			json += "'email':'"+ userInfo.getEmail() +"',";
			json += "'department':'"+ userInfo.getDepartment().getDepartName() +"',";
			
			/////////////////////////////add by lee for add platform in 2009.6.13 start ////////////////
			String platName = "--";
			if(userInfo.getPlatform()!=null){
				platName = userInfo.getPlatform().getName();
			}
			json += "'platform':'"+ platName +"',";
			/////////////////////////////add by lee for add platform in 2009.6.13 end ////////////////
			
			json += "'enabled':'"+ getBooleanValue(userInfo.getEnabled()) +"',";
			json += "'specialUser':'"+ getBooleanValue(userInfo.getSpecialUser()) +"',";
			json += "'isLock':'"+ getBooleanValue(userInfo.getIsLock()) + "'},";
		}
		//json = convertListData2JsonString(listData, page);
		
		if(json.length()>0&&json.endsWith(",")) {
			json = "{success: true, rowCount:"+page.getTotalCount()+",users:["+json.substring(0,json.length()-1)+"]}";
		}
		
		try {
			response.setCharacterEncoding("gbk");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getBooleanValue(Integer value){
		String result = "";
		if(value!=null && value.intValue()==0){
			result = "否";
		}else if(value!=null && value.intValue()==1){
			result = "是";
		}
		return result;
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
		String descn="";
		for(Role role : roles){
			if(role.getName()!=null){
				roleName=role.getName();
			}
			if(role.getDescn()!=null){
				descn=role.getDescn();
			}
			json += "{'name':'"+roleName+"',";
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
		List roles = sms.findRoleIdsByUserIdForPage(userId);
		JSONArray jsonObject = JSONArray.fromObject(roles);
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
		String realName = request.getParameter("realName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String mobilePhone = request.getParameter("mobilePhone");
		String enabled = request.getParameter("enabled");
		String specialUserStr = request.getParameter("specialUser");
		String deptCode = request.getParameter("department");
		
		String platform = request.getParameter("platform");//add by lee for add platform in 090613
		
		String isLock=request.getParameter("isLock");
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
		
		if(platform == null ||"".equals(platform)){
			user.setPlatform(null);//add by lee for add platform in 090613
		}else{
			Platform plat = platService.findPlatformById(Long.parseLong(platform));
			user.setPlatform(plat);
		}
		
		user.setDepartCode(Long.valueOf(deptCode));
		user.setDepartment(dept);
		user.setRealName(HttpUtil.ConverUnicode(realName));
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setTelephone(telephone);
		user.setMobilePhone(mobilePhone);
		user.setEnabled(new Integer(enabled));
		user.setIsLock((int)Long.parseLong(isLock));
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
		
		String userId = request.getParameter("id");
		List user = sms.findUserByIdForPage(userId);
		
		JSONArray jsonObject = JSONArray.fromObject(user);
		//System.out.println(jsonObject.toString());
		
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
		
		String platform = request.getParameter("platform");//add by lee for add platform in 090613
		
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
				     Department department = deptService.findDepartmentById(Long.valueOf(departCode));
					  user.setDepartment(department);
					  user.setDepartCode(Long.valueOf(departCode));
					}
				}
				
				///////add by lee for add platform in 090613 start/////////////////////////
				if(StringUtils.isNotBlank(platform)&&!platform.equals("callback")){//防止部门code为null发生异常
				    Platform plat = platService.findPlatformById(Long.valueOf(platform));
				    user.setPlatform(plat);
				}
				///////add by lee for add platform in 090613 end/////////////////////////
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
	 * 删除用户
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

	/**
	 * 根据平台名获取平台
	 * @Methods Name findPlatForCombo
	 * @Create In Jun 13, 2009 By lee
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findPlatForCombo(ActionMapping mapping,
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
		String orderBy = HttpUtil.getString(request, "orderBy", "flatName");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
		String pClazz = request.getParameter("clazz");
		Class clazz = Department.class;
		Long total = 0L;
		List queryList = new ArrayList();
		String platName = request.getParameter("platName");
		System.out.println(platName);
		platName = HttpUtil.ConverUnicode(platName);
		String isLoad = request.getParameter("isLoad");
		if(isLoad!=null&&!"".equals("true")){
			Object obj = service.findUnique(Platform.class, "name", platName);
			total = 1L;
			queryList.add(obj);
			json = this.encodeForPlatCombo(queryList, total);
		}else{
			//按部门名称查
			Map resultMap = platService.findPlatformByName(platName, orderBy, isAsc, pageNo, pageSize);
			
			total = (Long) resultMap.get("total");
			queryList = (List) resultMap.get("queryList");
			json = this.encodeForPlatCombo(queryList, total);
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
	
	/**
	 * 组装部门返回数据
	 * @Methods Name encodeForDepCombo
	 * @Create In Apr 29, 2009 By Administrator
	 * @param queryList
	 * @param total
	 * @return String
	 */
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
	
	/**
	 * 组装平台返回数据
	 * @Methods Name encodeForPlatCombo
	 * @Create In Jun 13, 2009 By lee
	 * @param queryList
	 * @param total
	 * @return String
	 */
	private String encodeForPlatCombo(List<Platform> queryList,Long total){
		String json = "";
		if(queryList==null){
			json = "{success:false}";
		}else{
			for(Platform plat:queryList){
				String dataItem = "";
				dataItem += "id:'"+plat.getId()+"',";
				dataItem += "platName:'"+plat.getName()+"'";
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
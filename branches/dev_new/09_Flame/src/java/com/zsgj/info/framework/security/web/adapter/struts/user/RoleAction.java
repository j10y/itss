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

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.appframework.menu.entity.DeptMenu;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Platform;
import com.zsgj.info.framework.security.entity.Province;
import com.zsgj.info.framework.security.entity.Region;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.security.service.PlatformService;
import com.zsgj.info.framework.security.service.SecurityManageService;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.entity.WorkflowRoleMaping;


public class RoleAction extends BaseDispatchAction{
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private DepartmentService ds = (DepartmentService) getBean("deptService");
	private PlatformService ps = (PlatformService) getBean("platService");
	private Service service = (Service) getBean("baseService");
	/**
	 * 查询出所有角色
	 * @Methods Name querylistRoles
	 * @Create In Apr 28, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward querylistRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/10+1;
		Map params=new HashMap<String, String>();
		String name = HttpUtil.ConverUnicode(request.getParameter("name"));
		params.put("name", name);
		Page page =	sms.findRols(params, pageNo, 10, "name", true );
		List queryList = page.list(); 
		List listData = metaDataManager.getEntityMapDataForList(Role.class, queryList);
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
	@SuppressWarnings("unchecked")
	public String convertListData2JsonString(List listData,Page page){
		String js = "";
		if(listData.size()==0){
			js = "{success: false, rowCount:0,roles:["+js+"]}";
		}
		for(int i=0;i<listData.size();i++) {
			Map data =  (Map)listData.get(i);
			String depCode = (String) data.get("departCode");
			
			//add by awen for add property platform on entity called "Role" begin
			String platform = (String)data.get("platform");
			if(platform !=null || !"".equals(platform)){
				data.put("platform", platform);
			}
			//add by awen for add property platform on entity called "Role" end
			
			if(depCode!=null&&!"".equals(depCode)){
				Department deptment = ds.findDepartmentById(Long.valueOf(depCode));
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
				jss += "'"+key+"':'"+strVal+"',";
				
			}
			//jss += "'id':'"+(data.get("id")==null?"":data.get("id"))+"'";
			if(jss.length()>0&&jss.endsWith(",")) {
				jss = jss.substring(0,jss.length()-1);
			}
			js += "{"+jss+"},";
		}
		if(js.length()>0&&js.endsWith(",")) {
			js = "{success: true, rowCount:"+page.getTotalCount()+",roles:["+js.substring(0,js.length()-1)+"]}";
		}
		return js;
	}
	/**
	 * 增加权限
	 * @Methods Name toAddRoles
	 * @Create In Apr 28, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward addAuthorization(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List<Authorization> authorizationsAll = sms.findAuthorizationsOrderByModule();
		String jsonString="";
		for(Authorization autthor:authorizationsAll){
			jsonString+="{'id':'"+autthor.getId().toString()+"',";
			jsonString+="'name':'"+autthor.getName()+"'},";
		}
		if(jsonString.length()>0&&jsonString.endsWith(",")) {
			jsonString = jsonString.substring(0,jsonString.length()-1);
		}
		//JSONArray jsonObject = JSONArray.fromObject(authorizationsAll);
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",auths:["+ jsonString + "]}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}
	/**
	 * 通过角色名称查找权限
	 * @Methods Name findAuthsByRoleId
	 * @Create In Apr 28, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findAuthsByRoleId(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String roleId = request.getParameter("id");
		if(roleId==null||roleId.equals("")||roleId.equals("0")){
			return null;
		}
		List auths = sms.findAuthsByRoleIdForPage(roleId);
		JSONArray jsonObject = JSONArray.fromObject(auths);
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",auths:"+ jsonObject.toString() + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 修改时查出所有的角色
	 * @Methods Name findRole
	 * @Create In Apr 29, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findRole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String roleId = request.getParameter("id");
		List role = sms.findRoleByIdForPage(roleId);
		
		JSONArray jsonObject = JSONArray.fromObject(role);
		//System.out.println(jsonObject.toString());
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + ",role:"+ jsonObject.toString() + "}");
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
	@SuppressWarnings("unchecked")
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
//		String pClazz = request.getParameter("clazz");
//		Class clazz = Department.class;
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
			Map resultMap = ds.findDepartmentByDepName(departName, orderBy, isAsc, pageNo, pageSize);
			
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
	 * 组装部门数据
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
	 * 保存角色信息
	 * @Methods Name saveRoles
	 * @Create In May 4, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		request.setCharacterEncoding("GBK");
		String name = HttpUtil.ConverUnicode(request.getParameter("name"));
		String descn = HttpUtil.ConverUnicode(request.getParameter("descn"));
		String dataView = request.getParameter("dataView");
		//add by awen for add property on entity called 'role' on june 14 2009 begin
		String dataViewPlatform = request.getParameter("dataViewPlatform");
		String dataViewRegion = request.getParameter("dataViewRegion");
		String dataViewProvince = request.getParameter("dataViewProvince");
		//add by awen for add property on entity called 'role' on june 14 2009 end
		String depertmentCode = request.getParameter("department");
//		String deptMenuTemplateId = request.getParameter("deptmt");
		
		String deptMenuId = request.getParameter("deptMenu");
		
		//add by awen for add property on entity called 'role' on june 14 2009 begin
		String platformId = HttpUtil.ConverUnicode(request.getParameter("platform"));
		String regionId = HttpUtil.ConverUnicode(request.getParameter("region"));
		String provinceId = HttpUtil.ConverUnicode(request.getParameter("province"));
		//add by awen for add property on entity called 'role' on june 14 2009 end
		Role role = new Role();
		role.setName(name);
		role.setDescn(descn);
		if(depertmentCode!=null&&!depertmentCode.trim().equals("")) {
			List departments = getService().find(Department.class, "departCode",new Long(depertmentCode));
			if(departments!=null&&!departments.isEmpty()) {
				Department department = (Department)departments.get(0);
				role.setDepartment(department);
			}
		}	

		if(deptMenuId!=null&&deptMenuId.trim().length()!=0){
			DeptMenu deptMenu=new DeptMenu();
			deptMenu.setId(Long.valueOf(deptMenuId));
			role.setDeptMenu(deptMenu);
		}
		//add by awen for add property on entity called 'role' on june 14 2009 begin
		if(platformId != null && !"".equals(platformId.trim())){
			if(StringUtils.isNumeric(platformId)){
				Platform platform = (Platform) service.find(Platform.class, platformId);
				role.setPlatform(platform);
			}
		}
		if(regionId != null && !"".equals(regionId.trim())){
			if(StringUtils.isNumeric(regionId)){
				Region region = (Region) service.find(Region.class, regionId);
				role.setRegion(region);
			}
		}
		if(provinceId != null && !"".equals(provinceId.trim())){
			if(StringUtils.isNumeric(provinceId)){
				Province province = (Province) service.find(Province.class, provinceId);
				role.setProvince(province);
			}
		}
		//add by awen for add property on entity called 'role' on june 14 2009 end
		
		role.setDataViewFlag(dataView==null||dataView.equals("")?null:Integer.valueOf(dataView));
		
		//add by awen for add property on entity called 'role' on june 14 2009 begin
		role.setDataViewPlatformFlag(dataViewPlatform==null||dataViewPlatform.equals("")?null:Integer.valueOf(dataViewPlatform));
		role.setDataViewRegionFlag(dataViewRegion==null||dataViewRegion.equals("")?null:Integer.valueOf(dataViewRegion));
		role.setDataViewProvinceFlag(dataViewProvince==null||dataViewProvince.equals("")?null:Integer.valueOf(dataViewProvince));
		//add by awen for add property on entity called 'role' on june 14 2009 end
		
		String[]  authIds = request.getParameterValues("authId");
		Set auSet = new HashSet();
		if(authIds != null && authIds.length !=0){
			for(int i=0; i<authIds.length; i++){
				String authId = authIds[i];
				if(!"".equals(authId)){
					Authorization auth = sms.findAuthorizationById(authId);
					auSet.add(auth);
				}
			}
		}
		role.setAuthorizations(auSet);
		Role role2 = sms.saveRole(role);
		PrintWriter out = null;
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ ",id:"+role2.getId()+"}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(out != null)
				out.close();
		}
		
		return null;
	}
	/**
	 * 修改角色
	 * @Methods Name modifyRole
	 * @Create In May 4, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward modifyRole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String roleId = request.getParameter("id");
		Role role = sms.findRoleById(roleId);
		String name = HttpUtil.ConverUnicode(request.getParameter("name"));
		String descn = HttpUtil.ConverUnicode(request.getParameter("descn"));
		String dataView = request.getParameter("dataView");
		String dataViewPlatform = request.getParameter("dataViewPlatform");
		String platform = request.getParameter("platformName");
		String dataViewRegion = request.getParameter("dataViewRegion");
		String region = request.getParameter("regionName");
		String dataViewProvince = request.getParameter("dataViewProvince");
		String province = request.getParameter("provinceName");
		String depertmentCode = request.getParameter("department");
//		String deptMenuTemplateId = request.getParameter("deptmt");
		String deptMenuId = request.getParameter("deptMenu");
		if(depertmentCode!=null&&!depertmentCode.trim().equals("")) {
			if(StringUtils.isNumeric(depertmentCode)){
				List departments = getService().find(Department.class, "departCode",new Long(depertmentCode));
				if(departments!=null&&!departments.isEmpty()) {
					Department department = (Department)departments.get(0);
					role.setDepartment(department);
				}
				
			}
		}

		if(deptMenuId!=null&&deptMenuId.trim().length()!=0){
			DeptMenu deptMenu=new DeptMenu();
			deptMenu.setId(Long.valueOf(deptMenuId));
			role.setDeptMenu(deptMenu);
		}else{
			role.setDeptMenu(null);
		}
		if(dataViewPlatform!=null&&!dataViewPlatform.trim().equals("")) {
			if(StringUtils.isNumeric(dataViewPlatform)){
				role.setDataViewPlatformFlag(new Integer(dataViewPlatform));
			}
		}
		if(dataViewRegion!=null&&!dataViewRegion.trim().equals("")) {
			if(StringUtils.isNumeric(dataViewRegion)){
				role.setDataViewRegionFlag(new Integer(dataViewRegion));
			}
		}
		if(dataViewProvince!=null&&!dataViewProvince.trim().equals("")) {
			if(StringUtils.isNumeric(dataViewProvince)){
				role.setDataViewProvinceFlag(new Integer(dataViewProvince));
			}
		}
		
		if(platform!=null&&!platform.trim().equals("")) {
			if(StringUtils.isNumeric(platform)){
				role.setPlatform((Platform) service.find(Platform.class, platform, true));
			}
		}
		if(region!=null&&!region.trim().equals("")) {
			if(StringUtils.isNumeric(region)){
				role.setRegion((Region) service.find(Region.class, region, true));
			}
		}
		if(province!=null&&!province.trim().equals("")) {
			if(StringUtils.isNumeric(province)){
				role.setProvince((Province) service.find(Province.class, province, true));
			}
		}
		role.setName(name);
		role.setDescn(descn);
		if(dataView==null||dataView.trim().equals("")) {
			role.setDataViewFlag(Integer.valueOf(0));
		}
		else {
			role.setDataViewFlag(Integer.valueOf(dataView));
		}
		String[]  authIds = request.getParameterValues("authId");
		Set auSet = new HashSet();
		if(authIds != null && authIds.length !=0){
			for(int i=0; i<authIds.length; i++){
				String authId = authIds[i];
				if(!"".equals(authId)){
					Authorization auth = sms.findAuthorizationById(authId);
					auSet.add(auth);
				}
			}
		}
		role.setAuthorizations(auSet);
		sms.saveRole(role);
		
		try {
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
	 * 删除角色
	 * @Methods Name removeRoles
	 * @Create In May 4, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward removeRoles(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String[] roleIds = request.getParameterValues("id"); 
		for(int i=0;i<roleIds.length;i++){
			String roleId = roleIds[i];
			Role role = (Role)getService().find(Role.class, roleId);
			List<WorkflowRoleMaping> rms = getService().find(WorkflowRoleMaping.class, "role", role);
			for(WorkflowRoleMaping rm : rms) {
				getService().remove(rm);
			}			
			sms.removeRole(roleId);
		}
		
		List roles = sms.findRolesAll();
		request.setAttribute("roles", roles);
		
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write("{success:" + true + "}");
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
		//return HttpUtil.redirect("roleManage.do?methodCall=listRoles");
	}
	
	/**
	 * 查找所有的平台
	 * @Methods Name findPlatformForCombo
	 * @Create In jun 14, 2009 By awen
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findPlatformForCombo(ActionMapping mapping,
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
		String orderBy = HttpUtil.getString(request, "orderBy", "platformName");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
//		String pClazz = request.getParameter("clazz");
//		Class clazz = Department.class;
		Long total = 0L;
		List queryList = new ArrayList();
		String platformName = request.getParameter("platformName");
		System.out.println(platformName);
		platformName = HttpUtil.ConverUnicode(platformName);
		String isLoad = request.getParameter("isLoad");
		if(isLoad!=null&&!"".equals("true")){
			Object obj = service.findUnique(Department.class, "platformName", platformName);
			total = 1L;
			queryList.add(obj);
			json = this.encodeForPlatformCombo(queryList, total);
		}else{
			//按部门名称查
			Map resultMap = ps.findPlatformByName(platformName, orderBy, isAsc, pageNo, pageSize);
			
			total = (Long) resultMap.get("total");
			queryList = (List) resultMap.get("queryList");
			json = this.encodeForPlatformCombo(queryList, total);
		}
		httpServletResponse.setCharacterEncoding("gbk");
		httpServletResponse.getWriter().write(json);
		httpServletResponse.getWriter().flush();
		return null;
	}
	/**
	 * 查找所有区域
	 * @Methods Name findRegionForCombo
	 * @Create In Jun 26, 2009 By Administrator
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findRegionForCombo(ActionMapping mapping,
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
		String orderBy = HttpUtil.getString(request, "orderBy", "name");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
//		String pClazz = request.getParameter("clazz");
//		Class clazz = Department.class;
		Long total = 0L;
		List queryList = new ArrayList();
		String regionName = request.getParameter("name");
		System.out.println(regionName);
		regionName = HttpUtil.ConverUnicode(regionName);
		String isLoad = request.getParameter("isLoad");
		if(isLoad!=null&&!"".equals("true")){
			Object obj = service.findUnique(Region.class, "name", regionName);
			total = 1L;
			queryList.add(obj);
			json = this.encodeForRegionCombo(queryList, total);
		}else{
			//按区域名称查
			Map resultMap = ps.findRegionByName(regionName, orderBy, isAsc, pageNo, pageSize);
			
			total = (Long) resultMap.get("total");
			queryList = (List) resultMap.get("queryList");
			json = this.encodeForRegionCombo(queryList, total);
		}
		httpServletResponse.setCharacterEncoding("gbk");
		httpServletResponse.getWriter().write(json);
		httpServletResponse.getWriter().flush();
		return null;
	}
	/**
	 * 查找所有省份
	 * @Methods Name findProvinceForCombo
	 * @Create In Sep 7, 2009 By sujs
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward findProvinceForCombo(ActionMapping mapping,
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
		String orderBy = HttpUtil.getString(request, "orderBy", "name");
		boolean isAsc = HttpUtil.getBoolean(request, "isAsc", false);
//		String pClazz = request.getParameter("clazz");
//		Class clazz = Department.class;
		Long total = 0L;
		List queryList = new ArrayList();
		String provinceName = request.getParameter("name");
		System.out.println(provinceName);
		provinceName = HttpUtil.ConverUnicode(provinceName);
		String isLoad = request.getParameter("isLoad");
		if(isLoad!=null&&!"".equals("true")){
			Object obj = service.findUnique(Province.class, "name", provinceName);
			total = 1L;
			queryList.add(obj);
			json = this.encodeForProvinceCombo(queryList, total);
		}else{
			//按省名称查
			Map resultMap = ps.findProvinceByName(provinceName, orderBy, isAsc, pageNo, pageSize);
			
			total = (Long) resultMap.get("total");
			queryList = (List) resultMap.get("queryList");
			json = this.encodeForProvinceCombo(queryList, total);
		}
		httpServletResponse.setCharacterEncoding("gbk");
		httpServletResponse.getWriter().write(json);
		httpServletResponse.getWriter().flush();
		return null;
	}
	/**
	 * 组装平台数据
	 * @Methods Name encodeForPlatformCombo
	 * @Create In june 14, 2009 By awen
	 * @param queryList
	 * @param total
	 * @return String
	 */
	private String encodeForPlatformCombo(List<Platform> queryList,Long total){
		String json = "";
		if(queryList==null){
			json = "{success:false}";
		}else{
			for(Platform platform:queryList){
				String dataItem = "";
				dataItem += "id:'"+platform.getId()+"',";
				dataItem += "platformName:'"+platform.getName()+"'";
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
	 * 组装区域数据
	 * @Methods Name encodeForRegionCombo
	 * @Create In Jun 26, 2009 By sujs
	 * @param queryList
	 * @param total
	 * @return String
	 */
	private String encodeForRegionCombo(List<Region> queryList,Long total){
		String json = "";
		if(queryList==null){
			json = "{success:false}";
		}else{
			for(Region region:queryList){
				String dataItem = "";
				dataItem += "id:'"+region.getId()+"',";
				dataItem += "name:'"+region.getName()+"'";
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
	 * 组装返回省的数据的
	 * @Methods Name encodeForRegionCombo
	 * @Create In Sep 7, 2009 By Administrator
	 * @param queryList
	 * @param total
	 * @return String
	 */
	private String encodeForProvinceCombo(List<Province> queryList,Long total){
		String json = "";
		if(queryList==null){
			json = "{success:false}";
		}else{
			for(Province province:queryList){
				String dataItem = "";
				dataItem += "id:'"+province.getId()+"',";
				dataItem += "name:'"+province.getName()+"'";
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
	
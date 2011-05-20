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


public class UserRoleAction extends BaseDispatchAction{
	private MetaDataManager metaDataManager = (MetaDataManager) getBean("metaDataManager");
	private Service service = (Service) getBean("baseService");
	private SecurityManageService sms = (SecurityManageService) getBean("securityManageService");
	private DepartmentService deptService = (DepartmentService) getBean("deptService");
	
	/**
	 * ��ʾ������Դ
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
		
		/*String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		List users = sms.findUserByQueryParams(userName, realName);
		request.setAttribute("users", users);
		
		return mapping.findForward("listUsers");*/
		int pageNo = HttpUtil.getInt(request, "start", 1);
//		int start = HttpUtil.getInt(request, "start", 1);
		String userName = HttpUtil.ConverUnicode(request.getParameter("userName"));
		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("realName", realName);
		
		Page page = metaDataManager.query(UserInfo.class, map, pageNo, pageSize, "userName", true);
		
		
		List queryList = page.list(); //sms.findUserByQueryParamsForPage(userName, realName);
		request.setAttribute("users", queryList);
		
		List listData = metaDataManager.getEntityMapDataForList(UserInfo.class, queryList);
		String json = convertListData2JsonString(listData, page);
		
		//System.out.println("�û�����11���û��б�"+json);
		
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
	 * �޸��û������ɫ��Ϣ
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
	 * ȥ���һ����Դ
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
	
	
	@SuppressWarnings("unchecked")
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
	 * ������Դ
	 * @Methods Name saveRoles
	 * @Create In 2008-3-14 By Vincent
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
		user.setUserViewStyle("ext-all");  //Ĭ��ҳ����ʽ
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
		//List roles = sms.findRolesAllForPage();
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
			out.write("{success:" +true+ "}");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			if(out != null)
				out.close();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward addRolesToUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		//List rolesAll = sms.findRolesAll();
//		String realName = HttpUtil.ConverUnicode(request.getParameter("realName"));
//		String userName = request.getParameter("userName");
//		String password = request.getParameter("password");
//		String email = request.getParameter("email");
//		String telephone = request.getParameter("telephone");
//		String mobilePhone = request.getParameter("mobilePhone");
//		String enabled = request.getParameter("enabled");
//		String specialUser = request.getParameter("specialUser");
//		String departCode = request.getParameter("department");
		String userId = request.getParameter("id");
		
		try {				
				UserInfo user = sms.findUserById(userId);
//				user.setRealName(realName);
//				user.setUserName(userName);
//				user.setPassword(password);
//				user.setEmail(email);
//				user.setEnabled(new Integer(enabled));
//				user.setSpecialUser(new Integer(specialUser));
				
//				if(StringUtils.isNotBlank(departCode)){//��ֹ����codeΪnull�����쳣
//					Department department = deptService.findDepartmentById(Long.valueOf(departCode));
//					user.setDepartment(department);
//					user.setDepartCode(Long.valueOf(departCode));
//				}
//				user.setMobilePhone(mobilePhone);
//				user.setTelephone(telephone);
//				
				String data = request.getParameter("data");
				JSONArray jsonArray = JSONArray.fromObject(data);
				Set<Role> roleSet = user.getRoles();
				Set<String> idSet = new HashSet();
				for(Role role:roleSet){
					idSet.add(role.getId()+"");
				}
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
					//�ж��ǲ���ѡ��
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
	
	@SuppressWarnings("unchecked")
	public ActionForward removeRolesFromUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		String userId = request.getParameter("id");
		
		try {				
				UserInfo user = sms.findUserById(userId);
//				
//				
				String data = request.getParameter("data");
				JSONArray jsonArray = JSONArray.fromObject(data);
				Set<Role> roleSet = user.getRoles();
				Set<String> idSet = new HashSet();
				for(Role role:roleSet){
					idSet.add(role.getId()+"");
				}
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
					//�ж��ǲ���ѡ��
					String checked = jsonObject.getString("checked");
					String id = jsonObject.getString("id");			
					if (!"".equals(checked) && checked.equals("true")&&!idSet.contains(id)) {	
						Role role = sms.findRoleById(id);
						roleSet.remove(role);//ѡ�е�ɾ��
					}
					else if(!"".equals(checked) && checked.equals("false") && idSet.contains(id)){
						Role role = sms.findRoleById(id);
						roleSet.add(role);	//Ϊѡ�ı���			
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
	
	@SuppressWarnings("unchecked")
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
				
				if(StringUtils.isNotBlank(departCode)){//��ֹ����codeΪnull�����쳣
					Department department = deptService.findDepartmentById(Long.valueOf(departCode));
					user.setDepartment(department);
					user.setDepartCode(Long.valueOf(departCode));
				}
				user.setMobilePhone(mobilePhone);
				user.setTelephone(telephone);
				
				String data = request.getParameter("data");
				JSONArray jsonArray = JSONArray.fromObject(data);
				Set<Role> roleSet = user.getRoles();
				//Ҫɾ���Ľ�ɫ
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
					//�ж��ǲ���ѡ��
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
			sms.saveUserInfoWithRoles(user);
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
	public ActionForward findUser2(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String json="";
		String userId = request.getParameter("id");
		UserInfo userInfo = (UserInfo) super.getService().find(UserInfo.class, userId);
		Long departCode = userInfo.getDepartment().getId();//.getDepartCode();
		String dname = userInfo.getDepartment().getDepartName();
		json += "{\"departCode\":"+departCode+",\"dname\":\""+dname+"\"}";
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write(json);
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
	 * �����û�Id����û������еĽ�ɫId
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
		//���ҵ�ǰ�û����еĽ�ɫ��������ɫ���ϣ���checked:true
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
	
	@SuppressWarnings("unchecked")
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
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("�����û�ʱ,����ǰ̨�Ĳ������ݣ� "+json);
		try {
			httpServletResponse.setCharacterEncoding("gbk");
			httpServletResponse.getWriter().write(json);
			httpServletResponse.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
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
			    		strVal = "��";
			    	}else if(Integer.valueOf(0).equals(value)){
			    		strVal = "��";
			    	}
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
	 * ����COMBO�в�ѯ�������û�ģ����ѯ�ͳ�ʼ��COMBO�Ĳ�ѯ
	 * @Methods Name findDeptForCombo
	 * @Create In Mar 30, 2009 By chuanyu ou
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
		// ע���Ժ��������ȡ��
		int pageSize = 10;
		// ע���Ժ��������ȡ��
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
			//���������Ʋ�
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
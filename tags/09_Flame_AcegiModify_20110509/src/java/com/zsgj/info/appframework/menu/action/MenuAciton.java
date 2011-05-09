package com.zsgj.info.appframework.menu.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.zsgj.info.appframework.menu.entity.DeptMenu;
import com.zsgj.info.appframework.menu.entity.DeptMenuItem;
import com.zsgj.info.appframework.menu.entity.TemplateMenuItem;
import com.zsgj.info.appframework.menu.entity.UserExtraMenuItem;
import com.zsgj.info.appframework.menu.service.MenuService;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.web.json.JsonUtil;

public class MenuAciton extends BaseAction{

	private MenuService menuService;
	private DepartmentService departmentService;
	
	/**
	 * 获取用户主菜单
	 * @Methods Name loadRootUserMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String loadRootUserMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userId = request.getParameter("userId");
		String parentId = request.getParameter("parentId");
		UserInfo user = null;
		if(StringUtils.isNotBlank(userId)){
			user = (UserInfo) this.getService().find(UserInfo.class, userId);
		}else{
			user = UserContext.getUserInfo();
		}
		List<TemplateMenuItem> userRootMenus = menuService.findUserMenuByParent(user,null);
		String result = "";
		if(userRootMenus.isEmpty()){
			result = "{success:false}";
			throw new ApplicationException("你没有可显示的菜单，请联系部门管理员");
		}else{
			for(TemplateMenuItem templateMenuItem : userRootMenus){
				result += "{\"id\":"+templateMenuItem.getId()+",\"text\":\""+templateMenuItem.getMenuName()+"\"},";
			}
			result = "[" + result.substring(0, result.length()-1) + "]";
		}
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 展示用户菜单
	 * @Methods Name loadUserMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String loadUserMenuItem(){
		HttpServletRequest request = super.getRequest();
		String userId = request.getParameter("userId");
		String parentId = request.getParameter("parentId");
		UserInfo user = null;
		if(StringUtils.isNotBlank(userId)){
			user = (UserInfo) this.getService().find(UserInfo.class, userId);
		}else{
			user = UserContext.getUserInfo();
		}
		request.setAttribute("list", menuService.findUserMenuByParent(user,parentId));
		return "user_menu_json";
	}
	/**
	 * 加载菜单树
	 * @Methods Name loadTemplateMenuItem
	 * @Create In Aug 12, 2010 By lee
	 * @return String
	 */
	public String loadTemplateMenuItem(){
		HttpServletRequest request = super.getRequest();
		String parentId = request.getParameter("id");
		if("0".equals(parentId)){
			request.setAttribute("list", menuService.findTemplateMenuItemNoParent());
		}else{
			request.setAttribute("list", menuService.findTemplateMenuItemByParent(parentId));
		}
		return "template_menu_json";
	}
	/**
	 * 保存菜单项
	 * @Methods Name saveTemplateMenuItem
	 * @Create In Aug 14, 2010 By lee
	 * @return String
	 */
	public String saveTemplateMenuItem(){
		
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String parentId = request.getParameter("parentId");
		String menuId = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String leafFlag = request.getParameter("leafFlag");
		System.out.println("【"+parentId+"】【"+menuId+"】【"+name+"】【"+url+"】【"+leafFlag+"】");
		TemplateMenuItem curItem = new TemplateMenuItem();
		if(StringUtils.isBlank(parentId)&&StringUtils.isBlank(menuId)){//新增顶层菜单
			curItem.setMenuOrder(menuService.findTemplateMenuItemNoParent().size());
		}else if(StringUtils.isNotBlank(parentId)&&StringUtils.isBlank(menuId)){//新增非顶层菜单
			curItem.setParentItem(menuService.findTemplateMenuItemById(parentId));
			curItem.setMenuOrder(menuService.findTemplateMenuItemByParent(parentId).size());
		}else if(StringUtils.isNotBlank(menuId)){//编辑已有菜单
			curItem = menuService.findTemplateMenuItemById(menuId);
		}
		curItem.setMenuName(name);
		curItem.setMenuUrl(url);
		curItem.setLeafFlag(Integer.valueOf(leafFlag));
		this.getService().save(curItem);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 移动菜单项
	 * @Methods Name removeTemplateMenuItem
	 * @Create In Aug 14, 2010 By lee
	 * @return String
	 */
	public String removeTemplateMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String menuId = request.getParameter("id");
		TemplateMenuItem curItem = menuService.findTemplateMenuItemById(menuId);
		menuService.removeTemplateMenuItem(curItem);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name moveTemplateMenuItem
	 * @Create In Aug 14, 2010 By lee
	 * @return String
	 */
	public String moveTemplateMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String menuId = request.getParameter("id");
		String oldParentId = request.getParameter("oldParentId");
		String newParentId = request.getParameter("newParentId");
		String nodeIndex = request.getParameter("nodeIndex");
		System.out.println("【"+menuId+"】【"+oldParentId+"】【"+newParentId+"】【"+nodeIndex+"】");
		menuService.saveMenuItemMove(menuId,oldParentId,newParentId,Integer.valueOf(nodeIndex));
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write("{success:true}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取部门菜单
	 * @Methods Name listDeptMenus
	 * @Create In Aug 14, 2010 By lee
	 * @return String
	 */
	public String listDeptMenus(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		List<DeptMenu> deptMenus = menuService.findAllDeptMenus();
		StringBuffer sb = new StringBuffer();
		for(DeptMenu deptMenu : deptMenus){
			sb.append("{id:"+deptMenu.getId()+",name:\""+deptMenu.getName()+"\",deptCode:\""+deptMenu.getDept().getDepartCode()+"\",dept:\""+deptMenu.getDept().getDepartName()+"\"},");
		}
		String json = sb.toString();
		if(json.endsWith(",")){
			json = json.substring(0, json.length()-1);
		}
		json = "{success:true,data:["+json+"]}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String findDepartmentComboList(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int start = HttpUtil.getInt(getRequest(), "start", 0);
		int pageNo = start / 10 + 1;
		String depName = request.getParameter("departName");	
		Map map = departmentService.findDepartmentByDepName(depName, "departCode", true, pageNo, 10);
		List<Department> listData = (List<Department>) map.get("queryList");
		StringBuilder json = new StringBuilder("{success: true, rowCount:" + map.get("total") + ",data:[");
		for (int i = 0; i < listData.size(); i++) {
			if (i != 0)
				json.append(",");
			json.append("{");
			json.append("departCode:'" + listData.get(i).getDepartCode() + "',");
			json.append("departName:'" + listData.get(i).getDepartName() + "'");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取部门对应部门菜单
	 * @Methods Name findDeptMenuByDept
	 * @Create In Aug 20, 2010 By lee
	 * @return String
	 */
	public String findDeptMenuByDept(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String departCode = request.getParameter("department");
		Department dept = null;
		if(StringUtils.isNotBlank(departCode)){
			dept = departmentService.findDepartmentById(Long.valueOf(departCode));
		}else{
			dept = departmentService.findDepartmentById(null);
		}
		List<DeptMenu> deptmenus = menuService.findDeptMenuByDept(dept);
		StringBuilder json = new StringBuilder("{success: true,data:[");
		for (int i = 0; i < deptmenus.size(); i++) {
			if (i != 0)
				json.append(",");
			json.append("{");
			json.append("id:" + deptmenus.get(i).getId() + ",");
			json.append("name:'" + deptmenus.get(i).getName() + "'");
			json.append("}");
		}
		json.append("]");
		json.append("}");
		String result = json.toString();
		System.out.println(result);
		PrintWriter out;
		response.setCharacterEncoding("UTF-8");
		try {
			out = response.getWriter();
			out.println(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	/**
	 * 加载部分菜单
	 * @Methods Name loadDeptMenu
	 * @Create In Aug 16, 2010 By lee
	 * @return String
	 */
	public String loadDeptMenu(){
		HttpServletRequest request = super.getRequest();
		String deptMenuId = request.getParameter("deptMenuId");
		String parentId = request.getParameter("id");
		DeptMenu deptmenu = menuService.findDeptMenuById(deptMenuId);
		if("0".equals(parentId)){
			request.setAttribute("list", menuService.findDeptMenuItemNoParent(deptmenu));
		}else{
			request.setAttribute("list", menuService.findDeptMenuItemByParent(parentId,deptmenu));
		}
		return "temp_menu_json";
	}
	/**
	 * 保存部门菜单
	 * @Methods Name saveDeptMenu
	 * @Create In Aug 17, 2010 By lee
	 * @return String
	 */
	public String saveDeptMenu(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		String deptMenuName = request.getParameter("deptMenuName");
		String deptMenuDept = request.getParameter("deptMenuDept");
		DeptMenu deptMenu = new DeptMenu();
		deptMenu.setId(StringUtils.isNotBlank(id)?Long.valueOf(id):null);
		deptMenu.setDept(StringUtils.isNotBlank(deptMenuDept)?departmentService.findDepartmentById(Long.valueOf(deptMenuDept)):null);
		deptMenu.setName(deptMenuName);
		deptMenu = menuService.saveDeptMenu(deptMenu);
		
		String json = "{success:true,id:"+deptMenu.getId()+"}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除部门菜单
	 * @Methods Name removeDeptMenu
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String removeDeptMenu(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String id = request.getParameter("id");
		DeptMenu deptMenu = menuService.findDeptMenuById(id);
		menuService.removeDeptMenu(deptMenu);
		
		String json = "{sucess:true,id:"+deptMenu.getId()+"}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 添加部门菜单菜单项
	 * @Methods Name addDeptMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String addDeptMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptMenuId = request.getParameter("deptMenuId");
		String templateMenuItemId = request.getParameter("templateMenuItemId");
		DeptMenu deptMenu = menuService.findDeptMenuById(deptMenuId);
		TemplateMenuItem templateMenuItem = menuService.findTemplateMenuItemById(templateMenuItemId);
		DeptMenuItem deptMenuItem = new DeptMenuItem();
		deptMenuItem.setDeptMenu(deptMenu);
		deptMenuItem.setTemplateMenuItem(templateMenuItem);
		menuService.saveDeptMenuItem(deptMenuItem);
		
		String json = "{sucess:true}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String removeDeptMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String deptMenuId = request.getParameter("deptMenuId");
		String templateMenuItemId = request.getParameter("templateMenuItemId");
		DeptMenu deptMenu = menuService.findDeptMenuById(deptMenuId);
		TemplateMenuItem templateMenuItem = menuService.findTemplateMenuItemById(templateMenuItemId);
		DeptMenuItem deptMenuItem = menuService.findDeptMenuItem(deptMenu,templateMenuItem);
		menuService.removeDeptMenuItem(deptMenuItem);
		String json = "{sucess:true}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 加载用户额外菜单
	 * @Methods Name loadUserExtraMenu
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String loadUserExtraMenu(){
		HttpServletRequest request = super.getRequest();
		String userId = request.getParameter("userId");
		String parentId = request.getParameter("id");
		UserInfo user = null;
		if(StringUtils.isNotBlank(userId)){
			user = (UserInfo) this.getService().find(UserInfo.class, userId);
		}
		request.setAttribute("list", menuService.findUserExtraMenuItemByParent(parentId,user));
		return "temp_menu_json";
	}
	/**
	 * 增加用户额外菜单项
	 * @Methods Name addUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String addUserExtraMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userId = request.getParameter("userId");
		String templateMenuItemId = request.getParameter("templateMenuItemId");
		UserInfo user = (UserInfo) this.getService().find(UserInfo.class, userId);
		TemplateMenuItem templateMenuItem = menuService.findTemplateMenuItemById(templateMenuItemId);
		UserExtraMenuItem userExtraMenuItem = new UserExtraMenuItem();
		userExtraMenuItem.setUserInfo(user);
		userExtraMenuItem.setTemplateMenuItem(templateMenuItem);
		menuService.saveUserExtraMenuItem(userExtraMenuItem);
		
		String json = "{sucess:true}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Methods Name removeUserExtraMenuItem
	 * @Create In Aug 18, 2010 By lee
	 * @return String
	 */
	public String removeUserExtraMenuItem(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String userId = request.getParameter("userId");
		String templateMenuItemId = request.getParameter("templateMenuItemId");
		UserInfo user = (UserInfo) this.getService().find(UserInfo.class, userId);
		TemplateMenuItem templateMenuItem = menuService.findTemplateMenuItemById(templateMenuItemId);
		UserExtraMenuItem userExtraMenuItem = menuService.findUserExtraMenuItem(user,templateMenuItem);
		menuService.removeUserExtraMenuItem(userExtraMenuItem);
		String json = "{sucess:true}";
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public DepartmentService getDepartmentService() {
		return departmentService;
	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}

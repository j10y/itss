package com.xpsoft.webservice.service.login.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.jbpm.pv.TaskInfo;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.StringUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.archive.ArchDispatchService;
import com.xpsoft.oa.service.flow.TaskService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.DepartmentService;
import com.xpsoft.webservice.service.login.LoginServie;

public class LoginServiceImpl implements LoginServie {
	public String filter(String userId,String passwd){
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		AppUser user=userService.get(Long.parseLong(userId));
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				user.getUsername(), passwd);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		AuthenticationManager authenticationManager=(AuthenticationManager) AppUtil.getBean("authenticationManager");
		securityContext.setAuthentication(authenticationManager.authenticate(authRequest));
		SecurityContextHolder.setContext(securityContext);
		return null;
	}
	public String Login(String userName, String passwd) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		AppUser user=userService.findByUserName(userName);
		String newpasswd=StringUtil.encryptSha256(passwd);
		if(user!=null){
			if(user.getPassword().equalsIgnoreCase(newpasswd)){
				return "{\"success\":true,\"userid\":"+user.getId()+",\"password\":\""+passwd+"\"}";
			}else{
				return "{\"success\":false,\"msg\":\"密码错误\",\"userid\":\"\",\"password\":\"\"}";
			}
		}else{
			return "{\"success\":false,\"msg\":\"用户不存在！\",\"userid\":\"\",\"password\":\"\"}";
		}
	}

	public String getInfoCount(String userId,String passwd) {
		filter( userId,passwd);
		TaskService flowTaskService= (TaskService) AppUtil.getBean("flowTaskService");
		ArchDispatchService archDispatchService= (ArchDispatchService) AppUtil.getBean("archDispatchService");
		PagingBean pb = new PagingBean(0, 999999);
		List<TaskInfo> tasks = flowTaskService.getTaskInfosByUserId(userId, pb);
		QueryFilter filter = new QueryFilter(pb);
		filter.addFilter("Q_userId_L_EQ", userId);
		filter.addFilter("Q_archUserType_SN_EQ","0");
		List list = archDispatchService.getAll(filter);
		List<TaskInfo> dycylist=new ArrayList();
		List<TaskInfo> dbgwlist=new ArrayList();
		for(TaskInfo task:tasks){
			if(task.getTaskName().equals("科室主任传阅")){
				dycylist.add(task);
			}else{
				dbgwlist.add(task);
			}
		}
		String sql="select newsId from (select newsId from notice_news_comment " +
				" where notice_news_comment.flag=2 and notice_news_comment.content=0 and notice_news_comment.userId="+userId +
				" union select newsId from notice_news where  notice_news.isAll=1 and newsId not in (select distinct newsId from notice_news_comment)) a";
		List dytzlist=flowTaskService.findDataList(sql);
		String yytzsql="select newsId from notice_news_comment where notice_news_comment.flag=2 and notice_news_comment.content=1 and notice_news_comment.userId="+userId;
		List yytzlist=flowTaskService.findDataList(yytzsql);
		return "{success:true, dytzCount :\""+dytzlist.size()+"\",dycyCount :\""+dycylist.size()+"\", dbsxCount :\""+dbgwlist.size()+"\", yytzCount :\""+yytzlist.size()+"\", yycyCount :\""+list.size()+"\"}";
	}

	public String findUserByUserName(String userName,String userId,String passwd) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		PagingBean pb = new PagingBean(0, 999999);
		QueryFilter filter = new QueryFilter(pb);
		if(userName!=null&&userName.length()>0){
			filter.addFilter("Q_username,fullname_S_ORLIKE", userName);
		}
		List<AppUser> list=userService.getAll(filter);
		String json="{\"success\":true,data:[";
		for(AppUser ap:list){
			json+="{id:\""+ap.getId()+"\",name:\""+ap.getFullname()+"/"+ap.getUsername()+"\"},";
		}
		if(list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		return json;
	}
	public String findUserByUserName(String userName,String userId,String passwd,String departId) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		PagingBean pb = new PagingBean(0, 999999);
		QueryFilter filter = new QueryFilter(pb);
		if(userName!=null&&userName.length()>0){
			filter.addFilter("Q_username,fullname_S_ORLIKE", userName);
		}
		if(departId!=null&&departId.length()>0){
			filter.addFilter("Q_department.depId_L_EQ", departId);
		}
		List<AppUser> list=userService.getAll(filter);
		String json="{\"success\":true,data:[";
		for(AppUser ap:list){
			json+="{id:\""+ap.getId()+"\",name:\""+ap.getFullname()+"/"+ap.getUsername()+"\"},";
		}
		if(list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		return json;
	}
	public String findDepartment() {
		// TODO Auto-generated method stub
		DepartmentService departmentService=(DepartmentService) AppUtil.getBean("departmentService");
		PagingBean pb = new PagingBean(0, 999999);
		QueryFilter filter = new QueryFilter(pb);
		
		List<Department> list=departmentService.getAll(filter);
		String json="{\"success\":true,data:[";
		for(Department ap:list){
			json+="{id:\""+ap.getDepId()+"\",name:\""+ap.getDepName()+"\"},";
		}
		if(list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		return json;
	}
	public String findUserByDepartId(String departId) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		PagingBean pb = new PagingBean(0, 999999);
		QueryFilter filter = new QueryFilter(pb);
		filter.addFilter("Q_department.depId_L_EQ", departId);
		List<AppUser> list=userService.getAll(filter);
		String json="{\"success\":true,data:[";
		for(AppUser ap:list){
			json+="{id:\""+ap.getId()+"\",name:\""+ap.getFullname()+"/"+ap.getUsername()+"\"},";
		}
		if(list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		return json;
	}		
}

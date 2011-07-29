package com.xpsoft.webservice.service.login.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.pvm.internal.task.TaskImpl;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.jbpm.pv.TaskInfo;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.StringUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchDispatchService;
import com.xpsoft.oa.service.flow.TaskService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.webservice.service.login.LoginServie;

public class LoginServiceImpl implements LoginServie {
	
	public String Login(String userName, String passwd) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		AppUser user=userService.findByUserName(userName);
		String newpasswd=StringUtil.encryptSha256(passwd);
		if(user!=null){
			if(user.getPassword().equalsIgnoreCase(newpasswd)){
				return "{\"success\":true,\"userid\":"+user.getId()+",\"password\":\""+user.getPassword()+"\"}";
			}else{
				return "{\"success\":false,\"msg\":\"密码错误\",\"userid\":\"\",\"password\":\"\"}";
			}
		}else{
			return "{\"success\":false,\"msg\":\"用户不存在！\",\"userid\":\"\",\"password\":\"\"}";
		}
	}

	public String getInfoCount(String userId) {
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
			if(task.getTaskName().equals("承办传阅")){
				dycylist.add(task);
			}else{
				dbgwlist.add(task);
			}
		}
		return "{success:true, dytzCount :\"0\",dycyCount :\""+dycylist.size()+"\", dbgwCount :\""+dbgwlist.size()+"\", yytzCount :\"0\", yycyCount :\""+list.size()+"\"}";
	}

	public String findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		PagingBean pb = new PagingBean(0, 999999);
		QueryFilter filter = new QueryFilter(pb);
		filter.addFilter("Q_username,fullname_S_ORLIKE", userName);
		List<AppUser> list=userService.getAll(filter);
		String json="{data:[";
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

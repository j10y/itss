package com.xpsoft.webservice.service.login.Impl;

import java.util.List;

import javax.annotation.Resource;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.StringUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
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
		PagingBean pb = new PagingBean(0, 999999);
		List tasks = flowTaskService.getTaskInfosByUserId(userId, pb);
		return "{success:true, dytzCount :\"12\",dycyCount :\"23\", dbgwCount :\""+tasks.size()+"\", yytzCount :\"45\", yycyCount :\"78\"}";
	}

}

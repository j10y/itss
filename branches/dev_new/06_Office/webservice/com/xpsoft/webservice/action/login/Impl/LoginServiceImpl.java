package com.xpsoft.webservice.action.login.Impl;

import javax.annotation.Resource;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.webservice.action.login.LoginServie;

public class LoginServiceImpl implements LoginServie {
	
	public String Login(String userName, String passwd) {
		// TODO Auto-generated method stub
		AppUserService userService=(AppUserService) AppUtil.getBean("appUserService");
		AppUser user=userService.findByUserName(userName);
		System.out.println(user.getUsername()+"_"+user.getPassword());
		return user.getUsername()+"_"+user.getPassword();
	}

}

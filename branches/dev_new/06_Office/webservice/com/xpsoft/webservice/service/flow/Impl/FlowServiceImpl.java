package com.xpsoft.webservice.service.flow.Impl;

import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.webservice.service.flow.FlowService;

public class FlowServiceImpl implements FlowService{
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
	public String getDycyList(String userId, String passwd) {
		filter(userId,passwd);
		
		return null;
	}

}

// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   UserInfoThreadLocalFactory.java

package com.zsgj.info.framework.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zsgj.info.framework.security.entity.SecurityMessageInfo;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.AuthenticationCust;

/**
 * 用户信息缓存
 * @Class Name UserInfoContext
 * @Author 张鹏
 * @Create In Dec 10, 2007
 */
public class UserContext
{
	//用户信息存储
	private static final Map<String,SecurityMessageInfo> loginMessage = new HashMap<String,SecurityMessageInfo>();
    
    private static final Set login = Collections.synchronizedSet(new HashSet());
    public UserContext() {
    }
    
    /**
     * 获取当前登录的用户信息
     * @Methods Name getUserInfo
     * @Create In Dec 10, 2007 By 张鹏
     * @return UserInfo
     */
    public static UserInfo getUserInfo() {
    	ApplicationContext ctx = ContextHolder.getApplicationContext();
		ProviderManager authenticationManager = (ProviderManager) ctx
				.getBean("authenticationManager");
		
		//authenticationManager.getSessionController();

		//add by awen for chang acegi to security3 on 2011-5-4 begin
		//空
		if(SecurityContextHolder.getContext() == null){
			return null;
		}
		
		//匿名登录，可能为第一次登录
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken){
			return null;
		}
		//add by awen for chang acegi to security3 end
		
		AuthenticationCust authen = (AuthenticationCust)SecurityContextHolder.getContext()
				.getAuthentication();
		if(authen != null){
			return authen.getCurrentUserInfo();
		}else{
			return null;
		}
    	
    }
    
    /**
     * 获取当前用户的权限信息
     * @Methods Name getAuthorities
     * @Create In Sep 26, 2008 By 张鹏
     * @return GrantedAuthority[]
     */
    public static GrantedAuthority[] getAuthorities(){
    	ApplicationContext ctx = ContextHolder.getApplicationContext();
		ProviderManager authenticationManager = (ProviderManager) ctx
				.getBean("authenticationManager");
		
		//authenticationManager.getSessionController();
		
		AuthenticationCust authen = (AuthenticationCust)SecurityContextHolder.getContext()
				.getAuthentication();
		
		if(authen != null){
			return authen.getAuthorities().toArray(new GrantedAuthority[authen.getAuthorities().size()]);
		}else{
			return null;
		}
    }
    
    /**
     * 修改当前登陆用户信息
     * @Methods Name changeCurrentUserInfo
     * @Create In Sep 26, 2008 By 张鹏
     * @param userInfo void
     */
    public static void changeCurrentUserInfo(UserInfo userInfo){
    	ApplicationContext ctx = ContextHolder.getApplicationContext();
		ProviderManager authenticationManager = (ProviderManager) ctx
				.getBean("authenticationManager");
		
		//authenticationManager.getSessionController();
		
		AuthenticationCust authen = (AuthenticationCust)SecurityContextHolder.getContext()
				.getAuthentication();
		
		authen.setCurrentUserInfo(userInfo);
		SecurityContextHolder.getContext().setAuthentication(authen);
    }
    
    /**
     * 设置全局在线用户列表
     * @Methods Name setOnlineUser
     * @Create In Nov 10, 2008 By 张鹏
     * @param user void
     */
    public static void setOnlineUser(UserInfo user){
    	login.add(user);
    }
    
    /**
     * 移出在线用户
     * @Methods Name removeOnlineUser
     * @Create In Nov 10, 2008 By 张鹏
     * @param user void
     */
    public static void removeOnlineUser(UserInfo user){
    	login.remove(user);
    }
    
    /**
     * 获取全部在线用户
     * @Methods Name getOnlineUserList
     * @Create In Nov 10, 2008 By 张鹏
     * @return Map
     */
    public static Set getOnlineUserList(){
    	return login;
    }
    
    /**
     * 写入登陆信息,主要记录异常等等
     * @Methods Name setLoginMessage
     * @Create In Jan 4, 2009 By 张鹏
     * @param smi void
     */
    public static void setLoginMessage(String userName,SecurityMessageInfo smi){
    	loginMessage.put(userName,smi);
    }
    
    /**
     * 删除登陆写入信息,一般登陆后移除
     * @Methods Name removeLoginMessage
     * @Create In Jan 4, 2009 By 张鹏
     * @param smi void
     */
    public static void removeLoginMessage(String userName){
    	loginMessage.remove(userName);
    }
    
    /**
     * 获取登陆异常信息
     * @Methods Name getLoginMessage
     * @Create In Jan 4, 2009 By 张鹏
     * @return String
     */
    public static SecurityMessageInfo getLoginMessage(){
    	return loginMessage.get("loginerror");
    }
}

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
 * �û���Ϣ����
 * @Class Name UserInfoContext
 * @Author ����
 * @Create In Dec 10, 2007
 */
public class UserContext
{
	//�û���Ϣ�洢
	private static final Map<String,SecurityMessageInfo> loginMessage = new HashMap<String,SecurityMessageInfo>();
    
    private static final Set login = Collections.synchronizedSet(new HashSet());
    public UserContext() {
    }
    
    /**
     * ��ȡ��ǰ��¼���û���Ϣ
     * @Methods Name getUserInfo
     * @Create In Dec 10, 2007 By ����
     * @return UserInfo
     */
    public static UserInfo getUserInfo() {
    	ApplicationContext ctx = ContextHolder.getApplicationContext();
		ProviderManager authenticationManager = (ProviderManager) ctx
				.getBean("authenticationManager");
		
		//authenticationManager.getSessionController();

		//add by awen for chang acegi to security3 on 2011-5-4 begin
		//��
		if(SecurityContextHolder.getContext() == null){
			return null;
		}
		
		//������¼������Ϊ��һ�ε�¼
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
     * ��ȡ��ǰ�û���Ȩ����Ϣ
     * @Methods Name getAuthorities
     * @Create In Sep 26, 2008 By ����
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
     * �޸ĵ�ǰ��½�û���Ϣ
     * @Methods Name changeCurrentUserInfo
     * @Create In Sep 26, 2008 By ����
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
     * ����ȫ�������û��б�
     * @Methods Name setOnlineUser
     * @Create In Nov 10, 2008 By ����
     * @param user void
     */
    public static void setOnlineUser(UserInfo user){
    	login.add(user);
    }
    
    /**
     * �Ƴ������û�
     * @Methods Name removeOnlineUser
     * @Create In Nov 10, 2008 By ����
     * @param user void
     */
    public static void removeOnlineUser(UserInfo user){
    	login.remove(user);
    }
    
    /**
     * ��ȡȫ�������û�
     * @Methods Name getOnlineUserList
     * @Create In Nov 10, 2008 By ����
     * @return Map
     */
    public static Set getOnlineUserList(){
    	return login;
    }
    
    /**
     * д���½��Ϣ,��Ҫ��¼�쳣�ȵ�
     * @Methods Name setLoginMessage
     * @Create In Jan 4, 2009 By ����
     * @param smi void
     */
    public static void setLoginMessage(String userName,SecurityMessageInfo smi){
    	loginMessage.put(userName,smi);
    }
    
    /**
     * ɾ����½д����Ϣ,һ���½���Ƴ�
     * @Methods Name removeLoginMessage
     * @Create In Jan 4, 2009 By ����
     * @param smi void
     */
    public static void removeLoginMessage(String userName){
    	loginMessage.remove(userName);
    }
    
    /**
     * ��ȡ��½�쳣��Ϣ
     * @Methods Name getLoginMessage
     * @Create In Jan 4, 2009 By ����
     * @return String
     */
    public static SecurityMessageInfo getLoginMessage(){
    	return loginMessage.get("loginerror");
    }
}

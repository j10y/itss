package com.zsgj.info.framework.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;

import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;

public class AcegiRoleUtil {
	
	public static Resource getAuthorizationByAcegiRole(Set authories, String acegiRole){
		
		Iterator iter = authories.iterator();
		while(iter.hasNext()){
			Authorization item = (Authorization) iter.next();
			Resource res = item.getResource();
			Right right = item.getRight();
			if(right.getKeyName()==acegiRole){ 
				return res;
			}
		}
		return null;
	}
	
	
	private static UserDetails getUserDetailsFromUser(UserInfo user) {
		GrantedAuthority[] authorities = role2authorities(user.getRoles());
		UserDetails ud = new org.acegisecurity.userdetails.User(user.getUserName(), user
				 .getPassword(), user.isUserEnabled(), true, true, true, authorities);
		//userCache.putUserInCache(ud);
		return ud;
	}

	/**
	 * 由role转为GrantedAuthority
	 */
	private static GrantedAuthority[] role2authorities(Collection roles) {
		List authorities = new ArrayList();
		for (Iterator iter = roles.iterator(); iter.hasNext();) {
			Role role = (Role) iter.next();
			GrantedAuthority g = new GrantedAuthorityImpl(role.getName());//ROLE_开始的角色名称
			authorities.add(g);
		}
		return (GrantedAuthority[]) authorities.toArray(new GrantedAuthority[0]);
	}


}

/**
 * @Probject Name: CMS
 * @Path: com.jeecms.customize.manager.maim.implUserDetailServiceImpl.java
 * @Create By Jack
 * @Create In 2011-6-10 下午05:26:30
 * TODO
 */
package com.jeecms.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jeecms.core.dao.AuthenticationDao;
import com.jeecms.core.entity.UnifiedUser;
import com.jeecms.core.manager.UnifiedUserMng;

/**
 * @Class Name UserDetailServiceImpl
 * @Author Jack
 * @Create In 2011-6-10
 */

public class UserDetailServiceImpl implements UserDetailsService{
	private UnifiedUserMng unifiedUserMng;
	/**
	 * @Return the UnifiedUserMng unifiedUserMng
	 */
	public UnifiedUserMng getUnifiedUserMng() {
		return unifiedUserMng;
	}

	/**
	 * @Param UnifiedUserMng unifiedUserMng to set
	 */
	public void setUnifiedUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}

	/**
	 * @Return the AuthenticationDao dao
	 */
	public AuthenticationDao getDao() {
		return dao;
	}

	private AuthenticationDao dao;

	public void setDao(AuthenticationDao dao) {
		this.dao = dao;
	}

	public void setUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}
	
	public UserDetails loadUserByUsername(String  userName)
			throws UsernameNotFoundException, DataAccessException {
		// TODO Auto-generated method stub
		UnifiedUser user = unifiedUserMng.getByUsername(userName);
		if(user != null){
			
			List<GrantedAuthority> authsList = new ArrayList<GrantedAuthority>();
			authsList.add(new GrantedAuthorityImpl("AUTH_LOGIN"));
			return new User(user.getUsername(), user.getPassword(), true, true, true, true, (GrantedAuthority[])authsList.toArray(new GrantedAuthority[authsList.size()]));
		}else{
			throw new UsernameNotFoundException(userName + " 不存在");
		}
	}

}

package com.digitalchina.info.framework.security.service.impl;

import java.util.Set;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

import com.digitalchina.info.framework.security.dao.AcegiRoleDao;
import com.digitalchina.info.framework.security.dao.UserInfoDao;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.SecurityManageService;
import com.digitalchina.info.framework.security.service.UserDetailsService;
import com.digitalchina.info.framework.util.PropertiesUtil;

/**
 * 基于ACEGI的UserDetailsService实现类。
 * @Class Name AcegiUserDetailServiceImpl
 * @Author xiaofeng
 * @Create In 2008-3-4
 */
public class UserDetailServiceHibernateImpl 
			implements UserDetailsService{
	
	private UserInfoDao userDao = null;
	private AcegiRoleDao acegiRoleDao = null;
	private SecurityManageService securityManageService;

	
	public void setUserDao(UserInfoDao userDao) {
		this.userDao = userDao;
	}
	public void setAcegiRoleDao(AcegiRoleDao acegiRoleDao) {
		this.acegiRoleDao = acegiRoleDao;
	}

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		UserInfo user = userDao.selectUserByUserName(userName);
		if(user==null){
			throw new UsernameNotFoundException("no such user");
		}
		//add by duxh in 20100531 for 为没有角色的用户增加默认角色及其菜单----begin----
		Set roles = user.getRoles();
		if (roles.isEmpty()) {
			String defalutRoleId = PropertiesUtil.getProperties("system.security.defaultRole");
			Role defalutRole = securityManageService.findRole(Long.parseLong(defalutRoleId));
			roles.add(defalutRole);
			user.setRoles(roles);
			user = securityManageService.saveUserInfoWithRoles(user);
		}
		//add by duxh in 20100531 for 为没有角色的用户增加默认角色及其菜单----end----
		GrantedAuthority[] auths = null;
		
		//add by zhangpeng for acegisecurity modify in 20
		auths = acegiRoleDao.selectAcegiRoleNamesByUser(user);
		User userAcegi = new User(userName, user.getPassword(), user.isUserEnabled(), true, true, user.isUserNonLock(), auths, user);
		//add by zhangpeng for acegisecurity modify in 2008-05-09 end
		
		return userAcegi;
	}
	
	public UserDetails loadUserByUsername(String userName, boolean isSystemAdmin)
			throws UsernameNotFoundException, DataAccessException {
		UserInfo user = userDao.selectUserByUserName(userName);
		if(user==null){
			throw new UsernameNotFoundException("no such user");
		}
		GrantedAuthority[] auths = null;
		
		if(isSystemAdmin){
			auths = acegiRoleDao.selectAllRolesForSysman();
		}else{
			auths = acegiRoleDao.selectAcegiRoleNamesByUser(user);
		}
		
		User userAcegi = new User(userName, user.getPassword(), user.isUserEnabled(), true, true, user.isUserNonLock(), auths, user);
		
		return userAcegi;
	}
	public SecurityManageService getSecurityManageService() {
		return securityManageService;
	}
	public void setSecurityManageService(SecurityManageService securityManageService) {
		this.securityManageService = securityManageService;
	}
	public UserInfoDao getUserDao() {
		return userDao;
	}
	public AcegiRoleDao getAcegiRoleDao() {
		return acegiRoleDao;
	}
	
}

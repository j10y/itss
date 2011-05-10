package com.zsgj.info.framework.security.cache;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;

import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.ResourceDetail;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserDetails;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.impl.User;

public interface AcegiCacheService {

	/**
	 * @Return the UserCache userCache
	 */
	public abstract UserCache getUserCache();

	/**
	 * @Param UserCache
	 *            userCache to set
	 */
	public abstract void setUserCache(UserCache userCache);

	/**
	 * 修改User时更改userCache
	 * 
	 * @Methods Name modifyUserInCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param user
	 * @param orgUsername
	 *            void
	 */
	public abstract void modifyUserInCache(UserDetails user, String orgUsername);

	/**
	 * 移出Userinfo
	 * @Methods Name removeUser
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param userName void
	 */
	public abstract void removeUser(String userName);
	
	/**
	 * 移出资源
	 * @Methods Name removeResource
	 * @Create In Oct 8, 2008 By 张鹏
	 * @param resString void
	 */
	public abstract void removeResource(String resString);
	
	/**
	 * 移出角色后更新缓存
	 * @Methods Name removeRole
	 * @Create In Oct 8, 2008 By 张鹏
	 * @param role void
	 */
	public abstract void removeRole(Role role);
	
	/**
	 * 移出授权后更新缓存
	 * @Methods Name removePermi
	 * @Create In Oct 10, 2008 By 张鹏
	 * @param permi void
	 */
	public abstract void removePermi(Authorization permi);
	
	/**
	 * 修改Resource时更改resourceCache
	 * 
	 * @Methods Name modifyResourceInCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param resource
	 * @param orgResourcename
	 *            void
	 */
	public abstract void modifyResourceInCache(Resource resource,
			String orgResourcename);
	
	/**
	 * 修改权限时更改用户缓存信息
	 * @Methods Name modifyRightInCache
	 * @Create In Oct 8, 2008 By 张鹏
	 * @param right
	 * @param orgRightName void
	 */
	public void modifyRightInCache(Right right, String orgRightName);

	/**
	 * 修改授权时同时修改userCache
	 * 
	 * @Methods Name modifyPermiInCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param permi
	 * @param orgPerminame
	 *            void
	 */
	public abstract void modifyPermiInCache(Authorization permi,
			String orgPerminame);

	/**
	 * User授予角色时更改userCache
	 * 
	 * @Methods Name authRoleInCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param user
	 *            void
	 */
	public abstract void authRoleInCache(User user);

	/**
	 * Role授予权限时更改userCache和resourceCache
	 * 
	 * @Methods Name authPermissionInCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param role
	 *            void
	 */
	public abstract void authPermissionInCache(Role role);

	/**
	 * 授予资源时更改userCache
	 * 
	 * @Methods Name authResourceInCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param permi
	 *            void
	 */
	public abstract void authResourceInCache(Authorization permi);

	/**
	 * 初始化userCache
	 * 
	 * @Methods Name initUserCache
	 * @Create In Sep 28, 2008 By 张鹏 void
	 */
	public abstract void initUserCache();

	/**
	 * 初始化resourceCache
	 * 
	 * @Methods Name initResourceCache
	 * @Create In Sep 28, 2008 By 张鹏 void
	 */
	public abstract void initResourceCache();

	/**
	 * 
	 * @Methods Name getUrlResStrings
	 * @Create In Sep 28, 2008 By 张鹏
	 * @return List
	 */
	public abstract List getUrlResStrings();

	/**
	 * 获取所有的Funtion资源
	 * 
	 * @Methods Name getFunctions
	 * @Create In Sep 28, 2008 By 张鹏
	 * @return List
	 */
	public abstract List getFunctions();

	/**
	 * 根据资源串获取资源
	 * 
	 * @Methods Name getAuthorityFromCache
	 * @Create In Sep 28, 2008 By 张鹏
	 * @param resString
	 * @return ResourceDetail
	 */
	public abstract ResourceDetail getAuthorityFromCache(String resString);

	/**
	 * @Param boolean
	 *            initializedUserCache to set
	 */
	public abstract void setInitializedUserCache(boolean initializedUserCache);

	/**
	 * @Param boolean
	 *            initializedResourceCache to set
	 */
	public abstract void setInitializedResourceCache(
			boolean initializedResourceCache);

	/**
	 * 是否初始化用户缓存
	 * @Methods Name isInitializedUserCache
	 * @Create In Oct 8, 2008 By 张鹏
	 * @return boolean
	 */
	public abstract boolean isInitializedUserCache();

	/**
	 * 是否已经初始化资源缓存
	 * @Methods Name isInitializedResourceCache
	 * @Create In Oct 8, 2008 By 张鹏
	 * @return boolean
	 */
	public abstract boolean isInitializedResourceCache();
	
	/**
	 * 获取用户角色
	 * @Methods Name getAcegiRoleNamesByUser
	 * @Create In Oct 8, 2008 By 张鹏
	 * @param user
	 * @return GrantedAuthority[]
	 */
	public abstract GrantedAuthority[] getAcegiRoleNamesByUser(UserInfo user);
	
	/**
	 * 组装UserDetail信息，UserDetail为安全框架用户信息
	 * @Methods Name buildUserDetail
	 * @Create In Oct 10, 2008 By 张鹏
	 * @param user
	 * @return UserDetails
	 */
	public abstract UserDetails buildUserDetail(UserInfo user);
}
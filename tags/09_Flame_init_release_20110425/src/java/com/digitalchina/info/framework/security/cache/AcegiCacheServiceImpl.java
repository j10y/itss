/**
 * @Probject Name: FrameworkB2B
 * @Path: com.digitalchina.info.framework.security.cacheAcegiCacheManager.java
 * @Create By 张鹏
 * @Create In Sep 28, 2008 4:51:03 PM
 * TODO
 */
package com.digitalchina.info.framework.security.cache;

import java.util.Iterator;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.providers.dao.UserCache;

import com.digitalchina.info.framework.security.Constants;
import com.digitalchina.info.framework.security.dao.AcegiRoleDao;
import com.digitalchina.info.framework.security.dao.SecurityManageDao;
import com.digitalchina.info.framework.security.dao.UserInfoDao;
import com.digitalchina.info.framework.security.entity.Authorization;
import com.digitalchina.info.framework.security.entity.Resource;
import com.digitalchina.info.framework.security.entity.ResourceDetail;
import com.digitalchina.info.framework.security.entity.Right;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserDetails;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.security.service.impl.User;
import com.digitalchina.info.framework.service.BaseService;

/**
 * @Class Name AcegiCacheManager
 * @Author 张鹏
 * @Create In Sep 28, 2008
 */
public class AcegiCacheServiceImpl extends BaseService implements
		AcegiCacheService {
	private UserCache userCache;
	private ResourceCache resourceCache;

	private UserInfoDao userDao;
	private AcegiRoleDao acegiRoleDao;
	private SecurityManageDao securityManageDao;

	private boolean initializedUserCache;
	private boolean initializedResourceCache;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getUserCache()
	 */
	public UserCache getUserCache() {
		return userCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setUserCache(org.acegisecurity.providers.dao.UserCache)
	 */
	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#modifyUserInCache(com.digitalchina.info.framework.security.service.UserDetails,
	 *      java.lang.String)
	 */
	public void modifyUserInCache(UserDetails user, String orgUsername) {
		if (this.getUserCache().getUserFromCache(user.getUsername()) != null) {
			this.getUserCache().removeUserFromCache(user.getUsername());
		}
		this.getUserCache().putUserInCache(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#removeUser(java.lang.String)
	 */
	public void removeUser(String userName) {
		// TODO Auto-generated method stub
		// this.getDaoUtils.getHibernateSupport.removeObject(User.class,id);
		if (this.getUserCache().getUserFromCache(userName) != null) {
			this.getUserCache().removeUserFromCache(userName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#removeResource(java.lang.String)
	 */
	public void removeResource(String resString) {
		if (this.getResourceCache().getAuthorityFromCache(resString) != null) {
			this.getResourceCache().removeAuthorityFromCache(resString);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#removeRole(com.digitalchina.info.framework.security.entity.Role)
	 */
	public void removeRole(Role role) {
		for (Iterator<UserInfo> it = role.getUserInfos().iterator(); it
				.hasNext();) {
			UserInfo user = (UserInfo) it.next();
			UserDetails newUser = buildUserDetail(user);
			this.modifyUserInCache(newUser, user.getUserName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#removePermi(com.digitalchina.info.framework.security.entity.Authorization)
	 */
	public void removePermi(Authorization permi) {
		modifyPermiInCache(permi, permi.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#modifyResourceInCache(com.digitalchina.info.framework.security.entity.Resource,
	 *      java.lang.String)
	 */
	public void modifyResourceInCache(Resource resource, String orgResourcename) {

		String resString = "";
		if (resource.getType().equals(Constants.RESOURCE_URL)) {
			resString = (resource.getClassName() + "/" + resource
					.getMethodName());
		} else if (resource.getType().equals(Constants.RESOURCE_FUNCTION)) {
			resString = (resource.getClassName() + "." + resource
					.getMethodName());
		}

		if (this.getResourceCache().getAuthorityFromCache(resString) != null) {
			this.getResourceCache().removeAuthorityFromCache(resString);
		}
		ResourceDetail resourceDetail = new ResourceDetail();
		resourceDetail.setResString(resString);
		resourceDetail.setResType(resource.getType());

		GrantedAuthority[] authority = new GrantedAuthority[resource
				.getAuthorizations().size()];
		int i = 0;
		for (Iterator<Authorization> item = resource.getAuthorizations()
				.iterator(); item.hasNext();) {
			Authorization author = (Authorization) item.next();
			authority[i] = new GrantedAuthorityImpl(author.getRight()
					.getKeyName());
		}
		resourceDetail.setAuthorities(authority);
		this.getResourceCache().putAuthorityInCache(resourceDetail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#modifyPermiInCache(org.acegisecurity.acls.Permission,
	 *      java.lang.String)
	 */
	public void modifyPermiInCache(Authorization permi, String orgPerminame) {
		List userList = this.userDao.selectUserByAuthorization(permi);
		for (Iterator<UserInfo> uit = userList.iterator(); uit.hasNext();) {
			UserInfo user = (UserInfo) uit.next();
			UserDetails newUser = buildUserDetail(user);
			this.modifyUserInCache(newUser, "");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#modifyRightInCache(com.digitalchina.info.framework.security.entity.Right,
	 *      java.lang.String)
	 */
	public void modifyRightInCache(Right right, String orgRightName) {

		List userList = this.userDao.selectUserByRight(right);

		for (Iterator<UserInfo> uit = userList.iterator(); uit.hasNext();) {
			UserInfo user = (UserInfo) uit.next();
			UserDetails newUser = buildUserDetail(user);
			this.modifyUserInCache(newUser, "");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#authRoleInCache(com.digitalchina.info.framework.security.service.impl.User)
	 */
	public void authRoleInCache(User user) {
		if (this.getUserCache().getUserFromCache(user.getUsername()) != null) {
			this.getUserCache().removeUserFromCache(user.getUsername());
		}
		this.getUserCache().putUserInCache(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#authPermissionInCache(com.digitalchina.info.framework.security.entity.Role)
	 */
	public void authPermissionInCache(Role role) {
		for (Iterator<UserInfo> it = role.getUserInfos().iterator(); it
				.hasNext();) {
			UserInfo user = (UserInfo) it.next();
			UserDetails newUser = buildUserDetail(user);
			this.modifyUserInCache(newUser, "");
		}

		for (Iterator<Authorization> it = role.getAuthorizations().iterator(); it
				.hasNext();) {
			Authorization author = (Authorization) it.next();
			Resource resource = author.getResource();
			this.modifyResourceInCache(resource, "");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#authResourceInCache(org.acegisecurity.acls.Permission)
	 */
	public void authResourceInCache(Authorization permi) {
		List userList = this.userDao.selectUserByAuthorization(permi);
		for (Iterator<UserInfo> uit = userList.iterator(); uit.hasNext();) {
			UserInfo user = (UserInfo) uit.next();
			UserDetails newUser = buildUserDetail(user);
			this.modifyUserInCache(newUser, "");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#buildUserDetail(com.digitalchina.info.framework.security.entity.UserInfo)
	 */
	public UserDetails buildUserDetail(UserInfo user) {
		GrantedAuthority[] auths = this.acegiRoleDao.selectAcegiRoleNamesByUser(user);
		if (isSystemAdmin(auths)) {
			auths = this.acegiRoleDao.selectAllRolesForSysman();
		}
		User newUser = new User(user.getUserName(), user.getPassword(), user
				.isUserEnabled(), true, true, user.isUserNonLock(), auths, user);
		return newUser;
	}

	/**
	 * 验证是否是系统管理员
	 * 
	 * @Methods Name isSystemAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isSystemAdmin(GrantedAuthority[] gAuthor) {
		String isSystemAdmin = this.getProperties(
				"system.adminkey.systemadmin", "AUTH_SYS_ADMIN");
		for (int i = 0; i < gAuthor.length; i++) {
			if (gAuthor[i].equals(isSystemAdmin)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证是否是用户管理员
	 * 
	 * @Methods Name isUserAdmin
	 * @Create In 2008-5-9 By zhangpeng
	 * @param userDetails
	 * @return boolean
	 */
	protected boolean isUserAdmin(GrantedAuthority[] gAuthor) {
		String isUserAdmin = this.getProperties("system.adminkey.useradmin",
				"ROLE_USER_ADMIN");
		for (int i = 0; i < gAuthor.length; i++) {
			if (gAuthor[i].equals(isUserAdmin)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#initUserCache()
	 */
	public void initUserCache() {
		if (!initializedUserCache) {
			List<UserInfo> users = this.getUserDao().selectAllUsers();
			if (null != users) {
				Iterator it = users.iterator();
				while (it.hasNext()) {
					UserInfo user = (UserInfo) it.next();
					UserDetails newUser = buildUserDetail(user);
					this.getUserCache().putUserInCache(newUser);
					userDao.clearUser(user);
				}
				this.initializedUserCache = true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#initResourceCache()
	 */
	public void initResourceCache() {
		if (!initializedResourceCache) {
			List<Resource> resources = this.securityManageDao
					.selectResourcesAllWithAuthorization();
			if (null != resources) {
				for (Iterator it = resources.iterator(); it.hasNext();) {
					Resource resource = (Resource) it.next();
					ResourceDetail resourceDetail = new ResourceDetail();
					if (resource.getType().equals(Constants.RESOURCE_URL)) {
						resourceDetail.setResString(resource.getClassName()
								+ "/" + resource.getMethodName());
					} else if (resource.getType().equals(
							Constants.RESOURCE_FUNCTION)) {
						resourceDetail.setResString(resource.getClassName()
								+ "." + resource.getMethodName());
					}
					resourceDetail.setResType(resource.getType());

					GrantedAuthority[] authority = new GrantedAuthority[resource
							.getAuthorizations().size()];
					int i = 0;
					for (Iterator<Authorization> item = resource
							.getAuthorizations().iterator(); item.hasNext();) {
						Authorization author = (Authorization) item.next();
						authority[i] = new GrantedAuthorityImpl(author
								.getRight().getKeyName());
					}
					resourceDetail.setAuthorities(authority);
					this.getResourceCache().putAuthorityInCache(resourceDetail);
				}

				this.initializedResourceCache = true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getUrlResStrings()
	 */
	public List getUrlResStrings() {
		return this.getResourceCache().getUrlResStrings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getFunctions()
	 */
	public List getFunctions() {
		return this.getResourceCache().getFunctions();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getAuthorityFromCache(java.lang.String)
	 */
	public ResourceDetail getAuthorityFromCache(String resString) {
		return this.getResourceCache().getAuthorityFromCache(resString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getResourceCache()
	 */
	public ResourceCache getResourceCache() {
		return resourceCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setResourceCache(com.digitalchina.info.framework.security.cache.ResourceCache)
	 */
	public void setResourceCache(ResourceCache resourceCache) {
		this.resourceCache = resourceCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getUserDao()
	 */
	public UserInfoDao getUserDao() {
		return userDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setUserDao(com.digitalchina.info.framework.security.dao.UserInfoDao)
	 */
	public void setUserDao(UserInfoDao userDao) {
		this.userDao = userDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getAcegiRoleDao()
	 */
	public AcegiRoleDao getAcegiRoleDao() {
		return acegiRoleDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setAcegiRoleDao(com.digitalchina.info.framework.security.dao.AcegiRoleDao)
	 */
	public void setAcegiRoleDao(AcegiRoleDao acegiRoleDao) {
		this.acegiRoleDao = acegiRoleDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setInitializedUserCache(boolean)
	 */
	public void setInitializedUserCache(boolean initializedUserCache) {
		this.initializedUserCache = initializedUserCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setInitializedResourceCache(boolean)
	 */
	public void setInitializedResourceCache(boolean initializedResourceCache) {
		this.initializedResourceCache = initializedResourceCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#getSecurityManageDao()
	 */
	public SecurityManageDao getSecurityManageDao() {
		return securityManageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#setSecurityManageDao(com.digitalchina.info.framework.security.dao.SecurityManageDao)
	 */
	public void setSecurityManageDao(SecurityManageDao securityManageDao) {
		this.securityManageDao = securityManageDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#isInitializedUserCache()
	 */
	public boolean isInitializedUserCache() {
		return initializedUserCache;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.digitalchina.info.framework.security.cache.AcegiCacheService#isInitializedResourceCache()
	 */
	public boolean isInitializedResourceCache() {
		return initializedResourceCache;
	}

	public GrantedAuthority[] getAcegiRoleNamesByUser(UserInfo user) {
		return this.acegiRoleDao.selectAcegiRoleNamesByUser(user);
	}
}

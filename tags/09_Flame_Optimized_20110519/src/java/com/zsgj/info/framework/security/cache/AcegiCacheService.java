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
	 * �޸�Userʱ����userCache
	 * 
	 * @Methods Name modifyUserInCache
	 * @Create In Sep 28, 2008 By ����
	 * @param user
	 * @param orgUsername
	 *            void
	 */
	public abstract void modifyUserInCache(UserDetails user, String orgUsername);

	/**
	 * �Ƴ�Userinfo
	 * @Methods Name removeUser
	 * @Create In Sep 28, 2008 By ����
	 * @param userName void
	 */
	public abstract void removeUser(String userName);
	
	/**
	 * �Ƴ���Դ
	 * @Methods Name removeResource
	 * @Create In Oct 8, 2008 By ����
	 * @param resString void
	 */
	public abstract void removeResource(String resString);
	
	/**
	 * �Ƴ���ɫ����»���
	 * @Methods Name removeRole
	 * @Create In Oct 8, 2008 By ����
	 * @param role void
	 */
	public abstract void removeRole(Role role);
	
	/**
	 * �Ƴ���Ȩ����»���
	 * @Methods Name removePermi
	 * @Create In Oct 10, 2008 By ����
	 * @param permi void
	 */
	public abstract void removePermi(Authorization permi);
	
	/**
	 * �޸�Resourceʱ����resourceCache
	 * 
	 * @Methods Name modifyResourceInCache
	 * @Create In Sep 28, 2008 By ����
	 * @param resource
	 * @param orgResourcename
	 *            void
	 */
	public abstract void modifyResourceInCache(Resource resource,
			String orgResourcename);
	
	/**
	 * �޸�Ȩ��ʱ�����û�������Ϣ
	 * @Methods Name modifyRightInCache
	 * @Create In Oct 8, 2008 By ����
	 * @param right
	 * @param orgRightName void
	 */
	public void modifyRightInCache(Right right, String orgRightName);

	/**
	 * �޸���Ȩʱͬʱ�޸�userCache
	 * 
	 * @Methods Name modifyPermiInCache
	 * @Create In Sep 28, 2008 By ����
	 * @param permi
	 * @param orgPerminame
	 *            void
	 */
	public abstract void modifyPermiInCache(Authorization permi,
			String orgPerminame);

	/**
	 * User�����ɫʱ����userCache
	 * 
	 * @Methods Name authRoleInCache
	 * @Create In Sep 28, 2008 By ����
	 * @param user
	 *            void
	 */
	public abstract void authRoleInCache(User user);

	/**
	 * Role����Ȩ��ʱ����userCache��resourceCache
	 * 
	 * @Methods Name authPermissionInCache
	 * @Create In Sep 28, 2008 By ����
	 * @param role
	 *            void
	 */
	public abstract void authPermissionInCache(Role role);

	/**
	 * ������Դʱ����userCache
	 * 
	 * @Methods Name authResourceInCache
	 * @Create In Sep 28, 2008 By ����
	 * @param permi
	 *            void
	 */
	public abstract void authResourceInCache(Authorization permi);

	/**
	 * ��ʼ��userCache
	 * 
	 * @Methods Name initUserCache
	 * @Create In Sep 28, 2008 By ���� void
	 */
	public abstract void initUserCache();

	/**
	 * ��ʼ��resourceCache
	 * 
	 * @Methods Name initResourceCache
	 * @Create In Sep 28, 2008 By ���� void
	 */
	public abstract void initResourceCache();

	/**
	 * 
	 * @Methods Name getUrlResStrings
	 * @Create In Sep 28, 2008 By ����
	 * @return List
	 */
	public abstract List getUrlResStrings();

	/**
	 * ��ȡ���е�Funtion��Դ
	 * 
	 * @Methods Name getFunctions
	 * @Create In Sep 28, 2008 By ����
	 * @return List
	 */
	public abstract List getFunctions();

	/**
	 * ������Դ����ȡ��Դ
	 * 
	 * @Methods Name getAuthorityFromCache
	 * @Create In Sep 28, 2008 By ����
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
	 * �Ƿ��ʼ���û�����
	 * @Methods Name isInitializedUserCache
	 * @Create In Oct 8, 2008 By ����
	 * @return boolean
	 */
	public abstract boolean isInitializedUserCache();

	/**
	 * �Ƿ��Ѿ���ʼ����Դ����
	 * @Methods Name isInitializedResourceCache
	 * @Create In Oct 8, 2008 By ����
	 * @return boolean
	 */
	public abstract boolean isInitializedResourceCache();
	
	/**
	 * ��ȡ�û���ɫ
	 * @Methods Name getAcegiRoleNamesByUser
	 * @Create In Oct 8, 2008 By ����
	 * @param user
	 * @return GrantedAuthority[]
	 */
	public abstract GrantedAuthority[] getAcegiRoleNamesByUser(UserInfo user);
	
	/**
	 * ��װUserDetail��Ϣ��UserDetailΪ��ȫ����û���Ϣ
	 * @Methods Name buildUserDetail
	 * @Create In Oct 10, 2008 By ����
	 * @param user
	 * @return UserDetails
	 */
	public abstract UserDetails buildUserDetail(UserInfo user);
}
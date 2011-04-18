package com.digitalchina.info.framework.security.dao;

import java.util.List;
import java.util.Set;

import org.acegisecurity.GrantedAuthority;

import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.entity.Resource;
import com.digitalchina.info.framework.security.entity.ResourceDetail;
import com.digitalchina.info.framework.security.entity.Right;
import com.digitalchina.info.framework.security.entity.UserInfo;

public interface AcegiRoleDao {
	
	public final static String RESOUCE_TYPE_FUNC = "FUNCTION";
	public final static String RESOUCE_TYPE_URL = "URL";

	/**
	 * 获取指定用户的所有资源
	 * @Methods Name selectResourcesByUser
	 * @Create In 2008-3-11 By peixf
	 * @param user
	 * @return Set
	 */
	Set selectResourcesByUser(UserInfo user);
	
	/**
	 * 通过模块获取模块下的所有资源
	 * @Methods Name selectResourceByModule
	 * @Create In 2008-3-11 By peixf
	 * @param module
	 * @return Resource
	 */
	Resource selectResourceByModule(Module module);
	
	/**
	 * 获取指定类型的资源 FUNCTION/ URL
	 * @Methods Name selectResourceByType
	 * @Create In 2008-3-11 By peixf
	 * @param type
	 * @return Resource
	 */
	Resource selectResourceByType(String type);
	
	/**
	 * 根据指定条件查询资源
	 * @Methods Name selectResourceByParam
	 * @Create In 2008-3-11 By peixf
	 * @param className
	 * @param methodName
	 * @return Resource
	 */
	Resource selectResourceByParam(String className, String methodName);
	
	/**
	 * 保存一个资源
	 * @Methods Name insertOrUpdateResource
	 * @Create In 2008-3-11 By peixf
	 * @param resource
	 * @return Resource
	 */
	Resource insertOrUpdateResource(Resource resource);
	
	/**
	 * 根据acegi的权限/角色关键字(如ROLE_ADMIN/AUTH_ADMIN)获取一个权限
	 * @Methods Name selectRightByAcegiRoleKeyName
	 * @Create In 2008-3-11 By peixf
	 * @param acegiRoleKeyName
	 * @return Right
	 */
	Right selectRightByAcegiRoleKeyName(String acegiRoleKeyName);
	
	/**
	 * 获取所有函数类型的资源，list中存放的方法名称格式为：className+"."+methodName
	 * @Methods Name selectFunctionResources
	 * @Create In 2008-3-12 By peixf
	 * @return List
	 */
	List selectFunctionNames();
	
	/**
	 * 获取系统配置的所有URL
	 * @Methods Name selectUrls
	 * @Create In 2008-3-12 By peixf
	 * @return List
	 */
	List selectUrls();
	
	/**
	 * 通过函数名称获取资源细节
	 * @Methods Name selectByFunctionName
	 * @Create In 2008-3-12 By peixf
	 * @param functionName
	 * @return ResourceDetails
	 */
	ResourceDetail selectResourceDetailByFunctionName(String functionName);
	
	/**
	 * 通过url获取资源细节
	 * @Methods Name selectResourceDetailByUrl
	 * @Create In 2008-3-12 By peixf
	 * @param url
	 * @return ResourceDetail
	 */
	ResourceDetail selectResourceDetailByUrl(String url);
	/**
	 * 通过用户获取acegi验证使用的角色名称集合
	 * @Methods Name selectAcegiRoleNamesByUser
	 * @Create In 2008-3-12 By peixf
	 * @param user
	 * @return List，存放类似ROLE_CUST_ADMIN的角色字符串
	 */
	GrantedAuthority[] selectAcegiRoleNamesByUser(UserInfo user);
	
	
	GrantedAuthority[] selectAllRolesForSysman();
	
}

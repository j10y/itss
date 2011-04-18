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
	 * ��ȡָ���û���������Դ
	 * @Methods Name selectResourcesByUser
	 * @Create In 2008-3-11 By peixf
	 * @param user
	 * @return Set
	 */
	Set selectResourcesByUser(UserInfo user);
	
	/**
	 * ͨ��ģ���ȡģ���µ�������Դ
	 * @Methods Name selectResourceByModule
	 * @Create In 2008-3-11 By peixf
	 * @param module
	 * @return Resource
	 */
	Resource selectResourceByModule(Module module);
	
	/**
	 * ��ȡָ�����͵���Դ FUNCTION/ URL
	 * @Methods Name selectResourceByType
	 * @Create In 2008-3-11 By peixf
	 * @param type
	 * @return Resource
	 */
	Resource selectResourceByType(String type);
	
	/**
	 * ����ָ��������ѯ��Դ
	 * @Methods Name selectResourceByParam
	 * @Create In 2008-3-11 By peixf
	 * @param className
	 * @param methodName
	 * @return Resource
	 */
	Resource selectResourceByParam(String className, String methodName);
	
	/**
	 * ����һ����Դ
	 * @Methods Name insertOrUpdateResource
	 * @Create In 2008-3-11 By peixf
	 * @param resource
	 * @return Resource
	 */
	Resource insertOrUpdateResource(Resource resource);
	
	/**
	 * ����acegi��Ȩ��/��ɫ�ؼ���(��ROLE_ADMIN/AUTH_ADMIN)��ȡһ��Ȩ��
	 * @Methods Name selectRightByAcegiRoleKeyName
	 * @Create In 2008-3-11 By peixf
	 * @param acegiRoleKeyName
	 * @return Right
	 */
	Right selectRightByAcegiRoleKeyName(String acegiRoleKeyName);
	
	/**
	 * ��ȡ���к������͵���Դ��list�д�ŵķ������Ƹ�ʽΪ��className+"."+methodName
	 * @Methods Name selectFunctionResources
	 * @Create In 2008-3-12 By peixf
	 * @return List
	 */
	List selectFunctionNames();
	
	/**
	 * ��ȡϵͳ���õ�����URL
	 * @Methods Name selectUrls
	 * @Create In 2008-3-12 By peixf
	 * @return List
	 */
	List selectUrls();
	
	/**
	 * ͨ���������ƻ�ȡ��Դϸ��
	 * @Methods Name selectByFunctionName
	 * @Create In 2008-3-12 By peixf
	 * @param functionName
	 * @return ResourceDetails
	 */
	ResourceDetail selectResourceDetailByFunctionName(String functionName);
	
	/**
	 * ͨ��url��ȡ��Դϸ��
	 * @Methods Name selectResourceDetailByUrl
	 * @Create In 2008-3-12 By peixf
	 * @param url
	 * @return ResourceDetail
	 */
	ResourceDetail selectResourceDetailByUrl(String url);
	/**
	 * ͨ���û���ȡacegi��֤ʹ�õĽ�ɫ���Ƽ���
	 * @Methods Name selectAcegiRoleNamesByUser
	 * @Create In 2008-3-12 By peixf
	 * @param user
	 * @return List���������ROLE_CUST_ADMIN�Ľ�ɫ�ַ���
	 */
	GrantedAuthority[] selectAcegiRoleNamesByUser(UserInfo user);
	
	
	GrantedAuthority[] selectAllRolesForSysman();
	
}

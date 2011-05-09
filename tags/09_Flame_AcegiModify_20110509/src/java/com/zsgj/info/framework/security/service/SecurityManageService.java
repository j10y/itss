package com.zsgj.info.framework.security.service;

import java.util.List;
import java.util.Map;

import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 系统安全管理服务，后天管理如资源，权限，授权，角色之类的安全相关数据。
 * @Class Name SecurityManageService
 * @Author peixf
 * @Create In 2008-3-14
 */
public interface SecurityManageService {
	
	List findRoleByDept(Department dept);
	
	List findModulesAllForPage();
	
	List findModulesAll();
	
	List findModuleTopLevel();
	
	List findModuleTopLevelForPage();
	
	Module findModuleById(String moduleId);
	
	List findModuleByIdForPage(String moduleId);
	
	Module saveModule(Module module);
	
	void removeModule(String moduleId);
	
	List findResourcesAllForPage();
	
	List findResourcesAll();
	
	List findResourceByIdForPage(String resourceId);
	
	Resource findResourceById(String resourceId);
	
	Resource saveResource(Resource res);
	
	void removeResource(String resourceId);
	
	List findRightsAll();
	
	List findRightsAllForPage();
	
	List findRightByIdForPage(String rightId);
	
	Right findRightById(String rightId);
	
	Right saveRight(Right right);
	
	void removeRight(String rightId);
	
	List findAuthorizationsAll();
	
	List findAuthorizationsAllForPage();
	
	List findAuthorizationByIdForPage(String authId);
	
	Authorization findAuthorizationById(String authId);
	
	Authorization saveAuthorization(Authorization auth);
	
	void removeAuthorization(String authId);
	
	Role findRoleById(String roleId);
	
	List findRolesAll();
	
	List findRolesAllForPage();
	
	List findRoleByIdForPage(String roleId);
	
	Role saveRole(Role role);
	
	void removeRole(String roleId);
	
	void addRoleToUser(UserInfo userInfo, Role role);
	
	void removeRoleFromUser(UserInfo userInfo, String roleId);

	List findAuthorizationsOrderByModule();

	
	List findModuleWithAuthorizations();
	
	List findUserByQueryParams(String userName,String realName);
	
	List findUserByQueryParamsForPage(String userName,String realName);

	Page findUser(Map params, int pageNo, int pagesize, String propName, boolean isAsc); //用户查询，返回内部用户
	
	Page findUser(Map params, int pageNo, int pagesize, String propName, boolean isAsc, String propName2, boolean isAsc2);
	
	UserInfo findUserById(String userId);
	
	List findUserByIdForPage(String userId);
	
	void removeUser(String userId);
	
	UserInfo saveUserInfoWithRoles(UserInfo userInfo);
	
	UserInfo saveUserInfoPwdModify(UserInfo userInfo);
	
	UserInfo saveUserInfoStyleWithRoles(UserInfo userInfo);
	
	List findRoleIdsByUserIdForPage(String userId);
	
	List findAuthsByRoleIdForPage(String roleId);
	Page findRols(Map params, int pageNo, int pageSize, String propName, boolean isAsc);
	/**
	 * 查询锁定用户
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @param propName
	 * @param isAsc
	 * @return
	 */
	Page findLockUser(Map params, int pageNo, int pageSize, String propName, boolean isAsc);
	
	/**
	 * 根据id查询角色，抓取角色的部门和父部门
	 * @Methods Name findRole
	 * @Create In May 24, 2010 By duxh
	 * @param roleId
	 * @return 
	 * @Return Role
	 */
	public Role findRole(Long roleId);
}

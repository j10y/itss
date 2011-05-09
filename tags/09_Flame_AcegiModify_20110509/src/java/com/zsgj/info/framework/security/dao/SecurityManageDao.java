package com.zsgj.info.framework.security.dao;

import java.util.List;
import java.util.Set;

import com.zsgj.info.framework.security.entity.Authorization;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.info.framework.security.entity.Resource;
import com.zsgj.info.framework.security.entity.Right;
import com.zsgj.info.framework.security.entity.Role;
import com.zsgj.info.framework.security.entity.UserActionLog;
import com.zsgj.info.framework.security.entity.UserInfo;

public interface SecurityManageDao {
	
	List selectModulesAll();
	
	List selectModulesHasNoChild();
	
	Module selectModuleById(Long id);
	
	Module insertOrUpdateModule(Module module);
	
	void deleteModuleById(Long id);
	
	List selectResourcesAll();
	
	List selectResourcesAllWithAuthorization();
	
	List selectResourcesByType(String type);
	
	Resource selectResourceById(Long id);
	
	Resource selectResourceWithAuthorizationById(Long id);
	
	Resource insertOrUpdateResource(Resource res);
	
	void deleteResourceById(Long id);
	
	Right selectRightById(Long id);
	
	List selectRightsAll();
	
	Right insertOrUpdateRight(Right right);
	
	void deleteRightById(Long id);
	
	Authorization selectAuthorizById(Long id);
	
	List selectAuthorizationsAll();
	
	Authorization insertOrUpdateAuthoriz(Authorization auth);
	
	void deleteAuthorizById(Long id);
	
	UserInfo saveUserInfoWithRoles(UserInfo userInfo);
	
	UserInfo saveUserInfoStyleWithRoles(UserInfo userInfo);
	
	Role selectRoleById(Long id);
	
	List selectRolesAll();
	
	Role insertOrUpdateRole(Role role);
	
	void deleteRoleById(Long roleId);
	
	List selectAuthorizationsOrderByReourceModule();
	
	List selectModuleWithAuthorizations();
	
	List selectRoleAuthorizationsAll();
	
	/*RoleAuthoriz selectRoleAuthorizById(Long id);
	
	void deleteRoleAuthorizById(Long id);
	
	RoleAuthoriz insertOrUpdateRoleAuthoriz(RoleAuthoriz ra);*/
	
	List selectUsersByUserNameOrRealName(String username, String realname);
	
	UserInfo selectUserById(Long userId);
	
	void deleteUserById(Long userId);
	

	/**
	 * 保存用户操作信息
	 * @Methods Name insertOrUpdateUserActionLog
	 * @Create In 2009-7-27 By 张鹏
	 * @param userActionLog
	 * @return UserActionLog
	 */
	UserActionLog saveUserActionLog(UserActionLog userActionLog);
	
	
}

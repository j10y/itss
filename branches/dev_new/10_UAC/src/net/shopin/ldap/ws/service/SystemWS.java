/**
 * 
 */
package net.shopin.ldap.ws.service;

import java.util.List;

import net.shopin.ldap.entity.Department;
import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.User;

/**
 * 系统对外提供的WebService接口
 * @author wchao
 *
 */
public interface SystemWS {
	
	/**
	 * 获取用户列表
	 * @return
	 */
	List<User> findUserList();
	
	/**
	 * 通过用户唯一标示(uid)或者姓名(name)获取用户列表
	 * @param uidORName
	 * @return
	 */
	List<User> findUserListByParam(String uidORName);
	
	/**
	 * 通过用户唯一标示查询其详细信息
	 * @param uid
	 * @return
	 */
	User getUserDetailByUid(String uid);
	
	/**
	 * 获取部门列表
	 * @return
	 */
	List<Department> getDeptList();
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	void updateUser(User user);
	
	/**
	 * 根据角色cn查询用户列表
	 * @param roleCN
	 * @return
	 */
	List<User> findUserListByRoleCN(String roleCN);
	
	/**
	 * 根据用户组cn查询用户列表
	 * @param roleCN
	 * @return
	 */
	List<User> findUserListByGroupCN(String groupCN);
	
	/**
	 * 根据系统cn查询角色列表
	 * @param roleCN
	 * @return
	 */
	List<Role> findRoleListBySystemCN(String systemCN);
}

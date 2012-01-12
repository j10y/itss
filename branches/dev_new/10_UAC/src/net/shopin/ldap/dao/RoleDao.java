package net.shopin.ldap.dao;

import java.util.List;

import org.springframework.ldap.core.ContextMapper;

import net.shopin.ldap.entity.Role;
import net.shopin.ldap.entity.User;
import net.shopin.ldap.entity.UserGroup;

/**
 * 角色Dao接口
 * @author wchao
 *
 */
public interface RoleDao {
	
	/**
	 * 新建角色<br>
	 * 角色编号生成需要根据编号生成规则,在当前角色下最大的加1
	 * @param role
	 */
	void create(Role role);
	
	/**
	 * 修改角色，尽可以修改角色名称
	 * @param role
	 */
	void update(Role role);
	
	/**
	 * 物理删除角色，如果存在关联需要提前进行用户等数据迁移
	 * @param role
	 */
	void remove(Role role);
	
	/**
	 * 逻辑删除角色
	 * @param roleDN
	 */
	void deleteByDN(String roleDN);
	
	/**
	 * 根据角色编号查询角色信息
	 * @param roleDN
	 * @return
	 */
	Role findByDN(String roleDN);
	
	/**
	 * 查询角色列表，每次展开一级
	 * @param parentDN
	 * @return
	 */
	List<Role> findSubRolesByParentDN(String parentDN);
	
	/**
	 * 查询角色列表
	 * 从角色名称过滤
	 * @param parentNo
	 * @return
	 */
	List<Role> findRolesByParam(String param);
	
	/**
	 * 查询角色列表
	 * 从角色名称过滤
	 * @param parentNo
	 * @param userDN
	 * @param isRelation
	 * @return
	 */	
	List<Role> findRolesByParam(String param, String userDN, boolean isRelation);
	
	/**
	 * 
	 * @return
	 */
	ContextMapper getContextMapper();
	
	public List<User> listMembers(String rolepDN);
	
	public List<Role> findRolesByUserDN(String userDN);
}

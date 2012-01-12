package net.shopin.ldap.dao;

import java.util.List;

import net.shopin.ldap.entity.User;
import net.shopin.ldap.entity.UserGroup;

import org.springframework.ldap.core.ContextMapper;

/**
 * 用户组Dao接口
 * @author wchao
 *
 */
public interface GroupDao {
	
	/**
	 * 新建用户组<br>
	 * 用户组编号生成需要根据编号生成规则,在当前用户组下最大的加1
	 * @param userGroup
	 */
	void create(UserGroup userGroup);
	
	/**
	 * 修改用户组，尽可以修改用户组名称
	 * @param userGroup
	 */
	void update(UserGroup userGroup);
	
	/**
	 * 物理删除用户组，如果存在关联需要提前进行用户等数据迁移
	 * @param userGroup
	 */
	void remove(UserGroup userGroup);
	
	/**
	 * 逻辑删除用户组
	 * @param groupDN
	 */
	void deleteByDN(String groupDN);
	
	/**
	 * 根据用户组编号查询用户组信息
	 * @param groupDN
	 * @return
	 */
	UserGroup findByDN(String groupDN);
	
	/**
	 * 查询用户组列表，每次展开一级
	 * @param parentDN
	 * @return
	 */
	List<UserGroup> findSubGroupsByParentDN(String parentDN);
	
	/**
	 * 查询用户组列表
	 * 从用户组名称过滤
	 * @param parentNo
	 * @return
	 */
	List<UserGroup> findGroupsByParam(String param);
	
	/**
	 * 查询用户组列表
	 * 从用户组名称过滤
	 * @param parentNo
	 * @param userDN
	 * @param isRelation
	 * @return
	 */	
	List<UserGroup> findGroupsByParam(String param, String userDN, boolean isRelation);
	
	/**
	 * 
	 * @return
	 */
	ContextMapper getContextMapper();
	
	
	/**
	 * 是否超级管理员
	 * param userDN dn(dn+searchBase=fullname)
	 * @return
	 */
	public boolean isSupserAdmin(String userDN);
	
	public List<User> listMembers(String groupDN);
	
	public List<UserGroup> findGroupsByUserDN(String userDN);
}
